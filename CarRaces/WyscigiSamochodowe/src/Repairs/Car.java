package Repairs;

import Races.Driver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Car implements Serializable {
    private static String fileName;
    private String brand;
    private String serialNumber;
    private double maxSpeed;
    private static List<Car> extent;
    private List<Repair> repairs = new ArrayList<>();
    private List<CarPart> parts = new ArrayList<>();
    private List<CarPart> allParts = new ArrayList<>();
    private List<Driver> drivers = new ArrayList<>();

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void removeDriver(Driver person){
        if(drivers.contains(person)) {
            drivers.remove(person);
            person.removeCar(this);
        }
    }

    public void addDriver(Driver person){
        if(!drivers.contains(person)) {
            drivers.add(person);
            person.addCar(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return Objects.equals(serialNumber, car.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber);
    }

    public void addPart(CarPart part) throws Exception {
        if(!parts.contains(part)) {
        // Check if the part has been already added to any wholes
            if(allParts.contains(part)) {
                throw new Exception("The part is already connected with a whole!");
            }
            parts.add(part);
            // Store on the list of all parts
            allParts.add(part);
        }
    }

    public void addRepair(Repair repair){
        if(repair!=null)
            if(!repairs.contains(repair)){
                repairs.add(repair);
                repair.setCar(this);
            }
    }

    public void removeRepair(Repair repair){
        if(repair!=null)
            if(repairs.contains(repair)){
                repairs.remove(repair);
                repair.destroy();
            }
    }

    public List<CarPart> getParts() {
        return parts;
    }

    public List<Repair> getRepairs() {
        return repairs;
    }

    public static void addCar(String brand, String serialNumber, double maxSpeed){
        if(getExtent().stream().filter(car -> car.getSerialNumber().equals(serialNumber)).collect(Collectors.toList()).size()==0)
            new Car(brand, serialNumber, maxSpeed);
    }

    Car(String brand, String serialNumber, double maxSpeed){
        this.brand = brand;
        this.serialNumber = serialNumber;
        this.maxSpeed = maxSpeed;
        repairs = new ArrayList<>();
        parts = new ArrayList<>();
        allParts = new ArrayList<>();
        drivers = new ArrayList<>();
        extent.add(this);
    }

    public static List<Car> getExtent(){
        return extent;
    }

    public static void writeExtent() throws IOException {
        new ObjectOutputStream(new FileOutputStream(fileName)).writeObject(extent);
    }

    public static void readExtent() throws ClassNotFoundException {
        try {
            extent = (List<Car>) new ObjectInputStream(new FileInputStream(fileName)).readObject();
        } catch (EOFException e) {
            extent = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFileName(){
        return fileName;
    }

    public static void setFileName(String file){
        fileName = file;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
