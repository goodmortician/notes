package com.goodmortician.notes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "note")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "Id")
    private Integer id;
    @Column (name = "name")
    private String name;
    @Column (name = "description")
    private String description;
    @Column(name = "create_at")
    private LocalDateTime createAt;

}
