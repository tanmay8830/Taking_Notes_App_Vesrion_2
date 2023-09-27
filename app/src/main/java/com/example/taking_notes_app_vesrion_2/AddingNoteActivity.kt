package com.example.taking_notes_app_vesrion_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.taking_notes_app_vesrion_2.databinding.ActivityAddingNoteBinding

class AddingNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddingNoteBinding
    private lateinit var db:DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddingNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)



        db = DataBaseHelper(this)

        binding.SaveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val descontent = binding.contentEditText.text.toString()
            val note = NoteDataClass(0 ,title , descontent)
            db.insertNote(note)
            finish()
            Toast.makeText(this,"Note Has Been Saved", Toast.LENGTH_SHORT).show()
        }
    }
}