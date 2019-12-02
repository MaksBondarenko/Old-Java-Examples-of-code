package admin.Service;

import admin.DAO.OrderDAO;
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
public class OrderService {
    @Autowired
    OrderDAO data;

    public List<String> getStatuses(){
        return data.getStatuses();
    }

    public void updateOrder(Order order){
        data.updateOrder(order);
    }

    public void deleteOrder(int id){
        data.deleteOrder(id);
    }

    public Order createOrder(Order order){
        return data.createOrder(order);
    }
    public Page<Order> getPage(Pageable pageable){
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItemIndex = pageSize * currentPage;
        List<Order> selectedOrders;
        if(data.getOrdersList().size()<startItemIndex){
            selectedOrders = Collections.emptyList();
        }else{
            int toIndex = Math.min(data.getOrdersList().size(),startItemIndex + pageSize);
            selectedOrders = data.getOrdersList().subList(startItemIndex,toIndex);
        }
        Page<Order> orderPage = new PageImpl<Order>(selectedOrders, PageRequest.of(currentPage,pageSize),data.getOrdersList().size());
        return orderPage;
    }
    public Page<Order> getPage(Pageable pageable, String serchword, String date_from, String date_to, Float price_from, Float price_to, Integer status){
        serchword=serchword.toLowerCase();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItemIndex = pageSize * currentPage;
        List<Order> allOrders = data.getOrdersList();
        List<Order> ordersSearched = new ArrayList<Order>();
        for(Order o : allOrders){
                    if(o.getClientName().toLowerCase().contains(serchword)||o.getClientSurname().toLowerCase().contains(serchword)
                            ||o.getDeliveryPlace().toLowerCase().contains(serchword)||o.getEmail().toLowerCase().contains(serchword)
                            ||o.getStatus().toLowerCase().contains(serchword)||o.getPhoneNumber().toLowerCase().contains(serchword)){
                        ordersSearched.add(o);
                    }
        }
        //////////// dates
        if(!date_from.equals("")) {
            Date orderDate_from = null;
            try {
                orderDate_from = new SimpleDateFormat("yyyy-MM-dd").parse(date_from);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            for(Order o : allOrders){
                if(!(o.getOrderDate().compareTo(orderDate_from)>=0)){
                    ordersSearched.remove(o);
                }
            }
        }
        if(!date_to.equals("")) {
            Date orderDate_to = null;
            try {
                orderDate_to = new SimpleDateFormat("yyyy-MM-dd").parse(date_to);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            for(Order o : allOrders){
                if(!(o.getOrderDate().compareTo(orderDate_to)<=0)){
                    ordersSearched.remove(o);
                }
            }
        }
        ////////////prices
        if(price_from!=null) {
            for(Order o : allOrders){
                if(o.getTotalCost()<price_from){
                    ordersSearched.remove(o);
                }
            }
        }
        if(price_to!=null) {
            for(Order o : allOrders){
                if(o.getTotalCost()>price_to){
                    ordersSearched.remove(o);
                }
            }
        }
        ////////////
        if(status!=null){
            switch (status){
                case (1):
                    ordersSearched.sort(Comparator.comparing(Order::getTotalCost));
                    break;
                case (2):
                    ordersSearched.sort(Comparator.comparing(Order::getTotalCost).reversed());
                    break;
                case (3):
                    ordersSearched.sort(Comparator.comparing(Order::getOrderDate));
                    break;
                case (4):
                    ordersSearched.sort(Comparator.comparing(Order::getOrderDate).reversed());
                    break;
            }
        }
        ////////////
        List<Order> selectedOrders;
        if(data.getOrdersList().size()<startItemIndex){
            selectedOrders = Collections.emptyList();
        }else{
            int toIndex = Math.min(ordersSearched.size(),startItemIndex + pageSize);
            selectedOrders = ordersSearched.subList(startItemIndex,toIndex);
        }
        Page<Order> orderPage = new PageImpl<Order>(selectedOrders, PageRequest.of(currentPage,pageSize),ordersSearched.size());
        return orderPage;
    }
    public Page<Order> getOrdersWithProduct(Pageable pageable,int id){
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItemIndex = pageSize * currentPage;
        List<Order> allOrders = data.getOrdersList();
        List<Order> ordersWithProduct = new ArrayList<Order>();
        for(Order order : allOrders){
            for(Product product : order.getProductsInOrder().keySet()){
                if(product.getProductid()==id)
                    ordersWithProduct.add(order);
            }
        }
        List<Order> selectedOrders;
        if(data.getOrdersList().size()<startItemIndex){
            selectedOrders = Collections.emptyList();
        }else{
            int toIndex = Math.min(ordersWithProduct.size(),startItemIndex + pageSize);
            selectedOrders = ordersWithProduct.subList(startItemIndex,toIndex);
        }
        Page<Order> orderPage = new PageImpl<Order>(selectedOrders, PageRequest.of(currentPage,pageSize),ordersWithProduct.size());
        return orderPage;
    }

    /*public List<Order> getAllOrders(){
        return data.getOrdersList();
    }*/

    public Order getOrder(int id){
        return data.getOrder(id);
    }
}
