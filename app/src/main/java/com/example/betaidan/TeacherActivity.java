package com.example.betaidan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.betaidan.FBref.refLocations;
import static com.example.betaidan.FBref.refstudent;
import static com.example.betaidan.R.layout.studentdial;

public class TeacherActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv;
    Button btn;
    TextView tvname  ,tvPhone,tvAbout,tvexp,tv5;
    String address;
    String name,phone,Experience,About,uid, sclass;
    AlertDialog.Builder adb;
    Intent t;
    LinearLayout studentdialog1;
    Dialog studentdialog12;
    Student student;
    FusedLocationProviderClient fusedLocationProviderClient ;
    ArrayList<String> offer=new ArrayList<>();
    ArrayList<LocationObject> locationObjects2 = new ArrayList<>();
    ArrayAdapter<String> adp;
    LocationObject lb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        lv=(ListView) findViewById(R.id.LV1);
        lv.setOnItemClickListener(TeacherActivity.this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adp = new ArrayAdapter<String>(TeacherActivity.this, R.layout.support_simple_spinner_dropdown_item, offer);

        lv.setAdapter(adp);


    }
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        lb = locationObjects2.get(position);
        uid = lb.getUid();
        name = lb.getName();
        phone = lb.getPhone();
        sclass = lb.getSClass();
        Toast.makeText(this, ""+name,Toast.LENGTH_LONG).show();

       // start();
    };



    ValueEventListener locationListener;{
        locationListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                offer.clear();
                locationObjects2.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String firstAddress = (String) data.getKey();
                    LocationObject LocationObject = data.getValue(LocationObject.class);
                    locationObjects2.add(LocationObject);
                    String Location = LocationObject.getMyLocation();
                    String Subject = LocationObject.getSubject();
                    offer.add("I am at:  " + Location + " and my subject is: " + Subject);

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
    DialogInterface.OnClickListener myclick= new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                getLocation();
                Intent t=new Intent(TeacherActivity.this,mapforTeacher.class);
                Toast.makeText(TeacherActivity.this,"Successful",Toast.LENGTH_SHORT).show();
            }
            if (which == DialogInterface.BUTTON_NEGATIVE) {
                dialog.cancel();

            }
        }

    };
    @SuppressLint("CutPasteId")
    public void start() {
        ValueEventListener studentdetails;

        studentdialog1 = (LinearLayout) getLayoutInflater().inflate(studentdial, null);
        adb = new AlertDialog.Builder(this);
        adb.setView(studentdialog1);
        adb.setTitle("customer details  ");
        tvname = (TextView) findViewById(R.id.tvnamee);
        tvPhone = (TextView) findViewById(R.id.tvPhonee);
        tvAbout = (TextView) findViewById(R.id.tvAbout);
        tvexp = (TextView) findViewById(R.id.tvexp);
        adb.setPositiveButton("enter", (DialogInterface.OnClickListener) myclick);
        adb.setNegativeButton("cancel", (DialogInterface.OnClickListener) myclick);
        adb.show();



    }

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                // Intiialize location
                Location location = task.getResult();
                if (location!=null){
                    //Intiialize geoCoder
                    Geocoder geocoder = new Geocoder(TeacherActivity.this,
                            Locale.getDefault());
                    // Intiialize address list
                    try {
                        List<Address> addresses= geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );
                        //set address
                        tv5.setText(Html.fromHtml(
                                "<font color =' #6200EE'><b>Address:</b><br></fonnt>"
                                        + addresses.get(0).getAddressLine(0))
                        );


                    } catch (IOException e){
                        e.printStackTrace();
                    }

                }

            }
        });
        address = tv5.getText().toString();
        Toast.makeText(TeacherActivity.this, address, Toast.LENGTH_LONG).show();
    }

}
