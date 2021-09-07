package com.example.studnotes.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.studnotes.MainActivity
import com.example.studnotes.db.MyDbManager
import com.example.studnotes.note.Adapter

class MyDialogFragment( pos: Int, Adapter: Adapter, Manager: MyDbManager): DialogFragment() {
    private val myAdapter = Adapter
    private val dbManager = Manager
    private val position = pos
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Удалить заметку?")
                    .setCancelable(true)
                    .setPositiveButton("Да") { dialog, id ->
                        myAdapter.deleteItem(position, dbManager)
                        Toast.makeText(activity, "Удалено", Toast.LENGTH_LONG).show()
                    }
                    .setNegativeButton("Нет",
                            DialogInterface.OnClickListener { dialog, id ->
                                myAdapter.notifyDataSetChanged()
                            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}