package com.journal.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Venturedive on 11/09/2017.
 */
public class JournalListResponseDto implements Serializable {
    private ResponseHeaderDto responseHeaderDto;
    private List<JournalDto> journalDtos;

    public JournalListResponseDto() {
    }

    public JournalListResponseDto(ResponseHeaderDto responseHeaderDto, List<JournalDto> journalDtos) {
        this.responseHeaderDto = responseHeaderDto;
        this.journalDtos = journalDtos;
    }

    public ResponseHeaderDto getResponseHeaderDto() {
        return responseHeaderDto;
    }

    public void setResponseHeaderDto(ResponseHeaderDto responseHeaderDto) {
        this.responseHeaderDto = responseHeaderDto;
    }

    public List<JournalDto> getJournalDtos() {
        return journalDtos;
    }

    public void setJournalDtos(List<JournalDto> journalDtos) {
        this.journalDtos = journalDtos;
    }
}
