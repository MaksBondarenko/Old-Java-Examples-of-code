

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

public class 
	MyTableModel 
		extends AbstractTableModel{
	int rows;
	int col;
	int ilosczmian=0;
	boolean isSelected=false;
	Samochod[][] tab;
	///////
	Samochod wrt;
	int poprzedniRow=0;
	int poprzedniCol=0;
	int[] ilosczmianPlayera=new int[10];
	boolean pierwszynacisk=true;
	int indexPlayera=0;
	public MyTableModel(){
		rows = (int)(Math.random()*3)+2;
		col = (int)(Math.random()*3)+2;
		tab = new Samochod[rows][col];
		//===========//Random blue/red \\================
		int liczba=rows*col;
		int blue = (rows-1)*col;
		int red = col;
		//===========================
		for(int i=0;i<tab.length;i++) 
			for(int j=0;j<tab[i].length;j++) { 
				int wrt=(int)(Math.random()*liczba);
				wrt++;
				if(red>0&&blue>0) {
					if(wrt<=liczba-col) {
						blue--;
						tab[i][j]= new Samochod("Blue");
					}
					if(wrt>liczba-col) {
						red--;
						tab[i][j]= new Samochod("Red");
					}
				}else {
					if(red==0) tab[i][j]= new Samochod("Blue");
					if(blue==0) tab[i][j]= new Samochod("Red");
				}
			}
	}

	@Override
	public int getColumnCount() {
		return col;
	}

	@Override
	public int getRowCount() {
		return rows;
	}
	
	@Override
	public Class<? extends Object> getColumnClass(int c) {
	    Object object = getValueAt(0, c);
	    if(object == null) 
	        return Object.class;
	    if(getValueAt(0, c) instanceof Samochod) {
	        return Samochod.class;
	    } else {
	        return getValueAt(0, c).getClass();
	    }
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex<=rows && columnIndex<=col)
			return tab[rowIndex][columnIndex];
		return "";
	}
	
	@Override
	public void setValueAt(Object wrt,int rowIndex, int columnIndex) {
		tab[rowIndex][columnIndex]=(Samochod)wrt;
	}
	void zmieszaj() {
		int liczba=this.rows*this.col;
		int blue = (this.rows-1)*this.col;
		int red = this.col;
		for(int i=0;i<this.tab.length;i++) 
			for(int j=0;j<this.tab[i].length;j++) { 
				int wrt=(int)(Math.random()*liczba);
				wrt++;
				if(red>0&&blue>0) {
					if(wrt<=liczba-this.col) {
						blue--;
						this.tab[i][j]= new Samochod("Blue");
					}
					if(wrt>liczba-this.col) {
						red--;
						this.tab[i][j]= new Samochod("Red");
					}
				}else {
					if(red==0) this.tab[i][j]= new Samochod("Blue");
					if(blue==0) this.tab[i][j]= new Samochod("Red");
				}
			}
		this.fireTableDataChanged();
		ilosczmianPlayera[indexPlayera]=ilosczmian;
		indexPlayera++;
		if(indexPlayera==10) indexPlayera=0;
		if(pierwszynacisk)
			for(int i=0;i<ilosczmianPlayera.length;i++) {
				ilosczmianPlayera[i]=ilosczmian;
				pierwszynacisk=false;
			}
		this.ilosczmian=0;
	}
	boolean sprawdz(){
		boolean sprawdz=true;
		for(int i=0; i<ilosczmianPlayera.length;i++) {
			if(ilosczmian>=ilosczmianPlayera[i])
				sprawdz = false;
		}
		return sprawdz;
	}
	void Bpressed() { 
		if(this.isSelected) {
			this.isSelected=false;
	   	 	this.wrt.select();
			this.fireTableDataChanged();
		}
	}
	void select(int row,int col, Object convert) {
		this.wrt = (Samochod)convert;
		this.wrt.select();
		this.isSelected=true;
		this.fireTableDataChanged();
	}
	void zamien() {
		this.wrt.select();
		this.ilosczmian++;
		this.isSelected=false;
		this.fireTableDataChanged();
	}
}
