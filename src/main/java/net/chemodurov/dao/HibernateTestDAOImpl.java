package net.chemodurov.dao;

import net.chemodurov.model.Test;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HibernateTestDAOImpl implements TestDAO {
    private SessionFactory sessionFactory;
    private Session session;
    private static Logger logger = Logger.getLogger(HibernateTestDAOImpl.class.getName());

    public HibernateTestDAOImpl() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public Long getNumberOfRows() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT COUNT (id) FROM Test");
        Long numberOfRows = (Long) query.getSingleResult();
        session.close();
        logger.log(Level.INFO,"Number of field requested. There is: " + numberOfRows);
        return numberOfRows;

    }

    public void addManyValues(String values) {
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery("INSERT INTO TEST (FIELD) VALUES "+ values + ";");
        query.executeUpdate();
        transaction.commit();
        logger.log(Level.INFO, "Test Table deleted");
        session.close();

    }

    public void deleteTable() {
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery("DROP TABLE IF EXISTS TEST;");
        query.executeUpdate();
        transaction.commit();
        logger.log(Level.INFO, "Test Table deleted");
        session.close();
    }

    public void createTable() {
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery("CREATE TABLE TEST (FIELD int(11) primary key);");
        query.executeUpdate();
        transaction.commit();
        logger.log(Level.INFO, "Test Table created");
        session.close();
    }

    public ArrayList<Test> getAllField() {
        ArrayList<Test> tests;
        session = sessionFactory.openSession();
        tests = new ArrayList<Test>(session.createQuery("FROM Test ").list());
        session.close();
        return tests;
    }
}
