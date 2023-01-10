package com.goodmortician.notes.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class JwtResponse {
    private final String token;
}
