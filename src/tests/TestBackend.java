﻿package tests;

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
		
		String [][] b = ModelDBConnection.getAllAbiturients();
		for(int i=0;i<b.length;i++){
			for(int j = 0; j<b[i].length; j++){
				System.out.print(b[i][j]);	
			}
			System.out.println();
		}
			
	}

}
