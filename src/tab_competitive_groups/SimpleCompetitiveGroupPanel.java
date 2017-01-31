package tab_competitive_groups;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SimpleCompetitiveGroupPanel extends JPanel {
	private String currentAbit;
	private Dimension	dimText = new Dimension(170, 25), 
						dimRigidArea = new Dimension(10, 0);

	private JTextField textDir, textSpec, textGroup, textBall;

	public SimpleCompetitiveGroupPanel(String[] data) {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEtchedBorder());

		JPanel compGroupPanel = new JPanel();
		compGroupPanel.setLayout(new BoxLayout(compGroupPanel, BoxLayout.PAGE_AXIS));
		this.add(compGroupPanel, BorderLayout.CENTER);

		JPanel dirSpecPanel = new JPanel();
		dirSpecPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel dirLabel = new JLabel("Направление");
		dirSpecPanel.add(dirLabel);
		textDir = new JTextField();
		textDir.setPreferredSize(dimText);
		dirSpecPanel.add(textDir);
		dirSpecPanel.add(Box.createRigidArea(dimRigidArea));
		JLabel specLabel = new JLabel("Специальность");
		dirSpecPanel.add(specLabel);
		textSpec = new JTextField();
		textSpec.setPreferredSize(dimText);
		dirSpecPanel.add(textSpec);
		compGroupPanel.add(dirSpecPanel);

		JPanel groupBallPanel = new JPanel();
		groupBallPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel groupLabel = new JLabel("Конкурсная группа");
		groupBallPanel.add(groupLabel);
		textGroup = new JTextField();
		textGroup.setPreferredSize(dimText);
		groupBallPanel.add(textGroup);
		groupBallPanel.add(Box.createRigidArea(dimRigidArea));
		JLabel ballLabel = new JLabel("Конкурсный балл");
		groupBallPanel.add(ballLabel);
		textBall = new JTextField();
		textBall.setPreferredSize(dimText);
		groupBallPanel.add(textBall);
		compGroupPanel.add(groupBallPanel);

		JButton showInfo = new JButton("+");
		showInfo.setForeground(Color.BLACK);
		showInfo.setFont(new Font("Tahoma", Font.BOLD, 20));
		this.add(showInfo, BorderLayout.LINE_END);

		JPanel allInfoPanel = new JPanel();
		// allInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(allInfoPanel, BorderLayout.PAGE_END);
		//this.setValues(data);
		showInfo.addActionListener(new CompetitiveGroupPanelListener(allInfoPanel, data));
	}

	public void setValues(String[] values) {
		currentAbit = values[0];
		textDir.setText(values[1]);
		textSpec.setText(values[2]);
		textGroup.setText(values[3]);
		textBall.setText(values[4]);
	}
}
