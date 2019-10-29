import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class MyRenderer 
						extends DefaultTableCellRenderer{

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		JLabel jl = (JLabel)c;
		jl.setText(null);
		Samochod sam = (Samochod)value;
		jl.setIcon(
				sam.icon
			);
		setHorizontalAlignment(SwingConstants.CENTER);
		return this;
	}
}
