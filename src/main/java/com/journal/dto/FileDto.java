package com.journal.dto;


import com.journal.entity.File;
import com.journal.util.ConstantsAndEnums.GlobalConstants;

import java.io.Serializable;

/**
 * Created by Venturedive on 10/29/2017.
 */
public class FileDto implements Serializable {
    private Integer id;
    private String fileName;
    private String fileKey;
    private String type;
    private String url;

    public FileDto() {
    }

    public FileDto(Integer id, String fileName, String fileKey, String type) {
        this.id = id;
        this.fileName = fileName;
        this.fileKey = fileKey;
        this.type = type;
        this.url= String.format("%s%s",GlobalConstants.FILE_DOWNLOAD_BASE_URL, fileKey);
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
        this.url = String.format("%s%s",GlobalConstants.FILE_DOWNLOAD_BASE_URL, fileKey);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public File asEntity() {
        return new File(this.id, this.fileName, this.fileKey, this.type);
    }
}