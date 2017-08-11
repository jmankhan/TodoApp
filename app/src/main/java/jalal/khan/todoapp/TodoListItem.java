package jalal.khan.todoapp;

import android.support.annotation.NonNull;

/**
 * Created by Jalal on 8/1/2017.
 */

public class TodoListItem  {

    private long id;
    private String text;
    private boolean favorite;
    private Priority priority;

    public TodoListItem(String text) {
        setText(text);
        setFavorite(favorite);
        setPriority(Priority.LOW);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if(text == null) {
            this.text = "";
        } else {
            this.text = text;
        }
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
