package org.izce.spring.jms;

import org.apache.activemq.artemis.core.config.Configuration;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJmsApplication {

	public static void main(String[] args) throws Exception {
		
		Configuration config = new ConfigurationImpl();
		config.addAcceptorConfiguration("in-vm", "vm://0");
		config.addAcceptorConfiguration("tcp", "tcp://127.0.0.1:61616");
		//config.setJournalDirectory("target/data");
	
		EmbeddedActiveMQ server = new EmbeddedActiveMQ();
		server.setConfiguration(config);
		server.start();
		
		SpringApplication.run(SpringJmsApplication.class, args);
	}

}
