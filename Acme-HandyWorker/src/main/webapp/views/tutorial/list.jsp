<%--
 * action-2.jsp
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
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<display:table id="row" name="tutorials"  requestURI="${requestURI}" pagesize="5" class="displaytag">

	<spring:message code="tutorial.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />


	<spring:message code="tutorial.formatMoment" var="formatMoment"/>
	<spring:message code="tutorial.moment" var="momentHeader" />
	<display:column  property="moment" title="${momentHeader}" sortable="true"  format="${formatMoment}"  />
		 
	<spring:message code="tutorial.summary" var="summaryHeader" />
	<display:column property="summary" title="${summaryHeader}" />
	
	<spring:message code="tutorial.pictures" var="picturesHeader" />
	<display:column property="pictures" title="${picturesHeader}" />
	
	<spring:message code="tutorial.sections" var="sectionsHeader" />
	<display:column property="sections" title="${sectionsHeader}" />
	
	<spring:message code="tutorial.handyWorker" var="handyWorkerHeader" />
	<display:column property="handyWorker.name" title="${handyWorkerHeader}" >
	</display:column>
	
	<display:column>
	<a href="handyWorker/display.do?handyWorkerId=${row.handyWorker.id}">
		<spring:message	code="tutorial.handyWorker.display" />			
	</a>	
	</display:column>
		
		
	<security:authorize access="hasRole('HANDYWORKER')">
	<display:column >
		<a href="tutorial/handyWorker/edit.do?tutorialId=${row.id}">
			<spring:message	code="tutorial.edit" />
		</a>
	</display:column>
	</security:authorize>
</display:table>

<security:authorize access="hasRole('HANDYWORKER')">
		<a href="tutorial/handyWorker/create.do?">
			<spring:message	code="tutorial.create" />
		</a>
	</security:authorize>