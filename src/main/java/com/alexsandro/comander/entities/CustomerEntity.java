package com.alexsandro.comander.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "customer")
public class CustomerEntity extends BaseEntity {

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "phone", length = 100)
    private String phone;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppointmentEntity> appointments;

}
