package Sponsorships;

import Races.Tournament;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SponsorTournament implements Serializable {
    private double investment;
    private List<String> wishes;
    private Sponsor sponsor;
    private Tournament tournament;
    private static String fileName;
    private static List<SponsorTournament> extent;

    public static List<SponsorTournament> getExtent(){
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
            extent = (List<SponsorTournament>) new ObjectInputStream(new FileInputStream(fileName)).readObject();
        } catch (EOFException e) {
            extent = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void destroy(){
        if(sponsor.getSponsorships().contains(this))
            sponsor.removeSponsorship(this);
        if(tournament.getSponsorships().contains(this))
            tournament.removeSponsorship(this);
    }

    public SponsorTournament(double investment, List<String> wishes, Sponsor sponsor, Tournament tournament){
        this.investment = investment;
        this.wishes = wishes;
        this.sponsor = sponsor;
        this.tournament = tournament;
        setTournament(tournament);
        setSponsor(sponsor);
    }

    void setSponsor(Sponsor sponsor) {
        if(sponsor!=null) {
            //destroy();
            this.sponsor = sponsor;
            tournament.addSponsorship(this);
            sponsor.addSponsorship(this);
        }
    }

    public void setTournament(Tournament tournament) {
        if(tournament!=null) {
            //destroy();
            this.tournament = tournament;
            sponsor.addSponsorship(this);
            tournament.addSponsorship(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SponsorTournament)) return false;
        SponsorTournament that = (SponsorTournament) o;
        return Double.compare(that.investment, investment) == 0 &&
                Objects.equals(wishes, that.wishes) &&
                Objects.equals(sponsor, that.sponsor) &&
                Objects.equals(tournament, that.tournament);
    }

    @Override
    public int hashCode() {
        return Objects.hash(investment, wishes, sponsor, tournament);
    }

    public double getInvestment() {
        return investment;
    }

    public void setInvestment(double investment) {
        this.investment = investment;
    }

    public List<String> getWishes() {
        return wishes;
    }

    public void setWishes(List<String> wishes) {
        this.wishes = wishes;
    }
}
