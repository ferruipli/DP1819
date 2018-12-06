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
	<strong> <spring:message code="application.fixUpTask" />: </strong>
	<jstl:out value="${application.fixUpTask.ticker}" />
</p>

	<p>
	<strong> <spring:message code="application.registerMoment" />: </strong>
	<jstl:out value="${application.registerMoment}" />
</p>

<p>
	<strong> <spring:message code="application.offeredPrice" />: </strong>
	<jstl:out value="${application.offeredPrice}" />
</p>

<p>
	<strong> <spring:message code="application.status" />: </strong>
	<jstl:out value="${application.status}" />
</p>

<p>
	<strong> <spring:message code="application.customerComments" />: </strong>
	<jstl:out value="${application.customerComments}" />
</p>
<p>
	<strong> <spring:message code="application.handyWorkerComments" />: </strong>
	<jstl:out value="${application.handyWorkerComments}" />
</p>
	   
<input type="button" 
	   name="return"
	   value="<spring:message code="application.return" />"
	   onclick="javascript: relativeRedir('application/handyWorker/list.do');" />	
	