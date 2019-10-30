/**
 *
 *  @author Bondarenko Maksym S16748
 *
 */

package zad1;


public class Main {

  public static void main(String[] args) {
	  Server serv = new Server();
	  Client c1 = new Client(serv);
	  Client c2 = new Client(serv);
  }
}
