package zad1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
public class Service {
	static private Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	String country;
	String city;
	String currency;
	String weather;
	String cur;
	public Service(String kraj) {
		country=kraj;
	}
	public String getWeather(String miasto) {
		city=miasto;
		String json="";
		String urlstr = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID=a1683cbffe399188b01f1c6ad050dc0c";
		try {
			URL url = new URL(urlstr);
			BufferedReader br = new BufferedReader (new InputStreamReader(url.openStream()));
			String inputLine;
	        while ((inputLine = br.readLine()) != null)
	            json+=inputLine;
			br.close();
		} catch (IOException e) { 
			System.out.println(e.toString());
		}
		City city = GSON.fromJson(json, City.class);
		
        String maininfo;
		maininfo=miasto+'\n'+"Sky: "+city.getSky();
		maininfo += '\n'+"Temperature: "+(BigDecimal.valueOf(city.getMain().get("temp")- 273.15)).setScale(2, RoundingMode.HALF_UP)+'\n'+"Humidity: "+city.getMain().get("humidity");
		maininfo += '\n'+"Wind speed: "+city.getWind();
		this.weather=maininfo;
		return json;
	}

	public Double getRateFor(String waluta) {
		currency=waluta;
		cur=waluta;
		Map<String,String> codes = new HashMap<String,String>();
		for(String iso: Locale.getISOCountries()) {
			Locale country = new Locale("",iso);
			codes.put(country.getDisplayCountry(), iso);
		}
		Currency c = Currency.getInstance(new Locale("",codes.get(country)));//mam w ruskim jezyku Zamienit
		String json="";
		this.currency=c.getCurrencyCode();
		String urlstr = "https://api.exchangeratesapi.io/latest?symbols="+waluta+"&base="+c.getCurrencyCode();
		try {
			URL url = new URL(urlstr);
			BufferedReader br = new BufferedReader (new InputStreamReader(url.openStream()));
			String inputLine;
	        while ((inputLine = br.readLine()) != null)
	            json+=inputLine;
			br.close();
			//System.out.println(json);
		} catch (IOException e) { 
			System.out.println(e.toString());
		}
		Map<String,Map<String,Double>> jso = GSON.fromJson(json, Map.class);
		return Double.valueOf(jso.get("rates").get("USD"));
	}

	public Double getNBPRate() {
		double ret=0;
		String content = null;
		URLConnection connection = null;
		try {
		  connection =  new URL("http://www.nbp.pl/kursy/kursya.html").openConnection();
		  Scanner scanner = new Scanner(connection.getInputStream());
		  scanner.useDelimiter("\\Z");
		  content = scanner.next();
		  scanner.close();
		  ret=searchInTable(content);
		  if(ret==1) {
			  connection =  new URL("http://www.nbp.pl/kursy/kursyb.html").openConnection();
			  scanner = new Scanner(connection.getInputStream());
			  scanner.useDelimiter("\\Z");
			  content = scanner.next();
			  scanner.close();
			  ret=searchInTable(content);
		  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	public String getCity() {
		return city;
	}
	public Double searchInTable(String doc) {
		double ret=1;
		if(currency.equals("PLN")) return ret;
			String[] lines = doc.split(System.getProperty("line.separator"));
	        for(int i=0;i<lines.length;i++) {
	        	if(lines[i].contains(currency)){
	        		if(lines[i].contains("100")) {
	        			int j = lines[i+1].indexOf("right")+7;//db-mssql
	        			String liczba = lines[i+1].substring(j, j+6);
	        			liczba = liczba.replace(',', '.');
	        			ret = Double.parseDouble(liczba)/100;
	        		}else {
	        			int j = lines[i+1].indexOf("right")+7;//db-mssql
	        			String liczba = lines[i+1].substring(j, j+6);
	        			liczba = liczba.replace(',', '.');
	        			ret = Double.parseDouble(liczba);
	        		}
	        		break;
	        	}
	        }
		return ret;
	}
}
