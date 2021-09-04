package com.example.studnotes.note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studnotes.R

class Adapter (mainList : ArrayList<NoteData>): RecyclerView.Adapter<Adapter.Holder>() {
    var listArray = mainList
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subject = itemView.findViewById<TextView>(R.id.subject)
        val title = itemView.findViewById<TextView>(R.id.title)
        val note = itemView.findViewById<TextView>(R.id.note)

        fun setData(el : NoteData){
            subject.text = el.subject
            title.text = el.title
            note.text = el.note
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(inflater.inflate(R.layout.rc_item, parent, false))
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