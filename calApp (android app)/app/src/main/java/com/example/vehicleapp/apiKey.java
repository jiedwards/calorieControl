package com.example.vehicleapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

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

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class apiKey extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String API = "api";
    public String apiKey;
    private String text;
    public EditText apiText;
    public EditText pwordField;

    public int codeFinal;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_key);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder() .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final Button apiButton = findViewById(R.id.keyButton);
        final Button newAcc = findViewById(R.id.newAcc);
        final HashMap<String, String> params = new HashMap<>();
        apiText = findViewById(R.id.apiText);
        pwordField = findViewById(R.id.apiText2);


        apiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String username = apiText.getText().toString();
                String password = pwordField.getText().toString();

                Users u = new Users(username, password, "none", "none", "none", 0,0.0,0.0,0.0, true, 0);

                String userJson = gson.toJson(u);
              //  Vehicle v = new Vehicle(999, make, model, year, price, license_number, colour, number_doors, transmission, mileage, fuel_type, engine_size, body_style, condition, notes, false);
                //String vehicleJson = gson.toJson(v);
                //System.out.println(vehicleJson);
                params.put("json", userJson);
                String url = "http://10.0.2.2:8000/users/apiKey";
                System.out.println("TESTING " + userJson);
                performPostCall(url, params);
                saveData();
                System.out.println(API);
            }
        });

        newAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newAccount = new Intent(getApplicationContext(), newAccount.class);
                startActivity(newAccount);
            }
        });

        loadData();
        updateView();
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
            conn.setRequestMethod("POST");
            conn.setRequestProperty("postType", "login");
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

            codeFinal = responseCode;

            System.out.println("responseCode = " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                Toast.makeText(this, "User Login Is Successful", Toast.LENGTH_LONG). show();
                String line;
                BufferedReader br= new BufferedReader(new InputStreamReader(conn.getInputStream()));
                System.out.println("TEST");
                System.out.println(conn.getResponseMessage());
                System.out.println(conn.getRequestMethod());
                System.out.println(responseCode == HttpsURLConnection.HTTP_OK);

                while ((line=br.readLine()) != null) {
                    response+=line;

                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


            }
            else {
                Toast.makeText(this, "Incorrect login information. Try again.", Toast.LENGTH_LONG).show();
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

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("apiInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        if (codeFinal == 200) {
            editor.putString("API", apiText.getText().toString());
            editor.apply();
            System.out.println("TESTEST123WORKS" + codeFinal);

            String testing = apiText.getText().toString();
            System.out.println("TESTEST123WORKS" + testing);
            System.out.println("TESTEST123WORKS" + pwordField);


        }

        // Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("apiInfo", Context.MODE_PRIVATE);


        text = sharedPreferences.getString("API", "");
    }

    public void updateView() {
        apiText.setText(text);

    }
}
