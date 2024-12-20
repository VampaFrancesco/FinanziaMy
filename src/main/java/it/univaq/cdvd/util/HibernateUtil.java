package it.univaq.cdvd.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;


    static {
        try {
            sessionFactory = new Configuration()
                    .configure("/config/hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            sessionFactory = new Configuration().configure("/config/hibernate.cfg.xml").buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void setSessionFactory(SessionFactory factory) {
        sessionFactory = factory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

}
