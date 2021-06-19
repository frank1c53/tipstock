package com.organization.tipstock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.time.LocalDate;
import java.util.Date;

public class LoginSuccessActivity extends AppCompatActivity {
    String phone,member,exdate;
    TextView Logged;
    DBHelper DB ;
    LocalDate date = LocalDate.now();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        Bundle bundle = getIntent().getExtras();
        Logged = (TextView) findViewById(R.id.Logged);
        phone = bundle.getString("phone");
        DB = new DBHelper(this);
        LocalDate expdate = date.plusDays(14);
        Cursor res = DB.getData();

            if(res.getCount() == 0){
            boolean insertdata = DB.insertUserData(phone, "trail",expdate.toString());
            if(insertdata == true){
                Toast.makeText(getApplicationContext(),"Data inserted successfully",Toast.LENGTH_LONG).show();
                StringBuffer buffer = new StringBuffer();
                Cursor res1 = DB.getData();
                while (res1.moveToNext()){
                    buffer.append("Phone:"+res1.getString(0)+"\n");
                    buffer.append("Subs:"+res1.getString(1)+"\n");
                    buffer.append("Exp Date:"+res1.getString(2)+"\n");
                    member = res1.getString(1) ;
                    exdate = res1.getString(2);

                }

                RequestQueue queue = Volley.newRequestQueue(this);
                String url ="https://script.google.com/macros/s/AKfycby3iqR2JnNUd7wO7TVgN7gH84Oi5hX1TIUA-wbtDp5d72jort4JfkCgmEVMNgzbY4O5/exec?phone="+phone+"&member="+member+"&expdate="+exdate;

// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Logged.setText("Response is: "+ response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logged.setText("That didn't work!");
                    }
                });

// Add the request to the RequestQueue.
                queue.add(stringRequest);






                AlertDialog.Builder builder = new AlertDialog.Builder(LoginSuccessActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }else {
                Toast.makeText(getApplicationContext(),"Data Insertion Unsuccessful",Toast.LENGTH_LONG).show();
            }
            return;
        }else{
            StringBuffer buffer = new StringBuffer();
            while (res.moveToNext()){
                buffer.append("Phone:"+res.getString(0)+"\n");
                buffer.append("Subs:"+res.getString(1)+"\n");
                buffer.append("Exp Date:"+res.getString(2)+"\n");
                member = res.getString(1) ;
                exdate = res.getString(2);

            }

            AlertDialog.Builder builder = new AlertDialog.Builder(LoginSuccessActivity.this);
            builder.setCancelable(true);
            builder.setTitle("User Entries");
            builder.setMessage(buffer.toString());
            builder.show();

        }




    }
}