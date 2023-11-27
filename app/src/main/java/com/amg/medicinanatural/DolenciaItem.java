package com.amg.medicinanatural;

/* loaded from: classes3.dex */
public class DolenciaItem {
    String desc;
    String nombre;
    String[] remedios;

    public DolenciaItem(String text) {
        parse(text);
    }

    private void parse(String text) {
        String[] elems = text.split("#");
        this.nombre = elems[0].split(":")[1];
        this.desc = elems[1].split(":")[1];
        this.remedios = elems[2].split(":")[1].split("%");
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getDesc() {
        return this.desc;
    }

    public String[] getRemedios() {
        return this.remedios;
    }
}
