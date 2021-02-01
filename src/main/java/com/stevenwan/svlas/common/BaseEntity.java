package com.stevenwan.svlas.common;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @Author steven.wan
 */
@Data
public class BaseEntity implements Serializable {
    /**
     * 主键ID
     */
    private Long id;
}
