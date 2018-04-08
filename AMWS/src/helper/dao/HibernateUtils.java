package helper.dao;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.*;
import org.hibernate.service.*;

public class HibernateUtils {
	private static SessionFactory factory;
	private static Configuration config;
	private static ServiceRegistry sr;

//	//private static final String filename = "file/mysql/hibernate.cfg.xml";
	private static final String filename = "hibernate.cfg.xml";


	static {
		try {
//			  factory = new Configuration()
//	            .configure() // configures settings from hibernate.cfg.xml
//	            .buildSessionFactory();
			System.out.println("using static constructor");
			config = new Configuration().configure(filename);
			sr = new StandardServiceRegistryBuilder().applySettings(
					config.getProperties()).build();
			factory = config.buildSessionFactory(sr);
//			Iterator<PersistentClass> mappingClasses = config
//					.getClassMappings();
//			while (mappingClasses.hasNext()) {
//				PersistentClass clazz = (PersistentClass) mappingClasses.next();
//				System.out.println(clazz.getEntityName());
//				clazz.setDynamicInsert(true);
//				clazz.setDynamicUpdate(true);
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static SessionFactory getSessionFactory() {
		if (factory == null || factory.isClosed()) {
			try {
				config = new Configuration().configure(filename);
				sr = new StandardServiceRegistryBuilder().applySettings(
						config.getProperties()).build();
				factory = config.buildSessionFactory(sr);
				System.out.println("rebuilding factory");
				config.buildMappings();
//				Iterator<PersistentClass> mappingClasses = config
//						.getClassMappings();
//				while (mappingClasses.hasNext()) {
//					PersistentClass clazz = (PersistentClass) mappingClasses
//							.next();
//					System.out.println(clazz.getEntityName());
//					clazz.setDynamicInsert(true);
//					clazz.setDynamicUpdate(true);
//				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return factory;
	}

	public static Session getSession() {
		return getSessionFactory().openSession();
	}

	public static void closeFactory() {
		if (factory != null && !factory.isClosed())
			factory.close();
	}

	public static void closeSession(Session s) {
		s.close();
		// closeFactory();
	}

	private HibernateUtils() {

	}
	public static Session getCurrentSession(){
		return getSessionFactory().getCurrentSession();
	}

}
