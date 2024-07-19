package com.example.parcial.controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.parcial.modelos.Emerg;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmergBD extends SQLiteOpenHelper implements IEmergBD {
    Context contexto;
    private List<Emerg> EmergList = new ArrayList<>();

    public EmergBD(@Nullable Context context, @Nullable String name,
                   @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        this.contexto = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE Emergencias (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date INTEGER, " +
                "titulo TEXT, " +
                "descripcion TEXT)";
        sqLiteDatabase.execSQL(sql);

        // Insertando datos de ejemplo
        String insert1 = "INSERT INTO Emergencias VALUES (null, 20240513, 'Emergencia 1', 'atencion a embarazada')";
        String insert2 = "INSERT INTO Emergencias VALUES (null, 20240205, 'Emergencia 2', 'Asistencia a hombre mayor')";
        String insert3 = "INSERT INTO Emergencias VALUES (null, 20240323, 'Emergencia 3', 'Atencion a niño pequeño')";

        sqLiteDatabase.execSQL(insert1);
        sqLiteDatabase.execSQL(insert2);
        sqLiteDatabase.execSQL(insert3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public Emerg elemento(int id) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM Emergencias WHERE id = " + id;
        Cursor cursor = database.rawQuery(sql, null);
        try {
            if (cursor.moveToNext()) {
                return extraerEmerg(cursor);
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d("TAG", "Error en elemento(id) EmergBD: " + e.getMessage());
            throw e;
        } finally {
            if (cursor != null) cursor.close();
        }

    }

    private Emerg extraerEmerg(Cursor cursor) {
        Emerg emerg = new Emerg();
        emerg.setId(cursor.getInt(0));
        emerg.setDate(cursor.getString(1));
        emerg.setTitulo(cursor.getString(2));
        emerg.setDescripcion(cursor.getString(3));
        return emerg;

    }

    @Override
    public Emerg elemento(String titulo) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM Emergencias WHERE titulo=?";
        Cursor cursor = database.rawQuery(sql, new String[]{titulo});
        try {
            if (cursor.moveToNext()) {
                Emerg emerg = extraerEmerg(cursor);
                Log.d("EmergBD", "Emergencia Encontrada: " + emerg.getTitulo());
                return emerg;
            } else {
                Log.d("EmergBD", "No se encontro Emergencia: " + titulo);
                return null;
            }
        } catch (Exception e) {
            Log.d("EmergBD", "Error en elemento(titulo): " + e.getMessage());
            throw e;
        } finally {
            if (cursor != null) cursor.close();
            database.close();
        }

    }

    @Override
    public List<Emerg> lista() {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM Emergencias ORDER BY titulo ASC";
        Cursor cursor = database.rawQuery(sql, null);
        List<Emerg> emergList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                emergList.add(
                        new Emerg(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return emergList;

    }

    @Override
    public void agregar(Emerg emerg) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", emerg.getDate());
        values.put("titulo", emerg.getTitulo());
        values.put("descripcion", emerg.getDescripcion());

        long result = database.insert("Emergencias", null, values);
        if (result == -1) {
            Log.e("EmergBD", "Error insertando datos: " + emerg.getTitulo());
        } else {
            Log.d("EmergBD", "Datos insertados: " + emerg.getTitulo());
        }
        database.close();

    }

    @Override
    public void actualizar(int id, Emerg emerg) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", emerg.getDate());
        values.put("titulo", emerg.getTitulo());
        values.put("descripcion", emerg.getDescripcion());

        int result = database.update("Emergencias", values, "id = ?", new String[]{String.valueOf(emerg.getId())});
        if (result == 0) {
            Log.e("EmergBD", "Error actualizando datos: " + emerg.getTitulo());
        } else {
            Log.d("EmergBD", "Datos actualizados: " + emerg.getTitulo());
        }
        database.close();

    }

    @Override
    public void borrar(int id) {
        SQLiteDatabase database = getWritableDatabase();
        int result = database.delete("Emergencias", "id = ?", new String[]{String.valueOf(id)});
        if (result == 0) {
            Log.e("EmergBD", "Error al borrar el registro con Id: " + id);
        } else {
            Log.d("EmergBD", "Registro borrado correctamente con Id: " + id);
        }
        database.close();

    }

    public List<Emerg> obtenerTodas() {
        List<Emerg> lista = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Emergencias", null);
        try {
            while (cursor.moveToNext()) {
                Emerg emerg = extraerEmerg(cursor);
                lista.add(emerg);
            }
        } finally {
            if (cursor != null) cursor.close();
            database.close();
        }
        return lista;

    }
}
