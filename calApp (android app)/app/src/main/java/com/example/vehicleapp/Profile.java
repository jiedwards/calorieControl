package com.example.vehicleapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class Profile extends AppCompatActivity {

    public String API;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("apiInfo", Context.MODE_PRIVATE);
        API = sharedPreferences.getString("API", "");

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder() .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final HashMap<String, String> params = new HashMap<>();

        final EditText fName = findViewById(R.id.editfName);
        final EditText lName = findViewById(R.id.editlName);
        final TextView uName = findViewById(R.id.edituName);
        final EditText pWord = findViewById(R.id.editpWord);
        final TextView calories = findViewById(R.id.editCalories);
        final EditText activityLevel = findViewById(R.id.editActivityLevel);
        final EditText height = findViewById(R.id.editHeight);
        final EditText weight = findViewById(R.id.editWeight);
        final EditText gender = findViewById(R.id.editGender);
        final EditText email = findViewById(R.id.editEmail);
        final EditText age = findViewById(R.id.editAge);
        final Button logoutB = findViewById(R.id.logoutB);


        final TextView editvHeader = findViewById(R.id.editHeader);
        final FloatingActionButton updateFloat = findViewById(R.id.updateFloat);
        Bundle extras = getIntent().getExtras();
        final Users userData = (Users) extras.get("profile");


        System.out.println("TESTINGNOWFRIENED" + userData.getUsername());

        editvHeader.setText( "My Account" );
        uName.setText(userData.getUsername());
        pWord.setText(userData.getPassword());
        fName.setText(userData.getFirstname());
        lName.setText(userData.getSurname());
        calories.setText(String.valueOf(userData.getCalories()));
        activityLevel.setText(String.valueOf(userData.getActivitylevel()));
        email.setText(userData.getEmail());
        height.setText(String.valueOf(userData.getHeight()));
        weight.setText(String.valueOf(userData.getWeight()));
        age.setText(String.valueOf(userData.getAge()));


        if(userData.getGender() == true)
        {
            gender.setText("Male");
        }
        else {
            gender.setText("Female");
        }



        logoutB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (API != "") {
                    SharedPreferences sharedPreferences = getSharedPreferences("apiInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("API", "");
                    editor.apply();

                    Intent home = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(home);
                }
            }
        });

        updateFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new Gson();
                String username = uName.getText().toString();
                String password = pWord.getText().toString();
                String firstname = fName.getText().toString();
                String lastname = lName.getText().toString();
                Double activitylevel = Double.valueOf(activityLevel.getText().toString());
                String emailU = email.getText().toString();
                Double heightU = Double.valueOf(height.getText().toString());
                Double weightU = Double.valueOf(weight.getText().toString());
                Integer ageU = Integer.valueOf(age.getText().toString());

                String cal;
                Integer caloriesU;
                Boolean genderU;
                Integer BMR;
                if (gender.getText().toString() == "Male"){
                     genderU = true;
                    caloriesU = Integer.valueOf((int) Math.round(88 + (13.397 * weightU) + (4.799 * heightU) - (5.677 * ageU)));
                        }
                else {
                     genderU = false;
                    caloriesU = Integer.valueOf((int) Math.round(447.593 + (9.247 * weightU) + (3.098 * heightU) - (4.330 * ageU)));

                }

                System.out.println("WHAT IS THIS VALUE" + genderU + "-" + gender.getText().toString());




                Users u = new Users(username, password, firstname, lastname, emailU, caloriesU, activitylevel, heightU, weightU, genderU, ageU);
                String usersJson = gson.toJson(u);
                params.put("json", usersJson);

                String url = "http://10.0.2.2:8000/users/apiKey";
                performPostCall(url, params);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
            }
        });



    }

    public String performPostCall(String requestURL, HashMap<String, String> postDataParams) {

        URL url;
        String response = "";

        try {
            url = new URL(requestURL);

            //create the connection object
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("PUT");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //write/send/POST data to the connection using output stream and buffered writer

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            //write/send/POST key/value data (url encoded) to the server
            writer.write(getPostDataString(postDataParams));

            //clear the writer
            writer.flush();
            writer.close();
            //close output stream
            os.close();

            //get the server response code to determine what to do next (i.e. success/error)

            int responseCode=conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                Toast.makeText(this, "User updated :)", Toast.LENGTH_LONG). show();
                String line;
                BufferedReader br= new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;

                }
            }
            else {
                Toast.makeText(this, "Failed to update user :(", Toast.LENGTH_LONG).show();
                response = "";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response = " + response);
        return response;
    }
    //this method converts a hashmap to a URL query string of key/value pairs e.g.: name=kaleem&job=tutor&...)
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
