package com.example.Proyecto.controller;

import com.example.Proyecto.configuration.ModelConfiguration;
import com.example.Proyecto.dto.CuentaDto;
import com.example.Proyecto.entity.Cliente;
import com.example.Proyecto.entity.Cuenta;
import com.example.Proyecto.repository.CuentaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
public class CuentaController {

    @Autowired
    CuentaRepository cuentaRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/agregarCuenta/{usuario}")
    public ResponseEntity<CuentaDto> addCuenta(@PathVariable("usuario") String usuario, @RequestBody Cuenta cuenta) {

        if (!islogged(usuario)) {
            return new ResponseEntity("Primero debes Iniciar sesion", HttpStatus.BAD_REQUEST);
        }


        Cliente cliente = consumirCliente(usuario);

        if (cliente.getUsuario() != null) {
            CuentaDto cuentaDto = modelMapper.map(cuenta, CuentaDto.class);

            if (cuenta.getTipoDeCuenta().equalsIgnoreCase("ahorro")) {
                Cuenta agregarCuenta = cuentaRepository.creacionCuentaAhorro(usuario, cuenta);
                ResponseEntity<Cuenta> clienteResponse = restTemplate.postForEntity("http://localhost:8080/agregarCuenta/" + usuario, cuenta, Cuenta.class);

            } else {
                Cuenta agregarCuenta = cuentaRepository.creacionCuentaCorriente(usuario, cuenta);
                ResponseEntity<Cuenta> clienteResponse = restTemplate.postForEntity("http://localhost:8080/agregarCuenta/" + usuario, cuenta, Cuenta.class);
            }
            return ResponseEntity.ok(cuentaDto);

        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public Cliente consumirCliente(String usuario) {
        try {
            Cliente cliente = restTemplate.getForObject("http://localhost:8080/buscarCliente/" + usuario, Cliente.class);
            return cliente;
        } catch (Exception e) {
            return new Cliente();
        }
    }


    @GetMapping("/ListarCuentas/{usuario}")
    public ResponseEntity<List<Cuenta>> getListaCuentas(@PathVariable("usuario") String usuario) {

        if (!islogged(usuario)) {
            return new ResponseEntity("Primero debes Iniciar sesion", HttpStatus.BAD_REQUEST);
        }

        Cliente cliente = consumirCliente(usuario);

        if (cliente.getUsuario() != null) {
            List<Cuenta> lista = cuentaRepository.getCuentas(cliente);

            if (lista.size() < 1) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST + ": El cliente no contiene cuentas", HttpStatus.BAD_REQUEST);
            } else {
                return ResponseEntity.ok(lista);
            }

        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/porCbu/{cbu}")
    public ResponseEntity<Cuenta> getCuentaPorCbu(@PathVariable("cbu") String cbu) {
        Optional<Cuenta> cuenta = cuentaRepository.getbyCbu(cbu);
        if (cuenta.isEmpty()) {
            return ResponseEntity.notFound().build();

        } else {
            return ResponseEntity.ok(cuenta.get());
        }
    }

    @PutMapping("/modificacionCbu/{usuario}/{cbuViejo}/{cbuNuevo}")
    public ResponseEntity<String> cambioDeCbu(@PathVariable("usuario") String usuario,
                              @PathVariable("cbuViejo") String cbuViejo,
                              @PathVariable("cbuNuevo") String cbuNuevo) {

        if (!islogged(usuario)) {
            return new ResponseEntity("Primero debes Iniciar sesion", HttpStatus.BAD_REQUEST);
        }

        Cliente cliente = consumirCliente(usuario);

        if (cliente.getUsuario() != null) {
            if(cuentaRepository.actualizarCbu(cliente,cbuViejo,cbuNuevo)){
                return new ResponseEntity("Cambio realizado con exito", HttpStatus.OK);
            }
            return new ResponseEntity("Solicitud rechazada", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Cliente no encontrado", HttpStatus.NOT_FOUND);
    }


    @GetMapping("consultarCbu/{usuario}/{tipoCuenta}")
    public ResponseEntity<List<String>> consultarCbu(@PathVariable("usuario") String usuario,
                                     @PathVariable("tipoCuenta") String tipoCuenta){
        if (!islogged(usuario)) {
            return new ResponseEntity("Primero debes Iniciar sesion", HttpStatus.BAD_REQUEST);
        }

        Cliente cliente = consumirCliente(usuario);

        if (cliente.getUsuario() != null) {
            List<String> listaCbus=cuentaRepository.consultarCbu(cliente, tipoCuenta);
            if(listaCbus.size()>0){
                return ResponseEntity.ok(listaCbus);
            }
            return new ResponseEntity("No se encontraron Cbus", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity("Cliente no encontrado", HttpStatus.NOT_FOUND);

    }

    public boolean islogged(String usuario) {
        Boolean isLogged = restTemplate.getForObject("http://localhost:8080/islogged/" + usuario, Boolean.class);
        System.out.println(isLogged.booleanValue());
        return isLogged;
    }


}
