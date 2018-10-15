package com.example.lenovo.te_ker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.te_ker.data.Section;

import java.util.List;

/**
 * Created by ankit on 27/10/17.
 */

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {

    private Context context;
    private List<Section> list;

    public SectionAdapter(Context context, List<Section> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.section_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Section section = list.get(position);
        holder.textViewSectionName.setText(section.getName());
        holder.textViewSectionSubject.setText(section.getSubject());
        holder.textViewSectionStartTime.setText(section.getStart_time());
        holder.textViewSectionEndTime.setText(section.getEnd_time());
        holder.textViewSectionRoom.setText(section.getRoom());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewSectionName, textViewSectionSubject, textViewSectionStartTime, textViewSectionEndTime, textViewSectionRoom;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewSectionName = itemView.findViewById(R.id.textViewSectionName);
            textViewSectionSubject = itemView.findViewById(R.id.textViewSectionSubject);
            textViewSectionStartTime = itemView.findViewById(R.id.textViewSectionStartTime);
            textViewSectionEndTime = itemView.findViewById(R.id.textViewSectionEndTime);
            textViewSectionRoom = itemView.findViewById(R.id.textViewSectionRoom);
        }
    }

}