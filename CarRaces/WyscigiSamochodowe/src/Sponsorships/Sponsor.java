package Sponsorships;

import Login.ILogable;
import Login.Worker;
import Repairs.Mechanic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sponsor implements ILogable, Serializable{
    private String login;
    private String password;
    private String name;
    private static String fileName;
    private static List<Sponsor> extent;
    private List<SponsorTournament> sponsorships = new ArrayList<>();

    //TODO
    public void addSponsorship(){

    }

    public List<SponsorTournament> getSponsorships() {
        return sponsorships;
    }

    public Sponsor(String login, String password, String name, List<SponsorTournament> sponsorships) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.sponsorships = new ArrayList<>();
        for(SponsorTournament sponsorship : sponsorships)
            addSponsorship(sponsorship);
        extent.add(this);
    }

    public void addSponsorship(SponsorTournament sponsorship){
        if(sponsorship!=null)
            if(!sponsorships.contains(sponsorship)){
                sponsorships.add(sponsorship);
                sponsorship.setSponsor(this);
            }
    }

    public void removeSponsorship(SponsorTournament sponsorship){
        if(sponsorship!=null)
            if(sponsorships.contains(sponsorship)){
                sponsorships.remove(sponsorship);
                sponsorship.destroy();
            }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sponsor)) return false;
        Sponsor sponsor = (Sponsor) o;
        return Objects.equals(login, sponsor.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    public static List<Sponsor> getExtent(){
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
            extent = (List<Sponsor>) new ObjectInputStream(new FileInputStream(fileName)).readObject();
        } catch (EOFException e) {
            extent = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean userExists(String login){
        List<Sponsor> sponsors = getExtent();
        if(sponsors!=null)
        for(Sponsor sponsor : sponsors) {
            if (sponsor.login.equals(login))
                return true;
        }
        return false;
    }

    Sponsor(String login, String password, String name){
        this.login = login;
        this.password = password;
        this.name = name;
        extent.add(this);
    }

    public static int addUser(String login, String password, String name){
        boolean workerExists = Worker.userExists(login);
        boolean sponsorExists = userExists(login);
        boolean mechanicExists = Mechanic.userExists(login);
        if(!(workerExists||sponsorExists||mechanicExists)){
            new Sponsor(login,password,name);
        }
        else
            return 1;
        return 0;
    }

    public static int login(String login, String password){
        if(login.equals("")||password.equals(""))
            return 2;
        List<Sponsor> sponsors = getExtent();
        for(Sponsor sponsor : sponsors) {
            if (sponsor.login.equals(login)&&sponsor.password.equals(password))
                return 0;
        }
        return 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
