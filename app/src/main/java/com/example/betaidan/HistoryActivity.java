package com.example.betaidan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.security.auth.Subject;

import static com.example.betaidan.FBref.refAuth;
import static com.example.betaidan.FBref.refLessonOffer;
import static com.example.betaidan.FBref.refLocations;

public class HistoryActivity extends AppCompatActivity {
    String UID,price,About,Experience,Date,Sbjct1,Name1,Phone1;
    LessonOffer lo;
    Intent intent;
    ArrayList<String> details = new ArrayList<>();
    ArrayList<LocationObject> locationObjects2 = new ArrayList<>();
    ArrayAdapter<String> adp;
    ListView LV1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ValueEventListener locationListener;
        LV1 = (ListView) findViewById(R.id.LV1);



        FirebaseUser firebaseUser = refAuth.getCurrentUser();
        UID = firebaseUser.getUid();


        Query query = refLessonOffer
                .orderByChild("uid")
                .equalTo(UID);
        query.addValueEventListener(VEL);







        /*{
            locationListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    offer.clear();
                    locationObjects2.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String firstAddress = (String) data.getKey();

                        LocationObject LocationObject = data.getValue(LocationObject.class);
                        if(!LocationObject.isAct()) {
                            locationObjects2.add(LocationObject);
                            String date = LocationObject.getDate();
                            String Subject = LocationObject.getSubject();
                            offer.add("Subject: " + Subject + "Date: " + date);
                        }
                    }

                    adp = new ArrayAdapter<String>(HistoryActivity.this,
                            R.layout.support_simple_spinner_dropdown_item, offer);
                    LV1.setAdapter(adp);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("TeacherActivity", "Failed to read value", databaseError.toException());

                }

            };
            refLocations.addValueEventListener(locationListener);

        }*/

    }

    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for(DataSnapshot data : dS.getChildren()) {

                    lo = data.getValue(LessonOffer.class);
                    if (!lo.isAct()){
                        price = lo.getPrice();
                        Experience = lo.getExperience();
                        Date = lo.getDate();
                        Sbjct1 = lo.getSubject();
                        About = lo.getAbout();
                        Name1 = lo.getName();
                        Phone1 = lo.getPhone();
                        details.add("Subject: "  + Sbjct1  + " Date: " + Date);

                    }


                }
                adp = new ArrayAdapter<String>(HistoryActivity.this,
                        R.layout.support_simple_spinner_dropdown_item, details);
                LV1.setAdapter(adp);
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };




    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.second, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String st = item.getTitle().toString();
        if (st.equals("Orders")) {
            intent = new Intent(HistoryActivity.this, TeacherActivity.class);
            startActivity(intent);
        }
        if (st.equals("Order History")) {
            intent = new Intent(HistoryActivity.this, HistoryActivity.class);
            startActivity(intent);
        }
        if (st.equals("Profile")) {
            intent = new Intent(HistoryActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
        if (st.equals("Credits")) {
            intent = new Intent(HistoryActivity.this, CreditsActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
