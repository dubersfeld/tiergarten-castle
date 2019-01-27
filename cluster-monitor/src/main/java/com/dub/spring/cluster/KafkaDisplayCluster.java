package com.dub.spring.cluster;

import java.util.ArrayList;
import java.util.List;

public class KafkaDisplayCluster {
	
	private List<String> ids;
	private List<String> uris;
	private String controllerId;

	
	public KafkaDisplayCluster() {
		ids = new ArrayList<>();
		uris = new ArrayList<>();
	}
	
	public KafkaDisplayCluster(List<String> uris, List<String> ids) {
		this.ids = ids;
		this.uris = uris;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public List<String> getUris() {
		return uris;
	}

	public void setUris(List<String> uris) {
		this.uris = uris;
	}

	public String getControllerId() {
		return controllerId;
	}

	public void setControllerId(String controllerId) {
		this.controllerId = controllerId;
	}
	
	
}
