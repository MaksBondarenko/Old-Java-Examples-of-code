import javax.swing.*;

public class Samochod {
	String color;
	ImageIcon icon;
	ImageIcon selectedicon;
	Samochod(String color){
		this.color=color;
		if(color=="Blue") {
			this.icon=new ImageIcon("C:\\My Space\\Elcipse\\projects\\SortGame\\src\\images\\blue.jpg");
			this.selectedicon=new ImageIcon("C:\\My Space\\Elcipse\\projects\\SortGame\\src\\images\\bluesel.jpg");
		}
		if(color=="Red") {
			this.icon=new ImageIcon("C:\\My Space\\Elcipse\\projects\\SortGame\\src\\images\\red.jpg");
			this.selectedicon=new ImageIcon("C:\\My Space\\Elcipse\\projects\\SortGame\\src\\images\\redsel.jpg");
		}
	}
	void select() {
		ImageIcon wrt=this.icon;
		this.icon=this.selectedicon;
		this.selectedicon=wrt;
	}
}
