package com.dub.spring.stomp;

import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import com.dub.spring.cluster.ClusterMonitor;
import com.dub.spring.cluster.Display3;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class MyHandler extends StompSessionHandlerAdapter {
	
	@Autowired
	private ClusterMonitor clusterMonitor;
		
	@Override
    public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {     
		
		try {
			sendKafkaDisplay(stompSession);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void sendKafkaDisplay(StompSession stompSession) throws JsonProcessingException, KeeperException, InterruptedException {
	       
		Display3 display3 = clusterMonitor.getDisplay3();
		
		ObjectMapper mapper = new ObjectMapper();
		
		stompSession.send("/app/displayNotify", display3);
    }
	
}
