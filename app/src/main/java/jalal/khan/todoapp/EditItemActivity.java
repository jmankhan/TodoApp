package jalal.khan.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Spinner prioritySpinner = (Spinner) findViewById(R.id.editItemPriority);
        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this,
                R.array.priorities, R.layout.spinner_item);
        prioritySpinner.setAdapter(priorityAdapter);

        EditText editText = (EditText) findViewById(R.id.editItemName);
        String oldText = getIntent().getStringExtra("editItemOldName");
        editText.setText(oldText);

    }

    public void saveItem(View v) {
        int position = getIntent().getIntExtra("editItemPosition", -1);
        EditText editText = (EditText) findViewById(R.id.editItemName);
        Spinner prioritySpinner = (Spinner) findViewById(R.id.editItemPriority);

        String editedName = editText.getText().toString();
        String editedPriority = prioritySpinner.getSelectedItem().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("editedName", editedName);
        resultIntent.putExtra("editedPriority", editedPriority);
        resultIntent.putExtra("editItemPosition", position);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
