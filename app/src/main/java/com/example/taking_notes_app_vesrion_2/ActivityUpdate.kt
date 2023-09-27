package com.example.taking_notes_app_vesrion_2

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.taking_notes_app_vesrion_2.databinding.ActivityUpdateBinding

class ActivityUpdate : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db:DataBaseHelper
    private var noteID : Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = DataBaseHelper(this)
        noteID = intent.getIntExtra("note_id" , -1)
        if (noteID == -1){
            finish()
            return
        }

        val note =db.getNotesByID(noteID)
        binding.updatetitleEditText.setText(note.title)
        binding.updatecontentEditText.setText(note.descontent)


        binding.updateDoneButton.setOnClickListener {
            val newTitle = binding.updatetitleEditText.text.toString()
            val newdescontent = binding.updatecontentEditText.text.toString()
            val NoteUpdate = NoteDataClass(noteID, newTitle,newdescontent)
            db.NoteUpdate(NoteUpdate)
            finish()
            Toast.makeText(this,"The Note Has Been Upadated", Toast.LENGTH_SHORT).show()
        }

    }
}