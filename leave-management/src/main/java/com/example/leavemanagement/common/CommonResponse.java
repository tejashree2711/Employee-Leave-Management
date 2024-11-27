package com.example.leavemanagement.common;

import lombok.Data;

@Data
public class CommonResponse<E> {

    Boolean success;
    Integer responseCode;
    String responseMessage;
    Object data;
}
