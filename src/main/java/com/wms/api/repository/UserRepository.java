package com.wms.api.repository;


import com.wms.api.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByLogin(String login);
    List<User> findByWarehouseId(int warehouseId);


    @Override
    List<User> findAll(Specification<User> spec);
}
