﻿use Ordinator;

go

insert into [Chair] values
(1,	'Кафедра1',	null),
(2,	'Кафедра2',	null),
(3,	'Кафедра3',	null);

go

insert into [CompetitiveGroup] values
(1,	'Конкурсная группа1',	null),
(2,	'Конкурсная группа2',	null);

go

insert into [Course] values
(1,	'Направление1',	null),
(2,	'Направление2',	null),
(3,	'Направление3',	null),
(4,	'Направление4',	null);

go

insert into [EducationForm] values
(1,	'очная',	null),
(2,	'заочная',	null),
(3,	'очно-заочная',	null);

go

insert into [EducationStandard] values
(1,	'ГОС',	null),
(2,	'ФГОС',	null);

go

insert into [EntranceTest] values
(1,	'Тест',	70,	null),
(2,	'Собеседование',	10,	null);

go

insert into [Gender] values
(1,	'Женский',	null),
(2,	'Мужской',	null);

go

insert into [IndividualAchievement] values
(1,	'Сочинение',	1,	null),
(2,	'Олимпиада',	2,	null);

go

insert into [LocalityType] values
(1,	'Город',	null),
(2,	'Поселок городского типа',	null),
(3,	'Село',	null);

go

insert into [Nationality] values
(1,	'РФ',	NULL),
(2,	'Украина',	NULL),
(3,	'Беларусь',	NULL),
(4,	'Казахстан',	NULL);

go

insert into [PassportType] values
(1,	'паспорт РФ', null),
(2,	'паспорт Украины',	null),
(3,	'паспорт Беларуси',	null);

go

insert into [Region] values
(1,	'Нижегородская область',	null),
(2,	'Московская область',	null),
(3,	'Ленинградская область',	null);

go

insert into [ReturnReasons] values
(1,	'Личное заявление абитуриента',	null),
(2,	'Неполное соответствие документов',	null);

go

insert into [Speciality] values
(1,	'Специальность1', null, null,	null),
(2,	'Специальность2', null, null,	null),
(3,	'Специальность3', null, null,	null);

go

insert into [TargetOrganisation] values
(1,	'Целевая организация1',	null),
(2,	'Целевая организация2',	null);

go

insert into [TestBox] values
(1,	'Биология',	null),
(2,	'Химия',	null);

go

insert into [Abiturient] values
(2,	'qwe',	'qwedg',	'qwe',	'2016-12-09',	'asd',	1,	1,	NULL,	NULL,	NULL,	'2009-12-12',	'2017-01-30',	1,	NULL,	NULL),
(3,	'qwea',	'qwed',	'qwe',	'2016-12-16','', 1,	1,	NULL,	NULL,	NULL,	'2009-12-12',	NULL,	NULL,	NULL,	NULL);

go

insert into [AbiturientPassport] values
(2,	1,	'1234',	'123456',	'qweqwesdf',	'2004-12-12'),
(3,	3,	'1234',	'123456',	'qweqwe',	'2004-12-12');

go

insert into [AbiturientHigherEducation] values
(2, '1234', '123456', 'HSE', 'Maths', 2014, 9.5);

go

insert into [AbiturientAddress] values
(2, 1, 1, '456654', 'gol');

go

insert into [AbiturientIndividualAchievement] values
(2, 1, 199, 'Document', '1234654', '654', 'HSE', '2016-12-12');

go

insert into [AssessmentBase] values
(1, 'Ведомость', null);

go

insert into [AdmissionPlan] values
(1, 1, 1, 1, 1, 1, 11),
(2, 2, 2, 2, 2, 2, 22);

go

insert into [Users] values
('admin1', 'admin1', null);

go

insert into [AbiturientEntranceTests] values
(2, 1, '1c1', 1, 1, '2016-12-12', 25, 1, 1),
(3, 1, '1c1', 1, 1, '2016-12-12', 45, 1, 1),
(2, 2, '1c1', 1, 1, '2016-12-08', 30, 1, 1);

go

insert into [AbiturientCompetitiveGroup] values
(2, 1, 1, 1, 1, 1, 1, 1, 50,NULL,NULL,NULL);

