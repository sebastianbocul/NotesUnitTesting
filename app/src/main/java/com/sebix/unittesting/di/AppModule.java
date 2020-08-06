package com.sebix.unittesting.di;

import android.app.Application;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sebix.unittesting.persistance.NoteDao;
import com.sebix.unittesting.persistance.NoteDatabase;
import com.sebix.unittesting.repository.NoteRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.sebix.unittesting.persistance.NoteDatabase.DATABASE_NAME;
@Module
class AppModule {

    @Singleton
    @Provides
    static NoteDatabase provideNoteDatabase(Application application){
        return Room.databaseBuilder(
                application,
                NoteDatabase.class,
                DATABASE_NAME
        ).build();
    }

    @Singleton
    @Provides
    static NoteDao provideNoteDao(NoteDatabase noteDatabase){
        return noteDatabase.getNoteDao();
    }


    @Singleton
    @Provides
    static NoteRepository provideNoteRepository(NoteDao noteDao){
        return new NoteRepository(noteDao);
    }
}