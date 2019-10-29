import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class 
	WynikiModel 
		extends AbstractTableModel{
	
	ArrayList<PlayerResult> gracze;
	
	public WynikiModel(){
		gracze = new ArrayList<>();
		try(FileReader reader = new FileReader("C://Users//asus//Desktop//wyniki.txt"))
        {
            gracze.add(new PlayerResult("",0));
            int c;
            String[] text;
            int i = 0;
            while((c=reader.read())!='\n'){
            	i++;
            	while((c=reader.read())!=-1){
          //          text[i]+=(char)c;
               } 
            } 
        }
        catch(IOException ex){
        }
	}
	
	void add(PlayerResult result) {
		boolean powtarza=false;
		if(result.imie!=null) {
			for(int i=0;i<gracze.size();i++) {
				if(result.imie.equals(gracze.get(i).imie)&&gracze.get(i)!=null) powtarza=true;
			}
			if(powtarza) {
				result.imie = JOptionPane.showInputDialog(
						 "Imię już istnieje, podaj nowe: "
					);
				this.add(result);
				}else {
			if(result.imie.equals(null)||result.imie.equals("")||result.imie.length()==0) {
				result.imie = JOptionPane.showInputDialog(
						 "Imię nie zostało podane: "
					);
				this.add(result);
			}else {
				gracze.add(result);
			 try(FileWriter writer = new FileWriter("C://Users//asus//Desktop//wyniki.txt"))
		        {
		            writer.write(result.imie+" "+result.score);
		            writer.append('\n');  
		            writer.flush();
		        }
		        catch(IOException ex){
		        } }
			this.fireTableDataChanged();
			}
		}
		if (result.imie==null) {
			result.imie = JOptionPane.showInputDialog(
					 "Imię nie zostało podane: "
				);
			this.add(result);
		}
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return gracze.size();
	}

	@Override
	public Object getValueAt(int rowInd, int colInd) {
		PlayerResult pr = (PlayerResult)gracze.get(rowInd);
		switch(colInd) {
		case 0:
			return pr.imie;
		case 1:
			return ""+pr.score;
		}
		return "";
	}
	
	@Override
	public String getColumnName(int column) {
		switch(column) {
		case 0:
			return "Najlepsze graczy";
		case 1:
			return "Score";
		}
		return "";
	}

}
