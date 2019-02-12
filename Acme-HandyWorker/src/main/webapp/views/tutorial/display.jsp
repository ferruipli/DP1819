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
	<a href = "handyWorker/display.do?actorId=${tutorial.handyWorker.id}">
		<jstl:out value="${tutorial.handyWorker.fullname}" />
	</a>
</p> 	
	
<p>
	<strong> <spring:message code="tutorial.pictures" />: </strong>
	<img src="${tutorial.pictures}" alt="Images" width="300" height="400" />
</p>

 
<jstl:if test="${not empty tutorial.sections}">
	<strong> <spring:message code="tutorial.sections" />: </strong>	
	<display:table name="tutorial.sections" id="sections">
	<display:column>	
			<a href="section/display.do?sectionId=${sections.id}">
				<spring:message	code="tutorial.display" />
			</a>
		</display:column>
		<display:column property="number" titleKey="tutorial.section.number" sortable="true" />	
			
		<display:column property="title" titleKey="tutorial.section.title" sortable="true" />
		
			
	</display:table>
</jstl:if>  
	
<a href="${sponsorship.targetPage}">
	<img src="${sponsorship.banner}" alt="Banner" width="300" height="400" >
</a>
<br />
  
<a href="tutorial/list.do">
	<spring:message	code="tutorial.return" />
</a> 

	