package com.goodmortician.notes.web.dto;

import com.goodmortician.notes.domain.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDtoCreate {
    private String login;
    private Boolean active;
    private String password;
    private String firstName;
    private String lastName;
    private List<Role> role;
}
