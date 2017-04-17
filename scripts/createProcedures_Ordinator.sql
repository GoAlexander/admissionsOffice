use Ordinator;
go

-- CHANGE ALTER TO CREATE WHEN RUNNING FOR THE FIRST TIME
alter procedure getCount(@tableName varchar(max))
as begin
	if (object_id(@tableName) is null)
	begin
		  RAISERROR('Недопустимое имя таблицы', 16, 1)
	end

	declare @query varchar(max), @countOf int
	set @query = 'select count(*) from ' + @tableName 
	execute('declare cur cursor for ' + @query)
	open cur
	fetch next from cur into @countOf
	close cur
	deallocate cur
	return @countOf
end;
go

alter procedure getCountForAbitID(@tableName varchar(max), @aid varchar(max))
as
  begin
	if (object_id(@tableName) is null)
	begin
		  RAISERROR('Недопустимое имя таблицы', 16, 1)
	end

	execute('select * from ' +  @tableName + ' where aid_abiturient = ' + @aid)
	if @@ROWCOUNT = 0
		return 0;

	declare @query varchar(max), @countOf int
	set @query = 'select count(*) from ' + @tableName + ' where aid_abiturient = ' + @aid
	execute('declare cur cursor for ' + @query)
	open cur
	fetch next from cur into @countOf
	close cur
	deallocate cur
	return @countOf
end;
go

alter procedure deleteElementInTableById(@tableName varchar(max), @name_id varchar(max), @value_id varchar(max))
as
begin
	if (object_id(@tableName) is null)
	begin
		  RAISERROR('Недопустимое имя таблицы', 16, 1)
	end

	execute('delete from ' +  @tableName + ' where ' + @name_id + ' = ' + @value_id)
end;
go

alter procedure deleteElementInTableByIds(@tableName varchar(max), @name_id1 varchar(max), @value_id1 varchar(max), @name_id2 varchar(max), @value_id2 varchar(max))
as
begin
	if (object_id(@tableName) is null)
	begin
		  RAISERROR('Недопустимое имя таблицы', 16, 1)
	end

	execute('delete from ' +  @tableName + ' where ' + @name_id1 + ' = ' + @value_id1 + ' and ' + @name_id2 + ' = ' + @value_id2)
end;
go

alter procedure deleteAbiturient(@aid varchar(max))
as
begin
execute('delete from Abiturient where aid =' + @aid)
end;
go


alter procedure getAllAchievmentsByAbiturientId(@aid varchar(max), @needAllCollumns bit)
AS BEGIN
	if @needAllCollumns = 1
		begin
			select * from AbiturientIndividualAchievement where aid_abiturient = + @aid
		end
	else
		begin
			select name, AbiturientIndividualAchievement.score from IndividualAchievement, AbiturientIndividualAchievement 
				where IndividualAchievement.id = AbiturientIndividualAchievement.id_individual_achievement 
					and aid_abiturient =  @aid
		end
	return
end;
go

alter procedure getAllFromTable (@tableName varchar(max), @name_id varchar(max))
as begin
	if (object_id(@tableName) is null)
	begin
		  RAISERROR('Недопустимое имя таблицы', 16, 1)
	end
	if (@name_id is null)
		begin
			execute('select * from ' +  @tableName)
		end
	else
		begin
			execute('select * from ' +  @tableName + ' order by ' + @name_id)
		end
	return
end;
go

alter procedure getAllEntranceTestsResultsByAbiturientId(@aid varchar(max), @needAllCollumns bit)
as begin
	if (@needAllCollumns = 1) 
		begin
			select * from AbiturientEntranceTests where aid_abiturient = + @aid
		end
	else
		begin
			if not exists(select * from AbiturientEntranceTests where aid_abiturient = + @aid)
				begin
					select EntranceTest.name, null, null, null, null from EntranceTest
				end
			else
				begin
					select EntranceTest.name, testGroup, TestBox.name, testDate, AbiturientEntranceTests.score from EntranceTest, AbiturientEntranceTests, TestBox 
						where EntranceTest.id = AbiturientEntranceTests.id_entranceTest 
							and TestBox.id = AbiturientEntranceTests.id_testBox and aid_abiturient = + @aid
					union all
					select EntranceTest.name, testGroup, null, testDate, AbiturientEntranceTests.score from EntranceTest, AbiturientEntranceTests
						where EntranceTest.id = AbiturientEntranceTests.id_entranceTest 
							and AbiturientEntranceTests.id_testBox is null and aid_abiturient = + @aid
				end
		end
	return
