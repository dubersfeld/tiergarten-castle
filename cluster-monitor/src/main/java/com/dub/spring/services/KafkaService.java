package com.dub.spring.services;

import java.io.IOException;

public interface KafkaService {

	public void startBroker(String uri) 
		throws IOException, InterruptedException;
}
