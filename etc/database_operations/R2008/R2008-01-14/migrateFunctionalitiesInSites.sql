-- Migration of homepage functionalities

-- Prémios 9e0b010c-48bc-480f-9530-c7b83a4c37e0
select @Functionality:=C.ID_INTERNAL from CONTENT C WHERE CONTENT_ID='9e0b010c-48bc-480f-9530-c7b83a4c37e0';

CREATE TEMPORARY TABLE ADD_FUNCTIONALITY(PARENT INTEGER, CHILD INTEGER);

ALTER TABLE ADD_FUNCTIONALITY ADD INDEX(PARENT), ADD INDEX(CHILD);

insert into ADD_FUNCTIONALITY(PARENT,CHILD) select C.ID_INTERNAL, @Functionality FROM CONTENT C, SITE S WHERE C.OJB_CONCRETE_CLASS LIKE 'net.sourceforge.fenixedu.domain.homepage.Homepage' AND S.ID_INTERNAL=C.OLD_ID_INTERNAL AND S.SHOW_PRIZES=1;

update NODE SET NODE_ORDER = NODE_ORDER+1 WHERE KEY_PARENT IN (SELECT PARENT FROM ADD_FUNCTIONALITY);

insert into NODE(KEY_PARENT, KEY_CHILD, NODE_ORDER, VISIBLE,ASCENDING, OJB_CONCRETE_CLASS) select PARENT AS KEY_PARENT, CHILD AS KEY_CHILD, '0', '1','1','net.sourceforge.fenixedu.domain.contents.ExplicitOrderNode' FROM ADD_FUNCTIONALITY;

drop TEMPORARY TABLE ADD_FUNCTIONALITY;

-- Actividades Cientificas 405b9767-a4d7-4bd7-93ec-00fe1e21d941
select @Functionality:=C.ID_INTERNAL from CONTENT C WHERE CONTENT_ID='405b9767-a4d7-4bd7-93ec-00fe1e21d941';

CREATE TEMPORARY TABLE ADD_FUNCTIONALITY(PARENT INTEGER, CHILD INTEGER);

ALTER TABLE ADD_FUNCTIONALITY ADD INDEX(PARENT), ADD INDEX(CHILD);

insert into ADD_FUNCTIONALITY(PARENT,CHILD) select C.ID_INTERNAL, @Functionality FROM CONTENT C, SITE S WHERE C.OJB_CONCRETE_CLASS LIKE 'net.sourceforge.fenixedu.domain.homepage.Homepage' AND S.ID_INTERNAL=C.OLD_ID_INTERNAL AND S.SHOW_PARTICIPATIONS=1;

update NODE SET NODE_ORDER = NODE_ORDER+1 WHERE KEY_PARENT IN (SELECT PARENT FROM ADD_FUNCTIONALITY);

insert into NODE(KEY_PARENT, KEY_CHILD, NODE_ORDER, VISIBLE,ASCENDING, OJB_CONCRETE_CLASS) select PARENT AS KEY_PARENT, CHILD AS KEY_CHILD, '0', '1','1','net.sourceforge.fenixedu.domain.contents.ExplicitOrderNode' FROM ADD_FUNCTIONALITY;

drop TEMPORARY TABLE ADD_FUNCTIONALITY;

-- Patentes 9d51e9a0-801f-48f1-b0f0-09a44c423010
select @Functionality:=C.ID_INTERNAL from CONTENT C WHERE CONTENT_ID='9d51e9a0-801f-48f1-b0f0-09a44c423010';

CREATE TEMPORARY TABLE ADD_FUNCTIONALITY(PARENT INTEGER, CHILD INTEGER);

ALTER TABLE ADD_FUNCTIONALITY ADD INDEX(PARENT), ADD INDEX(CHILD);

