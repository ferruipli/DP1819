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
<form:form action="personalRecord/handyWorker/edit.do" modelAttribute="personalRecord" onsubmit="javascript: return checkTelephone('${confirmTelephone}');">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="fullName">
		<spring:message code="personalRecord.fullName" />:
	</form:label>
	<form:input path="fullName" />
	<form:errors cssClass="error" path="fullName" />
	<br />
	
	<form:label path="photoLink">
		<spring:message code="personalRecord.photoLink" />:
	</form:label>
	<form:input path="photoLink" />
	<form:errors cssClass="error" path="photoLink" />
	<br />
	
	<form:label path="email">
		<spring:message code="personalRecord.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	
	<form:label path="phoneNumber">
		<spring:message code="personalRecord.phoneNumber" />:
	</form:label>
	<form:input path="phoneNumber" placeholder="+34 (111) 654654654"/>
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	
	<form:label path="linkedInProfile">
		<spring:message code="personalRecord.linkedInProfile" />:
	</form:label>
	<form:input path="linkedInProfile" />
	<form:errors cssClass="error" path="linkedInProfile" />
	<br />

	<input type="submit" name="save" value="<spring:message code="personalRecord.save" />" />
	
	<jstl:if test="${existCurriculum}">
		<input type="button" name="cancel" value="<spring:message code="personalRecord.cancel"/>"onclick="javascript: relativeRedir('curriculum/display.do?handyWorkerId=${handyWorkerId}');"/>
	</jstl:if>
	
	<jstl:if test="${!existCurriculum}">
		<input type="button" name="cancel" value="<spring:message code="personalRecord.cancel"/>"onclick="javascript: relativeRedir('actor/administrator,customer,handyWorker,referee,sponsor/display.do');"/>
	</jstl:if>
	<br />
	


</form:form>
