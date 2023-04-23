package com.example.admin.pausas_activas.Fragmentos;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.admin.pausas_activas.Inicio;
import com.example.admin.pausas_activas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Videos extends Fragment {
    int vector[] = new int[31];
    VideoView videoView;
    String hola;
    int a = 0;
    String Link = "android.resource://com.example.admin.pausas_activas/";
    Button siguiente;
    int sesion = 0;//arraca la sesion con los 10 movimientos

    public Videos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        videoView = (VideoView)view.findViewById(R.id.videoView);
        siguiente = (Button)view.findViewById(R.id.siguiente);
        siguiente.setVisibility(View.INVISIBLE);
        //evaluar si tiene rutina o no
        rutinaAleatorio();
        //si tiene rutina se saca la posicion del vector y el vector
        ciclos();
        return view;
    }
    public void rutinaAleatorio() {
        int i = 0, j;
        vector[i] = (int) (Math.random() * 31);
        for (i = 1; i < 31; i++) {
            vector[i] = (int) (Math.random() * 31);
            for (j = 0; j < i; j++) {
                if (vector[i] == vector[j]) {
                    i--;
                }
            }
        }
        //Guarda la rutina en la base de datos
    }
    private void ciclos ()
    {   sesion = sesion + 1;
        if (a == 31) {
            a = 0;
            rutinaAleatorio();
            ciclos();
        }else if (vector[a] == 0) {

            hola =  Link + R.raw.video1;
            startOtherVideo();
        } else if (vector[a] == 1) {
            hola = Link + R.raw.video2;
            startOtherVideo();
        } else if (vector[a] == 2) {
            hola = Link + R.raw.video3;
            startOtherVideo();
        } else if (vector[a] == 3) {
            hola = Link + R.raw.video4;
            startOtherVideo();
        } else if (vector[a] == 4) {
            hola = Link + R.raw.video5;
            startOtherVideo();
        } else if (vector[a] == 5) {
            hola = Link + R.raw.video6;
            startOtherVideo();
        }else
        if (vector[a] == 6) {
            hola = Link + R.raw.video7;
            startOtherVideo();
        } else if (vector[a] == 7) {
            hola = Link + R.raw.video8;
            startOtherVideo();
        } else if (vector[a] == 8) {
            hola = Link + R.raw.video9;
            startOtherVideo();
        } else if (vector[a] == 9) {
            hola = Link + R.raw.video10;
            startOtherVideo();
        } else if (vector[a] == 10) {
            hola = Link + R.raw.video11;
            startOtherVideo();
        }else if (vector[a] == 11) {
            hola = Link + R.raw.video12;
            startOtherVideo();
        } else if (vector[a] == 12) {
            hola = Link + R.raw.video13;
            startOtherVideo();
        } else if (vector[a] == 13) {
            hola = Link + R.raw.video14;
            startOtherVideo();
        } else if (vector[a] == 14) {
            hola = Link + R.raw.video15;
            startOtherVideo();
        } else if (vector[a] == 15) {
            hola = Link + R.raw.video16;
            startOtherVideo();}
        else if (vector[a] == 16) {
            hola = Link + R.raw.video17;
            startOtherVideo();
        } else if (vector[a] == 17) {
            hola = Link + R.raw.video18;
            startOtherVideo();
        } else if (vector[a] == 18) {
            hola = Link + R.raw.video19;
            startOtherVideo();
        } else if (vector[a] == 19) {
            hola = Link + R.raw.video20;
            startOtherVideo();
        } else if (vector[a] == 20) {
            hola = Link + R.raw.video21;
            startOtherVideo();
        } else if (vector[a] == 21) {
            hola = Link + R.raw.video22;
            startOtherVideo();
        }
        else if (vector[a] == 22) {
            hola = Link + R.raw.video23;
            startOtherVideo();
        }
        else if (vector[a] == 23) {
            hola = Link + R.raw.video24;
            startOtherVideo();
        }
        else if (vector[a] == 24) {
            hola = Link + R.raw.video25;
            startOtherVideo();
        }
        else if (vector[a] == 25) {
            hola = Link + R.raw.video26;
            startOtherVideo();
        }
        else if (vector[a] == 26) {
            hola = Link + R.raw.video27;
            startOtherVideo();
        }
        else if (vector[a] == 27) {
            hola = Link + R.raw.video28;
            startOtherVideo();
        }
        else if (vector[a] == 28) {
            hola = Link + R.raw.video29;
            startOtherVideo();
        }
        else if (vector[a] == 29) {
            hola = Link + R.raw.video30;
            startOtherVideo();
        }
        else if (vector[a] == 30) {
            hola = Link + R.raw.video31;
            startOtherVideo();
        }
    }
    private void startOtherVideo(){
        a = a + 1;
        Uri path = Uri.parse(hola);
        videoView.setVideoURI(path);
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                if (sesion==5)
                {
                    //guarda la posicion del vector A en la base de datos
                    Toast.makeText(getActivity(),"rutina culminada",Toast.LENGTH_LONG).show();
                    Intent intent =  new Intent(getActivity(), Inicio.class);
                    startActivity(intent);
                    getActivity().finish();
                }else {
                    siguiente.setVisibility(View.VISIBLE);
                    siguiente.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            siguiente.setVisibility(View.INVISIBLE);
                            ciclos();
                        }
                    });

                }
            }
        });
    }
}


