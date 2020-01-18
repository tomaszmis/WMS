package com.wms.api.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "taxcat")
public class Taxcat{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="taxcat_id")
    private Integer id;
    @Column(name ="taxcat_name")
    private String name;
    @Column(name ="taxcat_tax")
    private int tax;

    public Taxcat(){}

    public Taxcat(String name, int tax) {
        this.name = name;
        this.tax = tax;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }
}