package org.example.db.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.db.model.Genero;
import org.example.db.model.Personaje;
import org.example.db.util.ConnectionManager;

import java.util.List;

public class PersonajeDaoOrm implements PersonajeDao{
    @Override
    public List<Personaje> findAll() {
        EntityManager em = ConnectionManager.getEntityManager();
        List<Personaje> lista =em.createQuery("SELECT p FROM Personaje p", Personaje.class).getResultList();
        em.close();
        return lista;
    }

    @Override
    public Personaje findById(long id) {
        EntityManager em = ConnectionManager.getEntityManager();
        Personaje pj = em.find(Personaje.class, id);
        em.close();
        return pj;
    }

    @Override
    public Personaje findByName(String nombre) {
        EntityManager em = ConnectionManager.getEntityManager();
        try{
            TypedQuery<Personaje> query= em.createQuery("SELECT p FROM Personaje p WHERE p.nombre = :nombre", Personaje.class);
            query.setParameter("nombre", nombre);
            List<Personaje> pjName= query.getResultList();
            if(pjName.isEmpty()){
                return null;
            }
            return pjName.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public Personaje save(Personaje personaje) {
        EntityManager em = ConnectionManager.getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(personaje);
            em.getTransaction().commit();
            return personaje;
        } catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        }finally {
            em.close();
            System.out.println("Personaje guardado");
        }
    }

    @Override
    public void delete(Personaje personaje) {
        EntityManager em = ConnectionManager.getEntityManager();
        try{
            em.getTransaction().begin();
            Personaje pj = em.find(Personaje.class, personaje.getId());
            if(pj!=null){
                em.remove(pj);
            }
            em.getTransaction().commit();
        } catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    @Override
    public Personaje update(Personaje personaje){
        EntityManager em = ConnectionManager.getEntityManager();
        try {
            em.getTransaction().begin();
            Personaje updatedPersonaje = em.merge(personaje);
            em.getTransaction().commit();
            return updatedPersonaje;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        } finally {
            em.close();
            System.out.println("Personaje actualizado");
        }
    }

    @Override
    public List<Personaje> findByRaza(long razaId) {
        EntityManager em = ConnectionManager.getEntityManager();
        try{
            TypedQuery<Personaje> query= em.createQuery("SELECT p FROM Personaje p JOIN p.razas r WHERE r.id = :razaId", Personaje.class);
            query.setParameter("razaId", razaId);
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public List<Personaje> findByGenero(Genero genero) {
        EntityManager em = ConnectionManager.getEntityManager();
        try{
            TypedQuery<Personaje> query= em.createQuery("SELECT p FROM Personaje p WHERE p.genero = :genero", Personaje.class);
            query.setParameter("genero", genero);
            return query.getResultList();
        }finally {
            em.close();
        }
    }
}
