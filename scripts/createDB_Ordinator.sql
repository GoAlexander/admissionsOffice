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
go

--Индивидуальные достижения
create table IndividualAchievement (
	id int primary key, --код
	name text,			--наименование индивидуального достижения
	score int,			--балл
	codeFIS text		--код выгрузки в ФИС
);
go

--Абитуриент_Индивидуальные достижения
create table AbiturientIndividualAchievement (
	aid_abiturient int,					--код/номер личного дела абитуриента
	id_individual_achievement int,		--код индивидуального достижения
	documentName text,					--название документа
	documentSeries text,				--серия документа
	documentNumber text,				--номер документа
	issuedBy text,						--кем выдан
	issueDate Date,						--дата выдачи
	
	--Внешние ключи
	foreign key (aid_abiturient) references Abiturient(aid),
	foreign key (id_individual_achievement) references IndividualAchievement(id)
);
go

--Абитуриент_Последипломное образование
create table AbiturientPostgraduateEducation (
	aid_abiturient int,					--код/номер личного дела абитуриента
	diplomaSeries text,					--серия диплома
	diplomaNumber text,					--номер диплома
	diplomaSpeciality text,				--специальность диплома
	instituteName text,					--названиеВУЗа
	graduationYear int,					--год окончания
	
	--Внешние ключи
	foreign key (aid_abiturient) references Abiturient(aid)
);
go

--Абитуриент_Высшее образование
create table AbiturientHigherEducation (
	aid_abiturient int,					--код/номер личного дела абитуриента
	diplomaSeries text,					--серия диплома
	diplomaNumber text,					--номер диплома
	diplomaSpeciality text,				--специальность диплома
	instituteName text,					--названиеВУЗа
	graduationYear int,					--год окончания
	
	--Внешние ключи
	foreign key (aid_abiturient) references Abiturient(aid)
);
go

--Вступительные испытания
create table EntranceTest (
	id int primary key, --код
	name text,			--наименование вступительного испытания
	minScore int,		--минимальный балл
	codeFIS text		--код выгрузки в ФИС
);
go

--Блок испытаний
create table TestBox (
	id int primary key, --код
	name text,			--наименование блока испытаний
	codeFIS text		--код выгрузки в ФИС
);
go

--Основание оценки
create table AssessmentBase (
	id int primary key, --код
	name text,			--наименование основания оценки
	codeFIS text		--код выгрузки в ФИС
);
go

--Абитуриент_Вступительные испытания
create table AbiturientEntranceTests (
	aid_abiturient int,					--код/номер личного дела абитуриента
	id_entranceTest int,				--код вступительного испытания
	testGroup text, 					--группа
	indexNumber int, 					--порядковый номер в группе
	id_testBox int,						--код блока испытаний
	testDate Date,						--дата испытания
	score int,							--балл
	id_assessmentBase int,				--код основания оценки
	examPassed int,						--отметка сдачи
	
	--Внешние ключи
	foreign key (aid_abiturient) references Abiturient(aid),
	foreign key (id_entranceTest) references EntranceTest(id),
	foreign key (id_testBox) references TestBox(id),
	foreign key (id_assessmentBase) references AssessmentBase(id)
);
go

--Тип паспорта
create table PassportType (
	id int primary key, --код
	name text,			--наименование паспорта
	codeFIS text		--код ФИС
);
go

--Абитуриент_паспорт
create table AbiturientPassport (
	series text,	--серия паспорта
	pasNumber text,	--номер паспорта
	givenBy text,	--кем выдан
	givenDate Date,	--дата выдачи

	--Внешние ключи
	foreign key (aid_abiturient) references Abiturient(aid),
	foreign key (id_passportType) references PassportType(id)	
);
go

--Регион
create table Region (
	id int primary key, --код
	name text, 			--название насленного пункта
	codeFIS text		--код ФИС
);
go

--Тип населенного пункта
create table LocalityType (
	id int primary key, --код
	name text, 			--название насленного пункта
	codeFIS text		--код ФИС	
);
go

--Абитуриент_адрес
create table AbiturientAddress (
	indexAddress text,	--почтовый индекс
	address text,		--адрес

	--Внешние ключи
	foreign key (aid_abiturient) references Abiturient(aid),
	foreign key (id_region) references Region(id),
	foreign key (id_localityType) references LocalityType(id)	
);
go

