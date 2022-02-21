package com.almin.myplanner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.almin.myplanner.entity.Task;
import com.almin.myplanner.ui.Update_Task;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.TaskHolder> {

    private final Context context;
    private List<Task> listOfTask;

    public MyAdapter(Context context, List<Task> listOfTask) {
        this.context = context;
        this.listOfTask = listOfTask;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_task, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        Task task = listOfTask.get(position);
        holder.setDetails(task);

        holder.cardLayout.setOnClickListener((view) -> {
            Intent intent = new Intent(context, Update_Task.class);
            //putting all inf. about my Task and transfer
            intent.putExtra("taskName", task.getTaskName());
            intent.putExtra("taskDate", task.getDate());
            intent.putExtra("isActive", task.isActive());
            intent.putExtra("id", task.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listOfTask.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder {

        private final TextView txtTaskName, txtTaskDate, txtTaskIsActive;
        LinearLayout cardLayout;

        TaskHolder(View view) {
            super(view);
            txtTaskName = view.findViewById(R.id.txtTaskName);
            txtTaskDate = view.findViewById(R.id.txtTaskDate);
            txtTaskIsActive = view.findViewById(R.id.txtIsActiv);
            cardLayout = view.findViewById(R.id.cardLayout);
        }

        void setDetails(Task task) {

            txtTaskName.setText(task.getTaskName());
            txtTaskDate.setText(task.getDate());
            boolean isActive = task.isActive();
            if (isActive) {
                txtTaskIsActive.setText("Active");
            } else {
                txtTaskIsActive.setText("Completed");
            }

        }
    }
}
