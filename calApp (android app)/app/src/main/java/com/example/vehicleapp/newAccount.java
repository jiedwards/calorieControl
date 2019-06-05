package com.example.vehicleapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import javax.xml.transform.Result;

public class newAccount extends AppCompatActivity {

    /* The declaration of the API key for the user is below, as well as the hashmap which is required for the postcall.*/

    public String API = "9HADV2G2CE030921";
    final HashMap<String, String> params = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        /* Below is the execution of the class which retrieves the vehicle information
         * which has been transmitted from the detailsActivity, this code is a necessity for the application to be asynchronous,
         * it also ensures that the vehicles are retrieved as soon as the class begins. */

        NewVehicle newData = new NewVehicle();
        newData.execute();
    }

    /* The updateVehicle class executes the operation to locate the input boxes and receive the new data input.
     * It completes the task in an asynchronous manner, by executing a doInBackground, preExecute and postExecute. */

    class NewVehicle extends AsyncTask<String, Void, Result> {

        /* The declaration for all the input fields are declared within the class,
         * along with recieving the data which has been sent from the previous intent execution. */

        final EditText fName = findViewById(R.id.fName);
        final EditText lName = findViewById(R.id.lName);
        final EditText uName = findViewById(R.id.uName);
        final EditText password = findViewById(R.id.pWord);
        final EditText email = findViewById(R.id.email);
        public FloatingActionButton newFloat = findViewById(R.id.newFloat);


        @Override
        protected Result doInBackground(String... strings) {

            return null;
        }

        /* Upon the completion of doInBackground, onPostExecute is executed and delivers
         * the main UI thread whilst stopping the async process */

        @Override
        protected void onPostExecute(Result result) {

            /* Upon button click, the updated information entered into the input boxes is retrieved using getText
             * and stored in variables which will later be inserted into an object. */

            newFloat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Gson gson = new Gson();
                    String firstName = fName.getText().toString();
                    String lastName = lName.getText().toString();
                    String userName = uName.getText().toString();
                    String passWord = password.getText().toString();
                    String eMail = email.getText().toString();


                    /* The inputted vehicle information is stored in an object called v and will be used. */

                    Users u = new Users(userName, passWord, firstName, lastName, eMail, 0, 0.0, 0.0, 0.0, true, 0);
                    final String usersJson = gson.toJson(u);

                    /* To execute another HTTP request within this activity, it is preferrable
                     * to take place in a separate doInBackground method, therefore a new class has been declared
                     * and the required information will be sent to it, using the execute call below. */

                    String url = "http://10.0.2.2:8000/users/apiKey";
                    params.put("json", usersJson);
                    performPostCall(url, params);

                    /* An intent specifies a movement of activity,
                     * in this instance the activity is changed back to the main activity class when the button click is fired. */

                    Intent intentUpdate = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentUpdate);

                    System.out.println("TESTING" + usersJson);


                }
            });
        }
    }

    /* Another class is created to execute the request. Using String in the header will allow the class will receive the necessary
     * information which has previously been sent in the code. The request is executed using the performPostCall
     * along with it's required parameters. */

    /* The performPostCall is required to execute the POST/INSERT request above.
     * It requires the URL where the request is to be made, along with a hashmap of information. */

    public String performPostCall(String requestURL, HashMap<String, String> postDataParams) {

        URL url;
        String response = "";

        try {
            url = new URL(requestURL);

            //create the connection object
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);

            /* A declaration of request type, along with a property attached which contains the API authorisation key. */

            conn.setRequestMethod("POST");
            conn.setRequestProperty("postType", "newUser");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //write/send/POST data to the connection using output stream and buffered writer

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            /* The response code is retrieved from the server, this allows the user to determine whether the request
             * was a success or not. The if/else statement determines what occurs based on the response code. */

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "User is successfully inserted", Toast.LENGTH_SHORT).show();
                    }
                });
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;

                }

            } else {
                response = "failed";
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "User failed to insert", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response = " + response);
        return response;
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
}
