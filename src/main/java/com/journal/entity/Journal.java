package com.journal.entity;


import com.journal.dto.JournalDto;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Venturedive on 10/29/2017.
 */

@Entity // This tells Hibernate to make a table out of this class
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String fileKey;
    private String publisher;
    private Date date;
    private Integer numberOfViews;
    private String Abstract;
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private File image;
    @JoinColumn(name = "journal_uid", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private File journal;
    private String type;

    public Journal() {
    }

    public Journal(String name, String fileKey, String publisher, Date date, Integer numberOfViews, String anAbstract, File image, File journal, String type) {
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

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public File getJournal() {
        return journal;
    }

    public void setJournal(File journal) {
        this.journal = journal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JournalDto asDto() {
        return new JournalDto(this.id, this.name, this.fileKey, this.publisher, this.date, this.numberOfViews, this.Abstract, this.image.asDto(), this.journal.asDto(), this.type);
    }
}