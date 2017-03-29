package tests;

import java.sql.SQLException;

import backend.ModelDBConnection;

public class TestBackend {

	public static void main(String[] args) throws SQLException {

		ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Ordinator", "user", "password");
		ModelDBConnection.initConnection();
		/*String [] a = ModelDBConnection.getAbiturientGeneralInfoByID("2");
		for(int i=0;i<a.length;i++)
				System.out.println(a[i]);
		ModelDBConnection.getCount("Abiturient");
		ModelDBConnection.getCountForAbitID("AbiturientPassport", "1");
		ModelDBConnection.getAllFromTable("Gender");
		System.out.println("--------------------------");
		String[] data1 = { "1", "Мужской", null };
		ModelDBConnection.updateElementInTableById("Gender", data1);
		ModelDBConnection.getAllFromTableOrderedById("Gender");
		System.out.println("--------------------------");
		String[] data2 = { "1", "Женский", null };
		ModelDBConnection.updateElementInTableById("Gender", data2);
		ModelDBConnection.getAllFromTableOrderedById("Gender");
		String[] data = {"1", "2"};
		ModelDBConnection.getElementFromTableByIDs("AbiturientIndividualAchievement", data);
		ModelDBConnection.getNamesFromTableOrderedById("TestBox");*/
		
		// String [][] b = ModelDBConnection.getAllAbiturients();
		// String [] a = ModelDBConnection.getAbiturientGeneralInfoByID("2");

		String[] c = ModelDBConnection.getAbiturientPassportByID("3");

		String[] k = ModelDBConnection.getAllGroupsNames();
		for (int i = 0; i < k.length; i++) {
			System.out.println(k[i]);
		}

		String[][] a = ModelDBConnection.getListAbiturientsByEntranceTestAndGroupIDs("1", "1c1");
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j] + " ");
			}
			System.out.println();
		}

		// ModelDBConnection.deleteAbiturient("2");
	}

}
