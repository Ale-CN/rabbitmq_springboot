package com.ale.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogInfo implements Serializable {

    private String level;
    private String msg;
    private String exceptionName;

}
