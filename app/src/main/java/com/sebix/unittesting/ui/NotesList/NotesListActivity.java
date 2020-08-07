package com.sebix.unittesting.ui.NotesList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.sebix.unittesting.R;
import com.sebix.unittesting.repository.NoteRepository;
import com.sebix.unittesting.ui.note.NoteActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class NotesListActivity extends DaggerAppCompatActivity {
    public static final String TAG="NotesListActivity";


    @Inject
    NoteRepository noteRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        Log.d(TAG, "onCreate: " + noteRepository);

        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }
}
