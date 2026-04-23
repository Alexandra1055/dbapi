package org.example.db.service;

import com.google.gson.JsonObject;
import org.example.db.dao.PersonajeDao;
import org.example.db.dao.PersonajeDaoOrm;
import org.example.db.dao.RazaDao;
import org.example.db.dao.RazaDaoOrm;
import org.example.db.model.Genero;
import org.example.db.model.Personaje;
import org.example.db.model.Raza;
import org.example.db.util.ApiProvider;

import java.net.URI;
import java.util.List;

public class PersonajeServImpl implements PersonajeServ{
    private static final URI BASE_URI = URI.create("https://dragonball-api.com/api/characters/");
    PersonajeDao personajeDao = new PersonajeDaoOrm();
    RazaDao razaDao = new RazaDaoOrm();

    @Override
    public void savePersonaje(long id) {
        try{
            String response = ApiProvider.requestApi(BASE_URI.toString() + id);
            JsonObject json = ApiProvider.parseToJson(response);

            Personaje pj = new Personaje();
            pj.setNombre(json.get("name").getAsString());
            pj.setKi(json.get("ki").getAsInt());
            pj.setMaxKi(json.get("maxKi").getAsString());
            pj.setGenero(Genero.toApi(json.get("gender").getAsString()));
            pj.setImagen(json.get("image").getAsString());
            pj.setDescripcion(json.get("description").getAsString());
            pj.setAfiliacion(json.get("affiliation").getAsString());
            String nombreRaza = json.get("race").getAsString();
            Raza raza = razaDao.findByName(nombreRaza);
            if(raza==null){
                raza = new Raza();
                raza.setNombre(nombreRaza);
                razaDao.save(raza);
            }
            pj.getRazas().add(raza);
            personajeDao.save(pj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePersonaje(long id){
        Personaje pj = personajeDao.findById(id);
        if(pj==null){
            System.out.println("El personaje no existe");
            return;
        }
        personajeDao.delete(pj);
    }

    @Override
    public List<Personaje> findAll() {
        return personajeDao.findAll();
    }

    @Override
    public Personaje findById(long id) {
        return personajeDao.findById(id);
    }

    @Override
    public List<Personaje> findMestizos(Raza raza) {
        List<Personaje> pj = personajeDao.findByRaza(raza.getId());
        return pj.stream().filter(p -> p.getRazas() != null && p.getRazas().size() > 1).toList();
    }

    @Override
    public List<Personaje> findFemeninos(Genero genero) {
       List<Personaje> pj = personajeDao.findByGenero(genero);
       return pj.stream().filter(p -> p.getGenero() == Genero.FEMENINO).toList();
    }
}
