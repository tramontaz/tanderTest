package net.chemodurov.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "TEST")
@XmlRootElement(name = "entry")
public class Test {

    private int field;

    public Test() {
    }

    public Test(int field) {
        this.field = field;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FIELD")
    @XmlElement(name = "field")
    public int getField() {
        return field;
    }

    public void setField(int value) {
        this.field = value;
    }

    @Override
    public String toString() {
        return "Field = " + field;
    }
}
