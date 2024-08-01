package com.example;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import io.github.cdimascio.dotenv.Dotenv; 

public class HibernateConfig {

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Dotenv dotenv = Dotenv.load();

				Configuration configuration = new Configuration();

				// Configuration des propriétés Hibernate
				Properties settings = new Properties();
				settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
				settings.put("hibernate.connection.url",
						dotenv.get("jdbc:mysql://localhost:3309/library"));
				settings.put("hibernate.connection.username", dotenv.get("root"));
				settings.put("hibernate.connection.password", dotenv.get(""));
				settings.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
				settings.put("hibernate.show_sql", "true");
				settings.put("hibernate.hbm2ddl.auto", "update");

				configuration.setProperties(settings);

				StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();

				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (Exception e) {
				e.printStackTrace();
				if (sessionFactory != null) {
					sessionFactory.close();
				}
			}
		}
		return sessionFactory;
	}
}
