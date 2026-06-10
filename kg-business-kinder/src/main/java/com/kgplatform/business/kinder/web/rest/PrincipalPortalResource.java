package com.kgplatform.business.kinder.web.rest;

import com.kgplatform.business.kinder.service.PrincipalPortalService;
import com.kgplatform.common.web.core.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 园长端门户控制层
 * <p>
 * PrincipalPortalResource 控制层
 *
 * @author kg_chen
 * @since 2026-06-10 00:00:00
 */
@Validated
@RestController
@Tag(name = "PrincipalPortalResource", description = "园长端门户")
@RequestMapping(path = "/principal-portal", produces = MediaType.APPLICATION_JSON_VALUE)
public class PrincipalPortalResource {

    private final PrincipalPortalService principalPortalService;

    public PrincipalPortalResource(PrincipalPortalService principalPortalService) {
        this.principalPortalService = principalPortalService;
    }

    @GetMapping("/home")
    @Operation(summary = "园长端首页")
    public Result<Map<String, Object>> home() {
        return Result.ok(principalPortalService.home());
    }
}
