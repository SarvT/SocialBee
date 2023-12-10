package com.example.socialbee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialbee.daos.PostDao
import com.example.socialbee.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity(), IPostRVAdapter {

    lateinit var add_btn:FloatingActionButton
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PostRVAdapter
    lateinit var postDao: PostDao
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_btn = findViewById(R.id.add_post)
        recyclerView = findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(this)
        add_btn.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java)
            startActivity(intent)
        }

    }
    fun setupRV(){
        postDao = PostDao()
        val postCollection = postDao.postsCollection
        val query = postCollection.orderBy("timestamp", Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()

        adapter = PostRVAdapter(recyclerViewOptions, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikeBtnClicked(id: String) {
        postDao.updateLikes(id)
    }

}