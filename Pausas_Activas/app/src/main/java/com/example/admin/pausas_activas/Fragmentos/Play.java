package com.example.admin.pausas_activas.Fragmentos;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.pausas_activas.Clase_Pojo.Clase_Pojo;
import com.example.admin.pausas_activas.R;

import java.util.Calendar;
import java.util.Date;

import info.hoang8f.widget.FButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class Play extends Fragment {
    private FButton sesion1;
    private FButton sesion2;
    String horaServr = "";
    TextView txtCurrentTime;
    TextView hora;
    TextView empiezaJAVA;
    int horas;
    @TargetApi(Build.VERSION_CODES.N)
    public Play() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        sesion1 = (FButton)view.findViewById(R.id.botonXML);
        sesion2 = (FButton)view.findViewById(R.id.boton2XML);
        txtCurrentTime = (TextView)view.findViewById(R.id.hora);
        hora = (TextView)view.findViewById(R.id.hora2);
        empiezaJAVA = (TextView)view.findViewById(R.id.empieza);

        Thread myThread = null;
        Runnable runnable = new CountDownRunner();
        myThread = new Thread(runnable);
        myThread.start();
        return  view;
    }
    public void doWork() {
        getActivity().runOnUiThread(new Runnable() {
            @TargetApi(Build.VERSION_CODES.N)
            public void run() {
                try {
                    horaServr = Clase_Pojo.horaServer;
                    sesion1.setEnabled(false);
                    sesion2.setEnabled(false);
                    Calendar c = Calendar.getInstance();
                    int segundos = c.get(Calendar.SECOND);
                    int minutos = c.get(Calendar.MINUTE);
                    int horas = c.get(Calendar.HOUR);
                    Date dt = new Date();
                    int horas2 = dt.getHours();
                    int minutos2 = dt.getMinutes();
                    int segundos2 = dt.getSeconds();
                    String curTime = horas2 + ":" + minutos2 + ":" + segundos2;
                    hora.setText(curTime);
                    String horaTelefono = String.valueOf(horas);
                    if (horaTelefono.equals(horaTelefono)) {
                        if (horas2 < 12) {
                            String tiempo = horas + ":" + minutos + ":" + segundos+" AM";
                            txtCurrentTime.setText(tiempo);
                            if (horas2 <= 10) {
                                if ((horas2 == 10) || (horas2 == 11) || (horas2 == 12)) {
                                    sesion1.setEnabled(true);
                                    empiezaJAVA.setText("La rutina de la mañana la puede realizar");
                                    sesion1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Videos videos = new Videos();
                                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                            // Replace whatever is in the fragment_container view with this fragment,
                                            // and add the transaction to the back stack so the user can navigate back
                                            transaction.replace(R.id.contenedor2, videos);
                                            transaction.addToBackStack(null);
                                            // Commit the transaction
                                            transaction.commit();
                                        }
                                    });
                                } else {
                                    sesion1.setEnabled(false);
                                    int horasFaltante = 9 - horas2;
                                    int minutosFaltante = 60 - minutos2;
                                    int segundosFaltante = 60 - segundos2;
                                    //  Toast.makeText(MainActivity.this, , Toast.LENGTH_LONG).show();
                                    empiezaJAVA.setText("su rutina empieza: " + horasFaltante + ":" + minutosFaltante + ":" + segundosFaltante);
                                }
                            }
                        } else {
                            String tiempo = horas + ":" + minutos + ":" + segundos+ " PM";
                            txtCurrentTime.setText(tiempo);
                            if ((horas2 == 16) || (horas2 == 17) || (horas2 == 18)) {
                                sesion2.setEnabled(true);
                                empiezaJAVA.setText("La rutina de la tarde la puede realizar");
                                sesion2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Videos videos = new Videos();
                                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                        // Replace whatever is in the fragment_container view with this fragment,
                                        // and add the transaction to the back stack so the user can navigate back
                                        transaction.replace(R.id.contenedor2, videos);
                                        transaction.addToBackStack(null);
                                        // Commit the transaction
                                        transaction.commit();
                                    }
                                });
                            } else {
                                sesion2.setEnabled(false);
                                int horasFaltante = (15 - horas2);
                                int minutosFaltante = (60 - minutos2);
                                int segundosFaltante = (60 - segundos2);
                                // Toast.makeText(MainActivity.this, "Rutnias empiza en: " + horasFaltante + ":" + minutosFaltante + ":" + segundosFaltante, Toast.LENGTH_LONG).show();
                                empiezaJAVA.setText("Rutnias empiza en: " + horasFaltante + ":" + minutosFaltante + ":" + segundosFaltante);
                            }
                        }
                    }
                    else {
                        empiezaJAVA.setText("Actualiza el reloj de tu movil");
                        sesion1.setEnabled(false);
                        sesion2.setEnabled(false);
                    }
                }
                catch (Exception e) {}
            }
        });
    }
    class CountDownRunner implements Runnable{
        // @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }
}

