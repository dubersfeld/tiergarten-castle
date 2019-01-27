package com.dub.spring.cluster;

import java.util.HashMap;
import java.util.Map;

public class DisplayMult {
	
	private KafkaDisplayCluster kafkaDisplayCluster;

	private Map<String, Map<String, PartitionData>> partitionDatas;
	
	public DisplayMult() {
		partitionDatas = new HashMap<>();
	}

	public KafkaDisplayCluster getKafkaDisplayCluster() {
		return kafkaDisplayCluster;
	}

	public void setKafkaDisplayCluster(KafkaDisplayCluster kafkaDisplayCluster) {
		this.kafkaDisplayCluster = kafkaDisplayCluster;
	}

	public Map<String, Map<String, PartitionData>> getPartitionDatas() {
		return partitionDatas;
	}

	public void setPartitionDatas(Map<String, Map<String, PartitionData>> partitionDatas) {
		this.partitionDatas = partitionDatas;
	}

	
}
