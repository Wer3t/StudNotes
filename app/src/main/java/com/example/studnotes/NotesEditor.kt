package com.example.studnotes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.studnotes.db.MyDbManager
import kotlinx.android.synthetic.main.activity_notes_editor.*

class NotesEditor : AppCompatActivity() {
    val imageRequestCode = 10
    var tempImgUri = "empty"
    val dbManager = MyDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_editor)

        fabAddPicture.setOnClickListener {
            imViewLayout.visibility = View.VISIBLE
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, imageRequestCode)
            fabAddPicture.visibility = View.GONE
        }

        ibDeleteImg.setOnClickListener{
            val anim : Animation= AnimationUtils.loadAnimation(this, R.anim.click)
            ibDeleteImg.startAnimation(anim)
            imViewLayout.visibility = View.GONE
            fabAddPicture.visibility = View.VISIBLE
            tempImgUri = "empty"
        }

        ibEditImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, imageRequestCode)
        }

        fabSaveNote.setOnClickListener {
            val subject = etSubject.text.toString()
            val title = etTitle.text.toString()
            val note = etNote.text.toString()
            if(subject != "" && title != "" && note != ""){
                dbManager.insertToDb(subject, title, note, tempImgUri)
                Toast.makeText(this, "Заметка сохранена!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }

    override fun onResume() {
        super.onResume()
        dbManager.openDb()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == imageRequestCode){
            ivMainImage.setImageURI(data?.data)
            tempImgUri = data?.data.toString()
        }
    }
}