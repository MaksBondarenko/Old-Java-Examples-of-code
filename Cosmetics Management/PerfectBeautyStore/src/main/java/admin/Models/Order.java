package admin.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Order {
    private String ClientName;
    private String ClientSurname;
    private String PhoneNumber;
    private String Email;
    private Date OrderDate;
    private String DeliveryPlace;
    private String Status;
    private Map<Product,Integer> productsInOrder;
    private int orderId;

    public int getOrderId() {
        return orderId;
    }

    public Order() {
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Order(String clientName, String clientSurname, String phoneNumber, String email, Date orderDate, String deliveryPlace, String status, Map<Product,Integer> productsInOrder, int orderId) {
        ClientName = clientName;
        ClientSurname = clientSurname;
        PhoneNumber = phoneNumber;
        Email = email;
        OrderDate = orderDate;
        DeliveryPlace = deliveryPlace;
        Status = status;
        this.productsInOrder = productsInOrder;
        this.orderId = orderId;
    }
    public void addProductToOrder(Product product, int ilosc) {
        this.productsInOrder.put(product,ilosc);
    }
    public void deleteProductFromOrder(int id) {
        for( Product product : productsInOrder.keySet()){
            if(product.getProductid()==id){
                productsInOrder.remove(product);
            }
        }
    }

    public Map<Product,Integer> getProductsInOrder() {
        return productsInOrder;
    }

    public void setProductsInOrder(Map<Product,Integer> productsInOrder) {
        this.productsInOrder = productsInOrder;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getClientSurname() {
        return ClientSurname;
    }

    public void setClientSurname(String clientSurname) {
        ClientSurname = clientSurname;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Date getOrderDate() {
        return OrderDate;
    }
    public String getParsedDate() {
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "dd/MM/yyyy");
            return formatter.format(OrderDate);
    }

    public void setOrderDate(Date orderDate) {
        OrderDate = orderDate;
    }

    public String getDeliveryPlace() {
        return DeliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        DeliveryPlace = deliveryPlace;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Float getTotalCost(){
        Float totalCost = 0f;
        for(Product product: productsInOrder.keySet()){
            totalCost+=product.getCena()*productsInOrder.get(product); //cena*ilosc
        }
        return totalCost;
    }
}
