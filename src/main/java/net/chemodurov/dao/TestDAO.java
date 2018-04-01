package net.chemodurov.dao;

import net.chemodurov.model.Test;

import java.util.ArrayList;

public interface TestDAO {
    Long getNumberOfRows();
    void addManyValues(String values);
    void deleteTable();
    void createTable();
    ArrayList<Test> getAllField();


}
