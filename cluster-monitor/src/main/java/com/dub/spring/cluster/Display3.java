package com.dub.spring.cluster;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Display3 implements Serializable {

	/**
	 * Replace List<PartitionDisplay> by a Map<String, List<PartitionDisplay>>
	 * with topic as a key 
	 */
	private static final long serialVersionUID = -3933813238874933439L;
	
	private KafkaDisplayCluster kafkaDisplayCluster;

	private Map<String, List<PartitionDisplay>> partitions;
	
	public Display3() {
		partitions = new HashMap<>();
	}
	
	public KafkaDisplayCluster getKafkaDisplayCluster() {
		return kafkaDisplayCluster;
	}

	public void setKafkaDisplayCluster(KafkaDisplayCluster kafkaDisplayCluster) {
		this.kafkaDisplayCluster = kafkaDisplayCluster;
	}

	public Map<String, List<PartitionDisplay>> getPartitions() {
		return partitions;
	}

	public void setPartitions(Map<String, List<PartitionDisplay>> partitions) {
		this.partitions = partitions;
	}

	
	
}
