package com.wms.api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Integer id;
    @Column(name="user_warehouse_id", nullable=true)
    private Integer warehouseId;
   // @JsonBackReference
    @ManyToOne
    @JsonManagedReference
    @JsonIgnore
    @JoinColumn(name="user_role_id", insertable = false, updatable = false)
    private Role role;
    @Column(name="user_role_id")
    private Integer roleId;
    @Column(name="user_login")
    private String login;
    @Column(name="user_password")
    @JsonIgnore
    private String password;
    @Transient
    @JsonIgnore
    private String passwordConfirm;
    @Column(name="user_email", nullable=true)
    private String email;
    @Column(name="user_name", nullable=true)
    private String name;
    @Column(name="user_surname", nullable=true)
    private String surname;
    @Column(name="user_salary", nullable=true)
    private Integer salary;
    @Column(name="user_employment_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable=true)
    private Timestamp employmentDate;
    @Column(name="user_position", columnDefinition = "tinyint default 1", nullable=true)
    private Integer position;

    //relations
    @JsonIgnore
    @OneToOne(mappedBy = "owner")
    private Warehouse ownedWarehouse;

    //default constructor for .save() method & API requests
    public User() {}

    // worker/admin creation
    public User(Integer warehouseId, Role role, String login, String password, String passwordConfirm, String email, String name, String surname, Integer salary, Timestamp employmentDate, Integer position) {
        this.warehouseId = warehouseId;
        this.role = role;
        this.login = login;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.employmentDate = employmentDate;
        this.position = position;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    // owner registration
    public User(String name, String surname, String login, String email, String password, String passwordConfirm) {
        this.login = login;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }
    //user edition
    public User(String name, String surname, String login, String email, Integer salary, Integer position, Integer warehouseId, Integer id ) {
        this.login = login;
        this.salary = salary;
        this.position = position;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Timestamp getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Timestamp employmentDate) {
        this.employmentDate = employmentDate;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Warehouse getOwnedWarehouse() {
        return ownedWarehouse;
    }

    public void setOwnedWarehouse(Warehouse ownedWarehouse) {
        this.ownedWarehouse = ownedWarehouse;
    }

}
