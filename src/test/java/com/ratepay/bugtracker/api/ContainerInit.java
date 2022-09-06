package com.ratepay.bugtracker.api;

import org.jetbrains.annotations.NotNull;
import org.testcontainers.containers.MongoDBContainer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class ContainerInit implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2").withReuse(true);

//	@DynamicPropertySource
//	static void mongoProps(DynamicPropertyRegistry registry) {
//		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
//	}

	@Override
	public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
		mongoDBContainer.start();
	}
}
