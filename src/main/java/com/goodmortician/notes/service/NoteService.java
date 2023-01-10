package com.goodmortician.notes.service;
import com.goodmortician.notes.Note;
import com.goodmortician.notes.repository.NoteRepository;
import com.goodmortician.notes.web.dto.NoteDto;
import com.goodmortician.notes.web.dto.NoteDtoCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteDto createNote (NoteDtoCreateRequest dto) {
        Note note = buildEntity(dto);
        Note savedEntity = noteRepository.save(note);
        return buildNoteDto(savedEntity);
    }
    public NoteDto getOneNote (Integer id) {
        Note entity = noteRepository.getReferenceById(id);
        return buildNoteDto(entity);
    }
    public NoteDto updateNote (Integer id, NoteDto updateRequest){
        Note entity = noteRepository.getReferenceById(id);
        entity.setName(updateRequest.getName());
        entity.setDescription(updateRequest.getDescription());
        noteRepository.save(entity);
        return buildNoteDto(entity);
    }
    public List<NoteDto> getAllNotes() {
        List<Note> noteList = noteRepository.findAll();
         return noteList
                .stream()
                .map(this::buildNoteDto)
                .collect(Collectors.toList());
    }
    private Note buildEntity (NoteDtoCreateRequest dto){
        Note note = new Note();
        note.setCreateAt(LocalDateTime.now());
        note.setName(dto.getName());
        note.setDescription(dto.getDescription());
        return note;
    }
    private NoteDto buildNoteDto (Note savedEntity){
        NoteDto result = new NoteDto();
        result.setId(savedEntity.getId());
        result.setName(savedEntity.getName());
        result.setDescription(savedEntity.getDescription());
        result.setCreateAt(savedEntity.getCreateAt());
        System.out.println(result.toString());
        return result;
    }

}
