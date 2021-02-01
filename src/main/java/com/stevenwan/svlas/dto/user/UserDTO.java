package com.stevenwan.svlas.dto.user;

import lombok.Data;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-01 15:38
 */
@Data
public class UserDTO {
    /**
     * 性别 男，女
     */
    private Integer sex;

    /**
     * 姓名
     */
    private String name;

    /**
     * 电话
     */
    private String mobile;
}
