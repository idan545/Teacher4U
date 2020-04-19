package com.example.betaidan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.betaidan.Student;
import com.example.betaidan.Teacher;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.example.betaidan.FBref.refAuth;
import static com.example.betaidan.FBref.refImages;
import static com.example.betaidan.FBref.refLessonOffer;
import static com.example.betaidan.FBref.refTeacher;
import static com.example.betaidan.FBref.refstudent;

import java.io.File;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
  TextView nameview,phoneview,classview,aboutview,expview;
  ImageView iV;
  String UID,name,phone,sclass,about,exp,name1,phone1;
  Intent intent;
  int Gallery=1;
  Student student;
  Teacher teacher;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    nameview = (TextView) findViewById(R.id.nameview);
    phoneview = (TextView) findViewById(R.id.phoneview);
    classview = (TextView) findViewById(R.id.classview);
    aboutview = (TextView) findViewById(R.id.aboutview);
    expview = (TextView) findViewById(R.id.expview);
    iV=(ImageView)findViewById(R.id.iV);

    FirebaseUser firebaseUser = refAuth.getCurrentUser();
    UID = firebaseUser.getUid();

    Query query = refstudent
            .orderByChild("uid")
            .equalTo(UID);
    query.addListenerForSingleValueEvent(VEL);

    Query query2 = refTeacher
            .orderByChild("uid")
            .equalTo(UID);
    query2.addListenerForSingleValueEvent(VEL2);
  }

  com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dS) {
      if (dS.exists()) {
        for(DataSnapshot data : dS.getChildren()) {
          student = data.getValue(Student.class);
          name = student.getName();
          Toast.makeText(ProfileActivity.this, name, Toast.LENGTH_LONG).show();
          phone = student.getPhone();
          sclass = student.getSClass();
        }
        studentprof();

      }
    }
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }
  };

  com.google.firebase.database.ValueEventListener VEL2 = new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dS) {
      if (dS.exists()) {
        for(DataSnapshot data : dS.getChildren()) {
          teacher = data.getValue(Teacher.class);
          name1 = teacher.getName();
          phone1 = teacher.getPhone();
          about = teacher.getAbout();
          exp = teacher.getExperience();
          teacherprof();
        }

      }
    }
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }
  };

  /**
   * Selecting image file to upload to Firebase Storage
   * <p>
   *
   * @param view
   */
  public void upload(View view) {
    Intent si = new Intent(Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(si, Gallery);
  }

  /**
   * Uploading selected image file to Firebase Storage
   * <p>
   *
   * @param requestCode   The call sign of the intent that requested the result
   * @param resultCode    A code that symbols the status of the result of the activity
   * @param data          The data returned
   */
  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == Activity.RESULT_OK) {
      if (requestCode == Gallery) {
        Uri file = data.getData();
        if (file != null) {
          final ProgressDialog pd=ProgressDialog.show(this,"Upload image","Uploading...",true);
          StorageReference refImg = refImages.child("aaa.jpg");
          refImg.putFile(file)
                  .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      pd.dismiss();
                      Toast.makeText(ProfileActivity.this, "Image Uploaded", Toast.LENGTH_LONG).show();
                    }
                  })
                  .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                      pd.dismiss();
                      Toast.makeText(ProfileActivity.this, "Upload failed", Toast.LENGTH_LONG).show();
                    }
                  });
        } else {
          Toast.makeText(this, "No Image was selected", Toast.LENGTH_LONG).show();
        }
      }
    }
  }

  /**
   * Downloading selected image file from Firebase Storage
   * <p>
   *
   * @param view
   */
  public void download(View view) throws IOException {
    final ProgressDialog pd=ProgressDialog.show(this,"Image download","downloading...",true);

    StorageReference refImg = refImages.child("aaa.jpg");

    final File localFile = File.createTempFile("aaa","jpg");
    refImg.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
      @Override
      public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
        pd.dismiss();
        Toast.makeText(ProfileActivity.this, "Image download success", Toast.LENGTH_LONG).show();
        String filePath = localFile.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        iV.setImageBitmap(bitmap);
      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception exception) {
        pd.dismiss();
        Toast.makeText(ProfileActivity.this, "Image download failed", Toast.LENGTH_LONG).show();
      }
    });
  }

  private void studentprof() {

    classview.setVisibility(View.VISIBLE);


    nameview.setText(name);
    phoneview.setText(phone);
    classview.setText(sclass);
  }

  private void teacherprof() {

    aboutview.setVisibility(View.VISIBLE);
    expview.setVisibility(View.VISIBLE);

    nameview.setText(name1);
    phoneview.setText(phone1);
    aboutview.setText(about);
    expview.setText(exp);
  }
}



 /*   public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        String st = item.getTitle().toString();
        if(st.equals("Order History")) {
            intent = new Intent(ProfileActivity.this, HistoryActivity.class);
            startActivity(intent);
        }
        if(st.equals("Credits")) {
            intent = new Intent(ProfileActivity.this, CreditsActivity.class);
            startActivity(intent);
        }
        return true;
    }
  */