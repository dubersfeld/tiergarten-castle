package com.dub.spring;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.dub.spring.cluster.ClusterMonitor;
import com.dub.spring.stomp.MyHandler;
import com.dub.spring.stomp.StompClient;
import com.dub.spring.utils.TopicsHolder;
import com.dub.spring.utils.UrisHolder;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Bean("urisHolder")
	public UrisHolder urisHolder() {
		return new UrisHolder();
	}
	
	@Bean("topicsHolder")
	public TopicsHolder topicsHolder() {
		return new TopicsHolder();
	}
	
	@Bean("clusterMonitor")
	@DependsOn({"urisHolder", "topicsHolder", "myHandler", "stompClient"})
	public ClusterMonitor clusterMonitor() throws IOException, KeeperException, InterruptedException {
		return new ClusterMonitor(urisHolder(), topicsHolder(), myHandler(), stompClient());
	}
	
	@Bean("myHandler")
	public MyHandler myHandler() {
		return new MyHandler();
	}
	
	
	@Bean("stompClient")
	@DependsOn("myHandler")
	public StompClient stompClient() {
		StompClient stompClient = new StompClient(myHandler());	
		return stompClient;
	}
	

	public static void main(String[] args) throws KeeperException, InterruptedException {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		clusterMonitor().run();
		
	}
}
