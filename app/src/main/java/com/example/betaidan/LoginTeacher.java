package com.example.betaidan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import static com.example.betaidan.FBref.refAuth;

import static com.example.betaidan.FBref.refTeacher;

public class LoginTeacher extends AppCompatActivity {
  /**
   * @author		Idan Cohen
   * @version	    V1.0
   * @since		5/4/2020
   * Login/Register Activity for Teacher
   */


  TextView tVtitle, tVregister;
  EditText eTname, eTphone, eTemail, eTpass,eTexp,eTab;
  CheckBox cBstayconnect;
  Button btn;
  boolean yes = false;
  String name, phone, email, password, uid,Experience,About;
  Teacher teacher;
  Boolean stayConnect, registered;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_teacher);
    tVtitle=(TextView) findViewById(R.id.tVtitle);
    eTname=(EditText)findViewById(R.id.eTname);
    eTexp=(EditText)findViewById(R.id.eTexp);
    eTab=(EditText)findViewById(R.id.eTab);
    eTemail=(EditText)findViewById(R.id.eTemail);
    eTpass=(EditText)findViewById(R.id.eTpass);
    eTphone=(EditText)findViewById(R.id.eTphone);
    cBstayconnect=(CheckBox)findViewById(R.id.cBstayconnect);
    tVregister=(TextView) findViewById(R.id.tVregister);
    btn=(Button)findViewById(R.id.btn);

    stayConnect=false;
    registered=true;

    regoption();
  }

  protected void onStart() {
    /**
     * Checks if the user already checked the "Stay Connected" button.
     * <p>
     *
     */
    super.onStart();
    SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
    Boolean isChecked=settings.getBoolean("stayConnect",false);
    Intent si = new Intent(LoginTeacher.this,TeacherActivity.class);
    if (refAuth.getCurrentUser()!=null && isChecked) {
      stayConnect=true;
      startActivity(si);
    }



  }

  protected void onPause() {
    super.onPause();
    if (stayConnect) finish();
  }

  private void regoption() {
    /**
     * Switches the screen from Login to Register.
     * <p>
     */
    SpannableString ss = new SpannableString("Don't have an account?  Register here!");
    ClickableSpan span = new ClickableSpan() {
      @Override
      public void onClick(View textView) {
        tVtitle.setText("Register");
        eTname.setVisibility(View.VISIBLE);
        eTphone.setVisibility(View.VISIBLE);
        eTexp.setVisibility(View.VISIBLE);
        eTab.setVisibility(View.VISIBLE);

        btn.setText("Register");
        registered=false;
        logoption();
      }
    };
    ss.setSpan(span, 24, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    tVregister.setText(ss);
    tVregister.setMovementMethod(LinkMovementMethod.getInstance());
  }

  private void logoption() {
    /**
     * Switches the screen from Register to Login.
     * <p>
     */
    SpannableString ss = new SpannableString("Already have an account?  Login here!");
    ClickableSpan span = new ClickableSpan() {
      @Override
      public void onClick(View textView) {
        tVtitle.setText("Login");
        eTname.setVisibility(View.INVISIBLE);
        eTphone.setVisibility(View.INVISIBLE);
        eTexp.setVisibility(View.INVISIBLE);
        eTab.setVisibility(View.INVISIBLE);
        btn.setText("Login");
        registered=true;
        regoption();
      }
    };
    ss.setSpan(span, 26, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    tVregister.setText(ss);
    tVregister.setMovementMethod(LinkMovementMethod.getInstance());
  }

  public void logorreg(View view) {
    /**
     * Checks if the user is registered and logging in, else it will register.
     * <p>
     *
     * @param	view Button	on click operate the action.
     */
    if (registered) {
      email=eTemail.getText().toString();
      password=eTpass.getText().toString();

      final ProgressDialog pd=ProgressDialog.show(this,"Login","Connecting...",true);
      refAuth.signInWithEmailAndPassword(email, password)
              .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  pd.dismiss();
                  if (task.isSuccessful()) {
                    SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                    SharedPreferences.Editor editor=settings.edit();
                    editor.putBoolean("stayConnect",cBstayconnect.isChecked());
                    editor.commit();
                    Log.d("MainActivity", "signinUserWithEmail:success");
                    Toast.makeText(LoginTeacher.this, "Login Success", Toast.LENGTH_LONG).show();
                    Intent si = new Intent(LoginTeacher.this,TeacherActivity.class);
                    startActivity(si);
                  } else {
                    Log.d("MainActivity", "signinUserWithEmail:fail");
                    Toast.makeText(LoginTeacher.this, "e-mail or password are wrong!", Toast.LENGTH_LONG).show();
                  }
                }
              });
    } else {
      name=eTname.getText().toString();
      phone=eTphone.getText().toString();
      email=eTemail.getText().toString();
      password=eTpass.getText().toString();
      Experience=eTexp.getText().toString();
      About=eTab.getText().toString();

      final ProgressDialog pd=ProgressDialog.show(this,"Register","Registering...",true);
      refAuth.createUserWithEmailAndPassword(email, password)
              .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  pd.dismiss();
                  if (task.isSuccessful()) {
                    SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                    SharedPreferences.Editor editor=settings.edit();
                    editor.putBoolean("stayConnect",cBstayconnect.isChecked());
                    editor.commit();
                    Log.d("MainActivity", "createUserWithEmail:success");
                    FirebaseUser user = refAuth.getCurrentUser();
                    uid = user.getUid();
                    teacher=new Teacher( name, phone, email, Experience, About, uid,  yes);
                    refTeacher.child(uid).setValue(teacher);
                    Toast.makeText(LoginTeacher.this, "Successful registration", Toast.LENGTH_LONG).show();
                    Intent si = new Intent(LoginTeacher.this,TeacherActivity.class);
                    startActivity(si);
                  } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException)
                      Toast.makeText(LoginTeacher.this, "User with e-mail already exist!", Toast.LENGTH_LONG).show();
                    else {
                      Log.w("MainActivity", "createUserWithEmail:failure", task.getException());
                      Toast.makeText(LoginTeacher.this, "User create failed.",Toast.LENGTH_LONG).show();
                    }
                  }
                }
              });
    }
  }


}