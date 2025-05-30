// path: backend/src/main/java/com/agms/backend/dto/RegisterRequest.java
package com.agms.backend.dto;

import com.agms.backend.model.GraduationRequestStatus;
import com.agms.backend.model.users.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private String roleString;
    private String studentNumber;
}