package org.izce.spring.jms.sender;

import static org.izce.spring.jms.config.JmsConfig.MY_QUEUE;
import static org.izce.spring.jms.config.JmsConfig.MY_SEND_RECEIVE_QUEUE;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.izce.spring.jms.model.HelloWorldMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class HelloSender {
	private final JmsTemplate jmsTemplate;
	private final ObjectMapper objectMapper;

	@Scheduled(fixedRate = 2000)
	public void sendMessage() {
		// System.out.println("Sending a message");

		HelloWorldMessage message = HelloWorldMessage.builder().id(UUID.randomUUID()).message("Hello World!").build();
		jmsTemplate.convertAndSend(MY_QUEUE, message);

		// System.out.println("Message sent!");
	}

	@Scheduled(fixedRate = 2000)
	public void sendAndReceiveMessage() throws JMSException {

		final HelloWorldMessage message = HelloWorldMessage.builder().id(UUID.randomUUID()).message("Hello").build();

		Message receivedMsg = jmsTemplate.sendAndReceive(MY_SEND_RECEIVE_QUEUE, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message helloMessage = null;
				try {
					helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
					helloMessage.setStringProperty("_type", HelloWorldMessage.class.getTypeName());

					System.out.println("Sending Hello");

					return helloMessage;

				} catch (JsonProcessingException e) {
					throw new JMSException("boom");
				}
			}
		});
		
		 System.out.println(receivedMsg.getBody(String.class));

	}

}
