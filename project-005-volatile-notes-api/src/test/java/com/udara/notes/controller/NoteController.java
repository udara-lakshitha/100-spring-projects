package com.udara.notes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udara.notes.dto.CreateNoteDTO;
import com.udara.notes.exception.NoteNotFoundException;
import com.udara.notes.model.Note;
import com.udara.notes.service.InMemoryNoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
public class NoteController {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InMemoryNoteService inMemoryNoteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Note note1;
    private Note note2;
    private CreateNoteDTO createNoteDTO;

    @BeforeEach
    public void setup() {
        note1 = new Note(1L, "Test Note 1");
        note2 = new Note(2L, "Test Note 2");
        createNoteDTO = new CreateNoteDTO();
        createNoteDTO.setContent("New note content");
    }

    @Test
    void createNote_shouldReturnCreatedNoteAndStatus201() throws Exception {
        given(inMemoryNoteService.createNote(any(CreateNoteDTO.class))).willReturn(note1);

        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createNoteDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(note1.getId()))
                .andExpect(jsonPath("$.content").value(note1.getContent()));

        then(inMemoryNoteService).should(times(1)).createNote(any(CreateNoteDTO.class));
    }

    @Test
    void getAllNotes_shouldReturnListOfNotesAndStatus200() throws Exception {
        List<Note> notes = Arrays.asList(note1, note2);
        given(inMemoryNoteService.getAllNotes()).willReturn(notes);

        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk()) // Expect 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(notes.size()))
                .andExpect(jsonPath("$[0].id").value(note1.getId()))
                .andExpect(jsonPath("$[1].id").value(note2.getId()));

        then(inMemoryNoteService).should(times(1)).getAllNotes();
    }

    @Test
    void getNoteById_whenNoteExists_shouldReturnNoteAndStatus200() throws Exception {
        given(inMemoryNoteService.getNoteById(note1.getId())).willReturn(note1);

        mockMvc.perform(get("/notes/{id}", note1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(note1.getId()))
                .andExpect(jsonPath("$.content").value(note1.getContent()));

        then(inMemoryNoteService).should(times(1)).getNoteById(note1.getId());
    }

    @Test
    void getNoteById_whenNoteDoesNotExist_shouldReturnStatus404() throws Exception {
        Long nonExistentId = 99L;
        given(inMemoryNoteService.getNoteById(nonExistentId)).willThrow(new NoteNotFoundException(nonExistentId));

        mockMvc.perform(get("/notes/{id}", nonExistentId))
                .andExpect(status().isNotFound());

        then(inMemoryNoteService).should(times(1)).getNoteById(nonExistentId);
    }


    @Test
    void updateNote_whenNoteExists_shouldReturnUpdatedNoteAndStatus200() throws Exception {
        Long noteIdToUpdate = note1.getId();
        Note updatedNote = new Note(noteIdToUpdate, createNoteDTO.getContent());
        given(inMemoryNoteService.updateNote(eq(noteIdToUpdate), any(CreateNoteDTO.class))).willReturn(updatedNote);

        mockMvc.perform(put("/notes/{id}", noteIdToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createNoteDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(updatedNote.getId()))
                .andExpect(jsonPath("$.content").value(updatedNote.getContent()));

        then(inMemoryNoteService).should(times(1)).updateNote(eq(noteIdToUpdate), any(CreateNoteDTO.class));
    }

    @Test
    void updateNote_whenNoteDoesNotExist_shouldReturnStatus404() throws Exception {
        Long nonExistentId = 99L;
        given(inMemoryNoteService.updateNote(eq(nonExistentId), any(CreateNoteDTO.class)))
                .willThrow(new NoteNotFoundException(nonExistentId));

        mockMvc.perform(put("/notes/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createNoteDTO)))
                .andExpect(status().isNotFound());

        then(inMemoryNoteService).should(times(1)).updateNote(eq(nonExistentId), any(CreateNoteDTO.class));
    }

    @Test
    void deleteNote_whenNoteExists_shouldReturnStatus204() throws Exception {
        Long noteIdToDelete = note1.getId();
        willDoNothing().given(inMemoryNoteService).deleteNote(noteIdToDelete);
        mockMvc.perform(delete("/notes/{id}", noteIdToDelete))
                .andExpect(status().isNoContent()); // Expect 204 No Content

        then(inMemoryNoteService).should(times(1)).deleteNote(noteIdToDelete);
    }

    @Test
    void deleteNote_whenNoteDoesNotExist_shouldReturnStatus404() throws Exception {
        Long nonExistentId = 99L;
        willThrow(new NoteNotFoundException(nonExistentId)).given(inMemoryNoteService).deleteNote(nonExistentId);

        mockMvc.perform(delete("/notes/{id}", nonExistentId))
                .andExpect(status().isNotFound());

        then(inMemoryNoteService).should(times(1)).deleteNote(nonExistentId);
    }
}
