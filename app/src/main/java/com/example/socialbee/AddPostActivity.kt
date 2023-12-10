package com.example.socialbee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.socialbee.daos.PostDao
import com.example.socialbee.daos.UserDao

class AddPostActivity : AppCompatActivity() {
    lateinit var postTxt:EditText
    lateinit var postBtn:Button
    lateinit var postDao: PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        postTxt = findViewById(R.id.textInputLayoutEditText)
        postBtn = findViewById(R.id.add_post)

        postBtn.setOnClickListener {
            val text = postTxt.text.toString().trim()
            if (text.isNotEmpty()){
                postDao.addPost(text)
            }
            finish()
        }
    }
}