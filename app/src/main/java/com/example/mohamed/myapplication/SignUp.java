package com.example.mohamed.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity implements Animation.AnimationListener{

    RequestQueue requestQueue;


    EditText username, password, email, phone, name;
    TextView Sign;
    String url;


    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        username = (EditText) findViewById(R.id.edtusername);
        password = (EditText) findViewById(R.id.edtpassword);
        email = (EditText) findViewById(R .id.edtemail);
        phone = (EditText) findViewById(R.id.edtmobile);
        name = (EditText) findViewById(R.id.edtname);
        Sign = (TextView) findViewById(R.id.signUp);


        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();

            }
        });


        TextView sign=(TextView)findViewById(R.id.sign);
        animation=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        animation.setAnimationListener(this);
        sign.startAnimation(animation);




    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }





    private void signUp() {

        if (username.getText().toString().isEmpty()) {
            username.setError("please enter username");
        } else if (password.getText().toString().isEmpty()) {
            password.setError("please enter password");
        } else if (email.getText().toString().isEmpty()) {
            email.setError("please enter email");
        } else if (phone.getText().toString().isEmpty()) {
            phone.setError("please enter phone");
        } else if (name.getText().toString().isEmpty()) {
            name.setError("please enter name");
        } else {

            sendData();

        }
    }

    void sendData() {


        requestQueue = Volley.newRequestQueue(this);


        url = "http://lamsa.pioneers-solutions.org/Api/Helper/AddUser?" + "Username="
                + username.getText().toString() + "&Password=" + password.getText().toString() + "&Mobile=" + phone.getText().toString() + "&Email=" + email.getText().toString() + "&Name=" + name.getText().toString();

//        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(SignUp.this, "good", Toast.LENGTH_SHORT).show();
//                        // response
//                        Log.d("Response", response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(SignUp.this, "error", Toast.LENGTH_SHORT).show();
//                        // error
//                        Log.d("Error.Response", String.valueOf(error));
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Username", username.getText().toString());
//                params.put("Password", password.getText().toString());
//                params.put("Mobile", phone.getText().toString());
//                params.put("Email", email.getText().toString());
//                params.put("Name", name.getText().toString());
//
//                return params;
//
//            }
//        };


        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(SignUp.this, "good", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUp.this, "error", Toast.LENGTH_SHORT).show();
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Username", username.getText().toString());
                params.put("Password", password.getText().toString());
                params.put("Mobile", phone.getText().toString());
                params.put("Email", email.getText().toString());
                params.put("Name", name.getText().toString());

                return params;

            }
        };


        requestQueue.add(postRequest);


//        Response.Listener<String> listener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    boolean success = jsonObject.getBoolean("success");
//                    if (success) {
//                        Toast.makeText(SignUp.this, "done", Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        Toast.makeText(SignUp.this, "failed", Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//

//          final String send_uel = "http://lamsa.pioneers-solutions.org/Api/Helper/AddUser?" + "Username="
//                + usernam + "&Password=" + pass + "&Mobile=" + pho + "&Email=" + emai + "&Name=" + usernam;
//         Map<String, String> m_array;
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://lamsa.pioneers-solutions.org/Api/Helper/AddUser?" + "&Username="
//                + usernam + "&Password=" + pass + "&Mobile=" + pho + "&Email=" + emai + "&Name=" + usernam,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                    }
//                }
//
//
//                Send_data_SignUp send_data_registed = new Send_data_SignUp(Username, Pssaword, Phone, Email, Name, listener);
//        requestQueue = Volley.newRequestQueue(SignUp.this);
//        requestQueue.add(send_data_registed);
//
//
//    }

    }

}
