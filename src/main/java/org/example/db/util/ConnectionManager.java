package org.example.db.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConnectionManager {
    private static EntityManagerFactory emf;

    public static EntityManager getEntityManager() {
        if (emf == null) {
            synchronized (ConnectionManager.class) {
                if (emf == null) {
                    emf = Persistence.createEntityManagerFactory("dragonball");
                }
            }
        }
        return emf.createEntityManager();
    }
}
