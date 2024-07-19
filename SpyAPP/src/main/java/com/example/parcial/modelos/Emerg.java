package com.example.parcial.modelos;

public class Emerg {
    // Atributos de la clase
    private int id;
    private String date;  // Cambiado a tipo int para almacenar la fecha (puedes cambiarlo a String si prefieres)
    private String titulo;
    private String descripcion;

    // Constructor por defecto (sin argumentos)
    public Emerg() {
    }
    public Emerg(int id, String date, String titulo, String descripcion) {
        this.id = id;
        this.date = date;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    // Métodos getter y setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
