package org.example.db.service;

import org.example.db.model.Genero;
import org.example.db.model.Personaje;
import org.example.db.model.Raza;

import java.util.List;

public interface PersonajeServ {
    void savePersonaje(long id);
    void deletePersonaje(long id);
    List<Personaje> findAll();
    Personaje findById(long id);
    List<Personaje> findMestizos(Raza raza);
    List<Personaje> findFemeninos(Genero genero);
}
