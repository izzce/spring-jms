package org.izce.spring.jms.sender;

import static org.izce.spring.jms.config.JmsConfig.MY_QUEUE;

import java.util.UUID;

import org.izce.spring.jms.model.HelloWorldMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class HelloSender {
	private final JmsTemplate jmsTemplate;

	@Scheduled(fixedRate = 2000)
	void sendMessage() {
		
		System.out.println("Sending a message");
		
		HelloWorldMessage message = HelloWorldMessage
				.builder()
				.id(UUID.randomUUID())
				.message("Hello World!")
				.build();
	
		jmsTemplate.convertAndSend(MY_QUEUE, message);
		
		System.out.println("Message sent!");
		
	}
	
}
