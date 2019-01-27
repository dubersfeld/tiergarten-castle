package com.dub.spring.kafka;

import java.util.List;
import java.util.Map;

public class BrokerData {

	private Map<String, String> listener_security_protocol_map;
	private List<String> endpoints;
	private int jmx_port;
	private String host;
	private String timestamp;
	private int port;
	private int version;
	
	
	public Map<String, String> getListener_security_protocol_map() {
		return listener_security_protocol_map;
	}

	public void setListener_security_protocol_map(Map<String, String> listener_security_protocol_map) {
		this.listener_security_protocol_map = listener_security_protocol_map;
	}

	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getVersion() {
		return version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}

	public List<String> getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(List<String> endpoints) {
		this.endpoints = endpoints;
	}

	public int getJmx_port() {
		return jmx_port;
	}

	public void setJmx_port(int jmx_port) {
		this.jmx_port = jmx_port;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}


