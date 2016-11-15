--Удаляем существующую БД
drop database Ordinator;

--Создаем новую БД
create database Ordinator;
go

--указываем, что все дальнейшие запросы должны быть выполнены для БД ординатор
use Ordinator;
go

--Удаление существующих таблиц для универсальности скрипта
drop table /*if exists*/ AbiturientPassport;
drop table /*if exists*/ AbiturientAddress;
drop table /*if exists*/ AbiturientCompetitiveGroup;
drop table /*if exists*/ AbiturientEntranceTests;
drop table /*if exists*/ AbiturientIndividualAchievement;
drop table /*if exists*/ AbiturientPostgraduateEducation;
drop table /*if exists*/ AbiturientHigherEducation;
drop table /*if exists*/ Abiturient;
drop table /*if exists*/ Gender;
drop table /*if exists*/ Nationality;
drop table /*if exists*/ ReturnReasons;
drop table /*if exists*/ IndividualAchievement;
drop table /*if exists*/ EntranceTest;
drop table /*if exists*/ TestBox;
drop table /*if exists*/ AssessmentBase;
drop table /*if exists*/ PassportType;
drop table /*if exists*/ Region;
drop table /*if exists*/ LocalityType;
drop table /*if exists*/ AdmissionPlan;
drop table /*if exists*/ Course;
drop table /*if exists*/ Speciality;
drop table /*if exists*/ EducationForm;
drop table /*if exists*/ Chair;
drop table /*if exists*/ CompetitiveGroup;
drop table /*if exists*/ TargetOrganisation;
drop table /*if exists*/ EducationStandard;
drop table /*if exists*/ Users;

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
	email text,				--e-mail
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
	score int,							--фактический балл за индивидуальное достижение
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
	aid_abiturient int,	--код/номер личного дела абитуриента
	id_passportType int, --тип паспорта
	paspSeries text,		--серия паспорта
	paspNumber text,		--номер паспорта
	paspGivenBy text,		--кем выдан
	paspGivenDate Date,		--дата выдачи

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
	aid_abiturient int,		--код/номер личного дела абитуриента
	id_region int,			--код региона
	id_localityType int,	--тип населенного пункта
	indexAddress text,		--почтовый индекс
	factAddress text,		--адрес

	--Внешние ключи
	foreign key (aid_abiturient) references Abiturient(aid),
	foreign key (id_region) references Region(id),
	foreign key (id_localityType) references LocalityType(id)	
);
go

--Направление обучения
create table Course(
	id int primary key, --код
	name text,          --наименование
	codeFIS text		--код ФИС
);
go

--Специальность
create table Speciality(
	id int primary key, --код
	name text,          --наименование
	codeFIS text		--код ФИС
);
go

--Форма обучения
create table EducationForm (
	id int primary key, --код
	name text,          --наименование
	codeFIS text		--код ФИС
);
go

--Кафедра
create table Chair (
	id int primary key, --код
	name text,          --наименование
	codeFIS text		--код ФИС
);
go

--Конкурсная группа
create table CompetitiveGroup (
	id int primary key,	      --код
	name text,    			  --наименование конкурсной группы
	codeFIS text  			  --код ФИС
);
go

--Целевая организация
create table TargetOrganisation (
	id int primary key,	      --код
	name text,                --наименование целевой организации
	codeFIS text  			  --код ФИС
);
go

--Стандарт образования
create table EducationStandard (
	id int primary key,		--код
	name text,				--наименование стандарта образования
	codeFIS text			--код ФИС
);
go

--Абитуриент_конкурсные группы
create table AbiturientCompetitiveGroup (
	aid_abiturient int,                     --код/номер личного дела абитуриента
	course int,                             --направление
	speciality int,                         --специальность
	educationForm int,                      --форма обучения
	chair int,                              --кафедра
	competitiveGroup int,                   --конкурсная группа
	targetOrganisation int,                 --целевая организация
	educationStandard int,                  --стандарт образования
	competitiveBall int,                    --конкурсный балл
	availabilityIndividualAchievements int, --наличие индивидуальных достижений
	markEnrollment int,                     --отметка о зачислении
	
	--Внешние ключи
	foreign key (aid_abiturient) references Abiturient(aid),
	foreign key (course) references Course(id),
	foreign key (speciality) references Speciality(id),
	foreign key (educationForm) references EducationForm(id),
	foreign key (chair) references Chair(id),
	foreign key (competitiveGroup) references CompetitiveGroup(id),
	foreign key (targetOrganisation) references TargetOrganisation(id),
	foreign key (educationStandard) references EducationStandard(id),
);
go

--План приема
create table AdmissionPlan (
	specialtyCode int,		--код специальности
	educationForm int,		--форма обучения
	competitiveGroup int,	--конкурсная группа
	targetOrganisation int,	--целевая организация
	educationStandard int,	--стандарт образования
	placeCount int,		--количество мест
	
	--Внешние ключи
	foreign key (specialtyCode) references Speciality(id) ,
	foreign key (educationForm) references EducationForm(id),
	foreign key (competitiveGroup) references CompetitiveGroup(id),
	foreign key (targetOrganisation) references TargetOrganisation(id),
	foreign key (educationStandard) references EducationStandard(id)
);
go

--Пользователи
create table Users (
	userLogin text,			--логин
	userPassword text,		--пароль
	userSignature text,		--подпись (будет отражаться в ряде документов)
);
go