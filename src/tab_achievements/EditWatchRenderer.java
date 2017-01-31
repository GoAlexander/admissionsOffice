package tab_achievements;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class EditWatchRenderer extends DefaultTableCellRenderer {
	private EditWatchAchievmentPanel acceptRejectPane;

	public EditWatchRenderer(JTable table) {
		acceptRejectPane = new EditWatchAchievmentPanel(table);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (isSelected) {
			acceptRejectPane.setBackground(table.getSelectionBackground());
		} else {
			acceptRejectPane.setBackground(table.getBackground());
		}
		return acceptRejectPane;
	}
}
