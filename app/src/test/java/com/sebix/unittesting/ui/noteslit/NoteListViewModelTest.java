package com.sebix.unittesting.ui.noteslit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sebix.unittesting.models.Note;
import com.sebix.unittesting.repository.NoteRepository;
import com.sebix.unittesting.ui.Resource;
import com.sebix.unittesting.ui.noteslist.NotesListViewModel;
import com.sebix.unittesting.util.InstantExecutorExtension;
import com.sebix.unittesting.util.LiveDataTestUtil;
import com.sebix.unittesting.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.DELETE;

import static com.sebix.unittesting.repository.NoteRepository.DELETE_FAILURE;
import static com.sebix.unittesting.repository.NoteRepository.DELETE_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(InstantExecutorExtension.class)
public class NoteListViewModelTest {

    //system under test
    private NotesListViewModel notesListViewModel;

    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        notesListViewModel=new NotesListViewModel(noteRepository);
    }


    //retrieve list of notes| observe list | return list

    @Test
    void retrieveNotes_returnNotesList() throws Exception {
        //Arrange
        List<Note> returnedData = TestUtil.TEST_NOTES_LIST;
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.getNotes()).thenReturn(returnedValue);
        //Act
        notesListViewModel.getNotes();
        List<Note> observeData = liveDataTestUtil.getValue(notesListViewModel.observeNotes());
        //Assert
        assertEquals(returnedData,observeData);
    }
    //retrieve list of notes | observe the list | return empty list

    @Test
    void retrieveNotes_returnEmptyNotesList() throws Exception {
        //Arrange
        List<Note> returnedData = new ArrayList<>();
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.getNotes()).thenReturn(returnedValue);
        //Act
        notesListViewModel.getNotes();
        List<Note> observeData = liveDataTestUtil.getValue(notesListViewModel.observeNotes());
        //Assert
        assertEquals(returnedData,observeData);
    }
    //delete note | observe Resource.success | return Resource.success

    @Test
    void deleteNote_observeResourceSuccess() throws Exception {
        //Arrange
        Note deletedNote = new Note(TestUtil.TEST_NOTE_1);
        Resource<Integer> returnedData = Resource.success(1, DELETE_SUCCESS);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Resource<Integer>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.deleteNote(any(Note.class))).thenReturn(returnedValue);
        //Act
        Resource<Integer> observedValue = liveDataTestUtil.getValue(notesListViewModel.deleteNote(deletedNote));
        //Assert
        assertEquals(returnedData,observedValue);

    }
    //delete note | observe Resource.error | return Resource.error
    @Test
    void deleteNote_observeResourceError() throws Exception {
        //Arrange
        Note deletedNote = new Note(TestUtil.TEST_NOTE_1);
        Resource<Integer> returnedData = Resource.error(null, DELETE_FAILURE);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Resource<Integer>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.deleteNote(any(Note.class))).thenReturn(returnedValue);
        //Act
        Resource<Integer> observedValue = liveDataTestUtil.getValue(notesListViewModel.deleteNote(deletedNote));
        //Assert
        assertEquals(returnedData,observedValue);

    }
}
