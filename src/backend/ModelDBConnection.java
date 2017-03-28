package backend;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;


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

		ModelDBConnection.con = null;
	}

	public static String getLogin() {
		return login;
	}

	public static String getPassword() {
		return password;
	}

	public static String getServerAddress() {
		return serverAddress;
	}

	public static String getServerType() {
		return serverType;
	}

	public static String getDBName() {
		return dbName;
	}

	public static Connection getConnection() {
		return con;
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
				// System.out.println(count);
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
				cstmt = con.prepareCall("{? = call getCountForAbitID(?,?)}");

				cstmt.registerOutParameter(1, Types.INTEGER);
				cstmt.setString(2, tableName);
				cstmt.setString(3, aid);

				cstmt.execute();

				count = cstmt.getInt(1);
				// System.out.println(count);
				return count;
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return -1;
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		}

		return -1;
	}

	public static String[][] getAllAbiturients() {

		String[][] data = null;

		try {
			cstmt = con.prepareCall("{call getAllAbiturients}", 1004, 1007);

			rset = cstmt.executeQuery();

			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			if (countStrings > 0) {
				ResultSetMetaData rsmd = rset.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();
				data = new String[countStrings][numberOfColumns];
				int curPos = 0;
				while (rset.next()) {
					for (int i = 0; i < numberOfColumns; i++) {
						if (rset.getObject(i + 1) != null) {
							data[curPos][i] = rset.getObject(i + 1).toString();
						}
					}
					curPos++;
				}
			}
			cstmt.close();
			rset.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return data;

	}

	public static String[] getAbiturientGeneralInfoByID(String aid) throws SQLException {
		try {
			cstmt = con.prepareCall("{call getAbiturientGeneralInfoByID(?)}", 1004, 1007);

			cstmt.setString(1, aid);

			rset = cstmt.executeQuery();

			ResultSetMetaData rsmd = rset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			String[] result = new String[numberOfColumns];
			for (int i = 0; i < result.length; i++)
				result[i] = "";
			result[0] = aid;

			while (rset.next()) {
				for (int i = 0; i < numberOfColumns; i++) {
					if (rset.getObject(i + 1) != null)
						result[i] = rset.getObject(i + 1).toString();
				}
			}
			cstmt.close();
			rset.close();
			return result;
		} catch (Exception e) {
			return null;
		}
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
			query = query.replaceAll("'null'", "null");
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
			query = query.replaceAll("'null'", "null");
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
		cstmt = con.prepareCall("{call deleteAbiturient(?)}");
		cstmt.setString(1, aid);
		cstmt.execute();
		cstmt.close();
	}

	public static String[] getNamesFromTableOrderedById(String table) {
		try {
			cstmt = con.prepareCall("{call getNamesFromTableOrderedById(?)}", 1004, 1007);

			cstmt.setString(1, table);

			rset = cstmt.executeQuery();

			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			int i = 0;
			String[] listElems = new String[countStrings];

			while (rset.next()) {
				listElems[i] = rset.getString(1);
				i++;
			}

			cstmt.close();
			rset.close();

			return listElems;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

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

		String[][] data = null;

		try {
			cstmt = con.prepareCall("{call getAllFromTable(?, ?)}", 1004, 1007);

			cstmt.setString(1, table);
			cstmt.setString(2, id);

			rset = cstmt.executeQuery();

			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			if (countStrings > 0) {
				ResultSetMetaData rsmd = rset.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();
				data = new String[countStrings][numberOfColumns];
				int curPos = 0;
				while (rset.next()) {
					for (int i = 0; i < numberOfColumns; i++) {
						if (rset.getObject(i + 1) != null) {
							data[curPos][i] = rset.getObject(i + 1).toString();
							System.out.println(data[curPos][i]);
						}
					}
					curPos++;
				}
			}
			cstmt.close();
			rset.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return data;
	}

	public static String[][] getAllFromTable(String table) throws SQLException {
		String[][] data = null;

		try {
			cstmt = con.prepareCall("{call getAllFromTable(?, ?)}", 1004, 1007);

			cstmt.setString(1, table);
			cstmt.setString(2, null);

			rset = cstmt.executeQuery();

			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			if (countStrings > 0) {
				ResultSetMetaData rsmd = rset.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();
				data = new String[countStrings][numberOfColumns];
				int curPos = 0;
				while (rset.next()) {
					for (int i = 0; i < numberOfColumns; i++) {
						if (rset.getObject(i + 1) != null) {
							data[curPos][i] = rset.getObject(i + 1).toString();
							//System.out.println(data[curPos][i]);
						}
					}
					curPos++;
				}
			}
			cstmt.close();
			rset.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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

		String query = "select * from " + table + " where " + id + " = " + (!table.equals("Users") ? data[0] : "'" + data[0] + "'") + ";";
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
				query = query + " where " + id + " = " + (!table.equals("Users") ? data[0] : "'" + data[0] + "'")  + ";";
			} else {
				query = "insert into " + table + " values (" + (!table.equals("Users") ? data[0] : "'" + data[0] + "'")  + ", ";
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

		query = query.replaceAll("'null'", "null");
		System.out.println(query);

		stmt = con.createStatement();
		stmt.executeUpdate(query);

		stmt.close();
		rset.close();
	}

	public static void updateElementInTableByExpression(String table, String[] data, int countOfExprParams)
			throws SQLException {
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
			for (int i = 0; i < countOfExprParams; i++)
				if (i == countOfExprParams - 1)
					query = query + rsmd.getColumnLabel(i + 1) + (data[i] != null ? " = " + data[i] : " is " + data[i]) + ";";
				else
					query = query + rsmd.getColumnLabel(i + 1) + (data[i] != null ? " = " + data[i] : " is " + data[i]) + " and ";
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
				for (int i = 0; i < countOfExprParams; i++)
					if (i == countOfExprParams - 1)
						query = query + rsmd.getColumnLabel(i + 1) + (data[i] != null ? " = " + data[i] : " is " + data[i]) + ";";
					else
						query = query + rsmd.getColumnLabel(i + 1) + (data[i] != null ? " = " + data[i] : " is " + data[i]) + " and ";
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
		query = query.replaceAll("'null'", "null");
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

		String query = "select * from " + table + " where " + id1 + " = " + data[0] + " and " + id2 + " = " + data[1]
				+ ";";
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
		query = query.replaceAll("'null'", "null");
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
		case "AbiturientHigherEducation":
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

		cstmt = con.prepareCall("{call deleteElementInTableById(?, ?, ?)}");

		cstmt.setString(1, table);
		cstmt.setString(2, id);
		cstmt.setString(3, (!table.equals("Users") ? data : "'" + data + "'"));

		cstmt.execute();

		cstmt.close();
	}

	public static void deleteElementInTableByExpression(String table, String[] data, int countOfExprParams)
			throws SQLException {
		String id = "aid_abiturient", query;
		switch (table) {
		case "AbiturientCompetitiveGroup":
			id = "aid_abiturient";
			break;
		case "AdmissionPlan":
			id = "specialtyCode";
			break;
		}

		if (countOfExprParams > 0) {
			query = "select * from " + table + " where " + id + " = 0;";
			System.out.println(query);

			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			ResultSetMetaData rsmd = rset.getMetaData();

			query = "delete from " + table + " where ";
			for (int i = 0; i < countOfExprParams; i++)
				if (i == countOfExprParams - 1)
					query = query + rsmd.getColumnLabel(i + 1) + " = " + data[i] + ";";
				else
					query = query + rsmd.getColumnLabel(i + 1) + " = " + data[i] + " and ";
			System.out.println(query);
		} else
			query = "delete from " + table;

		query = query.replaceAll("= null", "is null");
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

		cstmt = con.prepareCall("{call deleteElementInTableByIds(?, ?, ?, ?, ?)}");

		cstmt.setString(1, table);
		cstmt.setString(2, id1);
		cstmt.setString(3, data[0]);
		cstmt.setString(4, id2);
		cstmt.setString(5, data[1]);

		cstmt.execute();

		cstmt.close();
	}

	public static String[][] getAllAchievmentsByAbiturientId(String aid, boolean need_all_columns) {
		String[][] data = null;

		try {
			cstmt = con.prepareCall("{call getAllAchievmentsByAbiturientId(?, ?)}", 1004, 1007);

			cstmt.setString(1, aid);
			cstmt.setBoolean(2, need_all_columns ? true : false);

			rset = cstmt.executeQuery();

			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			if (countStrings > 0) {
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
			}
			cstmt.close();
			rset.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return data;
	}

	public static String[][] getAllEntranceTestsResultsByAbiturientId(String aid, boolean need_all_columns) {
		String[][] data = null;

		try {
			cstmt = con.prepareCall("{call getAllEntranceTestsResultsByAbiturientId(?, ?)}", 1004, 1007);

			cstmt.setString(1, aid);
			cstmt.setBoolean(2, need_all_columns ? true : false);

			rset = cstmt.executeQuery();

			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			if (countStrings > 0) {
				ResultSetMetaData rsmd = rset.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();
				data = new String[countStrings][numberOfColumns];
				int curPos = 0;
				while (rset.next()) {
					for (int i = 0; i < numberOfColumns; i++) {
						if (rset.getObject(i + 1) != null)
							if (rset.getObject(i + 1) instanceof Date) {
								SimpleDateFormat format = new SimpleDateFormat();
								format.applyPattern("yyyy-MM-dd");
								Date docDate = format.parse(rset.getObject(i + 1).toString());
								format.applyPattern("dd.MM.yyyy");
								data[curPos][i] = format.format(docDate);
							} else
								data[curPos][i] = rset.getObject(i + 1).toString();
					}
					curPos++;
				}
			}
			cstmt.close();
			rset.close();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return data;
	}

	public static String[][] getAllCompetitiveGroupsByAbiturientId(String aid) {
		String[][] data = null;

		try {
			cstmt = con.prepareCall("{call getAllAllCompetitiveGroupsByAbiturientId(?)}", 1004, 1007);

			cstmt.setString(1, aid);

			rset = cstmt.executeQuery();

			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			if (countStrings > 0) {
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
			}
			cstmt.close();
			rset.close();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return data;
	}

	public static String[] getAbiturientPassportByID(String aid) throws SQLException {
		try {
			cstmt = con.prepareCall("{call getAbiturientPassportByID(?)}", 1004, 1007);

			cstmt.setString(1, aid);

			rset = cstmt.executeQuery();

			ResultSetMetaData rsmd = rset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			String[] result = new String[numberOfColumns];
			result[0] = aid;

			while (rset.next()) {
				for (int i = 0; i < numberOfColumns; i++) {
					if (rset.getObject(i + 1) != null)
						if (rset.getObject(i + 1) instanceof Date) {
							SimpleDateFormat format = new SimpleDateFormat();
							format.applyPattern("yyyy-MM-dd");
							Date docDate = format.parse(rset.getObject(i + 1).toString());
							format.applyPattern("dd.MM.yyyy");
							result[i] = format.format(docDate);
						} else
							result[i] = rset.getObject(i + 1).toString();
				}
			}
			cstmt.close();
			rset.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static void updateAbiturientPassportByID(String aid, String[] data) throws SQLException {
		try {
			String query = "update Abiturient set Birthplace = '" + data[5] + "' where aid = " + aid + ";";

			String[] abiturientPassportInfo = new String[6];
			abiturientPassportInfo[0] = aid;
			for (int i = 1; i < abiturientPassportInfo.length; i++)
				abiturientPassportInfo[i] = data[i - 1];

			ModelDBConnection.updateElementInTableById("AbiturientPassport", abiturientPassportInfo);
			query = query.replaceAll("'null'", "null");
			System.out.println(query);
			stmt = con.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String[] getAbiturientAddressAndContactsByID(String aid) throws SQLException {
		try {
			cstmt = con.prepareCall("{call getAbiturientAddressAndContactsByID(?)}", 1004, 1007);
			cstmt.setString(1, aid);
			rset = cstmt.executeQuery();

			ResultSetMetaData rsmd = rset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			String[] result = new String[numberOfColumns];
			result[0] = aid;

			while (rset.next()) {
				for (int i = 0; i < numberOfColumns; i++) {
					if (rset.getObject(i + 1) != null)
						if (rset.getObject(i + 1) instanceof Date) {
							SimpleDateFormat format = new SimpleDateFormat();
							format.applyPattern("yyyy-MM-dd");
							Date docDate = format.parse(rset.getObject(i + 1).toString());
							format.applyPattern("dd.MM.yyyy");
							result[i] = format.format(docDate);
						} else
							result[i] = rset.getObject(i + 1).toString();
				}
			}
			cstmt.close();
			rset.close();
			return result;
		} catch (Exception e) {
			return null;
		}
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
			query = query.replaceAll("'null'", "null");
			System.out.println(query);
			stmt = con.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String[] getAbiturientEducationByID(String aid, String nameTable) throws SQLException {
		try {
			cstmt = con.prepareCall("{call getAbiturientEducationByID(?, ?)}", 1004, 1007);
			cstmt.setString(1, aid);
			cstmt.setString(2, nameTable);
			rset = cstmt.executeQuery();

			ResultSetMetaData rsmd = rset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			String[] result = new String[numberOfColumns];
			result[0] = aid;

			while (rset.next()) {
				for (int i = 0; i < numberOfColumns; i++) {
					if (rset.getObject(i + 1) != null)
						if (rset.getObject(i + 1) instanceof Date) {
							SimpleDateFormat format = new SimpleDateFormat();
							format.applyPattern("yyyy-MM-dd");
							Date docDate = format.parse(rset.getObject(i + 1).toString());
							format.applyPattern("dd.MM.yyyy");
							result[i] = format.format(docDate);
						} else
							result[i] = rset.getObject(i + 1).toString();
				}
			}
			cstmt.close();
			rset.close();
			return result;
		} catch (Exception e) {
			return null;
		}
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

			cstmt = con.prepareCall("{call getElementFromTableByIDs(?, ?, ?, ?, ?)}", 1004, 1007);

			cstmt.setString(1, table);
			cstmt.setString(2, id1);
			cstmt.setString(3, data[0]);
			cstmt.setString(4, id2);
			cstmt.setString(5, data[1]);

			rset = cstmt.executeQuery();

			ResultSetMetaData rsmd = rset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			String[] result = new String[numberOfColumns];
			for (int i = 0; i < result.length; i++)
				result[i] = "";

			while (rset.next()) {
				for (int i = 0; i < numberOfColumns; i++) {
					if (rset.getObject(i + 1) != null)
						result[i] = rset.getObject(i + 1).toString();
					System.out.println("Read: " + result[i]);
				}
			}
			cstmt.close();
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
			String query = "update Abiturient set needSpecConditions = '" + data[data.length - 1] + "' where aid = "
					+ data[0] + ";";

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

	public static int getFreeNumberInGroupByExam(String idEntranceTest, String testGroup) {
		if (initConnection()) {
			try {
				int freeNumber = 0;
				cstmt = con.prepareCall("{? = call getFreeNumberInGroup(?,?)}");

				cstmt.registerOutParameter(1, Types.INTEGER);
				cstmt.setString(2, idEntranceTest);
				cstmt.setString(3, testGroup);

				cstmt.execute();

				freeNumber = cstmt.getInt(1);
				// System.out.println(count);
				return freeNumber;
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return -1;
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		}

		return -1;
	}

	public static String[][] getAdmissionPlan() {
		String[][] data = null;

		try {
			cstmt = con.prepareCall("{call getAdmissionPlan()}", 1004, 1007);

			rset = cstmt.executeQuery();

			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			if (countStrings > 0) {
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
			}
			cstmt.close();
			rset.close();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return data;
	}

	public static boolean checkUser(String login, String password) {
		if (initConnection()) {
			try {
				boolean result;
				cstmt = con.prepareCall("{? = call checkUser(?, ?)}");

				cstmt.registerOutParameter(1, Types.INTEGER);
				cstmt.setString(2, login);
				cstmt.setString(3, password);

				cstmt.execute();

				result = cstmt.getBoolean(1);
				// System.out.println(count);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public static void updateCompetitiveBallsByID(String aid) throws SQLException {
		if (initConnection()) {
			cstmt = con.prepareCall("{call updateCompetitiveBallsByID(?)}");

			cstmt.setString(1, aid);
			cstmt.execute();

			cstmt.close();
		}
	}
	
	public static String[] getAllEntranceTest(){
		String[] data = null;
		try{
			cstmt = con.prepareCall("{? = call getAllEntranceTest}", 1004, 1007);
			cstmt.registerOutParameter(1, Types.INTEGER);
			rset = cstmt.executeQuery();
			
			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			if (countStrings > 0) {
				data = new String[countStrings];
				int cursor = 0;
				while(rset.next()){
					data[cursor] = rset.getObject(1).toString();
					cursor++;
				}
			}
					
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return data;
		
	}
	
	public static String[] getAllGroupEntranceTest(){
		String[] data = null;
		try{
			cstmt = con.prepareCall("{? = call getAllGroupEntranceTest}", 1004, 1007);
			cstmt.registerOutParameter(1, Types.INTEGER);
			rset = cstmt.executeQuery();
			
			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			if (countStrings > 0) {
				data = new String[1];
				int cursor = 0;
				while(rset.next()){
					data[cursor] = rset.getObject(1).toString();
					cursor++;
				}
			}
					
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return data;
		
	}
	
	public static String[][] getListSpecialityWithAbit(String idET, String group){		
		String[][] data = null;
		try {
			cstmt = con.prepareCall("{? = call listSpecialityWithAbit(?, ?)}", 1004, 1007);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, idET);
			cstmt.setString(3, group);
			
			rset = cstmt.executeQuery();
			
			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			if (countStrings > 0) {
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
			}
			
			cstmt.close();
			rset.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static String[][] getEntranceTestGroupsAbit(String group){		
		String[][] data = null;
		try {
			cstmt = con.prepareCall("{? = call getEntranceTestGroupsAbit(?)}", 1004, 1007);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, group);
			
			rset = cstmt.executeQuery();
			
			int countStrings = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			if (countStrings > 0) {
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
			}
			
			cstmt.close();
			rset.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return data;
	}
}
