package com.jsjg73.hibernate.multiplicity;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	public static SessionFactory getSessionFactory(Strategy strategy) {
		if(sessionFactory == null)
			sessionFactory = buildSessionFactory(strategy);
		
		return sessionFactory;
	}
	private static SessionFactory buildSessionFactory(Strategy strategy){
		try {
		ServiceRegistry registry = confiureServiceRegistry();
		
		MetadataSources metadataSources = new MetadataSources(registry);
		for(Class<?> entityClass : strategy.getEntityClasses()) {
			metadataSources.addAnnotatedClass(entityClass);
		}
		Metadata metadata = metadataSources.getMetadataBuilder().build();
		
		return metadata.getSessionFactoryBuilder().build();
		}catch(IOException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	private static ServiceRegistry confiureServiceRegistry() throws IOException {
		Properties properties = getProperties();
		return new StandardServiceRegistryBuilder().applySettings(properties)
				.build();
	}
	private static Properties getProperties() throws IOException {
		Properties properties = new Properties();
		URL propertiesURL = Thread.currentThread()
				.getContextClassLoader()
				.getResource("mutiplicity/hibernate.properties");
		try(FileInputStream inputStream = new FileInputStream(propertiesURL.getFile())){
			properties.load(inputStream);
		}
		return properties;
	}

}
