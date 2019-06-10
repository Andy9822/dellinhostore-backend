package engsoft.dellinhostore.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		
		try {
			// Creates the Configuration from hibernate.cfg.xml properties and settings
			 Configuration hibernateConfig = new Configuration().configure("hibernate.cfg.xml");
			 
			 // Set manually some properties from environment variables (suchs as DB username, password, url)
			 hibernateConfig.setProperty("hibernate.connection.url",System.getenv("DATABASE_CUSTOM_URL"));
			 hibernateConfig.setProperty("hibernate.connection.username",System.getenv("DATABASE_USERNAME"));
			 hibernateConfig.setProperty("hibernate.connection.password",System.getenv("DATABASE_PASSWORD"));
			  
			 //Builds SessionFactory
			 return hibernateConfig.buildSessionFactory();
			
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("\nInitial SessionFactory creation failed.\n\nErro:" + ex + "\nFim erro\n\n\n");
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