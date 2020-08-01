package Repairs;

import Login.ILogable;
import Login.Person;
import Login.Worker;
import Sponsorships.Sponsor;
import Races.Team;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Mechanic extends Person implements ILogable, Serializable {
    private String login;
    private String password;
    private String workExperience;
    private static String fileName;
    private static List<Mechanic> extent;
    private List<Repair> repairs;
    private Team team;

    public Team getTeam() {
        return team;
    }

    //TODO
    public void registerNewRepair(){

    }

    //TODO
    public void addNewPart(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mechanic)) return false;
        Mechanic mechanic = (Mechanic) o;
        return Objects.equals(getName(), mechanic.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    public static List<Mechanic> getExtent(){
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
            extent = (List<Mechanic>) new ObjectInputStream(new FileInputStream(fileName)).readObject();
        } catch (EOFException e) {
            extent = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean userExists(String login){
        List<Mechanic> mechanics = getExtent();
        if(mechanics!=null)
        for(Mechanic mechanic : mechanics) {
            if (mechanic.login.equals(login))
                return true;
        }
        return false;
    }

    public void addRepair(Repair repair){
        if(repair!=null)
            if(!repairs.contains(repair)){
                repairs.add(repair);
                repair.setMechanic(this);
            }
    }

    public void removeRepair(Repair repair){
        if(repair!=null)
            if(repairs.contains(repair)){
                repairs.remove(repair);
                repair.destroy();
            }
    }

    public List<Repair> getRepairs() {
        return repairs;
    }

    Mechanic(Team team, String login, String password, String name, String surname, String workExperience){
        this.login = login;
        this.password = password;
        this.setName(name);
        this.setSurname(surname);
        this.workExperience = workExperience;
        this.team = team;
        repairs = new ArrayList<>();
        extent.add(this);
        try {
            team.addMember(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int addUser(Team team, String login, String password, String name, String surname, String workExperience){
        boolean workerExists = Worker.userExists(login);
        boolean sponsorExists = Sponsor.userExists(login);
        boolean mechanicExists = userExists(login);
        if(!(workerExists||sponsorExists||mechanicExists))
            new Mechanic(team, login, password, name, surname, workExperience);
        else
            return 1;
        return 0;
    }

    public static Mechanic login(String login, String password) throws Exception {
        if(login.equals("")||password.equals(""))
            throw new Exception("Nie podano wszystkich parametrów do logowania");
        List<Mechanic> mechanics = getExtent();
        for(Mechanic mechanic : mechanics) {
            if (mechanic.login.equals(login)&&mechanic.password.equals(password))
                return mechanic;
        }
        throw new Exception("<html>Sprawdź poprawność danych<br/>Nie znałeziono użytkownika z podanym hasłem</html>");
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
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
