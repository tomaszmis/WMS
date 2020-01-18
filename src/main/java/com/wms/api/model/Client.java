package com.wms.api.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "client_id")
    private Integer id;
    @Column(name = "client_warehouse_id")
    private Integer warehouseId;
    @Column(name = "client_nip", nullable=true)
    private String nip;
    @Column(name = "client_name", nullable=true)
    private String name;
    @Column(name = "client_address", nullable=true)
    private String address;
    @Column(name = "client_phone", nullable=true)
    private String phone;
    @Column(name = "client_email", nullable=true)
    private String email;
    @Column(name = "client_contact_person", nullable=true)
    private String contactPerson;

    public Client(){}

    public Client(Integer warehouseId, String nip, String name, String address, String phone, String email, String contactPerson) {
        this.warehouseId = warehouseId;
        this.nip = nip;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.contactPerson = contactPerson;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}