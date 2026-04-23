package org.example.db.dao;

import org.example.db.model.Raza;

import java.util.List;

public interface RazaDao {
    List<Raza> findAll();
    Raza findByName(String nombre);
    Raza save(Raza raza);
    void delete(Raza raza);
}
