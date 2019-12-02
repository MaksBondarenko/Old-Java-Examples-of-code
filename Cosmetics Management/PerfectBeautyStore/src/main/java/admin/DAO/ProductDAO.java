package admin.DAO;


import admin.Models.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class ProductDAO{
    List<Product> products;
    private int licznikId=0;
    String pathToImage = "images/products/soda.jpg";

    public ProductDAO() {
        Product product1 = new Product(pathToImage,"Kosmokrem 3000","Twarz","Maska dla twarzy","Hiber Cosmetic",57.25f,30.0f,3,"Super",0);
        Product product2 = new Product(pathToImage,"KreimenLeif","Twarz","Maska dla twarzy","NYX",190.25f,200.0f,5,"Super",1);
        Product product3 = new Product(pathToImage,"Gel SuperSkóra","Ciało","Krem dla rąk","Hiber Cosmetic",155.25f,130.0f,13,"Super",2);
        Product product4 = new Product(pathToImage,"Shampoo 5 stars","Włosy","Szampon","SzwarzKopf",17.25f,250.0f,34,"Super",3);
        Product product5 = new Product(pathToImage,"Shampoo 4U","Włosy","Szampon","Hiber Cosmetic",27.25f,300.0f,3,"Super",4);
        Product product6 = new Product(pathToImage,"Shampoo Fast&Dry","Włosy","Szampon","Dove",57.25f,530.0f,3,"Super",5);
        Product product7 = new Product(pathToImage,"Kosmokrem 2020","Twarz","Maska dla twarzy","Hiber Cosmetic",107.25f,240.0f,0,"Super",6);
        Product product8 = new Product(pathToImage,"MyGel","Ciało","Ciało","Dove",14.25f,100.0f,37,"Super",7);
        licznikId+=8;
        ArrayList<Product> array = new ArrayList<Product>();
        array.addAll(Arrays.asList(product1,product2,product3,product4,product5,product6,product7,product8));
        products= array;
    }

    public List<Product> getProductsList() {
        return products;
    }

    public Product createProduct(String nazwa, String rodzaj, String podrodzaj, String brend, Float cena, Float brutto, int ilosc, String opis) {
        Product product = new Product(nazwa,rodzaj,podrodzaj,brend,cena,brutto,ilosc,opis,licznikId);
        products.add(product);
        licznikId++;
        return product;
    }
    public Product createProduct(String nazwa, String rodzaj, String podrodzaj, String brend, Float cena, int ilosc, String opis) {
        Product product = new Product(nazwa,rodzaj,podrodzaj,brend,cena,ilosc,opis,licznikId);
        products.add(product);
        licznikId++;
        return product;
    }

    public void updateProduct(Product product_after){
        int id = product_after.getProductid();
        for(Product product_before : products) {
            if (product_before.getProductid() == id) {
                product_before.setNazwa(product_after.getNazwa());
                product_before.setRodzaj(product_after.getRodzaj());
                product_before.setPodrodzaj(product_after.getPodrodzaj());
                product_before.setBrend(product_after.getBrend());
                product_before.setBrutto(product_after.getBrutto());
                product_before.setCena(product_after.getCena());
                product_before.setIlosc(product_after.getIlosc());
                product_before.setOpis(product_after.getOpis());
            }
        }
    }

    public void deleteProduct(int id) {
        for (int i=0;i<products.size();i++) {
            if (products.get(i).getProductid() == id) {
                products.remove(i);
            }
        }
    }
    public Product getProduct(int id) {
        for(Product product : products){
            if(product.getProductid()==id)
                return product;
        }
        return null;
    }
}
