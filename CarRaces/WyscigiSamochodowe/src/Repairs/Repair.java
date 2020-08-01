package Repairs;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Repair implements Serializable{
    private Car car;
    private Mechanic mechanic;
    private List<CarPart> carPartList;
    private double worth;
    private Date date;
    private String description;
    private static String fileName;
    private static List<Repair> extent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Repair)) return false;
        Repair repair = (Repair) o;
        return Double.compare(repair.worth, worth) == 0 &&
                Objects.equals(car, repair.car) &&
                Objects.equals(mechanic, repair.mechanic) &&
                Objects.equals(carPartList, repair.carPartList) &&
                Objects.equals(date, repair.date) &&
                Objects.equals(description, repair.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(car, mechanic, carPartList, worth, date, description);
    }

    public static void createRepair(List<CarPart> carPartList, Car car, Mechanic mechanic, double worth, Date date, String description){
        Repair repair = new Repair();
        repair.carPartList = new ArrayList<>();
        for(CarPart part : carPartList){
            repair.addCarPart(part);
        }
        repair.car = car;
        repair.mechanic = mechanic;
        repair.worth = worth;
        repair.date = date;
        repair.description = description;
        repair.setCar(car);
        repair.setMechanic(mechanic);
        extent.add(repair);
    }

    public static List<Repair> getRepairsWithMechanic(Mechanic mechanic) {
        return getExtent().stream().filter(repair -> repair.getMechanic().getLogin().equals(mechanic.getLogin())).collect(Collectors.toList());
    }

    void destroy(){
        if(mechanic.getRepairs().contains(this))
            mechanic.removeRepair(this);
        if(car.getRepairs().contains(this))
            car.removeRepair(this);
    }

    public Car getCar() {
        return car;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    void setMechanic(Mechanic mechanic) {
        if(mechanic!=null) {
            //destroy();
            this.mechanic = mechanic;
            car.addRepair(this);
            mechanic.addRepair(this);
        }
    }

    public double getWorth() {
        return worth;
    }

    public void setWorth(double worth) {
        this.worth = worth;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    void setCar(Car car) {
        if(car!=null) {
            //destroy();
            this.car = car;
            mechanic.addRepair(this);
            car.addRepair(this);
        }
    }

    public static List<Repair> getExtent(){
        return extent;
    }

    public static void writeExtent() throws IOException {
        new ObjectOutputStream(new FileOutputStream(fileName)).writeObject(extent);
    }

    public static void readExtent() throws ClassNotFoundException {
        try {
            extent = (List<Repair>) new ObjectInputStream(new FileInputStream(fileName)).readObject();
        } catch (EOFException e) {
            extent = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCarPart(CarPart carPart){
        if(!carPartList.contains(carPart)) {
            carPartList.add(carPart);
            carPart.addRepair(this);
        }
    }

    public void removeCarPart(CarPart carPart){
        if(carPartList.contains(carPart)) {
            carPartList.remove(carPart);
            carPart.removeRepair(this);
        }
    }

    public static String getFileName(){
        return fileName;
    }

    public static void setFileName(String file){
        fileName = file;
    }

}
