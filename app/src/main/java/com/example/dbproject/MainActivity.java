package com.example.dbproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DBHepler dbHepler;
    private EditText commentTitle, commentBody,
            commentTitleShow, commentBodyShow;
    private AppCompatButton sendButton,
            eliminateButton;

    private Spinner spinner;


    ArrayList<String> commentTitlesList = new ArrayList<>();
    ArrayList<CommentModel> comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHepler = new DBHepler(this);
        commentTitle = findViewById(R.id.commentTitle);
        commentTitle.setText("");
        commentBody = findViewById(R.id.commentBody);
        commentBody.setText("");
        commentTitleShow = findViewById(R.id.commentTitleShow);
        commentBodyShow = findViewById(R.id.commentBodyShow);
        sendButton = findViewById(R.id.sendButton);
        eliminateButton = findViewById(R.id.eliminateButton);
        spinner = findViewById(R.id.commentSelector);
        spinner.setOnItemSelectedListener(this);
        updateSpinner();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, commentTitlesList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(arrayAdapter);
        setButtonsListeners();


    }

    private void setButtonsListeners() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title =  commentTitle.getText().toString();
                if (title.equals("") ){
                    return;
                }
                String body = commentBody.getText().toString();
                Integer id;
                if (comments.size() == 0){
                    id = 1;
                }else{
                    id = Integer.parseInt(comments.get(comments.size() - 1).idNumber) + 1;
                }
                dbHepler.addComment(new CommentModel(
                        String.valueOf(id),
                        title,
                        body));
                updateSpinner();
                commentTitle.setText("");
                commentBody.setText("");

            }
        });





    }

    private void updateSpinner() {
        comments.clear();
        comments = dbHepler.getComments();
        updateCommentTitles();
    }

    private void updateCommentTitles() {
        commentTitlesList.clear();
        for (int i = 0; i < comments.size(); i++){
            commentTitlesList.add(comments.get(i).title);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        commentTitleShow.setText(comments.get(position).title);
        commentBodyShow.setText(comments.get(position).body);

    }




    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}