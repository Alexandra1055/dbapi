package org.example.db.dao;

import org.example.db.model.Genero;
import org.example.db.model.Personaje;

import java.util.List;

public interface PersonajeDao {
    List<Personaje> findAll();
    Personaje findById(long id);
    Personaje findByName(String nombre);
    Personaje save(Personaje personaje);
    void delete(Personaje personaje);
    Personaje update(Personaje personaje);
    List<Personaje> findByRaza(long razaId);
    List<Personaje> findByGenero(Genero genero);
}
