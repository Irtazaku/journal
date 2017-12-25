package com.journal.dto;

import java.io.Serializable;

/**
 * Created by Venturedive on 11/09/2017.
 */
public class JournalResponseDto implements Serializable {
    private ResponseHeaderDto responseHeaderDto;
    private JournalDto journalDto;

    public JournalResponseDto() {
    }

    public JournalResponseDto(ResponseHeaderDto responseHeaderDto, JournalDto journalDto) {
        this.responseHeaderDto = responseHeaderDto;
        this.journalDto = journalDto;
    }

    public ResponseHeaderDto getResponseHeaderDto() {
        return responseHeaderDto;
    }

    public void setResponseHeaderDto(ResponseHeaderDto responseHeaderDto) {
        this.responseHeaderDto = responseHeaderDto;
    }

    public JournalDto getJournalDto() {
        return journalDto;
    }

    public void setJournalDto(JournalDto journalDto) {
        this.journalDto = journalDto;
    }
}
