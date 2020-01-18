package com.wms.api.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "warehouse_content")
public class WarehouseContent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wc_id")
    private Integer id;

    @Column(name = "wc_warehouse_id")
    private Integer warehouseId;

    @ManyToOne
    @JoinColumn(name = "wc_product_id")
    private Product product;
    @Column(name = "wc_available")
    private int available;
    @Column(name = "wc_reserved")
    private int reserved;

    public WarehouseContent(){}

    public WarehouseContent(int warehouseId, Product product, int available, int reserved) {
        this.warehouseId = warehouseId;
        this.product = product;
        this.available = available;
        this.reserved = reserved;
    }
    /*public boolean checkContent(Product[] products, Integer amount){
        boolean true;

    }*/

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int wc_available) {
        this.available = wc_available;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int wc_reserved) {
        this.reserved = wc_reserved;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}