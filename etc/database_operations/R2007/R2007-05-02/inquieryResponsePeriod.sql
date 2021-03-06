create table INQUIRY_RESPONSE_PERIOD (
    ID_INTERNAL int(11) not null auto_increment,
    KEY_ROOT_DOMAIN_OBJECT int(11) not null default 1,
    KEY_EXECUTION_PERIOD int(11) not null,
    BEGIN timestamp not null default '0000-00-00 00:00:00',
    END timestamp not null default '0000-00-00 00:00:00',
    INTRODUCTION longtext default '',
    primary key (ID_INTERNAL),
    index (KEY_ROOT_DOMAIN_OBJECT),
    index (KEY_EXECUTION_PERIOD)
) type=InnoDB;

alter table EXECUTION_PERIOD add column KEY_INQUIRY_RESPONSE_PERIOD int(11) not null;
alter table EXECUTION_PERIOD add index (KEY_INQUIRY_RESPONSE_PERIOD);

insert into INQUIRY_RESPONSE_PERIOD (ID_INTERNAL, KEY_EXECUTION_PERIOD, BEGIN, END)
    select ID_INTERNAL, ID_INTERNAL, INQUIRY_RESPONSE_BEGIN_DATE_TIME, INQUIRY_RESPONSE_END_DATE_TIME
    from EXECUTION_PERIOD
    where INQUIRY_RESPONSE_BEGIN_DATE_TIME is not null and INQUIRY_RESPONSE_BEGIN_DATE_TIME != '0000-00-00 00:00:00';

update EXECUTION_PERIOD, INQUIRY_RESPONSE_PERIOD
set EXECUTION_PERIOD.KEY_INQUIRY_RESPONSE_PERIOD = INQUIRY_RESPONSE_PERIOD.ID_INTERNAL
where INQUIRY_RESPONSE_PERIOD.KEY_EXECUTION_PERIOD = EXECUTION_PERIOD.ID_INTERNAL;

alter table EXECUTION_PERIOD drop column INQUIRY_RESPONSE_BEGIN_DATE_TIME;
alter table EXECUTION_PERIOD drop column INQUIRY_RESPONSE_END_DATE_TIME;