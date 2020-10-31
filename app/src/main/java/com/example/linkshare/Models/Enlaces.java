package com.example.linkshare.Models;

public class Enlaces {

    private String titulo;
    private String descripcion;
    private String img;

    public Enlaces(){}

    public Enlaces(String titulo, String descripcion, String img) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.img = img;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
