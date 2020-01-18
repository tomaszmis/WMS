package com.wms.api.repository;

import com.wms.api.model.Order;
import com.wms.api.model.OrderedProducts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderedProductsRepository extends JpaRepository<OrderedProducts, Integer> {
    List<OrderedProducts> findByOrder(Order order);
}
