package com.dub.spring.services;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dub.spring.utils.StreamGobbler;
import com.dub.spring.utils.UrisHolder;

@Service
public class KafkaServiceImpl implements KafkaService {

	@Value("${launchPathBase}")	
	private String launchPathBase;
	
	@Value("${password}")	
	private String password;
	
	@Value("${launchCommandBase}")
	private String launchCommandBase;

		
	@Autowired
	private UrisHolder urisHolder;
	
	@Override
	public void startBroker(String uri) throws IOException, InterruptedException {
		
		int index = urisHolder.getAllocatedUris().indexOf(uri);
		
		String arg;
		if (index == 0) {
			arg = "server.properties";	
		} else {
			arg = "server-" + index + ".properties";
		}
			
		String launchCommand = launchCommandBase + " " + arg;
			
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("bash", "-c", "cd " + launchPathBase + " ; pwd ; ls ; " + launchCommand); 

		Process process;
		
		try {
			process = builder.start();
			OutputStreamWriter output = new OutputStreamWriter(process.getOutputStream());
			
			StreamGobbler streamGobbler = 
	    		  new StreamGobbler(process.getInputStream(), System.out::println);    		
			Executors.newSingleThreadExecutor().submit(streamGobbler);
			 output.write(password + "\n");
             output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
		
}
