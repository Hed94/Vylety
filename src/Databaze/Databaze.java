package Databaze;

import Tridy.Klient;
import Tridy.Pruvodce;
import Tridy.Vylet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
Třída databáze se stará o komunikaci s databází.
Obsahuje metody na čtení dat v databázi a na její plnění a změny.
Insert metody předpokládají autoincrement funkce v databázi --> dělají si vlastní IDčka
*/

public class Databaze {
    private Connection con;
    private Statement st;
    private ResultSet rs;
    // konstruktor  třídy databáze, kterým se aplikace připojí k databázi
    public Databaze()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/test?useUnicode=yes&characterEncoding=UTF-8", "root", "");
            st = con.createStatement();
        }
        catch (Exception ex)
        {
            System.out.println("Chyba: " + ex);
        }
    }

    // Metoda získá z databáze všechny klienty
    public ObservableList<Klient> listKlientu()
    {
        try {
            String query = "SELECT * FROM `klient`";
            rs = st.executeQuery(query);
            ObservableList<Klient>list = FXCollections.observableArrayList();
            while(rs.next())
            {
                Klient pomocny = new Klient();
                pomocny.setIco(rs.getInt(1));
                pomocny.setNazevFirmy(rs.getString(2));
                pomocny.setEmail(rs.getString(3));
                pomocny.setTelefon(rs.getInt(4));
                list.add(pomocny);
            }
            return list;
        }
        catch(Exception ex)
        {
            System.out.println("Chyba: " + ex);
        }
        return null;
    }

    // Metoda získá název firmy z databáze podle jeho iča
    public String nazevKlienta(int ico)
    {
        try {
            String query = "SELECT * FROM `klient` where ico = \"" + ico + "\"";
            rs = st.executeQuery(query);
            String nazev = "";
            while(rs.next())
            {
                nazev += rs.getString(2);
            }
            return nazev;
        }
        catch(Exception ex)
        {
            System.out.println("Chyba: " + ex);
        }
        return null;
    }

    // Metoda získá z databáze všechny výlety
    public ObservableList<Vylet> vsechnyVylety()
    {
        try {
            String query = "SELECT * FROM `vylet`";
            rs = st.executeQuery(query);
            ObservableList<Vylet>list = FXCollections.observableArrayList();
            while(rs.next())
            {
                Vylet pomocny = new Vylet();
                pomocny.setID(rs.getInt(1));
                pomocny.setNazevVyletu(rs.getString(2));
                pomocny.setPopisVyletu(rs.getString(3));
                pomocny.setZacatek(rs.getDate(4).toLocalDate());
                pomocny.setKonec(rs.getDate(5).toLocalDate());
                pomocny.setPruvodce(rs.getInt(6));
                pomocny.setKlient(rs.getInt(7));
                list.add(pomocny);
            }
            return list;
        }
        catch(Exception ex)
        {
            System.out.println("Chyba: " + ex);
        }
        return null;
    }

    // Metoda získá z databáze všechny průvodce
    public ObservableList<Pruvodce> vsechnyPruvodce()
    {
        try {
            String query = "SELECT * FROM `pruvodce`";
            rs = st.executeQuery(query);
            ObservableList<Pruvodce>list = FXCollections.observableArrayList();
            while(rs.next())
            {
                Pruvodce pomocny = new Pruvodce();
                pomocny.setOsobniCislo(rs.getInt(1));
                pomocny.setJmeno(rs.getString(2));
                pomocny.setPrijmeni(rs.getString(3));
                pomocny.setTelefon(rs.getInt(4));
                list.add(pomocny);
            }
            return list;
        }
        catch(Exception ex)
        {
            System.out.println("Chyba: " + ex);
        }
        return null;
    }

    // Metoda získá jméno průvodce podle jeho osobního čísla
    public String jmenoPruvodce(int cislo)
    {
        try {
            String query = "SELECT * FROM `pruvodce` where osobniCIslo = \"" + cislo + "\"";
            rs = st.executeQuery(query);
            String jmeno = "";
            while(rs.next())
            {
                jmeno += (rs.getString(2) + " " + rs.getString(3));
            }
            return jmeno;
        }
        catch(Exception ex)
        {
            System.out.println("Chyba: " + ex);
        }
        return null;
    }

    // Metoda pro přidání zákazníka
    public void PridejKlienta(Klient klient)
    {
        try
        {
            String query = "Insert into `klient` SET `ico`= \"" + klient.getIco()
                    + "\",`nazevFirmy`= \"" + klient.getNazevFirmy()
                    + "\",`email`= \"" + klient.getEmail()
                    + "\",`telefon`= \"" + klient.getTelefon() + "\"";
            st.executeUpdate(query);
            Uspech("Nový klient byl úspěšně přidán");
        }
        catch (Exception ex)
        {
            Chyba(ex.toString());
        }
    }

    // Metoda pro přidání výletu
    public void PridejVylet(Vylet vylet)
    {
        try
        {
            String query = "Insert into `vylet` SET `nazevVyletu`= \"" + vylet.getNazevVyletu()
                    + "\",`popisVyletu`= \"" + vylet.getPopisVyletu()
                    + "\",`zacatek`= \"" + vylet.getZacatek()
                    + "\",`konec`= \"" + vylet.getKonec()
                    + "\",`pruvodce`= \"" + vylet.getPruvodce()
                    + "\",`klient`= \"" + vylet.getKlient()
                    + "\",`jmenoPruvodce`= \"" + vylet.getJmenoPruvodce()
                    + "\",`nazevFirmy`= \"" + vylet.getNazevFirmy() + "\"";
            st.executeUpdate(query);
            Uspech("Nový výlet byl úspěšně přidán");
        }
        catch (Exception ex)
        {
            Chyba(ex.toString());
        }
    }

    // Metoda pro přidání nového průvodce
    public void PridejPruvodce(Pruvodce pruvodce)
    {
        try
        {
            String query = "Insert into `pruvodce` SET `osobniCislo`= \"" + pruvodce.getOsobniCislo()
                    + "\",`jmeno`= \"" + pruvodce.getJmeno()
                    + "\",`prijmeni`= \"" + pruvodce.getPrijmeni()
                    + "\",`telefon`= \"" + pruvodce.getTelefon() + "\"";
            st.executeUpdate(query);
            Uspech("Nový průvodce byl úspěšně přidán");
        }
        catch (Exception ex)
        {
            Chyba(ex.toString());
        }
    }

    // Metoda pro úpravu klienta
    public void UpravKlienta(Klient klient)
    {
        try
        {
            String query = "Update `klient` SET `nazevFirmy`= \"" + klient.getNazevFirmy()
                    + "\",`email`= \"" + klient.getEmail()
                    + "\",`telefon`= \"" + klient.getTelefon()
                    + "\" where ico = \"" + klient.getIco() + "\"";
            st.executeUpdate(query);
            Uspech("Klient byl změněn");
        }
        catch (Exception ex)
        {
            Chyba(ex.toString());
        }
    }

    // Metoda pro úpravu výletu
    public void UpravVylet(Vylet vylet)
    {
        try
        {
            String query = "Update `vylet` SET `nazevVyletu`= \"" + vylet.getNazevVyletu()
                    + "\",`popisVyletu`= \"" + vylet.getPopisVyletu()
                    + "\",`zacatek`= \"" + vylet.getZacatek()
                    + "\",`konec`= \"" + vylet.getKonec()
                    + "\",`pruvodce`= \"" + vylet.getPruvodce()
                    + "\",`klient`= \"" + vylet.getKlient()
                    + "\",`jmenoPruvodce`= \"" + vylet.getJmenoPruvodce()
                    + "\",`nazevFirmy`= \"" + vylet.getNazevFirmy()
                    + "\" where ID = \"" + vylet.getID() + "\"";
            st.executeUpdate(query);
            Uspech("Výlet byl úspěšně změněn");
        }
        catch (Exception ex)
        {
            Chyba(ex.toString());
        }
    }

    // Metoda pro úpravu průvodce
    public void UpravPruvodce(Pruvodce pruvodce)
    {
        try
        {
            String query = "Update `pruvodce` SET `jmeno`= \"" + pruvodce.getJmeno()
                    + "\",`prijmeni`= \"" + pruvodce.getPrijmeni()
                    + "\",`telefon`= \"" + pruvodce.getTelefon()
                    + "\" where osobniCislo = \"" + pruvodce.getOsobniCislo() + "\"";
            st.executeUpdate(query);
            Uspech("Pruvodce byl úspěšně změněn");
        }
        catch (Exception ex)
        {
            Chyba(ex.toString());
        }
    }

    // Metoda pro smazání klienta
    public void SmazKlienta(Klient klient)
    {
        try
        {
            String query = "Delete from `klient` where ico = \"" + klient.getIco() + "\"";
            st.executeUpdate(query);
            Uspech("Klient byl úspěšně smazán");
        }
        catch (Exception ex)
        {
            Chyba(ex.toString());
        }
    }

    // Metoda pro smazání výletu
    public void SmazVylet(Vylet vylet)
    {
        try
        {
            String query = "Delete from `vylet` where ID = \"" + vylet.getID() + "\"";
            st.executeUpdate(query);
            Uspech("Klient byl úspěšně smazán");
        }
        catch (Exception ex)
        {
            Chyba(ex.toString());
        }
    }

    // Metoda pro smazání pruvodce
    public void SmazPruvodce(Pruvodce pruvodce)
    {
        try
        {
            String query = "Delete from `pruvodce` where osobniCislo = \"" + pruvodce.getOsobniCislo() + "\"";
            st.executeUpdate(query);
            Uspech("Klient byl úspěšně smazán");
        }
        catch (Exception ex)
        {
            Chyba(ex.toString());
        }
    }

    // Metoda pro výpis errorové hlášky
    public void Chyba(String error)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Chyba!");
        alert.setHeaderText("Jedno či více políček je špatně");
        alert.setContentText(error);
        alert.showAndWait();
    }

    // Metoda pro výpis úspěchové hlášky
    public void Uspech(String uspech)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Úspěch");
        alert.setHeaderText("Akce proběhla úspěšně");
        alert.setContentText(uspech);
        alert.showAndWait();
    }
}
