package com.example.admin.pausas_activas.Fragmentos;


import android.animation.ValueAnimator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.CircularProgressButton;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class Registrar extends Fragment implements View.OnClickListener {
    EditText nombreRegistrarJAVA, correoRegistraJAVA, numeroRegistrarJAVA, claveRegistrarJAVA, repeClaveRegistrarJAVA;
    String respuesta2="2";
    int respuesta=-1;//Respuesta del servicio para efetuar en el boton
    int clik=0;//Intenos al boton

    public Registrar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrar, container, false);

        nombreRegistrarJAVA = (EditText) view.findViewById(R.id.nombreRegistrar);
        correoRegistraJAVA = (EditText) view.findViewById(R.id.correoRegistra);
        numeroRegistrarJAVA = (EditText) view.findViewById(R.id.numeroRegistrar);
        claveRegistrarJAVA = (EditText) view.findViewById(R.id.claveRegistrar);
        repeClaveRegistrarJAVA = (EditText) view.findViewById(R.id.repeClaveRegistrar);
        claveRegistrarJAVA.setTransformationMethod(PasswordTransformationMethod.getInstance());
        repeClaveRegistrarJAVA.setTransformationMethod(PasswordTransformationMethod.getInstance());
        view.findViewById(R.id.registrarUsuario).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.registrarUsuario:

                String nombreEvaluar = nombreRegistrarJAVA.getText().toString();
                String correoEvaluar = correoRegistraJAVA.getText().toString();
                String numeroRegistrar = numeroRegistrarJAVA.getText().toString();
                String claveEvaluar = claveRegistrarJAVA.getText().toString();
                String repetirClaveEvaluar = repeClaveRegistrarJAVA.getText().toString();

                if (nombreEvaluar.equals("")) {
                    Snackbar.make(v, "Nombre vacio", Snackbar.LENGTH_LONG).show();
                } else if (correoEvaluar.equals("")) {
                    Snackbar.make(v, "Correo vacio", Snackbar.LENGTH_LONG).show();
                } else if (numeroRegistrar.equals("")) {
                    Snackbar.make(v, "Numero De Documento Vacio", Snackbar.LENGTH_LONG).show();
                } else if (claveEvaluar.equals("")) {
                    Snackbar.make(v, "Clave Vacio", Snackbar.LENGTH_LONG).show();
                } else if (repetirClaveEvaluar.equals("")) {
                    Snackbar.make(v, "Repetir Clave vacio", Snackbar.LENGTH_LONG).show();
                } else if (claveEvaluar.equals(repetirClaveEvaluar)) {
                    if (!ValidarCorreo(correoEvaluar)) {
                        Snackbar.make(v, "Correo Electronico incorrecto", Snackbar.LENGTH_LONG).show();
                    } else {

                        clik++;
                        if (clik == 1)
                        {

                            CircularProgressButton registrarUsuarioJAVA = (CircularProgressButton) v.findViewById(R.id.registrarUsuario);
                            if (registrarUsuarioJAVA.getProgress() == 0)
                            {
                                Servicios4 hiloconexion;
                                String IP = "http://pasennova.esy.es/pausas_activas/index";
                                String INSERT = IP + "/registrar_usuario.php";
                                hiloconexion = new Servicios4();
                                hiloconexion.execute(INSERT, "1", nombreEvaluar, correoEvaluar, numeroRegistrar, claveEvaluar);
                                simulateSuccessProgress2(registrarUsuarioJAVA);
                            }
                            else
                            {
                                registrarUsuarioJAVA.setProgress(0);
                                clik = 0;
                                if (respuesta2.equals("1"))
                                {
                                    Snackbar.make(v, "Error Al Registrar Vulvelo A Intentar....", Snackbar.LENGTH_LONG).show();
                                }
                                else if (respuesta2.equals("2"))
                                {
                                    Snackbar.make(v, "Error De Conexion....", Snackbar.LENGTH_LONG).show();
                                }
                                else if (respuesta2.equals("3"))
                                {
                                    Snackbar.make(v, "Usuario Registrado Correctamente...", Snackbar.LENGTH_LONG).show();
                                }
                                else if (respuesta2.equals("4"))
                                {
                                    Snackbar.make(v, "Ya Existe Un Usuario Con Este Correo Y Celular....", Snackbar.LENGTH_LONG).show();

                                }
                            }
                        }
                        else
                        {
                            Snackbar.make(v, "Proceso....", Snackbar.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Snackbar.make(v, "Las Claves No Coinciden", Snackbar.LENGTH_LONG).show();
                }
                break;
        }

    }
    private boolean ValidarCorreo(String correo) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }
    public class Servicios4 extends AsyncTask<String, Void, String> {

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
                    jsonParam.put("nombre", params[2]);
                    jsonParam.put("correo", params[3]);
                    jsonParam.put("documento", params[4]);
                    jsonParam.put("clave", params[5]);
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
                nombreRegistrarJAVA.setText("");
                correoRegistraJAVA.setText("");
                numeroRegistrarJAVA.setText("");
                claveRegistrarJAVA.setText("");
                repeClaveRegistrarJAVA.setText("");
                respuesta2="3";
            } else if (evaluarLogin.equals("2"))
            {
                respuesta = -1;
                Toast.makeText(getActivity(),"Correo y numero de documento ya existente",Toast.LENGTH_LONG).show();
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


    private void simulateSuccessProgress2(final CircularProgressButton button)
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