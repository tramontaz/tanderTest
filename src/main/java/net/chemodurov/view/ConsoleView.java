package net.chemodurov.view;

import net.chemodurov.controller.Core;

import net.chemodurov.dao.JdbcTestDAOImpl;

public class ConsoleView implements View{

    public ConsoleView(long n) {
        showResult(n);
    }
    public void showResult(long n){
        Core core = new Core(new JdbcTestDAOImpl()); //change JdbcTestDAOImpl() to HibernateTestDAOImpl() for using Hibernate
        System.out.println("Result: " + core.doAllAndGetResult(n));
    }
}
