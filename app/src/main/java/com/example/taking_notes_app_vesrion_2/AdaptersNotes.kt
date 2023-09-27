package com.example.taking_notes_app_vesrion_2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class AdaptersNotes(private  var notes:List<NoteDataClass>,context: Context): RecyclerView.Adapter<AdaptersNotes.NoteViewHolder>() {

    private val db : DataBaseHelper = DataBaseHelper(context)

    class NoteViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val titleTextView:TextView = itemView.findViewById(R.id.TitleTextViews)
        val contextTextView:TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton : ImageView = itemView.findViewById(R.id.Buttonupdate)
        val deleteButton :ImageView = itemView.findViewById(R.id.ButtonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note,parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
      val  note = notes[position]
        holder.titleTextView.text= note.title
        holder.contextTextView.text = note.descontent


        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context,ActivityUpdate::class.java).apply {
               putExtra("note_id" , note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.NoteDelete(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context,"The Note Has Been Deleted ", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newNotes : List<NoteDataClass>){
        notes = newNotes
        notifyDataSetChanged()
    }

}