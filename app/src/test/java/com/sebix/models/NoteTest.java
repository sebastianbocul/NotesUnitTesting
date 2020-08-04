package com.sebix.models;

import com.sebix.unittesting.models.Note;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Not;

import static org.junit.jupiter.api.Assertions.*;

public class NoteTest {
    //compare to equal Notes

    public static final String TIMESTAMP_1= "05-2019";
    public static final String TIMESTAMP_2= "04-2019";

    @Test
    void isNotesEqual_identicalProperties_returnTrue() throws Exception {
        //Arrange
        Note note1 = new Note(1,"Note #1", "This is note #1",TIMESTAMP_1);
        Note note2 = new Note(1,"Note #1", "This is note #1",TIMESTAMP_1);
        //Act
        //Assert
        assertEquals(note1,note2);
        System.out.println("The notes are equal!");
    }

    //compare notes with 2 different ids

    @Test
    void isNotesEqual_differentIds_returnFalse() throws Exception {
        //Arrange
        Note note1 = new Note(1,"Note #1", "This is note #1",TIMESTAMP_1);
        Note note2 = new Note(2,"Note #1", "This is note #1",TIMESTAMP_1);
        //Act
        //Assert
         assertNotEquals(note1,note2);
         System.out.println("The notes are not equal! Ids are different");
    }
    //compare two notes with different timestamps

    @Test
    void isNotesEqual_differentTimestamps_returnTrue() throws Exception {
        //Arrange
        Note note1 = new Note(1,"Note #1", "This is note #1",TIMESTAMP_1);
        Note note2 = new Note(1,"Note #1", "This is note #1",TIMESTAMP_2);
        //Act
        //Assert
        assertEquals(note1,note2);
        System.out.println("The are equal! Timestamps are different!");
    }
    //compare two notes with different titles
    @Test
    void isNotesEqual_differentTitles_returnFalse() throws Exception {
        //Arrange
        Note note1 = new Note(1,"Note #1", "This is note #1",TIMESTAMP_1);
        Note note2 = new Note(1,"Note #2", "This is note #1",TIMESTAMP_1);
        //Act
        //Assert
        assertNotEquals(note1,note2);
        System.out.println("The notes are equal! Titles are different!");
    }
    //compare two notes with different content
    @Test
    void isNotesEqual_differentContent_returnFalse() throws Exception {
        //Arrange
        Note note1 = new Note(1,"Note #1", "This is note #1",TIMESTAMP_1);
        Note note2 = new Note(1,"Note #1", "This is note #2",TIMESTAMP_1);
        //Act
        //Assert
        assertNotEquals(note1,note2);
        System.out.println("The notes are equal! Contents are different!");
    }
}
