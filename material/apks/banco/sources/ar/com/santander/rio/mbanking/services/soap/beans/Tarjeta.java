package ar.com.santander.rio.mbanking.services.soap.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tarjeta implements Parcelable {
    public static final Creator<Tarjeta> CREATOR = new Creator<Tarjeta>() {
        public Tarjeta createFromParcel(Parcel parcel) {
            return new Tarjeta(parcel);
        }

        public Tarjeta[] newArray(int i) {
            return new Tarjeta[i];
        }
    };
    @SerializedName("condicion")
    @Expose
    private String condicion;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("marcaWomen")
    @Expose
    private String marcaWomen;
    private Boolean selected = Boolean.valueOf(false);
    @SerializedName("suscripcionHabilitada")
    @Expose
    private String suscripcionHabilitada;
    @SerializedName("tipoTarjeta")
    @Expose
    private String tipoTarjeta;

    public int describeContents() {
        return 0;
    }

    protected Tarjeta(Parcel parcel) {
        this.descripcion = (String) parcel.readValue(String.class.getClassLoader());
        this.condicion = (String) parcel.readValue(String.class.getClassLoader());
        this.tipoTarjeta = (String) parcel.readValue(String.class.getClassLoader());
        this.marcaWomen = (String) parcel.readValue(String.class.getClassLoader());
        this.suscripcionHabilitada = (String) parcel.readValue(String.class.getClassLoader());
        this.selected = (Boolean) parcel.readValue(Boolean.class.getClassLoader());
    }

    public Tarjeta() {
    }

    public Tarjeta(String str, String str2, String str3, String str4, String str5) {
        this.descripcion = str;
        this.condicion = str2;
        this.tipoTarjeta = str3;
        this.marcaWomen = str4;
        this.suscripcionHabilitada = str5;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getCondicion() {
        return this.condicion;
    }

    public void setCondicion(String str) {
        this.condicion = str;
    }

    public String getTipoTarjeta() {
        return this.tipoTarjeta;
    }

    public void setTipoTarjeta(String str) {
        this.tipoTarjeta = str;
    }

    public String getMarcaWomen() {
        return this.marcaWomen;
    }

    public void setMarcaWomen(String str) {
        this.marcaWomen = str;
    }

    public String getSuscripcionHabilitada() {
        return this.suscripcionHabilitada;
    }

    public void setSuscripcionHabilitada(String str) {
        this.suscripcionHabilitada = str;
    }

    public void setSelected(Boolean bool) {
        this.selected = bool;
    }

    public Boolean isSelected() {
        return this.selected;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.descripcion);
        parcel.writeValue(this.condicion);
        parcel.writeValue(this.tipoTarjeta);
        parcel.writeValue(this.marcaWomen);
        parcel.writeValue(this.suscripcionHabilitada);
        parcel.writeValue(this.selected);
    }
}
