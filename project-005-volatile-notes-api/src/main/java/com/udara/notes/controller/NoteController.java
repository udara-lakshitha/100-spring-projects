package com.udara.notes.controller;

import com.udara.notes.dto.CreateNoteDTO;
import com.udara.notes.model.Note;
import com.udara.notes.service.InMemoryNoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private final InMemoryNoteService noteService;

    public NoteController(InMemoryNoteService noteService) {
        this.noteService = noteService;
    }

    // create
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody CreateNoteDTO noteDTO) {
        Note note = noteService.createNote(noteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(note);
    }

    // read all
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Note note = noteService.getNoteById(id);
        return ResponseEntity.ok(note);
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody CreateNoteDTO noteDTO) {
        Note note = noteService.updateNote(id, noteDTO);
        return ResponseEntity.ok(note);
    }

    // delete
    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        ResponseEntity.noContent().build();
    }

    // root endpoint
    @GetMapping("/")
    public String home() {
        return "Volatile Notes API base endpoint.";
    }
}
