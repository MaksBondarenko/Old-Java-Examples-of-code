package Repairs;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarPart implements Serializable {
    private static String fileName;
    private static List<CarPart> extent;
    private Car car;
    private String name;
    private double price;
    private String description;
    private List<Repair> repairs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarPart)) return false;
        CarPart carPart = (CarPart) o;
        return Double.compare(carPart.price, price) == 0 &&
                Objects.equals(car, carPart.car) &&
                Objects.equals(name, carPart.name) &&
                Objects.equals(description, carPart.description) &&
                Objects.equals(repairs, carPart.repairs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(car, name, price, description, repairs);
    }

    public Car getCar() {
        return car;
    }

    private CarPart(Car car, String name, double price, String description)
    {
        this.name = name;
        this.price = price;
        this.description = description;
        this.car= car;
        repairs = new ArrayList<>();
        extent.add(this);
    }

    public void removeRepair(Repair repair){
        if(repairs.contains(repair)) {
            repairs.remove(repair);
            repair.removeCarPart(this);
        }
    }

    public void addRepair(Repair repair){
        if(!repairs.contains(repair)) {
            repairs.add(repair);
            repair.addCarPart(this);
        }
    }

    public static CarPart createPart(Car whole, String name, double price, String description) throws Exception {
        if(whole == null) {
            throw new Exception("The given whole does not exist!");
        }
        // Create a new part
        CarPart part= new CarPart(whole, name, price, description);
        // Add to the whole
        whole.addPart(part);
        return part;
    }

    public static List<CarPart> getExtent(){
        return extent;
    }

    public static void writeExtent() throws IOException {
        new ObjectOutputStream(new FileOutputStream(fileName)).writeObject(extent);
    }

    public static void readExtent() throws ClassNotFoundException {
        try {
            extent = (List<CarPart>) new ObjectInputStream(new FileInputStream(fileName)).readObject();
        } catch (EOFException e) {
            extent = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String getFileName(){
        return fileName;
    }

    public static void setFileName(String file){
        fileName = file;
    }
}
