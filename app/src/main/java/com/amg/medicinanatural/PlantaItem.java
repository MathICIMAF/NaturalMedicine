package com.amg.medicinanatural;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PlantaItem {
    List<Integer> categorias;
    String imagen;
    boolean isFavorito;
    String modo;
    String nCient;
    String nombre;
    String organoUsado;
    private final int pos;
    String usos;

    public PlantaItem(String text, String imagen, int pos, boolean isFavorito) {
        parse(text);
        this.imagen = imagen;
        this.pos = pos;
        this.isFavorito = isFavorito;
    }

    private void parse(String text) {
        String[] elems = text.split("#");
        this.nCient = elems[0].split(":")[1];
        this.nombre = elems[1].split(":")[1];
        this.organoUsado = elems[2].split(":")[1];
        this.usos = elems[3].split(":")[1];
        this.modo = elems[4].split(":")[1];
        this.categorias = new ArrayList();
        String[] cats = new String[0];
        try {
            cats = elems[5].split(":")[1].split(",");
        } catch (Exception e) {
        }
        for (String str : cats) {
            this.categorias.add(Integer.valueOf(Integer.parseInt(str.trim())));
        }
    }

    public int getPos() {
        return this.pos;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getNCientifico() {
        return this.nCient;
    }

    public String getImagen() {
        return this.imagen;
    }

    public String getOrganoUsado() {
        return this.organoUsado;
    }

    public String getUsos() {
        return this.usos;
    }

    public String getModo() {
        return this.modo;
    }

    public List<Integer> getCategorias() {
        return this.categorias;
    }

    public boolean isFavorito() {
        return this.isFavorito;
    }

    public void setFavorito(boolean isFavorito) {
        this.isFavorito = isFavorito;
    }
}
