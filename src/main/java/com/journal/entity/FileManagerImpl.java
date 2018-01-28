package com.journal.entity;


import com.journal.util.EntityHelper;
import com.journal.util.Util;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
@Transactional
public class FileManagerImpl implements FileManager {

    public static final Logger LOGGER= Logger.getLogger(FileManagerImpl.class);

    @PersistenceContext()
    private EntityManager entityManager;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private Util util;

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
            ex.printStackTrace();
            if (EntityHelper.isNotNull(out)) {
                out.close();
            }
            throw ex;
        }
    }

    @Override
    public File save(InputStream fileStream, String fileName, String type) throws IOException {
       try {
            LOGGER.info("byted: " + fileStream.toString() + ", fileName: " +fileName +  ", type: " +type);

            String fileKey = util.generateFileKey(type, fileName);
            java.nio.file.Path path = Paths.get(fileKey);//check path
            OutputStream output = Files.newOutputStream(path);
            IOUtils.copy(fileStream, output);
            com.journal.entity.File file = new com.journal.entity.File();
            file.setFileName(fileName);
            file.setFileKey(fileKey);
            file.setType(type);
            file = fileRepository.save(file);
            return file;
        } catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public File getFileByKeyAndType(String fileKey, String type) {
        TypedQuery<File> query = entityManager
                .createNamedQuery("file.getFileByKeyAndType", File.class)
                .setParameter("fileKey", fileKey)
                .setParameter("type", type);

        List<File> files = query.getResultList();
        return !files.isEmpty() ? files.get(0) : null;
    }

    @Override
    public File getFileByKey(String fileKey) {
        TypedQuery<File> query = entityManager
                .createNamedQuery("file.getFileByKey", File.class)
                .setParameter("fileKey", fileKey);

        List<File> files = query.getResultList();
        return !files.isEmpty() ? files.get(0) : null;
    }


}
