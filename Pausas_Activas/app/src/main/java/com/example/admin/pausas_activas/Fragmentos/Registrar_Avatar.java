package com.example.admin.pausas_activas.Fragmentos;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

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
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Registrar_Avatar extends Fragment implements View.OnClickListener {

    Spinner generoJAVA;
    EditText nombrePersonajeJAVA;
    int respuesta = -1;//Respuesta del servicio para efetuar en el boton
    int clik = 0;//Intenos al boton
    String respuesta2 = "2";
    CheckBox discapacidadJAVA;
    String generoSelec;
    String id_usuario = Clase_Pojo.id_usuario;
    CircularProgressButton registrarAvatar;


    public Registrar_Avatar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrar__avatar, container, false);
        nombrePersonajeJAVA = (EditText) view.findViewById(R.id.avatar);
        discapacidadJAVA = (CheckBox) view.findViewById(R.id.discapacidad);
        generoJAVA= (Spinner)view.findViewById(R.id.genero);
        initCustomSpinner();
        registrarAvatar = (CircularProgressButton) view.findViewById(R.id.registrarAvatar);
        view.findViewById(R.id.registrarAvatar).setOnClickListener(this);
        return  view;
    }
    private void initCustomSpinner() {
        // Spinner Drop down elements
        ArrayList<String> languages = new ArrayList<String>();
        languages.add("Seleciona tu  genero:.");
        languages.add("Masculino");
        languages.add("Femenino");


        CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(getActivity(),languages);
        generoJAVA.setAdapter(customSpinnerAdapter);
        generoJAVA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                generoSelec = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<String> asr;

        public CustomSpinnerAdapter(Context context, ArrayList<String> asr) {
            this.asr = asr;
            activity = context;
        }


        public int getCount() {
            return asr.size();
        }

        public Object getItem(int i) {
            return asr.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(getActivity());
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(15);
            //txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
            txt.setText(asr.get(i));
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.registrarAvatar:
                String nombreAvatar = nombrePersonajeJAVA.getText().toString();
                if (nombreAvatar.equals("")) {
                    Snackbar.make(view, "Nombre avatar vacio", Snackbar.LENGTH_LONG).show();
                }
                else
                if (generoSelec.equals("0"))
                {
                    Snackbar.make(view, "Seleciona tu genero", Snackbar.LENGTH_LONG).show();
                }
                else {

                    if (discapacidadJAVA.isChecked()) {
                        Clase_Pojo.nombreAvatar=nombreAvatar;
                        Clase_Pojo.generoAvatar=generoSelec;
                        Discapacidad discapacidad = new Discapacidad();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back
                        transaction.replace(R.id.contenedor2, discapacidad);
                        transaction.addToBackStack(null);
                        // Commit the transaction
                        transaction.commit();

                    }

                    else {
                        clik++;
                        if (clik == 1) {
                            if (registrarAvatar.getProgress() == 0) {
                                Servicios8 hiloconexion8;
                                String IP = "http://pasennova.esy.es/pausas_activas/index";
                                String INSERTAVATAR = IP + "/registrar_avatar.php";
                                hiloconexion8 = new Servicios8();
                                hiloconexion8.execute(INSERTAVATAR, "1", nombrePersonajeJAVA.getText().toString(),id_usuario, generoSelec);
                                simulateSuccessProgress5(registrarAvatar);
                            } else {
                                registrarAvatar.setProgress(0);
                                simulateSuccessProgress5(registrarAvatar);
                                clik = 0;
                                if (respuesta2.equals("1")) {
                                    Snackbar.make(view, "Error Al Registrar Vulvelo A Intentar....", Snackbar.LENGTH_LONG).show();
                                } else if (respuesta2.equals("2")) {
                                    Snackbar.make(view, "Error De Conexion....", Snackbar.LENGTH_LONG).show();
                                } else if (respuesta2.equals("3")) {
                                    Intent intent = new Intent(getActivity(), Evaluar_Avatar.class);
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                }
                break;
        }
    }
    public class Servicios8 extends AsyncTask<String, Void, String> {

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
                    jsonParam.put("avatar", params[2]);
                    jsonParam.put("id_usuario", params[3]);
                    jsonParam.put("genero", params[4]);
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
                        } else {
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
            if (evaluarLogin.equals("1")) {
                respuesta = 1;
                respuesta2 = "3";
            } else if (evaluarLogin.equals(null)) {
                respuesta = -1;
                respuesta2 = "2";
            } else if (evaluarLogin.equals("")) {
                respuesta = -1;
                respuesta2 = "2";
            } else if (evaluarLogin.equals("error")) {
                respuesta = -1;
                respuesta2 = "1";
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

    private void simulateSuccessProgress5(final CircularProgressButton button) {

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
