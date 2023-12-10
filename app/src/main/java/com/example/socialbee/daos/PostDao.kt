package com.example.socialbee.daos

import com.example.socialbee.models.Post
import com.example.socialbee.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
    val db = FirebaseFirestore.getInstance()
    val postsCollection  = db.collection("posts")

    val auth = Firebase.auth

    fun addPost(text:String){
        val currentUser = auth.currentUser!!.uid
        GlobalScope.launch {
            val userDao = UserDao()
            val user = userDao.getUser(currentUser).await().toObject(User::class.java)!!
            val currentTime = System.currentTimeMillis()
            val post = Post(text, user, currentTime)
            postsCollection.document().set(post)
        }
    }

    fun getPostById(id: String):Task<DocumentSnapshot>{
        return postsCollection.document(id).get()
    }

    fun updateLikes(id:String){
        GlobalScope.launch {
            val currentUser = auth.currentUser!!.uid
            val post = getPostById(id).await().toObject(Post::class.java)!!
            val isLiked = post.likes.contains(currentUser)
            if (isLiked){
                post.likes.remove(currentUser)
            } else {
                post.likes.add(currentUser)
            }
            postsCollection.document(id).set(post)
        }
    }
}