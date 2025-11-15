package com.alexsandro.commander.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "beauty_procedure")
public class BeautyProcedure extends BaseEntity {

    @Column(length = 100)
    private String name;

    @Column(length =250)
    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @JsonIgnore
    @OneToMany(mappedBy = "beautyProcedure", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointment;

}
