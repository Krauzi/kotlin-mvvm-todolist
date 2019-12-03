package com.example.toodoo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Todo::class], version = 3)
@TypeConverters(Converter::class)
abstract class TodoDataBase : RoomDatabase() {
    abstract fun todoDao(): TodoDAO

    companion object {
        private var INSTANCE: TodoDataBase? = null

        fun getInstance(context: Context): TodoDataBase? {
            if (INSTANCE == null) {
                // synchronized - only one thread at the time can access that instance
                synchronized(TodoDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TodoDataBase::class.java, "todo_db").fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

//    private val CALLBACK = object : RoomDatabase.Callback() {
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//
//            PopulateDbAT(INSTANCE!!).execute()
//        }
//    }
//
//    private class PopulateDbAT(td_db: TodoDataBase) : AsyncTask<Unit, Unit, Unit>() {
//        private var todoDao: TodoDAO = td_db.todoDao()
//
//        var date = Date()
//        override fun doInBackground(vararg params: Unit?) {
//            todoDao.insertTodo(Todo("Tytuł 1", 2, 0, date))
//            todoDao.insertTodo(Todo("Tytuł 2", 1, 1, date))
//            todoDao.insertTodo(Todo("Tytuł 3", 2, 0, date))
//        }
//    }
}