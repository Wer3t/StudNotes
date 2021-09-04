package com.example.studnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studnotes.db.MyDbManager
import com.example.studnotes.note.Adapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val dbManager = MyDbManager(this)
    val myAdapter = Adapter(ArrayList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        fAB_add.setOnClickListener{
            val intent = Intent(this, NotesEditor::class.java)
            startActivity(intent)
        }
    }

    private fun init (){
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter
    }

    private fun fillAdapter(){
        val list = dbManager.readData()
        myAdapter.updateAdapter(list)
        if(list.size > 0)
            textView.visibility = View.GONE
        else
            textView.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }

    override fun onResume() {
        super.onResume()
        dbManager.openDb()
        fillAdapter()
    }

}