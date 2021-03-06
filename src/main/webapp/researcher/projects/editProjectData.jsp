<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html:xhtml/>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<bean:define id="projectId" name="selectedProject" property="externalId" />
<em>Projectos</em> <!-- tobundle -->
<h2><bean:message bundle="RESEARCHER_RESOURCES" key="researcher.project.editProject.data.useCaseTitle"/></h2>

<p class="mtop2 mbottom05"><strong><bean:message bundle="RESEARCHER_RESOURCES" key="researcher.project.editProject.data"/></strong></p>

<fr:edit name="selectedProject" schema="project.edit-defaults" action="<%="/projects/viewProject.do?method=prepare&projectId="+projectId%>">
    <fr:layout name="tabular">
	    <fr:property name="classes" value="tstyle1 thlight thright throp mtop05"/>
    	<fr:property name="columnClasses" value=",,tdclear tderror1"/>
    </fr:layout>
    <fr:destination name="cancel" path="<%="/projects/viewProject.do?method=prepare&projectId="+projectId%>"/>
</fr:edit>
