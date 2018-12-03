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


<p>
	<strong> <spring:message code="tutorial.title" />: </strong>
	<jstl:out value="${tutorial.title}" />
</p>

<p>
	<strong> <spring:message code="tutorial.moment" />: </strong>
	<jstl:out value="${tutorial.moment}" />
</p>

<p>
	<strong> <spring:message code="tutorial.summary" />: </strong>
	<jstl:out value="${tutorial.summary}" />
</p>

<strong> <spring:message code="tutorial.sections" />: </strong>
		

	<display:table name="tutorial.sections" id="sections">
	
	<spring:message code="tutorial.section.number" var="sectionNumber" />
	<display:column property="number" title="${sectionNumber}" sortable="true" />	
		
	<spring:message code="tutorial.section.title" var="sectionTitle" />
	<display:column property="title" title="${sectionTitle}" sortable="true" />
	
	<spring:message code="tutorial.section.text" var="sectionText" />
	<display:column property="sectionText" title="${sectionText}"/>
	
	<spring:message code="tutorial.section.pictures" var="sectionPictures" />
	<display:column property="pictures" title="${sectionPictures}" />
	
	</display:table>
	
	
	<strong> <spring:message code="tutorial.sponsorships" />: </strong>
		

	<display:table name="tutorial.sponsorships" id="sponsorships">
	
	<spring:message code="tutorial.sponsorships.banner" var="sponsorshipBanner" />
	<display:column property="banner" title="${banner}" sortable="true" />	
		
	<spring:message code="tutorial.sponsorships.targetPage" var="sponsorshipTargetPage" />
	<display:column property="targetPage" title="${targetPage}" sortable="true" />
	
	</display:table>
	   
<input type="button" 
	   name="return"
	   value="<spring:message code="tutorial.return" />"
	   onclick="javascript: relativeRedir('tutorial/list.do');" />	

	