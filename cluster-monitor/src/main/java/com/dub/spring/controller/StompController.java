package com.dub.spring.controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.dub.spring.cluster.Display3;


@Controller
public class StompController {

	 
    /**
     * This method is required to push a Display3 object to the browser
     * */ 
    
    @MessageMapping("/displayNotify")
    @SendTo("/topic/kafkaDisplay")
    public Display3 displayNotify(Display3 display) throws Exception {
    
        return display;    
    }
    
}
