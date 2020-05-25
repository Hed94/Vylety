package Tridy;

public class Klient {
    private int ico;
    private String nazevFirmy;
    private String email;
    private int telefon;

    public Klient() {

    }

    public Klient(String nazevFirmy, String email, int telefon) {
        this.nazevFirmy = nazevFirmy;
        this.email = email;
        this.telefon = telefon;
    }

    public int getIco() {
        return ico;
    }

    public void setIco(int ico) {
        this.ico = ico;
    }

    public String getNazevFirmy() {
        return nazevFirmy;
    }

    public void setNazevFirmy(String nazevFirmy) {
        this.nazevFirmy = nazevFirmy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefon() {
        return telefon;
    }

    public void setTelefon(int telefon) {
        this.telefon = telefon;
    }
}
