package Logika;

import Tridy.Klient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class KlientController implements Initializable
{
    // Deklarace grafiky a pomocných proměných
    @FXML
    private TextField ico,nazev,email,telefon;
    @FXML
    private Button tlacitko;
    @FXML
    private MainController hlavni;

    private boolean editace; // říká zda editujem ( true ) nebo tvoříme nového ( false )

    // Metoda pro tlačítko, která buďto vytvoří nebo upraví klienta
    @FXML
    public void potvrdit()
    {
        if(SpolecneMetody.jeToCislo(telefon.getText()) && SpolecneMetody.jeToCislo(ico.getText()))
        {
            if(SpolecneMetody.jeToEmail(email.getText()))
            {
                Klient novy = new Klient();
                novy.setTelefon(Integer.parseInt(telefon.getText()));
                novy.setEmail(email.getText());
                novy.setNazevFirmy(nazev.getText());
                novy.setIco(Integer.parseInt(ico.getText()));

                if(editace)
                {
                    hlavni.getDatabaze().UpravKlienta(novy);
                    hlavni.zobrazKlienty();
                    Stage stage = (Stage) tlacitko.getScene().getWindow();
                    stage.close();
                }
                else
                {
                    if(!ico.getText().equals(""))
                    {
                        hlavni.getDatabaze().PridejKlienta(novy);
                        hlavni.zobrazKlienty();
                        Stage stage = (Stage) tlacitko.getScene().getWindow();
                        stage.close();
                    }
                }
            }
            else
            {
                hlavni.getDatabaze().Chyba("Email není ve správném formátu.");
            }
        }
        else
        {
            hlavni.getDatabaze().Chyba("IČO, nebo telefon není ve formátu čísla.");
        }
    }
    // Metoda která nastaví propojení na hlavní controller
    public void setMainOkno(MainController hlavni)
    {
        this.hlavni = hlavni;
    }
    // Metoda pro vyplnění klienta pokuď ho upravujem - volána z hlavního kontrolleru
    public void vyplnKlienta(Klient klient)
    {
        editace = true;
        ico.setEditable(false);
        ico.setText(klient.getIco()+"");
        nazev.setText(klient.getNazevFirmy());
        email.setText(klient.getEmail());
        telefon.setText(klient.getTelefon()+"");
    }
    // Inicializační metoda která nastaví defaulně editaci na false
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        editace = false;
    }
}
