package admin.Controllers;

import admin.Models.Order;
import admin.Models.Product;
import admin.Service.OrderService;
import admin.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class OrderController {
    private static final int pageSize = 5;
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @GetMapping("tabela_zamowien")
    public String tabelaZamowien(Model model, @RequestParam("page") Optional<Integer> page,
                                @RequestParam(required = false,name="keyword") String keyword,
                                 @RequestParam(required = false,name="sortowanie")  Integer sort,
                                 @RequestParam(required = false,name="date_from") String date_from,
                                 @RequestParam(required = false,name="date_to") String date_to,
                                 @RequestParam(required = false,name="price_from") Float price_from,
                                 @RequestParam(required = false,name="price_to") Float price_to
    ){
        int currentPage = page.orElse(1);
        Page<Order> orderPage;
        if(keyword==null&&date_from==null&&date_to==null&&price_from==null&&price_to==null&&sort==null)
            orderPage = orderService.getPage(PageRequest.of(currentPage - 1, pageSize));
        else
            orderPage = orderService.getPage(PageRequest.of(currentPage - 1, pageSize),keyword,date_from,date_to,price_from,price_to,sort);
        model.addAttribute("orderPage", orderPage);
        model.addAttribute("currentPage", currentPage);
        ////Sort&Filter
        if(date_from!=null&&date_from!=""){
            model.addAttribute("date_from",date_from);
        }
        if(date_to!=null&&date_to!=""){
            model.addAttribute("date_to",date_to);
        }
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
        ////
        int totalPages = orderPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        if(currentPage>orderPage.getTotalPages()||currentPage<1)
            return "exceptions/pageNotFound";
        else
            return "admin/zarządzanie_zamówieniami/tabela_zamówień";
    }
    @RequestMapping("order_dodanie")
    String orderAdd(Model model){
        //Order order = new Order();
        List<Product> allProducts =  productService.getProductsList();
        //List<Product> selectedProducts = new ArrayList<Product>();
        List<String> statuses = orderService.getStatuses();
        //model.addAttribute("order",order);
        model.addAttribute("allProducts",allProducts);
        //model.addAttribute("selectedProducts",selectedProducts);
        model.addAttribute("statuses", statuses);
        return "admin/zarządzanie_zamówieniami/dodanie";
    }
    @RequestMapping("save_order")
    String orderSave(@RequestParam("imie") String clientName,@RequestParam("nazwisko") String clientSurname,@RequestParam("phone") String phoneNumber,@RequestParam("email") String email,@RequestParam("date") String dateString,@RequestParam("miejsce") String deliveryPlace,@RequestParam("status") String status,@RequestParam("productid") int[] productsId,@RequestParam("ilosc") int[] ilosci, Model model){
        Order order = new Order();
        order.setStatus(status);
        Map<Product,Integer> productsInOrder = new HashMap<Product, Integer>();
        for(int i = 0;i<productsId.length;i++){
            productsInOrder.put(productService.getProduct(productsId[i]),ilosci[i]);
        }
        order.setProductsInOrder(productsInOrder);
        order.setPhoneNumber(phoneNumber);
        Date orderDate = null;
        try {
            orderDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        order.setOrderDate(orderDate);
        order.setEmail(email);
        order.setDeliveryPlace(deliveryPlace);
        order.setClientSurname(clientSurname);
        order.setClientName(clientName);
        Order order4info = orderService.createOrder(order);
        model.addAttribute("order",order);
        return "admin/zarządzanie_zamówieniami/szczegóły";
    }
    @RequestMapping("order_details")
    String getInfo(@RequestParam("orderId") int id, Model model){
        model.addAttribute("order",orderService.getOrder(id));
        return "admin/zarządzanie_zamówieniami/szczegóły";
    }
    @RequestMapping("delete_order")
    String deleteOrder(Model model, @RequestParam("page") Optional<Integer> page,@RequestParam("id") int id){
        orderService.deleteOrder(id);
        int currentPage = page.orElse(1);

        Page<Order> orderPage = orderService.getPage(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("orderPage", orderPage);
        model.addAttribute("currentPage", currentPage);
        int totalPages = orderPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        if(currentPage>orderPage.getTotalPages()||currentPage<1)
            return "exceptions/pageNotFound";
        else
            return "admin/zarządzanie_zamówieniami/potwierdzenia/usuwanie";
    }
    @RequestMapping("orders_with_product")
    String ordersWithProduct(Model model, @RequestParam("page") Optional<Integer> page,@RequestParam("id") int id){

        int currentPage = page.orElse(1);

        Page<Order> orderPage = orderService.getOrdersWithProduct(PageRequest.of(currentPage - 1, pageSize),id);

        model.addAttribute("orderPage", orderPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("id",id);
        int totalPages = orderPage.getTotalPages();
        System.out.println(totalPages);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        if(currentPage>orderPage.getTotalPages()||currentPage<1)
            return "exceptions/pageNotFound";
        else
            return "admin/zarządzanie_zamówieniami/tabela_zamówień_z_produktem";
    }
    @RequestMapping("order_edycja")
    String orderEdit(@RequestParam("id") int id,Model model){
        List<Product> allProducts =  productService.getProductsList();
        List<String> statuses = orderService.getStatuses();
        SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String parsedDate = formatter.format(orderService.getOrder(id).getOrderDate());
        int[] ints = new int[orderService.getOrder(id).getProductsInOrder().size()];
        int i=0;
        for(Product p : orderService.getOrder(id).getProductsInOrder().keySet()){
            ints[i]=p.getProductid();
            i++;
        }
        model.addAttribute("ints",ints);
        model.addAttribute("parsedDate",parsedDate);
        model.addAttribute("order",orderService.getOrder(id));
        model.addAttribute("allProducts",allProducts);
        model.addAttribute("statuses", statuses);
        return "admin/zarządzanie_zamówieniami/edycja";
    }
    @RequestMapping("order_edited")
    String orderEdited(@RequestParam("id") int id,@RequestParam("imie") String clientName,@RequestParam("nazwisko") String clientSurname,@RequestParam("phone") String phoneNumber,@RequestParam("email") String email,@RequestParam("date") String dateString,@RequestParam("miejsce") String deliveryPlace,@RequestParam("status") String status,@RequestParam("productid") int[] productsId,@RequestParam("ilosc") int[] ilosci,Model model){
        Date orderDate = null;
        try {
            orderDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<Product,Integer> productsInOrder = new HashMap<Product, Integer>();
        for(int i = 0;i<productsId.length;i++){
            productsInOrder.put(productService.getProduct(productsId[i]),ilosci[i]);
        }
        Order order = new Order(clientName, clientSurname, phoneNumber, email,orderDate,  deliveryPlace, status, productsInOrder, id);
        orderService.updateOrder(order);
        model.addAttribute("order",orderService.getOrder(id));
        return "admin/zarządzanie_zamówieniami/szczegóły";
    }
}
