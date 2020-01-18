package com.wms.api.repository;

import com.wms.api.model.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    List<Order> findByWarehouseId(int warehouseId);
    Order findById(Integer id);
    @Override
    List<Order> findAll(Specification<Order> spec);
}