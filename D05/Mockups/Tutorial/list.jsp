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

	<display:column property="title" titleKey="tutorial.title" sortable="true" />


	<spring:message code="tutorial.formatMoment" var="formatMoment"/>
	<display:column  property="moment" titleKey="tutorial.moment" sortable="true"  format="${formatMoment}"  />

	<display:column property="summary" titleKey="tutorial.summary" />
	
	
	<display:column>
	<a href="tutorial/display.do?tutorialId=${row.id}">
		<spring:message	code="tutorial.display" />			
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