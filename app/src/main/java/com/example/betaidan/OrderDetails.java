package com.example.betaidan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class OrderDetails extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        String st = item.getTitle().toString();
        if(st.equals("Orders")) {
            intent = new Intent(OrderDetails.this, StudentActivity.class);
            startActivity(intent);
        }
        if(st.equals("Credits")) {
            intent = new Intent(OrderDetails.this, CreditsActivity.class);
            startActivity(intent);
        }
        if(st.equals("Profile")) {
            intent = new Intent(OrderDetails.this, ProfileActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
