package com.example.admin.pausas_activas;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.pausas_activas.Clase_Pojo.Clase_Pojo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bienvenido extends AppCompatActivity {
    static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_bienvenido);
        espera spEspera = new espera();
        spEspera.execute();
    }
    private class espera extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar pbSplash = (ProgressBar) findViewById(R.id.progressBarB);
            pbSplash.setProgress(0);
            pbSplash.setMax(2);
        }


        @Override
        protected Boolean doInBackground(String... params) {
            for (int i=0; i<5; i++){

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
            if (aBoolean){
                try {
                    FileInputStream fis = openFileInput("textFile.txt");
                    InputStreamReader isr = new InputStreamReader(fis);
                    char[] inputBuffer = new char[READ_BLOCK_SIZE];
                    String s = "";
                    int charRead;
                    while ((charRead = isr.read(inputBuffer)) > 0) {
                        // Convertimos los char a String
                        String readString = String.copyValueOf(inputBuffer, 0, charRead);
                        s += readString;
                        inputBuffer = new char[READ_BLOCK_SIZE];
                    }
                    // Establecemos en el EditText el texto que hemos leido
                    Clase_Pojo.id_usuario=s;
                    if ((s.equals("")))
                    {
                        Toast.makeText(Bienvenido.this, "Bienvenido ''debes Logearte para Iniciar tus Pausas Activas''", Toast.LENGTH_SHORT).show();
                        Intent main = new Intent(Bienvenido.this, Index.class);
                        Bienvenido.this.startActivity(main);
                        Bienvenido.this.finish();
                        isr.close();
                    }
                    else
                    {
                        Toast.makeText(Bienvenido.this, "Bienvenido a tus Pausas Activas''", Toast.LENGTH_SHORT).show();
                        Intent main = new Intent(Bienvenido.this, Inicio.class);
                        Bienvenido.this.startActivity(main);
                        Bienvenido.this.finish();
                        isr.close();
                    }
                } catch (IOException ex) {
                    Toast.makeText(Bienvenido.this,"Bienvenido ''debes Logearte para Iniciar tus Pausas Activas''", Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                    Intent main = new Intent(Bienvenido.this,Index.class);
                    Bienvenido.this.startActivity(main);
                    Bienvenido.this.finish();
                }
                // creo un intent y envio la app a la nueva actividad.
            }else {
                Toast.makeText(Bienvenido.this, "tu app esta mal", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            ProgressBar pbSplash =(ProgressBar)findViewById(R.id.progressBarB);
            pbSplash.setProgress(values[0]);
        }
    }
}