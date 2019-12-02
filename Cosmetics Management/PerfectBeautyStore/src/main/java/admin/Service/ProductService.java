package admin.Service;

import admin.DAO.ProductDAO;
import admin.Models.Order;
import admin.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProductService {//Will be needed later when I will actually have database connection
    @Autowired
    ProductDAO data;

    public void updateProduct(Product product){
        data.updateProduct(product);
    }

    public void deleteProduct(int id){
        data.deleteProduct(id);
    }

    public Product createProduct(String nazwa, String rodzaj, String podrodzaj, String brend, Float cena, String brutto, int ilosc, String opis){
        if(brutto.equals("")){
            return data.createProduct(nazwa, rodzaj, podrodzaj, brend, cena, ilosc, opis);
        }else{
            return data.createProduct(nazwa, rodzaj, podrodzaj, brend, cena, Float.parseFloat(brutto), ilosc, opis);
        }
    }
    public List<Product> getProductsList(){
        return data.getProductsList();
    }

    public Page<Product> getPage(Pageable pageable){
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItemIndex = pageSize * currentPage;
        List<Product> selectedProducts;
        if(data.getProductsList().size()<startItemIndex){
            selectedProducts = Collections.emptyList();
        }else{
            int toIndex = Math.min(data.getProductsList().size(),startItemIndex + pageSize);
            selectedProducts = data.getProductsList().subList(startItemIndex,toIndex);
        }
        Page<Product> productPage = new PageImpl<Product>(selectedProducts, PageRequest.of(currentPage,pageSize),data.getProductsList().size());
        return productPage;
    }

    public Product getProduct(int id){
        return data.getProduct(id);
    }

    public Page<Product> getPage(Pageable pageable, String serchword, Float price_from, Float price_to, Integer status) {
        serchword=serchword.toLowerCase();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItemIndex = pageSize * currentPage;
        List<Product> allProducts = data.getProductsList();
        List<Product> productsSearched = new ArrayList<Product>();
        for(Product o : allProducts){
            if(o.getBrend().toLowerCase().contains(serchword)||o.getNazwa().toLowerCase().contains(serchword)
                    ||o.getPodrodzaj().toLowerCase().contains(serchword)||o.getRodzaj().toLowerCase().contains(serchword)
                    ||o.getOpis().toLowerCase().contains(serchword)){
                productsSearched.add(o);
            }
        }
        ////////////prices
        if(price_from!=null) {
            for(Product o : allProducts){
                if(o.getCena()<price_from){
                    productsSearched.remove(o);
                }
            }
        }
        if(price_to!=null) {
            for(Product o : allProducts){
                if(o.getCena()>price_to){
                    productsSearched.remove(o);
                }
            }
        }
        ////////////
        if(status!=null){
            switch (status){
                case (1):
                    productsSearched.sort(Comparator.comparing(Product::getCena));
                    break;
                case (2):
                    productsSearched.sort(Comparator.comparing(Product::getCena).reversed());
                    break;
            }
        }
        ////////////
        List<Product> selectedProducts;
        if(data.getProductsList().size()<startItemIndex){
            selectedProducts = Collections.emptyList();
        }else{
            int toIndex = Math.min(productsSearched.size(),startItemIndex + pageSize);
            selectedProducts = productsSearched.subList(startItemIndex,toIndex);
        }
        Page<Product> productPage = new PageImpl<Product>(selectedProducts, PageRequest.of(currentPage,pageSize),productsSearched.size());
        return productPage;
    }
}
