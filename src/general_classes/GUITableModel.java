package general_classes;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class GUITableModel extends DefaultTableModel {
	public Vector<String> getInfoByKey(int i){
		Vector<String> info = new Vector<String>();
		for(int j = 0; j < getColumnCount(); j++){
			info.add(getValueAt(i, j).toString());
			}
			return info;
		}

	public int getKeyIndex(String key, int columnIndex) {
		int index = -1;
		for (int i = 0; i < getRowCount(); i++){
			if (getValueAt(i, columnIndex).toString().equals(key)){
				index = i;	
			}
		}
		return index;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		switch (getColumnName(column)) {
		case "№":
		case "Фамилия":
		case "Имя":
		case "Отчество":
			return false;
		}
		return true;
	}
}
