<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<em><bean:message key="label.candidacies" bundle="APPLICATION_RESOURCES"/></em>
<h2><bean:message key="title.erasmus.candidacy.create.registration" bundle="ACADEMIC_OFFICE_RESOURCES"/></h2>

<bean:define id="processId" name="process" property="externalId" />

<p><strong><bean:message key="message.erasmus.candidacy.registration.creation.for.candidate" bundle="ACADEMIC_OFFICE_RESOURCES" /></strong></p>

<%-- show person information --%>
<p class="mbottom05"><strong><bean:message key="label.candidacy.personalData" bundle="APPLICATION_RESOURCES" /></strong></p>
<fr:view name="process" property="personalDetails" schema="ErasmusCandidacyProcess.personalData">
	<fr:layout name="tabular">
		<fr:property name="classes" value="tstyle1 thlight thright mtop025"/>
        <fr:property name="columnClasses" value="width14em,,tdclear tderror1"/>
	</fr:layout>
</fr:view>

<html:link action="<%= "/caseHandlingMobilityIndividualApplicationProcess.do?method=executeCreateRegistration&processId=" + processId %>">
	<bean:message key="label.create" bundle="APPLICATION_RESOURCES" />
</html:link>
