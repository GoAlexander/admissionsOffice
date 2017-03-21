package tab_education;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class SimpleEducationPanel extends JPanel{
	private String currentAbit;
	private Dimension	dimStartRigidArea = new Dimension(50, 0), 
			dimTextDigitInfo = new Dimension(139, 25),
			dimRigidArea = new Dimension(10, 0);

	private JTextField textSeria, textNum, textYear, textSpeciality;
	private JTextArea textIssuedBy;

	public SimpleEducationPanel(String name) {
		this.setBorder(new TitledBorder(null, name, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel digitInfoEducPanel = new JPanel();
		digitInfoEducPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		digitInfoEducPanel.add(Box.createRigidArea(dimStartRigidArea));

		JLabel seriaLabel = new JLabel("Серия");
		digitInfoEducPanel.add(seriaLabel);
		textSeria = new JTextField();
		textSeria.setPreferredSize(dimTextDigitInfo);
		textSeria.setBackground(Color.WHITE);

		digitInfoEducPanel.add(textSeria);
		digitInfoEducPanel.add(Box.createRigidArea(dimRigidArea));

		JLabel numLabel = new JLabel("Номер");
		digitInfoEducPanel.add(numLabel);
		textNum = new JTextField();
		textNum.setPreferredSize(dimTextDigitInfo);
		textNum.setBackground(Color.WHITE);

		digitInfoEducPanel.add(textNum);
		digitInfoEducPanel.add(Box.createRigidArea(dimRigidArea));

		JLabel yearLabel = new JLabel("Год окончания");
		digitInfoEducPanel.add(yearLabel);
		textYear = new JTextField();
		textYear.setPreferredSize(dimTextDigitInfo);
		textYear.setBackground(Color.WHITE);

		digitInfoEducPanel.add(textYear);
		this.add(digitInfoEducPanel);

		JPanel specialityEducPanel = new JPanel();
		specialityEducPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		specialityEducPanel.add(Box.createRigidArea(dimStartRigidArea));

		JLabel specialityLabel = new JLabel("Специальность");
		specialityEducPanel.add(specialityLabel);
		textSpeciality = new JTextField();
		textSpeciality.setPreferredSize(new Dimension(538, 25));
		textSpeciality.setBackground(Color.WHITE);

		specialityEducPanel.add(textSpeciality);
		this.add(specialityEducPanel);

		JPanel issuedByEducPanel = new JPanel();
		issuedByEducPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		issuedByEducPanel.add(Box.createRigidArea(dimStartRigidArea));

		JLabel issuedByLabel = new JLabel("Кем выдан");
		issuedByEducPanel.add(issuedByLabel);
		textIssuedBy = new JTextArea(2, 51);
		textIssuedBy.setBackground(Color.WHITE);
		JScrollPane paneIssuedBy = new JScrollPane(textIssuedBy);
		textIssuedBy.setLineWrap(true);

		issuedByEducPanel.add(paneIssuedBy);
		this.add(issuedByEducPanel);

		this.setEditable(false);
	}

	public void setValues(String[] values) {
		currentAbit = values[0];
		textSeria.setText(values[1]);
		textNum.setText(values[2]);
		textSpeciality.setText(values[3]);
		textIssuedBy.setText(values[4]);
		textYear.setText(values[5]);
	}

	public void setEditable(boolean state) {
		textSeria.setEditable(state);
		textNum.setEditable(state);
		textYear.setEditable(state);
		textSpeciality.setEditable(state);
		textIssuedBy.setEditable(state);
	}

	public String[] getValues() {
		String[] values = new String[6];

		values[0] = currentAbit;
		values[1] = (!textSeria.getText().equals("") ? textSeria.getText() : null);
		values[2] = (!textNum.getText().equals("") ? textNum.getText() : null);
		values[3] = (!textSpeciality.getText().equals("") ? textSpeciality.getText() : null);
		values[4] = (!textIssuedBy.getText().equals("") ? textIssuedBy.getText() : null);
		values[5] = (!textYear.getText().equals("") ? textYear.getText() : null);

		return values;
	}

	public String getCurrentAbit() {
		return currentAbit;
	}
}
