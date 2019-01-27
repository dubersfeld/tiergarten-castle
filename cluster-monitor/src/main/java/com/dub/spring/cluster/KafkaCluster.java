package com.dub.spring.cluster;


import java.util.HashMap;
import java.util.Map;

public class KafkaCluster {
	
	private Map<String,String> brokerIds;
	
	
	public KafkaCluster() {
		brokerIds = new HashMap<>();
	}
	
	public KafkaCluster(Map<String,String> brokerIds) {
		this.brokerIds = brokerIds;
	}

	public Map<String, String> getBrokerIds() {
		return brokerIds;
	}

	public void setBrokerIds(Map<String, String> brokerIds) {
		this.brokerIds = brokerIds;
	}	
}
