package klubapp;

import java.io.Serializable;

public class Adres implements Serializable, Comparable<Adres> {

    private String miasto;
    private String ulica;
    private int nr;
    
    public Adres(String town, String street, int nr) {
        this.miasto = town;
        this.ulica = street;
        this.nr = nr;
    }
    
    public void setAddress(Adres address) {
        this.setTown(address.miasto);
        this.setStreet(address.ulica);
        this.setNr(address.nr);
    }
    
    public String getTown() {
        return miasto;
    }
    
    public String getStreet() {
        return ulica;
    }
    
    public int getNr() {
        return nr;
    }
    
    public void setTown(String town) {
        this.miasto = town;
    }
    
    public void setStreet(String street) {
        this.ulica = street;
    }
    
    public void setNr(int nr) {
        this.nr = nr;
    }
    
    public String toString() {
        return miasto + ";" + ulica + ";" + nr;
    }
    
    @Override
    public int compareTo(Adres o) {
        int r = miasto.compareTo(o.miasto);
        if (r == 0) {
            r = ulica.compareTo(o.ulica);
        }
        if (r == 0) {
            r = nr - o.nr;
        }
        return r;
    }
}
