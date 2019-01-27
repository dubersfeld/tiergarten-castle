package com.dub.spring.cluster;


import java.util.ArrayList;
import java.util.List;


/**
 * This POJO represents a single partition
 * */
public class PartitionDisplay {

	private String topic;
	private int partition;
	private String leader;
	private List<String> replicas;
	private List<String> inSyncReplicas;// not updated
	private List<String> offlineReplicas;
	
	
	public PartitionDisplay() {
		replicas = new ArrayList<>();
		inSyncReplicas = new ArrayList<>();
		offlineReplicas = new ArrayList<>();
	}
	
	
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public int getPartition() {
		return partition;
	}
	public void setPartition(int partition) {
		this.partition = partition;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public List<String> getReplicas() {
		return replicas;
	}
	public void setReplicas(List<String> replicas) {
		this.replicas = replicas;
	}
	public List<String> getInSyncReplicas() {
		return inSyncReplicas;
	}
	public void setInSyncReplicas(List<String> inSyncReplicas) {
		this.inSyncReplicas = inSyncReplicas;
	}


	public List<String> getOfflineReplicas() {
		return offlineReplicas;
	}


	public void setOfflineReplicas(List<String> offlineReplicas) {
		this.offlineReplicas = offlineReplicas;
	}
}
