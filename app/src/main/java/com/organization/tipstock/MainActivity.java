package com.organization.tipstock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    Button login;
    String user,pass;


    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper DB ;

        password=(EditText) findViewById(R.id.password);
        login=findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        DB = new DBHelper(this);
        Cursor res = DB.getData();
        if(res.getCount() == 0){
            Toast.makeText(MainActivity.this,"No Entry", Toast.LENGTH_LONG).show();




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pass=password.getText().toString();
                if(pass.length() == 0 || pass.length() > 10 || pass.length() < 10 ){
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Kindly Enter 10 digit Mobile No");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else{
                    Intent i = new Intent(getApplicationContext(),VerifyActivity.class);
                    i.putExtra("phone",pass);
                    startActivity(i);
                    finish();
                }
            }
        });


        }else{
            StringBuffer buffer = new StringBuffer();

            while (res.moveToNext()){
                buffer.append(res.getString(0));
            }
            Intent i = new Intent(getApplicationContext(),LoginSuccessActivity.class);
            i.putExtra("phone",buffer.toString());
            startActivity(i);
            finish();

        }


    }
}