package com.example.uberfamiliy;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uberfamiliy.model.User;

import java.util.List;

public class AddFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        EditText searchText = findViewById(R.id.searchText);
        searchText.getText();

    }

    public void setHeader(ListView listView) {
        TextView textView = new TextView(this);
        textView.setText("Hello. I'm a header view");

        listView.addHeaderView(textView);
    }

    private void fillUpList(List<User> users) {
        // Daten aus DB holen List<Entry> entryList= dataSource.getAllEntries();
// Neue Instanz von ArrayAdapter, die entryListwird // übergeben, ein Element ist auswählbar ArrayAdapter<Entry> entryArrayAdapter= newArrayAdapter<> ( this, android.R.layout.simple_list_item_single_choice, entryList);
// ListViewaus dem Layout holen entryListView= (ListView) findViewById(R.id.entry_listview);
// Ein Element ist auswählbar entryListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
// Den Adapter an die View binden -kein Array / keine Liste entryListView.setAdapter(entryArrayAdapter);
    }
}
