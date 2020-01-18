package com.wms.api.repository;

import com.wms.api.model.Deliverer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DelivererRepository extends JpaRepository<Deliverer, Integer>, JpaSpecificationExecutor<Deliverer> {
    List<Deliverer> findByWarehouseId(int warehouseId);
    Deliverer findById(Integer id);
    @Override
    List<Deliverer> findAll(Specification<Deliverer> spec);
}