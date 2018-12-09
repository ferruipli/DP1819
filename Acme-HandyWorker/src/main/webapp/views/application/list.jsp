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

<display:table id="row" name="applications"  requestURI="${requestURI}" pagesize="5" class="displaytag">


	<jsp:useBean id="now" class="java.util.Date" />
	<jstl:if test="${row.status=='PENDING' && (row.registerMoment.time<now.time)}">
		<jstl:set var="appColor" value="colorGrey" />
	</jstl:if>
	<jstl:if test="${row.status=='REJECTED'}">
		<jstl:set var="appColor" value="colorOrange" />
	</jstl:if>
	<jstl:if test="${row.status=='ACCEPTED'}">
		<jstl:set var="appColor" value="colorGreen" />
	</jstl:if>
	

	<spring:message code="application.formatMoment" var="formatMomentHeader" />
	<display:column  property="registerMoment" titleKey="application.registerMoment" sortable="true" format="${formatMomentHeader}" class="${appColor}" />

	<display:column property="fixUpTask.ticker" titleKey="application.fixUpTask"  class="${appColor}"/>

	<display:column property="status" titleKey="application.status"  sortable="true"  />

	<security:authorize access="hasRole('HANDYWORKER')">
	<display:column class="${appColor}" >
		<a href="application/handyWorker,customer/edit.do?applicationId=${row.id}">
					<spring:message	code="application.edit" />
		</a>
	</display:column>

	</security:authorize>

	<display:column class="${appColor}" >
		<a href="application/handyWorker,customer/display.do?applicationId=${row.id}">
					<spring:message	code="application.display" />
		</a>
	</display:column>

<security:authorize access="hasRole('CUSTOMER')">	
	<jstl:if test="${row.status=='PENDING'}">
	<display:column class="${appColor}">
		
				<a href="application/handyWorker,customer/edit.do?applicationId=${row.id}">
					<spring:message	code="application.status.change" />
				</a>
	</display:column>
	
			</jstl:if>

</security:authorize>
	
</display:table>