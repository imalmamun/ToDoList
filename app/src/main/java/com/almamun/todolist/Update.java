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
import java.util.Locale;

public class Update extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT2 = 2;
//    public static final String MESSAGE_KEY = "hi";
    public FloatingActionButton microphone;
    public EditText editText1;

    public TaskModelClass taskModelClass;
    public DatabaseHelperClass databaseHelperClass;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent ii = getIntent();
//        String value1 = ii.getStringExtra("k1");
        String value2 = ii.getStringExtra("k2");
//        String value3 = ii.getStringExtra("k3");
//        Bundle a = ii.getExtras("k2");
        Bundle k = ii.getExtras();

        int m = (int) k.get("k2");

        int id = item.getItemId();
        if(id==R.id.done){

//            String text2 = editText1.getText().toString();
            String l = editText1.getText().toString();

            if(editText1.getText().length() == 0){
                Toast.makeText(this,"Please Enter Your Task", Toast.LENGTH_SHORT).show();

            }else{

                TaskModelClass taskModelClass;
                try {
                    taskModelClass = new TaskModelClass(m, false, editText1.getText().toString());
//                    Toast.makeText(this, taskModelClass.toString(), Toast.LENGTH_SHORT).show();
                    DatabaseHelperClass databaseHelperClass = new DatabaseHelperClass(Update.this);
                    databaseHelperClass.updateValue(taskModelClass);
                    startActivity(new Intent(Update.this, MainActivity.class));
                    Toast.makeText(this,"Successfully Updated", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                    taskModelClass = new TaskModelClass(-1,false,"error");
                }
//            Toast.makeText(this, "success = " + success, Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }















    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("New Task");
//        actionBar.setIcon(R.drawable.ic_baseline_done_outline_24);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);
        microphone = findViewById(R.id.microphone);
        editText1 = findViewById(R.id.editText1);
//        editText1.setText(taskModelClass.getTasktext());
//        Intent ii = getIntent();
//        Bundle b = ii.getExtras();
//        String text = (String) b.get("MESSAGE_KEY");
//        editText1.setText(text);
//        databaseHelperClass = new DatabaseHelperClass(this);
        Intent ii = getIntent();
        String value1 = (String)ii.getStringExtra("k1");
        String value2 = ii.getStringExtra("k2");
//        String value3 = ii.getStringExtra("k3");
        Bundle k = ii.getExtras();

        int m = (int) k.get("k2");
//        int a = Integer.parseInt(value2);
        editText1.setText(value1);
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
                    Toast.makeText(Update.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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