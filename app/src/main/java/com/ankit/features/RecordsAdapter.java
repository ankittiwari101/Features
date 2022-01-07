package com.ankit.features;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.ViewHolder> {

    List<RecordPojo> recordPojoList;
    Context context;
    private static final String TAG = "RecordsAdapter";
    DBHelper dbHelper;

    public RecordsAdapter(List<RecordPojo> pojoList, Context context){
        this.recordPojoList = pojoList;
        this.context = context;
        dbHelper = new DBHelper(context);
    }


    @NonNull
    @Override
    public RecordsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_element,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordsAdapter.ViewHolder holder, int position) {
        RecordPojo pojo = recordPojoList.get(position);
        holder.name.setText(pojo.getName()+" "+pojo.getSurname());
        holder.jobTitle.setText(pojo.getJobTitle());
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Button clicked");
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.form);
                EditText name = dialog.findViewById(R.id.et_name);
                EditText surname = dialog.findViewById(R.id.et_surname);
                EditText jobTitle = dialog.findViewById(R.id.et_jobTitle);
                AppCompatButton button = dialog.findViewById(R.id.btn_submit);

                name.setText(pojo.getName());
                surname.setText(pojo.getSurname());
                jobTitle.setText(pojo.getJobTitle());

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(name.getText().toString().trim().isEmpty()){
                            name.setError("Name must not be empty");
                            return;
                        }
                        else if(surname.getText().toString().trim().isEmpty()){
                            surname.setError("Surname must not be empty");
                            return;
                        }
                        else if(jobTitle.getText().toString().trim().isEmpty()){
                            jobTitle.setError("Job title must not be empty");
                            return;
                        }
                        boolean success = dbHelper.updateData(pojo.getEmpId(), name.getText().toString(),surname.getText().toString(),jobTitle.getText().toString());
                        if(success){
                            //recreate();
                            /*allRecords = fetchRecords();
                            myAdapter.notifyDataSetChanged();*/
                            updateRecordWhere(pojo.getEmpId(),name.getText().toString(),surname.getText().toString(),jobTitle.getText().toString());
                            notifyDataSetChanged();
                            Toast.makeText(context,"Record Updated Successfully",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(context,"Something went wrong.Please try again later",Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
                int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.90);
                int height = (int)(context.getResources().getDisplayMetrics().heightPixels*0.45);
                dialog.getWindow().setLayout(width,height);
                dialog.show();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Alert").setMessage("Are you sure you want to delete this record??");
                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int result =  dbHelper.deleteRecord(pojo.getEmpId());
                        if(result > 0){
                            deleteRecordWhere(pojo.getEmpId());
                            notifyDataSetChanged();
                            Toast.makeText(context,"Record Deleted Successfully",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context,"Something went wrong.Please try again later",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    private void updateRecordWhere(String empId,String name,String surname,String jobTitle){
        for(RecordPojo pojo : recordPojoList){
            if(pojo.getEmpId().equalsIgnoreCase(empId)){
                pojo.setName(name);
                pojo.setSurname(surname);
                pojo.setJobTitle(jobTitle);
                break;
            }
        }
    }

    private void deleteRecordWhere(String empId){
        RecordPojo recordPojo = null;
        for(RecordPojo pojo : recordPojoList){
            if(pojo.getEmpId().equalsIgnoreCase(empId)){
                recordPojo = pojo;
                break;
            }
        }
        recordPojoList.remove(recordPojo);
    }


    @Override
    public int getItemCount() {
        return recordPojoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,jobTitle,delete;
        ImageView editBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            jobTitle = itemView.findViewById(R.id.job_title);
            delete = itemView.findViewById(R.id.tv_item_delete);
            editBtn = itemView.findViewById(R.id.btn_editRecord);
        }
    }
}
