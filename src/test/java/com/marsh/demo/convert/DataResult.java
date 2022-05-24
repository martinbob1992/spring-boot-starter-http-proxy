package com.marsh.demo.convert;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Marsh
 * @date 2022-05-24日 14:36
 */
@Data
public class DataResult implements Serializable {
    private Integer code;
    private String msg;
    private String key;
}
