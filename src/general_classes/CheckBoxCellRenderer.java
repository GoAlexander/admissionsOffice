package general_classes;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CheckBoxCellRenderer implements TableCellRenderer {
	JComboBox combo;

	public CheckBoxCellRenderer(JComboBox comboBox) {
		this.combo = new JComboBox();
		for (int i = 0; i < comboBox.getItemCount(); i++) {
			combo.addItem(comboBox.getItemAt(i));
		}
	}

	public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		combo.setSelectedItem(value);
		return combo;
	}
}
