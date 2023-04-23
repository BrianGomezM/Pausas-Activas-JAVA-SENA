package com.example.admin.pausas_activas;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.pausas_activas.Clase_Pojo.Clase_Pojo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Evaluar_Avatar extends AppCompatActivity {
    String id_usuario = Clase_Pojo.id_usuario;
    static final int READ_BLOCK_SIZE = 100;
    String evaluar = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_evaluar__avatar);
        espera spEspera = new espera();
        spEspera.execute();

    }


    private class espera extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar pbSplash = (ProgressBar) findViewById(R.id.progressBar1);
            pbSplash.setProgress(0);
            pbSplash.setMax(2);
        }


        @Override
        protected Boolean doInBackground(String... params) {
            for (int i = 0; i < 5; i++) {

                try {
                    Thread.sleep(800);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                ServiciosEva hiloconexionEva;
                String IP = "http://pasennova.esy.es/pausas_activas/index";
                String LOGIN = IP + "/evaluar_avatar.php";
                hiloconexionEva = new ServiciosEva();
                String cadenallamada = LOGIN + "?id_usuario=" + id_usuario;
                hiloconexionEva.execute(cadenallamada, "2");
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            ProgressBar pbSplash = (ProgressBar) findViewById(R.id.progressBar1);
            pbSplash.setProgress(values[0]);
        }
    }


    public class ServiciosEva extends AsyncTask<String, Void, String> {
        String evaluar = "";

        @Override
        protected String doInBackground(String... params) {
            String cadena = params[0];
            URL url = null;
            String devuelve = "";
            if (params[1] == "2")//Entrar
            {
                try {
                    evaluar = "2";
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" + " (Linux; Android 5.1; es-ES) Ejemplo HTTP");
                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();
                    if (respuesta == HttpURLConnection.HTTP_OK) {
                        InputStream in = new BufferedInputStream(connection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        JSONObject respuestaJSON = new JSONObject(result.toString());
                        String resultJSON = respuestaJSON.getString("estado");
                        String repuesta = resultJSON;
                        if (repuesta.equals("1")) {
                            devuelve = devuelve + respuestaJSON.getJSONObject("usuario").getString("id_avatar");
                        } else if (repuesta.equals("2")) {
                            devuelve = "2";
                        } else {
                            devuelve = "3";
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
            String evaluarAvatar = s;
            if (evaluarAvatar.equals("2")) {
                evaluar = "1";
                Clase_Pojo.evaluar = evaluar;
                Intent intent1 = new Intent(Evaluar_Avatar.this, Juego.class);
                startActivity(intent1);
                finish();
            } else if (evaluarAvatar.equals("")) {

                Toast.makeText(Evaluar_Avatar.this, "Verifica lA conexion", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(Evaluar_Avatar.this, Inicio.class);
                startActivity(intent1);
                finish();
            } else if (evaluarAvatar.equals(null)) {
                Toast.makeText(Evaluar_Avatar.this, "Verifica lA conexion", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(Evaluar_Avatar.this, Inicio.class);
                startActivity(intent2);
                finish();
            } else if (evaluarAvatar.equals("3")) {
                Toast.makeText(Evaluar_Avatar.this, "Verifica lA conexion", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(Evaluar_Avatar.this, Inicio.class);
                startActivity(intent2);
                finish();
            } else {
                horaServi();
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

    public void horaServi() {
        Servi hiloco;
        String IP1 = "http://pasennova.esy.es/pausas_activas/juego";
        String EVALUAR = IP1 + "/hora.php";
        hiloco = new Servi();
        String cadena = EVALUAR;
        hiloco.execute(cadena, "2");
    }

    public class Servi extends AsyncTask<String, Void, String> {
        String evaluar = "";

        @Override
        protected String doInBackground(String... params) {
            String cadena = params[0];
            URL url = null;
            String devuelve = "";
            if (params[1].equals("2"))//Entrar
            {
                try {
                    evaluar = "2";
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" + " (Linux; Android 5.1; es-ES) Ejemplo HTTP");
                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();
                    if (respuesta == HttpURLConnection.HTTP_OK) {
                        InputStream in = new BufferedInputStream(connection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        JSONObject respuestaJSON = new JSONObject(result.toString());
                        String resultJSON = respuestaJSON.getString("estado");
                        devuelve = devuelve + resultJSON;
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
            evaluar = "2";
            Clase_Pojo.horaServer = s;
            Clase_Pojo.evaluar = evaluar;
            Intent intent = new Intent(Evaluar_Avatar.this, Juego.class);
            startActivity(intent);
            finish();
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