package com.example.keanuppgift2v4.model;

import jakarta.persistence.*;

@Entity
@Table(name = "messageusers")
public class AccountData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private Integer age;

    private String address;


    @Column(name = "password", nullable = false)
    private String passwordHash;



    public Long getId() {
        return id;
    }
    public void setId(Long val) {
        this.id = val;
    }

    public String getName() {
        return name;
    }
    public void setName(String val) {
        this.name = val;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String val) {
        this.email = val;
    }

    public Integer getAge() {
        return age;
    }
    public void setAge(Integer val) {
        this.age = val;
    }

    public String getAddress() {
        return address;
    }
    public void setadress(String val) {
        this.address = val;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String val) {
        this.passwordHash = val;
    }
}
