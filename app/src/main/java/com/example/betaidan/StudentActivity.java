package com.example.betaidan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.betaidan.FBref.refAuth;
import static com.example.betaidan.FBref.refLessonOffer;
import static com.example.betaidan.FBref.refLocations;
import static com.example.betaidan.FBref.refTeacher;
import static com.example.betaidan.FBref.refstudent;

public class StudentActivity extends AppCompatActivity{
  /**
   * @author		Idan Cohen
   * @version	    V1.0
   * @since		7/4/2020
   * Student's will order their private lesson in this activity.
   */

  //Initialize variable
  Button btLocation,DateBtn;
  int status;
  TextView tv1,tv2,tv3,tv4,tv5,TVD,tvnamee1,tvPhonee1,tvAbout,tvExperience,tvDate1,tvSubject1,tVprice;
  String uid="sfns",subject="dmfsf", name, phone, sclass,price,About,Experience,Date,Sbjct1,Name1,Phone1,uidteach,price2;
  EditText targetSubject;
  LinearLayout priceofferdial;
  String test="try",eventdate;
  Student student;
  LessonOffer lo;
  Teacher yes;
  Boolean aBoolean=true;
  ProgressDialog pd;
  Intent intent;
  LocationObject locationObject1;
  long count;
  FusedLocationProviderClient fusedLocationProviderClient ;
  String UID;
  DatePickerDialog dpd;
  AlertDialog.Builder adb;
  Calendar c;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_student);

    FirebaseUser firebaseUser = refAuth.getCurrentUser();
    UID = firebaseUser.getUid();




    //  FirebaseUser firebaseUser = refAuth.getCurrentUser();
    refstudent.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot ds) {
        //  UID =  (String) data.getKey();
        student = ds.getValue(Student.class);
        name = student.getName();
        phone = student.getPhone();
        sclass = student.getSClass();


      }


      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.w("Failed", "Failed to read value", databaseError.toException());
      }
    });
    // refstudent.addValueEventListener(uploadlitner);





    //Assign variable
    targetSubject=(EditText)findViewById(R.id.targetSubject);
    DateBtn = (Button) findViewById(R.id.DateBtn);
    TVD = (TextView) findViewById(R.id.textView);
    btLocation=(Button)findViewById(R.id.ButtonGetCurrentLocation);
    tv1=(TextView) findViewById(R.id.tv1);
    tv2=(TextView) findViewById(R.id.tv2);
    tv3=(TextView) findViewById(R.id.tv3);
    tv4=(TextView) findViewById(R.id.tv4);
    tv5=(TextView) findViewById(R.id.tv5);

    //Initialize fusedLocationProviderClient
    fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

    btLocation.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        /**
         * Asking for getting location permission.
         * <p>
         *
         * @param	view Button	on click operate the action.
         */
        //check premission
        if (ActivityCompat.checkSelfPermission(StudentActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
          //when permission granted
          getLocation();
        }else {
          //when permission denied
          ActivityCompat.requestPermissions(StudentActivity.this,
                  new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);

        }
      }

      private void getLocation() {
        /**
         * When premission is granted, the action will fetch the users location.
         * <p>
         */
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
          @Override
          public void onComplete(@NonNull Task<Location> task) {
            // Intiialize location
            Location location = task.getResult();
            if (location!=null){
              //Intiialize geoCoder
              Geocoder geocoder = new Geocoder(StudentActivity.this,
                      Locale.getDefault());
              // Intiialize address list
              try {
                List<Address> addresses= geocoder.getFromLocation(
                        location.getLatitude(),location.getLongitude(),1
                );
                //set latitude on TextView
                tv1.setText(Html.fromHtml(
                        "<font color =' #6200EE'><b>Latitude:</b><br></fonnt>"
                                + addresses.get(0).getLatitude()

                ));
                //set Longitude on TextView
                tv2.setText(Html.fromHtml(
                        "<font color =' #6200EE'><b>Longitude:</b><br></fonnt>"
                                + addresses.get(0).getLongitude()

                ));
                //set country name
                tv3.setText(Html.fromHtml(
                        "<font color =' #6200EE'><b>Country:</b><br></fonnt>"
                                + addresses.get(0).getCountryName()));
                //set Locality
                tv4.setText(Html.fromHtml(
                        "<font color =' #6200EE'><b>Locality:</b><br></fonnt>"
                                + addresses.get(0).getLocality())
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
      }
    });
    DateBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        /**
         * Opens up the calendar for the user
         * <p>
         * @param view Button on click operate the action
         */

        c = Calendar.getInstance();
        final int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        dpd = new DatePickerDialog(StudentActivity.this, new DatePickerDialog.OnDateSetListener() {
          @Override
          public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            /**
             * Let's the student select the date that they want the private lesson will be at.
             * <p>
             *
             * @param	DatePicker view, int year, int Month, int dayOfMonth The date will be saved in those parameters.
             */

            Toast.makeText(StudentActivity.this, "" + dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
            if (month<10) {
              if (dayOfMonth<10) {
                eventdate="" + year + "_0" + (month+1) + "_0" + dayOfMonth;
              } else {
                eventdate="" + year + "_0" + (month+1) + "_" + dayOfMonth;
              }
            } else if (dayOfMonth<10) {
              eventdate="" + year + "_" + (month+1) + "_0" + dayOfMonth;
            } else {
              eventdate="" + year + "_" + (month+1) + "_" + dayOfMonth;
            }
            TVD.setText(eventdate);

          }
        }, day, month, year);
        dpd.show();

      }
    });
  }
  com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dS) {
      if (dS.exists()) {
        for(DataSnapshot data : dS.getChildren()) {
          lo  = data.getValue(LessonOffer.class);

        }
        if(lo.isAct()) {
          price = lo.getPrice();
          Experience = lo.getExperience();
          Date = lo.getDate();
          Sbjct1 = lo.getSubject();
          About = lo.getAbout();
          Name1 = lo.getName();
          uidteach = lo.getUidteach();
          price2 = lo.getPrice();
          Phone1 = lo.getPhone();
          pd.dismiss();
          confirmation();
        }

      }
    }
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }
  };
  public void confirmation(){
    /**
     * Opens up AlertDialog for the confirmation of the Lesson.
     * <p>
     *
     */

    priceofferdial = (LinearLayout) getLayoutInflater().inflate(R.layout.priceofferdial, null);

    tvnamee1 = (TextView) priceofferdial.findViewById(R.id.tvnamee1);
    tvPhonee1 = (TextView) priceofferdial.findViewById(R.id.tvPhonee1);
    tvAbout = (TextView) priceofferdial.findViewById(R.id.tvAbout);
    tvExperience = (TextView) priceofferdial.findViewById(R.id.tvExperience);
    tvDate1 = (TextView) priceofferdial.findViewById(R.id.tvDate2);
    tVprice = (TextView) priceofferdial.findViewById(R.id.tVprice1);
    tvSubject1 = (TextView) priceofferdial.findViewById(R.id.tvSubject1);

    tvnamee1.setText("Name: " + Name1);
    tvPhonee1.setText("Phone: " + Phone1);
    tvAbout.setText("About the teacher: " + About);
    tvExperience.setText("Experienced in: " + Experience);
    tvDate1.setText("Date: "+ Date);
    tvSubject1.setText("Subject: " + Sbjct1);
    tVprice.setText("The price for 1 hour is: "  + price);

    adb = new AlertDialog.Builder(this);
    adb.setView(priceofferdial);
    adb.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        /**
         * When accepted it will update the teacher's uid, order status, if the order is still active and the price,
         * it will sent the user to the history activity.
         * <p>
         *
         * @param	DialogInterface dialog, int which.
         */
        status = 2;
        locationObject1.setStatus(status);
        locationObject1.setAct(false);
        lo.setAct(false);
        locationObject1.setUidteach(uidteach);
        locationObject1.setPrice(price2);
        refLessonOffer.child("" + lo.getCount()).setValue(lo);
        refLocations.child("" + lo.getCount()).setValue(locationObject1);
        Toast.makeText(StudentActivity.this, "Lesson Accepted", Toast.LENGTH_LONG).show();
        intent = new Intent(StudentActivity.this,HistoryActivity.class);
        startActivity(intent);
      }
    });
    adb.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        /**
         * When cancelled it will update the order status and will keep searching for another teacher.
         * <p>
         *
         * @param	DialogInterface dialog, int which.
         */
        status = 0;
        locationObject1.setStatus(status);
        refLocations.child("" + lo.getCount()).setValue(locationObject1);
        Toast.makeText(StudentActivity.this, "Lesson Declined", Toast.LENGTH_LONG).show();
        dialog.cancel();
        pd.show();
      }
    });
    AlertDialog ad = adb.create();
    ad.show();
  }

  public void order(View view) {
    /**
     * The action will upload the order to firebase and it will search for an offer.
     * <p>
     *
     * @param view Button on click operate the action.
     */
    test = tv5.getText().toString();
    subject = targetSubject.getText().toString();
    if (eventdate.isEmpty()) TVD.setError("You must pick a date");
    if (subject.isEmpty()) targetSubject.setError("You must enter your subject");
    status = 1;
    DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
    mDatabaseRef.child("OrderReq").addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
          count = dataSnapshot.getChildrenCount();
          count = count + 1;
          locationObject1 = new LocationObject(test, subject, eventdate, UID, name, phone, sclass, status, true, count,"","");
          refLocations.child("" + count).setValue(locationObject1);
          Toast.makeText(StudentActivity.this, locationObject1.getMyLocation(), Toast.LENGTH_SHORT).show();
          pd = ProgressDialog.show(StudentActivity.this, "Search", "Searching...", true);
          Query query = refLessonOffer
                  .orderByChild("count")
                  .equalTo(count);
          query.addValueEventListener(VEL);
        } else {
          count = 1;
          locationObject1 = new LocationObject(test, subject, eventdate, UID, name, phone, sclass, status, true, count,"","");
          refLocations.child("" + count).setValue(locationObject1);
          Toast.makeText(StudentActivity.this, locationObject1.getMyLocation(), Toast.LENGTH_SHORT).show();
          pd = ProgressDialog.show(StudentActivity.this, "Search", "Searching...", true);
          Query query = refLessonOffer
                  .orderByChild("count")
                  .equalTo(count);
          query.addValueEventListener(VEL);
        }
      }
      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {
      }
    });
  }

  public boolean onCreateOptionsMenu(Menu menu){
    getMenuInflater().inflate(R.menu.main,menu);
    return true;
  }
  public boolean onOptionsItemSelected(MenuItem item){
    String st = item.getTitle().toString();
    if(st.equals("Order History")) {
      intent = new Intent(StudentActivity.this, HistoryActivity.class);
      startActivity(intent);
    }
    if(st.equals("Profile")) {
      intent = new Intent(StudentActivity.this, ProfileActivity.class);
      startActivity(intent);
    }
    if(st.equals("Credits")) {
      intent = new Intent(StudentActivity.this, CreditsActivity.class);
      startActivity(intent);
    }
    return true;
  }


}
