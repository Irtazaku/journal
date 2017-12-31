package com.journal.entity;


import com.journal.dto.JournalDto;
import com.journal.util.EntityHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class JournalManagerImpl implements JournalManager {

    public static final Logger LOGGER= Logger.getLogger(JournalManagerImpl.class);

    @PersistenceContext()
    private EntityManager entityManager;

    @Autowired
    private JournalRepository journalRepository;

    @Override
    public Journal getJournalById(Integer journalId) {
        TypedQuery<Journal> query = entityManager
                .createNamedQuery("journal.getJournalById", Journal.class)
                .setParameter("journalId", journalId);

        List<Journal> journals = query.getResultList();
        return !journals.isEmpty() ? journals.get(0) : null;
    }

    @Override
    public List<JournalDto> getRecentJournals() {
        TypedQuery<Journal> query = entityManager
                .createNamedQuery("journal.getRecentJournals", Journal.class);
        List<Journal> journals = query.getResultList();
        List<JournalDto> journalDtos= new ArrayList<>();
        for(Journal journal: EntityHelper.safeList(journals)){
            journalDtos.add(journal.asDto());
        }
        return journalDtos;
    }

    @Override
    public Journal merge(Journal journal) {
        return entityManager.merge(journal);
    }

    @Override
    public Journal persist(Journal journal) {
        return journalRepository.save(journal);
    }


}
