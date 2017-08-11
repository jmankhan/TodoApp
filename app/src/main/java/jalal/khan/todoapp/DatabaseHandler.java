package jalal.khan.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jalal on 8/1/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "todoList";

    private static final String TABLE_TODO_ITEMS = "todoItems";

    private static final String _ID = "id";
    private static final String _NAME = "name";
    private static final String _FAVORITE = "favorite";
    private static final String _PRIORITY = "priority";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TODO_ITEMS + "("
                + _ID + " INTEGER PRIMARY KEY,"
                + _NAME + " TEXT,"
                + _FAVORITE + " INTEGER,"
                + _PRIORITY + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_ITEMS);
        onCreate(db);
    }

    public void insertTodoItem(TodoListItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(_NAME, item.getText());
        values.put(_FAVORITE, item.isFavorite());
        values.put(_PRIORITY, item.getPriority().name());

        long id = db.insert(TABLE_TODO_ITEMS, null, values);
        item.setId(id);
        db.close();
    }


    public TodoListItem getTodoItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TODO_ITEMS,
                new String[] {_ID, _NAME, _FAVORITE },
                _ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String name = cursor.getString(cursor.getColumnIndex(_NAME));
        boolean favorite = cursor.getInt(cursor.getColumnIndex(_FAVORITE)) == 1;
        String priorityName = cursor.getString(cursor.getColumnIndex(_PRIORITY));

        TodoListItem item = new TodoListItem(name);
        item.setFavorite(favorite);
        item.setPriority(Priority.valueOf(priorityName));

        db.close();
        return item;
    }

    public ArrayList<TodoListItem> getAllTodoItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + _NAME + ", " + _FAVORITE + ", " + _PRIORITY + " FROM " + TABLE_TODO_ITEMS, null);
        ArrayList<TodoListItem> items = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex(_NAME));
                boolean favorite = cursor.getInt(cursor.getColumnIndex(_FAVORITE)) == 1;
                String priorityName = cursor.getString(cursor.getColumnIndex(_PRIORITY));
                TodoListItem item = new TodoListItem(name);
                item.setFavorite(favorite);
                item.setPriority(Priority.valueOf(priorityName));
                items.add(item);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return items;
    }

    public boolean updateTodoItem(TodoListItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(_ID, item.getId());
        cv.put(_NAME, item.getText());
        cv.put(_FAVORITE, item.isFavorite());
        cv.put(_PRIORITY, item.getPriority().name());

        String[] args = new String[]{Long.toString(item.getId())};
        boolean result = db.update(TABLE_TODO_ITEMS, cv, _ID + " =?", args) > 0;
        db.close();

        return result;
    }


    public boolean deleteTodoItem(TodoListItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = db.delete(TABLE_TODO_ITEMS, _ID + " = " + item.getId(), null) > 0;
        db.close();
        return result;
    }

}
