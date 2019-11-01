package zad1;


import java.util.Map;

public class City {
	private Map<String,String>[] weather;
	private Map<String,Double> main;
	private Map<String,String> wind;
	
	public String getSky() {
		return weather[0].get("main");
	}
	public Map<String,Double> getMain() {
		return main;
	}
	public String getWind() {
		return wind.get("speed");
	}
}
