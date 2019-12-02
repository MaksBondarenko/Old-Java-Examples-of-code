package admin.DAO;


import admin.Models.Order;
import admin.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
@Repository
public class OrderDAO {
    List<Order> orders;
    List<String> statuses = Arrays.asList("opłacone, w trakcie realizacji","niopłacone, w trackie realizacji", "odwolane", "zrealizowane");
    private int licznikId=0;
    @Autowired
    ProductDAO productDAO;
    @PostConstruct
    public void init(){
        Map<Product,Integer> products = new HashMap<Product,Integer>();
        System.out.println(productDAO.getProduct(0).getBrend());
        products.put(productDAO.getProduct(0),1);
        Order product1 = new Order("Maksym","Bondarenko","+48 999-999-999","max.bondarenko99@gmail.com", new GregorianCalendar(2017, 0 , 25).getTime(),"Pieklo, The walls of Dis",statuses.get(0),products,licznikId);
        licznikId++;
        products= new HashMap<Product,Integer>();
        products.put(productDAO.getProduct(2),1);
        Order product2 = new Order("Rafal","Glinka","+48 999-999-999","inkognito1@doesntexist.com",new GregorianCalendar(2017, 1 , 18).getTime(),"Ukraine, Kherson",statuses.get(2),products,licznikId);
        licznikId++;
        products= new HashMap<Product,Integer>();
        products.put(productDAO.getProduct(3),1);
        Order product3 = new Order("Sebastian","Goral","+48 999-999-999","inkognito2@doesntexist.com",new GregorianCalendar(2018, 11 , 03).getTime(),"Sesame street",statuses.get(1),products,licznikId);
        licznikId++;
        products= new HashMap<Product,Integer>();
        products.put(productDAO.getProduct(1),1);
        Order product4 = new Order("Kamil","Kus","+48 999-999-999","inkognito3@doesntexist.com",new GregorianCalendar(2019, 7 , 30).getTime(),"Dark side of moon",statuses.get(3),products,licznikId);
        licznikId++;
        products= new HashMap<Product,Integer>();
        products.put(productDAO.getProduct(2),1);
        Order product5 = new Order("Sam","Black","+48 999-999-999","inkognito4@doesntexist.com",new GregorianCalendar(2019, 9 , 12).getTime(),"Pieklo, Ice Lake",statuses.get(2),products,licznikId);
        licznikId++;
        products= new HashMap<Product,Integer>();
        products.put(productDAO.getProduct(6),1);
        products.put(productDAO.getProduct(7),3);
        Order product6 = new Order("John","Doe","+48 999-999-999","inkognito5@doesntexist.com",new GregorianCalendar(2019, 1 , 27).getTime(),"Uranus",statuses.get(0),products,licznikId);
        licznikId++;
        products= new HashMap<Product,Integer>();
        products.put(productDAO.getProduct(5),2);
        Order product7 = new Order("Kosmo","Stars","+48 999-999-999","inkognito6@doesntexist.com",new GregorianCalendar(2017, 0 , 05).getTime(),"London, The capital of Great Britain",statuses.get(1),products,licznikId);
        licznikId++;
        products= new HashMap<Product,Integer>();
        products.put(productDAO.getProduct(4),1);
        Order product8 = new Order("Samuel","Jackson","+48 999-999-999","inkognito7@doesntexist.com",new GregorianCalendar(2018, 4 , 17).getTime(),"Pieklo, The walls of Dat",statuses.get(0),products,licznikId);
        licznikId++;
        ArrayList<Order> array = new ArrayList<Order>();
        array.addAll(Arrays.asList(product1,product2,product3,product4,product5,product6,product7,product8));
        orders = array;
    }

    public List<Order> getOrdersList() {
        return orders;
    }

    public Order getOrder(int id) {
        for(Order order : orders){
            if(order.getOrderId()==id)
                return order;
        }
        return null;
    }
    public Order createOrder(Order order) {
        order.setOrderId(licznikId);
        orders.add(order);
        licznikId++;
        return order;
    }

    public List<String> getStatuses() {
        return statuses;
    }

    public void updateOrder(Order order_after){
        int id = order_after.getOrderId();
        for(Order order_before : orders) {
            if (order_before.getOrderId() == id) {
                order_before.setClientName(order_after.getClientName());
                order_before.setClientSurname(order_after.getClientSurname());
                order_before.setDeliveryPlace(order_after.getDeliveryPlace());
                order_before.setEmail(order_after.getEmail());
                order_before.setOrderDate(order_after.getOrderDate());
                order_before.setPhoneNumber(order_after.getPhoneNumber());
                order_before.setProductsInOrder(order_after.getProductsInOrder());
                order_before.setStatus(order_after.getStatus());
            }
        }
    }
    public void deleteOrder(int id) {
        for (int i=0;i<orders.size();i++) {
            if (orders.get(i).getOrderId() == id) {
                orders.remove(i);
            }
        }
    }
}
