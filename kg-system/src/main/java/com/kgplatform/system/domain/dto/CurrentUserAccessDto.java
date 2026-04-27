package com.kgplatform.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "Current user access aggregate")
public class CurrentUserAccessDto implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    @Schema(description = "Login username")
    private String username;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tenantId;

    @Schema(description = "Role codes")
    private List<String> roleCodes;

    @Schema(description = "Role names")
    private List<String> roleNames;

    @Schema(description = "Menus")
    private List<MenuDto> menus;
}
