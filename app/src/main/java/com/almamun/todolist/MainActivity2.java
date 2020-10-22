package com.almamun.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.almamun.todolist.Modelclass.TaskModelClass;
import com.almamun.todolist.database.DatabaseHelperClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT2 = 2;
    public FloatingActionButton microphone;
    public TaskModelClass taskModelClass;
    public EditText editText1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.done){
            //            String text2 = editText1.getText().toString();
            if(editText1.getText().length() == 0){
                Toast.makeText(this,"Please Enter Your Task", Toast.LENGTH_SHORT).show();
            }else {
                TaskModelClass taskModelClass;
                    try {
                        taskModelClass = new TaskModelClass(-1, false, editText1.getText().toString() );
                        Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                        taskModelClass = new TaskModelClass(-1,false,"error");
                    }
                    DatabaseHelperClass databaseHelperClass = new DatabaseHelperClass(MainActivity2 .this);
                    boolean success = databaseHelperClass.addTask(taskModelClass);
                    startActivity(new Intent(MainActivity2.this, MainActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("New Task");
//        actionBar.setIcon(R.drawable.ic_baseline_done_outline_24);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);



//        mapping start here
        microphone = findViewById(R.id.microphone);
        editText1 = findViewById(R.id.editText1);






        microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "hi speak");
                try {
                    startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT2);

                }catch (Exception e) {
//                    Toast.makeText(MainActivity2.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }


        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_CODE_SPEECH_INPUT2:
                if (resultCode == RESULT_OK && null!=data){
                    ArrayList<String> result2 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    Toast.makeText(this, result2.get(0)+"hi mamun this is microphone inner function", Toast.LENGTH_SHORT).show();

                    editText1.setText(result2.get(0));

                }
                break;
        }
    }
}