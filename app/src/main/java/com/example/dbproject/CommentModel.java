package com.example.dbproject;

public class CommentModel {
    public String idNumber = "-1";
    public String title = "commentariTitle";
    public String body = "commentariBody";
    public CommentModel(){}
    public CommentModel(String idNumber, String title, String body){
        this.idNumber = idNumber;
        this.title = title;
        this.body = body;
    }

}
