package com.example.mycontacts;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Intent intent;

    DBHandler dbHandler;

    ContactLists contactListsAdapter;

    ListView contactListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler = new DBHandler(this, null);

        contactListView = (ListView) findViewById(R.id.contactListView);

        contactListsAdapter = new ContactLists(this, dbHandler.getContactLists(),0);

        contactListView.setAdapter(contactListsAdapter);

        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String testDate = dbHandler.getContactListsDate((int)id);

                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String stringDate = simpleDateFormat.format(date);

                if(testDate.equals(stringDate)){
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);

                    //set it icon, title, and text
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setContentTitle("Contacts");
                    builder.setContentText("Contract Created!" +  " (" + stringDate + ")");

                    //initialize an Intent for the Main Activity
                    intent = new Intent(MainActivity.this, MainActivity.class);

                    //initialize a PendingIntent
                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    //set the content intent of the Notification
                    builder.setContentIntent(pendingIntent);

                    //initialize a NotificationManger
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    //have the NotificationManager send the Notification
                    notificationManager.notify(2142, builder.build());
                }



            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_home :
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_contact :
                intent = new Intent(this, AddContact.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }

    public void openAddList(View view){
        intent = new Intent(this, AddContact.class);
        startActivity(intent);
    }


}
