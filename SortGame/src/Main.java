import javax.swing.*;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public 
	class Main 
	extends JFrame {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(
			()->new Main()
		);
	}
	Main(){
		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		//==========================//Parking\\====================================================================================================================
		MyTableModel tm = new MyTableModel();
		JTable parking = new JTable(tm);
		parking.setDefaultRenderer(Samochod.class, new MyRenderer());
		parking.setRowHeight(100);
		center.add(parking,BorderLayout.CENTER);
		//==========================//Ilość zmian\\====================================================================================================================
		JPanel jpiz = new JPanel(new BorderLayout());
		JLabel jliz = new JLabel();
		jliz.setText("Ilość zmian: "+tm.ilosczmian);
		center.add(jpiz,BorderLayout.NORTH);
		jpiz.add(jliz,BorderLayout.EAST);
		//==========================//Statek w górnej części\\====================================================================================================================
		JPanel statek = new JPanel();
		JLabel statekimage = new JLabel();
		JLabel statekpokl = new JLabel();
		JLabel stateksam = new JLabel();
		JPanel statekinfo = new JPanel();
		statekimage.setIcon(new ImageIcon("C:\\Users\\asus\\Desktop\\Owoce\\statek.jpg"));
		statekpokl.setText("Ilość pokładów: "+tm.col);
		stateksam.setText("Ilość samochodów na pokładzie: "+tm.rows);
		statek.add(statekimage);
		statekinfo.setLayout(new GridLayout(2,1));
		statekinfo.add(statekpokl,1,0);
		statekinfo.add(stateksam,2,0);
		statek.add(statekinfo,BorderLayout.AFTER_LINE_ENDS);
		this.add(statek,BorderLayout.PAGE_START);
		//==========================//Przycisk\\====================================================================================================================
		JPanel jpforjb = new JPanel();
		JButton jb = new JButton("Załadować");
		JLabel napis_info = new JLabel("Press \"b\" to cancel selected one");
		jpforjb.add(jb);
		jpforjb.add(napis_info);
		this.add(jpforjb, BorderLayout.PAGE_END);
		//==========================//Tabela wyników\\====================================================================================================================
		WynikiModel wm = new WynikiModel();
		JTable wyniki = new JTable(wm);
		JPanel wyn = new JPanel(new BorderLayout());
		wyniki.setPreferredSize(new Dimension(200,0));
		wyn.add(wyniki,BorderLayout.CENTER);
		wyn.add(wyniki.getTableHeader(),BorderLayout.NORTH);
		this.add(wyn,BorderLayout.WEST);
		//==========================//Listenery\\====================================================================================================================
		parking.addMouseListener( //Parking listener
					new MouseAdapter(){
						int poprzedniRow=0;
						int poprzedniCol=0;
						public void mouseClicked(MouseEvent e) {
							if(parking.rowAtPoint(e.getPoint())!=-1&&e.getButton()==MouseEvent.BUTTON1) { /////// Bo po klinkięciu tam gdzie nie ma samochodu sypią blędy
								if(!tm.isSelected) {
									poprzedniRow = parking.getSelectedRow();
									poprzedniCol = parking.getSelectedColumn();
									Object convert = parking.getValueAt(poprzedniRow, poprzedniCol); // zeby nie trzeba było pisać (Samochod)wrt
									tm.select(poprzedniRow, poprzedniCol, convert);
								}else{
									int rowInd = parking.getSelectedRow();
									int colInd = parking.getSelectedColumn();
									parking.setValueAt(parking.getValueAt(rowInd, colInd), poprzedniRow, poprzedniCol);
									parking.setValueAt(tm.wrt, rowInd, colInd);
									tm.zamien();
								}
								jliz.setText("Ilość zmian: "+tm.ilosczmian);
							}
						}
					}
				);
		//Klawiatura "B"
		parking.addKeyListener(
				new KeyAdapter() {	     
				    public void keyPressed(KeyEvent e) {
				         if(e.getExtendedKeyCode()==KeyEvent.VK_B) {
				        	 tm.Bpressed();
				         }
				    }
				}
				);
		//Prycisk
		jb.addActionListener(
				(e)->{
					boolean ifRED=true;
					for(int i=0;i<parking.getColumnCount();i++) {
						if(((Samochod)(parking.getValueAt(parking.getRowCount()-1, i))).color=="Blue")
							ifRED=false;
					}
					if(ifRED) {
						if(tm.sprawdz()||tm.pierwszynacisk) {
						String imie = JOptionPane.showInputDialog(
								"Podaj swoje imię: ", 
								null
							);
						wm.add(new PlayerResult(imie,tm.ilosczmian));
					}else {
						JOptionPane.showMessageDialog(this, "Niestety nie zdążyłeś posortować lepiej, niż ostatni 10 playerów");
					}
						tm.zmieszaj();
						jliz.setText("Ilość zmian: "+tm.ilosczmian);
					}else {
						JOptionPane.showMessageDialog(this, "Należy posortować samochody zgodnie z polityką firmy");
					}
				}
			);
		//==============================================================================================================================================
		this.add(center, BorderLayout.CENTER);
		this.setPreferredSize(
				new Dimension(1000, 800)
			);		
		this.setLocation(450, 100);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}