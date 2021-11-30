package com.example.Proyecto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transferencia extends Movimiento{
    private String CuentaDestino;
    private String CuentaOrigen;
    private String cuitOrigen;
    private String cuitDestino;

}