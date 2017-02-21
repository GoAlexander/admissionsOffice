﻿package tab_competitive_groups;

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
import backend.ModelDBConnection;

public class CompetitiveGroupsPanel extends JPanel {
	private String currentAbit;

	private JButton addNewGroupButton;
	JPanel compGroupsPanel;

	public CompetitiveGroupsPanel(String currentAbit) {
		this.currentAbit = currentAbit;

		compGroupsPanel = new JPanel();
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		compGroupsPanel.setLayout(new BoxLayout(compGroupsPanel, BoxLayout.PAGE_AXIS));

		JScrollPane pane = new JScrollPane(compGroupsPanel);
		setValues(currentAbit);

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

	public void setValues(String aid) {
		currentAbit = aid;
		compGroupsPanel.removeAll();
		System.out.println("CA = " + currentAbit);
		String[][] allAbiturientsGroups = ModelDBConnection.getAllCompetitiveGroupsByAbiturientId(currentAbit);
		for (int i = 0; i < (allAbiturientsGroups!= null ? allAbiturientsGroups.length : 0); i++) {
			compGroupsPanel.add(new SimpleCompetitiveGroupPanel(allAbiturientsGroups[i], this));
			compGroupsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		}
		compGroupsPanel.updateUI();
	}

	private void addNewGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			AddNewCompetitiveGroup openCompetGroup = new AddNewCompetitiveGroup(this);
			String[] data = {currentAbit};
			openCompetGroup.setValues(data);
			openCompetGroup.setVisible(true);
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}
}
