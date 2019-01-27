package com.dub.spring.utils;

import java.io.IOException;

import com.dub.spring.cluster.ControllerData;
import com.dub.spring.cluster.PartitionData;
import com.dub.spring.kafka.BrokerData;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SerdeUtils {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static BrokerData readBrokerData(byte[] broker_data) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(broker_data, BrokerData.class);	
	}
	
	public static PartitionData readPartitionData(byte[] partition_data) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(partition_data, PartitionData.class);		
	}
	
	public static ControllerData readControllerData(byte[] controller_data) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(controller_data, ControllerData.class);		
	}
	
	

}
