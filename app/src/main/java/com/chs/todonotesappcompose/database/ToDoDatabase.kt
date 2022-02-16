package com.chs.todonotesappcompose.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chs.todonotesappcompose.database.model.ToDoTask

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase: RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}