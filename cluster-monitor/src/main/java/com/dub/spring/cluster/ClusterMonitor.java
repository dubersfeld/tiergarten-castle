package com.dub.spring.cluster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;

import com.dub.spring.kafka.BrokerData;
import com.dub.spring.stomp.MyHandler;
import com.dub.spring.stomp.StompClient;
import com.dub.spring.utils.PartitionUtils;
import com.dub.spring.utils.SerdeUtils;
import com.dub.spring.utils.TopicsHolder;
import com.dub.spring.utils.UrisHolder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClusterMonitor implements Runnable {
					
	private Display3 display3 = new Display3();
			
	private KafkaConsumer<String, String> consumer;
	private List<String> brokerIds;
	
	private final Watcher connectionWatcher;
	private final Watcher clusterWatcher;
		
	private ZooKeeper zk;
	
	boolean clusterAlive = true;
		
	private static final String brokersIdsPath = "/brokers/ids";
	private static final String controllerPath = "/controller";
	private String partitionsBasePath;
		
	private static final String topicsBasePath = "/brokers/topics";
	 
	private UrisHolder urisHolder;
	private TopicsHolder topicsHolder;
	
	@Value("${allocatedUris}")
	private String brokerUris;
		
	public ClusterMonitor(UrisHolder urisHolder, TopicsHolder topicsHolder, MyHandler myHandler, StompClient stompClient) throws IOException, KeeperException, InterruptedException {
		
		this.urisHolder = urisHolder;
		this.topicsHolder = topicsHolder;
		
		this.partitionsBasePath = topicsBasePath + "/" + topicsHolder.getTopics().get(0) + "/" + "partitions";
							
		Properties kafkaConsProps = new Properties();
		
		kafkaConsProps.setProperty("bootstrap.servers", urisHolder.getaUris());
		kafkaConsProps.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaConsProps.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			
		consumer = new KafkaConsumer<>(kafkaConsProps);
		
		// initial watcher
		connectionWatcher = new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				
				if (event.getType() == Watcher.Event.EventType.None
						&&
					event.getState() == Watcher.Event.KeeperState.SyncConnected) {
					System.out.println("\nEvent Received: "
											+ event.toString());
				
					try {
						// set new watch using clusterWatcher
						brokerIds = zk.getChildren(brokersIdsPath, clusterWatcher);
															
						readDisplayMult();
						
						setDisplay3();
								
					} catch (KeeperException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						clusterAlive = false;
					} catch (JsonParseException e) {			
						e.printStackTrace();
					} catch (JsonMappingException e) {					
						e.printStackTrace();
					} catch (IOException e) {	
						e.printStackTrace();
					}
				}
			}
		};
		
		// main watcher
		clusterWatcher = new Watcher() {

			@Override
			public void process(WatchedEvent event) {
						
				System.out.println("\nEvent Received: "
						+ event.toString());
				if (event.getType() == Watcher.Event.EventType.NodeChildrenChanged
						||
					event.getType() == Watcher.Event.EventType.NodeDataChanged
						) {
				
					try {
						// set a watch
						brokerIds = zk.getChildren(brokersIdsPath, this);
								
						readDisplayMult();
						
						setDisplay3();
					
						// display 
						if (stompClient.getStompSession() != null) {
							myHandler.sendKafkaDisplay(stompClient.getStompSession());		
						}
							
					} catch (KeeperException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						clusterAlive = false;
					} catch (JsonParseException e) {
						e.printStackTrace();
					} catch (JsonMappingException e) {				
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}// process
		};
		
		zk = new ZooKeeper(urisHolder.getZookeeperUri(), 2000, connectionWatcher);	
	
	}// constructor
	
	public synchronized void close() {
		try {
			zk.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			synchronized(this) {
				while (clusterAlive) {
					wait();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} finally {
			this.close();
		}
		
	}
	
	
	private KafkaCluster readKafkaCluster() throws KeeperException, InterruptedException, JsonParseException, JsonMappingException, IOException {
		
		KafkaCluster kafkaCluster = new KafkaCluster();
			
		for (String id : brokerIds) {
					
			// set no watch
			String item = brokersIdsPath + "/" + id;
			byte[] node_data = zk.getData(item, null, null);
					
			BrokerData brokerData 
						= SerdeUtils.readBrokerData(node_data);
			
			String uri = brokerData.getHost() + ":" + brokerData.getPort();
							
			kafkaCluster.getBrokerIds().put(uri, id);		
		}
			
		return kafkaCluster;
	} 
	
	
	private KafkaDisplayCluster readKafkaDisplayCluster() throws JsonParseException, JsonMappingException, IOException, KeeperException, InterruptedException {
				
		KafkaDisplayCluster kafkaDisplayCluster = new KafkaDisplayCluster();
		
		List<String> ids = new ArrayList<>();
		List<String> uris = new ArrayList<>();
		
		for (String uri : urisHolder.getAllocatedUris()) {
			uris.add(uri);
			if (readKafkaCluster().getBrokerIds().containsKey(uri)) {
				ids.add(readKafkaCluster().getBrokerIds().get(uri));
			} else {
				ids.add("idle");
			}
		}
		
		kafkaDisplayCluster.setIds(ids);
		kafkaDisplayCluster.setUris(uris);
			
		kafkaDisplayCluster.setControllerId(Integer.toString(readControllerData().getBrokerid()));
		
		return kafkaDisplayCluster;
	}
	
	
	private PartitionData readPartitionData(String path) throws JsonParseException, JsonMappingException, IOException, KeeperException, InterruptedException {
		
		// set a watch
		byte[] data = zk.getData(path, clusterWatcher, null);
				
		PartitionData pData = SerdeUtils.readPartitionData(data);
			
		return pData;
	}
	
	private ControllerData readControllerData() throws JsonParseException, JsonMappingException, IOException, KeeperException, InterruptedException {
		
		// set a watch
		byte[] data = zk.getData(controllerPath, clusterWatcher, null);
				
		ControllerData cData = SerdeUtils.readControllerData(data);
		
		return cData;
	}
	
	
	private DisplayMult readDisplayMult() throws JsonParseException, JsonMappingException, IOException, KeeperException, InterruptedException {
			
		DisplayMult displayMult = new DisplayMult();
					
		for (String topic : topicsHolder.getTopics()) {
				
			partitionsBasePath = topicsBasePath + "/" + topic + "/" + "partitions";
						
			// get all partitions for the topic
			List<String> topic_partitions = zk.getChildren(partitionsBasePath, clusterWatcher, null);
						
			Map<String, PartitionData> pMap = new HashMap<>(); 
			
			for (String partition : topic_partitions) {
					
				String path = partitionsBasePath + "/" + partition + "/state";
			
				pMap.put(partition, readPartitionData(path));
					
			}
			
			displayMult.getPartitionDatas().put(topic, pMap);	
		}
		
		displayMult.setKafkaDisplayCluster(readKafkaDisplayCluster());
		
		return displayMult;
	}
	
	
	private void setDisplay3() throws JsonParseException, JsonMappingException, IOException, KeeperException, InterruptedException {
			
		display3.getPartitions().clear();
		
		Map<String, List<PartitionDisplay>> pMap = new HashMap<>();;
		
		// for each topic
		for (String topic : topicsHolder.getTopics()) {
			
			// get partitions for current topic
			List<PartitionInfo> partition_infos 
							= consumer.partitionsFor(topic);
			
			List<PartitionDisplay> partition_displays = new ArrayList<>();
			
			PartitionDisplay pDisp;
			
			for (PartitionInfo partition_info : partition_infos) {
				pDisp = PartitionUtils.toDisplay(partition_info);
					
				PartitionData pData = readDisplayMult()
							.getPartitionDatas().get(topic)
							.get( Integer.toString( pDisp.getPartition() ) );
				
				// update pDisp
				for (int i = 0; i < pData.getIsr().size(); i++) {
					pDisp.getInSyncReplicas().add(pData.getIsr().get(i));
				}
				
				partition_displays.add(pDisp);
							
			}
			
			pMap.put(topic, partition_displays);
			
		}		
		
		display3.setPartitions(pMap);
				
		display3.setKafkaDisplayCluster(readKafkaDisplayCluster());
	
	}
		
		
	public Display3 getDisplay3() {
		return display3;
	}
	
	
}
