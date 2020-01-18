package com.wms.api.repository;

import com.wms.api.model.Delivery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer>, JpaSpecificationExecutor<Delivery> {
    List<Delivery> findByWarehouseId(int warehouseId);
    Delivery findById(Integer id);
    @Override
    List<Delivery> findAll(Specification<Delivery> spec);
}