go

use Aspirant;
go

insert into [Chair] values
(1,	'Кафедра1',	null),
(2,	'Кафедра2',	null),
(3,	'Кафедра3',	null);

go

insert into [CompetitiveGroup] values
(1,	'Конкурсная группа1',	null),
(2,	'Конкурсная группа2',	null);

go

insert into [Course] values
(1,	'Направление1',	null),
(2,	'Направление2',	null),
(3,	'Направление3',	null),
(4,	'Направление4',	null);

go

insert into [EducationForm] values
(1,	'очная',	null),
(2,	'заочная',	null),
(3,	'очно-заочная',	null);

go

insert into [EducationStandard] values
(1,	'ГОС',	null),
(2,	'ФГОС',	null);

go

insert into [EntranceTest] values
(1,	'Тест',	70,	null),
(2,	'Собеседование',	10,	null);

go

insert into [Gender] values
(1,	'Женский',	null),
(2,	'Мужской',	null);

go

insert into [IndividualAchievement] values
(1,	'Сочинение',	1,	null),
(2,	'Олимпиада',	2,	null);

go

insert into [LocalityType] values
(1,	'Город',	null),
(2,	'Поселок городского типа',	null),
(3,	'Село',	null);

go

insert into [Nationality] values
(1,	'РФ',	NULL),
(2,	'Украина',	NULL),
(3,	'Беларусь',	NULL),
(4,	'Казахстан',	NULL);

go

insert into [PassportType] values
(1,	'паспорт РФ', null),
(2,	'паспорт Украины',	null),
(3,	'паспорт Беларуси',	null);

go

insert into [Region] values
(1,	'Нижегородская область',	null),
(2,	'Московская область',	null),
(3,	'Ленинградская область',	null);

go

insert into [ReturnReasons] values
(1,	'Личное заявление абитуриента',	null),
(2,	'Неполное соответствие документов',	null);

go

insert into [Speciality] values
(1,	'Специальность1', null, null,	null),
(2,	'Специальность2', null, null,	null),
(3,	'Специальность3', null, null,	null);

go

insert into [TargetOrganisation] values
(1,	'Целевая организация1',	null),
(2,	'Целевая организация2',	null);

go

insert into [TestBox] values
(1,	'Биология',	null),
(2,	'Химия',	null);

go

insert into [Abiturient] values
(2,	'qwe',	'qwedg',	'qwe',	'2016-12-09',	'asd',	1,	1,	NULL,	NULL,	NULL,	'2009-12-12',	'2017-01-30',	1,	NULL,	NULL),
(3,	'qwea',	'qwed',	'qwe',	'2016-12-16','', 1,	1,	NULL,	NULL,	NULL,	'2009-12-12',	NULL,	NULL,	NULL,	NULL);

go

insert into [AbiturientPassport] values
(2,	1,	'1234',	'123456',	'qweqwesdf',	'2004-12-12'),
(3,	3,	'1234',	'123456',	'qweqwe',	'2004-12-12');

go

insert into [AbiturientHigherEducation] values
(2, '1234', '123456', 'HSE', 'Maths', 2014, 9.5);

go

insert into [AbiturientAddress] values
(2, 1, 1, '456654', 'gol');

go

insert into [AbiturientIndividualAchievement] values
(2, 1, 199, 'Document', '1234654', '654', 'HSE', '2016-12-12');

go

insert into [AssessmentBase] values
(1, 'Ведомость', null);

go

insert into [AdmissionPlan] values
(1, 1, 1, 1, 1, 1, 11),
(2, 2, 2, 2, 2, 2, 22);

go

insert into [Users] values
('admin1', 'admin1', null);

go

insert into [AbiturientEntranceTests] values
(2, 1, '1c1', 1, 1, '2016-12-12', 25, 1, 1),
(3, 1, '1c1', 1, 1, '2016-12-12', 45, 1, 1),
(2, 2, '1c1', 1, 1, '2016-12-08', 30, 1, 1);

go

insert into [AbiturientCompetitiveGroup] values
(2, 1, 1, 1, 1, 1, 1, 1, 50,NULL,NULL,NULL);

go
