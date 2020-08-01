package Races;


import Sponsorships.SponsorTournament;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

enum Status implements Serializable{Planned, InProgress, Ended, Canceled};

public class Tournament implements Serializable{
    private Date startDate;
    private Date endDate;
    private int maxTeamsNumber;
    private Status status;
    private static String fileName;
    private static List<Tournament> extent;
    private List<SponsorTournament> sponsorships = new ArrayList<>();
    private List<Race> races;
    private List<TeamTournament> tournamentResults = new ArrayList<>();

    public List<TeamTournament> getTournamentResults() {
        return tournamentResults;
    }

    public void addTournamentResult(TeamTournament raceResult){
        if(raceResult!=null)
            if(!(tournamentResults.size()==maxTeamsNumber)) {
                if (!tournamentResults.contains(raceResult)) {
                    tournamentResults.add(raceResult);
                    raceResult.setTournament(this);
                }
            } else{
                raceResult.destroy();
            }
    }

    public void removeTournamentResult(TeamTournament raceResult){
        if(raceResult!=null)
            if(tournamentResults.contains(raceResult)){
                tournamentResults.remove(raceResult);
                raceResult.destroy();
            }
    }

    public List<Race> getRaces() {
        return races;
    }

    public void addRace(Race race) throws Exception {
        if(!races.contains(race)) {
            races.add(race);
        }
    }

    public Tournament(Date startDate, Date endDate, int maxTeamsNumber){
        this.startDate=startDate;
        this.endDate=endDate;
        this.maxTeamsNumber=maxTeamsNumber;
        tournamentResults = new ArrayList<>();
        sponsorships = new ArrayList<>();
        races = new ArrayList<>();
        status = Status.Planned;
        extent.add(this);
    }

    public List<SponsorTournament> getSponsorships() {
        return sponsorships;
    }

    public void addSponsorship(SponsorTournament sponsorship){
        if(sponsorship!=null)
            if(!sponsorships.contains(sponsorship)){
                sponsorships.add(sponsorship);
                sponsorship.setTournament(this);
            }
    }

    public void removeSponsorship(SponsorTournament sponsorship){
        if(sponsorship!=null)
            if(sponsorships.contains(sponsorship)){
                sponsorships.remove(sponsorship);
                sponsorship.destroy();
            }
    }

    public List<Tournament> getTournamentsWithStatus(Status status){
        return getExtent().stream().filter(tournament -> tournament.getStatus().equals(status)).collect(Collectors.toList());
    }

    public static List<Tournament> getExtent(){
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
            extent = (List<Tournament>) new ObjectInputStream(new FileInputStream(fileName)).readObject();
        } catch (EOFException e) {
            extent = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStatus(Status status) throws StatusChangeException {
        if(this.status==Status.Canceled)
            throw new StatusChangeException(1);
        else if(this.status==Status.Ended)
            throw new StatusChangeException(2);
        else if(this.status==Status.InProgress&&status==Status.Canceled)
            throw new StatusChangeException(3);
        else if(this.status==Status.Planned&&status==Status.Ended)
            throw new StatusChangeException(4);
        else
            this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getMaxTeamsNumber() {
        return maxTeamsNumber;
    }

    public void setMaxTeamsNumber(int maxTeamsNumber) {
        this.maxTeamsNumber = maxTeamsNumber;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tournament)) return false;
        Tournament that = (Tournament) o;
        return maxTeamsNumber == that.maxTeamsNumber &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, maxTeamsNumber, status);
    }
}
