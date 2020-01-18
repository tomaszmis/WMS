package com.wms.api.repository;

import com.wms.api.model.Product;
import com.wms.api.model.Warehouse;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer>, JpaSpecificationExecutor<Warehouse> {
    Warehouse findById(Integer id);
    @Override
    List<Warehouse> findAll(Specification<Warehouse> spec);
}
