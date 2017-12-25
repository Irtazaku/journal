package com.journal.dto;

import java.io.Serializable;

/**
 * Created by Venturedive on 11/09/2017.
 */
public class UserResponseDto implements Serializable {
    private ResponseHeaderDto responseHeaderDto;
    private UserDto userDto;

    public UserResponseDto() {
    }

    public UserResponseDto(ResponseHeaderDto responseHeaderDto, UserDto userDto) {
        this.responseHeaderDto = responseHeaderDto;
        this.userDto = userDto;
    }

    public ResponseHeaderDto getResponseHeaderDto() {
        return responseHeaderDto;
    }

    public void setResponseHeaderDto(ResponseHeaderDto responseHeaderDto) {
        this.responseHeaderDto = responseHeaderDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
