use Aspirant;
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
	select aid, SName, FName, MName, Birthday, id_gender, id_nationality, registrationDate, id_returnReason, returnDate from Abiturient where aid = @aid
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

