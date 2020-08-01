package Races;

import Login.Person;
import Repairs.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Driver extends Person implements Serializable {
    //Minimum 1
    private List<String> specializations = new ArrayList<>();
    private static String fileName;
    private static List<Driver> extent;
    private Team team;

    private List<Car> cars = new ArrayList<>();

    public List<String> getSpecializations() {
        return specializations;
    }

    public Team getTeam() {
        return team;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void addCar(Car car){
        if(!cars.contains(car)) {
            cars.add(car);
            car.addDriver(this);
        }
    }

    public void removeCar(Car car){
        if(cars.contains(car)) {
            cars.remove(car);
            car.removeDriver(this);
        }
    }

    public Team getCar() {
        return team;
    }

    private Driver(Team team,List<String> specializations,String name, String surname)
    {
        this.specializations = specializations;
        this.setName(name);
        this.setSurname(surname);
        this.team = team;
        extent.add(this);
        try {
            team.addMember(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Driver> getExtent(){
        return extent;
    }

    public static String getFileName(){
        return fileName;
    }

    public static void setFileName(String file){
        fileName = file;
    }

    public static void writeExtent() throws IOException {
        new ObjectOutputStream(new FileOutputStream(fileName)).writeObject(extent);
    }

    public static void readExtent() throws ClassNotFoundException {
        try {
            extent = (List<Driver>) new ObjectInputStream(new FileInputStream(fileName)).readObject();
        } catch (EOFException e) {
            extent = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
