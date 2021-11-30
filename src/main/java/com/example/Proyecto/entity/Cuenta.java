package com.example.Proyecto.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "Cuentas")
public class Cuenta implements Serializable {

    @Id
    private String cbu;


    private Float saldo;
    private String moneda;
    private String tipoDeCuenta;
    private Float acuerdo;


    @OneToOne(cascade = CascadeType.ALL)
    private TarjetaDebito tarjetaDebito;

    @Override
    public String toString() {
        return "Cuenta{" +
                "cbu='" + cbu + '\'' +
                ", saldo=" + saldo +
                ", moneda='" + moneda + '\'' +
                ", tipoDeCuenta='" + tipoDeCuenta + '\'' +
                ", acuerdo=" + acuerdo +
                '}';
    }
}
