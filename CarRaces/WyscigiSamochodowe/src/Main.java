import GUI.Frames;
import Login.ILogable;
import Login.Worker;
import Repairs.Car;
import Repairs.CarPart;
import Repairs.Mechanic;
import Repairs.Repair;
import Sponsorships.Sponsor;
import Races.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args){
        List<ILogable> allUsers = new ArrayList<>();
        List<Worker> workerUsers = new ArrayList<>();
        List<Sponsor> sponsorUsers = new ArrayList<>();
        List<Mechanic> mechanicsUsers = new ArrayList<>();
        initializeCarsAndRepairsFiles();
        readCarsAndRepairs();
        //addCarsAndRepairs();
        writeCarsAndRepairs();
        initializeRacesFiles();
        readRaces();
        //addRaces();
        writeRaces();
        initializeUserFiles();
        readUsers();
        //addUsers();
        writeUsers();
        Frames.createLoginGUI();
        System.out.println("Best time: "+Race.getExtent().get(0).getBestTime());
        System.out.println(TeamRace.getExtent().get(0).getTeam().getName() + ": Position "+ TeamRace.getExtent().get(0).getPosition() + ", Time "+TeamRace.getExtent().get(0).getTime());
        System.out.println(TeamRace.getExtent().get(1).getTeam().getName() + ": Position "+ TeamRace.getExtent().get(1).getPosition() + ", Time "+TeamRace.getExtent().get(1).getTime());
        System.out.println(TeamRace.getExtent().get(2).getTeam().getName() + ": Position "+ TeamRace.getExtent().get(2).getPosition() + ", Time "+TeamRace.getExtent().get(2).getTime());
    }

    static void writeUsers(){
        try {
            Worker.writeExtent();
            Sponsor.writeExtent();
            Mechanic.writeExtent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void writeCarsAndRepairs(){
        try {
            Car.writeExtent();
            CarPart.writeExtent();
            Repair.writeExtent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void writeRaces(){
        try {
            Race.writeExtent();
            Team.writeExtent();
            Driver.writeExtent();
            TeamRace.writeExtent();
            Tournament.writeExtent();
            TeamTournament.writeExtent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void readCarsAndRepairs(){
        try {
            Car.readExtent();
            CarPart.readExtent();
            Repair.readExtent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void readUsers(){
        try {
            Worker.readExtent();
            Sponsor.readExtent();
            Mechanic.readExtent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void readRaces(){
        try {
            Race.readExtent();
            Team.readExtent();
            Driver.readExtent();
            TeamRace.readExtent();
            Tournament.readExtent();
            TeamTournament.readExtent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void initializeCarsAndRepairsFiles(){
        File cars = new File("cars.bin");
        File carParts = new File("carParts.bin");
        File repairs = new File("repairs.bin");
        try {
            if(!cars.exists())
                cars.createNewFile();
            if(!carParts.exists())
                carParts.createNewFile();
            if(!repairs.exists())
                repairs.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Car.setFileName("cars.bin");
        CarPart.setFileName("carParts.bin");
        Repair.setFileName("repairs.bin");
    }
    static void initializeRacesFiles(){
        File drivers = new File("drivers.bin");
        File teams = new File("teams.bin");
        File raceResults = new File("raceResults.bin");
        File races = new File("races.bin");
        File tournaments = new File("tournaments.bin");
        File tournamentResults = new File("tournamentResults.bin");
        try {
            if(!drivers.exists())
                drivers.createNewFile();
            if(!teams.exists())
                teams.createNewFile();
            if(!raceResults.exists())
                raceResults.createNewFile();
            if(!races.exists())
                races.createNewFile();
            if(!tournaments.exists())
                tournaments.createNewFile();
            if(!tournamentResults.exists())
                tournamentResults.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Driver.setFileName("drivers.bin");
        Team.setFileName("teams.bin");
        TeamRace.setFileName("raceResults.bin");
        Race.setFileName("races.bin");
        Tournament.setFileName("tournaments.bin");
        TeamTournament.setFileName("tournamentResults.bin");
    }
    static void addCarsAndRepairs(){
        Car.addCar("BMW","XAE102", 100);
        // Powinno nie udać się
        Car.addCar("BMW","XAE102", 100);
        Car.addCar("Mercedes","XAE103", 100);
        try {
            CarPart.createPart(
                    Car.getExtent().get(0),"Kiermo bmw", 1200, "To jest kiermo"
            );
            CarPart.createPart(
                    Car.getExtent().get(0),"Motor bmw", 22000, "To jest kiermo"
            );
            CarPart.createPart(
                    Car.getExtent().get(1),"Kiermo mers", 2200, "To jest kiermo"
            );
            Car.getExtent().get(1).addPart(CarPart.createPart(
                    Car.getExtent().get(1),"Motor mers", 20000, "To jest motor"
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void addRaces(){
        Tournament tournament = new Tournament(new Date(2020,12,1),new Date(2020,12,31),20);
        Race race = new Race(tournament,new Date(2020,12,1),10);
        new TeamRace(3000, false, new Team("Team1",1), race);
        TeamRace teamRace = new TeamRace(2000, false, new Team("Team2",2), race);
        new TeamRace(1000, false, new Team("Team3",3), race);
    }

    static void addUsers(){
        Mechanic.addUser(Team.getExtent().get(0),"John","123", "John", "Doe", null);
        Mechanic.addUser(Team.getExtent().get(1),"John2","1234", "Johny", "Doddy", null);
        Worker.addUser("Mark","qwe", "Mark", "Miller");
        if(Mechanic.addUser(Team.getExtent().get(2),"Mark","0000", "Mark", "Miller", null)==1)
            System.out.println("User already exists");
    }

    static void initializeUserFiles(){
        File workerLogins = new File("workers.bin");
        File sponsorLogins = new File("sponsors.bin");
        File mechanicLogins = new File("mechanics.bin");
        File teams = new File("teams.bin");
        try {
            if(!workerLogins.exists())
                workerLogins.createNewFile();
            if(!sponsorLogins.exists())
                sponsorLogins.createNewFile();
            if(!mechanicLogins.exists())
                mechanicLogins.createNewFile();
            if(!teams.exists())
                teams.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Worker.setFileName("workers.bin");
        Sponsor.setFileName("sponsors.bin");
        Mechanic.setFileName("mechanics.bin");
        Team.setFileName("teams.bin");
    }


}
