package com.almamun.todolist;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.almamun.todolist.Modelclass.TaskModelClass;
import com.almamun.todolist.database.DatabaseHelperClass;

import java.util.List;

public class TaskAdapterClass extends RecyclerView.Adapter<TaskAdapterClass.ViewHolder> {
    List<TaskModelClass> alltask;
    Context context;
    DatabaseHelperClass databaseHelperClass;

    public TaskAdapterClass(List<TaskModelClass> storeAllData, MainActivity mainActivity) {

        this.alltask = storeAllData;
        this.context = mainActivity;
        databaseHelperClass = new DatabaseHelperClass(context);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.work_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TaskModelClass taskModelClass = alltask.get(position);

        holder.checkBox.setText(taskModelClass.getTasktext());
        if(taskModelClass.getStatus() == true){
            holder.checkBox.setChecked(true);
            holder.checkBox.setPaintFlags(holder.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else if(taskModelClass.getStatus() == false){
            holder.checkBox.setChecked(false);
            holder.checkBox.setPaintFlags(holder.checkBox.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        else{

        }





    }

    @Override
    public int getItemCount() {
        return alltask.size();
    }
    public void delete(int positon){
        TaskModelClass taskModelClass = alltask.get(positon);
        int id = taskModelClass.getId();
        databaseHelperClass.deleteTask(id);
//        Toast.makeText(context, String.valueOf(id), Toast.LENGTH_SHORT).show();
        this.notifyItemRemoved(positon);
        alltask.remove(positon);
    }
    public void placeIT(int fposition, int lposition){

    }
//    public void setTasks(List<TaskModelClass> storeAllData){
//        this.alltask = storeAllData;
//        notifyDataSetChanged();
//
//    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        DatabaseHelperClass databaseHelperClass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
//            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if(isChecked){
//                        TaskModelClass taskModelClass = ;
//
//                        databaseHelperClass.delete(taskModelClass);
//
//                    }
//                }
//            });

        }
    }
}
