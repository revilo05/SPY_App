package com.example.parcial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.parcial.controladores.EmergBD;
import com.example.parcial.modelos.Emerg;

public class GestionarEmergActivity extends AppCompatActivity{

    Context context;
    EditText txtTitulo, txtFecha, txtDescripcion;
    int id;
    Button btnguardar, btnactualizar, btnborrar;
    EmergBD emergBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gestionar_emerg);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intit();
    }
    private void intit(){
        context = getApplicationContext();
        txtTitulo = findViewById(R.id.ges_txttitulo);
        txtFecha = findViewById(R.id.ges_txtfecha);
        txtDescripcion = findViewById(R.id.ges_txtdescripcion);
        emergBD = new EmergBD(context, "EmergenciasBD.db", null, 1);
        btnactualizar = findViewById(R.id.ges_btnactualizar);
        btnguardar = findViewById(R.id.ges_btnGuardar);
        btnborrar = findViewById(R.id.ges_btnborrar);

        Intent i = getIntent();
        Bundle bolsa = i.getExtras();
        id = bolsa.getInt("id");
        if (id != 0){
            txtTitulo.setText(bolsa.getString("titulo"));
            txtFecha.setText(bolsa.getString("fecha"));
            txtDescripcion.setText(bolsa.getString("descripcion"));
            btnguardar.setEnabled(false);
        }else {
            btnactualizar.setEnabled(false);
            btnborrar.setEnabled(false);
        }


    }

    private void limpiarCampos(){
        id = 0;
        txtTitulo.setText("");
        txtDescripcion.setText("");
        txtFecha.setText("");
    }

    private Emerg llenarEmerg() {
        Emerg emerg = new Emerg();
        String t = txtTitulo.getText().toString();
        String d = txtDescripcion.getText().toString();
        String f = txtFecha.getText().toString();

        emerg.setId(id);
        emerg.setTitulo(t);
        emerg.setDescripcion(d);
        emerg.setDate(f);

        return emerg;

    }
    public void guardar(View v) {
        String titulo = txtTitulo.getText().toString();
        String fecha = txtFecha.getText().toString();
        String descripcion = txtDescripcion.getText().toString();

        if (!titulo.isEmpty() && !fecha.isEmpty() && !descripcion.isEmpty()) {
            Emerg emerg = new Emerg();
            emerg.setTitulo(titulo);
            emerg.setDate(fecha);
            emerg.setDescripcion(descripcion);

            EmergBD emergBD = new EmergBD(this, "EmergenBD.db", null, 1);
            emergBD.agregar(emerg);

            Toast.makeText(this, "Guardado Nuevo OK", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    public void actualizar(View v) {
        guardar(v);
    }

    public void borrar(View v) {
        if (id == 0) {
            Toast.makeText(context, "No se pueden borrar datos", Toast.LENGTH_LONG).show();
        } else {
            emergBD.borrar(id);
            limpiarCampos();
            btnguardar.setEnabled(true);
            btnactualizar.setEnabled(false);
            btnborrar.setEnabled(false);
            Toast.makeText(context, "Datos Borrados", Toast.LENGTH_LONG).show();
        }
    }





}