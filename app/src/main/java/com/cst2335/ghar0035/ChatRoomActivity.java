package com.cst2335.ghar0035;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.Bidi;
import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    public static final String TAG = "ChatRoomActivity";

    private ArrayList<Message> messages = new ArrayList<>();
    ListAdapter messageAdapter;    /*Adapter retrieves data from an external
                                     source and creates a View that represents each data entry */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Button sendBtn = findViewById(R.id.sendBtn);
        Button receiveBtn = findViewById(R.id.receiveBtn);
        EditText inputTex = findViewById(R.id.inputText);
        ListView chatListView = findViewById(R.id.chatListView);   //AdapterView

        Resources res = getResources();
        String deleteAlert = res.getString(R.string.deleteAlert);
        String selectedRow = res.getString(R.string.selectedRow);
        String databaseId = res.getString(R.string.databaseId);
        String deleteBtn = res.getString(R.string.deleteBtn);
        String cancelBtn = res.getString(R.string.cancelBtn);

        /*To populate the ListView with data,call setAdapter() on the ListView,to associate an adapter with the list*/
        chatListView.setAdapter(messageAdapter = new ListAdapter());

        // button send click
        sendBtn.setOnClickListener( click -> {
            Message message = new Message(inputTex.getText().toString(),"Send");
            messages.add(message);
            inputTex.setText("");
            messageAdapter.notifyDataSetChanged();
        });

        // button receive click
        receiveBtn.setOnClickListener(click -> {
            Message message = new Message(inputTex.getText().toString(),"Receive");
            messages.add(message);
            inputTex.setText("");
            messageAdapter.notifyDataSetChanged();
        });

        chatListView.setOnItemClickListener((parent, view, i,l) -> {

            AlertDialog.Builder alert = new AlertDialog.Builder(ChatRoomActivity.this);
            alert.setTitle(deleteAlert)

                    .setMessage(selectedRow + i  + "\n \n" + databaseId + messageAdapter.getItemId(i))

                    .setPositiveButton(deleteBtn, (click, arg) -> {

                        messages.remove(i);
                        messageAdapter.notifyDataSetChanged();

                    })

                    .setNegativeButton(cancelBtn, (click, arg) -> {

                        alert.create().dismiss();

                    })

                    .create().show();

        });
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
            return (long) position;
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
            tView.setText(getItem(position).text);

            if(getItem(position).type == "Send") {
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
         String text = "";
         String type = "";

        Message(String text, String type){
            this.text = text;
            this.type = type;
        }
    }
}

