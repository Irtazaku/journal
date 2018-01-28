package com.journal.entity;


import com.journal.dto.FileDto;

import javax.persistence.*;

/**
 * Created by Venturedive on 10/29/2017.
 */

@Entity
@Table(name = "files")
@NamedQueries({
        @NamedQuery(name = "file.getFileById",
                query = " SELECT f" +
                        " FROM File f " +
                        " WHERE f.id = :fileId "),
        @NamedQuery(name = "file.getFileByKeyAndType",
                query = " SELECT f" +
                        " FROM File f " +
                        " WHERE f.fileKey = :fileKey" +
                        " and f.type = :type" +
                        " order by f.id desc "),
        @NamedQuery(name = "file.getFileByKey",
                query = " SELECT f" +
                        " FROM File f " +
                        " WHERE f.fileKey = :fileKey" +
                        " order by f.id desc ")
})
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

    public File( String fileName, String fileKey, String type) {
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