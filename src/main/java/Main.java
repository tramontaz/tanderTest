import net.chemodurov.dao.JdbcTestDAOImpl;
import net.chemodurov.dao.TestDAO;
import net.chemodurov.model.Test;
import net.chemodurov.view.ConsoleView;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Main {
    private static Logger log = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        long start = System.nanoTime();
        new ConsoleView(Long.parseLong(args[0]));
        long stop = System.nanoTime() - start;
        System.out.println("Execution time of the program: " + stop/1000000000 + " sec.");
    }

    private static void hibernateTest() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Test ");
        ArrayList<Test> fields = new ArrayList<Test>(query.list());
        session.close();
        for (Object field : fields) {
            System.out.println(field);
        }
        System.exit(0);
    }

    private static void jdbcTest(){
        TestDAO testDAO = new JdbcTestDAOImpl();

//        //getAll
//        ArrayList<Test> tests = testDAO.getAllField();
//        for (Test t : tests) {
//            System.out.println(t.toString());
//        }

        //dropTable
//        testDAO.deleteTable();
        //create table
//        testDAO.createTable();

    }

}