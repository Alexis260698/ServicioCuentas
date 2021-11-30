package com.example.Proyecto.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Clientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cliente implements Serializable {

    @Id
    private String usuario;

    private String nombre;
    private String contraseña;
    private String celular;
    private String email;
    private Integer edad;
    private boolean bloqueado;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "Cliente_Cuentas")
    List<Cuenta> cuentas;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "Cliente_Prestamos")
    List<Prestamo> prestamos;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "Cliente_PlazoFijo")
    private List<PlazoFijo> plazoFijoLista;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "Usuario_Tarjetas")
    private List<TarjetaDebito> tarjetas;

    @Override
    public String toString() {
        return "Cliente{" +
                "usuario='" + usuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", celular='" + celular + '\'' +
                ", email='" + email + '\'' +
                ", edad=" + edad +
                ", bloqueado=" + bloqueado +
                ", cuentas=" + cuentas +
                ", prestamos=" + prestamos +
                ", plazoFijoLista=" + plazoFijoLista +
                '}';
    }
}
