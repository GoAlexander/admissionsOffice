package tab_address_contacts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import backend.MessageProcessing;
import backend.ModelDBConnection;

public class AddressContactsPanel extends JPanel {
	private String currentAbit;

	private Dimension dimStartRigidArea = new Dimension(50, 0), dimTextDigitInfo = new Dimension(139, 25),
			dimRigidArea = new Dimension(10, 0);

	private JTextField textIndex, textPhone, textEmail;
	private JComboBox comboRegionType, comboPunktType;
	private JTextArea textAdressLiving;
	private JButton editAddressContactsButton, saveAddressContactsButton;

	public AddressContactsPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		JPanel addressPanel = new JPanel();
		addressPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		addressPanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel indexLabel = new JLabel("Индекс");
		addressPanel.add(indexLabel);
		textIndex = new JTextField();
		textIndex.setPreferredSize(dimTextDigitInfo);
		textIndex.setBackground(Color.WHITE);

		addressPanel.add(textIndex);
		addressPanel.add(Box.createRigidArea(dimRigidArea));

		JLabel regionLabel = new JLabel("Регион");
		addressPanel.add(regionLabel);
		String[] arrRegionType = ModelDBConnection.getNamesFromTableOrderedById("Region");
		comboRegionType = new JComboBox(arrRegionType);
		comboRegionType.setSelectedIndex(-1);

		addressPanel.add(comboRegionType);
		this.add(addressPanel);

		JPanel typePunktPanel = new JPanel();
		typePunktPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		typePunktPanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel typePunktLabel = new JLabel("Тип населенного пункта");
		typePunktPanel.add(typePunktLabel);
		String[] arrPunktType = ModelDBConnection.getNamesFromTableOrderedById("LocalityType");
		comboPunktType = new JComboBox(arrPunktType);
		comboPunktType.setSelectedIndex(-1);

		typePunktPanel.add(comboPunktType);
		this.add(typePunktPanel);

		JPanel adressLivingPanel = new JPanel();
		adressLivingPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		adressLivingPanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel adressLivingLabel = new JLabel("Адрес");
		adressLivingPanel.add(adressLivingLabel);
		textAdressLiving = new JTextArea(2, 51);
		JScrollPane paneIssuedBy = new JScrollPane(textAdressLiving);
		textAdressLiving.setLineWrap(true);
		textAdressLiving.setBackground(Color.WHITE);

		adressLivingPanel.add(paneIssuedBy);
		this.add(adressLivingPanel);

		JPanel contInfoPanel = new JPanel();
		contInfoPanel.setLayout(new BoxLayout(contInfoPanel, BoxLayout.PAGE_AXIS));
		contInfoPanel.setBorder(
				new TitledBorder(null, "Контактная информация", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JPanel phonePanel = new JPanel();
		phonePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		phonePanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel phoneLabel = new JLabel("Телефон");
		phonePanel.add(phoneLabel);
		textPhone = new JTextField();
		textPhone.setPreferredSize(new Dimension(545, 25));
		textPhone.setBackground(Color.WHITE);

		phonePanel.add(textPhone);
		contInfoPanel.add(phonePanel);

		JPanel emailPanel = new JPanel();
		emailPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		emailPanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel emailLabel = new JLabel("e-mail");
		emailPanel.add(emailLabel);
		textEmail = new JTextField();
		textEmail.setPreferredSize(new Dimension(565, 25));
		textEmail.setBackground(Color.WHITE);

		emailPanel.add(textEmail);
		contInfoPanel.add(emailPanel);
		this.add(contInfoPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		editAddressContactsButton = new JButton("Редактировать");
		editAddressContactsButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editAddressContactsButtonActionPerformed(evt);
			}
		});

		buttonPanel.add(editAddressContactsButton);

		saveAddressContactsButton = new JButton("Сохранить");
		saveAddressContactsButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveAddressContactsButtonActionPerformed(evt);
			}
		});

		buttonPanel.add(saveAddressContactsButton);
		this.add(buttonPanel);

		this.setEditable(false);
	}

	private void editAddressContactsButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			this.setEditable(true);
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private void saveAddressContactsButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			String[] values = getValues();
			ArrayList<Integer> mistakesIndices = checkData(values);
			if (values[0].equals("0"))
				values[0] = null;
			if (values[1].equals("0"))
				values[1] = null;
			if (mistakesIndices.contains(2))
				textIndex.setForeground(Color.RED);
			else
				textIndex.setForeground(Color.BLACK);
			if (mistakesIndices.contains(4))
				textEmail.setForeground(Color.RED);
			else
				textEmail.setForeground(Color.BLACK);
			if (mistakesIndices.contains(5))
				textPhone.setForeground(Color.RED);
			else
				textPhone.setForeground(Color.BLACK);

			if (mistakesIndices.isEmpty()) {
				ModelDBConnection.updateAbiturientAddressAndContactsByID(currentAbit, values);
				this.setEditable(false);
			}
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	// TODO Where to check on isEmpty()?
	private ArrayList<Integer> checkData(String[] values) {
		ArrayList<Integer> mistakesIndices = new ArrayList<Integer>();
		if (!values[2].isEmpty() && !values[2].matches("^[0-9]+$"))
			mistakesIndices.add(2);
		if (!values[4].isEmpty() && !values[4].matches("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}"))
			mistakesIndices.add(4);
		if (!values[5].isEmpty() && !values[5].matches("^[0-9()+-]+$"))
			mistakesIndices.add(5);
		return mistakesIndices;
	}

	public void setValues(String[] values) {
		currentAbit = values[0];
		comboRegionType.setSelectedIndex(values[1].equals("") ? -1 : Integer.valueOf(values[1]) - 1);
		comboPunktType.setSelectedIndex(values[1].equals("") ? -1 : Integer.valueOf(values[2]) - 1);
		textIndex.setText(values[3]);
		textAdressLiving.setText(values[4]);
		textEmail.setText(values[5]);
		textPhone.setText(values[6]);
		textIndex.setForeground(Color.BLACK);
		textEmail.setForeground(Color.BLACK);
		textPhone.setForeground(Color.BLACK);
	}

	public String[] getValues() {
		String[] values = new String[6];
		values[0] = String.valueOf(comboRegionType.getSelectedIndex() + 1);
		values[1] = String.valueOf(comboPunktType.getSelectedIndex() + 1);
		values[2] = textIndex.getText();
		values[3] = textAdressLiving.getText();
		values[4] = textEmail.getText();
		values[5] = textPhone.getText();

		return values;
	}

	public void setEditable(boolean state) {
		textIndex.setEditable(state);
		comboRegionType.setEnabled(state);
		comboPunktType.setEnabled(state);
		comboRegionType.setEditable(!state);
		comboPunktType.setEditable(!state);
		textAdressLiving.setEditable(state);
		textPhone.setEditable(state);
		textEmail.setEditable(state);
		saveAddressContactsButton.setEnabled(state);
		editAddressContactsButton.setEnabled(!state);
	}
}
