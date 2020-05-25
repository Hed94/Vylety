package Tridy;

public class Pruvodce {
    private int osobniCislo;
    private String jmeno;
    private String prijmeni;
    private int telefon;

    public Pruvodce() {
    }

    public Pruvodce(String jmeno, String prijmeni, int telefon) {
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.telefon = telefon;
    }

    public int getOsobniCislo() {
        return osobniCislo;
    }

    public void setOsobniCislo(int osobniCislo) {
        this.osobniCislo = osobniCislo;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public int getTelefon() {
        return telefon;
    }

    public void setTelefon(int telefon) {
        this.telefon = telefon;
    }
}
