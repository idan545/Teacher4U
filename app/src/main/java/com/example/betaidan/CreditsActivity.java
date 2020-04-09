package com.example.betaidan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class CreditsActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }
 /*   public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        String st = item.getTitle().toString();
        if(st.equals("Order History"))
            intent = new Intent(CreditsActivity.this, HistoryActivity.class);
        if(st.equals("Profile"))
            intent = new Intent(CreditsActivity.this, ProfileActivity.class);
        if(st.equals("Orders"))
            intent = new Intent(CreditsActivity.this, StudentActivity.class);
        return true;
    }
  */
}
