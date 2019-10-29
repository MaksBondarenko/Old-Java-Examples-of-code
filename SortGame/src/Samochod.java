import java.net.URL;

import javax.swing.*;

public class Samochod {
	String color;
	ImageIcon icon;
	ImageIcon selectedicon;
	Samochod(String color){
		this.color=color;
		if(color=="Blue") {
			URL blueURL = getClass().getResource("/images/blue.jpg");
			URL blueselURL = getClass().getResource("/images/bluesel.jpg");
			this.icon=new ImageIcon(blueURL);
			this.selectedicon=new ImageIcon(blueselURL);
		}
		if(color=="Red") {
			URL redURL = getClass().getResource("/images/red.jpg");
			URL redselURL = getClass().getResource("/images/redsel.jpg");
			this.icon=new ImageIcon(redURL);
			this.selectedicon=new ImageIcon(redselURL);
		}
	}
	void select() {
		ImageIcon wrt=this.icon;
		this.icon=this.selectedicon;
		this.selectedicon=wrt;
	}
}
