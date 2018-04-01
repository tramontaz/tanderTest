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
    public static void main(String[] args) {
        long start = System.nanoTime();
        new ConsoleView(Long.parseLong(args[0]));
        long stop = System.nanoTime() - start;
        System.out.println("Execution time of the program: " + stop / 1000000000 + " sec.");
    }
}