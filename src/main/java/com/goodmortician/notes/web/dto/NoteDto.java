package com.goodmortician.notes.web.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoteDto implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime createAt;

    }
