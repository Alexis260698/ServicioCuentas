package com.example.Proyecto.repository;

import com.example.Proyecto.entity.Cliente;
import com.example.Proyecto.entity.Cuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class CuentaRepository{

    @Autowired
    CuentaRepositoryDao cuentaRepositoryDao;
    @Autowired
    RestTemplate restTemplate;

    public Cuenta creacionCuentaAhorro(String usuario,Cuenta cuenta) {
        String cadena=usuario+"Ah"+numeroRandom();
        cuenta.setTipoDeCuenta("Ahorro");
        cuenta.setCbu(cadena);
        cuentaRepositoryDao.save(cuenta);
        return cuenta;
    }

    public Cuenta creacionCuentaCorriente(String usuario,Cuenta cuenta) {
        String cadena=usuario+"Co"+numeroRandom();
        cuenta.setTipoDeCuenta("Corriente");
        cuenta.setAcuerdo((float) 3000);
        cuenta.setCbu(cadena);
        cuentaRepositoryDao.save(cuenta);
        return cuenta;
    }


    public Integer numeroRandom(){
        Random r = new Random();
        return r.nextInt(10000)+1;
    }

    private List<Cuenta> listaCuenta = new ArrayList<>();

    public Optional<Cuenta> getbyCbu(String cbu){
        return cuentaRepositoryDao.findById(cbu);
    }

    public List<Cuenta> getCuentas(Cliente cliente) {
        List<Cuenta> lista = new ArrayList<>();
        for(Cuenta c: cliente.getCuentas()){
            lista.add(c);
        }
        return lista;
    }

    public boolean actualizarCbu(Cliente cliente,String cbuViejo,String cbuNuevo){
        for(Cuenta c:cliente.getCuentas()){
            if(c.getCbu().equals(cbuViejo)){
                c.setCbu(cbuNuevo);
                enviarDatosCliente(cliente);
                return true;
            }
        }
        return false;
    }

    public List<String> consultarCbu(Cliente cliente, String tipoCuenta){
        List<String>cbus= new ArrayList<>();

        for(Cuenta c: cliente.getCuentas()){
            if(c.getTipoDeCuenta().equalsIgnoreCase(tipoCuenta)){
                cbus.add(c.getCbu());
            }
        }
        return cbus;
    }


    public void enviarDatosCliente(Cliente cliente) {
        restTemplate.put("http://localhost:8080/actualizarCliente", cliente);
    }

}
