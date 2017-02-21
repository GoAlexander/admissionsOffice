package backend;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class ModelDBConnection {
	static String login;
	static String password;
	static String serverAddress;
	static String serverType;
	static String dbName;

	static Connection con = null;
	static CallableStatement cstmt = null;
	static ResultSet rset = null;
	static Statement stmt = null;
	
	static boolean DEBUG = false;

	public static void setConnectionParameters(String serverType, String serverAddress, String dbName, String login,
			String password) {
		ModelDBConnection.serverType = serverType;
		ModelDBConnection.serverAddress = serverAddress;
		ModelDBConnection.dbName = dbName;
		ModelDBConnection.login = login;
		ModelDBConnection.password = password;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public String getServerType() {
		return serverType;
	}

	public String getDBName() {
		return dbName;
	}

	public static boolean initConnection() {
		if (con == null) {
			try {
				String connectionUrl = null;
				switch (serverType) {
				case "AzureServer":
					connectionUrl = "jdbc:sqlserver://" + serverAddress + ":1433;database=" + dbName + ";user=" + login
							+ ";password=" + password
							+ ";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
					break;
				case "MSServer":
					connectionUrl = "jdbc:sqlserver://" + serverAddress + ":1433;databaseName=" + dbName + ";user="
							+ login + ";password=" + password + ";";
					break;
				}

				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				con = DriverManager.getConnection(connectionUrl);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public static int getCount(String tableName) {
		if (initConnection()) {
			try {
				int count = 0;
				cstmt = con.prepareCall("{? = call getCount(?)}");

				cstmt.registerOutParameter(1, Types.INTEGER);
				cstmt.setString(2, tableName);

				cstmt.execute();

				count = cstmt.getInt(1);
				//System.out.println(count);
				return count;
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		}
		return -1;
	}

	public static int getCountForAbitID(String tableName, String aid) {
		if (initConnection()) {
			try {
				int count = 0;
				String query = "select count(*) from " + tableName + " where aid_abiturient = " + aid;
				stmt = con.createStatement();
				rset = stmt.executeQuery(query);

				while (rset.next()) {
					count = rset.getInt(1);
				}

				stmt.close();
				rset.close();

				return count;
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		}
		return -1;
	}

	public static String[][] getAllAbiturients() {
		int countAbits = getCount("Abiturient");

		String[][] data = null;

		if (countAbits > 0) {
			try {
				data = new String[countAbits][4];

				String query = "select aid, SName, Fname, MName from Abiturient order by aid";
				stmt = con.createStatement();
				rset = stmt.executeQuery(query);

				int curPos = 0;
				while (rset.next()) {
					data[curPos][0] = String.valueOf(rset.getInt(1));
					data[curPos][1] = rset.getString(2);
					data[curPos][2] = rset.getString(3);
					data[curPos][3] = rset.getString(4);
					curPos++;
				}

				stmt.close();
				rset.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return data;
	}

	public static String[] getAbiturientGeneralInfoByID(String aid) throws SQLException {
		String query = "select aid, SName, FName, MName, Birthday, id_gender, id_nationality, registrationDate, id_returnReason, returnDate from Abiturient where aid = "
				+ aid + ";";
		String[] abiturientInfo = null;
		if (initConnection()) {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				abiturientInfo = new String[10];
				abiturientInfo[0] = String.valueOf(rset.getInt(1));
				abiturientInfo[1] = rset.getString(2);
				abiturientInfo[2] = rset.getString(3);
				abiturientInfo[3] = rset.getString(4);
				abiturientInfo[4] = rset.getDate(5).toString();
				abiturientInfo[5] = String.valueOf(rset.getInt(6));
				abiturientInfo[6] = String.valueOf(rset.getInt(7));
				abiturientInfo[7] = rset.getDate(8).toString();
				abiturientInfo[8] = rset.getInt(9) == 0 ? "" : String.valueOf(rset.getInt(9));
				abiturientInfo[9] = rset.getDate(10) == null ? "" : rset.getDate(10).toString();
				
				if (DEBUG) {
				System.out.println(abiturientInfo[0] + " " + abiturientInfo[1] + " " + abiturientInfo[2] + " "
						+ abiturientInfo[3] + " " + abiturientInfo[4] + " " + abiturientInfo[5] + " "
						+ abiturientInfo[6] + " " + abiturientInfo[7] + " ");
				}
			}

			stmt.close();
			rset.close();
		}
		return abiturientInfo;
	}

	public static void insertAbiturient(String[] info) throws SQLException {
		String aid, SName, FName, MName, birthday, birthplace, id_gender, id_nationality, email, phoneNumbers,
				needHostel, registrationDate, returnDate, id_returnReason, needSpecConditions, is_enrolled;
		String query;

		query = aid = SName = FName = MName = birthday = birthplace = id_gender = id_nationality = email = phoneNumbers = needHostel = registrationDate = returnDate = id_returnReason = needSpecConditions = is_enrolled = null;

		switch (info.length) {
		case 16:
			aid = info[0];
			SName = "'" + info[1] + "'";
			FName = "'" + info[2] + "'";
			MName = "'" + info[3] + "'";
			birthday = "'" + info[4] + "'";
			birthplace = "'" + info[5] + "'";
			id_gender = info[6];
			id_nationality = info[7];
			email = "'" + info[8] + "'";
			phoneNumbers = "'" + info[9] + "'";
			needHostel = info[10];
			registrationDate = "'" + info[11] + "'";
			returnDate = "'" + info[12] + "'";
			id_returnReason = info[13];
			needSpecConditions = info[14];
			is_enrolled = info[15];

			query = "insert into Abiturient Values (" + aid + ", " + SName + ", " + FName + ", " + MName + ", "
					+ birthday + ", " + birthplace + ", " + id_gender + ", " + id_nationality + ", " + email + ", "
					+ phoneNumbers + ", " + needHostel + ", " + registrationDate + ", " + returnDate + ", "
					+ id_returnReason + ", " + needSpecConditions + ", " + is_enrolled + ");";

			break;
		case 8:
			aid = info[0];
			SName = "'" + info[1] + "'";
			FName = "'" + info[2] + "'";
			MName = "'" + info[3] + "'";
			birthday = "'" + info[4] + "'";
			id_gender = info[5];
			id_nationality = info[6];
			registrationDate = "'" + info[7] + "'";

			query = "insert into Abiturient (aid, SName, FName, MName, Birthday, id_gender, id_nationality, registrationDate) Values ("
					+ aid + ", " + SName + ", " + FName + ", " + MName + ", " + birthday + ", " + id_gender + ", "
					+ id_nationality + ", " + registrationDate + ");";

			break;
		}

		if (initConnection()) {
			stmt = con.createStatement();
			stmt.executeUpdate(query);

			stmt.close();
		}
	}

	public static void editAbiturient(String[] info) throws SQLException {

		String aid, SName, FName, MName, birthday, birthplace, id_gender, id_nationality, email, phoneNumbers,
				needHostel, registrationDate, returnDate, id_returnReason, needSpecConditions, is_enrolled;
		String query;

		query = aid = SName = FName = MName = birthday = birthplace = id_gender = id_nationality = email = phoneNumbers = needHostel = registrationDate = returnDate = id_returnReason = needSpecConditions = is_enrolled = null;

		switch (info.length) {
		case 16:
			aid = info[0];
			SName = "'" + info[1] + "'";
			FName = "'" + info[2] + "'";
			MName = "'" + info[3] + "'";
			birthday = "'" + info[4] + "'";
			birthplace = "'" + info[5] + "'";
			id_gender = info[6];
			id_nationality = info[7];
			email = "'" + info[8] + "'";
			phoneNumbers = "'" + info[9] + "'";
			needHostel = info[10];
			registrationDate = "'" + info[11] + "'";
			returnDate = "'" + info[12] + "'";
			id_returnReason = info[13];
			needSpecConditions = info[14];
			is_enrolled = info[15];

			query = "update Abiturient set SName = " + SName + ", FName = " + FName + ", MName = " + MName
					+ ", Birthday = " + birthday + ", Birthplace = " + birthplace + ", id_gender = " + id_gender
					+ ", id_nationality = " + id_nationality + ", email = " + email + ", phoneNumbers = " + phoneNumbers
					+ ", needHostel = " + needHostel + ", registrationDate = " + registrationDate + ", returnDate = "
					+ returnDate + ", id_returnReason = " + id_returnReason + ", needSpecConditions = "
					+ needSpecConditions + ", is_enrolled = " + is_enrolled + " where aid = " + aid + ";";

			break;
		case 8:
			aid = info[0];
			SName = "'" + info[1] + "'";
			FName = "'" + info[2] + "'";
			MName = "'" + info[3] + "'";
			birthday = "'" + info[4] + "'";
			id_gender = info[5];
			id_nationality = info[6];
			registrationDate = "'" + info[7] + "'";

			query = "update Abiturient set SName = " + SName + ", FName = " + FName + ", MName = " + MName
					+ ", Birthday = " + birthday + ", id_gender = " + id_gender + ", id_nationality = " + id_nationality
					+ ", registrationDate = " + registrationDate + " where aid = " + aid + ";";

			break;
		case 10:
			aid = info[0];
			SName = "'" + info[1] + "'";
			FName = "'" + info[2] + "'";
			MName = "'" + info[3] + "'";
			birthday = "'" + info[4] + "'";
			id_gender = info[5];
			id_nationality = info[6];
			registrationDate = "'" + info[7] + "'";
			returnDate = info[8] == null ? null : "'" + info[8] + "'";
			id_returnReason = info[9];

			query = "update Abiturient set SName = " + SName + ", FName = " + FName + ", MName = " + MName
					+ ", Birthday = " + birthday + ", id_gender = " + id_gender + ", id_nationality = " + id_nationality
					+ ", registrationDate = " + registrationDate + ", returnDate = " + returnDate
					+ ", id_returnReason = " + id_returnReason + " where aid = " + aid + ";";
			System.out.println(query);
			break;
		}

		if (initConnection()) {
			stmt = con.createStatement();
			stmt.executeUpdate(query);

			stmt.close();
		}
	}

	public static void deleteAbiturient(String aid) throws SQLException {
		String query = "delete from Abiturient where aid = " + aid + ";";
		if (initConnection()) {
			stmt = con.createStatement();
			stmt.executeUpdate(query);

			stmt.close();
		}
	}

	public static String[] getNamesFromTableOrderedById(String table) {
		try {
			String query = "select name from " + table + " order by id";

			int i = 0, countTableRows = getCount(table);
			String[] listElems = new String[countTableRows];

			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				listElems[i] = rset.getString(1);
				i++;
			}

			stmt.close();
			rset.close();

			return listElems;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * public static String[] getTableColumnNames(String table) throws
	 * SQLException {
	 * 
	 * try { String query = "select * from " + table + " ;"; stmt =
	 * con.createStatement(); rset = stmt.executeQuery(query); ResultSetMetaData
	 * rsmd = rset.getMetaData(); int numberOfColumns = rsmd.getColumnCount();
	 * String[] data = new String[numberOfColumns]; for (int i = 0; i <
	 * numberOfColumns; i++) { data[i] = rsmd.getColumnLabel(i+1); }
	 * stmt.close(); rset.close(); return data; } catch (Exception e) {
	 * e.printStackTrace(); return null; } }
	 */

	public static String[][] getAllFromTableOrderedById(String table) throws SQLException {
		String id = "id";

		switch (table) {
		case "Abiturient":
			id = "aid";
			break;
		case "AbiturientIndividualAchievement":
		case "AbiturientPostgraduateEducation":
		case "AbiturientEntranceTests":
		case "AbiturientPassport":
		case "AbiturientAddress":
		case "AbiturientCompetitiveGroup":
			id = "aid_abiturient";
			break;
		case "AdmissionPlan":
			id = "specialtyCode";
			break;
		case "Users":
			id = "userLogin";
			break;
		default:
			id = "id";
		}

		int count = getCount(table);

		String[][] data = null;

		if (count > 0) {
			try {

				String query = "select * from " + table + " order by " + id + " ;";
				stmt = con.createStatement();
				rset = stmt.executeQuery(query);
				ResultSetMetaData rsmd = rset.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();

				data = new String[count][numberOfColumns];
				int curPos = 0;
				while (rset.next()) {
					for (int i = 0; i < numberOfColumns; i++) {
						if (rset.getObject(i + 1) != null)
							data[curPos][i] = rset.getObject(i + 1).toString();
						System.out.println(data[curPos][i]);
					}
					curPos++;
				}
				stmt.close();
				rset.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return data;
	}

	// TODO insert into GUI
	public static String[][] getAllFromTable(String table) throws SQLException {
		int count = getCount(table);

		String[][] data = null;

		if (count > 0) {
			try {

				String query = "select * from " + table + " ;";
				stmt = con.createStatement();
				rset = stmt.executeQuery(query);
				ResultSetMetaData rsmd = rset.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();

				data = new String[count][numberOfColumns];
				int curPos = 0;
				while (rset.next()) {
					for (int i = 0; i < numberOfColumns; i++) {
						if (rset.getObject(i + 1) != null)
							data[curPos][i] = rset.getObject(i + 1).toString();
						System.out.println(data[curPos][i]);
					}
					curPos++;
				}
				stmt.close();
				rset.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return data;
	}

	public static void updateElementInTableById(String table, String[] data) throws SQLException {
		String id = "id";
		switch (table) {
		case "Abiturient":
			id = "aid";
			break;
		case "AbiturientIndividualAchievement":
		case "AbiturientPostgraduateEducation":
		case "AbiturientEntranceTests":
		case "AbiturientPassport":
		case "AbiturientAddress":
		case "AbiturientCompetitiveGroup":
		case "AbiturientHigherEducation":
			id = "aid_abiturient";
			break;
		case "AdmissionPlan":
			id = "specialtyCode";
			break;
		case "Users":
			id = "userLogin";
			break;
		default:
			id = "id";
		}

		String query = "select * from " + table + " where " + id + " = " + data[0] + ";";
		System.out.println(query);
		int numberOfColumns = 0;
		if (initConnection()) {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			ResultSetMetaData rsmd = rset.getMetaData();
			numberOfColumns = rsmd.getColumnCount();

			int countStrings = 0;
			while (rset.next()) {
				countStrings++;
			}

			if (countStrings > 0) {
				query = "update " + table + " set ";
				for (int i = 1; i < numberOfColumns; i++) {
					if (i == numberOfColumns - 1)
						query = query + rsmd.getColumnLabel(i + 1) + " = " + "'" + data[i] + "'";
					else
						query = query + rsmd.getColumnLabel(i + 1) + " = " + "'" + data[i] + "'" + ", ";
				}
				query = query + " where " + id + " = " + data[0] + ";";
			} else {
				query = "insert into " + table + " values (" + data[0] + ", ";
				for (int i = 1; i < numberOfColumns; i++) {
					if (i == numberOfColumns - 1)
						query = query + "'" + data[i] + "')";
					else
						query = query + "'" + data[i] + "'" + ", ";
				}
			}
			stmt.close();
			rset.close();
		}

		System.out.println(query);

		stmt = con.createStatement();
		stmt.executeUpdate(query);

		stmt.close();
		rset.close();
	}

	public static void updateElementInTableByExpression(String table, String[] data, int countOfExprParams) throws SQLException {
		String id = "id";
		switch (table) {
		case "Abiturient":
			id = "aid";
			break;
		case "AbiturientIndividualAchievement":
		case "AbiturientPostgraduateEducation":
		case "AbiturientEntranceTests":
		case "AbiturientPassport":
		case "AbiturientAddress":
		case "AbiturientCompetitiveGroup":
		case "AbiturientHigherEducation":
			id = "aid_abiturient";
			break;
		case "AdmissionPlan":
			id = "specialtyCode";
			break;
		case "Users":
			id = "userLogin";
			break;
		default:
			id = "id";
		}

		String query = "select * from " + table + " where " + id + " = 0;";
		System.out.println(query);
		int numberOfColumns = 0;
		if (initConnection()) {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			ResultSetMetaData rsmd = rset.getMetaData();
			numberOfColumns = rsmd.getColumnCount();

			int countStrings = 0;
			query = "select * from " + table + " where ";
			for(int i = 0; i < countOfExprParams; i++)
				if(i == countOfExprParams-1)
					query = query + rsmd.getColumnLabel(i + 1) + " = " + data[i] + ";";
				else
					query = query + rsmd.getColumnLabel(i + 1) + " = " + data[i] + " and ";
			System.out.println(query);
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				countStrings++;
			}

			if (countStrings > 0) {
				query = "update " + table + " set ";
				for (int i = 1; i < numberOfColumns; i++) {
					if (i == numberOfColumns - 1)
						query = query + rsmd.getColumnLabel(i + 1) + " = " + "'" + data[i] + "'";
					else
						query = query + rsmd.getColumnLabel(i + 1) + " = " + "'" + data[i] + "'" + ", ";
				}
				query = query + " where ";
				for(int i = 0; i < countOfExprParams; i++)
					if(i == countOfExprParams-1)
						query = query + rsmd.getColumnLabel(i + 1) + " = " + data[i] + ";";
					else
						query = query + rsmd.getColumnLabel(i + 1) + " = " + data[i] + " and ";
			} else {
				query = "insert into " + table + " values (" + data[0] + ", ";
				for (int i = 1; i < numberOfColumns; i++) {
					if (i == numberOfColumns - 1)
						query = query + "'" + data[i] + "')";
					else
						query = query + "'" + data[i] + "'" + ", ";
				}
			}
			stmt.close();
			rset.close();
		}

		System.out.println(query);

		stmt = con.createStatement();
		stmt.executeUpdate(query);

		stmt.close();
		rset.close();
	}

	public static void updateElementInTableByIds(String table, String[] data) throws SQLException {
		String id1 = "aid_abiturient", id2 = "";
		switch (table) {
		case "AbiturientIndividualAchievement":
			id1 = "aid_abiturient";
			id2 = "id_individual_achievement";
			break;
		case "AbiturientEntranceTests":
			id1 = "aid_abiturient";
			id2 = "id_entranceTest";
			break;
		}

		String query = "select * from " + table + " where " + id1 + " = " + data[0] + " and " + id2 + " = " + data[1] + ";";
		System.out.println(query);
		int numberOfColumns = 0;
		if (initConnection()) {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			ResultSetMetaData rsmd = rset.getMetaData();
			numberOfColumns = rsmd.getColumnCount();

			int countStrings = 0;
			while (rset.next()) {
				countStrings++;
			}

			if (countStrings > 0) {
				query = "update " + table + " set ";
				for (int i = 1; i < numberOfColumns; i++) {
					if (i == numberOfColumns - 1)
						query = query + rsmd.getColumnLabel(i + 1) + " = " + "'" + data[i] + "'";
					else
						query = query + rsmd.getColumnLabel(i + 1) + " = " + "'" + data[i] + "'" + ", ";
				}
				query = query + " where " + id1 + " = " + data[0] + " and " + id2 + " = " + data[1] + ";";
			} else {
				query = "insert into " + table + " values (" + data[0] + ", ";
				for (int i = 1; i < numberOfColumns; i++) {
					if (i == numberOfColumns - 1)
						query = query + "'" + data[i] + "')";
					else
						query = query + "'" + data[i] + "'" + ", ";
				}
			}
			stmt.close();
			rset.close();
		}

		System.out.println(query);

		stmt = con.createStatement();
		stmt.executeUpdate(query);

		stmt.close();
		rset.close();
	}

	public static void deleteElementInTableById(String table, String data) throws SQLException {
		String id = "id";
		switch (table) {
		case "Abiturient":
			id = "aid";
			break;
		case "AbiturientIndividualAchievement":
		case "AbiturientPostgraduateEducation":
		case "AbiturientEntranceTests":
		case "AbiturientPassport":
		case "AbiturientAddress":
		case "AbiturientCompetitiveGroup":
			id = "aid_abiturient";
			break;
		case "AdmissionPlan":
			id = "specialtyCode";
			break;
		case "Users":
			id = "userLogin";
			break;
		default:
			id = "id";
		}

		String query = "delete from " + table + " where " + id + " = " + data + ";";
		System.out.println(query);

		stmt = con.createStatement();
		stmt.executeUpdate(query);

		stmt.close();
	}

	public static void deleteElementInTableByExpression(String table, String[] data, int countOfExprParams) throws SQLException {
		String id = "aid_abiturient";
		switch (table) {
		case "AbiturientCompetitiveGroup":
			id = "aid_abiturient";
			break;
		}

		String query = "select * from " + table + " where " + id + " = 0;";
		System.out.println(query);
		int numberOfColumns = 0;

		stmt = con.createStatement();
		rset = stmt.executeQuery(query);

		ResultSetMetaData rsmd = rset.getMetaData();
		numberOfColumns = rsmd.getColumnCount();

		query = "delete from " + table + " where ";
		for(int i = 0; i < countOfExprParams; i++)
			if(i == countOfExprParams-1)
				query = query + rsmd.getColumnLabel(i + 1) + " = " + data[i] + ";";
			else
				query = query + rsmd.getColumnLabel(i + 1) + " = " + data[i] + " and ";
		System.out.println(query);

		stmt = con.createStatement();
		stmt.executeUpdate(query);

		stmt.close();
	}

	public static void deleteElementInTableByIds(String table, String[] data) throws SQLException {
		String id1 = "aid_abiturient", id2 = "";
		switch (table) {
		case "AbiturientIndividualAchievement":
			id1 = "aid_abiturient";
			id2 = "id_individual_achievement";
			break;
		case "AbiturientEntranceTests":
			id1 = "aid_abiturient";
			id2 = "id_entranceTest";
			break;
		}

		String query = "delete from " + table + " where " + id1 + " = " + data[0] + " and " + id2 + " = " + data[1] + ";";
		System.out.println(query);

		stmt = con.createStatement();
		stmt.executeUpdate(query);

		stmt.close();
	}

	public static String[][] getAllAchievmentsByAbiturientId(String aid, boolean need_all_columns) {
		String[][] data = null;

		int countStrings = getCountForAbitID("AbiturientIndividualAchievement", aid);

		if (countStrings > 0) {
			try {
				String query;
				if(need_all_columns)
					query = "select * from AbiturientIndividualAchievement "
						+ "where aid_abiturient = " + aid;
				else
					query = "select name, AbiturientIndividualAchievement.score from IndividualAchievement, AbiturientIndividualAchievement "
							+ "where IndividualAchievement.id = AbiturientIndividualAchievement.id_individual_achievement "
							+ "and aid_abiturient = " + aid;

				stmt = con.createStatement();
				rset = stmt.executeQuery(query);
				ResultSetMetaData rsmd = rset.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();

				data = new String[countStrings][numberOfColumns];
				int curPos = 0;
				while (rset.next()) {
					for (int i = 0; i < numberOfColumns; i++) {
						if (rset.getObject(i + 1) != null)
							data[curPos][i] = rset.getObject(i + 1).toString();
					}
					curPos++;
				}
				stmt.close();
				rset.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return data;
	}

	public static String[][] getAllEntranceTestsResultsByAbiturientId(String aid, boolean need_all_columns) {
		String[][] data = null;
		String query = "select EntranceTest.name, null, null, null, null from EntranceTest";
		int countStrings = getCountForAbitID("AbiturientEntranceTests", aid);

		if(need_all_columns) {
			query = "select * from AbiturientEntranceTests "
					+ "where aid_abiturient = " + aid;
		}
		else {
			if (countStrings > 0) {
				query = "select EntranceTest.name, testGroup, TestBox.name, testDate, AbiturientEntranceTests.score from EntranceTest, AbiturientEntranceTests, TestBox "
							+ "where EntranceTest.id = AbiturientEntranceTests.id_entranceTest "
							+ "and TestBox.id = AbiturientEntranceTests.id_testBox " + "and aid_abiturient = " + aid;
			} else {
				countStrings = getCount("EntranceTest");
			}
		}

		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			data = new String[countStrings][numberOfColumns];
			int curPos = 0;
			while (rset.next()) {
				for (int i = 0; i < numberOfColumns; i++) {
					if (rset.getObject(i + 1) != null)
						data[curPos][i] = rset.getObject(i + 1).toString();
				}
				curPos++;
			}
			stmt.close();
			rset.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return data;
	}

	public static String[][] getAllCompetitiveGroupsByAbiturientId(String aid) {
		String[][] data = null;

		int countStrings = getCountForAbitID("AbiturientCompetitiveGroup", aid);

		if (countStrings > 0) {
			try {
				String query = "Select aid_abiturient, course, speciality, educationForm, chair,"
						+ " competitiveGroup, targetOrganisation, educationStandard, competitiveBall,"
						+ " availabilityIndividualAchievements, originalsReceivedDate, markEnrollment"
						+ " from Abiturient, AbiturientCompetitiveGroup"
						+ " where Abiturient.aid = AbiturientCompetitiveGroup.aid_abiturient and"
						+ " aid_abiturient = " + aid + ";";
				System.out.println(query);

				stmt = con.createStatement();
				rset = stmt.executeQuery(query);
				ResultSetMetaData rsmd = rset.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();
	
				data = new String[countStrings][numberOfColumns];
				int curPos = 0;
				while (rset.next()) {
					for (int i = 0; i < numberOfColumns; i++) {
						if (rset.getObject(i + 1) != null)
							data[curPos][i] = rset.getObject(i + 1).toString();
					}
					curPos++;
				}
				stmt.close();
				rset.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return data;
	}

	public static String[] getAbiturientPassportByID(String aid) throws SQLException {
		String query = "select aid_abiturient, id_passportType, paspSeries, paspNumber, paspGivenBy, paspGivenDate, Birthplace "
				+ "from Abiturient, AbiturientPassport where " + "Abiturient.aid = AbiturientPassport.aid_abiturient "
				+ "and aid_abiturient = " + aid + ";";
		String[] abiturientPassportInfo = new String[7];
		for (int i = 0; i < abiturientPassportInfo.length; i++)
			abiturientPassportInfo[i] = "";
		abiturientPassportInfo[0] = aid;

		if (initConnection()) {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				abiturientPassportInfo = new String[7];
				abiturientPassportInfo[0] = String.valueOf(rset.getInt(1));
				abiturientPassportInfo[1] = String.valueOf(rset.getInt(2));
				abiturientPassportInfo[2] = rset.getString(3);
				abiturientPassportInfo[3] = rset.getString(4);
				abiturientPassportInfo[4] = rset.getString(5);
				abiturientPassportInfo[5] = rset.getString(6);
				abiturientPassportInfo[6] = rset.getString(7);

				if (DEBUG) {
					System.out.println(abiturientPassportInfo[0] + " " + abiturientPassportInfo[1] + " "
							+ abiturientPassportInfo[2] + " " + abiturientPassportInfo[3] + " "
							+ abiturientPassportInfo[4] + " " + abiturientPassportInfo[5] + " "
							+ abiturientPassportInfo[6]);
				}
			}

			stmt.close();
			rset.close();
		}
		return abiturientPassportInfo;
	}

	public static void updateAbiturientPassportByID(String aid, String[] data) throws SQLException {
		try {
			String query = "update Abiturient set Birthplace = '" + data[5] + "' where aid = " + aid + ";";

			String[] abiturientPassportInfo = new String[6];
			abiturientPassportInfo[0] = aid;
			for (int i = 1; i < abiturientPassportInfo.length; i++)
				abiturientPassportInfo[i] = data[i - 1];

			ModelDBConnection.updateElementInTableById("AbiturientPassport", abiturientPassportInfo);
			System.out.println(query);
			stmt = con.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String[] getAbiturientAddressAndContactsByID(String aid) throws SQLException {
		String query = "select aid_abiturient, id_region, id_localityType, indexAddress, factAddress, email, phoneNumbers "
				+ "from Abiturient, AbiturientAddress  where " + "Abiturient.aid = AbiturientAddress.aid_abiturient "
				+ "and aid_abiturient = " + aid + ";";
		String[] abiturientAddressAndContactsInfo = new String[7];
		for (int i = 0; i < abiturientAddressAndContactsInfo.length; i++)
			abiturientAddressAndContactsInfo[i] = "";
		abiturientAddressAndContactsInfo[0] = aid;

		if (initConnection()) {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				abiturientAddressAndContactsInfo = new String[7];
				abiturientAddressAndContactsInfo[0] = String.valueOf(rset.getInt(1));
				abiturientAddressAndContactsInfo[1] = String.valueOf(rset.getInt(2));
				abiturientAddressAndContactsInfo[2] = String.valueOf(rset.getInt(3));
				abiturientAddressAndContactsInfo[3] = rset.getString(4);
				abiturientAddressAndContactsInfo[4] = rset.getString(5);
				abiturientAddressAndContactsInfo[5] = rset.getString(6);
				abiturientAddressAndContactsInfo[6] = rset.getString(7);

				if (DEBUG) {
					System.out.println(abiturientAddressAndContactsInfo[0] + " " + abiturientAddressAndContactsInfo[1]
							+ " " + abiturientAddressAndContactsInfo[2] + " " + abiturientAddressAndContactsInfo[3]
							+ " " + abiturientAddressAndContactsInfo[4] + " " + abiturientAddressAndContactsInfo[5]
							+ " " + abiturientAddressAndContactsInfo[6]);
				}
			}

			stmt.close();
			rset.close();
		}
		return abiturientAddressAndContactsInfo;
	}

	public static void updateAbiturientAddressAndContactsByID(String aid, String[] data) throws SQLException {
		try {
			String query = "update Abiturient set email = '" + data[4] + "', phoneNumbers = '" + data[5]
					+ "' where aid = " + aid + ";";

			String[] abiturientAddressInfo = new String[5];
			abiturientAddressInfo[0] = aid;
			for (int i = 1; i < abiturientAddressInfo.length; i++)
				abiturientAddressInfo[i] = data[i - 1];

			ModelDBConnection.updateElementInTableById("AbiturientAddress", abiturientAddressInfo);
			System.out.println(query);
			stmt = con.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String[] getAbiturientEducationByID(String aid, String nameTable) throws SQLException {
		String query = "select aid_abiturient, diplomaSeries, diplomaNumber, instituteName, diplomaSpeciality, graduationYear "
				+ "from " + nameTable + " where aid_abiturient = " + aid + ";";

		String[] educationInfo = new String[6];
		for (int i = 0; i < educationInfo.length; i++)
			educationInfo[i] = "";
		educationInfo[0] = aid;

		if (initConnection()) {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				educationInfo = new String[6];
				educationInfo[0] = String.valueOf(rset.getInt(1));
				educationInfo[1] = rset.getString(2);
				educationInfo[2] = rset.getString(3);
				educationInfo[3] = rset.getString(4);
				educationInfo[4] = rset.getString(5);
				educationInfo[5] = String.valueOf(rset.getInt(6));

				if (DEBUG) {
					System.out.println(educationInfo[0] + " " + educationInfo[1] + " " + educationInfo[2] + " "
							+ educationInfo[3] + " " + educationInfo[4] + " " + educationInfo[5]);
				}
			}

			stmt.close();
			rset.close();
		}
		return educationInfo;
	}

	public static String[] getElementFromTableByIDs(String table, String[] data) {
		try {
			String id1 = "aid_abiturient", id2 = "";
			switch (table) {
			case "AbiturientIndividualAchievement":
				id1 = "aid_abiturient";
				id2 = "id_individual_achievement";
				break;
			case "AbiturientEntranceTests":
				id1 = "aid_abiturient";
				id2 = "id_entranceTest";
				break;
			}

			String query = "select * from " + table + " where " + id1 + " = " + data[0] + " and " + id2 + " = " + data[1] + ";";
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			String[] result = new String[numberOfColumns];
			for(int i = 0; i < result.length; i++)
				result[i] = "";

			while (rset.next()) {
				for (int i = 0; i < numberOfColumns; i++) {
					if (rset.getObject(i + 1) != null)
						result[i] = rset.getObject(i + 1).toString();
					System.out.println("Read: " + result[i]);
				}
			}
			stmt.close();
			rset.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static void updateAbiturientEducationByID(String nameTable, String[] data) throws SQLException {
		try {
			ModelDBConnection.updateElementInTableById(nameTable, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateAbiturientIndividualAchivementByID(String[] data) throws SQLException {
		try {
			ModelDBConnection.updateElementInTableByIds("AbiturientIndividualAchievement", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateAbiturientEntranceTestsResultsByID(String[] data) throws SQLException {
		try {
			String query = "update Abiturient set needSpecConditions = '" + data[data.length - 1] + "' where aid = " + data[0] + ";";

			String[] abiturientEntranceTestsResultsInfo = new String[data.length - 1];
			for (int i = 0; i < abiturientEntranceTestsResultsInfo.length; i++)
				abiturientEntranceTestsResultsInfo[i] = data[i];

			ModelDBConnection.updateElementInTableByIds("AbiturientEntranceTests", abiturientEntranceTestsResultsInfo);
			System.out.println(query);
			stmt = con.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean needAbiturientSpecialConditionsByID(String aid) {
		boolean state = false;
		String query = "select needSpecConditions from Abiturient where aid = " + aid + ";";

		System.out.println(query);
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				state = rset.getBoolean(1);
			}

			stmt.close();
			rset.close();
			return state;
		} catch (Exception e) {
			e.printStackTrace();
			return state;
		}
	}

	public static void updateAbiturientCompetitiveGroupByID(String[] data) throws SQLException {
		try {
			ModelDBConnection.updateElementInTableByExpression("AbiturientCompetitiveGroup", data, 8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteAbiturientCompetitiveGroupByID(String aid, String[] data) throws SQLException {
		deleteElementInTableByExpression("AbiturientCompetitiveGroup", data, 8);
	}
}
