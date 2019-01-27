package com.dub.spring.cluster;


import java.util.ArrayList;
import java.util.List;


/**
 * This POJO represents a single partition
 * */
public class PartitionData {

	private int controller_epoch;
	private String leader;
	private int version;
	private int leader_epoch;
	private List<String> isr;
	
	
	public PartitionData() {
		isr = new ArrayList<>();
	}


	public int getController_epoch() {
		return controller_epoch;
	}


	public void setController_epoch(int controller_epoch) {
		this.controller_epoch = controller_epoch;
	}


	public String getLeader() {
		return leader;
	}


	public void setLeader(String leader) {
		this.leader = leader;
	}


	public int getVersion() {
		return version;
	}


	public void setVersion(int version) {
		this.version = version;
	}


	public int getLeader_epoch() {
		return leader_epoch;
	}


	public void setLeader_epoch(int leader_epoch) {
		this.leader_epoch = leader_epoch;
	}


	public List<String> getIsr() {
		return isr;
	}


	public void setIsr(List<String> isr) {
		this.isr = isr;
	}
	
	
	


}
