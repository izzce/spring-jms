package org.izce.spring.jms.listener;

import javax.jms.Message;

import org.izce.spring.jms.config.JmsConfig;
import org.izce.spring.jms.model.HelloWorldMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class HelloMessageListener {
	
	@JmsListener(destination = JmsConfig.MY_QUEUE)
	public void listen(@Payload HelloWorldMessage helloWorldMessage, 
			@Headers MessageHeaders headers, 
			Message message) {
		
		System.out.println("Message received!");
		System.out.println("helloWorldMessage: " + helloWorldMessage);
		System.out.println("headers: " + headers);
		System.out.println("message: " + message);
		
		//throw new RuntimeException("dummy-exception to cause re-delivery!");
	}

}

