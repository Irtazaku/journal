package com.journal.entity;


import com.journal.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Component
@Transactional
public class FileManagerImpl implements FileManager {

    public static final Logger LOGGER = LoggerFactory.getLogger(FileManagerImpl.class);

    @PersistenceContext()
    private EntityManager entityManager;

	@Autowired
    Util util;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public File getFileById(Integer fileId) {
        TypedQuery<File> query = entityManager
                .createNamedQuery("file.getFileById", File.class)
                .setParameter("fileId", fileId);

        List<File> files = query.getResultList();
        return !files.isEmpty() ? files.get(0) : null;
    }

    @Override
    public File merge(File file) {
        return entityManager.merge(file);
    }

    @Override
    public File persist(File file) {
        return fileRepository.save(file);
    }

    @Override
    public File save(byte[] bytes, String fileName, String type) throws IOException {
        OutputStream out = null;
        try {
            LOGGER.info("byted: " + bytes.toString() + ", fileName: " +fileName +  ", type: " +type);
            String fileKey = util.generateFileKey(type, fileName);
            out = new BufferedOutputStream(new FileOutputStream(fileKey));
            out.write(bytes);
            out.flush();
            out.close();
            com.journal.entity.File file = new com.journal.entity.File();
            file.setFileName(fileName);
            file.setFileKey(fileKey);
            file.setType(type);
            file = fileRepository.save(file);
            return file;
        } catch (Exception ex){
            out.close();
            throw ex;
            //return null;
        }
    }

}
