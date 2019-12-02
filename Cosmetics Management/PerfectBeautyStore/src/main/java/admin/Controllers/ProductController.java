package admin.Controllers;

import admin.Models.Product;
import admin.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProductController {
    private static final int pageSize = 5;
    @Autowired
    ProductService productService;
    @GetMapping("tabela_ofert")
    public String tabelaOfert(Model model, @RequestParam("page") Optional<Integer> page,
                              @RequestParam(required = false,name="keyword") String keyword,
                              @RequestParam(required = false,name="sortowanie")  Integer sort,
                              @RequestParam(required = false,name="price_from") Float price_from,
                              @RequestParam(required = false,name="price_to") Float price_to
    ){
        int currentPage = page.orElse(1);
        Page<Product> productPage;
        if(keyword==null&&price_from==null&&price_to==null&&sort==null)
            productPage = productService.getPage(PageRequest.of(currentPage - 1, pageSize));
        else
            productPage = productService.getPage(PageRequest.of(currentPage - 1, pageSize),keyword,price_from,price_to,sort);

        //List<Product> x = productPage.get().collect(Collectors.toList());

        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", currentPage);

        if(price_from!=null){
            model.addAttribute("price_from",price_from);
        }
        if(price_to!=null){
            model.addAttribute("price_to",price_to);
        }
        if(sort!=null){
            model.addAttribute("sort",sort);
        }
        if(keyword!=null){
            model.addAttribute("keyword",keyword);
        }

        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        if(currentPage>productPage.getTotalPages()||currentPage<1)
            return "exceptions/pageNotFound";
        else
            return "admin/zarządzanie_ofertami/tabela_ofert";
    }
    @RequestMapping("product_details")
    String productDetails(@RequestParam("id") int id,Model model){
        model.addAttribute("product",productService.getProduct(id));
        return "admin/zarządzanie_ofertami/szczegóły";
    }
    @RequestMapping("product_edycja")
    String productEdit(@RequestParam("id") int id, Model model){
        model.addAttribute("product",productService.getProduct(id));
        return "admin/zarządzanie_ofertami/edycja";
    }
    @RequestMapping("product_zedytowany")
    String productEdited(@RequestParam("opis") String opis,@RequestParam(value = "brutto",required = false) String brutto,@RequestParam("cena") float cena,@RequestParam("ilosc") int ilosc,@RequestParam("brend") String brend,@RequestParam("podrodzaj") String podrodzaj,@RequestParam("nazwa") String nazwa,@RequestParam("rodzaj")  String rodzaj,@RequestParam("id")  int id, Model model){
        Product product = null;
        if(!brutto.equals("")){
            product = new Product(nazwa,rodzaj,podrodzaj,brend,cena,Float.parseFloat(brutto),ilosc,opis,id);
        }else{
            product = new Product(nazwa,rodzaj,podrodzaj,brend,cena,ilosc,opis,id);
        }
        productService.updateProduct(product);
        model.addAttribute("product",productService.getProduct(id));
        return "admin/zarządzanie_ofertami/szczegóły";
    }
    @RequestMapping("delete_product")
    String productDeleted(Model model,@RequestParam(value="page",required= false) Optional<Integer> page,@RequestParam("id") int id){
        productService.deleteProduct(id);
        int currentPage = page.orElse(1);

        Page<Product> productPage = productService.getPage(PageRequest.of(currentPage - 1, pageSize));
        //List<Product> x = productPage.get().collect(Collectors.toList());
        if( currentPage!=1&&currentPage==productPage.getTotalPages()+1) { // if < 1 element on page -> go to previous page
            currentPage--;
            productPage = productService.getPage(PageRequest.of(currentPage - 1, pageSize));
        }
        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", currentPage);

        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        if(currentPage>productPage.getTotalPages()||currentPage<1)
            return "exceptions/pageNotFound";
        else
            return "admin/zarządzanie_ofertami/potwierdzenia/usuwanie";
    }
    @RequestMapping("product_dodanie")
    String productAdd(Model model){
        Product product = new Product();
        model.addAttribute("product",product);
        return "admin/zarządzanie_ofertami/dodanie";
    }
    @RequestMapping("save_product")
    String productSave(@RequestParam("opis") String opis,@RequestParam(value = "brutto",required = false) String brutto,@RequestParam("cena") float cena,@RequestParam("ilosc") int ilosc,@RequestParam("brend") String brend,@RequestParam("podrodzaj") String podrodzaj,@RequestParam("nazwa") String nazwa,@RequestParam("rodzaj")  String rodzaj, Model model){
        Product product = productService.createProduct(nazwa, rodzaj, podrodzaj, brend, cena, brutto, ilosc, opis);
        model.addAttribute("product",product);
        return "admin/zarządzanie_ofertami/szczegóły";
    }
    @RequestMapping("zobacz_wiecej")
    String zobaczWiecej(@RequestParam("id") int id, Model model){
        model.addAttribute("product",productService.getProduct(id));
        return "admin/zarządzanie_zamówieniami/szczegóły_oferty";
    }
}
