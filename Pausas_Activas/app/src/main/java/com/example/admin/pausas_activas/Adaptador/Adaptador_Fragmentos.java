package com.example.admin.pausas_activas.Adaptador;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.admin.pausas_activas.Fragmentos.Ingresar;
import com.example.admin.pausas_activas.Fragmentos.Recuperar;
import com.example.admin.pausas_activas.Fragmentos.Registrar;

/**
 * Created by ADMIN on 17/11/2016.
 */
public class Adaptador_Fragmentos extends FragmentPagerAdapter {


    private int tabs;
    public static int posicion;


    public Adaptador_Fragmentos(FragmentManager fm, int tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position)
    {
        this.posicion = position;
        switch (position)
        {
            case 0:
                Ingresar ingresar= new Ingresar() ;
                return ingresar;
            case 1:
                Registrar registrar = new Registrar();
                return registrar;
            case 2:
                Recuperar recuperar = new Recuperar();
                return recuperar;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabs;
    }
}