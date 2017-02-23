use Ordinator;
go

create procedure getCount(@tableName varchar(max))
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

-- NEED TO CHECK IF @AID EXISTS
create procedure getCountForAbitID(@tableName varchar(max), @aid int)
as 
  begin
	if (object_id(@tableName) is null)
	begin
		  RAISERROR('Недопустимое имя таблицы', 16, 1)
	end

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

-- HOW TO RETURN?
create procedure getAllAbiturients
as begin

	declare @query varchar(max)
	set @query = 'select aid, SName, Fname, MName from Abiturient order by aid';
	execute('declare cur cursor for ' + @query)
	open cur
	WHILE @@FETCH_STATUS = 0  
	fetch next from cur
	close cur
	deallocate cur
end;

go