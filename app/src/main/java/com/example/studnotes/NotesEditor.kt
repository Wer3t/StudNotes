package com.example.studnotes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.studnotes.db.MyDbManager
import com.example.studnotes.note.IntentConstants
import kotlinx.android.synthetic.main.activity_notes_editor.*

class NotesEditor : AppCompatActivity() {
    private val imageRequestCode = 10
    private var tempImgUri = "empty"
    private val dbManager = MyDbManager(this)
    private var isEditState = false
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_editor)
        ivBigImage.visibility = View.GONE
        getIntents()
        fabAddPicture.setOnClickListener {
            imViewLayout.visibility = View.VISIBLE
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
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
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            startActivityForResult(intent, imageRequestCode)
        }

        fabSaveNote.setOnClickListener {
            val subject = etSubject.text.toString()
            val title = etTitle.text.toString()
            val note = etNote.text.toString()
            if (subject != "" && title != "" && note != "") {
                if(!isEditState) {
                    dbManager.insertToDb(subject, title, note, tempImgUri)
                    Toast.makeText(this, "?????????????? ??????????????????!", Toast.LENGTH_LONG).show()
                }else{
                    dbManager.updateItem(id, subject, title, note, tempImgUri)
                    Toast.makeText(this, "?????????????? ??????????????????!", Toast.LENGTH_LONG).show()
                }
                finish()
            }
        }

        ivMainImage.setOnClickListener {
            if(tempImgUri != "empty"){
                ivBigImage.setImageURI(Uri.parse(tempImgUri))
                ivBigImage.visibility = View.VISIBLE
                Toast.makeText(this, "?????????????? ?????? ?????? ?????? ???? ??????????????", Toast.LENGTH_LONG).show()
            }
        }
        ivBigImage.setOnClickListener {
            ivBigImage.visibility = View.GONE
        }
    }

    private fun getIntents(){
        val i = intent
        if(i != null){
            if(i.getStringExtra(IntentConstants.SUBJECT_KEY) != null){
                id = i.getIntExtra(IntentConstants.ID, 0)
                isEditState = true
                tempImgUri = i.getStringExtra(IntentConstants.IMAGE_URI_KEY)!!
                etSubject.setText(i.getStringExtra(IntentConstants.SUBJECT_KEY))
                etTitle.setText(i.getStringExtra(IntentConstants.TITLE_KEY))
                etNote.setText(i.getStringExtra(IntentConstants.NOTE_KEY))
                if(i.getStringExtra(IntentConstants.IMAGE_URI_KEY) != "empty"){
                    imViewLayout.visibility = View.VISIBLE
                    ivMainImage.setImageURI(Uri.parse(i.getStringExtra(IntentConstants.IMAGE_URI_KEY)))
                    fabAddPicture.visibility = View.GONE
                }
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
            this.contentResolver.takePersistableUriPermission (data?.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

    }
}