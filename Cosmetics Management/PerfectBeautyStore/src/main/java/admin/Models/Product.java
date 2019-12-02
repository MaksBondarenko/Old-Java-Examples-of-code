package admin.Models;

import java.util.List;

public class Product {
    private String nazwa;
    private String rodzaj;
    private String podrodzaj;
    private String brend;
    private Float cena;
    private Float brutto;
    private int ilosc;
    private String opis;
    private int productid;
    private String pathToImage;

    public Product() {
    }



    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }


    public Product(String pathToImage,String nazwa, String rodzaj, String podrodzaj, String brend, Float cena, Float brutto, int ilosc, String opis, int productid) {
        this.nazwa = nazwa;
        this.rodzaj = rodzaj;
        this.podrodzaj = podrodzaj;
        this.brend = brend;
        this.cena = cena;
        this.brutto = brutto;
        this.ilosc = ilosc;
        this.opis = opis;
        this.productid = productid;
        this.pathToImage = pathToImage;
    }
    public Product(String nazwa, String rodzaj, String podrodzaj, String brend, Float cena, Float brutto, int ilosc, String opis, int productid) {
        this.nazwa = nazwa;
        this.rodzaj = rodzaj;
        this.podrodzaj = podrodzaj;
        this.brend = brend;
        this.cena = cena;
        this.brutto = brutto;
        this.ilosc = ilosc;
        this.opis = opis;
        this.productid = productid;
    }
    public Product(String nazwa, String rodzaj, String podrodzaj, String brend, Float cena, int ilosc, String opis, int productid) {
        this.nazwa = nazwa;
        this.rodzaj = rodzaj;
        this.podrodzaj = podrodzaj;
        this.brend = brend;
        this.cena = cena;
        this.ilosc = ilosc;
        this.opis = opis;
        this.productid = productid;
    }
//    public String getPathToImage() {
//        return pathToImage;
//    }
//
//    public void setPathToImage(String pathToImage) {
//        this.pathToImage = pathToImage;
//    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public Float getBrutto() {
        return brutto;
    }

    public void setBrutto(Float brutto) {
        this.brutto = brutto;
    }



    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }

    public String getPodrodzaj() {
        return podrodzaj;
    }

    public void setPodrodzaj(String podrodzaj) {
        this.podrodzaj = podrodzaj;
    }

    public String getBrend() {
        return brend;
    }

    public void setBrend(String brend) {
        this.brend = brend;
    }

    public Float getCena() {
        return cena;
    }

    public void setCena(Float cena) {
        this.cena = cena;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
