package com.example.lenovo.te_ker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.te_ker.data.Attendance;
import com.example.lenovo.te_ker.data.Section;

import java.util.List;

public class AttendanceAdapater extends RecyclerView.Adapter<AttendanceAdapater.ViewHolder> {

    private Context context;
    private List<Attendance> list;

    public AttendanceAdapater(Context context, List<Attendance> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public AttendanceAdapater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.attendance_list, parent, false);
        return new AttendanceAdapater.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AttendanceAdapater.ViewHolder holder, int position) {
        Attendance attendance = list.get(position);
        holder.textViewAttendanceName.setText(attendance.getName());
        holder.textViewAttendance.setText(attendance.getTotal());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        public TextView textViewAttendance, textViewAttendanceName;
        public Button btnSend;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewAttendanceName = itemView.findViewById(R.id.textViewAttendanceName);
            textViewAttendance = itemView.findViewById(R.id.textViewAttendance);
            btnSend = itemView.findViewById(R.id.btnSend);
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int adapterPosition = getAdapterPosition();
                    Attendance attendance = list.get(adapterPosition);
                    String phone = attendance.getPhone();
                    Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("address",phone);
                    smsIntent.putExtra("sms_body","message");
                    context.startActivity(smsIntent);
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


        }
    }
}
