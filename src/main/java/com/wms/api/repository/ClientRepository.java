package com.wms.api.repository;

import com.wms.api.model.Client;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer>, JpaSpecificationExecutor<Client> {
    List<Client> findByWarehouseId(int warehouseId);
    Client findById(Integer id);
    Client findByName(String name);
    @Override
    List<Client> findAll(Specification<Client> spec);

}