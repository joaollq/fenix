<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/taglib/jsf-fenix" prefix="fc"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/taglib/jsf-tiles" prefix="ft"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<ft:tilesView definition="pedagogicalCouncil.masterPage" attributeName="body-inline">
	<f:loadBundle basename="resources/ScientificCouncilResources" var="scouncilBundle"/>
	<f:loadBundle basename="resources/PedagogicalCouncilResources" var="pcouncilBundle"/>
	<f:loadBundle basename="resources/EnumerationResources" var="enumerationBundle"/>

		<h:outputText value="<em>#{pcouncilBundle['pedagogical.council']}</em>" escape="false"/>
		<h:outputText value="<h2>#{pcouncilBundle['competenceCoursesManagement']}</h2>" escape="false"/>

		<h:form>
	
		<h:messages infoClass="success0" errorClass="error0" layout="table" globalOnly="true"/>

		<h:panelGrid columns="2" style="infocell" columnClasses="infocell">
			<h:outputText value="#{scouncilBundle['department']}:" escape="false"/>
			<fc:selectOneMenu value="#{CompetenceCourseManagement.selectedDepartmentUnitID}" onchange="submit()">
				<f:selectItems value="#{CurricularCourseManagement.departmentUnits}"/>
			</fc:selectOneMenu>
			<h:outputText value="<input value='#{htmlAltBundle['submit.sumbit']}' id='javascriptButtonID' class='altJavaScriptSubmitButton' alt='#{htmlAltBundle['submit.sumbit']}' type='submit'/>" escape="false"/>
		</h:panelGrid>

<%--
		<h:outputText value="<p class='mtop2 mbottom2'><a href='#members' title='#{scouncilBundle['view.group.members.description']}'>#{scouncilBundle['view.group.members']}</a></p>" escape="false"/>
