package com.journal.dto;


import com.journal.entity.File;

/**
 * Created by Venturedive on 10/29/2017.
 */
public class FileDto {
    private Integer id;
    private String fileName;
    private String fileKey;
    private String type;

    public FileDto() {
    }

    public FileDto(Integer id, String fileName, String fileKey, String type) {
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

    public File asEntity() {
        return new File(this.id, this.fileName, this.fileKey, this.type);
    }
}