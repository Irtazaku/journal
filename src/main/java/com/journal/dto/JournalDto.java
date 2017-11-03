package com.journal.dto;


import com.journal.entity.Journal;

import java.util.Date;

/**
 * Created by Venturedive on 10/29/2017.
 */

public class JournalDto {
    private Integer id;
    private String name;
    private String fileKey;
    private String publisher;
    private Date date;
    private Integer numberOfViews;
    private String Abstract;
    private FileDto image;
    private FileDto journal;
    private String type;

    public JournalDto() {
    }

    public JournalDto(Integer id, String name, String fileKey, String publisher, Date date, Integer numberOfViews, String anAbstract, FileDto image, FileDto journal, String type) {
        this.id = id;
        this.name = name;
        this.fileKey = fileKey;
        this.publisher = publisher;
        this.date = date;
        this.numberOfViews = numberOfViews;
        Abstract = anAbstract;
        this.image = image;
        this.journal = journal;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(Integer numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public String getAbstract() {
        return Abstract;
    }

    public void setAbstract(String anAbstract) {
        Abstract = anAbstract;
    }

    public FileDto getImage() {
        return image;
    }

    public void setImage(FileDto image) {
        this.image = image;
    }

    public FileDto getJournal() {
        return journal;
    }

    public void setJournal(FileDto journal) {
        this.journal = journal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Journal asEntity() {
        return new Journal( this.name, this.fileKey, this.publisher, this.date, this.numberOfViews, this.Abstract, this.image.asEntity(), this.journal.asEntity(), this.type);
    }
}