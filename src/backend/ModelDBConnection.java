package backend;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
							+ "@projectownserver;password=" + password
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
				String query = "select count(*) from " + tableName;
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

	// TODO test
	public static void insertAbiturient(String[] info) {
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
			try {
				stmt = con.createStatement();
				stmt.executeUpdate(query);

				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// TODO test
	public static void editAbiturient(String[] info) {

		String aid, SName, FName, MName, birthday, birthplace, id_gender, id_nationality, email, phoneNumbers,
				needHostel, registrationDate, returnDate, id_returnReason, needSpecConditions, is_enrolled;
		String query;

		query = aid = SName = FName = MName = birthday = birthplace = id_gender = id_nationality = email = phoneNumbers = needHostel = registrationDate = returnDate = id_returnReason = needSpecConditions = is_enrolled = null;

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

		query = "update Abiturient set SName = " + SName + ", FName = " + FName + ", MName = " + MName + ", Birthday = "
				+ birthday + ", Birthplace = " + birthplace + ", id_gender = " + id_gender + ", id_nationality = "
				+ id_nationality + ", email = " + email + ", phoneNumbers = " + phoneNumbers + ", needHostel = "
				+ needHostel + ", registrationDate = " + registrationDate + ", returnDate = " + returnDate
				+ ", id_returnReason = " + id_returnReason + ", needSpecConditions = " + needSpecConditions
				+ ", is_enrolled = " + is_enrolled + " where aid = " + aid + ";";

		if (initConnection()) {
			try {
				stmt = con.createStatement();
				stmt.executeUpdate(query);

				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// TODO test
	public static void deleteAbiturient(String aid) {
		try {
			String query = "delete from Abiturient where aid = " + aid + ";";
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			stmt.close();
			rset.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
