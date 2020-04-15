package com.example.betaidan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.betaidan.FBref.refAuth;
import static com.example.betaidan.FBref.refLessonOffer;
import static com.example.betaidan.FBref.refLocations;
import static com.example.betaidan.FBref.refTeacher;


public class TeacherActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv;
    TextView tvname, tvPhone, tv5, tvSClass, tvdate, tvSubject;
    EditText eTprice;
    String address;
    Teacher teacher = new Teacher();
    String name, phone, uid, About, Experience, date, price, subject, sclass, name1, phone1;
    AlertDialog.Builder adb;
    LinearLayout studentdial;
    Intent intent;
    ProgressDialog pd;
    LocationObject lo;
    FusedLocationProviderClient fusedLocationProviderClient;
    ArrayList<String> offer = new ArrayList<>();
    ArrayList<LocationObject> locationObjects2 = new ArrayList<>();
    ArrayAdapter<String> adp;
    LocationObject lb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        lv = (ListView) findViewById(R.id.LV1);
        lv.setOnItemClickListener(TeacherActivity.this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adp = new ArrayAdapter<String>(TeacherActivity.this, R.layout.support_simple_spinner_dropdown_item, offer);
        lv.setAdapter(adp);

        FirebaseUser firebaseUser = refAuth.getCurrentUser();


        //  FirebaseUser firebaseUser = refAuth.getCurrentUser();
        refTeacher.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                //  UID =  (String) data.getKey();
                teacher = ds.getValue(Teacher.class);
                name1 = teacher.getName();
                phone1 = teacher.getPhone();
                About = teacher.getAbout();
                Experience = teacher.getExperience();


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Failed", "Failed to read value", databaseError.toException());
            }
        });


    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        lb = locationObjects2.get(position);
        uid = lb.getUid();
        name = lb.getName();
        phone = lb.getPhone();
        date = lb.getDate();
        sclass = lb.getSClass();
        subject = lb.getSubject();
        start();
    /*    if (trigger) {
            final ProgressDialog pd = ProgressDialog.show(this, "Awaiting student acceptment..", "Waiting...", true);
        }
     */

    }


    ValueEventListener locationListener;

    {
        locationListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                offer.clear();
                locationObjects2.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String firstAddress = (String) data.getKey();

                    LocationObject LocationObject = data.getValue(LocationObject.class);
                    if(LocationObject.isAct()) {
                        locationObjects2.add(LocationObject);
                        String Location = LocationObject.getMyLocation();
                        String Subject = LocationObject.getSubject();
                        offer.add(Location + " and my subject is: " + Subject);
                    }

                }

                adp = new ArrayAdapter<String>(TeacherActivity.this,
                        R.layout.support_simple_spinner_dropdown_item, offer);
                lv.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TeacherActivity", "Failed to read value", databaseError.toException());

            }

        };
        refLocations.addValueEventListener(locationListener);

    }

    DialogInterface.OnClickListener myclick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                price = eTprice.getText().toString();
                price = price + "₪";
                LessonOffer lo = new LessonOffer(name1, phone1, date, price, subject, uid, About, Experience, true);
                refLessonOffer.child(uid).setValue(lo);
                Toast.makeText(TeacherActivity.this, "Succeed", Toast.LENGTH_SHORT).show();
                studentconfirm();
            }
            if (which == DialogInterface.BUTTON_NEGATIVE) {
                dialog.cancel();

            }
        }

    };

    @SuppressLint("CutPasteId")

    public void start() {

        studentdial = (LinearLayout) getLayoutInflater().inflate(R.layout.studentdial, null);

        tvname = (TextView) studentdial.findViewById(R.id.tvnamee);
        tvPhone = (TextView) studentdial.findViewById(R.id.tvPhonee);
        tvSClass = (TextView) studentdial.findViewById(R.id.tvSClass);
        tvdate = (TextView) studentdial.findViewById(R.id.tvDate);
        eTprice = (EditText) studentdial.findViewById(R.id.eTprice);
        tvSubject = (TextView) studentdial.findViewById(R.id.tvSubject);
        tvname.setText("Name: " + name);
        tvPhone.setText("Phone: " + phone);
        tvSClass.setText("Student Class: " + sclass);
        tvdate.setText("Date: " + date);
        tvSubject.setText("Subject: " + subject);


        adb = new AlertDialog.Builder(this);
        adb.setView(studentdial);

        adb.setPositiveButton("Confirm", (DialogInterface.OnClickListener) myclick);
        adb.setNegativeButton("Cancel", (DialogInterface.OnClickListener) myclick);
        adb.show();


    }

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                // Intiialize location
                Location location = task.getResult();
                if (location != null) {
                    //Intiialize geoCoder
                    Geocoder geocoder = new Geocoder(TeacherActivity.this,
                            Locale.getDefault());
                    // Intiialize address list
                    try {
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        //set address
                        tv5.setText(Html.fromHtml(
                                "<font color =' #6200EE'><b>Address:</b><br></fonnt>"
                                        + addresses.get(0).getAddressLine(0))
                        );


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
        address = tv5.getText().toString();
        Toast.makeText(TeacherActivity.this, address, Toast.LENGTH_LONG).show();
    }

    public void studentconfirm() {
        pd = ProgressDialog.show(this, "Awaiting student acceptment", "Waiting...", true);
        Query query = refLocations
                .orderByChild("uid").equalTo(uid);
        query.addValueEventListener(VEL);
    }

    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            if (dS.exists()) {
                for (DataSnapshot data : dS.getChildren()) {
                    lo = data.getValue(LocationObject.class);
                }
                if (lo.getStatus() == 2) {
                    Toast.makeText(TeacherActivity.this, "Lesson Accepted!", Toast.LENGTH_LONG).show();
                    pd.dismiss();
                    intent = new Intent(TeacherActivity.this, HistoryActivity.class);
                    startActivity(intent);
                }
                else{
                    if(lo.getStatus()==0){
                        refLessonOffer.child(uid).removeValue();
                        Toast.makeText(TeacherActivity.this, "Lesson Declined", Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.second, menu);
            return true;
        }

        public boolean onOptionsItemSelected(MenuItem item) {
            String st = item.getTitle().toString();
            if (st.equals("Orders")) {
                intent = new Intent(TeacherActivity.this, TeacherActivity.class);
                startActivity(intent);
            }
            if (st.equals("Order History")) {
                intent = new Intent(TeacherActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
            if (st.equals("Profile")) {
                intent = new Intent(TeacherActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
            if (st.equals("Credits")) {
                intent = new Intent(TeacherActivity.this, CreditsActivity.class);
                startActivity(intent);
            }
            return true;
        }
    };
}
