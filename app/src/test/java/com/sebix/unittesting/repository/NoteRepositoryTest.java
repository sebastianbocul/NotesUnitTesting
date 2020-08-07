package com.sebix.unittesting.repository;

import androidx.lifecycle.MutableLiveData;

import com.sebix.unittesting.models.Note;
import com.sebix.unittesting.persistance.NoteDao;
import com.sebix.unittesting.ui.Resource;
import com.sebix.unittesting.util.InstantExecutorExtension;
import com.sebix.unittesting.util.LiveDataTestUtil;
import com.sebix.unittesting.util.TestUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static com.sebix.unittesting.repository.NoteRepository.DELETE_FAILURE;
import static com.sebix.unittesting.repository.NoteRepository.DELETE_SUCCESS;
import static com.sebix.unittesting.repository.NoteRepository.INSERT_FAILURE;
import static com.sebix.unittesting.repository.NoteRepository.INSERT_SUCCESS;
import static com.sebix.unittesting.repository.NoteRepository.INVALID_NOTE_ID;
import static com.sebix.unittesting.repository.NoteRepository.NOTE_TITLE_NULL;
import static com.sebix.unittesting.repository.NoteRepository.UPDATE_FAILURE;
import static com.sebix.unittesting.repository.NoteRepository.UPDATE_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith({InstantExecutorExtension.class})
public class NoteRepositoryTest {
    public static final Note NOTE1 = new Note(TestUtil.TEST_NOTE_1);
    //system under test
    private NoteRepository noteRepository;
    // @Mock
    private NoteDao noteDao;

    @BeforeEach
    public void initEech() {
        //   MockitoAnnotations.initMocks(this);
        noteDao = mock(NoteDao.class);
        noteRepository = new NoteRepository(noteDao);
    }

    //    insert note
//    verify the correct method is called
//    confirm observer is triggere
//    confirm new row inserted
    @Test
    void insertNote_returnRow() throws Exception {
        //Arrange
        final Long insertRow = 1L;
        final Single<Long> returnedData = Single.just(insertRow);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);
        //Act
        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingFirst();
        //Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);
        System.out.println("Returned value: " + returnedValue.data);
        assertEquals(Resource.success(1, INSERT_SUCCESS), returnedValue);
        //or test using RxJava
//        noteRepository.insertNote(NOTE1)
//                .test()
//                .await()
//                .assertValue(Resource.success(1,INSERT_SUCCESS));
    }

    //    insert note
//    failure (return -1)
    @Test
    void insertNote_returnError() throws Exception {
        //Arrange
        final Long failedInsert = -1L;
        final Single<Long> returnedData = Single.just(failedInsert);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);
        //Act
        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingFirst();
        //Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);
        assertEquals(Resource.error(null, INSERT_FAILURE), returnedValue);
    }

    //    insert note
//    null title
//    confirm throw exception
    @Test
    void insertNote_noTitle_throwException() throws Exception {
        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.insertNote(note);
            }
        });
        assertEquals(NOTE_TITLE_NULL, exception.getMessage());
    }
    //update note
    // verify correct method is called
    //confirm observer is trigger
    //confirm number of rows updated

    @Test
    void updateNote_returnNumRowsUpdated() throws Exception {
        //Arrange
        final int updatedRow = 1;
        when(noteDao.updateNotes(any(Note.class))).thenReturn(Single.just(updatedRow));
        //Act
        final Resource<Integer> returnValue = noteRepository.updateNote(NOTE1).blockingFirst();
        //Assert
        verify(noteDao).updateNotes(any(Note.class));
        verifyNoMoreInteractions(noteDao);
        assertEquals(Resource.success(updatedRow, UPDATE_SUCCESS), returnValue);
    }
    //update note
    //failure (-1)

    @Test
    void updateNote_returnFailure() throws Exception {
        //Arrange
        final int failedInsert = -1;
        final Single<Integer> returnedData = Single.just(failedInsert);
        when(noteDao.updateNotes(any(Note.class))).thenReturn(returnedData);
        //Act
        final Resource<Integer> returnValue = noteRepository.updateNote(NOTE1).blockingFirst();
        //Assert
        verify(noteDao).updateNotes(any(Note.class));
        verifyNoMoreInteractions(noteDao);
        assertEquals(Resource.error(null, UPDATE_FAILURE), returnValue);
    }

    //update note
    //null title
    //throw exception
    @Test
    void updatetNote_noTitle_throwException() throws Exception {
        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.updateNote(note);
            }
        });
        assertEquals(NOTE_TITLE_NULL, exception.getMessage());
    }
    //delete note with null id |throw exception

    @Test
    void deleteNote_nullId_throwException() throws Exception {
        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setId(-1);
                noteRepository.deleteNote(note);
            }
        });
        assertEquals(INVALID_NOTE_ID, exception.getMessage());
    }
    //delete note|delete success | return Resource.success with deleted row

    @Test
    void deleteNote_deleteSuccess_returnResourceSuccess() throws Exception {
        //Arrange
        final int deletedRow = 1;
        Resource<Integer> successResponse = Resource.success(deletedRow, DELETE_SUCCESS);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        when(noteDao.deleteNote(any(Note.class))).thenReturn(Single.just(deletedRow));
        //Act
        Resource<Integer> observeResource = liveDataTestUtil.getValue(noteRepository.deleteNote(NOTE1));
        //Assert
        assertEquals(successResponse, observeResource);
    }
    //delete note| delete failure| return Resource.error

    @Test
    void deleteNote_deleteFailure_returnResourceError() throws Exception {
        //Arrange
        final int deletedRow = -1;
        Resource<Integer> errorResponse = Resource.error(null, DELETE_FAILURE);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        when(noteDao.deleteNote(any(Note.class))).thenReturn(Single.just(deletedRow));
        //Act
        Resource<Integer> observeResource = liveDataTestUtil.getValue(noteRepository.deleteNote(NOTE1));
        //Assert
        assertEquals(errorResponse, observeResource);
    }
    //retrieve notes | return list of notes

    @Test
    void getNotes_returnListWithNotes() throws Exception {
        //Arrange
        List<Note> notes = TestUtil.TEST_NOTES_LIST;
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedData = new MutableLiveData<>();
        returnedData.setValue(notes);
        when(noteDao.getNotes()).thenReturn(returnedData);
        //Act
        List<Note> observedData = liveDataTestUtil.getValue(noteRepository.getNotes());
        //Assert
        assertEquals(notes,observedData);
    }

    //retrieve notes | return empty list
    @Test
    void getNotes_returnEmptyList() throws Exception {
        //Arrange
        List<Note> notes = new ArrayList<>();
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedData = new MutableLiveData<>();
        returnedData.setValue(notes);
        when(noteDao.getNotes()).thenReturn(returnedData);
        //Act
        List<Note> observedData = liveDataTestUtil.getValue(noteRepository.getNotes());
        //Assert
        assertEquals(notes,observedData);
    }
}
