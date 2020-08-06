package com.sebix.unittesting.repository;

import com.sebix.unittesting.models.Note;
import com.sebix.unittesting.persistance.NoteDao;
import com.sebix.unittesting.ui.Resource;
import com.sebix.unittesting.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import io.reactivex.Single;

import static com.sebix.unittesting.repository.NoteRepository.INSERT_FAILURE;
import static com.sebix.unittesting.repository.NoteRepository.INSERT_SUCCESS;
import static com.sebix.unittesting.repository.NoteRepository.NOTE_TITLE_NULL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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
        assertEquals(Resource.success(1,INSERT_SUCCESS),returnedValue);

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
        assertEquals(Resource.error(null,INSERT_FAILURE),returnedValue);
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
        assertEquals(NOTE_TITLE_NULL,exception.getMessage());
    }
}
