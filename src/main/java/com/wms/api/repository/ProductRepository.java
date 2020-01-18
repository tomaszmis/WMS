package com.wms.api.repository;

import com.wms.api.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.method.P;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    List<Product> findByWarehouseId(Integer warehouseId);
    List<Product> findByWarehouseIdAndStatus(Integer warehouseId, Integer status);
    Product findById(Integer id);

    @Override
    List<Product> findAll(Specification<Product> spec);
}