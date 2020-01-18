package com.wms.api.repository;

import com.wms.api.model.Taxcat;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaxcatRepository extends JpaRepository<Taxcat, Integer>, JpaSpecificationExecutor<Taxcat> {

    Taxcat findById(Integer id);
    @Override
    List<Taxcat> findAll(Specification<Taxcat> spec);
}