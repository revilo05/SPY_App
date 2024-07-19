package com.example.parcial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.parcial.controladores.EmergBD;
import com.example.parcial.modelos.Emerg;

public class BuscarEmergActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    EditText txttitulo;
    Button btnbuscar;
    EmergBD emergBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscar_emerg);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }
    private void init() {
        context = getApplicationContext();
        txttitulo = findViewById(R.id.bus_txttitulo);
        btnbuscar = findViewById(R.id.bus_btnbuscar);
        btnbuscar.setOnClickListener(this);

        emergBD = new EmergBD(context, "EmergenBD.db", null, 1);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bus_btnbuscar) {
            String titulo = txttitulo.getText().toString();
            if (!titulo.isEmpty()) {
                Emerg emerg = buscarEmerg(titulo);
                if (emerg != null) {
                    Bundle bolsa = new Bundle();

                    bolsa.putInt("id", emerg.getId());
                    bolsa.putString("titulo", emerg.getTitulo());
                    bolsa.putString("descripcion", emerg.getDescripcion());
                    bolsa.putString("fecha", String.valueOf(emerg.getDate()));

                    Intent i = new Intent(context, GestionarEmergActivity.class);
                    i.putExtras(bolsa);
                    startActivity(i);
                } else {
                    Toast.makeText(context, "No existe una emergencia con ese título", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Ingrese un título de emergencia válido", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private Emerg buscarEmerg(String titulo) {
        EmergBD emergBD = new EmergBD(context, "EmergenBD.db", null, 1);
        Emerg emerg = emergBD.elemento(titulo);

        return emerg;
    }




}