package com.journal.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.util.URIUtil;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Irtaza Zaidi
 * Date: 02/10/17
 */

@Component
//@Service("fileServiceClient")
public class FileServiceClientImpl implements FileServiceClient {

    private static final Logger LOGGER = Logger.getLogger(FileServiceClientImpl.class.getName());

    //@Value("${external.fileService.url}")
    private String fileServiceUrl;


    private int httpStatus;

    public FileServiceClientImpl() {
    }

    public FileServiceClientImpl(String fileServiceUrl) {
        this.fileServiceUrl = fileServiceUrl;
    }

    public boolean uploadFile(String fileKey, byte[] data) {
        boolean result = false;
        httpStatus = 0; // reset on every upload

        PostMethod post = new PostMethod(escapeUrlString(fileServiceUrl + "postfile?fileKey=" + fileKey));
        try {
            Part[] parts = {
                    new FilePart("Filedata", new ByteArrayPartSource("Filedata", data))
            };

            post.setRequestEntity(
                    new MultipartRequestEntity(parts, post.getParams())
            );

            HttpClient client = new HttpClient();

            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

            int status = client.executeMethod(post);
            httpStatus = status;

            if (status == HttpStatus.SC_OK) {
                result = true;
            }
            else {
                LOGGER.log(Level.SEVERE, "Upload failed. Response code: " + status);
            }
        }
        catch (Throwable e) {
            LOGGER.log(Level.SEVERE, "An exception occurred in FileServiceClient.uploadFile: ", e);
        }
        finally {
            post.releaseConnection();
        }

        return result;
    }

    private String escapeUrlString(String input) {
        String request = "";
        try {
            request = URIUtil.encodeQuery(input);
            LOGGER.info("Created URI " + request);
        } catch (URIException e) {
            LOGGER.warning("Invalid URI attempted for " + input);
        }
        return request;
    }
}
