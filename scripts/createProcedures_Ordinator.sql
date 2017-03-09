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