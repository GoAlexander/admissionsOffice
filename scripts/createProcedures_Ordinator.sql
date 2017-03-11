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

alter procedure getAllAchievmentsByAbiturientId(@aid varchar(max), @needAllCollumns bit)
AS BEGIN
	if @needAllCollumns = 1
		begin
			execute ('select * from AbiturientIndividualAchievement where aid_abiturient = ' + @aid)
		end
	else
		begin
			execute ('select name, AbiturientIndividualAchievement.score from IndividualAchievement, AbiturientIndividualAchievement 
						where IndividualAchievement.id = AbiturientIndividualAchievement.id_individual_achievement 
							and aid_abiturient = ' + @aid)
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

-- TODO Автоматическое присваивание номера, исходя из свободных мест в группе!!!
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

drop procedure getAllAbiturients
go

create procedure getAllAbiturients
as
begin
	select aid, SName, Fname, MName from Abiturient order by aid
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
		print '!!!'
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