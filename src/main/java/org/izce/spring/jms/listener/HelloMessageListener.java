package org.izce.spring.jms.listener;

import static org.izce.spring.jms.config.JmsConfig.MY_QUEUE;
import static org.izce.spring.jms.config.JmsConfig.MY_SEND_RECEIVE_QUEUE;

import java.util.UUID;

import javax.jms.Message;

import org.izce.spring.jms.model.HelloWorldMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class HelloMessageListener {

	private final JmsTemplate jmsTemplate;

	@JmsListener(destination = MY_QUEUE)
	public void listen(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers, Message message) {

//		System.out.println("Message received!");
//		System.out.println("helloWorldMessage: " + helloWorldMessage);
//		System.out.println("headers: " + headers);
//		System.out.println("message: " + message);
//		
		// throw new RuntimeException("dummy-exception to cause re-delivery!");
	}

	@JmsListener(destination = MY_SEND_RECEIVE_QUEUE)
	public void listenForHello(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers,
			Message message) throws Exception {

		HelloWorldMessage payloadMsg = HelloWorldMessage.builder().id(UUID.randomUUID()).message("World!!").build();

		jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMsg);
	}

}
