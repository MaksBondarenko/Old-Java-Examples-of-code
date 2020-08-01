package Races;

import Login.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {
    private String name;
    private int number;

    private static String fileName;
    private static List<Team> extent;

    private List<Person> members;
    private List<Person> allMembers;
    private List<TeamRace> raceResults = new ArrayList<>();
    private List<TeamTournament> tournamentResults = new ArrayList<>();

    public List<TeamTournament> getTournamentResults() {
        return tournamentResults;
    }

    public void addTournamentResult(TeamTournament raceResult){
        if(raceResult!=null)
            if(!tournamentResults.contains(raceResult)){
                tournamentResults.add(raceResult);
                raceResult.setTeam(this);
            }
    }

    public void removeTournamentResult(TeamTournament raceResult){
        if(raceResult!=null)
            if(tournamentResults.contains(raceResult)){
                tournamentResults.remove(raceResult);
                raceResult.destroy();
            }
    }

    public List<TeamRace> getRaceResults() {
        return raceResults;
    }

    public void addRaceResult(TeamRace raceResult){
        if(raceResult!=null)
            if(!raceResults.contains(raceResult)){
                raceResults.add(raceResult);
                raceResult.setTeam(this);
            }
    }

    public void removeRaceResult(TeamRace raceResult){
        if(raceResult!=null)
            if(raceResults.contains(raceResult)){
                raceResults.remove(raceResult);
                raceResult.destroy();
            }
    }

    public Team(String name, int number){
        this.name = name;
        this.number = number;
        members = new ArrayList<>();
        allMembers = new ArrayList<>();
        raceResults = new ArrayList<>();
        extent.add(this);
    }

    public void addMember(Person part) throws Exception {
        if(!members.contains(part)) {
            // Check if the part has been already added to any wholes
            if(allMembers.contains(part)) {
                throw new Exception("The part is already connected with a whole!");
            }
            members.add(part);
            // Store on the list of all parts
            allMembers.add(part);
        }
    }

    public static List<Team> getExtent(){
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
            extent = (List<Team>) new ObjectInputStream(new FileInputStream(fileName)).readObject();
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
