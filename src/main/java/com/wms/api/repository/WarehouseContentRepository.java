package com.wms.api.repository;

import com.wms.api.model.Product;
import com.wms.api.model.WarehouseContent;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WarehouseContentRepository extends JpaRepository<WarehouseContent, Integer>, JpaSpecificationExecutor<WarehouseContent> {
    List<WarehouseContent> findByWarehouseId(Integer id);
    WarehouseContent findByProduct(Product product);
    @Override
    List<WarehouseContent> findAll(Specification<WarehouseContent> spec);
}
