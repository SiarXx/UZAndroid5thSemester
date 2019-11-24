package com.example.uzapp.tools

import com.example.uzapp.models.Note
import java.io.File

class FileToNoteMapper {
    fun mapNotes(file:File):Note{
        return Note(
            file.name.substring(0,file.name.length-4),
            file.readText()
        )
    }
}