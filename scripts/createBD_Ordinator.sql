create database Ordinator;
go

--указываем, что все дальнейшие запросы должны быть выполнены для БД ординатор
use Ordinator;
go

--Пол
create table Gender (
	id int primary key, --код
	name text,			--название пола (для отображения в интерфейсе)
	codeFIS text		--код выгрузки в ФИС
);
go

--Гражданство
create table Nationality (
	id int primary key, --код
	name text,			--название гражданства/страна (для отображения в интерфейсе)
	codeFIS text		--код выгрузки в ФИС
);
go

--Причины возврата документов
create table ReturnReasons (
	id int primary key, --код
	name text,			--наименование причины (для отображения в интерфейсе)
	codeFIS text		--код выгрузки в ФИС
);
go

--Абитуриент
create table Abiturient (
	aid int primary key,	--код/номер личного дела абитуриента
	SName text,				--Фамилия
	Fname text,				--Имя
	MName text,				--Отчество
	Birthday Date,			--Дата рождения
	Birthplace text,		--Место рождения
	id_gender int,			--код пола
	id_nationality int,		--код страны гражданства
	phoneNumbers text,		--телефоны
	needHostel int,			--метка "Нуждается в общежитии"
	registrationDate Date,	--дата подачи заявления
	returnDate Date,		--Дата возврата документов
	id_returnReason int,	--причина возврата документов
	needSpecConditions int,	--метка "Нуждается в специальных условиях вступительных испытаний"
	is_enrolled int,		--метка о зачислении
	
	--Внешние ключи
	foreign key (id_gender) references Gender(id),
	foreign key (id_nationality) references Nationality(id),
	foreign key (id_returnReason) references ReturnReasons(id)
);