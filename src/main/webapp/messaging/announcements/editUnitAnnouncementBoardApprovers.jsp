<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/taglib/enum" prefix="e"%>

<html:xhtml/>
<em><bean:message key="label.communicationPortal.header" bundle="MESSAGING_RESOURCES"/></em>
<h2><bean:message key="label.manageChannel" bundle="MESSAGING_RESOURCES"/></h2>


<jsp:include flush="true" page="/messaging/context.jsp"/>

<h3 class="mbottom0 fwnormal">Unidade: <span class="emphasis1"><bean:write name="unit" property="name"/></span></h3>

<logic:present name="announcementBoard">
<bean:define id="contextPrefix" name="contextPrefix" />
<bean:define id="extraParameters" name="extraParameters" />
<bean:define id="announcementBoardId" name="announcementBoard" property="externalId"/>

<html:form action="/announcements/manageUnitAnnouncementBoard" >
		<html:hidden bundle="HTMLALT_RESOURCES" altKey="hidden.method" property="method" value="editAnnouncementBoardApprovers"/>
		<html:hidden bundle="HTMLALT_RESOURCES" altKey="hidden.keyUnit" property="keyUnit"/>
		<html:hidden bundle="HTMLALT_RESOURCES" altKey="hidden.announcementBoardId" property="announcementBoardId" value="<%=request.getParameter("announcementBoardId")%>"/>		
		<html:hidden bundle="HTMLALT_RESOURCES" altKey="hidden.returnAction" property="returnAction"/>
		<html:hidden bundle="HTMLALT_RESOURCES" altKey="hidden.returnMethod" property="returnMethod"/>
		<html:hidden bundle="HTMLALT_RESOURCES" altKey="hidden.tabularVersion" property="tabularVersion"/>
		
		<br/>
		<p>
			<fr:edit id="approvers" name="approvers" schema="messaging.announcementBoards.view.approvers">
				<fr:layout name="tabular-editable">
					<fr:property name="classes" value="tstyle2 tdcenter mtop05"/>
				</fr:layout>
			</fr:edit>
		</p>
		<br/>
		<html:submit>
			<bean:message key="messaging.submit.button" bundle="MESSAGING_RESOURCES"/>
		</html:submit>
</html:form>


</logic:present>
