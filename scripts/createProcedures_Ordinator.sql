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

	if (@needAllCollumns = 1 ) 
			execute('select * from AbiturientEntranceTests where aid_abiturient = ' + @aid)
	else
		begin
			if (@@ROWCOUNT > 0)
				execute ('select EntranceTest.name, testGroup, TestBox.name, testDate, AbiturientEntranceTests.score from EntranceTest, AbiturientEntranceTests, TestBox 
						where EntranceTest.id = AbiturientEntranceTests.id_entranceTest 
							and TestBox.id = AbiturientEntranceTests.id_testBox and aid_abiturient = ' + @aid)
			else 
				execute ('select EntranceTest.name, null, null, null, null from EntranceTest')
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