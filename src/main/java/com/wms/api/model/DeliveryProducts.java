package com.wms.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;



@Entity
@Table(name = "delivery_products")
public class DeliveryProducts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dp_id")
    Integer id;

    @ManyToOne
    @JoinColumn(name = "dp_product_id")
    private Product product;

    @JsonIgnore
    @ManyToOne(optional = true)
    @JoinColumn(name = "dp_delivery_id")
    private Delivery delivery;

    @Column(name = "dp_warehouse_id")
    private Integer warehouseId;

    @Column(name = "dp_quantity")
    private Integer quantity;

    public DeliveryProducts(){}

    public DeliveryProducts(Delivery delivery, Product product, Integer quantity, Integer warehouseId) {
        this.delivery = delivery;
        this.product = product;
        this.quantity = quantity;
        this.warehouseId = warehouseId;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }
}