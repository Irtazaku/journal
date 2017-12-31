package com.journal.entity;

import com.journal.dto.JournalDto;

import java.util.List;

public interface JournalManager {

    Journal getJournalById(Integer journalId);


    Journal merge(Journal journal);

    Journal persist(Journal journal);

    List<JournalDto> getRecentJournals(String queryString);
}
