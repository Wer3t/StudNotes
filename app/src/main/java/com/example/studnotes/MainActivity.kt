package com.example.studnotes

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studnotes.db.MyDbManager
import com.example.studnotes.dialog.MyDialogFragment
import com.example.studnotes.note.Adapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val dbManager = MyDbManager(this)
    val myAdapter = Adapter(ArrayList(), this)
    val swapHelper = getSwapMg()

    private var isOkDelete = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        fAB_add.setOnClickListener{
            val intent = Intent(this, NotesEditor::class.java)
            startActivity(intent)
        }
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

    private fun init (){
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter
        swapHelper.attachToRecyclerView(recyclerView)
    }

    private fun fillAdapter(){
        val list = dbManager.readData()
        myAdapter.updateAdapter(list)
        if(list.size > 0)
            textView.visibility = View.GONE
        else
            textView.visibility = View.VISIBLE
    }

    private fun getSwapMg(): ItemTouchHelper{

        return ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val myDialogFragment = MyDialogFragment(viewHolder.adapterPosition, myAdapter,dbManager)
                val manager = supportFragmentManager
                myDialogFragment.show(manager, "myDialog")
            }

        })
    }

}