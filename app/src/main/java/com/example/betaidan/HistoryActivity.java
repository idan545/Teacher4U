package com.example.betaidan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class HistoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String UID, price, About, Experience, Date, Sbjct1, Name1, Phone1,name,phone,sclass;
    TextView tvnamee1, tvPhonee1, tvAbout, tvExperience, tvDate1, tvSubject1,
            tVprice,tvname1,tvPhone1,tvDate2,tVsbj,tVprice1,tVsclass;
    Boolean teacher = false;
    LessonOffer lo;
    LocationObject Lo1;
    LinearLayout OfferDial;
    Intent intent;
    AlertDialog.Builder adb;
    ArrayList<String> details = new ArrayList<>();
    ArrayList<LessonOffer> Detail = new ArrayList<>();
    ArrayList<LocationObject> lob = new ArrayList<>();
    ArrayAdapter<String> adp;
    ListView LV1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ValueEventListener locationListener;
        LV1 = (ListView) findViewById(R.id.LV1);
        LV1.setOnItemClickListener(HistoryActivity.this);
        LV1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        FirebaseUser firebaseUser = refAuth.getCurrentUser();
        UID = firebaseUser.getUid();


        Query query = refLessonOffer
                .orderByChild("uid")
                .equalTo(UID);
        query.addValueEventListener(VEL);

        Query query1 = refLessonOffer
                .orderByChild("uidteach")
                .equalTo(UID);
        query1.addValueEventListener(VEL);







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
                for (DataSnapshot data : dS.getChildren()) {
                    lo = data.getValue(LessonOffer.class);
                    if (!lo.isAct()) {
                        Detail.add(lo);
                        Date = lo.getDate();
                        Sbjct1 = lo.getSubject();
                        details.add("Subject: " + Sbjct1 + " Date: " + Date);

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



    public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.second, menu);
            return true;
        }

        public boolean onOptionsItemSelected (MenuItem item){
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(!Detail.get(position).isAct()){
                price = Detail.get(position).getPrice();
                Experience = Detail.get(position).getExperience();
                Date = Detail.get(position).getDate();
                Sbjct1 = Detail.get(position).getSubject();
                About = Detail.get(position).getAbout();
                Name1 = Detail.get(position).getName();
                Phone1 = Detail.get(position).getPhone();
                adblv();
            }
    }

    public void adblv(){
        OfferDial = (LinearLayout) getLayoutInflater().inflate(R.layout.priceofferdial, null);

        tvnamee1 = (TextView) OfferDial.findViewById(R.id.tvnamee1);
        tvPhonee1 = (TextView) OfferDial.findViewById(R.id.tvPhonee1);
        tvAbout = (TextView) OfferDial.findViewById(R.id.tvAbout);
        tvExperience = (TextView) OfferDial.findViewById(R.id.tvExperience);
        tvDate2 = (TextView) OfferDial.findViewById(R.id.tvDate2);
        tVprice1 = (TextView) OfferDial.findViewById(R.id.tVprice1);
        tvSubject1 = (TextView) OfferDial.findViewById(R.id.tvSubject1);

        tvnamee1.setText("Teacher: " + Name1);
        tvPhonee1.setText("Phone: " + Phone1);
        tvAbout.setText("About: " + About);
        tvExperience.setText("Experienced in: " + Experience);
        tvDate2.setText("Date: "+ Date);
        tvSubject1.setText("Subject: " + Sbjct1);
        tVprice1.setText("The price for 1 hour is: " + price);



        adb = new AlertDialog.Builder(this);
        adb.setView(OfferDial);
        AlertDialog ad = adb.create();
        ad.show();
    }

}