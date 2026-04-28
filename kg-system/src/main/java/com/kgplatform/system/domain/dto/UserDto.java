
package com.kgplatform.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kgplatform.common.web.core.AppConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户
 * <p>
 * UserDto返回对象
 *
 * @author kg_chen
 * @since 2026-04-23 20:40:00
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "系统用户")
public class UserDto implements Serializable {

    /**
     * ID主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 登录账号
     */
    @Schema(description = "登录账号")
    @Size(max = 100, message = "登录账号字段过长")
    private String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 状态 1启用 0禁用
     */
    @Schema(description = "状态 1启用 0禁用")
    private Integer status;

    /**
     * 是否删除 1删除  0未删除
     */
    @Schema(description = "是否删除 1删除  0未删除")
    private Boolean deleteStatus;

    /**
     * 创建时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = AppConstant.FORMAT_PATTERN_DATE_TIME)
    private LocalDateTime createTime;
}
