package com.example.admin.pausas_activas;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.admin.pausas_activas.Clase_Pojo.Clase_Pojo;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
public class Inicio extends AppCompatActivity {
    String evaluarLogin = Clase_Pojo.id_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_inicio);
        if (evaluarLogin.equals("")) {
            Toast.makeText(Inicio.this,"Porfavor vuelva a logearse gracias",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Inicio.this, Index.class);
            startActivity(intent);
            finish();
        } else {
            try {
                FileOutputStream fos = openFileOutput("textFile.txt", MODE_PRIVATE);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                // Escribimos el String en el archivo
                osw.write(evaluarLogin);
                osw.flush();
                osw.close();
                // Mostramos que se ha guardado
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        final FloatingActionButton actionb = (FloatingActionButton) findViewById(R.id.action_b);
        actionb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  actionb.setTitle("Empezar");
                Intent intent = new Intent(Inicio.this, Evaluar_Avatar.class);
                startActivity(intent);
            }
        });

        final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //actionA.setTitle("Tutorial");
             /*   Intent intent = new Intent(Inicio.this, Tutorial.class);
                startActivity(intent);*/
            }
        });
    }
}