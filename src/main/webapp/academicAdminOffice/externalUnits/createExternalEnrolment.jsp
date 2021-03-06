<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>

<html:xhtml/>

<br />
<h2><bean:message key="label.externalUnits.createExternalEnrolment" bundle="ACADEMIC_OFFICE_RESOURCES"/></h2>

<bean:define id="externalCurricularCourseId">&oid=<bean:write name="externalEnrolmentBean" property="externalCurricularCourse.externalId" /></bean:define>

<html:messages property="error" message="true" id="errMsg" bundle="ACADEMIC_OFFICE_RESOURCES">
	<p>
		<span class="error0"><!-- Error messages go here --><bean:write name="errMsg" /></span>
	</p>
</html:messages>

<fr:edit id="createExternalEnrolmentBean" 
		 name="externalEnrolmentBean"
		 schema="CreateExternalEnrolmentBean.edit"
		 action="/externalUnits.do?method=createExternalEnrolment">
		 
	<fr:layout name="tabular-editable">
		<fr:property name="classes" value="tstyle5 thlight thright"/>
		<fr:property name="columnClasses" value=",,tdclear tderror1"/>
	</fr:layout>
	
	<fr:destination name="cancel" path="<%= "/externalUnits.do?method=viewExternalCurricularCourse" + externalCurricularCourseId %>" />
</fr:edit>
