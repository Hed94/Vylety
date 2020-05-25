package Logika;

import Tridy.Klient;
import Tridy.Pruvodce;
import Tridy.Vylet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class VyletController implements Initializable {

    // Deklarace grafiky a pomocných proměných
    @FXML
    private TextField nazevVyletu;
    @FXML
    private DatePicker zacatek,konec;
    @FXML
    private TextArea popis;
    @FXML
    private Button tlacitko;
    @FXML
    private MainController hlavni;
    @FXML
    private TableView<Klient> klient;
    @FXML
    private TableColumn<Klient,String> nazevFirmy;
    @FXML
    private TableColumn<Klient,Integer> IDK;
    @FXML
    private TableView<Pruvodce> pruvodce;
    @FXML
    private TableColumn<Pruvodce,String> jmeno,prijmeni;
    @FXML
    private TableColumn<Pruvodce,Integer> IDP;

    private boolean editace; // říká zda editujem ( true ) nebo tvoříme nového ( false )
    private int upravovaneID; // ukládá ID výletu který upravujeme
    // Seznamy pro jednotlivé tabulky
    private ObservableList<Pruvodce> listPruvodcu = FXCollections.observableArrayList();
    private ObservableList<Klient> listKlientu = FXCollections.observableArrayList();

    // Metoda pro tlačítko, která buďto vytvoří nebo upraví výlet
    // Po skončení okno zavře
    @FXML
    public void potvrdit()
    {
        if(konec.getValue() != null && zacatek.getValue() != null)
        {
            if(konec.getValue().isAfter(zacatek.getValue()) || konec.getValue().equals(zacatek.getValue()))
            {
                Pruvodce zvolenyPruvodce = pruvodce.getSelectionModel().getSelectedItem();
                Klient zvolenyKlient = klient.getSelectionModel().getSelectedItem();
                if(zvolenyPruvodce != null && zvolenyKlient != null)
                {
                    Vylet novy = new Vylet();
                    novy.setNazevVyletu(nazevVyletu.getText());
                    novy.setZacatek(zacatek.getValue());
                    novy.setKonec(konec.getValue());
                    novy.setKlient(zvolenyKlient.getIco());
                    novy.setPruvodce(zvolenyPruvodce.getOsobniCislo());
                    novy.setPopisVyletu(popis.getText());
                    novy.setJmenoPruvodce(zvolenyPruvodce.getJmeno() + " " + zvolenyPruvodce.getPrijmeni());
                    novy.setNazevFirmy(zvolenyKlient.getNazevFirmy());

                    if (editace)
                    {
                        novy.setID(upravovaneID);
                        hlavni.getDatabaze().UpravVylet(novy);
                        hlavni.zobrazVylety();
                        Stage stage = (Stage) tlacitko.getScene().getWindow();
                        stage.close();
                    }
                    else
                    {
                        hlavni.getDatabaze().PridejVylet(novy);
                        hlavni.zobrazVylety();
                        Stage stage = (Stage) tlacitko.getScene().getWindow();
                        stage.close();
                    }
                }
                else
                {
                    hlavni.getDatabaze().Chyba("Je nutno vybrat klienta a průvodce z tabulky");
                }
            }
            else
            {
                hlavni.getDatabaze().Chyba("Datum konce nemůže být menší jak začátku");
            }
        }
        else
        {
            hlavni.getDatabaze().Chyba("Datum konce nebo začátku není vyplněn");
        }
    }
    // Metoda která nastaví propojení na hlavní controller
    public void setMainOkno(MainController hlavni)
    {
        this.hlavni = hlavni;
        listPruvodcu = this.hlavni.getDatabaze().vsechnyPruvodce();
        listKlientu = this.hlavni.getDatabaze().listKlientu();
        pruvodce.setItems(listPruvodcu);
        klient.setItems(listKlientu);
    }
    // Metoda pro vyplnění výletu pokuď ho upravujem - volána z hlavního kontrolleru
    public void vyplnVylet(Vylet vylet)
    {
        editace = true;
        upravovaneID = vylet.getID();
        nazevVyletu.setText(vylet.getNazevVyletu());
        zacatek.setValue(vylet.getZacatek());
        konec.setValue(vylet.getKonec());
        popis.setText(vylet.getPopisVyletu());

        for(Klient hledany : listKlientu)
        {
            if(hledany.getIco() == vylet.getKlient())
            {
                klient.getSelectionModel().select(hledany);
                break;
            }
        }

        for(Pruvodce hledany : listPruvodcu)
        {
            if(hledany.getOsobniCislo() == vylet.getPruvodce())
            {
                pruvodce.getSelectionModel().select(hledany);
                break;
            }
        }

    }
    // Inicializační metoda která nastaví defaulně editaci na false
    // A mapují se zde jednotlivé sloupce tabulky ke třídám ke kterým patří.
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        editace = false;

        nazevFirmy.setCellValueFactory(new PropertyValueFactory<>("nazevFirmy"));
        IDK.setCellValueFactory(new PropertyValueFactory<>("ico"));

        jmeno.setCellValueFactory(new PropertyValueFactory<>("jmeno"));
        prijmeni.setCellValueFactory(new PropertyValueFactory<>("prijmeni"));
        IDP.setCellValueFactory(new PropertyValueFactory<>("osobniCislo"));
    }
}
