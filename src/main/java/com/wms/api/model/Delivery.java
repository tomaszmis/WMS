package com.wms.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "delivery_id")
    private Integer id;
    @Column(name = "delivery_warehouse_id")
    private Integer warehouseId;
    @ManyToOne
    @JoinColumn(name = "delivery_deliverer_id", insertable=false, updatable=false)
    private Deliverer deliverer;
    @Column(name = "delivery_deliverer_id")
    private Integer delivererId;
    @Column(name = "delivery_number")
    private String number;
    @Column(name = "delivery_date", insertable=false, updatable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp date;
    @Column(name = "delivery_status", columnDefinition = "tinyint default 1")
    private Integer status;

    @Transient
    private Integer[] productIds;

    // relations
    //@JsonIgnore
    @JsonIgnore
    @OneToMany(mappedBy = "delivery", fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private List<DeliveryProducts> deliveryProducts;

    public Delivery() {}

    public Delivery(Integer warehouseId, Integer delivererId, String number, Integer[] productIds) {
        this.warehouseId = warehouseId;
        this.delivererId = delivererId;
        this.number = number;
        this.productIds = productIds;
    }

    public Integer[] getProductIds() {
        return productIds;
    }

    public void setProductIds(Integer[] productIds) {
        this.productIds = productIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWarehouse() {
        return warehouseId;
    }

    public void setWarehouse(Integer warehouse) {
        this.warehouseId = warehouse;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<DeliveryProducts> getDeliveryProducts() {
        return deliveryProducts;
    }

    public void setDeliveryProducts(List<DeliveryProducts> deliveryProducts) {
        this.deliveryProducts = deliveryProducts;
    }

    public Integer getDelivererId() {
        return delivererId;
    }

    public void setDelivererId(Integer delivererId) {
        this.delivererId = delivererId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Deliverer getDeliverer() {
        return deliverer;
    }

    public void setDeliverer(Deliverer deliverer) {
        this.deliverer = deliverer;
    }
}