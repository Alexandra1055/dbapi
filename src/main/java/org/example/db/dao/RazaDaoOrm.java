package org.example.db.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.db.model.Personaje;
import org.example.db.model.Raza;
import org.example.db.util.ConnectionManager;

import java.util.List;

public class RazaDaoOrm implements RazaDao{
    @Override
    public List<Raza> findAll() {
        EntityManager em = ConnectionManager.getEntityManager();
        List<Raza> lista = em.createQuery("SELECT p FROM Raza p", Raza.class).getResultList();
        em.close();
        return lista;
    }

    @Override
    public Raza findByName(String nombre) {
        EntityManager em = ConnectionManager.getEntityManager();
        try{
            TypedQuery<Raza> query= em.createQuery("SELECT p FROM Raza p WHERE p.nombre = :nombre", Raza.class);
            query.setParameter("nombre", nombre);
            List<Raza> pjName= query.getResultList();
            if(pjName.isEmpty()){
                return null;
            }
            return pjName.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public Raza save(Raza raza) {
        EntityManager em = ConnectionManager.getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(raza);
            em.getTransaction().commit();
            return raza;
        } catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        }finally {
            em.close();
            System.out.println("Raza guardada");
        }
    }

    @Override
    public void delete(Raza raza) {
        EntityManager em = ConnectionManager.getEntityManager();
        try{
            em.getTransaction().begin();
            Raza rz = em.find(Raza.class, raza.getId());
            if(rz!=null){
                em.remove(rz);
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
}
