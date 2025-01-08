package it.univaq.cdvd.utilTest;


import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.FileReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;


public class HibernateUtilTest {

    private static SessionFactory sessionFactory;
    private Session session;


        static {
            try {
                sessionFactory = new Configuration()
                        .configure("/hibernate-test.cfg.xml")
                        .buildSessionFactory();
                Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "admin", "admin");
                RunScript.execute(connection, new StringReader("DROP ALL OBJECTS;"));
                RunScript.execute(connection, new FileReader("src/test/resources/finanziamy.sql"));

            } catch (Throwable ex) {
                throw new ExceptionInInitializerError(ex);
            }
        }

        public static SessionFactory getSessionFactory() {
            if (sessionFactory == null || sessionFactory.isClosed()) {
                sessionFactory = new Configuration().configure("/hibernate-test.cfg.xml").buildSessionFactory();
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

