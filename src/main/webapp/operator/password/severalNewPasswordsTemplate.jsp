<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:html xhtml="true">
<head>
	<title>Nova Password</title>
</head>

<body>
    
    <logic:iterate id="person" name="persons">
    <table width="100%" height="100%" border="0">
    <tr height="30"><td>
     <table width="100%" border="0" valign="top">
      <tr> 
        <td height="100" colspan="2">
          <table border="0" width="100%" height="100" align="center" cellpadding="0" cellspacing="0">
            <tr> 
              <td width="50" height="100">
               <img src="<%= request.getContextPath() %>/masterDegreeAdministrativeOffice/guide/images/istlogo.gif" alt="<bean:message key="istlogo" bundle="IMAGE_RESOURCES" />" width="50" height="104" border="0"/> 
              </td>
              <td>
                &nbsp;
              </td>
              <td>
                <table border="0" width="100%" height="100%">
                  <tr valign="top" align="left"> 
                    <td>&nbsp;<b><bean:message key="institution.nameUpperCase" bundle="GLOBAL_RESOURCES"/></b><br/>
                      &nbsp;<b>Centro de Informática do <%=net.sourceforge.fenixedu.domain.organizationalStructure.Unit.getInstitutionName()%></b><br/>
                      <hr size="1">
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
      </tr>
	</table>

	</td>
	</tr>
    <tr valign="top"><td>

	<table width="100%" border="0">
	 <tr>
	 <td>
      <table width="100%" border="0">
          <tr>
            <td width="40%"><strong>Processo de:</strong></td>
            <td width="60%">&nbsp;</td>
          </tr>
          <tr>
            <td> <bean:message key="label.candidate.username"/> </td>
            <td> <bean:write name="person" property="username"/> </td>
          </tr>
          <tr>
            <td> <bean:message key="label.masterDegree.administrativeOffice.requesterName"/> </td>
            <td> <bean:write name="infoPerson" property="name"/> </td>
          </tr>
          <tr>
            <td> <bean:message key="label.candidate.identificationDocumentNumber"/> </td>
            <td> <bean:write name="person" property="documentIdNumber"/> </td>
          </tr>
          <tr>
            <td> <bean:message key="label.candidate.identificationDocumentType"/> </td>
            <td> 
            	<bean:define id="idType" name="person" property="idDocumentType"/>
            	<bean:message key='<%=idType.toString()%>'/> 
            </td>
          </tr>
          
	      <tr>
	        <td> <bean:message key="label.candidate.password"/> </td>
	        <td> <font face="Verdana"><bean:write name="person" property="password"/> </font></td>
	      </tr>
          
          <tr>
            <td> <bean:message key="label.candidate.accessAddress"/> </td>
            <td> <%= net.sourceforge.fenixedu._development.PropertiesManager.getProperty("login.page") %> </td>
          </tr>

          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          



	  </table>

	 </td>
	 </tr>
	</table>
	</td>
	</tr>




	<tr height="30">
	<td>

    <table width="100%" border="0">
     <tr>	 
	 <td>
	 	<table align="center" width="100%" valign="bottom">
	      <tr>
          <td colspan="2" valign="bottom" >
            <div align="center">
              <font size="2"> Documento processado por computador.</font> 
            </div>
            <hr size="1" color="#000000" width="100%">
            <div align="center">
              <font size="2"> Av. Rovisco Pais, 1 1049-001 Lisboa Codex Telefone: 218417336 Fax: 218419531 Contribuinte Nº: 501507930</font>
            </div>
          </td>
          </tr>
        </table>
     </td>	 
	 </tr>
	</table>

    </td>
    </tr>
    </table>
    </logic:iterate>
    </body>
</html:html>
