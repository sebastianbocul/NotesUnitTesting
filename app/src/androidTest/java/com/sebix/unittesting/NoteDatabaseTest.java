package com.sebix.unittesting;

import android.app.Application;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.sebix.unittesting.persistance.NoteDao;
import com.sebix.unittesting.persistance.NoteDatabase;

import org.junit.After;
import org.junit.Before;

public abstract class NoteDatabaseTest {
    //system under test
    private NoteDatabase noteDatabase;

    public NoteDao getNoteDao(){
        return noteDatabase.getNoteDao();
    }

    @Before
    public void init(){
        noteDatabase= Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                NoteDatabase.class
        ).build();
    }

    @After
    public void finish(){
        noteDatabase.close();
    }
}
