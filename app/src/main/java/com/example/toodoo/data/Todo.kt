package com.example.toodoo.data

import android.os.Parcel
import android.os.Parcelable
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "todo_table")
data class Todo(var title: String,
                var priority: Int,
                var status: Int,
                var date: Date,
                var comment: String? = "" ) : Serializable
{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}