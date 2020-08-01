package Login;

import Repairs.Mechanic;
import Sponsorships.Sponsor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Worker extends Person implements ILogable, Serializable {
    private String login;
    private String password;
    private static String fileName;
    private static List<Worker> extent;

    //TODO
    public void registerNewTournament(){

    }

    public static boolean userExists(String login){
        List<Worker> workers = getExtent();
        if(workers!=null)
        for(Worker worker : workers) {
            if (worker.login.equals(login))
                return true;
        }
        return false;
    }

    Worker(String login, String password, String name, String surname){
        this.login = login;
        this.password = password;
        this.setName(name);
        this.setSurname(surname);
        extent.add(this);
    }

    public static int addUser(String login, String password, String name, String surname){
        boolean workerExists = userExists(login);
        boolean sponsorExists = Sponsor.userExists(login);
        boolean mechanicExists = Mechanic.userExists(login);
        if(!(workerExists||sponsorExists||mechanicExists))
            new Worker(login,password,name,surname);
        else
            return 1;
        return 0;
    }

    public static int login(String login, String password){
        if(login.equals("")||password.equals(""))
            return 2;
        List<Worker> workers = getExtent();
        for(Worker worker : workers) {
            if (worker.login.equals(login)&&worker.password.equals(password))
                return 0;
        }
        return 1;
    }



    public static List<Worker> getExtent(){
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
            extent = (List<Worker>) new ObjectInputStream(new FileInputStream(fileName)).readObject();
        } catch (EOFException e) {
            extent = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Worker)) return false;
        if (!super.equals(o)) return false;
        Worker worker = (Worker) o;
        return Objects.equals(login, worker.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login);
    }
}