end;
go

alter procedure getElementFromTableByIDs(@tableName varchar(max), @name_id1 varchar(max), @value_id1 varchar(max), @name_id2 varchar(max), @value_id2 varchar(max))
as
begin
	if (object_id(@tableName) is null)
	begin
		  RAISERROR('Недопустимое имя таблицы', 16, 1)
	end

	execute('select * from ' +  @tableName + ' where ' + @name_id1 + ' = ' + @value_id1 + ' and ' + @name_id2 + ' = ' + @value_id2)
	return
end;
go

alter procedure getNamesFromTableOrderedById(@tableName varchar(max))
as
begin
	if (object_id(@tableName) is null)
	begin
		  RAISERROR('Недопустимое имя таблицы', 16, 1)
	end

	execute('select name from ' + @tableName + ' order by id')
	return
end;
go

alter procedure getAllAbiturients
as
begin
	select aid, SName, Fname, MName from Abiturient order by aid
	return
end;
go

alter procedure getAbiturientGeneralInfoByID(@aid varchar(max))
as
begin
	select aid, SName, FName, MName, Birthday, id_gender, id_nationality, registrationDate, id_returnReason, returnDate, needHostel from Abiturient where aid = @aid
	return
end;
go

alter procedure getAbiturientPassportByID(@aid varchar(max))
as
begin
	select aid_abiturient, id_passportType, paspSeries, paspNumber, paspGivenBy, paspGivenDate, Birthplace 
				from Abiturient, AbiturientPassport where Abiturient.aid = AbiturientPassport.aid_abiturient 
				and aid_abiturient = @aid
	return
end;
go

alter procedure getAbiturientAddressAndContactsByID(@aid varchar(max))
as
begin
	select aid_abiturient, id_region, id_localityType, indexAddress, factAddress, email, phoneNumbers
				from Abiturient, AbiturientAddress where Abiturient.aid = AbiturientAddress.aid_abiturient
				and aid_abiturient = @aid
	return
end;
go

