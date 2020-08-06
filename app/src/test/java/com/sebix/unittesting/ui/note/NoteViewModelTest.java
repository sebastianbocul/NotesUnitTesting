package com.sebix.unittesting.ui.note;

import com.sebix.unittesting.models.Note;
import com.sebix.unittesting.repository.NoteRepository;
import com.sebix.unittesting.ui.Resource;
import com.sebix.unittesting.util.InstantExecutorExtension;
import com.sebix.unittesting.util.LiveDataTestUtil;
import com.sebix.unittesting.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Flowable;
import io.reactivex.internal.operators.single.SingleToFlowable;

import static com.sebix.unittesting.repository.NoteRepository.INSERT_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(InstantExecutorExtension.class)
public class NoteViewModelTest {
    //system under test
    private NoteViewModel noteViewModel;
    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        noteViewModel = new NoteViewModel(noteRepository);
    }



    // can't observe a note that hasnt been set
    @Test
    void observeEmptyNote_whenNotSet() throws Exception {
        //Arrange
        LiveDataTestUtil<Note> liveDataTestUtil = new LiveDataTestUtil<>();
        //Act
        Note note = liveDataTestUtil.getValue(noteViewModel.observeNote());
        //Assert
        assertNull(note);
    }
    //Observe a note has been set and onChanged will trigger in activity

    @Test
    void observeNote_whenSet() throws Exception {
        //Arrange
        Note note= new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Note> liveDataTestUtil = new LiveDataTestUtil<>();
        //Act
        noteViewModel.setNote(note);
        Note observedNote = liveDataTestUtil.getValue(noteViewModel.observeNote());
        //Assert
        assertEquals(note,observedNote);
    }
    //insert a new note and observe row returned

    @Test
    void insertNote_returnRow() throws Exception {
        //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        final int insertedRow =1 ;
        Flowable<Resource<Integer>> returnedData = SingleToFlowable.just(Resource.success(insertedRow,INSERT_SUCCESS));
        when(noteRepository.insertNote(any(Note.class))).thenReturn(returnedData);
        //Act
        noteViewModel.setNote(note);
        Resource<Integer> returnValue = liveDataTestUtil.getValue(noteViewModel.insertNote());
        //Assert
        assertEquals(Resource.success(insertedRow,INSERT_SUCCESS),returnValue);


    }

    //insert:dont return a new row without observer
    @Test
    void dontReturnInsertRowWithoutObserver() throws Exception {
        //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        //Act
        noteViewModel.setNote(note);
        //Assert
        verify(noteRepository,never()).insertNote(any(Note.class));
    }

    //set note , null title, throw exception
    @Test
    void setNote_nullTitle_throwException() throws Exception {
        //Arrange
        final Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);
        //Assert
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                //Act
                noteViewModel.setNote(note);
            }
        });

    }
}
