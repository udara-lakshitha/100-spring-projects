package com.udara.notes.service;

import com.udara.notes.dto.CreateNoteDTO;
import com.udara.notes.exception.NoteNotFoundException;
import com.udara.notes.model.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryNoteService {
    private static final Logger log = LoggerFactory.getLogger(InMemoryNoteService.class);

    private final Map<Long, Note> notes = new ConcurrentHashMap<>();

    private final AtomicLong counter = new AtomicLong();

    // create
    public Note createNote(CreateNoteDTO noteDTO) {
        Long id = counter.incrementAndGet();
        Note note = new Note(id, noteDTO.getContent());
        notes.put(id, note);
        log.info("Created new note: {}", note);
        return note;
    }

    // read all
    public List<Note> getAllNotes() {
        log.info("Retrieving all notes ({} total)", notes.size());
        return new ArrayList<>(notes.values());
    }

    // get by id
    public Note getNoteById(Long id) {
        log.info("Attempting to retrieve note with id: {}", id);
        Note note = notes.get(id);
        if (note == null) {
            log.warn("Note not found with id {}", id);
            throw new NoteNotFoundException(id);
        }
        log.info("Retrieved Note: {}", note);
        return note;
    }

    // update
    public Note updateNote(Long id, CreateNoteDTO noteDTO) {
        log.info("Attempting to update note with id: {}", id);
        Note note = notes.get(id);
        if (note == null) {
            log.warn("Update failed. Note not found with id: {}", id);
            throw new NoteNotFoundException(id);
        }
        note.setContent(noteDTO.getContent());
        log.info("Updated Note: {}", note);
        return note;
    }

    // delete
    public void deleteNote(Long id) {
        log.info("Attempting to delete note with id: {}", id);
        Note note = notes.remove(id);
        if (note == null) {
            log.warn("Delete failed. Note not found with id: {}", id);
            throw new NoteNotFoundException(id);
        }
        log.info("Deleted Note: {}", note);
    }
}
