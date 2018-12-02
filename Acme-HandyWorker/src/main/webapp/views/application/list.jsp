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

<display:table id="row" name="applications"  requestURI="${requestURI}" pagesize="5" class="displaytag">


	<jsp:useBean id="now" class="java.util.Date" />
	<jstl:if test="${row.status=='PENDING' && (row.registerMoment.time<now.time)}">
		<jstl:set var="appColor" value="colorGrey" />
	</jstl:if>
	<jstl:if test="${row.status=='REJECTED'}">
		<jstl:set var="appColor" value="colorOrange" />
	</jstl:if>
	<jstl:if test="${row.status=='ACCEPTED'}">
		<jstl:set var="colorClass" value="colorGreen" />
	</jstl:if>
	

	<spring:message code="application.registerMoment" var="registerMomentHeader" />
	<spring:message code="application.formatMoment" var="formatMomentHeader" />
	<display:column  property="registerDate" title="${registerDateHeader}" sortable="true" format="${formatDateHeader}" class="${appColor}" />
		 
	<spring:message code="application.offeredPrice" var="offeredPriceHeader" />
	<display:column property="registerDate" title="${registerDateHeader}" sortable="true" class="${appColor}" />
		 
	<spring:message code="application.fixUpTask" var="fixUpTaskHeader" />
	<display:column property="fixUpTask" title="${fixUpTaskHeader}"  class="${appColor}"/>
	
	<spring:message code="application.status" var="statusHeader" />
	<display:column property="status" title="${statusHeader}"  sortable="true"  />

	<security:authorize access="hasRole('CUSTOMER')">
	<spring:message code="application.handyWorker" var="handyWorkerHeader" />
	<display:column  property="handyWorker" title="${handyWorkerHeader.name}"  class="${appColor}"/>
	
	<display:column class="${appColor}">
			<jstl:if test="${row.status=='PENDING'}">
				<a href="application/customer/edit.do?applicationId=${row.id}">
					<spring:message	code="application.edit" />
				</a>
			</jstl:if>
	</display:column>

	<display:column class="${appColor}">
			<jstl:if test="${row.status=='PENDING'}">
				<a href="application/customer/cancel.do?applicationId=${row.id}">
					<spring:message	code="application.cancel" />
				</a>
			</jstl:if>
	</display:column>
	</security:authorize>
	
	<spring:message code="application.handyWorkerComments" var="handyWorkerCommentsHeader" />
	<display:column property="handyWorkerComments" title="${handyWorkerCommentsHeader}" class="${appColor}"/>
	
	<spring:message code="application.customerComments" var="customerCommentsHeader" />
	<display:column property="customerComments" title="${customerCommentsHeader}"class="${appColor}"/>
	
	<security:authorize access="hasRole('HANDYWORKER')">
	<display:column class="${appColor}" >
		<a href="application/handyWorker/edit.do?applicationId=${row.id}">
					<spring:message	code="application.edit" />
		</a>
	</display:column>
	
	<display:column class="${appColor}" >
		<a href="application/handyWorker/display.do?applicationId=${row.id}">
					<spring:message	code="application.display" />
		</a>
	</display:column>
	</security:authorize>	
</display:table>