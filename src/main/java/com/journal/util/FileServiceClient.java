package com.journal.util;

/**
 * Created by IntelliJ IDEA.
 * User: Irtaza Zaidi
 * Date: 02/10/17
 */

public interface FileServiceClient {

    boolean uploadFile(String fileKey, byte[] data);

}
