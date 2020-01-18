package com.wms.api.repository;

import com.wms.api.model.Delivery;
import com.wms.api.model.DeliveryProducts;
import com.wms.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  DeliveryProductsRepository extends JpaRepository<DeliveryProducts, Integer> {
    DeliveryProducts findByProduct(Product product);
    List<DeliveryProducts> findByDelivery(Delivery delivery);
    List<DeliveryProducts> findByWarehouseIdAndDelivery(Integer id, Delivery delivery);
}
