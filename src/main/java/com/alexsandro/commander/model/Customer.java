package com.alexsandro.commander.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "customer")
public class Customer extends BaseEntity {

    @Column(name = "name", length = 100)
    private String name;

    @ToString.Exclude
    @Column(name = "phone", length = 100)
    private String phone;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

}
