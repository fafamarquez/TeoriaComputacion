/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.ipn.escom.practica1tdlc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Clase para calcular la Cerradura de Kleene (Σ*) y Cerradura Positiva (Σ+)
 * @author rafael-marquez
 */
public class Cerradura {
    
    /**
     * Calcula la Cerradura de Kleene (Σ*) para un alfabeto dado hasta una longitud máxima
     * Incluye la cadena vacía (λ)
     * @param alfabeto Lista de símbolos del alfabeto
     * @param longitudMax Longitud máxima de las cadenas a generar
     * @return Set con todas las cadenas de Σ* hasta longitudMax
     */
    public Set<String> calcularKleene(List<String> alfabeto, int longitudMax) {
        TreeSet<String> kleene = new TreeSet<>();
        
        // Agregar cadena vacía (λ)
        kleene.add("λ");
        
        // Generar cadenas de longitud 1 hasta longitudMax
        for (int longitud = 1; longitud <= longitudMax; longitud++) {
            generarCombinaciones(alfabeto, "", 0, longitud, kleene);
        }
        
        return kleene;
    }
    
    /**
     * Calcula la Cerradura Positiva (Σ+) para un alfabeto dado hasta una longitud máxima
     * NO incluye la cadena vacía (λ)
     * @param alfabeto Lista de símbolos del alfabeto
     * @param longitudMax Longitud máxima de las cadenas a generar
     * @return Set con todas las cadenas de Σ+ hasta longitudMax
     */
    public Set<String> calcularPositiva(List<String> alfabeto, int longitudMax) {
        TreeSet<String> positiva = new TreeSet<>();
        
        // Generar cadenas de longitud 1 hasta longitudMax (sin incluir λ)
        for (int longitud = 1; longitud <= longitudMax; longitud++) {
            generarCombinaciones(alfabeto, "", 0, longitud, positiva);
        }
        
        return positiva;
    }
    
    /**
     * Calcula ambas cerraduras (Kleene y Positiva) y las devuelve en un objeto contenedor
     * @param alfabeto Lista de símbolos del alfabeto
     * @param longitudMax Longitud máxima de las cadenas a generar
     * @return Objeto ResultadoCerradura con ambas cerraduras
     */
    public ResultadoCerradura calcularAmbas(List<String> alfabeto, int longitudMax) {
        Set<String> kleene = calcularKleene(alfabeto, longitudMax);
        Set<String> positiva = calcularPositiva(alfabeto, longitudMax);
        
        return new ResultadoCerradura(kleene, positiva, alfabeto, longitudMax);
    }
    
    /**
     * Método recursivo para generar todas las combinaciones posibles
     * @param alfabeto Lista de símbolos del alfabeto
     * @param actual Cadena actual que se está construyendo
     * @param posicion Posición actual en la recursión
     * @param longitudMax Longitud máxima de la cadena a generar
     * @param resultados Set donde se almacenan los resultados
     */
    private void generarCombinaciones(List<String> alfabeto, String actual, 
                                      int posicion, int longitudMax, 
                                      Set<String> resultados) {
        if (posicion == longitudMax) {
            if (!actual.isEmpty()) {
                resultados.add(actual);
            }
            return;
        }
        
        for (String simbolo : alfabeto) {
            generarCombinaciones(alfabeto, actual + simbolo, 
                               posicion + 1, longitudMax, resultados);
        }
    }
    
    /**
     * Versión iterativa (no recursiva) para generar combinaciones
     * Útil para longitudes grandes donde la recursión podría ser ineficiente
     * @param alfabeto Lista de símbolos del alfabeto
     * @param longitudMax Longitud máxima de las cadenas a generar
     * @return Set con todas las combinaciones
     */
    public Set<String> generarCombinacionesIterativo(List<String> alfabeto, int longitudMax) {
        TreeSet<String> resultados = new TreeSet<>();
        
        // Comenzar con cadenas de longitud 1
        List<String> cadenasActuales = new ArrayList<>();
        for (String simbolo : alfabeto) {
            cadenasActuales.add(simbolo);
            resultados.add(simbolo);
        }
        
        // Generar cadenas de longitud 2 hasta longitudMax
        for (int longitud = 2; longitud <= longitudMax; longitud++) {
            List<String> nuevasCadenas = new ArrayList<>();
            
            for (String cadena : cadenasActuales) {
                for (String simbolo : alfabeto) {
                    String nueva = cadena + simbolo;
                    nuevasCadenas.add(nueva);
                    resultados.add(nueva);
                }
            }
            
            cadenasActuales = nuevasCadenas;
        }
        
        return resultados;
    }
    
