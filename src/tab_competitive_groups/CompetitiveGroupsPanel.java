package tab_competitive_groups;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import backend.MessageProcessing;

public class CompetitiveGroupsPanel extends JPanel {
	private String currentAbit;
	
	private JButton addNewGroupButton;

	public CompetitiveGroupsPanel(String currentAbit) {
		this.currentAbit = currentAbit;

		JPanel compGroupsPanel = new JPanel();
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		compGroupsPanel.setLayout(new BoxLayout(compGroupsPanel, BoxLayout.PAGE_AXIS));

		JScrollPane pane = new JScrollPane(compGroupsPanel);

		if(currentAbit.equals("")) {
			compGroupsPanel.add(new SimpleCompetitiveGroupPanel(null));
			compGroupsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		} else {
			// !!!!!!!!!!!!!!!!!!!!
			// Заменить на инициализацию для конкретного абитуриента
			int numGroup = 7;
			for (int i = 0; i < numGroup; i++) {
				compGroupsPanel.add(new SimpleCompetitiveGroupPanel(null));
				compGroupsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
			}
		}

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(0, 1));

		addNewGroupButton = new JButton("Добавить новую конкурсную группу");
		addNewGroupButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addNewGroupButtonActionPerformed(evt);
			}
		});

		btnPanel.setMaximumSize(new Dimension(764, 30));
		btnPanel.add(addNewGroupButton);

		this.add(pane);
		this.add(btnPanel);
	}

	private void addNewGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			AddNewCompetitiveGroup openCompetGroup = new AddNewCompetitiveGroup();
			openCompetGroup.setVisible(true);
			System.out.println(this.getSize().getHeight());
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}
}
