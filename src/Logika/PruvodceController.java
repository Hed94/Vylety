package Logika;

import Tridy.Pruvodce;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PruvodceController implements Initializable {

    // Deklarace grafiky a pomocných proměných
    @FXML
    private TextField jmeno,prijmeni,telefon,cislo;
    @FXML
    private Button tlacitko;
    @FXML
    private MainController hlavni;

    private boolean editace; // říká zda editujem ( true ) nebo tvoříme nového ( false )

    // Metoda pro tlačítko, která buďto vytvoří nebo upraví průvodce
    // Po skončení okno zavře
    @FXML
    public void potvrdit()
    {
        if(SpolecneMetody.jeToCislo(telefon.getText()) && SpolecneMetody.jeToCislo(cislo.getText()))
        {
            Pruvodce novy = new Pruvodce();
            novy.setTelefon(Integer.parseInt(telefon.getText()));
            novy.setJmeno(jmeno.getText());
            novy.setPrijmeni(prijmeni.getText());
            novy.setOsobniCislo(Integer.parseInt(cislo.getText()));

            if(editace)
            {
                hlavni.getDatabaze().UpravPruvodce(novy);
                Stage stage = (Stage) tlacitko.getScene().getWindow();
                hlavni.zobrazPruvodce();
                stage.close();
            }
            else
            {
                if(!cislo.getText().equals(""))
                {
                    hlavni.getDatabaze().PridejPruvodce(novy);
                    Stage stage = (Stage) tlacitko.getScene().getWindow();
                    hlavni.zobrazPruvodce();
                    stage.close();
                }
            }
        }
        else
        {
            hlavni.getDatabaze().Chyba("Telefon nebo osobní číslo průvodce není ve formátu čísla.");
        }

    }
    // Metoda která nastaví propojení na hlavní controller
    public void setMainOkno(MainController hlavni)
    {
        this.hlavni = hlavni;
    }
    // Metoda pro vyplnění průvodce pokuď ho upravujem - volána z hlavního kontrolleru
    public void vyplnPruvodce(Pruvodce pruvodce)
    {
        editace = true;
        cislo.setEditable(false);
        cislo.setText(pruvodce.getOsobniCislo()+"");
        jmeno.setText(pruvodce.getJmeno());
        prijmeni.setText(pruvodce.getPrijmeni());
        telefon.setText(pruvodce.getTelefon()+"");
    }
    // Inicializační metoda která nastaví defaulně editaci na false
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        editace = false;
    }
}
