package com.czl.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户Entity
 *
 * @author jjy
 * @since 2019/12/06 17:00
 */
@Data
public class UserEntity implements Serializable {

    private String accountId;

    private String companyId;

    private String jobId;
    
    private String roleId;
    
    private String status;
}
