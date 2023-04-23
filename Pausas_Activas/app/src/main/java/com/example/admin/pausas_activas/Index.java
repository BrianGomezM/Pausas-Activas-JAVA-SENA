package com.example.admin.pausas_activas;

import android.content.pm.ActivityInfo;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.pausas_activas.Adaptador.Adaptador_Fragmentos;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

public class Index extends AppCompatActivity {
    private ViewPager contenerdorViewJAVA;
    private NavigationTabStrip diseñoBarraJAVA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_index);

        contenerdorViewJAVA = (ViewPager)findViewById(R.id.contenedor);
        diseñoBarraJAVA=(NavigationTabStrip) findViewById(R.id.diseñoBarra);
        Adaptador_Fragmentos adapter = new Adaptador_Fragmentos(getSupportFragmentManager(),3);
        contenerdorViewJAVA.setAdapter(adapter);
        diseñoBarraJAVA.setViewPager(contenerdorViewJAVA, 0 );

    }
}