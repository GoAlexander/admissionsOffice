package tab_competitive_groups;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CompetitiveGroupPanelListener implements ActionListener {
	private boolean isPushed = false;
	private JPanel panel = new JPanel();
	private AddNewCompetitiveGroup openCompetGroup;

	public CompetitiveGroupPanelListener(JPanel panel, String[] data, JPanel parentPanel) {
		this.panel = panel;
		openCompetGroup = new AddNewCompetitiveGroup(parentPanel);
		openCompetGroup.setValues(data);
		panel.add(openCompetGroup.getAddNewCompetGroup());
		panel.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			if (!isPushed) {
				((JButton) arg0.getSource()).setText("-");
				panel.setVisible(true);
				isPushed = true;
			} else {
				((JButton) arg0.getSource()).setText("+");
				panel.setVisible(false);
				isPushed = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
