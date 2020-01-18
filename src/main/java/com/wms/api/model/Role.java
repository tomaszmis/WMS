package com.wms.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ur_id")
    private Integer id;
    @Column(name="ur_name", nullable=true)
    private String name;
    @JsonBackReference
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<User> users;

    public Role(){}

    public Role(Integer id, String name, Set<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
