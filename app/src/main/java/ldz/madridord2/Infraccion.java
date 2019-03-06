package ldz.madridord2;

import android.text.SpannableString;

import java.io.Serializable;

/**
 * Created by Leeloo on 29/04/2018.
 */

public class Infraccion implements Serializable {
    private int id;
    private String clave;
    private String articulo;
    private String euros;
    private String puntos;
    //private String descripcion;
    //STRING SPANNABLE
    private SpannableString descripcion;
    private String tipo;
    private String norma;

    public Infraccion(){}

    public Infraccion(String tipo, SpannableString descripcion, String articulo){
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.articulo = articulo;
    }



    public Infraccion(int id, String clave, String articulo, String euros, String puntos, SpannableString descripcion){
        this.id = id;
        this.clave = clave;
        this.articulo = articulo;
        this. euros = euros;
        this.puntos = puntos;
        this.descripcion = descripcion;
    }

    public Infraccion(String clave, String articulo, String euros, String puntos, SpannableString descripcion){
        this.clave = clave;
        this.articulo = articulo;
        this. euros = euros;
        this.puntos = puntos;
        this.descripcion = descripcion;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getEuros() {
        return euros;
    }

    public void setEuros(String euros) {
        this.euros = euros;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public SpannableString getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(SpannableString descripcion) {
        this.descripcion = descripcion;
    }

    public String getNorma() {
        return norma;
    }

    public void setNorma(String norma) {
        this.norma = norma;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
