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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<display:table id="row" name="applications"  requestURI="${requestURI}"  class="displaytag">


	<jsp:useBean id="now" class="java.util.Date" />
	<jstl:if test="${row.status=='PENDING' && (row.fixUpTask.endDate.time<now.time)}">
		<jstl:set var="colorValue" value="grey" />
	</jstl:if>
	<jstl:if test="${row.status=='REJECTED'}">
		<jstl:set var="colorValue" value="orange" />
	</jstl:if>
	<jstl:if test="${row.status=='ACCEPTED'}">
		<jstl:set var="colorValue" value="green" />
	</jstl:if>
	

	<spring:message code="application.formatMoment" var="formatMomentHeader" />
	<display:column  property="registerMoment" titleKey="application.registerMoment" sortable="true" format="${formatMomentHeader}" style="background-color:${colorValue }" />

	<display:column property="fixUpTask.ticker" titleKey="application.fixUpTask"  style="background-color:${colorValue }"/>

	<display:column property="status" titleKey="application.status"  sortable="true" style="background-color:${colorValue }" />

<security:authorize access="hasRole('HANDYWORKER')">
	<display:column style="background-color:${colorValue }" >
		<a href="application/handyWorker/edit.do?applicationId=${row.id}">
					<spring:message	code="application.edit" />
		</a>
	</display:column>
</security:authorize>

	<display:column style="background-color:${colorValue }" >
		<a href="application/handyWorker,customer/display.do?applicationId=${row.id}">
					<spring:message	code="application.display" />
		</a>
	</display:column>

<security:authorize access="hasRole('CUSTOMER')">	
	<jstl:if test="${row.status=='PENDING'}">
	<display:column style="background-color:${colorValue}">
			<a href="application/customer/cancel.do?applicationId=${row.id}">
				<spring:message	code="application.cancel" />
			</a>
	</display:column>
	
	<display:column style="background-color:${colorValue}">
			<a href="application/customer/edit.do?applicationId=${row.id}">
				<spring:message	code="application.accept" />
			</a>
	</display:column>	
	</jstl:if>

</security:authorize>
	
</display:table>