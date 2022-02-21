package com.almin.myplanner.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.almin.myplanner.service.DataBaseHelper;
import com.almin.myplanner.MainActivity;
import com.almin.myplanner.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Calendar;

public class Update_Task extends AppCompatActivity {
    private EditText textNameTask_update;
    private EditText textDateTask_update;
    private SwitchMaterial switch_update;
    private ImageButton calendarButton_update;
    private Button buttonUpdate;
    private Button buttonDelete;

    int idTask;
    String taskName;
    String taskDate;
    boolean isActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        textNameTask_update = findViewById(R.id.editTextTaskName_update);
        textDateTask_update = findViewById(R.id.editTextDate_udpdate);
        switch_update = findViewById(R.id.switch1_update);
        buttonUpdate = findViewById(R.id.button_update);
        buttonDelete = findViewById(R.id.buttonDelete);
        calendarButton_update = findViewById(R.id.imageButtonCalendar_update);

        getIntentDate(); //so at this point,i have ID of OBJ,actually i have all field of OBJ

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(taskName);

        calendarButton_update.setOnClickListener(v -> {
            calendarClicker();
        });

        buttonUpdate.setOnClickListener((v) -> {
            updateTask();
            backToMainActivity();
        });

        buttonDelete.setOnClickListener(v -> {
            DataBaseHelper myDataBase = new DataBaseHelper(Update_Task.this);
            myDataBase.deleteTask(idTask);
            backToMainActivity();

        });

    }

    public void updateTask() {

        DataBaseHelper myDataBase = new DataBaseHelper(this);
        taskName = textNameTask_update.getText().toString().trim();
        taskDate = textDateTask_update.getText().toString().trim();
        isActive = switch_update.isChecked();

        myDataBase.updateData(idTask, taskName, taskDate, isActive);

    }

    public void getIntentDate() {

        if (getIntent().hasExtra("taskName") && getIntent().hasExtra("taskDate")
                && getIntent().hasExtra("isActive")) {
            //im taking date here
            taskName = getIntent().getStringExtra("taskName");
            taskDate = getIntent().getStringExtra("taskDate");
            isActive = getIntent().getBooleanExtra("isActive", true);
            idTask = getIntent().getIntExtra("id", 0);//im taking id from OBJ

            //and setting here
            textNameTask_update.setText(taskName);
            textDateTask_update.setText(taskDate);
            switch_update.setChecked(isActive);

        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    public void calendarClicker() {
        int tDate, tMonth, tYear;
        final Calendar calendar = Calendar.getInstance();
        tDate = calendar.get(Calendar.DATE);
        tMonth = calendar.get(Calendar.MONDAY);
        tYear = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Update_Task.this, android.R.style.Theme_DeviceDefault_Dialog,
                (datePicker, year, month, date) ->
                        textDateTask_update.setText(date + "-" + (month + 1) + "-" + year), tDate, tMonth, tYear);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000000000);
        datePickerDialog.show();
    }

    public void backToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}