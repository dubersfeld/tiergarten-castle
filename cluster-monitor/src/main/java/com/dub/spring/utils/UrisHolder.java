package com.dub.spring.utils;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

public class UrisHolder {
	
	@Value("${spring.cloud.zookeeper.connect-string}")
	private String zookeeperUri;
	
	@Value("${allocatedUris}")
	private String aUris;
	
	List<String> allocatedUris = new ArrayList<>();;

	
	@PostConstruct
	public void init() {
		String[] urisSplit = aUris.split(",");
		for (int i = 0; i < urisSplit.length; i++) {
			allocatedUris.add(urisSplit[i]);
		}
	}
	
	
	public List<String> getAllocatedUris() {
		return allocatedUris;
	}

	public void setAllocatedUris(List<String> allocatedUris) {
		this.allocatedUris = allocatedUris;
	}


	public String getaUris() {
		return aUris;
	}


	public void setaUris(String aUris) {
		this.aUris = aUris;
	}


	public String getZookeeperUri() {
		return zookeeperUri;
	}


	public void setZookeeperUri(String zookeeperUri) {
		this.zookeeperUri = zookeeperUri;
	}

}