alter procedure getAbiturientEducationByID(@aid varchar(max), @tableName varchar(max))
as
begin
	if (object_id(@tableName) is null)
	begin
		  RAISERROR('Недопустимое имя таблицы', 16, 1)
	end

	execute('select aid_abiturient, diplomaSeries, diplomaNumber, diplomaSpeciality, instituteName, graduationYear, avgBall
				from ' + @tableName + ' where aid_abiturient = ' + @aid)
	return
end;
go


alter procedure getFreeNumberInGroup(@id_entranceTest varchar(max), @testGroup varchar(max))
as
begin
	declare @query varchar(max), @freeNumber int, @curPos int

	set @freeNumber = 1
	set @curPos = 1
	set @query = 'select indexNumber from AbiturientEntranceTests 
				where id_entranceTest = ' + @id_entranceTest + ' and (testGroup like ''' + @testGroup +
				''') order by indexNumber'

	execute('declare cur cursor for ' + @query)
	open cur
	fetch next from cur into @freeNumber
	while @@FETCH_STATUS=0
	begin
		if (@freeNumber <> @curPos)
		begin
			set @freeNumber = @curPos
			break
		end
		set @curPos = @curPos + 1
		fetch next from cur into @freeNumber
	end
	close cur
	deallocate cur
	
	if (@curPos > 20)
		begin
			set @curPos = -1
		end
	
	return @curPos
end;
go

alter procedure getAdmissionPlan
as
begin
	select Course.name, Speciality.name, EducationForm.name, CompetitiveGroup.name, TargetOrganisation.name, EducationStandard.name, AdmissionPlan.placeCount 
		from (((((AdmissionPlan left outer join  Speciality on (specialtyCode = Speciality.id)) left outer join 
				EducationForm on (educationForm = EducationForm.id)) left outer join 
				CompetitiveGroup on (competitiveGroup = CompetitiveGroup.id)) left outer join 
				EducationStandard on (educationStandard = EducationStandard.id)) left outer join 
				TargetOrganisation on (targetOrganisation = TargetOrganisation.id)) left outer join 
				Course on (course = Course.id)
			order by course, specialtyCode, educationForm, competitiveGroup, targetOrganisation, educationStandard
	return
end;
go

alter procedure checkUser(@login varchar(max), @password varchar(max))
as begin
	select * from Users where userLogin like @login and userPassword like @password

	if (@@ROWCOUNT > 0)
		begin
			return 1
		end
	else
		begin
			return 0
		end
end;
go

alter procedure getAllAllCompetitiveGroupsByAbiturientId(@aid varchar(max))
as begin
	select	aid_abiturient, course, speciality, educationForm, chair, competitiveGroup, targetOrganisation, 
			educationStandard, competitiveBall, availabilityIndividualAchievements, originalsReceivedDate, markEnrollment, Course.name, Speciality.name, CompetitiveGroup.name
		from	Abiturient, AbiturientCompetitiveGroup, Course, Speciality, CompetitiveGroup 
			where	Abiturient.aid = AbiturientCompetitiveGroup.aid_abiturient and 
					Course.id = AbiturientCompetitiveGroup.course and 
					Speciality.id = AbiturientCompetitiveGroup.speciality and 
					CompetitiveGroup.id = AbiturientCompetitiveGroup.competitiveGroup and aid_abiturient = @aid
	return
end;
go

alter procedure updateCompetitiveBallsByID(@aid varchar(max))
as
begin
	if not exists(select * from AbiturientEntranceTests where aid_abiturient = @aid)
		begin
			update AbiturientCompetitiveGroup set competitiveBall = null where aid_abiturient = @aid
		end
	else
		begin
			update AbiturientCompetitiveGroup set competitiveBall = (select sum(score) from AbiturientEntranceTests  where aid_abiturient = @aid) where aid_abiturient = @aid
		end

	if not exists(select * from AbiturientIndividualAchievement where aid_abiturient = @aid)
		begin
			update AbiturientCompetitiveGroup set availabilityIndividualAchievements = null where aid_abiturient = @aid
		end
	else
		begin
			update AbiturientCompetitiveGroup set availabilityIndividualAchievements = 1 where aid_abiturient = @aid
			update AbiturientCompetitiveGroup set competitiveBall = competitiveBall + (select sum(score) from AbiturientIndividualAchievement  where aid_abiturient = @aid) where aid_abiturient = @aid
		end
end;
go

alter procedure getAllGroupsNames
as begin
	select distinct testGroup from AbiturientEntranceTests
	return
end;
go

alter procedure getListAbiturientsByEntranceTestAndGroupIDs(@idET varchar(max), @group varchar(max))
as begin
	select Abiturient.SName, Abiturient.FName, Abiturient.MName, AbiturientEntranceTests.score, AbiturientEntranceTests.testDate, AbiturientPassport.paspSeries, AbiturientPassport.paspNumber, AbiturientEntranceTests.indexNumber
		from (Abiturient join AbiturientPassport on (Abiturient.aid = AbiturientPassport.aid_abiturient)) left outer join 
			AbiturientEntranceTests on (AbiturientEntranceTests.aid_abiturient = Abiturient.aid) left outer join
			EntranceTest on (AbiturientEntranceTests.id_entranceTest = EntranceTest.id)
				where AbiturientEntranceTests.id_entranceTest = @idET and AbiturientEntranceTests.testGroup = @group
				order by AbiturientEntranceTests.indexNumber
			
	return
end;
go

alter procedure getEntranceTestGroupsAbit(@group varchar(max))
as begin
	select distinct Abiturient.SName, Abiturient.FName, Abiturient.MName, AbiturientPassport.paspSeries, AbiturientPassport.paspNumber  
		from Abiturient  left outer join 
			AbiturientEntranceTests on (AbiturientEntranceTests.aid_abiturient = Abiturient.aid) left outer join
			AbiturientPassport on (Abiturient.aid = AbiturientPassport.aid_abiturient)
				where AbiturientEntranceTests.testGroup = @group
			
	return
end;
go

alter procedure getStatisticsGZGU(@count bit)
as begin
	if @count = 0
		begin
			select Speciality.name as 'Специальность', EducationForm.name as 'Форма обучения', 
			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup
			where competitiveGroup = 2 and targetOrganisation is NULL and 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id) as 'Всего бюджетников',
			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup
			where competitiveGroup = 1 and targetOrganisation is not NULL and 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id) as 'Всего целевиков', 
			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup
			where competitiveGroup = 3 and 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id) as 'Всего коммерсантов',
 
			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup
			where competitiveGroup = 2 and targetOrganisation is NULL and markEnrollment > 0 and 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id) as 'Кол-во зачисленных бюджетников',
			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup
			where competitiveGroup = 1 and targetOrganisation is not NULL and markEnrollment > 0 and 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id) as 'Кол-во зачисленных целевиков', 
			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup
			where competitiveGroup = 3 and markEnrollment > 0 and  
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id) as 'Кол-во зачисленных коммерсантов'

			from Speciality, EducationForm order by Speciality.id, EducationForm.id;
		end
	else
		begin
			select Course.name as 'Направление', EducationForm.name as 'Форма обучения',
			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup
			where competitiveGroup = 2 and targetOrganisation is NULL and 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id) as 'Всего бюджетников', 
			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup
			where competitiveGroup = 1 and targetOrganisation is not NULL and 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id) as 'Всего целевиков', 
			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup
			where competitiveGroup = 3 and 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id) as 'Всего коммерсантов',
 
			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup
			where competitiveGroup = 2 and targetOrganisation is NULL and markEnrollment > 0 and 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id) as 'Кол-во зачисленных бюджетников', 
			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup
			where competitiveGroup = 1 and targetOrganisation is not NULL and markEnrollment > 0 and 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id) as 'Кол-во зачисленных целевиков', 
			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup
			where competitiveGroup = 3 and markEnrollment > 0 and  
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id) as 'Всего коммерсантов'

			from Course, EducationForm order by Course.id, EducationForm.id;
		end
			
	return
