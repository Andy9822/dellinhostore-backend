package engsoft.dellinhostore.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		
		// Creates the Configuration from hibernate.cfg.xml properties and settings
		 Configuration hibernateConfig = new Configuration().configure("hibernate.cfg.xml");
		 hibernateConfig.setProperty("hibernate.connection.url",System.getenv("DATABASE_CUSTOM_URL"));
		 hibernateConfig.setProperty("hibernate.connection.username",System.getenv("DATABASE_USERNAME"));
		 hibernateConfig.setProperty("hibernate.connection.password",System.getenv("DATABASE_PASSWORD"));
		  
		 //Creates ServiceRegistry and applies loaded settings
		 ServiceRegistry  serviceRegistry  = new StandardServiceRegistryBuilder()
				 .applySettings(hibernateConfig.getProperties())
				 .build();
		try {
			return hibernateConfig.buildSessionFactory(serviceRegistry);
			
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("\nInitial SessionFactory creation failed.\n" + ex + "\n");
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
}