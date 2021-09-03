package com.example.studnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_notes_editor.*

class NotesEditor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_editor)
        fabAddPicture.setOnClickListener { imViewLayout.visibility = View.VISIBLE }
        ibDeleteImg.setOnClickListener{
            val anim : Animation= AnimationUtils.loadAnimation(this, R.anim.click)
            ibDeleteImg.startAnimation(anim)
            imViewLayout.visibility = View.GONE
        }
    }
}