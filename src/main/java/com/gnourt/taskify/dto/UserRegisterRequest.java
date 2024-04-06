package com.gnourt.taskify.dto;

import com.gnourt.taskify.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UserRegisterRequest {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String avatar;
    private String phone;
    private Role role;
}
