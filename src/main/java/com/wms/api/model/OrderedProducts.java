package com.wms.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ordered_products")
public class OrderedProducts implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "op_id")
    private Integer id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="op_order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name="op_product_id")
    private Product product;

    @Column(name = "op_amount")
    private Integer amount;

    public OrderedProducts(){}

    public OrderedProducts(Order order, Product product, Integer amount) {
        this.order = order;
        this.product = product;
        this.amount = amount;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer op_amount) {
        this.amount = amount;
    }
}