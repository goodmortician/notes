package com.goodmortician.notes.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private String login;
    private Boolean active;
    private String firstName;
    private String lastName;
}
