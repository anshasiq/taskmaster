package com.example.taskmaster.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.taskmaster.Model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    public void insertATask(Task task);
    @Query("select * from Task")
    public List<Task> findAll();

    @Query("select * from Task ORDER BY title ASC")
    public List<Task> findAllSortedByName();

    @Query("select * from Task where id = :id")
    Task findByAnId(long id);

    @Query("select * from Task where title = :title")
    Task findByTitle(String title);


}