insert into ADD_FUNCTIONALITY(PARENT,CHILD) select C.ID_INTERNAL, @Functionality FROM CONTENT C, SITE S WHERE C.OJB_CONCRETE_CLASS LIKE 'net.sourceforge.fenixedu.domain.homepage.Homepage' AND S.ID_INTERNAL=C.OLD_ID_INTERNAL AND S.SHOW_PATENTS=1;

update NODE SET NODE_ORDER = NODE_ORDER+1 WHERE KEY_PARENT IN (SELECT PARENT FROM ADD_FUNCTIONALITY);

insert into NODE(KEY_PARENT, KEY_CHILD, NODE_ORDER, VISIBLE,ASCENDING, OJB_CONCRETE_CLASS) select PARENT AS KEY_PARENT, CHILD AS KEY_CHILD, '0', '1','1','net.sourceforge.fenixedu.domain.contents.ExplicitOrderNode' FROM ADD_FUNCTIONALITY;

drop TEMPORARY TABLE ADD_FUNCTIONALITY;

-- Publications (31fd792e-73c4-4a78-88ee-36d73347caf4)

select @Functionality:=C.ID_INTERNAL from CONTENT C WHERE CONTENT_ID='31fd792e-73c4-4a78-88ee-36d73347caf4';

CREATE TEMPORARY TABLE ADD_FUNCTIONALITY(PARENT INTEGER, CHILD INTEGER);

ALTER TABLE ADD_FUNCTIONALITY ADD INDEX(PARENT), ADD INDEX(CHILD);

insert into ADD_FUNCTIONALITY(PARENT,CHILD) select C.ID_INTERNAL, @Functionality FROM CONTENT C, SITE S WHERE C.OJB_CONCRETE_CLASS LIKE 'net.sourceforge.fenixedu.domain.homepage.Homepage' AND S.ID_INTERNAL=C.OLD_ID_INTERNAL AND S.SHOW_PUBLICATIONS=1;

update NODE SET NODE_ORDER = NODE_ORDER+1 WHERE KEY_PARENT IN (SELECT PARENT FROM ADD_FUNCTIONALITY);

insert into NODE(KEY_PARENT, KEY_CHILD, NODE_ORDER, VISIBLE,ASCENDING, OJB_CONCRETE_CLASS) select PARENT AS KEY_PARENT, CHILD AS KEY_CHILD, '0', '1','1','net.sourceforge.fenixedu.domain.contents.ExplicitOrderNode' FROM ADD_FUNCTIONALITY;

drop TEMPORARY TABLE ADD_FUNCTIONALITY;


-- Interesses Cientificos 129fcf2c-4edb-4273-a58e-6d504ea3964c
select @Functionality:=C.ID_INTERNAL from CONTENT C WHERE CONTENT_ID='129fcf2c-4edb-4273-a58e-6d504ea3964c';

CREATE TEMPORARY TABLE ADD_FUNCTIONALITY(PARENT INTEGER, CHILD INTEGER);

ALTER TABLE ADD_FUNCTIONALITY ADD INDEX(PARENT), ADD INDEX(CHILD);

insert into ADD_FUNCTIONALITY(PARENT,CHILD) select C.ID_INTERNAL, @Functionality FROM CONTENT C, SITE S WHERE C.OJB_CONCRETE_CLASS LIKE 'net.sourceforge.fenixedu.domain.homepage.Homepage' AND S.ID_INTERNAL=C.OLD_ID_INTERNAL AND S.SHOW_INTERESTS=1;

update NODE SET NODE_ORDER = NODE_ORDER+1 WHERE KEY_PARENT IN (SELECT PARENT FROM ADD_FUNCTIONALITY);

insert into NODE(KEY_PARENT, KEY_CHILD, NODE_ORDER, VISIBLE,ASCENDING, OJB_CONCRETE_CLASS) select PARENT AS KEY_PARENT, CHILD AS KEY_CHILD, '0', '1','1','net.sourceforge.fenixedu.domain.contents.ExplicitOrderNode' FROM ADD_FUNCTIONALITY;

drop TEMPORARY TABLE ADD_FUNCTIONALITY;
