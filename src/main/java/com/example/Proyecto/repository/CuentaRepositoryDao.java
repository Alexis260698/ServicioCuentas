package com.example.Proyecto.repository;

import com.example.Proyecto.entity.Cuenta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
public interface CuentaRepositoryDao  extends CrudRepository<Cuenta, String> {

}
