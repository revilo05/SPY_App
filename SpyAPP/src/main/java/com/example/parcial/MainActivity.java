package com.example.parcial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.nio.channels.InterruptedByTimeoutException;

public class MainActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    private void init() {
        context = getApplicationContext();
    }

    public void onClickRegistrar(View view) {
        Toast.makeText(context, "Registrar", Toast.LENGTH_LONG).show();
        Intent i = new Intent(context, GestionarEmergActivity.class);
        Bundle bolsa = new Bundle();
        bolsa.putInt("id", 0);
        i.putExtras(bolsa);
        startActivity(i);
    }

    public void onClickBuscar(View view) {
        Toast.makeText(context, "Buscar", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, BuscarEmergActivity.class);
        startActivity(intent);
    }

    public void onClickListar(View view) {
        Toast.makeText(context, "Listar", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, ListadoEmergActivity.class);
        startActivity(intent);
    }
}
