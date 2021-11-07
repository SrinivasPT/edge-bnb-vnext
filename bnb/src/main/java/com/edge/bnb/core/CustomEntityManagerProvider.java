//package com.edge.bnb.core;
//
//import lombok.NoArgsConstructor;
//import org.axonframework.common.jpa.EntityManagerProvider;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//@NoArgsConstructor
//public class CustomEntityManagerProvider implements EntityManagerProvider {
//
//    private EntityManager entityManager;
//
//    @Override
//    public EntityManager getEntityManager() {
//        return entityManager;
//    }
//
//    @PersistenceContext(unitName = "eventStore")
//    public void setEntityManager(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//
//}
