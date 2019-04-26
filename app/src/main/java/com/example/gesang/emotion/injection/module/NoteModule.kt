package com.example.gesang.emotion.injection.module

import com.example.gesang.emotion.service.i.NoteService
import com.example.gesang.emotion.service.impl.NoteServiceImp
import dagger.Module
import dagger.Provides

@Module
class NoteModule {

    @Provides
    fun providesNoteService(noteService:NoteServiceImp):NoteService = noteService


}