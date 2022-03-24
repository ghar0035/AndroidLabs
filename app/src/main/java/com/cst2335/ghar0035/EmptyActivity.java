package com.cst2335.ghar0035;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Intent fromChatRoom = getIntent();
        String message = fromChatRoom.getStringExtra("Message");
        Long id = fromChatRoom.getLongExtra("ID", 0);
        boolean isSent = fromChatRoom.getBooleanExtra("IsSent",true);

        DetailsFragment detailFragment = new DetailsFragment();

        Bundle args = new Bundle();
        args.putLong(detailFragment.ID, id);
        args.putBoolean(detailFragment.IS_SENT,isSent);
        args.putString(detailFragment.MESSAGE,message );
        detailFragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
        ft.setReorderingAllowed(true);
        ft.replace(R.id.chatFrameLayoutMobile, detailFragment);    // add    Fragment
        ft.commit();

    }
}