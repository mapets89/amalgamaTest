package com.example.mapets.testamalgama.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mapets.testamalgama.activities.ImageActivity;
import com.example.mapets.testamalgama.R;
import com.example.mapets.testamalgama.entity.Perro;
import com.example.mapets.testamalgama.listener.RazasListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RazaAdapter extends RecyclerView.Adapter<RazaAdapter.ViewHolder> implements RazasListener {

    final private Context context;
    private ArrayList<Perro> mDataset;
    private String urlImage;
    private String finalUrl;
    private String nombreRaza;



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView razasTv;
        private Perro listaRazas;
        private Context context = itemView.getContext();
        public RazasListener razasListener;

        private ViewHolder(View itemView) {
            super(itemView);
            razasTv = itemView.findViewById(R.id.razas);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    urlImage = "https://dog.ceo/api/breed/"+primMin(listaRazas.getRaza())+"/images/random";
                    nombreRaza = listaRazas.getRaza();
                        urlImageFinal(urlImage, nombreRaza);
                    }
            });

        }
        private void fillData (Perro Lista, Context context){
            this.listaRazas = Lista;
            this.context = context;
            razasTv.setText(listaRazas.getRaza());

        }
    }

    public RazaAdapter(ArrayList<Perro> myDataset, Context context){
        this.mDataset = myDataset;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raza, parent, false);
        v.setCardElevation(20);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int position) {
        Perro perro = mDataset.get(position);
        viewHolder.fillData(perro, context);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    private static String primMin(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return nombre;
        } else {
            return nombre.substring(0, 1).toLowerCase() + nombre.substring(1);
        }
    }


    private void urlImageFinal(String urlImage, final String nombreRaza){
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.start();
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, urlImage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    finalUrl = new JSONObject(response).getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(context, ImageActivity.class);
                intent.putExtra("urlImage", finalUrl);
                intent.putExtra("raza", nombreRaza);
                context.startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }


    @Override
    public void razaslistener(ArrayList<Perro> perros) {

    }
}
