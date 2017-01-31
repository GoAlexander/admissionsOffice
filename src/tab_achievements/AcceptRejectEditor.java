package tab_achievements;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;

public class AcceptRejectEditor extends AbstractCellEditor implements TableCellEditor {
	private EditWatchAchievmentPanel acceptRejectPane;

	public AcceptRejectEditor(JTable table) {
		acceptRejectPane = new EditWatchAchievmentPanel(table);
		/*acceptRejectPane.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						stopCellEditing();
					}
				});
			}
		});*/
	}

	@Override
	public Object getCellEditorValue() {
		return acceptRejectPane.getState();
	}

	@Override
	public boolean isCellEditable(EventObject e) {
		return true;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
			int column) {
		if (isSelected) {
			acceptRejectPane.setBackground(table.getSelectionBackground());
		} else {
			acceptRejectPane.setBackground(table.getBackground());
		}
		return acceptRejectPane;
	}
}
