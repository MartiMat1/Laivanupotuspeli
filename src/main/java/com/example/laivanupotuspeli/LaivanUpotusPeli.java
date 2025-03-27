package com.example.laivanupotuspeli;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.Arrays;


/**
 * JavaFX luokka, jolla pelataan laivanupotusta.
 *
 * @author Matias Martikainen
 * @version 1.1
 */
public class LaivanUpotusPeli extends Application {

    /**
     * Scenet eri tapahtumille pelissä.
     */
    Scene aloitusRuutu, ohjeetRuutu, tuloksetRuutu, luoAreenaRuutu, aloitaPeliRuutu, peliOhiRuutu;
    /**
     * Olio PeliMetodit luokasta.
     */
    PeliMetodit peliMetodit = new PeliMetodit();
    /**
     * Label joka muuttuu pelin voittajan mukaan peli-ohi ruudussa.
     */
    Label voittaja = new Label();
    /**
     * Label joka näyttää tulosruudussa pelatut pelit ja niiden voittajat.
     */
    Label voittajat = new Label();
    /**
     * Nappijono, johon tallennetaan eri napit, joita käytetään laivojen asettamiseen areenalle.
     */
    Button[] osaAreenaX = new Button[16];

    /**
     * Nappijono pelaajan käyttämille napeille. Pelaaja painaa näitä omilla vuorollaan. Toimii pelaajan areenana.
     */
    Button[] pOsaButtonX = new Button[16];
    /**
     * Labeljono, johon merkitään botin osumat. Toimii botin areenana.
     */
    Label[] bOsaLabelX = new Label[16];
    /**
     * Label joka näyttää jäljellä olevien laivojen määrän.
     */
    Label laivojaJajljella = new Label("Laivoja jäljellä: " + peliMetodit.getPelaajanLaivat());
    /**
     * Haluttu fontti Laivanupotuspeli tekstille.
     */
    Font fontNormal = Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR,10);


    /**
     * Peli aloitetaan
     * @param args käynnistää pelin
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Start metodi, jossa luodaan peli.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage primaryStage) throws InterruptedException {


        /*
        Luodaan aloitusRuutu, jossa on mahdollista navigoida eri valikoissa.
        */
        VBox aloitusRuutuAsettelu = new VBox();
        aloitusRuutuAsettelu.setAlignment(Pos.CENTER);
        Label laivanUpotus = new Label("Laivanupotuspeli");
        laivanUpotus.setFont(fontNormal);
        laivanUpotus.setPrefHeight(50);
        laivanUpotus.setScaleX(2);
        laivanUpotus.setScaleY(2);
        Button aloitaNappi = new Button("Aloita peli");


        aloitaNappi.setOnAction(event ->
        {
            primaryStage.setScene(luoAreenaRuutu);
            peliMetodit.setPelaajanLaivat(4);
            peliMetodit.setPeliOhi(false);
            peliMetodit.setPelaajanTuhoamatLaivat(0);
            peliMetodit.setBotinTuhoamatlaivat(0);
            for(int i = 0; i<=15;i++) {
                osaAreenaX[i].setDisable(false);
                osaAreenaX[i].setText("");
                pOsaButtonX[i].setDisable(false);
                pOsaButtonX[i].setText("");
                bOsaLabelX[i].setText("");
                peliMetodit.peliAreenaJono[i]=0;
                peliMetodit.bottiAreenaJono[i]=0;
            }
            peliMetodit.bottiAreenaJono[0]=1;
            peliMetodit.bottiAreenaJono[1]=1;
            peliMetodit.bottiAreenaJono[2]=1;
            peliMetodit.bottiAreenaJono[3]=1;
            laivojaJajljella.setText("Laivoja jäljellä: " + peliMetodit.getPelaajanLaivat());

            System.out.println("Resetoitu pelaajan areena: " + Arrays.toString(peliMetodit.peliAreenaJono));
            System.out.println("Resetoitu botin areena: " + Arrays.toString(peliMetodit.bottiAreenaJono));
            System.out.println("Resetoitu pelaajan laittamat laivat: " + peliMetodit.getPelaajanLaivat()
                    + "\nResetoitu pelaajan tuhoamat laivat: " + peliMetodit.getPelaajanTuhoamatLaivat()
                    + "\nresetoitu botin tuhoamat laivat: " + peliMetodit.getBotinTuhoamatlaivat());
        });


        Button ohjeetNappi = new Button("Ohjeet");
        ohjeetNappi.setOnAction(event -> primaryStage.setScene(ohjeetRuutu));

        Button tuloksetNappi = new Button("Tulokset");
        tuloksetNappi.setOnAction(event -> {
            primaryStage.setScene(tuloksetRuutu);
            peliMetodit.lueTiedostosta();
            voittajat.setText(peliMetodit.teksti);
        });

        aloitusRuutu = new Scene(aloitusRuutuAsettelu, 500,500);
        aloitusRuutuAsettelu.setSpacing(10);
        aloitusRuutuAsettelu.getChildren().addAll(laivanUpotus,aloitaNappi,ohjeetNappi,tuloksetNappi);


       //-----------------------------------------------------------------------


        /*
        luodaan Ohjeruutu, jossa neuvotaan kuinka peliä pelataan.
        */
        VBox ohjeetPeliinAsettelu = new VBox();
        ohjeetPeliinAsettelu.setAlignment(Pos.CENTER);
        Label laivanUpotus1 = new Label("Laivanupotuspeli");
        laivanUpotus1.setFont(fontNormal);
        laivanUpotus1.setPrefHeight(50);
        laivanUpotus1.setScaleX(2);
        laivanUpotus1.setScaleY(2);
        Label ohjeetPeliin = new Label(
                """
                        1) Ennen pelin alkua pelaaja asettaa 4kpl laivoja areenalle.\s
                        2) Pelin alkaessa pelaaja aloittaa painamalla nappia,\n     jossa kuvittelee botin laivan olevan.\s
                        3) Pelaajan osuessa botin laivaan, muuttuu nappi X,\n    jos pelaaja ampuu ohi muuttuu nappi O.
                        4) Botin areenalta näät minne asetit laivasi, jos botti osuu muuttuu laiva K,\n     jos botti ampuu ohi muuttuu se O.
                        4) Se kumpi tuhoaa toisen kaikki laivat ensin voittaa.""");
        Button takaisinNappi = new Button("Alkuun");
        takaisinNappi.setOnAction(event -> primaryStage.setScene(aloitusRuutu));
        ohjeetRuutu = new Scene(ohjeetPeliinAsettelu, 500,500);
        ohjeetPeliinAsettelu.getChildren().addAll(laivanUpotus1,ohjeetPeliin,takaisinNappi);
        ohjeetPeliinAsettelu.setSpacing(10);


        //-------------------------------------------------------------


        //Luodaan tuloksetRuutu, jossa näkyy eri pelaajien eri tulokset.
        //tulokset näkyvät muodossa PELATUN PELIN PÄIVÄMÄÄRÄ + KUKA VOITTI BOTTI/PELAAJA.

        VBox tuloksetRuutuAsettelu = new VBox();
        tuloksetRuutuAsettelu.setAlignment(Pos.CENTER);
        Label laivanUpotus2 = new Label("Laivanupotuspeli");
        laivanUpotus2.setFont(fontNormal);
        laivanUpotus2.setPrefHeight(50);
        laivanUpotus2.setScaleX(2);
        laivanUpotus2.setScaleY(2);
        Label pelinTulokset = new Label("Pelatut pelit:");
        Button takaisinNappi1 = new Button("Alkuun");
        takaisinNappi1.setOnAction(event -> primaryStage.setScene(aloitusRuutu));
        tuloksetRuutu = new Scene(tuloksetRuutuAsettelu, 500,500);
        tuloksetRuutuAsettelu.setSpacing(10);
        tuloksetRuutuAsettelu.getChildren().addAll(laivanUpotus2,pelinTulokset,voittajat,takaisinNappi1);


        //---------------------------------------------------------------


        //Luodaan luoAreenRuutu, jossa pelaaja asettaa laivat pelikentälle.

        BorderPane luoAreenaAsettelu = new BorderPane();
        GridPane areena = new GridPane();
        areena.setAlignment(Pos.CENTER);
        HBox paneeliOhjausNapeille = new HBox();
        paneeliOhjausNapeille.setAlignment(Pos.CENTER);
        paneeliOhjausNapeille.setPrefHeight(50);


        osaAreenaX[0] = new Button();
        osaAreenaX[1] = new Button();
        osaAreenaX[2] = new Button();
        osaAreenaX[3] = new Button();
        osaAreenaX[4] = new Button();
        osaAreenaX[5] = new Button();
        osaAreenaX[6] = new Button();
        osaAreenaX[7] = new Button();
        osaAreenaX[8] = new Button();
        osaAreenaX[9] = new Button();
        osaAreenaX[10] = new Button();
        osaAreenaX[11] = new Button();
        osaAreenaX[12] = new Button();
        osaAreenaX[13] = new Button();
        osaAreenaX[14] = new Button();
        osaAreenaX[15] = new Button();


        for(int i = 0; i<=15; i++) {
            int finalI = i;
            osaAreenaX[i].setPrefSize(25,25);
            osaAreenaX[i].setOnAction(event -> {
                if (peliMetodit.getPelaajanLaivat() > 0) {
                    peliMetodit.peliAreenaJono[finalI] = 1;
                    osaAreenaX[finalI].setDisable(true);
                    peliMetodit.tarkistaLaivojenMaara();
                    osaAreenaX[finalI].setText("X");
                    laivojaJajljella.setText("Laivoja jäljellä: " + peliMetodit.getPelaajanLaivat());
                }
                else {
                    System.out.println("Laivat loppuivat");
                    laivojaJajljella.setText("Laivat loppuivat");
                }
            });
        }

        areena.add(laivojaJajljella,0,0,4,1);
        areena.add(osaAreenaX[0],0,1);
        areena.add(osaAreenaX[1],1,1);
        areena.add(osaAreenaX[2],2,1);
        areena.add(osaAreenaX[3],3,1);
        areena.add(osaAreenaX[4],0,2);
        areena.add(osaAreenaX[5],1,2);
        areena.add(osaAreenaX[6],2,2);
        areena.add(osaAreenaX[7],3,2);
        areena.add(osaAreenaX[8],0,3);
        areena.add(osaAreenaX[9],1,3);
        areena.add(osaAreenaX[10],2,3);
        areena.add(osaAreenaX[11],3,3);
        areena.add(osaAreenaX[12],0,4);
        areena.add(osaAreenaX[13],1,4);
        areena.add(osaAreenaX[14],2,4);
        areena.add(osaAreenaX[15],3,4);


        Button takaisinNappi2 = new Button("Alkuun");
        takaisinNappi2.setOnAction(event -> primaryStage.setScene(aloitusRuutu));
        Button aloitaPeliNappi = new Button("Aloita Peli");

        paneeliOhjausNapeille.setSpacing(100);
        paneeliOhjausNapeille.getChildren().addAll(takaisinNappi2,aloitaPeliNappi);
        paneeliOhjausNapeille.setAlignment(Pos.TOP_CENTER);


        luoAreenaRuutu = new Scene(luoAreenaAsettelu,500,500);
        luoAreenaAsettelu.setCenter(areena);
        luoAreenaAsettelu.setBottom(paneeliOhjausNapeille);

        //------------------------------------------------------------------


        /*
         Luodaan aloitaPeliruutu, jossa peli etenee seuraavasti:
         PELAAJA ampuu ensin,
         BOTTI vastaa tähän
         Peli päättyy kun jompi kumpi on saanut tuhottua toisen laivat kokonaan.
         */
        BorderPane luoAloitaPeliAsettelu = new BorderPane();
        GridPane pelaajaAreena = new GridPane();
        GridPane LabelbottiAreena = new GridPane();
        LabelbottiAreena.setGridLinesVisible(true);
        Label pelaajanTuhoamatLaivat = new Label("Pelaajan tuhoamat laivat:\n" + peliMetodit.getPelaajanTuhoamatLaivat());
        Label botinTuhoamatLaivat = new Label("Botin tuhoamat laivat:\n" + peliMetodit.getBotinTuhoamatlaivat());
        HBox areenatPB = new HBox();
        HBox info = new HBox();
        pelaajaAreena.setAlignment(Pos.TOP_CENTER);
        LabelbottiAreena.setAlignment(Pos.TOP_CENTER);
        areenatPB.setAlignment(Pos.CENTER);
        info.setAlignment(Pos.BOTTOM_CENTER);


        //----------------------------PELAAJA AREENA------------------------------


        pOsaButtonX[0] = new Button();
        pOsaButtonX[1] = new Button();
        pOsaButtonX[2] = new Button();
        pOsaButtonX[3] = new Button();
        pOsaButtonX[4] = new Button();
        pOsaButtonX[5] = new Button();
        pOsaButtonX[6] = new Button();
        pOsaButtonX[7] = new Button();
        pOsaButtonX[8] = new Button();
        pOsaButtonX[9] = new Button();
        pOsaButtonX[10] = new Button();
        pOsaButtonX[11] = new Button();
        pOsaButtonX[12] = new Button();
        pOsaButtonX[13] = new Button();
        pOsaButtonX[14] = new Button();
        pOsaButtonX[15] = new Button();


        for(int i = 0; i<=15; i++){
            pOsaButtonX[i].setPrefSize(25,25);
            int finalI = i;
            pOsaButtonX[i].setOnAction(event -> {
                if (peliMetodit.bottiAreenaJono[finalI] == 1) {
                pOsaButtonX[finalI].setText("X");
                peliMetodit.setPelaajanTuhoamatLaivat(peliMetodit.getPelaajanTuhoamatLaivat() + 1);
                peliMetodit.tarkistaPelaajanTilanne();
            }
            else {
                pOsaButtonX[finalI].setText("O");
            }
            pOsaButtonX[finalI].setDisable(true);
            pelaajanTuhoamatLaivat.setText("Pelaajan tuhoamat laivat:\n" + peliMetodit.getPelaajanTuhoamatLaivat());
            peliMetodit.onkoBotinVuoro = true;
            peliMetodit.botinVuoroUUSI();
                switch(peliMetodit.getLaivanPaikkaJonossa()) {
                case 0:
                    if(peliMetodit.getLaivaJonossa() == 1) {
                        bOsaLabelX[0].setText("K");
                        bOsaLabelX[0].setTextFill(Color.RED);
                    } else bOsaLabelX[0].setText("O");
                    break;
                case 1:
                    if(peliMetodit.getLaivaJonossa() == 1) {
                        bOsaLabelX[1].setText("K");
                        bOsaLabelX[1].setTextFill(Color.RED);
                    } else bOsaLabelX[1].setText("O");
                    break;
                case 2:
                    if(peliMetodit.getLaivaJonossa() == 1) {
                        bOsaLabelX[2].setText("K");
                        bOsaLabelX[2].setTextFill(Color.RED);
                    } else bOsaLabelX[2].setText("O");
                    break;
                case 3:
                    if(peliMetodit.getLaivaJonossa() == 1) {
                        bOsaLabelX[3].setText("K");
                        bOsaLabelX[3].setTextFill(Color.RED);
                    } else bOsaLabelX[3].setText("O");
                    break;
                case 4:
                    if(peliMetodit.getLaivaJonossa() == 1) {
                        bOsaLabelX[4].setText("K");
                        bOsaLabelX[4].setTextFill(Color.RED);
                    } else bOsaLabelX[4].setText("O");
                    break;
                case 5:
                    if(peliMetodit.getLaivaJonossa() == 1) {
                        bOsaLabelX[5].setText("K");
                        bOsaLabelX[5].setTextFill(Color.RED);
                    } else bOsaLabelX[5].setText("O");
                    break;
                case 6:
                    if(peliMetodit.getLaivaJonossa() == 1) {
                        bOsaLabelX[6].setText("K");
                        bOsaLabelX[6].setTextFill(Color.RED);
                    } else bOsaLabelX[6].setText("O");
                    break;
                case 7:
                    if(peliMetodit.getLaivaJonossa() == 1) {
                        bOsaLabelX[7].setText("K");
                        bOsaLabelX[7].setTextFill(Color.RED);
                    } else bOsaLabelX[7].setText("O");
                    break;
                case 8:
                    if(peliMetodit.getLaivaJonossa() == 1) {
                        bOsaLabelX[8].setText("K");
                        bOsaLabelX[8].setTextFill(Color.RED);
                    } else bOsaLabelX[8].setText("O");
                    break;
                case 9:
                    if(peliMetodit.getLaivaJonossa() == 1) {
                        bOsaLabelX[9].setText("K");
                        bOsaLabelX[9].setTextFill(Color.RED);
                    } else bOsaLabelX[9].setText("O");
                    break;
                case 10:
                    if(peliMetodit.getLaivaJonossa() == 1) {
                        bOsaLabelX[10].setText("K");
                        bOsaLabelX[10].setTextFill(Color.RED);
                    } else bOsaLabelX[10].setText("O");
                    break;
                case 11:
                    if(peliMetodit.getLaivaJonossa() == 1) {
                        bOsaLabelX[11].setText("K");
                        bOsaLabelX[11].setTextFill(Color.RED);
                    } else bOsaLabelX[11].setText("O");
                    break;
                case 12:
                    if(peliMetodit.getLaivaJonossa() == 1) {
                        bOsaLabelX[12].setText("K");
                        bOsaLabelX[12].setTextFill(Color.RED);
                    } else bOsaLabelX[12].setText("O");
                    break;
                case 13:
                    if(peliMetodit.getLaivaJonossa() == 1) {
                        bOsaLabelX[13].setText("K");
                        bOsaLabelX[13].setTextFill(Color.RED);
                    } else bOsaLabelX[13].setText("O");
                    break;
                case 14:
                    if(peliMetodit.getLaivaJonossa() == 1) {
                        bOsaLabelX[14].setText("K");
                        bOsaLabelX[14].setTextFill(Color.RED);
                    } else bOsaLabelX[14].setText("O");
                    break;
                case 15:
                    if(peliMetodit.getLaivaJonossa() == 1) {
                        bOsaLabelX[15].setText("K");
                        bOsaLabelX[15].setTextFill(Color.RED);
                    } else bOsaLabelX[15].setText("O");
                    break;
            }
            botinTuhoamatLaivat.setText("Botin tuhoamat laivat:\n" + peliMetodit.getBotinTuhoamatlaivat());
                if (peliMetodit.getPeliOhi()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("Odotus ei onnistunut");
                    }
                    primaryStage.setScene(peliOhiRuutu);
                    if(peliMetodit.getBotinTuhoamatlaivat()==4) {
                        voittaja.setText("HÄVISIT PELIN\nBOTTI VOITTI");
                    }
                    else
                        voittaja.setText("VOITIT PELIN");
                }
            });
        }

        pelaajaAreena.add(pOsaButtonX[0],0,0);
        pelaajaAreena.add(pOsaButtonX[1],1,0);
        pelaajaAreena.add(pOsaButtonX[2],2,0);
        pelaajaAreena.add(pOsaButtonX[3],3,0);
        pelaajaAreena.add(pOsaButtonX[4],0,1);
        pelaajaAreena.add(pOsaButtonX[5],1,1);
        pelaajaAreena.add(pOsaButtonX[6],2,1);
        pelaajaAreena.add(pOsaButtonX[7],3,1);
        pelaajaAreena.add(pOsaButtonX[8],0,2);
        pelaajaAreena.add(pOsaButtonX[9],1,2);
        pelaajaAreena.add(pOsaButtonX[10],2,2);
        pelaajaAreena.add(pOsaButtonX[11],3,2);
        pelaajaAreena.add(pOsaButtonX[12],0,3);
        pelaajaAreena.add(pOsaButtonX[13],1,3);
        pelaajaAreena.add(pOsaButtonX[14],2,3);
        pelaajaAreena.add(pOsaButtonX[15],3,3);


        //---------------------------BOTTIAREENA--------------------------------


        bOsaLabelX[0] = new Label(" ");
        bOsaLabelX[1] = new Label(" ");
        bOsaLabelX[2] = new Label(" ");
        bOsaLabelX[3] = new Label(" ");
        bOsaLabelX[4] = new Label(" ");
        bOsaLabelX[5] = new Label(" ");
        bOsaLabelX[6] = new Label(" ");
        bOsaLabelX[7] = new Label(" ");
        bOsaLabelX[8] = new Label(" ");
        bOsaLabelX[9] = new Label(" ");
        bOsaLabelX[10] = new Label(" ");
        bOsaLabelX[11] = new Label(" ");
        bOsaLabelX[12] = new Label(" ");
        bOsaLabelX[13] = new Label(" ");
        bOsaLabelX[14] = new Label(" ");
        bOsaLabelX[15] = new Label(" ");

        for(int i = 0;i<16;i++) {
            bOsaLabelX[i].setPrefSize(20,20);
            bOsaLabelX[i].setAlignment(Pos.CENTER);
        }

        ColumnConstraints sarake1 =new ColumnConstraints();
        ColumnConstraints sarake2 =new ColumnConstraints();
        ColumnConstraints sarake3 =new ColumnConstraints();
        ColumnConstraints sarake4 =new ColumnConstraints();

        RowConstraints rivi1 = new RowConstraints();
        RowConstraints rivi2 = new RowConstraints();
        RowConstraints rivi3 = new RowConstraints();
        RowConstraints rivi4 = new RowConstraints();

        LabelbottiAreena.getColumnConstraints().add(sarake1);
        LabelbottiAreena.getColumnConstraints().add(sarake2);
        LabelbottiAreena.getColumnConstraints().add(sarake3);
        LabelbottiAreena.getColumnConstraints().add(sarake4);

        LabelbottiAreena.getRowConstraints().add(rivi1);
        LabelbottiAreena.getRowConstraints().add(rivi2);
        LabelbottiAreena.getRowConstraints().add(rivi3);
        LabelbottiAreena.getRowConstraints().add(rivi4);

        sarake1.setPrefWidth(24);
        sarake2.setPrefWidth(24);
        sarake3.setPrefWidth(24);
        sarake4.setPrefWidth(24);

        rivi1.setPrefHeight(24);
        rivi2.setPrefHeight(24);
        rivi3.setPrefHeight(24);
        rivi4.setPrefHeight(24);

        LabelbottiAreena.add(bOsaLabelX[0],0,0);
        LabelbottiAreena.add(bOsaLabelX[1],1,0);
        LabelbottiAreena.add(bOsaLabelX[2],2,0);
        LabelbottiAreena.add(bOsaLabelX[3],3,0);
        LabelbottiAreena.add(bOsaLabelX[4],0,1);
        LabelbottiAreena.add(bOsaLabelX[5],1,1);
        LabelbottiAreena.add(bOsaLabelX[6],2,1);
        LabelbottiAreena.add(bOsaLabelX[7],3,1);
        LabelbottiAreena.add(bOsaLabelX[8],0,2);
        LabelbottiAreena.add(bOsaLabelX[9],1,2);
        LabelbottiAreena.add(bOsaLabelX[10],2,2);
        LabelbottiAreena.add(bOsaLabelX[11],3,2);
        LabelbottiAreena.add(bOsaLabelX[12],0,3);
        LabelbottiAreena.add(bOsaLabelX[13],1,3);
        LabelbottiAreena.add(bOsaLabelX[14],2,3);
        LabelbottiAreena.add(bOsaLabelX[15],3,3);


        aloitaPeliNappi.setOnAction(event -> {
            if(peliMetodit.getPelaajanLaivat() == 0) {
                System.out.println("PELI ALKAA");
                primaryStage.setScene(aloitaPeliRuutu);
                peliMetodit.sekoitaAreena();
            for(int i = 0; i <= 15; i++) {
                if (peliMetodit.peliAreenaJono[i] == 1) {
                    bOsaLabelX[i].setText("X");
                }
                bOsaLabelX[i].setTextFill(Color.BLACK);
            } }
            else {
                System.out.println("ASETA KAIKKI LAIVAT AREENALLE");
                laivojaJajljella.setText("Aseta kaikki laivat");
            }
        });


        aloitaPeliRuutu = new Scene(luoAloitaPeliAsettelu,500,500);

        info.getChildren().addAll(pelaajanTuhoamatLaivat,botinTuhoamatLaivat);
        info.setPrefHeight(200);
        info.setSpacing(10);
        areenatPB.setSpacing(20);
        areenatPB.getChildren().addAll(pelaajaAreena,LabelbottiAreena);
        luoAloitaPeliAsettelu.setCenter(areenatPB);
        luoAloitaPeliAsettelu.setTop(info);


        //-------------------------------------------------------------------


        /*
         luodaan peliOhiRuutu, jossa näkyy seuraavat asiat:
         Kumpi voitti pelin
         Mikä oli aika kun peli loppui
         Paljonko laivoja kumpikin sai tuhottua
         */
        VBox peliOhiRuutuAsettelu = new VBox();
        peliOhiRuutuAsettelu.setAlignment(Pos.CENTER);
        Label laivanUpotus4 = new Label("Laivanupotuspeli");
        laivanUpotus4.setFont(fontNormal);
        laivanUpotus4.setPrefHeight(50);
        laivanUpotus4.setScaleX(2);
        laivanUpotus4.setScaleY(2);
        voittaja.setPrefHeight(75);
        voittaja.setScaleX(1);
        voittaja.setScaleY(1);
        Button alkuun = new Button("Aloita alusta");
        peliOhiRuutu = new Scene(peliOhiRuutuAsettelu, 500,500);
        peliOhiRuutuAsettelu.getChildren().addAll(laivanUpotus4,voittaja,alkuun);

        alkuun.setOnAction(event -> {
            primaryStage.setScene(aloitusRuutu);
            System.out.println("pelaajan areena: " + Arrays.toString(peliMetodit.peliAreenaJono));
            System.out.println("botin areena: " + Arrays.toString(peliMetodit.bottiAreenaJono));
            System.out.println("pelaajan laittamatta jättämät laivat: " + peliMetodit.getPelaajanLaivat()
                                + "\npelaajan tuhoamat laivat: " + peliMetodit.getPelaajanTuhoamatLaivat()
                                + "\nbotin tuhoamat laivat: " + peliMetodit.getBotinTuhoamatlaivat());
            peliMetodit.kirjoitaTiedostoon();
        });


        //----------------------------------------------------------------------


        /*
         Luodaan primaryStage ja päätetään mistä ruudusta ohjelma lähtee liikkeelle.
        */
        primaryStage.setScene(aloitusRuutu);
        primaryStage.setTitle("Laivanupotuspeli");
        primaryStage.show();


    }
}
