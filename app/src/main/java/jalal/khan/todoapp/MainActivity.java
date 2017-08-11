package jalal.khan.todoapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    private static final int ANIMATION_ADD_DURATION = 500;
    public static int EDIT_ITEM_ACTION = 1;

    private ArrayList<TodoListItem> items;
    private TodoListAdapter adapter;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DatabaseHandler(this);
        items = dbHandler.getAllTodoItems();
        adapter = new TodoListAdapter(this, items);
        ListView listView = (ListView) findViewById(R.id.mainListView);

        listView.setAdapter(this.adapter);
        listView.setOnItemLongClickListener(this);
    }

    public void beginAddAnimation(View v) {
        final Button addItemButton = (Button) findViewById(R.id.mainAddButton);
        Animation anim = new ScaleAnimation(
                1f, 0.25f,
                1f, 1f,
                Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f);
        anim.setDuration(ANIMATION_ADD_DURATION);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                addItemButton.setVisibility(GONE);
                findViewById(R.id.mainAddButton2).setVisibility(VISIBLE);
                findViewById(R.id.mainEditText).setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

        });
        addItemButton.startAnimation(anim);
    }

    public void addItem(View v) {
        EditText addItemEditText = (EditText) findViewById(R.id.mainEditText);

        //hide soft keyboard in case it's still up, in cases where they hit the + button instead
        //of enter, or where the enter button doesn't work (it's not realiable for all keyboards)
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(addItemEditText.getWindowToken(), 0);

        //create the item and add it to the adapter and db
        String name = addItemEditText.getText().toString();
        TodoListItem item = new TodoListItem(name);

        adapter.add(item);
        dbHandler.insertTodoItem(item);

        //reset everything
        addItemEditText.setText("");
        findViewById(R.id.mainAddButton).setVisibility(View.VISIBLE);
        findViewById(R.id.mainAddButton2).setVisibility(INVISIBLE);
        findViewById(R.id.mainEditText).setVisibility(INVISIBLE);
    }

    public void editItem(int position) {
        Intent intent = new Intent(this, EditItemActivity.class);
        intent.putExtra("editItemPosition", position);
        intent.putExtra("editItemOldName", adapter.getItem(position).getText());
        startActivityForResult(intent, EDIT_ITEM_ACTION);
    }

    public void deleteItem(final int position) {
        runOnUiThread(new Runnable() {
            public void run() {
                new AlertDialog.Builder(MainActivity.this).setMessage(getString(R.string.confirm_delete))
                        .setTitle(getString(R.string.delete))
                        .setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TodoListItem item = adapter.getItem(position);
                                adapter.remove(item);
                                dbHandler.deleteTodoItem(item);
                            }
                        })
                        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_ITEM_ACTION) {
            if (resultCode == Activity.RESULT_OK) {
                String editedName = data.getStringExtra("editedName");
                String editedPriority = data.getStringExtra("editedPriority");

                int position = data.getIntExtra("editItemPosition", -1);
                TodoListItem item = adapter.getItem(position);

                item.setText(editedName);
                item.setPriority(Priority.valueOf(editedPriority.toUpperCase()));

                adapter.notifyDataSetChanged();
                dbHandler.updateTodoItem(item);
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        deleteItem(position);
        return true;
    }
}
