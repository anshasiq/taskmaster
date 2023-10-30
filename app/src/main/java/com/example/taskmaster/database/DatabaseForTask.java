package com.example.taskmaster.database;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.taskmaster.Model.Task;
import com.example.taskmaster.dao.TaskDao;

@Database(entities = {Task.class}, version = 1)
@TypeConverters({TaskDatabaseConverters.class})

public abstract class DatabaseForTask extends RoomDatabase {
    public abstract TaskDao taskDao();
}
