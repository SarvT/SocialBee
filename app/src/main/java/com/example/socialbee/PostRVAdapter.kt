package com.example.socialbee

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialbee.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PostRVAdapter(options: FirestoreRecyclerOptions<Post>, val listener: IPostRVAdapter) :
    FirestoreRecyclerAdapter<Post, PostRVAdapter.PostRVVHolder>(options) {
    class PostRVVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val likeBtn:ImageButton =itemView.findViewById(R.id.likeBtn)
        val textDesc:TextView = itemView.findViewById(R.id.postText)
        val textTitle:TextView = itemView.findViewById(R.id.postTitle)
        val textTime:TextView = itemView.findViewById(R.id.timestamp)
        val textLikeCnt:TextView = itemView.findViewById(R.id.likeCnt)
        val textUsername:TextView = itemView.findViewById(R.id.userName)
        val imgUser:ImageView = itemView.findViewById(R.id.userImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostRVVHolder {
        val itemView = PostRVVHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_recycler_view_item, parent, false))

        itemView.likeBtn.setOnClickListener {
            listener.onLikeBtnClicked(snapshots.getSnapshot(itemView.adapterPosition).id)
        }
        return itemView
    }

    override fun onBindViewHolder(holder: PostRVVHolder, position: Int, model: Post) {
        holder.textDesc.text = model.text
        holder.textUsername.text = model.author.toString()
        holder.textTime.text = Utils.getTimeAgo(model.timeStamp)
//        holder.imgUser.setImageBitmap(BitmapFactory.decodeFile(model.author.img))
        holder.textLikeCnt.text = model.likes.size.toString()
        Glide.with(holder.imgUser.context).load(model.author.img).circleCrop().into(holder.imgUser)

        val auth = Firebase.auth
        val currentUser = auth.currentUser!!.uid
        val isLiked = model.likes.contains(currentUser)
        if (isLiked){
            holder.likeBtn.setImageDrawable(ContextCompat.getDrawable(holder.likeBtn.context, R.drawable.icon_liked))
        } else {
            holder.likeBtn.setImageDrawable(ContextCompat.getDrawable(holder.likeBtn.context, R.drawable.icon_unliked))

        }
    }
}
interface IPostRVAdapter{
    fun onLikeBtnClicked(id:String)
}