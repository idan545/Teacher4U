package com.example.betaidan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.betaidan.FBref.refAuth;
import static com.example.betaidan.FBref.refLessonOffer;
import static com.example.betaidan.FBref.refLocations;
import static com.example.betaidan.FBref.refTeacher;


public class TeacherActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    /**
     * @author		Idan Cohen
     * @version	    V1.0
     * @since		8/4/2020
     * This activity will show any active order based on location.
     */

    ListView lv;
    TextView tvname, tvPhone, tv5, tvSClass, tvdate, tvSubject;
    EditText eTprice;
    String address;
    Teacher teacher = new Teacher();
    String name, phone, uid, About, Experience, date, price, subject, sclass, name1, phone1,uidteach;
    AlertDialog.Builder adb;
    LinearLayout studentdial;
    long count;
    Intent intent;
    ProgressDialog pd;
    LocationObject lo;
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


        refTeacher.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                teacher = ds.getValue(Teacher.class);
                name1 = teacher.getName();
                phone1 = teacher.getPhone();
                About = teacher.getAbout();
                Experience = teacher.getExperience();
                uidteach = teacher.getUid();


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Failed", "Failed to read value", databaseError.toException());
            }
        });


    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /**
         * Respond to the order that has been clicked.
         * <p>
         * @param parent,view,position,id
         */
        lb = locationObjects2.get(position);
        uid = lb.getUid();
        count = lb.getCount();
        name = lb.getName();
        phone = lb.getPhone();
        date = lb.getDate();
        sclass = lb.getSClass();
        subject = lb.getSubject();
        start();

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
            /**
             * Respond to Price offer acceptment or decline
             * <p>
             * @param dialog,which
             */
            if (which == DialogInterface.BUTTON_POSITIVE) {
                price = eTprice.getText().toString();
                price = price + "₪";
                LessonOffer lo = new LessonOffer(name1, phone1, date, price, subject, uid, About, Experience, true, count,uidteach);
                refLessonOffer.child("" + count).setValue(lo);
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
        /**
         * AlertDialog for the student's Information.
         * <p>
         */

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

    public void studentconfirm() {
        /**
         * Wait for the student to confirm or decline the offer
         * <p>
         */
        pd = ProgressDialog.show(this, "Awaiting student acceptment", "Waiting...", true);
        Query query = refLocations
                .orderByChild("count").equalTo(count);
        query.addValueEventListener(VEL);
    }

    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            /**
             *  Listener for if student confirms the order or declining.
             * <p>
             * @param dS
             */
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
                        refLessonOffer.child("" + lo.getCount()).removeValue();
                        Toast.makeText(TeacherActivity.this, "Lesson Declined", Toast.LENGTH_LONG).show();
                        pd.dismiss();
                        lo.setStatus(1);
                        refLocations.child("" +count).setValue(lo);
                    }
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    };
    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         * Show menu options
         * <p>
         * @param menu
         */
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * Respond to the menu item selected
         * <p>
         * @param item
         */
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
}
