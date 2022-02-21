package com.almin.myplanner.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.almin.myplanner.service.DataBaseHelper;
import com.almin.myplanner.MainActivity;
import com.almin.myplanner.R;
import com.almin.myplanner.entity.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import java.util.Calendar;


public class AddActivity extends AppCompatActivity {

    private EditText taskNameText;
    private EditText dateTaskText;
    private SwitchMaterial buttonSwitch;
    private ImageButton calendarButton;

    Button buttonAdd;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        taskNameText = findViewById(R.id.editTextTaskName);
        dateTaskText = findViewById(R.id.editTextDate);
        calendarButton = findViewById(R.id.imageButtonCalendar);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonSwitch = findViewById(R.id.switch1);

        dataBaseHelper = new DataBaseHelper(AddActivity.this);

        calendarButton.setOnClickListener(v -> openCalendar());
        buttonAdd.setOnClickListener(v -> {
            addButtonClicker();
        });

    }

    public void openCalendar() {
        int tDate, tMonth, tYear;
        final Calendar calendar = Calendar.getInstance();
        tDate = calendar.get(Calendar.DATE);
        tMonth = calendar.get(Calendar.MONDAY);
        tYear = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddActivity.this, android.R.style.Theme_DeviceDefault_Dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        dateTaskText.setText(date + "-" + (month + 1) + "-" + year);
                    }
                }, tYear, tMonth, tYear);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000000000);
        datePickerDialog.show();
    }

    public void addButtonClicker() {
        Task task;

        if (isEmptyEditText(taskNameText) || isEmptyEditText(dateTaskText)) {
            Toast.makeText(AddActivity.this, "Name and Date is Require", Toast.LENGTH_SHORT).show();
        } else {
            task = new Task(
                    taskNameText.getText().toString().trim(),//prevent blank space at beginning
                    dateTaskText.getText().toString().trim(),
                    buttonSwitch.isChecked()
            );
            boolean insetSuccess = dataBaseHelper.addTask(task);
            if (insetSuccess) {
                Toast.makeText(AddActivity.this, "Add Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
            backToMainActivity();
        }
    }

    public void backToMainActivity() {
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public boolean isEmptyEditText(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

}