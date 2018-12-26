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

<form:form action="message/administrator,customer,handyWorker,referee,sponsor/send.do" modelAttribute="messageToSend" >
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="sendMoment"/>
	<form:hidden path="sender"/>
	
	<form:label path="subject">
		<spring:message code="message.display.subject"/>
	</form:label>
	<form:input path="subject"/>
	<form:errors cssClass="error" path="subject"/>
	<br>
	
	<form:label path="body">
		<spring:message code="message.display.body"/>
	</form:label>
	<form:textarea path="body"/>
	<form:errors cssClass="error" path="body"/>
	<br>
	
	<form:label path="priority">
		<spring:message code="message.display.priority"/>
	</form:label>
	<form:select path="priority">
		<form:option label="-----" value="0"/>
		<form:option label="HIGH" value="HIGH"/>
		<form:option label="NEUTRAL" value="NEUTRAL"/>
		<form:option label="LOW" value="LOW"/>
	</form:select>
	<br>
	
	<jstl:choose>
		<jstl:when test="${not empty actors}">
			<label for="recipientsId">
				<spring:message code="message.display.recipients"/>
			</label>
			<select name="recipients" id="recipientsId" multiple>
				<jstl:forEach var="recipient" items="${actors}">
					<option value="${recipient.id}" label="${recipient.name} ${recipient.surname} - (${recipient.email})" />
				</jstl:forEach>
			</select>
			<br>
		</jstl:when>
		<jstl:otherwise>
			<form:hidden path="recipients"/>
		</jstl:otherwise>
	</jstl:choose>
		
	
	
	<form:label path="tags">
		<spring:message code="message.display.tags"/>
	</form:label>
	<form:textarea path="tags"/>
	<form:errors cssClass="error" path="tags"/>
	<br>
	
	
	<!-- Buttons -->
	
	<input type="submit" name="send" value="<spring:message code="message.button.send"/>" />
	<input type="button" name="cancel" value="<spring:message code="message.button.cancel"/>" 
			onclick="javascript: relativeRedir('box/administrator,customer,handyWorker,referee,sponsor/list.do')" />	

</form:form>