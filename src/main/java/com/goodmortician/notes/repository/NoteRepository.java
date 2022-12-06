package com.goodmortician.notes.repository;

import com.goodmortician.notes.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {
}
