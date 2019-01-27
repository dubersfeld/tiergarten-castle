package com.dub.spring.utils;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

public class TopicsHolder {

	@Value("${createdTopics}")
	private String topicsStr;
	
	List<String> topics = new ArrayList<>();;

	@PostConstruct
	public void init() {
		String[] enclume = topicsStr.split(",");
		for (int i = 0; i < enclume.length; i++) {
			topics.add(enclume[i]);
		}
	}


	public List<String> getTopics() {
		return topics;
	}

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}
	
}
