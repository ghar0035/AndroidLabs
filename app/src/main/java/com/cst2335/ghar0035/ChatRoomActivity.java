package com.cst2335.ghar0035;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.Bidi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {
    public static final String TAG = "ChatRoomActivity";
    //Create an OpenHelper to store data
    MyOpenHelper myOpener;
    SQLiteDatabase db;
    int version = 1;
    Boolean isTablet = false;

    private ArrayList<Message> messages = new ArrayList<>();
    ListAdapter messageAdapter;    /*Adapter retrieves data from an external source and creates a View that represents each data entry */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);   //load XML

        Button sendBtn = findViewById(R.id.sendBtn);
        Button receiveBtn = findViewById(R.id.receiveBtn);
        EditText inputTex = findViewById(R.id.inputText);
        ListView chatListView = findViewById(R.id.chatListView);   //AdapterView
        FrameLayout chatFramelayout = findViewById(R.id.chatFrameLayoutTablet);

        Resources res = getResources();
        String deleteAlert = res.getString(R.string.deleteAlert);
        String selectedRow = res.getString(R.string.selectedRow);
        String databaseId = res.getString(R.string.databaseId);
        String deleteBtn = res.getString(R.string.deleteBtn);
        String cancelBtn = res.getString(R.string.cancelBtn);

        if(chatFramelayout != null){
            isTablet = true;
        } else {
            isTablet = false;
        }

        //initialize in onCreate
        myOpener = new MyOpenHelper(this);

        //open the database
        db = myOpener.getWritableDatabase();

        //load from the database
        Cursor cursor = db.rawQuery("Select * from " + MyOpenHelper.TABLE_NAME + ";", null );

        //Convert column names to indices
        int idIndex = cursor.getColumnIndex(MyOpenHelper.COL_ID);
        int messageIndex = cursor.getColumnIndex(MyOpenHelper.COL_MESSAGE);
        int isSentIndex = cursor.getColumnIndex(MyOpenHelper.COL_IS_SENT);

        //cursor is pointing to row1, returns false if no more data
        while(cursor.moveToNext()){
            //pointing to row2
            int id = cursor.getInt(idIndex);
            String message = cursor.getString(messageIndex);
            int isSent = cursor.getInt(isSentIndex);

            //add to arrayList:
            messages.add( new Message( message , isSent == 1 ? true : false , id ));

        }


        /*To populate the ListView with data,call setAdapter() on the ListView,to associate an adapter with the list*/
        chatListView.setAdapter(messageAdapter = new ListAdapter());

        // button send click
        sendBtn.setOnClickListener( click -> {
            String inputText = inputTex.getText().toString();
            if(inputText.isEmpty()) {
                return;

            }
            Message message = new Message(inputText,true);
            messages.add(message);
            ContentValues cv = new ContentValues();
            cv.put(myOpener.COL_MESSAGE, inputText);
            cv.put(myOpener.COL_IS_SENT, true);
            long id = db.insert(myOpener.TABLE_NAME, myOpener.COL_ID, cv);
            inputTex.setText("");
            this.printCursor(cursor, version);
            messageAdapter.notifyDataSetChanged();
        });

        // button receive click
        receiveBtn.setOnClickListener(click -> {
            String inputText = inputTex.getText().toString();
            if(inputText.isEmpty()) {
                return;
            }

            Message message = new Message(inputText,false);
            messages.add(message);
            ContentValues cv = new ContentValues();
            cv.put(myOpener.COL_MESSAGE, inputText);
            cv.put(myOpener.COL_IS_SENT, false);
            long id = db.insert(myOpener.TABLE_NAME, myOpener.COL_ID, cv);
            inputTex.setText("");
            this.printCursor(cursor, version);
            messageAdapter.notifyDataSetChanged();
        });

        chatListView.setOnItemClickListener((list, view, position,id) -> {

            if(isTablet){
                DetailsFragment detailFragment = new DetailsFragment();

                Bundle args = new Bundle();
                args.putLong(detailFragment.ID, messages.get(position).id);
                args.putBoolean(detailFragment.IS_SENT,  messages.get(position).isSent);
                args.putString(detailFragment.MESSAGE,  messages.get(position).message);
                detailFragment.setArguments(args);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
                ft.setReorderingAllowed(true);
                ft.replace(R.id.chatFrameLayoutTablet, detailFragment, "SOMETAG");    // add    Fragment
                ft.commit();
            } else {
                Intent goToEmpty = new Intent(ChatRoomActivity.this , EmptyActivity.class);
                goToEmpty.putExtra("ID",messages.get(position).id);
                goToEmpty.putExtra("IsSent",messages.get(position).isSent);
                goToEmpty.putExtra("Message",messages.get(position).message);

                startActivity(goToEmpty);
            }
        });

        chatListView.setOnItemLongClickListener((parent, view, i,l) -> {

            AlertDialog.Builder alert = new AlertDialog.Builder(ChatRoomActivity.this);
            alert.setTitle(deleteAlert)
                    .setMessage(selectedRow + " " + i  + "\n \n" + databaseId + " " +  messageAdapter.getItemId(i))
                    .setPositiveButton(deleteBtn, (click, arg) -> {
                        db.delete(myOpener.TABLE_NAME, MyOpenHelper.COL_ID + "= ?", new String[] { Long.toString(messageAdapter.getItemId(i)) });
                        messages.remove(i);
                        messageAdapter.notifyDataSetChanged();
                        this.printCursor( cursor, version);

                        Fragment fragment = getSupportFragmentManager().findFragmentByTag("SOMETAG") ;
                        if(fragment != null){
                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }
                    })
                    .setNegativeButton(cancelBtn, (click, arg) -> {
                        alert.create().dismiss();
                    })
                    .create().show();
            return true;
        });
    }

    private void printCursor(Cursor c, int version) {
        Log.i(TAG, "database version" + db.getVersion());
        Log.i(TAG, "Number of Columns " + c.getColumnCount());
        Log.i(TAG, "Name of the Columns " + Arrays.toString(c.getColumnNames()));
        Log.i(TAG, "Number of rows " + c.getCount() );

        String tableString = String.format("Table %s:\n", myOpener.TABLE_NAME);
        if (c.moveToFirst() ){
            String[] columnNames = c.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s ", name,
                            c.getString(c.getColumnIndex(name)));
                }
            } while (c.moveToNext());
            Log.i(TAG,"Each row of results in the cursor" + tableString);
        }
    }

    /*ListAdapter is an Interface that you must implement by writing these 4 public functions */
    private class ListAdapter extends BaseAdapter {

        //1-returns the number of items to display in the list(returns the size of theArray or ArrayList)
        public int getCount(){
            return messages.size();
        }

        //2-This function should return the object(Message) that you want to display at row position in the list
        public Message getItem(int position) {
            return messages.get(position);
        }

        //3-return the database ID of the element at the given index of position
        public long getItemId(int position) {
            return getItem(position).id;
        }

        //4-creates a View object(newView) to go in a row of the ListView and returns newView
        public View getView(int position, View old, ViewGroup parent){

            LayoutInflater inflater = getLayoutInflater(); //we need a LayoutInflater object to load an XML layout file
            //load an XML layout file, make a new row
            //newView is now the root object from your XML file, It contains the widgets that are in your layout
            View newView = inflater.inflate(R.layout.row_layout, parent,false);

            LinearLayout rowLayout = newView.findViewById(R.id.layoutContainer);

            ImageView iView = newView.findViewById(R.id.avatar);
            TextView tView = newView.findViewById(R.id.messageText);
            tView.setText(getItem(position).message);

            if(getItem(position).isSent) {
                iView.setImageResource(R.drawable.row_send);
                rowLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                tView.setGravity(Gravity.RIGHT);
            } else {
                iView.setImageResource(R.drawable.row_receive);
                rowLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                tView.setGravity(Gravity.LEFT);
            }

            return newView;   //return the inflated view
        }
    }

    private class Message {
         String message = "";
         Boolean isSent = false;
         long id;

        Message(String message, boolean isSent){
            this.message = message;
            this.isSent = isSent;
        }

        Message(String message, boolean isSent, long id){
            this.message = message;
            this.isSent = isSent;
            this.id = id;
        }
    }
}

