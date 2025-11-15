package com.alexsandro.commander.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter @Setter
@Table(name = "appointment")
public class Appointment extends BaseEntity {

    @Column(name = "appointment_date_time")
    private LocalDateTime appointmentDateTime;

    @Column(name = "appointment_opened")
    private boolean appointmentOpened;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "beauty_procedure_id", nullable = false)
    private BeautyProcedure beautyProcedure;

}
