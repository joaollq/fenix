UPDATE 
ACADEMIC_SERVICE_REQUEST, ACADEMIC_SERVICE_REQUEST_SITUATION 
SET 
ACADEMIC_SERVICE_REQUEST.REQUEST_DATE = ACADEMIC_SERVICE_REQUEST_SITUATION.SITUATION_DATE 
WHERE 
ACADEMIC_SERVICE_REQUEST.REQUEST_DATE = "2008-03-06 20:14:24" 
AND 
ACADEMIC_SERVICE_REQUEST.CREATION_DATE = "2008-03-06 13:50:06" 
AND 
ACADEMIC_SERVICE_REQUEST_SITUATION.KEY_ACADEMIC_SERVICE_REQUEST = ACADEMIC_SERVICE_REQUEST.ID_INTERNAL;
