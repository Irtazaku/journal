package com.journal.entity;

import java.io.IOException;
import java.io.InputStream;

public interface FileManager {


    File getFileById(Integer fileId);

    File merge(File file);

    File persist(File file);
    File save(byte[] bytes, String fileName, String type) throws IOException;

    File save(InputStream inputStream, String fileName, String type) throws IOException;

    File getFileByKeyAndType(String fileKey, String Type);
}
