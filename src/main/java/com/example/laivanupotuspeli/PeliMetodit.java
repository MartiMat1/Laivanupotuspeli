package com.example.laivanupotuspeli;
import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;

/**
 * Luokka, joka käsittelee laivanupotuspelin tapahtumia.
 */
public class PeliMetodit {
    /**
     * Kokonaislukujono, joka tallettaa pelaajan laivat 1 ja tyhjät kentät 0.
     * botin ampumat kentät talletetaan 2.
     */
    Integer[] peliAreenaJono;
    /**
     * Kokonaislukujono, joka tallettaa botin laivat 1 ja tyhjät kentät 0.
     */
    Integer[] bottiAreenaJono;
    /**
     * Boolean arvo joka antaa botille vuoron pelata.
     */
    Boolean onkoBotinVuoro;
    /**
     * Kokonaisluku, joka kertoo kuinka monta laivaa pelaaja voi asettaa areenalle.
     */
    private int asetaPelaajanLaivat;
    /**
     * boolean arvo, joka kertoo onko peli ohi.
     */
    private Boolean peliOhi;
    /**
     * Kokonaisluku, kuinka monta laivaa botti on tuhonnut.
     */
    private int botinTuhoamatlaivat;
    /**
     * kokonaisluku, kuinka monta laivaa pelaaja on tuhonnut.
     */
    private int pelaajanTuhoamatLaivat;
    /**
     * kokonaisluku, saa arvokseen peliAreenaJonon yhden arvon arvottuna.
     */
    private int laivaJonossa;
    /**
     * kokonaisluku, saa arvokseen peliAreenaJonon yhden paikan jonossa.
     */
    private int laivanPaikkaJonossa;
    /**
     * Ottaa pelatun pelin päivämäärän.
     */
    LocalDate paiva = LocalDate.now();
    /**
     * Tiedosto jota käsitellään.
     */
    File voittajatTiedosto = new File("Voittaja.txt");
    /**
     * Tyhjä merkkijono, joka saa arvokseen tiedostosta luetut arvot.
     */
    String teksti = "";


//-----------------------------GETTERIT JA SETTERIT-----------------------------


    /**
     * Get- metodi PelaajaLaivat kentälle.
     * @return palauttaa PelaajaLaivat arvon
     */
    public int getPelaajanLaivat() {
        return asetaPelaajanLaivat;
    }

    /**
     * Set metodi pelaajaLaivat kentälle.
     * @param setPelaajanLaivat asettaa arvon pelaajaLaiva kentälle
     */
    public void setPelaajanLaivat(int setPelaajanLaivat) {
        this.asetaPelaajanLaivat = setPelaajanLaivat;
    }

    /**
     * Get metodi botin tuhoamille laivoille.
     * @return palauttaa botin tuhoamien laivojen määrän.
     */
    public int getBotinTuhoamatlaivat() {
        return botinTuhoamatlaivat;
    }

    /**
     * Set metodi botin tuhoamille laivoille.
     * @param botinTuhoamatlaivat asettaa botin tuhoamien laivojen määrän.
     */
    public void setBotinTuhoamatlaivat(int botinTuhoamatlaivat) {
        this.botinTuhoamatlaivat = botinTuhoamatlaivat;
    }

    /**
     * Get-metodi pelaajan tuhoamille laivoille.
     * @return palauttaa pelaajan tuhoamat laivat
     */
    public int getPelaajanTuhoamatLaivat() {
        return pelaajanTuhoamatLaivat;
    }

    /**
     * Set-metodi pelaajan tuhoamille laivoille
     * @param pelaajanTuhoamatLaivat asettaa pelaajan tuhoamat laivat.
     */
    public void setPelaajanTuhoamatLaivat(int pelaajanTuhoamatLaivat) {
        this.pelaajanTuhoamatLaivat = pelaajanTuhoamatLaivat;
    }
    /**

    /**
     * GetPeliOhi metodi joka kertoo onko peli ohi.
     * @return Peliohi boolean (true/false)
     */
    public Boolean getPeliOhi() {
        return peliOhi;
    }

