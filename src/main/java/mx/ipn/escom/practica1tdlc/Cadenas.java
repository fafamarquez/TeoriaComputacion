/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.ipn.escom.practica1tdlc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author rafael-marquez
 */
public class Cadenas {
    
    public List<String> obtenerPrefijos(String cadena){
        List<String> prefijos = new ArrayList<>();
        for (int i = 0; i <= cadena.length(); i++) {
            String resultado = cadena.substring(0,i);
            if (resultado.isEmpty()) {
                prefijos.add("λ");
            }
            else {
                prefijos.add(resultado);
            }
        }
        return prefijos;
    }
    
    public List<String> obtenerSufijos(String cadena){
        List<String> sufijos = new ArrayList<>();
        for (int i = cadena.length(); i >= 0 ; i--) {
            String resultado = cadena.substring(i,cadena.length());
            if (resultado.isEmpty()) {
                sufijos.add("λ");
            }
            else {
                sufijos.add(resultado);
            }
        }
        return sufijos;
    }

    public Set<String> obtenerSubcadenas(String cadena){
        TreeSet<String> subcadenas = new TreeSet<>();
        subcadenas.add("λ");
        for (int i = 0; i <= cadena.length(); i++) {
            for (int j = i + 1; j <= cadena.length(); j++) {
                String resultado = cadena.substring(i, j);
                subcadenas.add(resultado);
            }
        }
        return subcadenas;
    }
    
    public void exportarResultados(String nombreArchivo, String titulo, Collection<String> datos) {
    try (PrintWriter out = new PrintWriter(new FileWriter(nombreArchivo, true))) {
        out.println("=== " + titulo + " ===");
        for (String linea : datos) {
            out.println(linea);
        }
        out.println();
        System.out.println("Se guardó correctamente en " + nombreArchivo);
    } catch (IOException e) {
        System.err.println("Error al escribir el archivo: " + e.getMessage());
    }
}
}
