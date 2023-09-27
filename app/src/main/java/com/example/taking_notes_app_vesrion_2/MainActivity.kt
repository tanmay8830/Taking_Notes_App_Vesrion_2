package com.example.taking_notes_app_vesrion_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taking_notes_app_vesrion_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var db : DataBaseHelper
    private lateinit var adaptersNotes: AdaptersNotes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DataBaseHelper(this)
        adaptersNotes = AdaptersNotes(db.getAllNotes(),this)

        binding.RecyclernotesView.layoutManager = LinearLayoutManager(this)
        binding.RecyclernotesView.adapter = adaptersNotes


        binding.FloatingAddButton.setOnClickListener {
            val intent =Intent(this,AddingNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        adaptersNotes.refreshData(db.getAllNotes())
    }
}