package com.example.Proyecto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Cuota")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cuota {

    @Id
    @Column(name="ID")
    private Integer id;

    private Float valorCuota;
    private String proximaCuota;
    private Float importeMaximoCuota;

}
