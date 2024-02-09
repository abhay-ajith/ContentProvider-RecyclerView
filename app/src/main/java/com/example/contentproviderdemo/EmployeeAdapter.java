package com.example.contentproviderdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private List<EmployeeData> data;
    private Context context;

    public EmployeeAdapter(List<EmployeeData> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmployeeData employee = data.get(position);

        holder.nameTextView.setText(employee.getName());
        holder.idTextView.setText(String.valueOf(employee.getId()));
        holder.designationTextView.setText(employee.getDesignation());
        holder.salaryTextView.setText(String.valueOf(employee.getSalary()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<EmployeeData> newData) {
        data.clear();
        data.addAll(newData);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, idTextView, designationTextView, salaryTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.employee_name);
            idTextView = itemView.findViewById(R.id.employee_id);
            designationTextView = itemView.findViewById(R.id.employee_designation);
            salaryTextView = itemView.findViewById(R.id.employee_salary);
        }
    }
}
