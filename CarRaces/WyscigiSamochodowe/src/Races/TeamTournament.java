package Races;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TeamTournament implements Serializable {
    private int position;
    private static String fileName;
    private static List<TeamTournament> extent;
    private Team team;
    private Tournament tournament;

    void destroy(){
        if(team.getTournamentResults().contains(this))
            team.removeTournamentResult(this);
        if(tournament.getTournamentResults().contains(this))
            tournament.removeTournamentResult(this);
    }

    public TeamTournament(int position, Team team, Tournament tournament){
        this.position = position;
        this.team = team;
        this.tournament=tournament;
        setTournament(tournament);
        setTeam(team);
        extent.add(this);
    }

    void setTournament(Tournament tournament) {
        if(tournament!=null) {
            //destroy();
            this.tournament = tournament;
            team.addTournamentResult(this);
            tournament.addTournamentResult(this);
        }
    }

    void setTeam(Team team) {
        if(team!=null) {
            //destroy();
            this.team = team;
            tournament.addTournamentResult(this);
            team.addTournamentResult(this);
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }



    public static List<TeamTournament> getExtent(){
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
            extent = (List<TeamTournament>) new ObjectInputStream(new FileInputStream(fileName)).readObject();
        } catch (EOFException e) {
            extent = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