    /**
     * SetPeliOhi metodi joka asettaa lipun.
     * @param peliOhi asettaa boolean arvon.
     */
    public void setPeliOhi(Boolean peliOhi) {
        this.peliOhi = peliOhi;
    }

    /**
     * GetlaivaJonossa metodi joka palauttaa laivan arvon listassa.
     * @return palauttaa laivan arvon listassa
     */
    public int getLaivaJonossa() {
        return laivaJonossa;
    }

    /**
     * SetLaivaJonossa metodi, joka asettaa laivan arvon listaan.
     * @param laivaJonossa asettaa arvon listaan.
     */
    public void setLaivaJonossa(int laivaJonossa) {
        this.laivaJonossa = laivaJonossa;
    }

    /**
     * GetLaivanPaikkaJonossa, joka palauttaa laivan paikan listassa.
     * @return palauttaa laivan paikan listassa.
     */
    public int getLaivanPaikkaJonossa() {
        return laivanPaikkaJonossa;
    }

    /**
     * SetLaivanPaikkaJonossa, joka asettaa lavain paikan listassa.
     * @param laivanPaikkaJonossa asettaa laivan paikan listassa.
     */
    public void setLaivanPaikkaJonossa(int laivanPaikkaJonossa) {
        this.laivanPaikkaJonossa = laivanPaikkaJonossa;
    }

    //----------------------------------------------------------------------------

