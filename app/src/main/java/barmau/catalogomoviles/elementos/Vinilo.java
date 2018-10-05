package barmau.catalogomoviles.elementos;

import android.os.Parcel;
import android.os.Parcelable;

public class Vinilo implements Parcelable{

    private String codigo;
    private String nombre;
    private String color;
    private double precio;

    public Vinilo(String codigo, String nombre, String color, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.color = color;
        this.precio = precio;
    }

    protected Vinilo(Parcel in) {
        codigo = in.readString();
        nombre = in.readString();
        color = in.readString();
        precio = in.readDouble();
    }

    public static final Creator<Vinilo> CREATOR = new Creator<Vinilo>() {
        @Override
        public Vinilo createFromParcel(Parcel in) {
            return new Vinilo(in);
        }

        @Override
        public Vinilo[] newArray(int size) {
            return new Vinilo[size];
        }
    };

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(codigo);
        dest.writeString(nombre);
        dest.writeString(color);
        dest.writeDouble(precio);
    }
}
