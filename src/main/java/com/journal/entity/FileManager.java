package com.journal.entity;

import java.io.IOException;

public interface FileManager {


    File getFileById(Integer fileId);

    File merge(File file);

    File persist(File file);
    File save(byte[] bytes, String fileName, String type) throws IOException;
}
