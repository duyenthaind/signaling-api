package org.example.signalingapi.repository.custom;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

/**
 * @author duyenthai
 */
@Log4j2
@Repository
public class CustomContactRepositoryImpl implements CustomContactRepository{

    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<String> getAllContactIdOfUser(String ofUser, int from, int maxResult) {
        try{
            Query query = entityManager.createQuery("select contactId from ContactEntity where creator = :creator")
                    .setParameter("creator", ofUser)
                    .setFirstResult(from)
                    .setMaxResults(maxResult);
            return query.getResultList();
        } catch (Exception ex){
            log.error("Error when get list all contact id");
        }
        return Collections.emptyList();
    }
}
