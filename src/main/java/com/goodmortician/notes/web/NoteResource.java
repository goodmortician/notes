package com.goodmortician.notes.web;

import com.goodmortician.notes.auth.Authenticate;
import com.goodmortician.notes.service.NoteService;
import com.goodmortician.notes.web.dto.NoteDto;
import com.goodmortician.notes.web.dto.NoteDtoCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.goodmortician.notes.config.Const.ROLE_ADMIN;
import static com.goodmortician.notes.config.Const.ROLE_USER;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/notes")
public class NoteResource {
    private final NoteService noteService;

    @Authenticate(allowedRoles = {ROLE_ADMIN, ROLE_USER})
    @GetMapping(value = "/{id}")
    public NoteDto getOneNote(@PathVariable(name = "id") Integer id) {
        return noteService.getOneNote(id);
    }

    @PostMapping()
    public NoteDto createNote(@RequestBody NoteDtoCreateRequest createRequest) {
        return noteService.createNote(createRequest);
    }

    @PutMapping(value = "/{id}")
    public NoteDto updateNote(@PathVariable(name = "id") Integer id, @RequestBody NoteDto updateRequest) {
        return noteService.updateNote(id, updateRequest);
    }

    @Authenticate(allowedRoles = {ROLE_ADMIN, ROLE_USER})
    @GetMapping()
    public List<NoteDto> getAll() {
        return noteService.getAllNotes();
    }

}
