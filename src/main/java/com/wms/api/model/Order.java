package com.wms.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "client_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Integer id;
    @Column(name = "order_warehouse_id")
    private Integer warehouseId;
    @ManyToOne
    @JoinColumn(name = "order_client_id", insertable=false, updatable=false)
    private Client client;
    @Column(name = "order_client_id") //only for filtering, will be used in SELECT, but not in UPDATE or INSERT
    private Integer clientId;
    @Column(name = "order_number")
    private String number;
    @Column(name = "order_created_at", insertable=false, updatable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
    @Column(name = "order_annotation", nullable=true)
    private String annotation;
    @Column(name = "order_status", columnDefinition = "tinyint default 1")
    private Integer status;

    @Transient
    private Integer[] productIds;
    @Transient
    private Integer[] productAmounts;

    // relations
    //@JsonIgnore
    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<OrderedProducts> orderedProducts;

    public Order(){}

    public Order(Integer warehouseId, Integer clientId, String number, String annotation, Integer status, Integer[] productIds, Integer[] productAmounts) {
        this.warehouseId = warehouseId;
        this.clientId = clientId;
        this.number = number;
        this.annotation = annotation;
        this.status = status;
        this.productIds = productIds;
        this.productAmounts = productAmounts;

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer order_status) {
        this.status = order_status;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public List<OrderedProducts> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProducts> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public Integer[] getProductIds() {
        return productIds;
    }

    public void setProductIds(Integer[] productIds) {
        this.productIds = productIds;
    }

    public Integer[] getProductAmounts() {
        return productAmounts;
    }

    public void setProductAmounts(Integer[] productAmounts) {
        this.productAmounts = productAmounts;
    }
}