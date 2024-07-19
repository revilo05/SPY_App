package com.example.parcial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.parcial.controladores.EmergBD;
import com.example.parcial.controladores.SelectListener;
import com.example.parcial.modelos.Emerg;

import java.util.ArrayList;
import java.util.List;

public class ListadoEmergActivity extends AppCompatActivity implements SelectListener {

    ListView listView;
    List<String> emergenciasList;
    ArrayList<String> nombresEmerg;
    ArrayList<Integer> idEmerg;
    EmergBD emergBD;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado_emerg);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        cargarEmergencias();

    }

    private void cargarEmergencias() {
        List<Emerg> emergencias = emergBD.obtenerTodas();
        emergenciasList = new ArrayList<>();
        final List<Integer> ids = new ArrayList<>();
        for (Emerg emerg : emergencias) {
            emergenciasList.add(emerg.getTitulo());
            ids.add(emerg.getId());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, emergenciasList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String titulo = emergenciasList.get(position);
                Intent intent = new Intent(context, GestionarEmergActivity.class);
                intent.putExtra("titulo", titulo);
                startActivity(intent);
            }
        });

    }

    private void init(){
        context = this.getApplicationContext();
        emergBD = new EmergBD(context, "EmergenBD.de", null, 1);
        listView = findViewById(R.id.listaEmergencia);
        llenarListView();
    }

    private void llenarListView() {
        nombresEmerg = new ArrayList<String>();
        idEmerg = new ArrayList<Integer>();

        List<Emerg> listaEmerg = emergBD.lista();
        for (int i = 0; i < listaEmerg.size(); i++){
            Emerg e = listaEmerg.get(i);
            nombresEmerg.add(e.getTitulo());
            idEmerg.add(e.getId());
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        context,
                        android.R.layout.simple_list_item_1,
                        nombresEmerg
                );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Emerg emerg = emergBD.elemento(idEmerg.get(i));
                Bundle bolsa = new Bundle();
                bolsa.putInt("id", emerg.getId());
                bolsa.putString("titulo", emerg.getTitulo());
                bolsa.putString("descripcion", emerg.getDescripcion());
                bolsa.putString("fecha", String.valueOf(emerg.getDate()));

                Intent intetn = new Intent(context, GestionarEmergActivity.class);
                intetn.putExtras(bolsa);
                startActivity(intetn);
            }
        });
    }

    @Override
    public void onTitemClick(String titulo) {

    }
}