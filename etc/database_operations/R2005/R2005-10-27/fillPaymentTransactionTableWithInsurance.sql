select concat('insert into PAYMENT_TRANSACTION (ID_INTERNAL, ACK_OPT_LOCK, VALUE, TRANSACTION_DATE, REMARKS, PAYMENT_TYPE, TRANSACTION_TYPE, WAS_INTERNAL_BALANCE, KEY_RESPONSIBLE_PERSON, KEY_GUIDE_ENTRY, KEY_PERSON_ACCOUNT, KEY_EXECUTION_YEAR, KEY_STUDENT, CLASS_NAME) values (', ID_INTERNAL,',', ACK_OPT_LOCK,',', VALUE,',''', TRANSACTION_DATE,''',', if(REMARKS is null, "null" ,concat('"',REMARKS,'"')),',''', PAYMENT_TYPE,''',''', TRANSACTION_TYPE,''',', WAS_INTERNAL_BALANCE,',', KEY_RESPONSIBLE_PERSON,',', if(KEY_GUIDE_ENTRY is null, "null" ,KEY_GUIDE_ENTRY),',', KEY_PERSON_ACCOUNT,',', KEY_EXECUTION_YEAR,',', KEY_STUDENT,', ''net.sourceforge.fenixedu.domain.transactions.InsuranceTransaction'');') as '' from INSURANCE_TRANSACTION;