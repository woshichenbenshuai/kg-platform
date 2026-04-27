package com.kgplatform.auth.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 登录请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
@Schema(description = "登录请求参数")
public class LoginVo {

    @Schema(description = "登录账号")
    private String username;

    @Schema(description = "登录密码")
    private String password;
}
