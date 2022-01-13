package com.jsjg73.jpa.defaultvalues;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserRepository {
	
	private EntityManagerFactory emf = null;
	long key = 0l;
	
	public UserRepository() {
		emf = Persistence.createEntityManagerFactory("entity-default-values");
	}

	public void clean() {
		emf.close();
	}
	
	public User find(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        User user = entityManager.find(User.class, id);
        entityManager.close();
        return user;
    }

    public void save(User user, Long id) {
        user.setId(id);

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

	public long newPrimaryKey() {
		key++;
		return key-1;
	}
	
}
