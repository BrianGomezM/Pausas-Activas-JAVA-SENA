package com.example.admin.pausas_activas;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.pausas_activas.Clase_Pojo.Clase_Pojo;
import com.example.admin.pausas_activas.Fragmentos.Play;
import com.example.admin.pausas_activas.Fragmentos.Registrar_Avatar;

public class Juego extends AppCompatActivity {
    String verificar = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_juego);
        //verificar = Clase_Pojo.evaluar;
        verificar = "2";
        if (verificar.equals("1"))
        {
            Registrar_Avatar registrarAvatar = new Registrar_Avatar();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().add(R.id.contenedor2, registrarAvatar).commit();
        }
        else
        if (verificar.equals("2"))
        {
            Play play = new Play();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().add(R.id.contenedor2, play).commit();
        }
        else
        {
            Intent intent1 = new Intent(Juego.this,Inicio.class);
            startActivity(intent1);
            finish();
        }
    }
}
