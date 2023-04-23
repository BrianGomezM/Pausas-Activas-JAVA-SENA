package com.example.admin.pausas_activas.Fragmentos;


import android.animation.ValueAnimator;
import android.content.Intent;
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

import com.dd.CircularProgressButton;
import com.example.admin.pausas_activas.Clase_Pojo.Clase_Pojo;
import com.example.admin.pausas_activas.Google;
import com.example.admin.pausas_activas.Inicio;
import com.example.admin.pausas_activas.R;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ingresar extends Fragment  implements View.OnClickListener {
    EditText correoLoginJAVA, claveLoginJAVA;
    int respuesta = -1;//Respuesta del servicio para efetuar en el boton
    int clik = 0;//Intenos al boton
    String respuesta2 = "2";


    public Ingresar() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingresar, container, false);
        correoLoginJAVA = (EditText) view.findViewById(R.id.correoLogin);
        claveLoginJAVA = (EditText) view.findViewById(R.id.claveLogin);
        claveLoginJAVA.setTransformationMethod(PasswordTransformationMethod.getInstance());
        view.findViewById(R.id.entrarGMAIL).setOnClickListener(this);
        view.findViewById(R.id.ingresarLogin).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.entrarGMAIL:
                Intent intent2 = new Intent(getActivity(), Google.class);
                startActivity(intent2);
                getActivity().finish();

                break;
            case R.id.ingresarLogin:

                String correoEvaluar = correoLoginJAVA.getText().toString();
                String claveEvaluar = claveLoginJAVA.getText().toString();
                if (correoEvaluar.equals("")) {
                    Snackbar.make(v, "Correo electronico vacio", Snackbar.LENGTH_LONG).show();
                } else if (claveEvaluar.equals("")) {
                    Snackbar.make(v, "Contraseña vacio", Snackbar.LENGTH_LONG).show();
                } else if (!isValidEmail(correoEvaluar)) {
                    Snackbar.make(v, "Correo electronico incorrecto", Snackbar.LENGTH_LONG).show();
                } else {
                    clik++;
                    if (clik == 1) {
                        CircularProgressButton ingresarLoginJAVA = (CircularProgressButton) v.findViewById(R.id.ingresarLogin);
                        if (ingresarLoginJAVA.getProgress() == 0) {
                            Servicios1 hiloconexion;
                            String IP = "http://pasennova.esy.es/pausas_activas/index";
                            String LOGIN = IP + "/ingresar_usuario.php";
                            hiloconexion = new Servicios1();
                            String cadenallamada = LOGIN + "?correo=" + correoEvaluar + "&&clave=" + claveEvaluar;
                            hiloconexion.execute(cadenallamada, "2");
                            simulateSuccessProgress(ingresarLoginJAVA);
                        } else {
                            ingresarLoginJAVA.setProgress(0);
                            simulateSuccessProgress(ingresarLoginJAVA);
                            clik = 0;
                            if (respuesta2.equals("1")) {
                                Snackbar.make(v, "Datos Incorrectos....", Snackbar.LENGTH_LONG).show();
                            } else if (respuesta2.equals("2")) {
                                Snackbar.make(v, "Error de conexion....", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Snackbar.make(v, "Proceso....", Snackbar.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public class Servicios1 extends AsyncTask<String, Void, String> {
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
                        if (resultJSON.equals("1")) {
                            devuelve = devuelve + respuestaJSON.getJSONObject("usuario").getString("id_usuario");
                            Clase_Pojo.id_usuario = devuelve;
                        }else
                        if (respuestaJSON.equals("2"))
                        {
                            devuelve = "2";
                        }
                        else if (respuestaJSON.equals("3"))
                        {
                            devuelve = "3";
                        }
                        else {
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
            String evaluarLogin = s;
            if (evaluarLogin.equals("error")) {
                respuesta = -1;
                respuesta2 = "1";
            } else if (evaluarLogin.equals("")) {
                respuesta = -1;
                respuesta2 = "2";
            } else if (evaluarLogin.equals(null)) {
                respuesta = -1;
                respuesta2 = "2";
            }
            else
            if (evaluarLogin.equals("2"))
            {
                respuesta = -1;
                respuesta2 = "1";
            }
            else if (evaluarLogin.equals("3"))
            {
                respuesta = -1;
                respuesta2 = "1";
            }
            else {
                respuesta = 1;
                respuesta2 = "3";
                Intent intent = new Intent(getActivity(), Inicio.class);
                startActivity(intent);
                getActivity().finish();
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

    private void simulateSuccessProgress(final CircularProgressButton button) {

        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(12000);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
                if (value == 99) {
                    button.setProgress(respuesta);//evalua si se iso el proceso o no
                    clik = 0;
                }
            }
        });
        widthAnimation.start();
    }
}