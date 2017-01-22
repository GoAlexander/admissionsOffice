package tests;

import java.sql.SQLException;

import backend.ModelDBConnection;

public class TestBackend {

	public static void main(String[] args) throws SQLException {

		ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Ordinator", "user", "password");
		ModelDBConnection.getAllFromTableOrderedById("Gender");
		System.out.println("--------------------------");
		String[] data1 = { "1", "Мужской", null };
		ModelDBConnection.updateElementInTableById("Gender", data1);
		ModelDBConnection.getAllFromTableOrderedById("Gender");
		System.out.println("--------------------------");
		String[] data2 = { "1", "Женский", null };
		ModelDBConnection.updateElementInTableById("Gender", data2);
		ModelDBConnection.getAllFromTableOrderedById("Gender");
	}

}
