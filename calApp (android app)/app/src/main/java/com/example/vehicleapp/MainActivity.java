package com.example.vehicleapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    public String API;
    private TextView header;
    private TextView macroHeader;
    public Users user;
    public Users userData;
    private String text;


    Gson gson = new Gson();
    final HashMap<String, String> params = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        final TextView calText = findViewById(R.id.calorieText);

        header = (TextView) findViewById(R.id.testText);
        macroHeader = (TextView) findViewById(R.id.macroText);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setSubtitle("");
        getSupportActionBar().setIcon(R.drawable.kcal2);

        SharedPreferences sharedPreferences = getSharedPreferences("apiInfo", Context.MODE_PRIVATE);
        API = sharedPreferences.getString("API", "");
        if (API != "") {

            HttpURLConnection urlConnection;
            BufferedInputStream in = null;
            try {
                /* The URL which the app is retrieving the vehicles from, there is also an API key attached. */
                URL url = new URL("http://10.0.2.2:8000/users/apiKey?username="+API);
                urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            String data = convertStreamToString(in);


            try {

                JSONObject jsonArray = new JSONObject(data);

                String username = jsonArray.get("username").toString();
                String password = jsonArray.get("password").toString();
                String firstname = jsonArray.get("firstname").toString();
                String lastname = jsonArray.get("surname").toString();
                Double activitylevel = Double.valueOf(jsonArray.get("activitylevel").toString());
                Integer calories = Integer.valueOf(jsonArray.get("calories").toString());
                String email = jsonArray.get("email").toString();
                Double height = Double.valueOf(jsonArray.get("height").toString());
                Double weight = Double.valueOf(jsonArray.get("weight").toString());
                Boolean gender = Boolean.valueOf(jsonArray.get("gender").toString());
                Integer age = Integer.valueOf(jsonArray.get("age").toString());



                Users userData = new Users(username, password, firstname, lastname, email, calories, activitylevel, height, weight, gender, age);

                System.out.println("TESTER BROTHER" + userData);


                header.setText("Welcome "+ userData.getUsername() + " your recommended caloric intake is " + userData.getCalories());

                macroHeader.setText("For a 50% P/30% C/ 20% F split." + System.getProperty("line.separator") + Math.round((Integer.valueOf(userData.getCalories()) * 0.5) / 4)  + "G PROTEIN"
                        + System.getProperty("line.separator") +
                        Math.round((Integer.valueOf(userData.getCalories()) * 0.3) / 4)  + "G CARBS" +
                        System.getProperty("line.separator") +
                        Math.round((Integer.valueOf(userData.getCalories()) * 0.2) / 9)  + "G FAT");



                calText.setText(String.valueOf(userData.getCalories()));
                user = userData;

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

    }



    public String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    /* The method below allows a hashmap to be converted into a string of key/value pairs
     * which are suitable to be used in a URL. */

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        String msg = " ";
        switch (item.getItemId()) {
            case R.id.account:
                msg = "Account";
                if (API != "") {

                    System.out.println("TESTER BROTHER- BRAND NEWS" + user);

                    Intent account = new Intent(getApplicationContext(), Profile.class);
                    account.putExtra("profile", user);
                    startActivity(account);
                }

                else {
                    Intent api = new Intent(getApplicationContext(), apiKey.class);
                    startActivity(api);
                }
                Toast.makeText(this, msg + " Checked", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
