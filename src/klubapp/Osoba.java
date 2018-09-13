package klubapp;

import java.io.Serializable;

public class Osoba implements Serializable, Comparable<Osoba> {

    private String imię;
    private String nazwisko;

    public Osoba(String nazwisko, String imię) {
        this.nazwisko = nazwisko;
        this.imię = imię;
    }

    public String getImię() {
        return imię;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setImię(String imię) {
        this.imię = imię;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    
    public void setOsoba(Osoba trener){
        this.setNazwisko(trener.nazwisko);
        this.setImię(trener.imię);
    }

    @Override
    public int compareTo(Osoba o) {
        int r = nazwisko.compareTo(o.nazwisko);
        if (r == 0) {
            r = imię.compareTo(o.imię);
        }
        return r;
    }
    
    @Override
    public String toString(){
        return this.nazwisko + ";" + this.imię;
    }
}
