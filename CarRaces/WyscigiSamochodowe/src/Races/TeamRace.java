package Races;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TeamRace implements Serializable {
    private int position;
    private double time;
    private boolean isDisqualified;

    private static String fileName;
    private static List<TeamRace> extent;

    private Team team;
    private Race race;

    public Team getTeam() {
        return team;
    }

    public Race getRace() {
        return race;
    }

    void destroy(){
        if(team.getRaceResults().contains(this))
            team.removeRaceResult(this);
        if(race.getRaceResults().contains(this))
            race.removeRaceResult(this);
    }

    public TeamRace(double time, boolean isDisqualified, Team team, Race race){
        this.time = time;
        this.isDisqualified = isDisqualified;
        this.race=race;
        this.team=team;
        setRace(race);
        setTeam(team);
        extent.add(this);
    }

    void setRace(Race race) {
        if(race!=null) {
            //destroy();
            this.race = race;
            team.addRaceResult(this);
            race.addRaceResult(this);
        }
    }

    void setTeam(Team team) {
        if(team!=null) {
            //destroy();
            this.team = team;
            race.addRaceResult(this);
            team.addRaceResult(this);
        }
    }



    public static List<TeamRace> getExtent(){
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
            extent = (List<TeamRace>) new ObjectInputStream(new FileInputStream(fileName)).readObject();
        } catch (EOFException e) {
            extent = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getPosition() {
        int pos = 1;
        for(int i = 0;i<extent.size();i++){
            if(extent.get(i).getTime()<time)
                pos++;
        }
        return pos;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public boolean isDisqualified() {
        return isDisqualified;
    }

    public void setDisqualified(boolean disqualified) {
        isDisqualified = disqualified;
    }
}
