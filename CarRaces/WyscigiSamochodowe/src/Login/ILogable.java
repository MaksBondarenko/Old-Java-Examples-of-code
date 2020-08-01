package Login;

public interface ILogable {
    //0 - ok, 1 - dane niepoprawne, 2 - nie podano wszystkich parametrow
    //bez spacji
    String getLogin();
    void setLogin(String login);
    String getPassword();
    void setPassword(String login);
}
