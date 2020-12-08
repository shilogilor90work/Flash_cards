package com.example.flash_cards;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DefinitionsAdapter extends ArrayAdapter {
    DatabaseReference root_database;
    FirebaseUser user;
    ArrayList<String> records;
    String subject;
    public DefinitionsAdapter(Context context, ArrayList<String> records) {
        super(context, 0, records);
        this.records = records;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        root_database = FirebaseDatabase.getInstance().getReference().child("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        String item = (String) getItem(position);
        if (convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.definitions_listview, null);
        }
        TextView definitions_text = (TextView) convertView.findViewById(R.id.definitions_text);
        TextView definition_value_text = (TextView) convertView.findViewById(R.id.definition_value_text);
        Button definition_delete = (Button) convertView.findViewById(R.id.definition_delete);
        Log.d("hithere", item);
        String key = item.substring(0,item.indexOf("|split|"));
        String value = item.substring(item.indexOf("|split|") + 7 , item.indexOf("|*subject*|"));
        subject= item.substring(item.indexOf("|*subject*|") + 11);
        definitions_text.setText(key);
        definition_value_text.setText(value);
        definition_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db_node = root_database.child( user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("subjects").child(subject).child(key);
                db_node.removeValue();
                records.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
