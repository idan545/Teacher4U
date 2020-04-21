package com.example.betaidan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
  /**
   * @author		Idan Cohen
   * @version	    V1.0
   * @since		19/4/2020
   * The first activity that shows up on the user's screen!.
   */

  Intent t;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }
  /**
   * Go to the next Activity
   * <p>
   *
   * @param	view Button	if clicked operate the action.
   */

  public void go(View view) {
    t=new Intent(MainActivity.this , EntranceActivity.class);
    startActivity(t);
  }
}