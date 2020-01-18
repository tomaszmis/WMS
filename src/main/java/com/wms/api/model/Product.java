package com.wms.api.model;


import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Integer id;
    @Column(name = "product_warehouse_id")
    private Integer warehouseId;
    @ManyToOne
    @JoinColumn(name = "product_deliverer_id", insertable=false, updatable=false)
    private Deliverer deliverer;
    @Column(name = "product_deliverer_id") //only for filtering, will be used in SELECT, but not in UPDATE or INSERT
    private Integer delivererId;
    @Column(name = "product_code")
    private String code;
    @Column(name = "product_name")
    private String name;
    @Column(name = "product_brand")
    private String brand;
    @Column(name = "product_category")
    private String category;
    @ManyToOne
    @JoinColumn(name = "product_taxcat_id", insertable=false, updatable=false)
    private Taxcat taxcat;
    @Column(name = "product_taxcat_id") //only for filtering, will be used in SELECT, but not in UPDATE or INSERT
    private Integer taxcatId;
    @Column(name = "product_buy_nett_price")
    private int buyNettPrice;
    @Column(name = "product_sell_nett_price")
    private int sellNettPrice;
    @Column(name = "product_desc", nullable=true)
    private String desc;
    //@Enumerated
    //@Column(name = "product_status", columnDefinition = "tinyint default 1")
   // private ProductStatus status;
    @Column(name = "product_status", insertable=false, updatable=false) //only for filtering, will be used in SELECT, but not in UPDATE or INSERT
    private Integer status;
    @Column(name = "product_unit", nullable=true)
    private String unit;
    @Column(name = "product_optimal_quant")
    private int optimalQuant;
    @Column(name = "product_minimal_quant")
    private int minimalQuant;

    public Product() {}

    public Product(Integer id, Integer warehouseId, Deliverer deliverer, String code, String name, String brand, String category, Taxcat taxcat, int buyNettPrice, int sellNettPrice, String desc, Integer status, String unit, int optimalQuant, int minimalQuant) {
        this.id = id;
        this.warehouseId = warehouseId;
        this.deliverer = deliverer;
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.taxcat = taxcat;
        this.buyNettPrice = buyNettPrice;
        this.sellNettPrice = sellNettPrice;
        this.desc = desc;
        this.status = status;
        this.unit = unit;
        this.optimalQuant = optimalQuant;
        this.minimalQuant = minimalQuant;
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

    public Deliverer getDeliverer() {
        return deliverer;
    }

    public void setDeliverer(Deliverer deliverer) {
        this.deliverer = deliverer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Taxcat getTaxcat() {
        return taxcat;
    }

    public void setTaxcat(Taxcat taxcat) {
        this.taxcat = taxcat;
    }

    public int getBuyNettPrice() {
        return buyNettPrice;
    }

    public void setBuyNettPrice(int buyNettPrice) {
        this.buyNettPrice = buyNettPrice;
    }

    public int getSellNettPrice() {
        return sellNettPrice;
    }

    public void setSellNettPrice(int sellNettPrice) {
        this.sellNettPrice = sellNettPrice;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    /*public status getStatus() {
        return status;
    }

    public void setStatus(status status) {
        this.status = status;
    }*/

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getOptimalQuant() {
        return optimalQuant;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setOptimalQuant(int optimalQuant) {
        this.optimalQuant = optimalQuant;
    }

    public int getMinimalQuant() {
        return minimalQuant;
    }

    public void setMinimalQuant(int minimalQuant) {
        this.minimalQuant = minimalQuant;
    }

    public Integer getDelivererId() {
        return delivererId;
    }

    public void setDelivererId(Integer delivererId) {
        this.delivererId = delivererId;
    }

    public Integer getTaxcatId() {
        return taxcatId;
    }

    public void setTaxcatId(Integer taxcatId) {
        this.taxcatId = taxcatId;
    }
}