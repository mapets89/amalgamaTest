package com.example.mapets.testamalgama.helper;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mapets.testamalgama.entity.Perro;
import com.example.mapets.testamalgama.listener.RazasListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SingletonRazas {
    private static SingletonRazas intance = null;
    public Perro raza;

    private SingletonRazas(){

    }
    public static SingletonRazas getIntance(){
        if (intance == null){
            intance = new SingletonRazas();
        }
        return intance;
    }


    public void buscadorRazas (final Context context, final RazasListener listener){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url;
        url = "https://dog.ceo/api/breeds/list";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {

                            try {
                                    JSONArray razasArray = new JSONObject(response).getJSONArray("message");
                                    ArrayList<Perro> perros = new ArrayList<>();
                                    for (int i = 0; i < razasArray.length(); i++) {
                                        raza = new Perro(primMay(razasArray.get(i).toString()));
                                        perros.add(raza);
                                    }
                                    listener.razaslistener(perros);

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    CharSequence text;
                    int duration = Toast.LENGTH_LONG;

                    if (error instanceof TimeoutError) {
                        text = "Demasiado tiempo en cargar.... compruebe conexion a internet";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    else if (error instanceof NoConnectionError){
                        text = "Compruebe conexion a internet...";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    else if (error instanceof AuthFailureError) {
                        text = "Falla de Autorizacion...";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (error instanceof ServerError) {
                        text = "Problemas con el servidor....";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (error instanceof NetworkError) {
                        text = "Problema de red, compuebe su conexion a internet...";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (error instanceof ParseError) {
                        text = "Parse error";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                }
            });
            queue.add(stringRequest);
    }

    private static String primMay(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return nombre;
        } else {
            return nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
        }
    }
}
