package klubapp;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class Klub implements Serializable, Comparable<Klub> {

    String name;
    Date dataZal;
    Adres adres;
    Osoba prezes;
    Osoba trener;
    Integer pojemnosc;
    String telefon;

    public Klub(String n, Adres a, Date d, Osoba p, Osoba t, Integer poj, String tel) {
        super();
        this.name = n;
        this.adres = a;
        this.dataZal = d;
        this.prezes = p;
        this.trener = t;
        pojemnosc = poj;
        telefon = tel;
    }

    public String getName() {
        return name;
    }

    public Date getDataZal() {
        return dataZal;
    }

    public Adres getAdres() {
        return adres;
    }

    public Osoba getPrezes() {
        return prezes;
    }

    public Osoba getTrener() {
        return trener;
    }

    public Integer getPojemnosc() {
        return pojemnosc;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDataZal(Date dataZał) {
        this.dataZal = dataZał;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public void setPrezes(Osoba prezes) {
        this.prezes = prezes;
    }

    public void setTrener(Osoba trener) {
        this.trener = trener;
    }

    public void setPojemnosc(Integer pojemność) {
        this.pojemnosc = pojemność;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public int compareTo(Klub o) {
        int r = name.compareTo(o.name);
        if (r == 0) {
            r = adres.compareTo(o.adres);
        }
        return r;
    }

    public String toString() {
        return this.name + " " + this.adres.toString();
    }

    static List generate(int i) throws ParseException {
        List<Klub> elist = new Vector<Klub>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        elist.add(new Klub("Zawisza", new Adres("Bydgoszcz", "Gdańska", 243), dateFormat.parse("20050102"), 
                new Osoba("Felicjan", "Wielki"), new Osoba("Tarasiewicz", "Ryszard"), 45000, "52333333"));
        elist.add(new Klub("Widzew", new Adres("Bydgoszcz", "Gdańska", 243), dateFormat.parse("20050102"), 
                new Osoba("Felicjan", "Wielki"), new Osoba("Tarasiewicz", "Ryszard"), 45000, "52333333"));
        return elist;
    }
}
