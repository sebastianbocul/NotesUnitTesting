package com.sebix.unittesting.di;

import com.sebix.unittesting.NotesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class AcitvityBuildersModule {

    @ContributesAndroidInjector
    abstract NotesListActivity contribureNotesListActivity();
}
