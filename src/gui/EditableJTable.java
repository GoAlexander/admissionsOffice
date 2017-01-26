
import java.util.ArrayList;

import javax.swing.JTable;

class Cell {
	Integer Row = null;
	Integer Col = null;

	public Cell(Integer row, Integer col) {
		if (row != null) {
			this.Row = row;
		} else {
			this.Row = 0;
		}
		if (col != null) {
			this.Col = col;
		} else {
			this.Col = 0;
		}
	}

	public Integer getRow() {
		return this.Row;
	}

	public Integer getCol() {
		return this.Col;
	}

	public boolean equals(Cell cell) {
		if (cell.getRow() == this.getRow() && cell.getCol() == this.getCol()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean equals(Object oCell) {
		Cell cell = (Cell) oCell;
		return equals(cell);
	}
}

public class EditableJTable extends JTable {

	private ArrayList<Cell> nonEditableCells = new ArrayList<Cell>();

	public EditableJTable(GUITableModel currentTM) {
		super(currentTM);
	}

	/**
	 * 
	 * <P>
	 * 
	 * @param row
	 * @param column
	 * @param editable
	 */
	public void setCellEditable(int row, int column, boolean editable) {
		Cell cell = new Cell(row, column);
		if (editable) {
			while (nonEditableCells.remove(cell)) {
			}
		} else {
			nonEditableCells.add(cell);
		}
	}

	/**
	 * 
	 * <P>
	 * 
	 * @param row
	 * @param editable
	 */
	public void setRowEditable(int row, boolean editable) {
		for (int i = 0; i < this.getColumnCount(); i++) {
			if (editable) {
				while (nonEditableCells.remove(new Cell(row, i))) {
				}
			} else {
				nonEditableCells.add(new Cell(row, i));
			}
		}
	}

	/**
	 * 
	 * <P>
	 * 
	 * @param column
	 * @param editable
	 */
	public void setColumnEditable(int column, boolean editable) {
		for (int i = 0; i < this.getRowCount(); i++) {
			if (editable) {
				while (nonEditableCells.remove(new Cell(i, column))) {
				}
			} else {
				nonEditableCells.add(new Cell(i, column));
			}
		}
	}

	/**
	 * 
	 * @param row
	 * @param column
	 */
	public boolean isCellEditable(int row, int column) {
		Cell cell = new Cell(row, column);
		for (int i = 0; i < nonEditableCells.size(); i++) {
			if (cell.equals(nonEditableCells.get(i))) {
				return false;
			}
		}
		return super.isCellEditable(row, column);
	}
}
