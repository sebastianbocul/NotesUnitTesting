package com.sebix.unittesting.di;

import com.sebix.unittesting.ui.NotesList.NotesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class AcitvityBuildersModule {

    @ContributesAndroidInjector
    abstract NotesListActivity contribureNotesListActivity();
}