    /**
     * Obtiene estadísticas de las cerraduras calculadas
     * @param alfabeto Lista de símbolos del alfabeto
     * @param longitudMax Longitud máxima
     * @return String con las estadísticas formateadas
     */
    public String obtenerEstadisticas(List<String> alfabeto, int longitudMax) {
        StringBuilder sb = new StringBuilder();
        
        int totalKleene = 0;
        int totalPositiva = 0;
        
        // Calcular totales
        for (int i = 0; i <= longitudMax; i++) {
            int combinaciones = (int) Math.pow(alfabeto.size(), i);
            totalKleene += combinaciones;
            if (i > 0) {
                totalPositiva += combinaciones;
            }
        }
        
        sb.append("ESTADÍSTICAS:\n");
        sb.append("=============\n");
        sb.append("Tamaño del alfabeto: |Σ| = ").append(alfabeto.size()).append("\n");
        sb.append("Símbolos: {").append(String.join(", ", alfabeto)).append("}\n");
        sb.append("Longitud máxima: ").append(longitudMax).append("\n\n");
        
        sb.append("Cerradura de Kleene Σ*:\n");
        sb.append("  • Incluye cadena vacía (λ)\n");
        sb.append("  • Total de cadenas: ").append(totalKleene).append("\n\n");
        
        sb.append("Cerradura Positiva Σ+:\n");
        sb.append("  • No incluye cadena vacía\n");
        sb.append("  • Total de cadenas: ").append(totalPositiva).append("\n\n");
        
        sb.append("Distribución por longitud:\n");
        for (int i = 0; i <= longitudMax; i++) {
            int count = (int) Math.pow(alfabeto.size(), i);
            if (i == 0) {
                sb.append("  Longitud ").append(i).append(" (λ): ").append(count).append("\n");
            } else {
                sb.append("  Longitud ").append(i).append(": ").append(count).append(" cadenas\n");
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Clase interna para contener los resultados de ambas cerraduras
     */
    public static class ResultadoCerradura {
        private final Set<String> kleene;
        private final Set<String> positiva;
        private final List<String> alfabeto;
        private final int longitudMax;
        
        public ResultadoCerradura(Set<String> kleene, Set<String> positiva, 
                                  List<String> alfabeto, int longitudMax) {
            this.kleene = kleene;
            this.positiva = positiva;
            this.alfabeto = alfabeto;
            this.longitudMax = longitudMax;
        }
        
        public Set<String> getKleene() {
            return kleene;
        }
        
        public Set<String> getPositiva() {
            return positiva;
        }
        
        public List<String> getAlfabeto() {
            return alfabeto;
        }
        
        public int getLongitudMax() {
            return longitudMax;
        }
        
        /**
         * Formatea los resultados para mostrarlos en la interfaz
         * @return String formateado con los resultados
         */
        public String formatearResultados() {
            StringBuilder sb = new StringBuilder();
            
            sb.append("RESULTADOS DE CERRADURAS\n");
            sb.append("========================\n\n");
            sb.append("Alfabeto Σ = {").append(String.join(", ", alfabeto)).append("}\n");
            sb.append("Longitud máxima: ").append(longitudMax).append("\n\n");
            
            sb.append("Σ* (Cerradura de Kleene):\n");
            sb.append("Incluye ").append(kleene.size()).append(" cadenas\n");
            sb.append(formatearLista(kleene, 50)).append("\n\n");
            
            sb.append("Σ+ (Cerradura Positiva):\n");
            sb.append("Incluye ").append(positiva.size()).append(" cadenas\n");
            sb.append(formatearLista(positiva, 50)).append("\n");
            
            return sb.toString();
        }
        
        /**
         * Formatea una lista para mostrar con límite de elementos
         */
        private String formatearLista(Set<String> lista, int maxMostrar) {
            List<String> listaOrdenada = new ArrayList<>(lista);
            if (listaOrdenada.size() <= maxMostrar) {
                return String.join(", ", listaOrdenada);
            } else {
                List<String> primeros = listaOrdenada.subList(0, maxMostrar);
                return String.join(", ", primeros) + 
                       "\n... y " + (listaOrdenada.size() - maxMostrar) + " cadenas más";
            }
        }
        
        /**
         * Genera contenido para exportar a archivo
         * @return String listo para guardar en archivo
         */
        public String generarContenidoArchivo() {
            StringBuilder sb = new StringBuilder();
            
            sb.append("PRÁCTICA 1 - TEORÍA DE LA COMPUTACIÓN\n");
            sb.append("CERRADURA DE KLEENE Y POSITIVA\n");
            sb.append("================================\n\n");
            sb.append("Fecha: ").append(new java.util.Date()).append("\n\n");
            
            sb.append("INFORMACIÓN DE ENTRADA:\n");
            sb.append("=======================\n");
            sb.append("Alfabeto Σ = {").append(String.join(", ", alfabeto)).append("}\n");
            sb.append("Longitud máxima: ").append(longitudMax).append("\n\n");
            
            sb.append("RESULTADOS:\n");
            sb.append("===========\n\n");
            
            sb.append("1. CERRADURA DE KLEENE (Σ*):\n");
            sb.append("   Total: ").append(kleene.size()).append(" cadenas\n");
            sb.append("   Cadenas:\n   ");
            
            // Escribir en columnas para mejor legibilidad
            List<String> listaKleene = new ArrayList<>(kleene);
            for (int i = 0; i < listaKleene.size(); i++) {
                if (i > 0 && i % 10 == 0) {
                    sb.append("\n   ");
                }
                sb.append(listaKleene.get(i));
                if (i < listaKleene.size() - 1) {
                    sb.append(", ");
                }
            }
            
            sb.append("\n\n2. CERRADURA POSITIVA (Σ+):\n");
            sb.append("   Total: ").append(positiva.size()).append(" cadenas\n");
            sb.append("   Cadenas:\n   ");
            
            List<String> listaPositiva = new ArrayList<>(positiva);
            for (int i = 0; i < listaPositiva.size(); i++) {
                if (i > 0 && i % 10 == 0) {
                    sb.append("\n   ");
                }
                sb.append(listaPositiva.get(i));
                if (i < listaPositiva.size() - 1) {
                    sb.append(", ");
                }
            }
            
            return sb.toString();
        }
    }
}
