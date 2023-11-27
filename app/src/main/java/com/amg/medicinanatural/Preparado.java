package com.amg.medicinanatural;

/* loaded from: classes3.dex */
public class Preparado {
    String desc;
    String nombre;

    public Preparado(String text) {
        parse(text);
    }

    private void parse(String text) {
        String[] elems = text.split("#");
        this.nombre = elems[0].split(":")[1];
        this.desc = elems[1].split(":")[1];
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getDesc() {
        return this.desc;
    }
}