--%>

		<h:panelGroup rendered="#{!empty CompetenceCourseManagement.groupMembersLabels}">
			<h:outputText value="<br/><b id='members' class='highlight1'>#{scouncilBundle['groupMembers']}</b> #{scouncilBundle['label.group.members.explanation']}:<br/>" escape="false" />
			<h:outputText value="<ul>" escape="false"/>
			<fc:dataRepeater value="#{CompetenceCourseManagement.groupMembersLabels}" var="memberLabel">
				<h:outputText value="<li>#{memberLabel}</li>" escape="false"/>
			</fc:dataRepeater>
			<h:outputText value="</ul>" escape="false"/>
		</h:panelGroup>
		<h:panelGroup rendered="#{empty CompetenceCourseManagement.groupMembersLabels && !empty CompetenceCourseManagement.selectedDepartmentUnitID}">
			<h:outputText value="<br/><i>#{scouncilBundle['label.empty.group.members']}</i><br/>" escape="false" />
		</h:panelGroup>

		<h:panelGroup rendered="#{!empty CompetenceCourseManagement.scientificAreaUnits}">	
		<h:outputText value="<p class='mtop2 mbottom05'><b>Opções de listagem:</b></p>" escape="false"/>
		<h:outputText value="<ul>" escape="false"/>
		<h:panelGroup rendered="#{!empty CompetenceCourseManagement.departmentDraftCompetenceCourses}">
			<h:outputText value="<li>" escape="false"/>
				<h:outputLink rendered="#{!empty CompetenceCourseManagement.departmentDraftCompetenceCourses}" value="showAllCompetenceCourses.faces" target="_blank">
				<h:outputText value="#{scouncilBundle['showDraftCompetenceCourses']} (#{scouncilBundle['newPage']})" escape="false"/>
				<f:param name="competenceCoursesToList" value="DRAFT"/>
				<f:param name="selectedDepartmentUnitID" value="#{CompetenceCourseManagement.selectedDepartmentUnitID}"/>
			</h:outputLink>
			<h:outputText value="</li>" escape="false"/>
		</h:panelGroup>
		<h:panelGroup rendered="#{!empty CompetenceCourseManagement.departmentPublishedCompetenceCourses}">
			<h:outputText value="<li>" escape="false"/>
			<h:outputLink value="showAllCompetenceCourses.faces" target="_blank">
				<h:outputText value="#{scouncilBundle['showPublishedCompetenceCourses']} (#{scouncilBundle['newPage']})" escape="false"/>
				<f:param name="competenceCoursesToList" value="PUBLISHED"/>
				<f:param name="selectedDepartmentUnitID" value="#{CompetenceCourseManagement.selectedDepartmentUnitID}"/>
			</h:outputLink>
			<h:outputText value="</li>" escape="false"/>
		</h:panelGroup>
		<h:panelGroup rendered="#{!empty CompetenceCourseManagement.departmentApprovedCompetenceCourses}">
			<h:outputText value="<li>" escape="false"/>
			<h:outputLink value="showAllCompetenceCourses.faces" target="_blank">
				<h:outputText value="#{scouncilBundle['showApprovedCompetenceCourses']} (#{scouncilBundle['newPage']})" escape="false"/>
				<f:param name="competenceCoursesToList" value="APPROVED"/>
				<f:param name="selectedDepartmentUnitID" value="#{CompetenceCourseManagement.selectedDepartmentUnitID}"/>
			</h:outputLink>
			<h:outputText value="</li>" escape="false"/>
		</h:panelGroup>
		<h:outputText value="</ul>" escape="false"/>
		</h:panelGroup>		

		<h:dataTable value="#{CompetenceCourseManagement.scientificAreaUnits}" var="scientificAreaUnit"
				rendered="#{!empty CompetenceCourseManagement.scientificAreaUnits}">
			<h:column>
				<h:outputText value="<p class='mtop2 mbottom0'><strong>#{scientificAreaUnit.name}</strong></p>" escape="false"/>
				<h:panelGroup rendered="#{empty scientificAreaUnit.competenceCourseGroupUnits}">
					<h:outputText style="font-style:italic" value="#{scouncilBundle['noCompetenceCourseGroupUnits']}<br/>" escape="false"/>
				</h:panelGroup>
				
				<h:panelGroup rendered="#{!empty scientificAreaUnit.competenceCourseGroupUnits}">
					<h:outputText value="<ul class='list3'>" escape="false"/>
					<h:dataTable value="#{scientificAreaUnit.competenceCourseGroupUnits}" var="competenceCourseGroupUnit">
							<h:column>
								<h:outputText value="<li class='tree_label' style='background-position: 0em 0.5em;'>#{competenceCourseGroupUnit.name}" escape="false"/>
								<h:dataTable value="#{competenceCourseGroupUnit.competenceCourses}" var="competenceCourse"
										styleClass="showinfo1 smallmargin mtop05" style="width: 55em;" rowClasses="color2" columnClasses=",aright" rendered="#{!empty competenceCourseGroupUnit.competenceCourses}">
										
										<h:column>
											<h:outputText value="#{competenceCourse.name} "/>
											<h:outputText rendered="#{!empty competenceCourse.acronym}" value="(#{competenceCourse.acronym}) "/>
											<h:outputText value="<span style='color: #aaa;'>" escape="false"/>
											<h:outputText rendered="#{competenceCourse.curricularStage.name == 'DRAFT'}" value="<em style='color: #bb5;'>#{enumerationBundle[competenceCourse.curricularStage]}</em>" escape="false"/>
											<h:outputText rendered="#{competenceCourse.curricularStage.name == 'PUBLISHED'}" value="<em style='color: #569;'>#{enumerationBundle[competenceCourse.curricularStage]}</em>" escape="false"/>
											<h:outputText rendered="#{competenceCourse.curricularStage.name == 'APPROVED'}" value="<em style='color: #595;'>#{enumerationBundle[competenceCourse.curricularStage]}</em>" escape="false"/>
											<h:outputText value="</span>" escape="false"/>
										</h:column>
									
										<h:column>
											<h:outputLink value="showCompetenceCourse.faces">
												<h:outputText value="#{scouncilBundle['show']}"/>
												<f:param name="action" value="ccm"/>
												<f:param name="competenceCourseID" value="#{competenceCourse.externalId}"/>
												<f:param name="selectedDepartmentUnitID" value="#{CompetenceCourseManagement.selectedDepartmentUnitID}"/>
											</h:outputLink>
										</h:column>
										
								</h:dataTable>
								<h:outputText value="</li>" escape="false"/>
							</h:column>
					</h:dataTable>
					<h:outputText value="</ul>" escape="false"/>
				</h:panelGroup>
			</h:column>
		</h:dataTable>
		<h:panelGroup rendered="#{empty CompetenceCourseManagement.scientificAreaUnits && !empty CompetenceCourseManagement.selectedDepartmentUnitID}">
			<h:outputText  value="<i>#{scouncilBundle['noScientificAreaUnits']}<i><br/>" escape="false"/>
		</h:panelGroup>
		
	</h:form>
</ft:tilesView>