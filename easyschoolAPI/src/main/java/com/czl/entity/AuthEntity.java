package com.czl.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 安全令牌Entity
 *
 * @author jjy
 * @since 2019/12/06 17:00
 */
@Data
public class AuthEntity implements Serializable {

    private String access_token;

    private String refresh_token;

    private String expires_in;
}
