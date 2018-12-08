<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h2><spring:message code="box.name"/>:</h2><jstl:out value="${box.name}"/>
	<jstl:if test="${box.isSystemBox == false }">
		<a href="box/edit.do?boxId=${box.id }"><spring:message code="box.edit"/></a>
	</jstl:if>

<fieldset>
	<legend><spring:message code="box.messages"/></legend>
<display:table name="messages" id="row" requestURI="box/display.do?boxId=${box.id}" pagesize="5" class="displaytag">
	<display:column>
		<a href="message/display.do?messageId=${rowMessage.id}&folderId=${folder.id}"><spring:message code="folder.table.display" /></a>
	</display:column>
	
	<spring:message code="message.date.format" var="dateFormat"/>
	<display:column property="sendMoment" titleKey="message.sendMoment" format="${dateFormat}"/>
	
	<display:column property="subject" titleKey="message.subject"/>
	
	<display:column property="priority" titleKey="message.priority"/>
	
	<display:column property="sender" titleKey="message.sender"/>
	
	<display:column property="tags" titleKey="message.tags"/>
	
</display:table>

	<a href="message/edit.do"><spring:message code="message.send"/></a>

</fieldset>

<input type="button" name="return" value="<spring:message code="box.return" />" 
				onclick="javascript: relativeRedir('box/list.do');" />