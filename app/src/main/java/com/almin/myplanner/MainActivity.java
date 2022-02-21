package com.almin.myplanner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.almin.myplanner.entity.Task;
import com.almin.myplanner.service.DataBaseHelper;
import com.almin.myplanner.ui.AddActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private MyAdapter myAdapter;

    private List<Task> listOfTask;
    List<Task> listOfActiveTask;
    DataBaseHelper myDataBase;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingButt);
        floatingActionButton.setOnClickListener((v) -> {

            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);

        });
        myDataBase = new DataBaseHelper(MainActivity.this);
        listOfTask = myDataBase.findALl();

        listOfActiveTask = listOfTask.stream()
                .filter(task -> task.isActive() == true)
                .collect(Collectors.toList());
        Collections.reverse(listOfActiveTask);
        Collections.reverse(listOfTask);// i want my new added task to be on top so i need reverse list

        myAdapter = new MyAdapter(MainActivity.this, listOfActiveTask);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.todoListMenu:

                myAdapter = new MyAdapter(MainActivity.this, listOfActiveTask);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                return true;

            case R.id.allListMenu:

                myAdapter = new MyAdapter(MainActivity.this, listOfTask);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}