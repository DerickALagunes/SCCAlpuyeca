package com.simor.sistemacontrolcobros.model;

import java.util.ArrayList;

public class Mensaje {

    // Definición correcta de Enum
    public enum TipoMensaje {
        ERROR, SUCCESS, WARNING, INFO, NORMAL;
    }

    // Atributos
    private TipoMensaje tipo = TipoMensaje.ERROR; // Valor por defecto
    private ArrayList<String> texto = new ArrayList<>(); // Inicialización
    private String img = null;

    // Constructor vacío
    public Mensaje() {}

    // Constructor con parámetros
    public Mensaje(TipoMensaje tipo, ArrayList<String> texto, String img) {
        this.tipo = tipo;
        this.texto = texto != null ? texto : new ArrayList<>();
        this.img = img;
    }

    public Mensaje(TipoMensaje tipo, ArrayList<String> texto) {
        this.tipo = tipo;
        this.texto = texto != null ? texto : new ArrayList<>();
    }

    public Mensaje(ArrayList<String> texto, String img) {
        this.texto = texto != null ? texto : new ArrayList<>();
        this.img = img;
    }

    public Mensaje(ArrayList<String> texto) {
        this.texto = texto != null ? texto : new ArrayList<>();
    }

    // Getters y Setters
    public TipoMensaje getTipo() {
        return tipo;
    }

    public void setTipo(TipoMensaje tipo) {
        this.tipo = tipo;
    }

    public ArrayList<String> getTexto() {
        return texto;
    }

    public void setTexto(ArrayList<String> texto) {
        this.texto = texto;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    // Método para agregar un mensaje al texto
    public void agregarTexto(String mensaje) {
        this.texto.add(mensaje);
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "tipo=" + tipo +
                ", texto=" + texto +
                ", img='" + img + '\'' +
                '}';
    }
}
