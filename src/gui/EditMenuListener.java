package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditMenuListener implements ActionListener {

	private String table;

	public EditMenuListener(String table) {
		this.table = table;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			EditCatalogElem window = new EditCatalogElem(table);
			window.setVisible(true);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}