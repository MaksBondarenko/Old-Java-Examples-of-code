package Races;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Race implements Serializable {
    private Date startTime;
    //private double bestTime;
    private int maxParticipantNumber;
    private Tournament tournament;
    private List<TeamRace> raceResults = new ArrayList<>();

    public List<TeamRace> getRaceResults() {
        return raceResults;
    }

    public void addRaceResult(TeamRace raceResult){
        if(raceResult!=null)
            if(!(raceResults.size()==maxParticipantNumber)) {
                if (!raceResults.contains(raceResult)) {
                    raceResults.add(raceResult);
                    raceResult.setRace(this);
                }
            } else{
                raceResult.destroy();
            }
    }

    public void removeRaceResult(TeamRace raceResult){
        if(raceResult!=null)
            if(raceResults.contains(raceResult)){
                raceResults.remove(raceResult);
                raceResult.destroy();
            }
    }

    public Race(Tournament tournament,Date startTime,int maxParticipantNumber)
    {
        this.startTime = startTime;
        raceResults = new ArrayList<>();
        this.maxParticipantNumber= maxParticipantNumber;
        this.tournament = tournament;
        extent.add(this);
        try {
            tournament.addRace(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String fileName;
    private static List<Race> extent;

    public static List<Race> getExtent(){
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
            extent = (List<Race>) new ObjectInputStream(new FileInputStream(fileName)).readObject();
        } catch (EOFException e) {
            extent = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getBestTime(){
        double time = TeamRace.getExtent().get(0).getTime();
        for(int i = 0;i<TeamRace.getExtent().size();i++){
            if(TeamRace.getExtent().get(i).getTime()<time)
                time = TeamRace.getExtent().get(i).getTime();
        }
        return time;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getMaxParticipantNumber() {
        return maxParticipantNumber;
    }

    public void setMaxParticipantNumber(int maxParticipantNumber) {
        this.maxParticipantNumber = maxParticipantNumber;
    }
}
