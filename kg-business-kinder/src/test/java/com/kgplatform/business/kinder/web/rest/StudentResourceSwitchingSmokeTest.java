package com.kgplatform.business.kinder.web.rest;

import com.kgplatform.business.kinder.domain.dto.StudentDetailDto;
import com.kgplatform.business.kinder.domain.dto.StudentDto;
import com.kgplatform.business.kinder.domain.dto.TenantDto;
import com.kgplatform.business.kinder.service.IStudentService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudentResourceSwitchingSmokeTest {

    @Test
    void should_expose_current_database_and_student_detail() throws Exception {
        IStudentService studentService = mock(IStudentService.class);
        when(studentService.currentDatabase()).thenReturn("kg_kinder_demo_001");
        when(studentService.selectDetail(1L)).thenReturn(new StudentDetailDto()
                .setStudent(new StudentDto().setId(1L).setStudentNo("STU001").setStudentName("张小明"))
                .setTenant(new TenantDto().setId(2L).setTenantCode("KINDER_DEMO_001").setTenantName("演示园所一"))
                .setCurrentUserNickname("园所管理员001"));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new StudentResource(studentService)).build();

        mockMvc.perform(get("/students/current-database"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data").value("kg_kinder_demo_001"));

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data.student.id").value("1"))
                .andExpect(jsonPath("$.data.student.studentName").value("张小明"))
                .andExpect(jsonPath("$.data.tenant.id").value("2"))
                .andExpect(jsonPath("$.data.tenant.tenantCode").value("KINDER_DEMO_001"))
                .andExpect(jsonPath("$.data.tenant.tenantName").value("演示园所一"))
                .andExpect(jsonPath("$.data.currentUserNickname").value("园所管理员001"));
    }
}
