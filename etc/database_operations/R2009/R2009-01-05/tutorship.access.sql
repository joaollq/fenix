SET AUTOCOMMIT = 0;

START TRANSACTION;

INSERT INTO AVAILABILITY_POLICY (EXPRESSION,TARGET_GROUP,CONTENT_ID,KEY_CONTENT,KEY_ROOT_DOMAIN_OBJECT,OJB_CONCRETE_CLASS) VALUES ('role(PEDAGOGICAL_COUNCIL) || role(TUTORSHIP)','role(PEDAGOGICAL_COUNCIL) || role(TUTORSHIP)','333b44da-3a7e-49c7-8672-4a0c7c7eda12',1,1,'net.sourceforge.fenixedu.domain.functionalities.ExpressionGroupAvailability')  ;

UPDATE AVAILABILITY_POLICY SET EXPRESSION='role(PEDAGOGICAL_COUNCIL)',TARGET_GROUP='role(\'PEDAGOGICAL_COUNCIL\')',CONTENT_ID='fb0cae678481ddf69faddf180adcb12a',KEY_CONTENT=null,KEY_ROOT_DOMAIN_OBJECT=1,OJB_CONCRETE_CLASS='net.sourceforge.fenixedu.domain.functionalities.ExpressionGroupAvailability' WHERE CONTENT_ID = 'fb0cae678481ddf69faddf180adcb12a'  ;

UPDATE CONTENT SET ENABLED=null,MODIFICATION_DATE=null,MAXIMIZABLE=null,PREFIX=null,KEY_INITIAL_CONTENT=null,CONTENT_ID='fb0cae678481ddf69faddf180adcb12a',CREATION_DATE='2008-01-14 20:40:18',NAME='pt19:Conselho Pedagógico',TITLE=null,BODY=null,DESCRIPTION=null,NORMALIZED_NAME=null,KEY_PORTAL=null,KEY_AVAILABILITY_POLICY=2,KEY_ROOT_DOMAIN_OBJECT=1,KEY_CREATOR=null,OJB_CONCRETE_CLASS='net.sourceforge.fenixedu.domain.Section' WHERE CONTENT_ID = 'fb0cae678481ddf69faddf180adcb12a'  ;

CREATE TEMPORARY TABLE UUID_TEMP_TABLE(COUNTER integer, UUID varchar(255), FROM_UUID varchar(255));

INSERT INTO UUID_TEMP_TABLE(COUNTER,UUID,FROM_UUID) VALUES(1,'fb0cae678481ddf69faddf180adcb12a','333b44da-3a7e-49c7-8672-4a0c7c7eda12') ;
INSERT INTO UUID_TEMP_TABLE(COUNTER,UUID,FROM_UUID) VALUES(2,'333b44da-3a7e-49c7-8672-4a0c7c7eda12','fb0cae678481ddf69faddf180adcb12a') ;
ALTER TABLE UUID_TEMP_TABLE ADD INDEX (COUNTER), ADD INDEX (UUID), ADD INDEX (FROM_UUID); 


UPDATE NODE T, UUID_TEMP_TABLE UIT, CONTENT CT set T.KEY_PARENT=CT.ID_INTERNAL WHERE T.KEY_PARENT=UIT.COUNTER AND T.CONTENT_ID = UIT.FROM_UUID AND CT.CONTENT_ID=UIT.UUID;
UPDATE NODE T, UUID_TEMP_TABLE UIT, CONTENT CT set T.KEY_CHILD=CT.ID_INTERNAL WHERE T.KEY_CHILD=UIT.COUNTER AND T.CONTENT_ID = UIT.FROM_UUID AND CT.CONTENT_ID=UIT.UUID;
UPDATE CONTENT T, UUID_TEMP_TABLE UIT, CONTENT CT set T.KEY_FUNCTIONALITY=CT.ID_INTERNAL WHERE T.KEY_FUNCTIONALITY=UIT.COUNTER AND T.CONTENT_ID = UIT.FROM_UUID AND CT.CONTENT_ID=UIT.UUID;
UPDATE CONTENT T, UUID_TEMP_TABLE UIT, EXECUTION_PATH CT set T.KEY_EXECUTION_PATH_VALUE=CT.ID_INTERNAL WHERE T.KEY_EXECUTION_PATH_VALUE=UIT.COUNTER AND T.CONTENT_ID = UIT.FROM_UUID AND CT.CONTENT_ID=UIT.UUID;
UPDATE CONTENT T, UUID_TEMP_TABLE UIT, CONTENT CT set T.KEY_INITIAL_CONTENT=CT.ID_INTERNAL WHERE T.KEY_INITIAL_CONTENT=UIT.COUNTER AND T.CONTENT_ID = UIT.FROM_UUID AND CT.CONTENT_ID=UIT.UUID;
UPDATE CONTENT T, UUID_TEMP_TABLE UIT, CONTENT CT set T.KEY_PORTAL=CT.ID_INTERNAL WHERE T.KEY_PORTAL=UIT.COUNTER AND T.CONTENT_ID = UIT.FROM_UUID AND CT.CONTENT_ID=UIT.UUID;
UPDATE CONTENT T, UUID_TEMP_TABLE UIT, AVAILABILITY_POLICY CT set T.KEY_AVAILABILITY_POLICY=CT.ID_INTERNAL WHERE T.KEY_AVAILABILITY_POLICY=UIT.COUNTER AND T.CONTENT_ID = UIT.FROM_UUID AND CT.CONTENT_ID=UIT.UUID;
UPDATE AVAILABILITY_POLICY T, UUID_TEMP_TABLE UIT, CONTENT CT set T.KEY_CONTENT=CT.ID_INTERNAL WHERE T.KEY_CONTENT=UIT.COUNTER AND T.CONTENT_ID = UIT.FROM_UUID AND CT.CONTENT_ID=UIT.UUID;
UPDATE EXECUTION_PATH T, UUID_TEMP_TABLE UIT, CONTENT CT set T.KEY_FUNCTIONALITY=CT.ID_INTERNAL WHERE T.KEY_FUNCTIONALITY=UIT.COUNTER AND T.CONTENT_ID = UIT.FROM_UUID AND CT.CONTENT_ID=UIT.UUID;
DROP TABLE UUID_TEMP_TABLE;



COMMIT;