end;
go


alter procedure getStatisticsMinZdrav(@count bit)
as begin
	if @count = 0
		begin
			select Speciality.name as 'Специальность', EducationForm.name as 'Форма обучения', TargetOrganisation.name as 'Наименование целевой организации',
			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup
			where competitiveGroup = 1 and targetOrganisation is not NULL and 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id) as 'Кол-во поданных целевых заявлений',
			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup
			where competitiveGroup = 1 and targetOrganisation is not NULL and markEnrollment > 0 and 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id) as 'Кол-во зачисленных'
			from Speciality, EducationForm, TargetOrganisation order by Speciality.id, EducationForm.id, TargetOrganisation.id;
		end
	else
		begin
			select Course.name as 'Направление', EducationForm.name as 'Форма обучения', TargetOrganisation.name as 'Наименование целевой организации',
			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup
			where competitiveGroup = 1 and targetOrganisation is not NULL and 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id) as 'Кол-во поданных целевых заявлений',
			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup
			where competitiveGroup = 1 and targetOrganisation is not NULL and markEnrollment > 0 and 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id) as 'Кол-во зачисленных'
			from Course, EducationForm, TargetOrganisation order by Course.id, EducationForm.id, TargetOrganisation.id;
		end
			
	return
end;
go



alter procedure getStatisticsRegionFull_SubmittedDocuments(@count bit)
as begin
	if @count = 0
		begin
			select Speciality.name as 'Специальность', EducationForm.name as 'Форма обучения', CompetitiveGroup.name as 'Источник', Region.name as 'Регион', 

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientAddress.id_region = Region.id) as 'Всего заявлений',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where targetOrganisation is not NULL and
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientAddress.id_region = Region.id) as 'Заявлений целевиков'

			from Speciality, EducationForm, CompetitiveGroup, Region order by Speciality.id, EducationForm.id, CompetitiveGroup.id, Region.id ;
		end
	else
		begin
			select Course.name as 'Направление', EducationForm.name as 'Форма обучения', CompetitiveGroup.name as 'Источник', Region.name as 'Регион', 

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientAddress.id_region = Region.id) as 'Всего заявлений',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where targetOrganisation is not NULL and
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientAddress.id_region = Region.id) as 'Заявлений целевиков'

			from Course, EducationForm, CompetitiveGroup, Region order by Course.id, EducationForm.id, CompetitiveGroup.id, Region.id ;

		end
			
	return
