<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="tutorial/handyWorker/edit.do" modelAttribute="tutorial">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="sponsorShips" />
	<form:hidden path="handyWorker" />
	<form:hidden path="sections" />	
	
	<form:label path="title">
		<spring:message code="tutorial.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	
	<form:label path="summary">
		<spring:message code="tutorial.summary" />:
	</form:label>
	<form:input path="summary"/>
	<form:errors cssClass="error" path="summary" />
	<br />

	<form:label path="pictures">
		<spring:message code="tutorial.pictures" />:
	</form:label>
	<form:textarea path="pictures" />
	<form:errors cssClass="error" path="pictures" />
	<br />

	<jstl:if test="${not empty tutorial.sections}">	
		<p> <spring:message code="tutorial.sections" />: </p>
		<display:table name="tutorial.sections" id="sections">
			<display:column property="number" titleKey="tutorial.section.number" sortable="true" />	
			<display:column property="title" titleKey="tutorial.section.title" sortable="true" />
			<display:column property="text" titleKey="tutorial.section.text" />
		</display:table>
	</jstl:if>
	
	<jstl:if test="${tutorial.id != 0}">
		<a href="section/handyWorker/edit.do?tutorialId=${tutorial.id}">
			<spring:message	code="tutorial.section.add" />
		</a>
		<br />
	</jstl:if>
	
	<input type="submit" name="save" value="<spring:message code="tutorial.save" />" />
	<jstl:if test="${tutorial.id != 0 && tutorial.handyWorker == owner}">
		<input type="submit" name="delete" value="<spring:message code="tutorial.delete" />" onclick="return confirm('<spring:message code="tutorial.confirm.delete" />')" />
	</jstl:if>
	<input type="button" name="cancel"	value="<spring:message code="tutorial.cancel"/>" onclick="javascript: relativeRedir('tutorial/handyWorker/list.do');" />
	<br />
</form:form>