package com.example.mycontacts;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ContactLists extends CursorAdapter {

    public ContactLists(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.li_add_contact, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view.findViewById(R.id.nameTextView)).
                setText(cursor.getString(cursor.getColumnIndex("name")));
        ((TextView) view.findViewById(R.id.emailTextView)).
                setText(cursor.getString(cursor.getColumnIndex("email")));
        ((TextView) view.findViewById(R.id.dateTextView)).
                setText("Date Created: " + cursor.getString(cursor.getColumnIndex("date")));
    }
}
