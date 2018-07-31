package com.example.mohamed.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohamed.myapplication.Common.Common;
import com.example.mohamed.myapplication.models.User;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity {

    EditText name , password;
    Button log;

    String url;
    RequestQueue requestQueue;
    String Token;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        url = "https://mysiteandroid.000webhostapp.com/checklogin.php";

        name=(EditText)findViewById(R.id.etname);
        password=(EditText)findViewById(R.id.etpass);
        log=(Button)findViewById(R.id.log);




        SharedPreferences share = getSharedPreferences("da", Context.MODE_PRIVATE);
         Token = share.getString("Token", null);


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("Token is ", FirebaseInstanceId.getInstance().getToken());
                Toast.makeText(SignIn.this, "token is "+FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_SHORT).show();


                if (name.getText().toString().isEmpty()) {
                    name.setError("please enter name");
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("please enter password");
                }  else{
                    select();
                }
            }
        });

    }

    void load() {


        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);

                        String name = object.getString("name");
                        String password = object.getString("password");
                        
                        if (name=="m" & password=="m"){
                            Toast.makeText(SignIn.this, "good", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SignIn.this, "bad", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    void select() {
        requestQueue = Volley.newRequestQueue(this);


        url = "http://opec.pioneers-solutions.info/api/Account/Login";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SignIn.this, "sucess", Toast.LENGTH_SHORT).show();
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignIn.this, "error", Toast.LENGTH_SHORT).show();
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Username", name.getText().toString());
                params.put("Password", password.getText().toString());


                params.put("Token", Token);
             //   params.put("Token", FirebaseInstanceId.getInstance().getToken());



                return params;

            }
        };

        requestQueue.add(postRequest);
    }


    public void goSignup(View view) {
        Intent intent=new Intent(SignIn.this,SignUp.class);
        startActivity(intent);
    }
}
