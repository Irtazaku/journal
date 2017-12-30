package com.journal.dto;

import java.io.Serializable;

/**
 * Created by Venturedive on 11/09/2017.
 */
public class FileResponseDto implements Serializable {
    private ResponseHeaderDto responseHeaderDto;
    private FileDto fileDto;

    public FileResponseDto() {
    }

    public FileResponseDto(ResponseHeaderDto responseHeaderDto, FileDto fileDto) {
        this.responseHeaderDto = responseHeaderDto;
        this.fileDto = fileDto;
    }

    public ResponseHeaderDto getResponseHeaderDto() {
        return responseHeaderDto;
    }

    public void setResponseHeaderDto(ResponseHeaderDto responseHeaderDto) {
        this.responseHeaderDto = responseHeaderDto;
    }


    public FileDto getFileDto() {
        return fileDto;
    }

    public void setFileDto(FileDto fileDto) {
        this.fileDto = fileDto;
    }
}
