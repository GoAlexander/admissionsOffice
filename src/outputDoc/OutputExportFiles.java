﻿package outputDoc;

import java.io.File;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import backend.ModelDBConnection;

public class OutputExportFiles {
	private static String currentPath = new File("").getAbsolutePath();

	public static void printTargetOrganisations() throws Exception {
		String query, moduleType = ModelDBConnection.getDBName().equals("Aspirant") ? "аспирантура" : "ординатура";
		int previousTargOrg = 0, currentTargOrg = 0, currentId = 1;

		Connection con = ModelDBConnection.getConnection();
		ResultSet rset = null;
		CallableStatement cstmt = null;

		File file = new File(currentPath + "\\TargetOrgs_" + moduleType + ".txt");
        if(!file.exists()){
            file.createNewFile();
        }
        PrintWriter out = new PrintWriter(file.getAbsoluteFile());
        out.println("<Root>");
        out.println("<AuthData>");
        out.println("<Login>ulitinh@nizhgma.ru</Login>");
        out.println("<Pass>Grandopera200352</Pass>");
        out.println("</AuthData>");

		if (moduleType.equals("ординатура")) {
			query = "select specialtyCode, "
					+ "(cast(Speciality.name as varchar) + ': ' + cast(TargetOrganisation.name as varchar)), "
					+ "('corg2' + cast(ISNULL(Speciality.codeDirection, '') as varchar) + cast(YEAR(GETDATE()) as varchar)) "
					+ "from Speciality join AdmissionPlan on (Speciality.id = AdmissionPlan.specialtyCode) "
					+ "join TargetOrganisation on (TargetOrganisation.id = AdmissionPlan.targetOrganisation) "
					+ "order by specialtyCode, targetOrganisation";
			cstmt = con.prepareCall(query, 1004, 1007);
			rset = cstmt.executeQuery();
			int countOfTargOrgs = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			if (countOfTargOrgs > 0) {
				out.println("<PackageData>");
				out.println("<TargetOrganizations>");
				while (rset.next()) {
					currentTargOrg = rset.getInt(1);
					if (currentTargOrg != previousTargOrg) {
						currentId = 1;
						previousTargOrg = currentTargOrg;
					}
					System.out.println(rset.getString(2) + " " + rset.getString(3) + currentId);
					out.println("<TargetOrganization>");
					out.println("<Name>" + rset.getString(2) + "</Name>");
					out.println("<UID>" + rset.getString(3) + currentId + "</UID>");
					out.println("</TargetOrganization>");
					currentId++;
				}
				out.println("</TargetOrganizations>");
				out.println("</PackageData>");
			}
			out.println("</Root>");
			out.close();
			rset.close();
		}
	}

	public static void printAssesmentPlanGeneral() throws Exception {
		String query, moduleType = ModelDBConnection.getDBName().equals("Aspirant") ? "аспирантура" : "ординатура";

		Connection con = ModelDBConnection.getConnection();
		ResultSet rset = null;
		CallableStatement cstmt = null;

		File file = new File(currentPath + "\\AssesmentPlanGeneral_" + moduleType + ".txt");
        if(!file.exists()){
            file.createNewFile();
        }
        PrintWriter out = new PrintWriter(file.getAbsoluteFile());
        out.println("<Root>");
        out.println("<AuthData>");
        out.println("<Login>login</Login>");
        out.println("<Pass>password</Pass>");
        out.println("</AuthData>");

		if (moduleType.equals("ординатура")) {
			query = "select	cast(ISNULL(Speciality.codeDirection, '') as varchar) + '_18' + cast(YEAR(GETDATE()) as varchar),"
					+ "cast(YEAR(GETDATE()) as varchar) + '-2',"
					+ "Speciality.codeDirection,"
					+ "(select ISNULL(sum(AdmissionPlan.placeCount), 0) from AdmissionPlan where specialtyCode = Speciality.id and competitiveGroup = 2),"
					+ "(select ISNULL(sum(AdmissionPlan.placeCount), 0) from AdmissionPlan where specialtyCode = Speciality.id and competitiveGroup = 3),"
					+ "(select ISNULL(sum(AdmissionPlan.placeCount), 0) from AdmissionPlan where specialtyCode = Speciality.id and competitiveGroup = 1),"
					+ "0"
					+ "from Speciality order by Speciality.id";
			cstmt = con.prepareCall(query, 1004, 1007);
			rset = cstmt.executeQuery();
			int countOfTargOrgs = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			if (countOfTargOrgs > 0) {
				out.println("<PackageData>");
				out.println("<AdmissionInfo>");
				out.println("<AdmissionVolume>");
				while (rset.next()) {
					out.println("<Item>");
					out.println("<UID>" + rset.getString(1) + "</UID>");
					out.println("<CampaignUID>" + rset.getString(2) + "</CampaignUID>");
					out.println("<CampaignUID>18</CampaignUID>");
					out.println("<DirectionID>" + rset.getString(3) + "</DirectionID>");
					out.println("<NumberBudgetO>" + rset.getString(4) + "<NumberBudgetO>");
					out.println("<NumberPaidO>" + rset.getString(5) + "</NumberPaidO>");
					out.println("<NumberTargetO>" + rset.getString(6) + "</NumberTargetO>");
					out.println("<NumberQuotaO>" + rset.getString(7) + "</NumberQuotaO>");
					out.println("</Item>");
				}
				out.println("</AdmissionVolume>");
				out.println("</AdmissionInfo>");
				out.println("</PackageData>");
			}
			out.println("</Root>");
			out.close();
			rset.close();
		}
	}

	public static void main(String[] args) {
		try {
			ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Ordinator", "user", "password");
			ModelDBConnection.initConnection();
			printTargetOrganisations();
			printAssesmentPlanGeneral();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