end;
go



alter procedure getStatisticsRegionFull_Enrolled(@count bit)
as begin
	if @count = 0
		begin
			select Speciality.name as 'Специальность', EducationForm.name as 'Форма обучения', CompetitiveGroup.name as 'Источник', Region.name as 'Регион', 

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where markEnrollment > 0 and
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientAddress.id_region = Region.id) as 'Всего зачислено',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where targetOrganisation is not NULL and markEnrollment > 0 and
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientAddress.id_region = Region.id) as 'Зачислено целевиков'

			from Speciality, EducationForm, CompetitiveGroup, Region order by Speciality.id, EducationForm.id, CompetitiveGroup.id, Region.id ;
		end
	else
		begin
			select Course.name as 'Направление', EducationForm.name as 'Форма обучения', CompetitiveGroup.name as 'Источник', Region.name as 'Регион', 

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where markEnrollment > 0 and
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientAddress.id_region = Region.id) as 'Всего заявлений',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where targetOrganisation is not NULL and markEnrollment > 0 and
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm=EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientAddress.id_region = Region.id) as 'Заявлений целевиков'

			from Course, EducationForm, CompetitiveGroup, Region order by Course.id, EducationForm.id, CompetitiveGroup.id, Region.id ;

		end
			
	return
end;
go


