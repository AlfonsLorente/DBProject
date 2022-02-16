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

    private int selectedComment = 1;

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
        comments = dbHepler.getComments();
        if (comments.size() == 0){
            dbHepler.addComment(new CommentModel("0", "Example comment",
                    "Example body"));
        }
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
                    Toast.makeText(getApplicationContext(),
                            "No title found",
                            Toast.LENGTH_SHORT).show();
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


        eliminateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = commentTitleShow.getText().toString();
                if (title.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "No commentari selected",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                dbHepler.removeComment(comments.get(selectedComment).idNumber);
                updateSpinner();
                spinner.setSelection(commentTitlesList.size()-1);
                commentTitleShow.setText("");
                commentBodyShow.setText("");


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
        selectedComment = position;

    }




    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}