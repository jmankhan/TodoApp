package jalal.khan.todoapp;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jalal on 8/1/2017.
 */

public class TodoListAdapter extends ArrayAdapter<TodoListItem> {

    private Context context;

    public TodoListAdapter(Context context, ArrayList<TodoListItem> items) {
        super(context, R.layout.todolist_item, items);
        this.context = context;
    }

    private static class ViewHolder {
        TextView name;
        ImageButton edit;
        ImageButton trash;
        View priority;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TodoListItem item = getItem(position);
        ViewHolder viewHolder;

        //generate a new view if convertview is null, otherwise recycle it
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.todolist_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.todoListItemName);
            viewHolder.priority = convertView.findViewById(R.id.todoListItemPriority);
            viewHolder.edit = (ImageButton) convertView.findViewById(R.id.todoListItemEdit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(item.getText());
        int priorityColor;
        switch(item.getPriority()) {
            case LOW: priorityColor = ContextCompat.getColor(context, R.color.priorityLow); break;
            case MEDIUM: priorityColor = ContextCompat.getColor(context, R.color.priorityMedium); break;
            case HIGH: priorityColor = ContextCompat.getColor(context, R.color.priorityHigh); break;
            default: priorityColor = ContextCompat.getColor(context, R.color.priorityLow); break;
        }

        viewHolder.priority.setBackgroundColor(priorityColor);
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).editItem(position);
            }
        });

        return convertView;
    }

}
