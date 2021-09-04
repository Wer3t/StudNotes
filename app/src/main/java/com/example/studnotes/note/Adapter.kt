package com.example.studnotes.note

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studnotes.NotesEditor
import com.example.studnotes.R


class Adapter (mainList : ArrayList<NoteData>, contextMain: Context): RecyclerView.Adapter<Adapter.Holder>() {
    var listArray = mainList
    var context = contextMain

    class Holder(itemView: View, contextV : Context) : RecyclerView.ViewHolder(itemView) {
        val context = contextV

        val subject = itemView.findViewById<TextView>(R.id.subject)
        val title = itemView.findViewById<TextView>(R.id.title)
        val note = itemView.findViewById<TextView>(R.id.note)

        fun setData(el : NoteData){
            subject.text = el.subject
            title.text = el.title
            note.text = el.note

            itemView.setOnClickListener{
                val intent = Intent(context, NotesEditor::class.java).apply {
                    putExtra(IntentConstants.SUBJECT_KEY, el.subject)
                    putExtra(IntentConstants.TITLE_KEY, el.title)
                    putExtra(IntentConstants.NOTE_KEY, el.note)
                    putExtra(IntentConstants.IMAGE_URI_KEY, el.imgUri)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(inflater.inflate(R.layout.rc_item, parent, false),  context)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setData(listArray.get(position))
    }

    override fun getItemCount(): Int {
        return listArray.size
    }

    fun updateAdapter(listItems : List<NoteData>){
        listArray.clear()
        listArray.addAll(listItems)
        notifyDataSetChanged()
    }

}