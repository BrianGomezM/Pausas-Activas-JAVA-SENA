package com.example.admin.pausas_activas.Fragmentos;


import android.animation.ValueAnimator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
public class Recuperar extends Fragment implements View.OnClickListener {
    int respuesta = -1;//Respuesta del servicio para efetuar en el boton
    String respuesta2 = "2";
    int clik = 0;//Intenos al boton

    EditText correoRecuperarJAVA, documentoRecuperarJAVA;

    public Recuperar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recuperar, container, false);
        correoRecuperarJAVA=(EditText)view.findViewById(R.id.correoRecuperar);
        documentoRecuperarJAVA=(EditText)view.findViewById(R.id.documentoRecuperar);
        view.findViewById(R.id.recuperarUsuario).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recuperarUsuario:
                String correoRecuperarPrueva = correoRecuperarJAVA.getText().toString();
                String documentoRecuperar = documentoRecuperarJAVA.getText().toString();
                if (correoRecuperarPrueva.equals(""))
                {
                    Snackbar.make(v,"Correo Electronico vacio",Snackbar.LENGTH_LONG).show();
                }
                else
                if (documentoRecuperar.equals(""))
                {
                    Snackbar.make(v,"Numero De Documento vacio",Snackbar.LENGTH_LONG).show();
                }
                else
                if (!ValidarCorreo(correoRecuperarPrueva))
                {
                    Snackbar.make(v,"Correo Electronico incorrecto",Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    clik++;
                    if (clik == 1)
                    {
                        CircularProgressButton recuperarUsuarioJAVA = (CircularProgressButton) v.findViewById(R.id.recuperarUsuario);
                        recuperarUsuarioJAVA.setProgress(0);
                        if (recuperarUsuarioJAVA.getProgress()==0)
                        {
                            Servicios3 hiloconexion;
                            String IP = "http://pasennova.esy.es/pausas_activas/index";
                            String RECUPERAR = IP + "/recuperar_usuario.php";
                            hiloconexion = new Servicios3();
                            String cadenallamada = RECUPERAR + "?correo=" + correoRecuperarPrueva + "&&documento=" + documentoRecuperar;
                            hiloconexion.execute(cadenallamada, "3");
                            simulateSuccessProgress1(recuperarUsuarioJAVA);
                        }
                        else
                        {
                            recuperarUsuarioJAVA.setProgress(0);
                            simulateSuccessProgress1(recuperarUsuarioJAVA);
                            clik = 0;
                            if (respuesta2.equals("1"))
                            {
                                Snackbar.make(v, "Datos Incorrectos....", Snackbar.LENGTH_LONG).show();
                            }
                            else if (respuesta2.equals("2"))
                            {
                                Snackbar.make(v, "Error de conexion....", Snackbar.LENGTH_LONG).show();
                            }
                            else
                            if (respuesta2.equals("3"))
                            {
                                Snackbar.make(v, "Tu nueva cotnrseña esta en correo....", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                    else
                    {
                        Snackbar.make(v, "Proceso....", Snackbar.LENGTH_LONG).show();
                    }
                }break;
        }
    }

    private boolean ValidarCorreo(String correo)
    {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }
    public class Servicios3 extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            String cadena = params[0];
            URL url = null;
            String devuelve ="";

            if (params[1] == "3") ///Olvido contrasela
            {
                try {

                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" + " (Linux; Android 5.0.1; es-ES) Ejemplo HTTP");
                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();
                    if (respuesta == HttpURLConnection.HTTP_OK) {
                        InputStream in = new BufferedInputStream(connection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        String line;
                        while ((line = reader.readLine()) != null)
                        {
                            result.append(line);
                        }
                        JSONObject respuestaJSON = new JSONObject(result.toString());
                        String resultJSON = respuestaJSON.getString("estado");
                        if (resultJSON.equals("1")) {
                            devuelve = "1";
                        }
                        else if (resultJSON.equals("2"))
                        {
                            devuelve = "2";
                        }
                        else if (resultJSON.equals("3"))
                        {
                            devuelve = "3";
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

            String evaluarLogin = s;

            if(evaluarLogin.equals("error"))
            {
                respuesta =-1;
                respuesta2="1";
            }
            else if (evaluarLogin.equals(""))
            {
                respuesta =-1;
                respuesta2="2";
            }
            else
            if (evaluarLogin==null)
            {
                respuesta =-1;
                respuesta2="2";
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
            else
            if (evaluarLogin.equals("1"))
            {
                respuesta =1;
                respuesta2="3";
                Toast.makeText(getActivity(),"Su nueva contraseña esta en el correo....",Toast.LENGTH_LONG).show();
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

    private void simulateSuccessProgress1(final CircularProgressButton button)
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
                    clik = 0;
                }
            }
        });
        widthAnimation.start();
    }
}
