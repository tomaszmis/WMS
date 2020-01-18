package com.wms.api.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "warehouse")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="warehouse_id")
    private Integer id;
    @Column(name="warehouse_name")
    private String name;
    @OneToOne
    @JoinColumn(unique = true, name="warehouse_owner_id")
    private User owner;
    @Column(unique = true, name="warehouse_owner_id", insertable = false, updatable = false)
    private Integer ownerId;
    @Column(name="warehouse_created_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name="warehouse_status", columnDefinition = "tinyint default 1")
    private Integer status;
    @Column(name="warehouse_description", nullable=true)
    private String description;

    @Column(name="warehouse_payment_status", columnDefinition = "tinyint default 1")
    private Integer paymentStatus;

    public Warehouse() {}

    public Warehouse(Integer id, String name, User owner, Timestamp createdAt, Integer status, String description, Integer paymentStatus) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.createdAt = createdAt;
        this.status = status;
        this.description = description;
        this.paymentStatus = paymentStatus;
    }

    public Warehouse(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.paymentStatus = paymentStatus;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
}