package com.journal.entity;


import com.journal.dto.FileDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Venturedive on 10/29/2017.
 */

@Entity // This tells Hibernate to make a table out of this class
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String fileName;
    private String fileKey;
    private String type;

    public File() {
    }

    public File(Integer id, String fileName, String fileKey, String type) {
        this.id = id;
        this.fileName = fileName;
        this.fileKey = fileKey;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FileDto asDto() {
        return new FileDto(this.id, this.fileName, this.fileKey, this.type);
    }
}