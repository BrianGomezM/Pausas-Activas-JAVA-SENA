package com.example.admin.pausas_activas.Fragmentos;


import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.example.admin.pausas_activas.Clase_Pojo.Clase_Pojo;
import com.example.admin.pausas_activas.Evaluar_Avatar;
import com.example.admin.pausas_activas.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class Discapacidad extends Fragment implements View.OnClickListener {

    CheckBox munecaIzqJAVA, codoIzqJAVA, hombroIzqJAVA, munecaDereJAVA, codoDereJAVA, hombroDereJAVA, cuelloJAVA, cinturaJAVA, piernasJAVA;
    String avatar = Clase_Pojo.nombreAvatar;
    String generoAvatar = Clase_Pojo.generoAvatar;
    String id_usuario = Clase_Pojo.id_usuario;
    ImageView cuerpoJAVA;
    String munecaDere = "0", munecaIzq = "0", codoDere = "0", codoIzq = "0", hombroDere = "0", hombroIzq = "0", cuello = "0", cintura = "0", piernas = "0";
    int respuesta = -1;//Respuesta del servicio para efetuar en el boton
    int clik = 0;//Intenos al boton
    String respuesta2 = "2";

    public Discapacidad() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discapacidad, container, false);
        munecaDereJAVA = (CheckBox)view.findViewById(R.id.munecaDere);
        munecaIzqJAVA = (CheckBox)view.findViewById(R.id.munecaizqui);
        codoDereJAVA = (CheckBox)view.findViewById(R.id.codoDere);
        codoIzqJAVA = (CheckBox)view.findViewById(R.id.codoizqui);
        hombroDereJAVA = (CheckBox)view.findViewById(R.id.hombroDere);
        hombroIzqJAVA = (CheckBox)view.findViewById(R.id.hombroizqui);
        cuelloJAVA = (CheckBox)view.findViewById(R.id.cuello);
        cinturaJAVA = (CheckBox)view.findViewById(R.id.cintura);
        piernasJAVA = (CheckBox)view.findViewById(R.id.piernas);
        cuerpoJAVA = (ImageView)view.findViewById(R.id.cuerpo);
        if (generoAvatar.equals("1"))
        {
            cuerpoJAVA.setBackgroundResource(R.drawable.cuerpo_hombre);
        }
        else
        {
            cuerpoJAVA.setBackgroundResource(R.drawable.cuerpo_mujer);
        }
        munecaDereJAVA.setOnCheckedChangeListener(myCheckboxListener);
        munecaIzqJAVA.setOnCheckedChangeListener(myCheckboxListener);
        codoDereJAVA.setOnCheckedChangeListener(myCheckboxListener);
        codoIzqJAVA.setOnCheckedChangeListener(myCheckboxListener);
        hombroDereJAVA.setOnCheckedChangeListener(myCheckboxListener);
        hombroIzqJAVA.setOnCheckedChangeListener(myCheckboxListener);
        cuelloJAVA.setOnCheckedChangeListener(myCheckboxListener);
        cinturaJAVA.setOnCheckedChangeListener(myCheckboxListener);
        piernasJAVA.setOnCheckedChangeListener(myCheckboxListener);
        view.findViewById(R.id.guardarDiscapacidad).setOnClickListener(this);
        return view;
    }

    private CompoundButton.OnCheckedChangeListener myCheckboxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton view, boolean isChecked) {
            switch (view.getId()) {
                case R.id.munecaDere:
                    Snackbar.make(view, "Muñeca derecha seleccionada", Snackbar.LENGTH_LONG).show();
                    break;
                case R.id.munecaizqui:
                    Snackbar.make(view, "Muñeca izquierda seleccionada", Snackbar.LENGTH_LONG).show();
                    break;
                case R.id.codoDere:
                    if (codoDereJAVA.isChecked()) {
                        Snackbar.make(view, "Codo derecho seleccionado", Snackbar.LENGTH_LONG).show();
                        munecaDereJAVA.setChecked(true);
                        munecaDereJAVA.setEnabled(false);
                        codoDere = "3";
                        munecaDere = "1";
                    } else {
                        munecaDereJAVA.setEnabled(true);
                        munecaDereJAVA.setChecked(false);
                        codoDere = "0";
                        munecaDere = "0";
                    }

                    break;
                case R.id.codoizqui:
                    if (codoIzqJAVA.isChecked()) {
                        Snackbar.make(view, "Codo izquierdo seleccionado", Snackbar.LENGTH_LONG).show();
                        munecaIzqJAVA.setChecked(true);
                        munecaIzqJAVA.setEnabled(false);
                        codoIzq = "4";
                        munecaIzq = "2";
                    } else {
                        munecaIzqJAVA.setEnabled(true);
                        munecaIzqJAVA.setChecked(false);
                        codoIzq = "0";
                        munecaIzq = "0";
                    }
                    break;
                case R.id.hombroDere:
                    if (hombroDereJAVA.isChecked()) {
                        Snackbar.make(view, "Hombro derecho seleccionado", Snackbar.LENGTH_LONG).show();
                        munecaDereJAVA.setChecked(true);
                        munecaDereJAVA.setEnabled(false);
                        codoDereJAVA.setChecked(true);
                        codoDereJAVA.setEnabled(false);

                        hombroDere = "5";
                        codoDere = "3";
                        munecaDere = "1";
                    } else {
                        munecaDereJAVA.setEnabled(true);
                        munecaDereJAVA.setChecked(false);
                        codoDereJAVA.setEnabled(true);
                        codoDereJAVA.setChecked(false);

                        hombroDere = "0";
                        codoDere = "0";
                        munecaDere = "0";
                    }
                    break;
                case R.id.hombroizqui:
                    if (hombroIzqJAVA.isChecked()) {
                        Snackbar.make(view, "Hombro izquierdo seleccionado", Snackbar.LENGTH_LONG).show();
                        munecaIzqJAVA.setChecked(true);
                        munecaIzqJAVA.setEnabled(false);
                        codoIzqJAVA.setChecked(true);
                        codoIzqJAVA.setEnabled(false);

                        hombroIzq = "6";
                        codoIzq = "4";
                        munecaIzq = "2";
                    } else {
                        munecaIzqJAVA.setEnabled(true);
                        munecaIzqJAVA.setChecked(false);
                        codoIzqJAVA.setEnabled(true);
                        codoIzqJAVA.setChecked(false);
                        hombroIzq = "0";
                        codoIzq = "0";
                        munecaIzq = "0";
                    }
                    break;
                case R.id.cuello:
                    if (cuelloJAVA.isChecked()) {
                        Snackbar.make(view, "Cuello seleccionado", Snackbar.LENGTH_LONG).show();
                        cuello = "7";
                    } else {
                        cuello = "0";
                    }
                    break;
                case R.id.cintura:
                    if (cinturaJAVA.isChecked()) {
                        Snackbar.make(view, "Cintura seleccionada", Snackbar.LENGTH_LONG).show();
                        cintura = "8";
                    } else {
                        cintura = "0";
                    }
                    break;
                case R.id.piernas:
                    if (piernasJAVA.isChecked()) {
                        Snackbar.make(view, "Piernas seleccionadas", Snackbar.LENGTH_LONG).show();
                        piernas = "9";
                    } else {
                        piernas = "0";
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.guardarDiscapacidad:
                if ((munecaDereJAVA.isChecked()==false)&&(munecaIzqJAVA.isChecked()==false)&&(codoDereJAVA.isChecked()==false)&&(codoIzqJAVA.isChecked()==false)&&(hombroIzqJAVA.isChecked()==false)&&(hombroDereJAVA.isChecked()==false)
                        &&(cuelloJAVA.isChecked()==false) &&(cinturaJAVA.isChecked()==false)&&(piernasJAVA.isChecked()==false))
                {
                    Snackbar.make(v, "Señale sus Discapacidades para contiuar", Snackbar.LENGTH_LONG).show();
                }else {
                    CircularProgressButton guardarDiscapacidadJAVA = (CircularProgressButton) v.findViewById(R.id.guardarDiscapacidad);
                    clik++;
                    if (clik == 1)
                    {
                        if (guardarDiscapacidadJAVA.getProgress() == 0)
                        {   ServicioDiscapacidad hiloconexionDisca;
                            String IP = "http://pasennova.esy.es/pausas_activas/index";
                            String INSERT = IP + "/registrar_usuario.php";
                            hiloconexionDisca = new ServicioDiscapacidad();
                            hiloconexionDisca.execute(INSERT, "1",id_usuario, avatar, generoAvatar,munecaDere, munecaIzq, codoDere, codoIzq, hombroDere, hombroIzq, cuello, cintura, piernas);
                            simulateSuccessProgresDisca(guardarDiscapacidadJAVA);
                        }
                        else
                        {
                            guardarDiscapacidadJAVA.setProgress(0);
                            simulateSuccessProgresDisca(guardarDiscapacidadJAVA);
                            clik = 0;
                            if (respuesta2.equals("1"))
                            {
                                Snackbar.make(v, "Error Al Registrar Avatar vulve a intentarlo....", Snackbar.LENGTH_LONG).show();
                            }
                            else if (respuesta2.equals("2"))
                            {
                                Snackbar.make(v, "Error De Conexion....", Snackbar.LENGTH_LONG).show();
                            }
                            else if (respuesta2.equals("3"))
                            {
                                Snackbar.make(v, "Avatar registrado correctamente", Snackbar.LENGTH_LONG).show();
                                Intent intent = new Intent(getActivity(), Evaluar_Avatar.class);
                                startActivity(intent);
                            }
                            else if (respuesta2.equals("4"))
                            {
                                Snackbar.make(v, "Error Al Registrar Avatar vulve a intentarlo.....", Snackbar.LENGTH_LONG).show();

                            }
                        }
                    }
                }


                break;
        }
    }


    public class ServicioDiscapacidad extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String cadena = params[0];
            URL url = null;
            String devuelve = "";
            if (params[1] == "1")////Registrar
            {
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
                    jsonParam.put("id_avatar", params[2]);
                    jsonParam.put("avatar", params[3]);
                    jsonParam.put("genero", params[4]);
                    jsonParam.put("munecaDere", params[5]);
                    jsonParam.put("munecaIzq", params[6]);
                    jsonParam.put("codoDere", params[7]);
                    jsonParam.put("codoIzq", params[8]);
                    jsonParam.put("hombroDere", params[9]);
                    jsonParam.put("hombroIzq", params[10]);
                    jsonParam.put("cuello", params[11]);
                    jsonParam.put("cintura", params[12]);
                    jsonParam.put("piernas", params[13]);
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
                            devuelve = "1";
                        } else if (resultJSON.equals("2")) {
                            devuelve = "2";
                        }
                        else
                        {
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
            String evaluarLogin=s;
            if (evaluarLogin.equals("1"))
            {
                respuesta = 1;
                Toast.makeText(getActivity(),"Registro exitoso....",Toast.LENGTH_LONG).show();
                respuesta2="3";
            } else if (evaluarLogin.equals("2"))
            {
                respuesta = -1;
                Toast.makeText(getActivity(),"Vulve a intentarlo porfavor",Toast.LENGTH_LONG).show();
                respuesta2="4";
            } else if (evaluarLogin.equals(null))
            {
                respuesta = -1;
                respuesta2="2";
            } else if (evaluarLogin.equals(""))
            {
                respuesta = -1;
                respuesta2="2";
            }
            else
            if (evaluarLogin.equals("error"))
            {
                respuesta = -1;
                respuesta2="1";
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

    private void simulateSuccessProgresDisca(final CircularProgressButton button)
    {

        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(12000);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
                if (value == 99)
                {
                    button.setProgress(respuesta);//evalua si se iso el proceso o no
                    clik=0;
                }
            }
        });
        widthAnimation.start();
    }
}