    /**
     * Parametriton alustaja, jolla voidaan kutsua metodeja LaivanUpotusPeli luokassa.
     */
    public PeliMetodit() {
        this.peliAreenaJono = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.bottiAreenaJono = new Integer[]{1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.asetaPelaajanLaivat = 4;
        this.peliOhi = false;
        this.botinTuhoamatlaivat = 0;
        this.onkoBotinVuoro = false;
    }

    /**
     * Tarkistetaan areenalle asetettavien laivojen määrä.
     * Jos arvo on 0, ei laivoja voida asettaa areenalle enempää.
     */
    public void tarkistaLaivojenMaara() {
        if (asetaPelaajanLaivat > 0) {
            asetaPelaajanLaivat = asetaPelaajanLaivat - 1;
            System.out.println("Laiva asetettu, laivoja jäljellä: " + asetaPelaajanLaivat);
            System.out.println("Laivat asetettu paikoille: " + Arrays.toString(peliAreenaJono));

        } else
            System.out.println("Laivat lopussa, jatka peliin");

    }

    /**
     * Metodi, joka tarkistaa onko botti tuhonnut kaikki pelaajan laivat.
     * Jos botinTuhoamatlaivat arvo on 4, vaihdetaan peliOhi boolean arvo true.
     */
    public void tarkistaBotinTilanne() {
        if (getBotinTuhoamatlaivat() == 4) {
            setPeliOhi(true);
            System.out.println(peliOhi);
            System.out.println("PELI OHI, BOTTI VOITTI");
        }
    }

    /**
     * Metodi joka tarkistaa onko pelaaja tuhonnut kaikki botin laivat.
     * jos PelaajanTuhoamatLaivat arvo on 4, asetetaan peliOhi boolean arvo true.
     */
    public void tarkistaPelaajanTilanne() {
        if (getPelaajanTuhoamatLaivat() == 4) {
            setPeliOhi(true);
            System.out.println(peliOhi);
            System.out.println("PELI OHI, PELAAJA VOITTI");
        }
    }

    /**
     * Sekoittaa botin areenan.
     * HUOM!! koodi otettu Javan perusrakenteista luentodioista.
     */
    public void sekoitaAreena() {
        for (int i = 0; i < bottiAreenaJono.length; i++) {
            int index = (int) (Math.random()
                    * bottiAreenaJono.length);
            Integer apu = bottiAreenaJono[i];
            bottiAreenaJono[i] = bottiAreenaJono[index];
            bottiAreenaJono[index] = apu;
        }
        System.out.println("Botin laivat asetettu paikoille: " + Arrays.toString(bottiAreenaJono));

    }

    /**
     * Hakee satunnaisen laivan listasta ja asettaa laivan paikan ja sen arvon
     * laivaJonossa ja laivanPaikkaJonossa kenttiin.
     * @param array Saa arvoksi jonon josta halutaan arpoa yksi arvo.
     * @return palauttaa satunnaisen laivan paikan jonossa.
     */
    public int setLaivanTiedot(Integer[] array) {
        int laiva = new Random().nextInt(array.length);
        setLaivaJonossa(array[laiva]);
        setLaivanPaikkaJonossa(laiva);
        return laiva;
    }

    /**
     * Metodi, jonka avulla botti ampuu pelaajan areenalle.
     * Metodi tarkistaa onkoBotinvuoro boolean arvo true ja onko peliOhi boolean arvo false ja onko arvottu arvo jonosso joku muu kuin 2.
     * Jos säännöt täyttyvät ja arvottu arvo jonossa on 1, vaihtaa metodi kyseisen arvon arvoksi 2 sekä lisää botin tuhoamiin laivoihin +1
     * Lopuksi se tarkistaa botin tilanteen kutsumalla metodia tarkistaBotintilanne.
     * Jos arvottu arvo on 0, vaihtaa metodi kyseisen arvon vain 2:seksi ja vuoro vaihtuu pelaajalle.
     * lopuksi botinvuoro boolean asetetaan false.
     */
    public void botinVuoroUUSI() {
        for (int laiva = setLaivanTiedot(peliAreenaJono); laiva <= 15; laiva = setLaivanTiedot(peliAreenaJono)) {
            if ((onkoBotinVuoro = true) && (!getPeliOhi()) && (getLaivaJonossa() != 2)) {
                if (getLaivaJonossa() == 1) {
                    peliAreenaJono[laiva] = 2;
                    System.out.println("botti osui" + laiva);
                    botinTuhoamatlaivat = getBotinTuhoamatlaivat() + 1;
                    System.out.println("BOTIN TUHOAMAT LAIVAT: " + getBotinTuhoamatlaivat());
                    tarkistaBotinTilanne();
                    break;
                } else if (getLaivaJonossa() == 0) {
                    System.out.println("botti ampui ohi" + laiva);
                    peliAreenaJono[laiva] = 2;
                    break;
                }
            } else if ((peliAreenaJono[laiva] == 2) && (!getPeliOhi())) {
                System.out.println("Botti ampui samaan paikkaan " + laiva);
            } else
                break;
        }onkoBotinVuoro = false;
    }

    /**
     * Metodi, joka lukee tiedot tiedostosta.
     * Metodi asettaa ensin alustajan teksti arvoksi null,
     * tämän jälkeen lukee tiedostosta arvot.
     */
    public void lueTiedostosta() {
        teksti = "";
        try {
            BufferedReader lukija = new BufferedReader(new FileReader(voittajatTiedosto));
            String rivi;
            while((rivi = lukija.readLine()) != null) {
                System.out.println(rivi);
                teksti = teksti.concat(rivi + "\n");
            }
            lukija.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ei voitu lukea tiedostoa");
        } catch (IOException e) {
            System.out.println("IO ongelma");
        }
    }

    /**
     * Metodi, jonka avulla kirjoitetaan tulokset peleistä tiedostoon.
     * Metodi kirjoittaa pelataun pelin päivämäärän sekä voittajan.
     */
    public void kirjoitaTiedostoon() {
        try (FileWriter kirjoita = new FileWriter(voittajatTiedosto, true)) {
            if (getPelaajanTuhoamatLaivat() == 4) {
                kirjoita.write("\nPäivämäärä: " + paiva + " Voittaja: PELAAJA");
            } else if (getBotinTuhoamatlaivat() == 4) {
                kirjoita.write("\nPäivämäärä: " + paiva + " Voittaja: BOTTI");
            }
            kirjoita.close();
            System.out.println("Tiedoston päivitys onnistui");
        } catch (Exception e) {
            System.out.println("Ei voitu kirjoittaa tiedostoon");
        }
}
}