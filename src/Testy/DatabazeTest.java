import Databaze.Databaze;
import Tridy.Klient;
import Tridy.Pruvodce;
import Tridy.Vylet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DatabazeTest
{
    private Databaze databaze;
    private Klient klient;
    private Pruvodce pruvodce;
    private Vylet vylet;

    /***************************************************************************
     * Metoda se provede před spuštěním každé testovací metody. Používá se
     * k vytvoření tzv. přípravku (fixture), což jsou datové atributy (objekty),
     * s nimiž budou testovací metody pracovat.
     */

    @Before
    public void setUp()
    {
        databaze = new Databaze();
    }

    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každé testovací metody.
     * Zkouší smazat klienta kdyby se náhodou test operace s klientem zasekl a nesmazal ho sám
     */

    @After
    public void tearDown() {
        klient = new Klient();
        klient.setIco(123456789);
        databaze.SmazKlienta(klient);
    }

    /**
     * Testování operací s klientem - přidání, úprava, smazání
     *
     * Nejprve se vytvoří seznam a nahrajou se do něj všichni klienti.
     * Pak se vytvoří nový klient a kontroluje se že nový klient v seznamu zatím není ( není v databázi )
     * Poté se klient přidá do databáze a znova se načte seznam a kontroluje se, že klient s tímto ičo už v seznamu je
     * Následně se klientovi změní název a zavolá se metoda upravit a opět následuje kontrola že úprava proběhla správně
     * V poslední části se kontroluje stejným principem smazání
     *
     * Pro správnou funkci této metody respektive celé třídy je extrémně důležité !!!
     * Aby ve třídě databáze byly zakomentovány všechny Úspěch metody a případně i Chyba metody u metod které používáme
     * Tzn u přijdej, uprav, smaž klienta.
     * Problém je ve vláknech. Tyto metody vytvoří vyskakovací alert okno které se stane hlavním oknem a čeká na odklik
     * Jenže v testu to okno nijak nepotvrzujem - nejde ho odkliknout - a tak test spadne.
     *
     */
    @Test
    public  void operaceSKlientem()
    {
        ObservableList<Klient> list = FXCollections.observableArrayList();
        list = databaze.listKlientu();

        klient = new Klient();
        klient.setIco(123456789);
        klient.setNazevFirmy("Testovaci Firma s.r.o.");
        klient.setEmail("testovaciEmail@test.cz");
        klient.setTelefon(757848968);
        assertNull(najdiKlienta(klient.getIco(),list));

        databaze.PridejKlienta(klient);
        list = databaze.listKlientu();
        assertNotNull(najdiKlienta(klient.getIco(),list));

        klient.setNazevFirmy("Testovaci Firma jina s.r.o.");
        databaze.UpravKlienta(klient);
        list = databaze.listKlientu();
        assertFalse(najdiKlienta(klient.getIco(),list).getNazevFirmy().equals("Testovaci Firma s.r.o."));

        databaze.SmazKlienta(klient);
        list = databaze.listKlientu();
        assertNull(najdiKlienta(klient.getIco(),list));
    }

    // Pomocná metoda která slouží k hledání klienta v seznamu.
    public Klient najdiKlienta(int ico,ObservableList<Klient> list)
    {
        for(Klient hledany : list)
        {
            if(hledany.getIco() == klient.getIco())
            {
                return hledany;
            }
        }
        return null;
    }
}
