/**
 *
 *  @author Bondarenko Maksym S16748
 *
 */

package zad1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application{
	static String city;
	static String weather;
	static String rate1Gui;
	static String rate2Gui;
	public static void main(String[] args) {
	    Service s = new Service("Польша");
	    String weatherJson = s.getWeather("Warsaw");
	    Double rate1 = s.getRateFor("USD");
	    Double rate2 = s.getNBPRate();
	    // ...
	    // część uruchamiająca GUI
	    city=s.getCity();
	    weather=s.weather;
	    rate1Gui="1 "+s.currency+" = "+rate1+" "+s.cur;
	    rate2Gui="1 "+s.currency+" = "+rate2+" PLN";
	    launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		String url = "https://en.wikipedia.org/wiki/"+city;
		final WebView browser = new WebView();
	    final WebEngine webEngine = browser.getEngine();
	    webEngine.load(url);
		GridPane root = new GridPane();
	    root.setPadding(new Insets(5));
	    root.getColumnConstraints().add(new ColumnConstraints(1500));
	    Label label = new Label(weather+'\n'+rate1Gui+'\n'+rate2Gui);
	    root.add(browser, 0,1);
	    root.add(label, 0,0);
	    Scene scene = new Scene(root);
	
	    stage.setTitle("Program");
	    stage.setScene(scene);
	    stage.setWidth(1525);
	    stage.setHeight(800);
	
	    stage.show();
	}
}
