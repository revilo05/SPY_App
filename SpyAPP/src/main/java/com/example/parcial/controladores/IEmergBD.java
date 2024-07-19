package com.example.parcial.controladores;

import com.example.parcial.modelos.Emerg;

import java.util.List;

public interface IEmergBD {

    Emerg elemento(int id);// Devuelve el elemento dado su id
    Emerg elemento(String titulo);// Devuelve el elemento dado su título exacto


    // Devuelve una lista con todos los elementos registrados
    List<Emerg> lista();


    void agregar(Emerg emerg);// Añadir
    void actualizar(int id, Emerg emerg);// Actualizar
    void borrar(int id);// Eliminar

}
