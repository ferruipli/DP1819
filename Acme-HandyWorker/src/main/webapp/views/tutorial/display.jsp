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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<p>
	<strong> <spring:message code="tutorial.title" />: </strong>
	<jstl:out value="${tutorial.title}" />
</p>

<p>
	<strong> <spring:message code="tutorial.summary" />: </strong>
	<jstl:out value="${tutorial.summary}" />
</p>

<p>
	<strong> <spring:message code="tutorial.moment" />: </strong>
	<spring:message code="tutorial.formatMoment1" var="formatMoment"/>
	<fmt:formatDate value="${tutorial.moment}" pattern="${formatMoment}"/>
</p>
<p>
<strong> <spring:message code="tutorial.handyWorker" />: </strong>
	<a href = "handyWorker/display.do?handyorkerId=${tutorial.handyWorker.id}"><jstl:out value="${tutorial.handyWorker.name}" /></a>
</p> 	
	
<p>
<strong> <spring:message code="tutorial.pictures" />: </strong>
	<a href = "${tutorial.pictures}"><jstl:out value="${tutorial.pictures}"></jstl:out></a>
</p> 
<strong> <spring:message code="tutorial.sections" />: </strong>
		

	<display:table name="tutorial.sections" id="sections">
	
	<spring:message code="tutorial.section.number" var="sectionNumber" />
	<display:column property="number" title="${sectionNumber}" sortable="true" />	
		
	<spring:message code="tutorial.section.title" var="sectionTitle" />
	<display:column property="title" title="${sectionTitle}" sortable="true" />
	
	<spring:message code="tutorial.section.pictures" var="sectionPictures" />
	<display:column property="pictures" title="${sectionPictures}" />
	
	<spring:message code="tutorial.section.text" var="sectionText" />
	<display:column property="text" title="${sectionText}"/>

	</display:table>  
	
	
<%-- 	<security:authorize access="hasRole('HANDYWORKER', 'SPONSOR')">
 --%>	<strong> <spring:message code="tutorial.sponsorships" />: </strong>
		

	<display:table name="tutorial.sponsorShips" id="sponsorShips">
	
	<spring:message code="tutorial.sponsorShips.banner" var="sponsorShipBanner" />
	<display:column property="banner" title="${sponsorShipBanner}"  />	
		
	<spring:message code="tutorial.sponsorShips.targetPage" var="sponsorShipTargetPage" />
	<display:column property="targetPage" title="${sponsorShipTargetPage}"/>
	
	</display:table>
	
<%-- 	</security:authorize> --%>

<security:authorize access="hasRole('HANDYWORKER')">   
<input type="button" 
	   name="return"
	   value="<spring:message code="tutorial.return" />"
	   onclick="javascript: relativeRedir('tutorial/handyWorker/list.do');" />	
</security:authorize>

<security:authorize access= "isAnonymous()">   
<input type="button" 
	   name="return"
	   value="<spring:message code="tutorial.return" />"
	   onclick="javascript: relativeRedir('tutorial/list.do');" />	
</security:authorize>

	