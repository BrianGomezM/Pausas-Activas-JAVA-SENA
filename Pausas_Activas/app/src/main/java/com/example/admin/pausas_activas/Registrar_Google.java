package com.example.admin.pausas_activas;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.admin.pausas_activas.Clase_Pojo.Clase_Pojo;
import com.example.admin.pausas_activas.LogueoGoogle.AbstractGetNameTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Registrar_Google extends AppCompatActivity {
    String nombreGMAIL, correoGMAIL, textGender, textBirthday, userImageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_registrar__google);

        /*imageProfile = (ImageView) findViewById(R.id.imageView1);
        textViewName = (TextView) findViewById(R.id.textViewNameValue);
        textViewEmail = (TextView) findViewById(R.id.textViewEmailValue);
        textViewGender = (TextView) findViewById(R.id.textViewGenderValue);
        textViewBirthday = (TextView) findViewById(R.id.textViewBirthdayValue);*/

        /**
         * get user email using intent
         */

        Intent intent = getIntent();
        correoGMAIL = intent.getStringExtra("email_id");
        System.out.println(correoGMAIL);
        //    textViewEmail.setText(correoGMAIL);

        /**
         * get user data from google account
         */

        try {
            System.out.println("On Home Page***"
                    + AbstractGetNameTask.GOOGLE_USER_DATA);
            JSONObject profileData = new JSONObject(
                    AbstractGetNameTask.GOOGLE_USER_DATA);

            if (profileData.has("picture")) {
                userImageUrl = profileData.getString("picture");
                new GetImageFromUrl().execute(userImageUrl);
            }
            if (profileData.has("name")) {
                nombreGMAIL = profileData.getString("name");
                //textViewName.setText(nombreGMAIL);
            }
            if (profileData.has("gender")) {
                textGender = profileData.getString("gender");
                //textViewGender.setText(textGender);
            }
            if (profileData.has("birthday")) {
                textBirthday = profileData.getString("birthday");
                //  textViewBirthday.setText(textBirthday);
            }

            Servicios2 hiloconexion;
            String IP = "http://pasennova.esy.es/pausas_activas/index/";
            String INSERT = IP + "registrar_gmail.php";
            hiloconexion = new Servicios2();
            hiloconexion.execute(INSERT, "4", correoGMAIL, nombreGMAIL);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            //imageProfile.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;
            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }

    public class Servicios2 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String cadena = params[0];
            URL url = null;
            String devuelve = "";
            if (params[1] == "4") {
                try {
                    HttpURLConnection urlConn;
                    DataOutputStream printout;
                    DataInputStream input;
                    url = new URL(cadena);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    urlConn.setRequestProperty("Accept", "application/json");
                    urlConn.connect();
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("correoGAMIL", params[2]);
                    jsonParam.put("nombreGMAIL", params[3]);
                    OutputStream os = urlConn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();
                    int respuesta = urlConn.getResponseCode();
                    StringBuilder result = new StringBuilder();
                    if (respuesta == HttpURLConnection.HTTP_OK) {
                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            result.append(line);
                        }
                        JSONObject respuestaJSON = new JSONObject(result.toString());
                        String resultJSON = respuestaJSON.getString("estado");
                        if (resultJSON.equals("1")) {
                            devuelve = devuelve + respuestaJSON.getJSONObject("usuario").getString("id_usuario");
                            Clase_Pojo.id_usuario = devuelve;
                        } else if (resultJSON.equals("2")) {
                            devuelve = "error";
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return devuelve;
            }
            return devuelve;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            String evaluarLogin = s;
            if (evaluarLogin.equals("error")) {
                Toast.makeText(Registrar_Google.this, "Error en el servicio porfavor vulve a intentarlo", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Registrar_Google.this, Index.class);
                startActivity(intent);
                finish();
            } else if (evaluarLogin.equals("")) {
                Toast.makeText(Registrar_Google.this, "Falta de conexion", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Registrar_Google.this, Index.class);
                startActivity(intent);
                finish();
            } else if (evaluarLogin.equals(null)) {
                Toast.makeText(Registrar_Google.this, "Falta de conexion", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Registrar_Google.this, Index.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(Registrar_Google.this, Inicio.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}




