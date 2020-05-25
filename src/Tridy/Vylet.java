package Tridy;

import java.time.LocalDate;
import java.util.Date;

public class Vylet {
    private int ID;
    private String nazevVyletu;
    private String popisVyletu;
    private LocalDate zacatek;
    private LocalDate konec;
    private int pruvodce;
    private int klient;
    private String jmenoPruvodce;
    private String nazevFirmy;

    public Vylet() {
    }

    public Vylet(String nazevVyletu, String popisVyletu, LocalDate zacatek, LocalDate konec, int pruvodce, int klient, String jmenoPruvodce, String nazevFirmy) {
        this.nazevVyletu = nazevVyletu;
        this.popisVyletu = popisVyletu;
        this.zacatek = zacatek;
        this.konec = konec;
        this.pruvodce = pruvodce;
        this.klient = klient;
        this.jmenoPruvodce = jmenoPruvodce;
        this.nazevFirmy = nazevFirmy;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNazevVyletu() {
        return nazevVyletu;
    }

    public void setNazevVyletu(String nazevVyletu) {
        this.nazevVyletu = nazevVyletu;
    }

    public String getPopisVyletu() {
        return popisVyletu;
    }

    public void setPopisVyletu(String popisVyletu) {
        this.popisVyletu = popisVyletu;
    }

    public LocalDate getZacatek() {
        return zacatek;
    }

    public void setZacatek(LocalDate zacatek) {
        this.zacatek = zacatek;
    }

    public LocalDate getKonec() {
        return konec;
    }

    public void setKonec(LocalDate konec) {
        this.konec = konec;
    }

    public int getPruvodce() {
        return pruvodce;
    }

    public void setPruvodce(int pruvodce) {
        this.pruvodce = pruvodce;
    }

    public int getKlient() {
        return klient;
    }

    public void setKlient(int klient) {
        this.klient = klient;
    }

    public String getJmenoPruvodce() {
        return jmenoPruvodce;
    }

    public void setJmenoPruvodce(String jmenoPruvodce) {
        this.jmenoPruvodce = jmenoPruvodce;
    }

    public String getNazevFirmy() {
        return nazevFirmy;
    }

    public void setNazevFirmy(String nazevFirmy) {
        this.nazevFirmy = nazevFirmy;
    }
}
