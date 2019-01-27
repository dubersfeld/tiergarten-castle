package com.dub.spring.utils;


import org.apache.kafka.common.PartitionInfo;

import com.dub.spring.cluster.PartitionDisplay;

public class PartitionUtils {

	// PartitionInfo is a Kafka class
	public static PartitionDisplay toDisplay(PartitionInfo partitionInfo) {
	
		PartitionDisplay display = new PartitionDisplay();
		
		display.setPartition(partitionInfo.partition());
		display.setLeader(partitionInfo.leader().idString());
		display.setTopic(partitionInfo.topic());
		
		for (int i = 0; i < partitionInfo.replicas().length; i++) {
			display.getReplicas().add(partitionInfo.replicas()[i].idString());
		}
		
		// no update yet
		/*
		for (int i = 0; i < partitionInfo.inSyncReplicas().length; i++) {
			display.getInSyncReplicas().add(partitionInfo.inSyncReplicas()[i].idString());
		}
		*/
		
		for (int i = 0; i < partitionInfo.offlineReplicas().length; i++) {
			display.getOfflineReplicas().add(partitionInfo.offlineReplicas()[i].idString());
		}
			
		return display;
	}
}
