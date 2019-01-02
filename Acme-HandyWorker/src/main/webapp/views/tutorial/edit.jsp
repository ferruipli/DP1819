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

<jstl:if test="${empty tutorial.sections}">	
	<strong> <spring:message code="tutorial.sections" />: </strong>
	<display:table name="tutorial.sections" id="sections">
	
	<spring:message code="tutorial.section.number" var="sectionNumber" />
	<display:column property="number" title="${sectionNumber}" sortable="true" />	
		
	<spring:message code="tutorial.section.title" var="sectionTitle" />
	<display:column property="title" title="${sectionTitle}" sortable="true" />
	
	<spring:message code="tutorial.section.text" var="sectionText" />
	<display:column property="text" title="${sectionText}"/>
	
	<spring:message code="tutorial.section.pictures" var="sectionPictures" />
	<display:column property="pictures" title="${sectionPictures}" />
	
	</display:table>
</jstl:if>

<jstl:if test="${tutorial.id != 0}">
	<a href="section/handyWorker/edit.do?tutorialId=${tutorial.id}">
			<spring:message	code="tutorial.section.add" />
	</a>
</jstl:if>
	
	<p />
	<input type="submit" name="save" value="<spring:message code="tutorial.save" />" />
	<jstl:if test="${tutorial.id != 0 && tutorial.handyWorker == owner}">
	<input type="submit" name="delete" value="<spring:message code="tutorial.delete" />"onclick="return confirm('<spring:message code="tutorial.confirm.delete" />')" />
	</jstl:if>
	<a href="tutorial/handyWorker/list.do">
					<spring:message	code="tutorial.return" />
	</a> 
	<br />

</form:form>