package com.almamun.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.almamun.todolist.Modelclass.TaskModelClass;
import com.almamun.todolist.Util.RecyclerTouchListener;
import com.almamun.todolist.database.DatabaseHelperClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
//    here declaring start
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    public FloatingActionButton add;
    public EditText editText;
    public ImageButton microphone,single_done1;
    public RecyclerView recyclerView;
    public List<TaskModelClass> storeAllData;
    public DatabaseHelperClass databaseHelperClass;
    public ItemTouchHelper.Callback itemTouchHelperCallback;
    private MenuItem item;
    private static long back_pressed;
    public Update update;




    //    inflate section here
    @Override
public boolean onCreateOptionsMenu(Menu menu) {

    getMenuInflater().inflate(R.menu.main, menu);
    return super.onCreateOptionsMenu(menu);
    }




//       inflate onIptionsItemSelected section
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.it_followMe){
            Uri uri = Uri.parse("https://www.youtube.com/channel/UCCbf6AFt6bvKm5II16kBGig");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
        else if(id == R.id.it_search){
            Toast.makeText(this, "All items are sorted perfectly in ascending order" , Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.it_dropdown){
            Toast.makeText(this, "Drop Down menu will work in next update...", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.it_settings){
            Toast.makeText(this, "Everything is set perfectly", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.it_taskLists){
            Toast.makeText(this, "There is no Task List Yet!!!", Toast.LENGTH_SHORT).show();
        }
        else{}
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" All Lists");
        actionBar.setIcon(R.drawable.ic_baseline_done_outline_24);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);




//        here mapping start
        add = findViewById(R.id.add);
        microphone = findViewById(R.id.microphone);
        editText = findViewById(R.id.editText);
        single_done1 = findViewById(R.id.single_done1);
        recyclerView = findViewById(R.id.recyclerView);



//        layout_manager information here
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);




//        database information portion here
        storeAllData = new ArrayList<>();
        databaseHelperClass = new DatabaseHelperClass(MainActivity.this);
        storeAllData = databaseHelperClass.getTaskList();
//        Toast.makeText(this, storeAllData.toString(), Toast.LENGTH_SHORT).show();
//        assert storeAllData != null;
        if(storeAllData != null){
            final TaskAdapterClass taskAdapterClass = new TaskAdapterClass(storeAllData,MainActivity.this);
            recyclerView.setAdapter(taskAdapterClass);
            taskAdapterClass.notifyDataSetChanged();
//            ItemTouchHelper.UP|ItemTouchHelper.DOWN
            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                    final int fromPosition = viewHolder.getAdapterPosition();
//                    final int toPosition = target.getAdapterPosition();
//
//                    Toast.makeText(MainActivity.this, String.valueOf(fromPosition), Toast.LENGTH_SHORT).show();
//                    Collections.swap(taskAdapterClass.alltask, fromPosition, toPosition);
//                    taskAdapterClass.notifyDataSetChanged();
//                    taskAdapterClass.notifyItemMoved(fromPosition, toPosition);


                    return false;

                }
                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                long id = viewHolder.getAdapterPosition();
                    if(direction == ItemTouchHelper.RIGHT){
                        try {
//                        int idi = (int)viewHolder.itemView.getTag();
                            int position = viewHolder.getAdapterPosition();
                            taskAdapterClass.delete(position);
//                        storeAllData.remove(position);
                            String a = String.valueOf(position);
                            storeAllData = databaseHelperClass.getTaskList();
                            if(storeAllData != null){
                                TaskAdapterClass taskAdapterClass = new TaskAdapterClass(storeAllData,MainActivity.this);
                                recyclerView.setAdapter(taskAdapterClass);
                                //                        taskAdapterClass.setTasks(storeAllData);
                                taskAdapterClass.notifyDataSetChanged();
//                            taskAdapterClass.notifyItemRemoved(position);
                            }else{
                                Toast.makeText(update, "There is no data", Toast.LENGTH_SHORT).show();
                            }
//                        databaseHelperClass.deleteTask(position);
//                        storeAllData.remove(position);
                            taskAdapterClass.notifyDataSetChanged();
                            taskAdapterClass.notifyItemRemoved(position);
                            Toast.makeText(MainActivity.this,"Task Deleted", Toast.LENGTH_SHORT).show();

                        }catch (Exception e){
                            Toast.makeText(MainActivity.this,"Some problem occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(direction == ItemTouchHelper.LEFT){
                        Toast.makeText(MainActivity.this, storeAllData.get(2).getTasktext().toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Update mode activated", Toast.LENGTH_SHORT).show();
                        int position = viewHolder.getAdapterPosition();
                        TaskModelClass taskModelClass;
                        taskModelClass = storeAllData.get(position);
                        String message = taskModelClass.getTasktext();
                        String id = String.valueOf(taskModelClass.getId());
                        String pass = "mamun";
                        Intent i = new Intent(MainActivity.this, Update.class);
                        i.putExtra("k1",message);
                        i.putExtra("k2",storeAllData.get(position).getId());
                        i.putExtra("k3",pass);
                        storeAllData = databaseHelperClass.getTaskList();
                        if(storeAllData != null){
                            TaskAdapterClass taskAdapterClass = new TaskAdapterClass(storeAllData,MainActivity.this);
                            recyclerView.setAdapter(taskAdapterClass);
                            //                        taskAdapterClass.setTasks(storeAllData);
                            taskAdapterClass.notifyDataSetChanged();
//                            taskAdapterClass.notifyItemRemoved(position);
                        }else{
                            Toast.makeText(update, "There is no data", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(i);
                    }
                    else {

                    }

                }
            }).attachToRecyclerView(recyclerView);
        }else{
            Toast.makeText(this,"There is no data", Toast.LENGTH_SHORT).show();
        }




        //      click listener information here
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                TaskModelClass taskModelClass;
                taskModelClass = storeAllData.get(position);
                databaseHelperClass.updateCheck(taskModelClass);
                storeAllData = databaseHelperClass.getTaskList();
                if(storeAllData != null){
                    TaskAdapterClass taskAdapterClass = new TaskAdapterClass(storeAllData,MainActivity.this);
                    recyclerView.setAdapter(taskAdapterClass);
                    //                        taskAdapterClass.setTasks(storeAllData);
                    taskAdapterClass.notifyDataSetChanged();
                }else{
                    Toast.makeText(update, "There is no data", Toast.LENGTH_SHORT).show();
                }
                taskModelClass = storeAllData.get(position);

//                Toast.makeText(MainActivity.this,taskModelClass.toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View view, int position) {
//                Toast.makeText(MainActivity.this, position+"Long click successful", Toast.LENGTH_SHORT).show();

            }
        }));




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txt = editText.getText().toString().trim();
                microphone.setVisibility(View.GONE);
                single_done1.setVisibility(View.VISIBLE);
                if(txt.isEmpty()){
                    microphone.setVisibility(View.VISIBLE);
                    single_done1.setVisibility(View.GONE);
                }

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        single_done1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskModelClass taskModelClass;
                if(editText.getText().length() == 0){
                    Toast.makeText(MainActivity.this, "Please Enter Your Task First", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        taskModelClass = new TaskModelClass(-1, false, editText.getText().toString() );
                        Toast.makeText(MainActivity.this, "Task added", Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        taskModelClass = new TaskModelClass(-1,false,"error");
                    }
                    databaseHelperClass = new DatabaseHelperClass(MainActivity.this);
                    databaseHelperClass.addTask(taskModelClass);
                    storeAllData = databaseHelperClass.getTaskList();
                    if(storeAllData != null){
                        TaskAdapterClass taskAdapterClass = new TaskAdapterClass(storeAllData,MainActivity.this);
                        recyclerView.setAdapter(taskAdapterClass);
                        //                        taskAdapterClass.setTasks(storeAllData);
                        taskAdapterClass.notifyDataSetChanged();

                    }else{
                    }
//            Toast.makeText(this, "success = " + success, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                }
            }
        });

        microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "hi speak");
                try {
                    startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);

                }catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Microphone is not working", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
}




//declaration here
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_CODE_SPEECH_INPUT:
                if (resultCode == RESULT_OK && null!=data){
                ArrayList<String> result1 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Toast.makeText(this, result1.get(0)+"hi mamun this is microphone inner function", Toast.LENGTH_SHORT).show();

                editText.setText(result1.get(0));

            }
                break;
        }
    }







}