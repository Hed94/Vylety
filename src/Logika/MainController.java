package Logika;

import Databaze.Databaze;
import Tridy.Klient;
import Tridy.Pruvodce;
import Tridy.Vylet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController implements Initializable
{
    // Pomocné proměné
    private Databaze databaze;
    private Klient editovanyKlient;
    private Pruvodce editovanyPruvodce;
    private Vylet editovanyVylet;
    // seznamy pro jednotlivé tabulky
    private ObservableList<Klient> listKlientu = FXCollections.observableArrayList();
    private ObservableList<Pruvodce> listPruvodcu = FXCollections.observableArrayList();
    private ObservableList<Vylet> listVyletu = FXCollections.observableArrayList();
    // Grafické prvky
    @FXML
    private AnchorPane panelMenu,panelKlientu,panelPruvodcu,panelVyletu;
    @FXML
    private TextArea vyletPopis;
    @FXML
    private TableView<Klient> tableKlienti;
    @FXML
    private TableColumn<Klient,String> kNazev,kEmail;
    @FXML
    private TableColumn<Klient,Integer> kIco,kTelefon;
    @FXML
    private TableView<Pruvodce> tablePruvodcu;
    @FXML
    private TableColumn<Pruvodce,String> pJmeno,pPrijmeni;
    @FXML
    private TableColumn<Pruvodce,Integer> pID,pTelefon;
    @FXML
    private TableView<Vylet> tableVyletu;
    @FXML
    private TableColumn<Vylet,String> vNazev;
    @FXML
    private TableColumn<Vylet,Integer> vID,vIDK,vIDP;
    @FXML
    private TableColumn<Vylet, Date> vZacatek,vKonec;
    // Inicializační metoda která vytvoří databázi
    // A dále namapuje všechny tabulky k atributům tříd ke kterým patří
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        databaze = new Databaze();

        kNazev.setCellValueFactory(new PropertyValueFactory<>("nazevFirmy"));
        kIco.setCellValueFactory(new PropertyValueFactory<>("ico"));
        kEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        kTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));

        pJmeno.setCellValueFactory(new PropertyValueFactory<>("jmeno"));
        pID.setCellValueFactory(new PropertyValueFactory<>("osobniCislo"));
        pPrijmeni.setCellValueFactory(new PropertyValueFactory<>("prijmeni"));
        pTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));

        vNazev.setCellValueFactory(new PropertyValueFactory<>("nazevVyletu"));
        vID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        vIDK.setCellValueFactory(new PropertyValueFactory<>("klient"));
        vIDP.setCellValueFactory(new PropertyValueFactory<>("pruvodce"));
        vZacatek.setCellValueFactory(new PropertyValueFactory<>("zacatek"));
        vKonec.setCellValueFactory(new PropertyValueFactory<>("konec"));
    }

    // Metoda která plošně řeší otevření nového okna
    // V podstatě vytvoří nové okno podle toho které ji pošlem argumenty
    // Musíme ji zadat cestu ke grafice kterou chceme otevřít
    // Dále titulek stránky, podle kterého si vybere správnou if větev kontrolleru
    // a ji musíme dát také informaci o tom jestli vytváříme klienta ( false ) nebo upravujem ( true )
    public void otevriOkno(String url,String title,boolean editace) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        AnchorPane newWindow = (AnchorPane) loader.load();

        if(title.equals("Formulář klientů")) {
            KlientController controller = loader.getController();
            controller.setMainOkno(this);
            if(editace)
            {
                controller.vyplnKlienta(editovanyKlient);
            }
        }

        if(title.equals("Formulář výletů")) {
            VyletController controller = loader.getController();
            controller.setMainOkno(this);
            if(editace)
            {
                controller.vyplnVylet(editovanyVylet);
            }
        }

        if(title.equals("Formulář průvodců")) {
            PruvodceController controller = loader.getController();
            controller.setMainOkno(this);
            if(editace)
            {
                controller.vyplnPruvodce(editovanyPruvodce);
            }
        }

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(newWindow);
        stage.setScene(scene);
        stage.show();
    }

    // Metoda pro přechod zpět do menu
    public void menu()
    {
        panelKlientu.setVisible(false);
        panelPruvodcu.setVisible(false);
        panelVyletu.setVisible(false);
        panelMenu.setVisible(true);
    }
    // Metoda pro zobrazení klientů - slouží také pro refresh
    public void zobrazKlienty()
    {
        listKlientu = databaze.listKlientu();
        tableKlienti.setItems(listKlientu);
        panelMenu.setVisible(false);
        panelKlientu.setVisible(true);
    }
    // Metoda pro zobrazení klientů - slouží také pro refresh
    public void zobrazVylety()
    {
        listVyletu = databaze.vsechnyVylety();
        tableVyletu.setItems(listVyletu);
        panelMenu.setVisible(false);
        panelVyletu.setVisible(true);
    }
    // Metoda pro zobrazení klientů - slouží také pro refresh
    public void zobrazPruvodce()
    {
        listPruvodcu = databaze.vsechnyPruvodce();
        tablePruvodcu.setItems(listPruvodcu);
        panelMenu.setVisible(false);
        panelPruvodcu.setVisible(true);
    }
    // Metoda pro vytvoření nového klienta, respektive otevře se okno s kontrollerem který to řeší
    public void novyKlient() throws IOException {
        otevriOkno("../Klient.fxml","Formulář klientů",false);
    }
    // Metoda pro upravení klienta, respektive otevře se okno s kontrollerem který to řeší
    // ale jen pokuď máme nějakého klienta v tabulce nakliklého
    public void upravitKlienta() throws IOException {
        Klient pomocny = tableKlienti.getSelectionModel().getSelectedItem();
        if(pomocny != null)
        {
            editovanyKlient = pomocny;
            otevriOkno("../Klient.fxml", "Formulář klientů", true);
        }
        else
        {
            databaze.Chyba("Je nutno vybrat z tabulky klienta pro úpravu.");
        }
    }
    // Metoda pro smazání klienta, pokuď máme nějakého vybraného
    public void smazKlienta()
    {
        Klient mazany = tableKlienti.getSelectionModel().getSelectedItem();
        if(mazany != null)
        {
            databaze.SmazKlienta(mazany);
            zobrazKlienty();
        }
        else
        {
            databaze.Chyba("Je nutno vybrat kterého klienta chcete smazat");
        }
    }
    // Metoda pro vytvoření nového průvodce, respektive otevře se okno s kontrollerem který to řeší
    public void novyPruvodce() throws IOException {
        otevriOkno("../Pruvodce.fxml","Formulář průvodců",false);
    }
    // Metoda pro upravení průvodce, respektive otevře se okno s kontrollerem který to řeší
    // ale jen pokuď máme nějakého průvodce v tabulce nakliklého
    public void upravitPruvodce() throws IOException {
        Pruvodce pomocny = tablePruvodcu.getSelectionModel().getSelectedItem();
        if(pomocny != null)
        {
            editovanyPruvodce = pomocny;
            otevriOkno("../Pruvodce.fxml", "Formulář průvodců", true);
        }
        else
        {
            databaze.Chyba("Je nutno vybrat z tabulky průvodce pro úpravu.");
        }
    }
    // Metoda pro smazání průvodce, pokuď máme nějakého vybraného
    public void smazPruvodce()
    {
        Pruvodce mazany = tablePruvodcu.getSelectionModel().getSelectedItem();
        if(mazany != null)
        {
            databaze.SmazPruvodce(mazany);
            zobrazPruvodce();
        }
        else
        {
            databaze.Chyba("Je nutno vybrat kterého průvodce chcete smazat");
        }
    }
    // Metoda pro vytvoření nového výletu, respektive otevře se okno s kontrollerem který to řeší
    public void novyVylet() throws IOException {
        otevriOkno("../Vylet.fxml","Formulář výletů",false);
    }
    // Metoda pro upravení výletu, respektive otevře se okno s kontrollerem který to řeší
    // ale jen pokuď máme nějaký výlet v tabulce nakliklého
    public void upravitVylet() throws IOException {
        Vylet pomocny = tableVyletu.getSelectionModel().getSelectedItem();
        if(pomocny != null)
        {
            editovanyVylet = pomocny;
            otevriOkno("../Vylet.fxml", "Formulář výletů", true);
        }
        else
        {
            databaze.Chyba("Je nutno vybrat z tabulky výlet pro úpravu.");
        }
    }
    // Metoda pro smazání výletu, pokuď máme nějaký vybraný
    public void smazVylet()
    {
        Vylet mazany = tableVyletu.getSelectionModel().getSelectedItem();
        if(mazany != null)
        {
            databaze.SmazVylet(mazany);
            zobrazVylety();
        }
        else
        {
            databaze.Chyba("Je nutno vybrat který výlet chcete smazat");
        }
    }
    // Metoda vyplňuje popis výletu poté co na nějaký výlet kliknem
    public void vyplnPopisVyletu()
    {
        Vylet kliknuty = tableVyletu.getSelectionModel().getSelectedItem();

        if(kliknuty != null)
        {
            vyletPopis.setText("Klient: " + databaze.nazevKlienta(kliknuty.getKlient()) + "\nPrůvodce " + databaze.jmenoPruvodce(kliknuty.getPruvodce()) + "\n\n--------\n " +kliknuty.getPopisVyletu());
        }
    }

    // Metoda přes kterou se pomocné kontrollery dostávají k databázi
    // teoreticky by každý  z nich mohl mít vlastní instanci databáze
    // ale je to zbytečné a duplicitní řešení
    public Databaze getDatabaze() {
        return databaze;
    }
}
