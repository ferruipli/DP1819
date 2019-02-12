<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="confirm.telephone" var="confirmTelephone"/>

<form:form action="endorserRecord/handyWorker/edit.do"	modelAttribute="endorserRecord" onsubmit="javascript: return checkTelephone('${confirmTelephone}');">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="fullName">
		<spring:message code="endorserRecord.fullName" />:
	</form:label>
	<form:input path="fullName" />
	<form:errors cssClass="error" path="fullName" />
	<br />

	<form:label path="email">
		<spring:message code="endorserRecord.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />

	<form:label path="phoneNumber">
		<spring:message code="endorserRecord.phoneNumber" />:
	</form:label>
	<form:input path="phoneNumber" placeholder="+34 (111) 654654654" />
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	
	<form:label path="linkedInProfile">
		<spring:message code="endorserRecord.linkedInProfile" />:
	</form:label>
	<form:input path="linkedInProfile" />
	<form:errors cssClass="error" path="linkedInProfile" />
	<br />

	<form:label path="comments">
		<spring:message code="endorserRecord.comments" />:
	</form:label>
	<form:textarea path="comments" />
	<form:errors cssClass="error" path="comments" />
	<br />

	<jstl:if test="${endorserRecord.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="endorserRecord.delete" />"
			onclick="return confirm('<spring:message code="endorserRecord.confirm.delete" />')" />&nbsp; 	
	</jstl:if>

	<input type="submit" name="save"
		value="<spring:message code="endorserRecord.save" />" />

	<input type="button" name="cancel"
		value="<spring:message code="endorserRecord.cancel"/>"
		onclick="javascript: relativeRedir('curriculum/display.do?handyWorkerId=${handyWorkerId}');" />
	<br />



</form:form>
