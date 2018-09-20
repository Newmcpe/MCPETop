package ru.newmcpe.mcpetop;

import android.util.Log;

/**
 * Created by Newmcpe on 28.12.2017.
 */

public class Comment {

    private String name;
    private String comment;

    public Comment(String name, String comment) {

        this.name = name;
        this.comment = comment;
        Log.d("CommentAdd",name + " : " + comment);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
