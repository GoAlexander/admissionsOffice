package outputDoc;

import java.io.File;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

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
			//System.out.println(query);
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
			//System.out.println(query);
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
					out.println("<EducationLevelID>18</EducationLevelID>");
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

	public static void printCompetitiveGroups() throws Exception {
		String query, moduleType = ModelDBConnection.getDBName().equals("Aspirant") ? "аспирантура" : "ординатура";

		Connection con = ModelDBConnection.getConnection();
		ResultSet rset = null;
		CallableStatement cstmt = null;

		String[][] allEntrtanceTests = ModelDBConnection.getAllFromTableOrderedById("EntranceTest");

		File file = new File(currentPath + "\\CompetitiveGroups_" + moduleType + ".txt");
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
			query = "select "
					+ "('g2' + cast(ISNULL(Speciality.codeDirection, '') as varchar) + '_' + "
					+ "	cast(YEAR(GETDATE()) as varchar) + cast(ISNULL(CompetitiveGroup.codeFIS, '') as varchar) +  "
					+ "cast(ISNULL(EducationForm.codeFIS, '') as varchar)"
					+ "),"
					+ "'2016-2',"
					+ "(cast(Speciality.name as varchar) + '_' + cast(ISNULL(TargetOrganisation.name, '') as varchar) + '_2' +"
					+ "cast(YEAR(GETDATE()) as varchar) + cast(ISNULL(CompetitiveGroup.codeFIS, '') as varchar) +  "
					+ "cast(ISNULL(EducationForm.codeFIS, '') as varchar)"
					+ "),"
					+ "CompetitiveGroup.codeFIS,"
					+ "EducationForm.codeFIS,"
					+ "Speciality.codeDirection,"
					+ "('op2' + cast(ISNULL(Speciality.codeDirection, '') as varchar) + "
					+ "cast(YEAR(GETDATE()) as varchar) + cast(ISNULL(CompetitiveGroup.codeFIS, '') as varchar) +  "
					+ "cast(ISNULL(EducationForm.codeFIS, '') as varchar)"
					+ "),"
					+ "Speciality.name,"
					+ "Speciality.codeByStandart,"
					+ "AdmissionPlan.placeCount,"
					+ "('corg2' + cast(ISNULL(Speciality.codeDirection, '') as varchar) + cast(YEAR(GETDATE()) as varchar))"
					+ "from (Speciality join AdmissionPlan on (Speciality.id = specialtyCode) "
					+ "join CompetitiveGroup on (CompetitiveGroup.id = competitiveGroup) "
					+ "join EducationForm on (EducationForm.id = educationForm)) left join TargetOrganisation on (TargetOrganisation.id = targetOrganisation)"
					+ "order by specialtyCode";
			//System.out.println(query);
			cstmt = con.prepareCall(query, 1004, 1007);
			rset = cstmt.executeQuery();
			int countOfCompGroups = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			if (countOfCompGroups > 0) {
				out.println("<PackageData>");
				out.println("<AdmissionInfo>");
				out.println("<CompetitiveGroups>");
				while (rset.next()) {
					out.println("<CompetitiveGroup>");
					out.println("<UID>" + rset.getString(1) + "1</UID>");
					out.println("<CampaignUID>" + rset.getString(2) + "</CampaignUID>");
					out.println("<Name>" + rset.getString(3) + "1</Name>");
					out.println("<EducationLevelID>18</EducationLevelID>");
					out.println("<EducationSourceID>" + rset.getString(4) + "</EducationSourceID>");
					out.println("<EducationFormID>" + rset.getString(5) + "</EducationFormID>");
					out.println("<DirectionID>" + rset.getString(6) + "</DirectionID>");
					out.println("<EduPrograms>");
					out.println("<EduProgram>");
					out.println("<UID>" + rset.getString(7) + "1</UID>");
					out.println("<Name>" + rset.getString(8) + "</Name>");
					out.println("<Code>" + rset.getString(9) + "</Code>");
					out.println("</EduProgram>");
					out.println("</EduPrograms>");

					switch(rset.getString(4)) {
					case "16":
						out.println("<TargetOrganizations>");
						out.println("<TargetOrganization>");
						out.println("<UID>" + rset.getString(11) + "1</UID>");
						out.println("<CompetitiveGroupTargetItem>");
						out.println("<NumberTargetO>" + rset.getString(10) + "</NumberTargetO>");
						out.println("</CompetitiveGroupTargetItem>");
						out.println("</TargetOrganization>");
						out.println("</TargetOrganizations>");
						break;
					case "15":
						out.println("<CompetitiveGroupItem>");
						out.println("<NumberPaidO>" + rset.getString(10) + "</NumberPaidO>");
						out.println("</CompetitiveGroupItem>");
						break;
					case "14":
						out.println("<CompetitiveGroupItem>");
						out.println("<NumberBudgetO>" + rset.getString(10) + "</NumberBudgetO>");
						out.println("</CompetitiveGroupItem>");
						break;
					}

					out.println("<EntranceTestItems>");
					for (int i = 0; i < allEntrtanceTests.length; i++){
						out.println("<EntranceTestItem>");
						out.println("<UID>" + rset.getString(1) + allEntrtanceTests[i][0] + "</UID>");
						out.println("<EntranceTestTypeID>1</EntranceTestTypeID>");
						out.println("<MinScore>" + allEntrtanceTests[i][2] + "</MinScore>");
						out.println("<EntranceTestPriority>" + allEntrtanceTests[i][0] + "</EntranceTestPriority>");
						out.println("<EntranceTestSubject>");
						out.println("<SubjectName>" + allEntrtanceTests[i][1] + "</SubjectName>");
						out.println("</EntranceTestSubject>");
						out.println("</EntranceTestItem>");
					}

					out.println("</EntranceTestItems>");
					out.println("</CompetitiveGroup>");
				}
				out.println("</CompetitiveGroups>");
				out.println("</AdmissionInfo>");
				out.println("</PackageData>");
			}
			out.println("</Root>");
			out.close();
			rset.close();
		}
	}

	public static void printAbiturientsAssesments() throws Exception {
		String query, query1, query2, moduleType = ModelDBConnection.getDBName().equals("Aspirant") ? "аспирантура" : "ординатура";

		Connection con = ModelDBConnection.getConnection();
		ResultSet rset, rset1, rset2 = null;
		CallableStatement cstmt, cstmt1, cstmt2 = null;

		String[][] allEntrtanceTests = ModelDBConnection.getAllFromTableOrderedById("EntranceTest");

		File file = new File(currentPath + "\\Abiturients_" + moduleType + "_" + (new SimpleDateFormat("dd.MM.yyyy").format(new Date())) + ".txt");
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
			query = "select aid,"
					+ "cast(aid as varchar) + '_2_' + cast(YEAR(GETDATE()) as varchar),"
					+ "Fname,"
					+ "MName,"
					+ "SName,"
					+ "Gender.codeFIS,"
					+ "email,"
					+ "Region.codeFIS,"
					+ "LocalityType.codeFIS,"
					+ "(cast(indexAddress as varchar) + ' ' + cast(factAddress as varchar)),"
					+ "registrationDate,"
					+ "(case when needHostel is null then '0' else '1' end),"
					+ "(case when returnDate is null then '4' else '1' end),"
					+ "registrationDate,"
					+ "AbiturientPassport.paspNumber,"
					+ "AbiturientPassport.paspSeries,"
					+ "AbiturientPassport.paspGivenDate,"
					+ "PassportType.codeFIS,"
					+ "Birthday,"
					+ "Nationality.codeFIS,"
					+ "AbiturientHigherEducation.diplomaSeries,"
					+ "AbiturientHigherEducation.diplomaNumber "
					+ "from Abiturient join Gender on (Gender.id = Abiturient.id_gender) "
					+ "join Nationality on (Nationality.id = Abiturient.id_nationality) "
					+ "join AbiturientAddress on (Abiturient.aid = AbiturientAddress.aid_abiturient) "
					+ "join Region on (Region.id = AbiturientAddress.id_region) "
					+ "join LocalityType on (LocalityType.id = AbiturientAddress.id_localityType) "
					+ "join AbiturientPassport on (Abiturient.aid = AbiturientPassport.aid_abiturient) "
					+ "join PassportType on (PassportType.id = AbiturientPassport.id_passportType) "
					+ "join AbiturientHigherEducation on (Abiturient.aid = AbiturientHigherEducation.aid_abiturient) ";
			//System.out.println(query);
			cstmt = con.prepareCall(query, 1004, 1007);
			rset = cstmt.executeQuery();
			int countOfAbiturientss = rset.last() ? rset.getRow() : 0;
			rset.beforeFirst();

			if (countOfAbiturientss > 0) {
				out.println("<PackageData>");
				out.println("<Applications>");
				while (rset.next()) {
					out.println("<Application>");
					out.println("<UID>" + rset.getString(2) + "</UID>");
					out.println("<ApplicationNumber>" + rset.getString(2) + "</ApplicationNumber>");
					out.println("<Entrant>");
					out.println("<UID>eo" + rset.getString(2) + "</UID>");
					out.println("<FirstName>" + rset.getString(3) + "</FirstName>");
					out.println("<MiddleName>" + rset.getString(4) + "</MiddleName>");
					out.println("<LastName>" + rset.getString(5) + "</LastName>");
					out.println("<GenderID>" + rset.getString(6) + "</GenderID>");
					out.println("<EmailOrMailAddress>");
					out.println("<Email>" + rset.getString(7) + "</Email>");
					out.println("<MailAddress>");
					out.println("<RegionID>" + rset.getString(8) + "</RegionID>");
					out.println("<TownTypeID>" + rset.getString(9) + "</TownTypeID>");
					out.println("<Address>" + rset.getString(10) + "</Address>");
					out.println("</MailAddress>");
					out.println("</EmailOrMailAddress>");
					out.println("</Entrant>");
					out.println("<RegistrationDate>" + rset.getString(11) + "T09:00:00</RegistrationDate>");
					out.println("<NeedHostel>" + rset.getString(12) + "</NeedHostel>");
					out.println("<StatusID>" + rset.getString(13) + "</StatusID>");
					out.println("<ApplicationDocuments>");
					out.println("<IdentityDocument>");
					out.println("<UID>id_" + rset.getString(2) + "</UID>");
					out.println("<OriginalReceivedDate>" + rset.getString(14) + "</OriginalReceivedDate>");
					out.println("<DocumentSeries>" + rset.getString(15) + "</DocumentSeries>");
					out.println("<DocumentNumber>" + rset.getString(16) + "</DocumentNumber>");
					out.println("<DocumentDate>" + rset.getString(17) + "</DocumentDate>");
					out.println("<IdentityDocumentTypeID>" + rset.getString(18) + "</IdentityDocumentTypeID>");
					out.println("<BirthDate>" + rset.getString(19) + "</BirthDate>");
					out.println("<NationalityTypeID>" + rset.getString(20) + "</NationalityTypeID>");
					out.println("</IdentityDocument>");
					out.println("<EduDocuments>");
					out.println("<EduDocument>");
					out.println("<HighEduDiplomaDocument>");
					out.println("<UID>ed_" + rset.getString(2) + "</UID>");
					out.println("<DocumentSeries>" + rset.getString(21) + "</DocumentSeries>");
					out.println("<DocumentNumber>" + rset.getString(22) + "</DocumentNumber>");
					out.println("</HighEduDiplomaDocument>");
					out.println("</EduDocument>");
					out.println("</EduDocuments>");
					out.println("</ApplicationDocuments>");
					
					query1 = "select "
							+ "('g2' + cast(ISNULL(Speciality.codeDirection, '') as varchar) + '_' +"
							+ "cast(YEAR(GETDATE()) as varchar) + cast(ISNULL(CompetitiveGroup.codeFIS, '') as varchar) + "
							+ "cast(ISNULL(EducationForm.codeFIS, '') as varchar)"
							+ ")"
							+ "from (Speciality join AbiturientCompetitiveGroup on (Speciality.id = competitiveGroup)"
							+ "join CompetitiveGroup on (CompetitiveGroup.id = competitiveGroup)"
							+ "join EducationForm on (EducationForm.id = educationForm)) left join TargetOrganisation on (TargetOrganisation.id = targetOrganisation)"
							+ "where AbiturientCompetitiveGroup.aid_abiturient = " + rset.getString(1)
							+ " order by speciality";
					//System.out.println(query1);
					cstmt1 = con.prepareCall(query1, 1004, 1007);
					rset1 = cstmt1.executeQuery();
					int countOfSpecialitiesForCurrentAbiturient = rset1.last() ? rset1.getRow() : 0;
					rset1.beforeFirst();
					if (countOfSpecialitiesForCurrentAbiturient > 0) {
						out.println("<FinSourceAndEduForms>");

						while (rset1.next()) {
							out.println("<FinSourceEduForm>");
							out.println("<CompetitiveGroupUID>" + rset1.getString(1) + "1</CompetitiveGroupUID>");
							out.println("</FinSourceEduForm>");
						}
						out.println("</FinSourceAndEduForms>");
					}

					cstmt1 = con.prepareCall(query1, 1004, 1007);
					rset1 = cstmt1.executeQuery();
					countOfSpecialitiesForCurrentAbiturient = rset1.last() ? rset1.getRow() : 0;
					rset1.beforeFirst();
					if (countOfSpecialitiesForCurrentAbiturient > 0) {
						query2 = "select "
								+ "score,"
								+ "'2',"
								+ "EntranceTest.name,"
								+ "'1',"
								+ "AssessmentBase.codeFIS,"
								+ "testDate "
								+ "from AbiturientEntranceTests join EntranceTest on (id_entranceTest = EntranceTest.id)"
								+ "join AssessmentBase on (id_assessmentBase = AssessmentBase.id)"
								+ "where AbiturientEntranceTests.aid_abiturient = " + rset.getString(1)
								+ " order by id_entranceTest";
						//System.out.println(query2);
						cstmt2 = con.prepareCall(query2, 1004, 1007);
						rset2 = cstmt2.executeQuery();
						int countOfEntranceTestsForCurrentAbiturient = rset2.last() ? rset2.getRow() : 0;
						rset2.beforeFirst();
						if (countOfEntranceTestsForCurrentAbiturient > 0) {
							out.println("<EntranceTestResults>");

							int currentCompGroup = 0;
							while (rset1.next()) {
								currentCompGroup++;
								int currentEntranceTestResult = 0;
								while (rset2.next()) {
									currentEntranceTestResult++;
									out.println("<EntranceTestResult>");
									out.println("<CompetitiveGroupUID>" + rset1.getString(1) + "1</CompetitiveGroupUID>");
									out.println("<UID>etr_" + rset.getString(2) + "_" + currentCompGroup + "_" + currentEntranceTestResult + "</UID>");
									out.println("<ResultValue>" + rset2.getString(1) + "</ResultValue>");
									out.println("<ResultSourceTypeID>" + rset2.getString(2) + "</ResultSourceTypeID>");
									out.println("<EntranceTestSubject>");
									out.println("<SubjectName>" + rset2.getString(3) + "</SubjectName>");
									out.println("</EntranceTestSubject>");
									out.println("<EntranceTestTypeID>" + rset2.getString(4) + "</EntranceTestTypeID>");
									out.println("<ResultDocument>");
									out.println("<InstitutionDocument>");
									out.println("<DocumentNumber>el_" + rset.getString(2) + "_" + currentCompGroup + "_" + currentEntranceTestResult + "</DocumentNumber>");
									out.println("<DocumentTypeID>" + rset2.getString(5) + "</DocumentTypeID>");
									out.println("<DocumentDate>" + rset2.getString(6) + "</DocumentDate>");
									out.println("</InstitutionDocument>");
									out.println("</ResultDocument>");
									out.println("</EntranceTestResult>");
								}
							}
							out.println("</EntranceTestResults>");
						}
					}
					out.println("</Application>");
				}

				out.println("</Applications>");
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
			printCompetitiveGroups();
			printAbiturientsAssesments();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
