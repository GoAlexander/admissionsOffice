package tab_catalogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditMenuListener implements ActionListener {

	private String table;
	private String title;

	public EditMenuListener(String table, String title) {
		this.table = table;
		this.title = title;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			EditCatalogElem window = new EditCatalogElem(table, title);
			window.setVisible(true);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}