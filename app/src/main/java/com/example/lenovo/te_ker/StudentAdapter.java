package com.example.lenovo.te_ker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.te_ker.data.Section;
import com.example.lenovo.te_ker.data.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private Context context;
    private List<Student> list;

    public StudentAdapter(Context context, List<Student> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.student_list, parent, false);
        return new StudentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StudentAdapter.ViewHolder holder, int position) {
        Student student = list.get(position);
        holder.textViewStudentName.setText(student.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener {
        public TextView textViewStudentName;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewStudentName = itemView.findViewById(R.id.textViewStudentName);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            int adapterPosition = getAdapterPosition();
            Student student = list.get(adapterPosition);
            String person_id = student.getPerson_id();
            String name = student.getName();
            Intent intent = new Intent(context, StudentImageActivity.class);
            intent.putExtra("person_id", person_id);
            intent.putExtra("name", name);
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            Context context = v.getContext();
            int adapterPosition = getAdapterPosition();
            Student student = list.get(adapterPosition);
            String student_id = student.getID();
            String name = student.getName();
            String email = student.getEmail();
            String parent_name = student.getParent_name();
            String parent_number = student.getParent_number();
            String status = student.getStatus();
            Intent intent = new Intent(context, EditStudentActivity.class);
            intent.putExtra("student_id", student_id);
            intent.putExtra("name", name);
            intent.putExtra("email", email);
            intent.putExtra("parent_name", parent_name);
            intent.putExtra("parent_number", parent_number);
            intent.putExtra("status", status);
            context.startActivity(intent);
            return true;
        }
    }
}
