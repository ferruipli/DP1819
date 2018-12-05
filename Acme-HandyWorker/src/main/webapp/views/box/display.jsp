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
	
	<spring:message code="message.sendMoment" var="sendMoment" />
	<spring:message code="message.date.format" var="dateFormat"/>
	<display:column property="sendMoment" title="${sendMoment}" sortable="true" format="${dateFormat}" />
	
	<spring:message code="message.subject" var="subject" />
	<display:column property="subject" title="${subject}" sortable="true"/>
	
	<spring:message code="message.priority" var="priority" />
	<display:column property="priority" title="${priority}" sortable="true"/>
	
	<spring:message code="message.sender" var="sender" />
	<display:column property="sender" title="${sender}" sortable="true"/>
	
	<spring:message code="message.tags" var="tags" />
	<display:column property="tags" title="${tags}" sortable="true"/>
	
</display:table>

	<a href="message/edit.do"><spring:message code="message.send"/></a>

</fieldset>

<input type="button" name="return" value="<spring:message code="box.return" />" 
				onclick="javascript: relativeRedir('box/list.do');" />