alter procedure getStatisticsRegionShort(@count bit)
as begin
	if @count = 0
		begin
			select Speciality.id, Speciality.name as 'Специальность', EducationForm.id, EducationForm.name as 'Форма обучения', CompetitiveGroup.id, CompetitiveGroup.name as 'Источник', TargetOrganisation.id, TargetOrganisation.name as 'Целевая организация', 

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region = 1 and AbiturientAddress.factAddress LIKE 'Н%Новгород') as 'Подано заявлений из НН',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region = 1 and AbiturientAddress.factAddress NOT LIKE 'Н%Новгород') as 'Подано заявлений из Нижегородской обл',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region <> 1) as 'Подано заявлений не из НН',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region = 1 and AbiturientAddress.factAddress LIKE 'Н%Новгород' and markEnrollment>0) as 'Зачислено из НН',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region = 1 and AbiturientAddress.factAddress NOT LIKE 'Н%Новгород' and markEnrollment>0) as 'Зачислено из Нижегородской обл',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region <> 1 and markEnrollment>0) as 'Зачислено не из НН'

			from Speciality, EducationForm, CompetitiveGroup, TargetOrganisation

			UNION ALL

			select Speciality.id, Speciality.name as 'Специальность', EducationForm.id, EducationForm.name as 'Форма обучения', CompetitiveGroup.id, CompetitiveGroup.name as 'Источник', TargetOrganisation.id, TargetOrganisation = NULL,

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			(AbiturientCompetitiveGroup.competitiveGroup = 2 or AbiturientCompetitiveGroup.competitiveGroup = 3) and
			AbiturientAddress.id_region = 1 and AbiturientAddress.factAddress LIKE 'Н%Новгород') as 'Подано заявлений из НН',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			(AbiturientCompetitiveGroup.competitiveGroup = 2 or AbiturientCompetitiveGroup.competitiveGroup = 3) and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region = 1 and AbiturientAddress.factAddress NOT LIKE 'Н%Новгород') as 'Подано заявлений из Нижегородской обл',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			(AbiturientCompetitiveGroup.competitiveGroup = 2 or AbiturientCompetitiveGroup.competitiveGroup = 3) and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region <> 1) as 'Подано заявлений не из НН',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			(AbiturientCompetitiveGroup.competitiveGroup = 2 or AbiturientCompetitiveGroup.competitiveGroup = 3) and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region = 1 and AbiturientAddress.factAddress LIKE 'Н%Новгород' and markEnrollment>0) as 'Зачислено из НН',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			(AbiturientCompetitiveGroup.competitiveGroup = 2 or AbiturientCompetitiveGroup.competitiveGroup = 3) and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region = 1 and AbiturientAddress.factAddress NOT LIKE 'Н%Новгород' and markEnrollment>0) as 'Зачислено из Нижегородской обл',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.speciality = Speciality.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			(AbiturientCompetitiveGroup.competitiveGroup = 2 or AbiturientCompetitiveGroup.competitiveGroup = 3) and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region <> 1 and markEnrollment>0) as 'Зачислено не из НН'

			from Speciality, EducationForm, CompetitiveGroup, TargetOrganisation order by Speciality.id, EducationForm.id, CompetitiveGroup.id, TargetOrganisation.id ;
		end
	else
		begin
			select Course.id, Course.name as 'Специальность', EducationForm.id, EducationForm.name as 'Форма обучения', CompetitiveGroup.id, CompetitiveGroup.name as 'Источник', TargetOrganisation.id, TargetOrganisation.name as 'Целевая организация', 

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region = 1 and AbiturientAddress.factAddress LIKE 'Н%Новгород') as 'Подано заявлений из НН',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region = 1 and AbiturientAddress.factAddress NOT LIKE 'Н%Новгород') as 'Подано заявлений из Нижегородской обл',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region <> 1) as 'Подано заявлений не из НН',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region = 1 and AbiturientAddress.factAddress LIKE 'Н%Новгород' and markEnrollment>0) as 'Зачислено из НН',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region = 1 and AbiturientAddress.factAddress NOT LIKE 'Н%Новгород' and markEnrollment>0) as 'Зачислено из Нижегородской обл',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			AbiturientCompetitiveGroup.competitiveGroup = CompetitiveGroup.id and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region <> 1 and markEnrollment>0) as 'Зачислено не из НН'

			from Course, EducationForm, CompetitiveGroup, TargetOrganisation

			UNION ALL

			select Course.id, Course.name as 'Специальность', EducationForm.id, EducationForm.name as 'Форма обучения', CompetitiveGroup.id, CompetitiveGroup.name as 'Источник', TargetOrganisation.id, TargetOrganisation = NULL,

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			(AbiturientCompetitiveGroup.competitiveGroup = 2 or AbiturientCompetitiveGroup.competitiveGroup = 3) and
			AbiturientAddress.id_region = 1 and AbiturientAddress.factAddress LIKE 'Н%Новгород') as 'Подано заявлений из НН',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			(AbiturientCompetitiveGroup.competitiveGroup = 2 or AbiturientCompetitiveGroup.competitiveGroup = 3) and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region = 1 and AbiturientAddress.factAddress NOT LIKE 'Н%Новгород') as 'Подано заявлений из Нижегородской обл',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			(AbiturientCompetitiveGroup.competitiveGroup = 2 or AbiturientCompetitiveGroup.competitiveGroup = 3) and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region <> 1) as 'Подано заявлений не из НН',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			(AbiturientCompetitiveGroup.competitiveGroup = 2 or AbiturientCompetitiveGroup.competitiveGroup = 3) and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region = 1 and AbiturientAddress.factAddress LIKE 'Н%Новгород' and markEnrollment>0) as 'Зачислено из НН',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			(AbiturientCompetitiveGroup.competitiveGroup = 2 or AbiturientCompetitiveGroup.competitiveGroup = 3) and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region = 1 and AbiturientAddress.factAddress NOT LIKE 'Н%Новгород' and markEnrollment>0) as 'Зачислено из Нижегородской обл',

			(select count (AbiturientCompetitiveGroup.aid_abiturient) from AbiturientCompetitiveGroup, AbiturientAddress
			where 
			AbiturientCompetitiveGroup.course = Course.id and 
			AbiturientCompetitiveGroup.educationForm = EducationForm.id and
			(AbiturientCompetitiveGroup.competitiveGroup = 2 or AbiturientCompetitiveGroup.competitiveGroup = 3) and 
			AbiturientCompetitiveGroup.targetOrganisation = TargetOrganisation.id and
			AbiturientAddress.id_region <> 1 and markEnrollment>0) as 'Зачислено не из НН'

			from Course, EducationForm, CompetitiveGroup, TargetOrganisation order by Course.id, EducationForm.id, CompetitiveGroup.id, TargetOrganisation.id ;

		end
			
	return
end;
go

