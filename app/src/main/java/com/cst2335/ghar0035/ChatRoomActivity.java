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
    private ArrayList<Message> messages = new ArrayList<Message>(  );
    ListAdapter messageAdapter;

    Button sendBtn ;
    Button receiveBtn;
    EditText inputTex;
    ListView chatList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        sendBtn = findViewById(R.id.sendBtn);
        receiveBtn = findViewById(R.id.receiveBtn);
        inputTex = findViewById(R.id.inputText);
        chatList = findViewById(R.id.chatListView);

        Resources res = getResources();
        String deleteAlert = res.getString(R.string.deleteAlert);
        String selectedRow = res.getString(R.string.selectedRow);
        String databaseId = res.getString(R.string.databaseId);
        String deleteBtn = res.getString(R.string.deleteBtn);
        String cancelBtn = res.getString(R.string.cancelBtn);


        chatList.setAdapter(messageAdapter = new ListAdapter());

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

        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                this.showAlert(i);
            }

            private void showAlert(int i) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ChatRoomActivity.this);
                    alert.setTitle(deleteAlert);
                    alert.setMessage(selectedRow + i  + "\n \n" + databaseId + messageAdapter.getItemId(i));

                    alert.setPositiveButton(deleteBtn, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            messages.remove(i);
                            messageAdapter.notifyDataSetChanged();
                        }
                    });

                    alert.setNegativeButton(cancelBtn, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alert.show();
            }
        });
    }


    private class ListAdapter extends BaseAdapter {
        public int getCount(){
            return messages.size();
        }

        public Message getItem(int position) {
            return messages.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();

            //make a new row:
            View newView = inflater.inflate(R.layout.row_layout, parent,false);


            TextView tView = newView.findViewById(R.id.messageText);
            ImageView iView = newView.findViewById(R.id.avatar);
            LinearLayout rowLayout = newView.findViewById(R.id.layoutContainer);
            tView.setText(getItem(position).text);

            if(getItem(position).type == "Send") {
                iView.setImageResource(R.drawable.row_send);
                rowLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                tView.setGravity(Gravity.RIGHT);
            } else {
                iView.setImageResource(R.drawable.row_receive);
            }

            return newView;
        }
    }

    private class Message {
        public String text = "";
        public String type = "";

        Message(String text, String type){
            this.text = text;
            this.type = type;
        }
    }
}

