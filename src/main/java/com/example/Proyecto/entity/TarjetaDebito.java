package com.example.Proyecto.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name= "TarjetaDebito")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TarjetaDebito {
    @Id
    @Column(name="ID")
    private String numeroTarjeta;

    private String marca;
    private String tipo;
    private String estado;
    private Float limiteDeExtraccion;
    private String vencimiento;
    private boolean bloqueo;
    private boolean metodoPago;


    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "Tarjeta_Movimiento")
    private List<Movimiento> movimientos;


}
