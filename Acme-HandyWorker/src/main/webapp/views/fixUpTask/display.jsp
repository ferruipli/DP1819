<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="fixUpTask.date.format2" var="dateFormat"/>
<spring:message code="fixUpTask.date.format3" var="momentFormat"/>

<fieldset>
	<legend><spring:message code="fixUpTask.attributes"/></legend>

	<strong><spring:message code="fixUpTask.ticker"/>:</strong>
	<jstl:out value="${fixUpTask.ticker}"/>
	<br/>
	
	<strong><spring:message code="fixUpTask.customer"/>:</strong>
	<a href="actor/administrator,customer,handyWorker,referee,sponsor/display.do?actorId=${fixUpTask.customer.id}"><jstl:out value="${fixUpTask.customer.name} ${fixUpTask.customer.surname}"/></a>
	<br/>
	
	<strong><spring:message code="fixUpTask.publicationMoment"/>:</strong>
	<fmt:formatDate value="${fixUpTask.publicationMoment}" pattern="${momentFormat}"/>
	<br/>
	
	<strong><spring:message code="fixUpTask.description"/>:</strong>
	<jstl:out value="${fixUpTask.description}"/>
	<br/>
	
	<strong><spring:message code="fixUpTask.address"/>:</strong>
	<jstl:out value="${fixUpTask.address}"/>
	<br/>
	
	<spring:message code="fixUpTask.vat" var="vatTag"/>
	<strong><spring:message code="fixUpTask.maxPrice"/>:</strong>
	<fmt:formatNumber type="number" maxFractionDigits="2" value="${fixUpTask.maxPrice * (1 + vat)}"/> &#8364; <jstl:out value="(${vat*100}% ${vatTag} Inc.)"/>
	<br/>
	
	<strong><spring:message code="fixUpTask.startDate"/>:</strong>
	<fmt:formatDate value="${fixUpTask.startDate}" pattern="${dateFormat}"/>
	<br/>
	
	<strong><spring:message code="fixUpTask.endDate"/>:</strong>
	<fmt:formatDate value="${fixUpTask.endDate}" pattern="${dateFormat}"/>
	<br/>
	
	<strong><spring:message code="fixUpTask.warranty"/>:</strong>
	<a href="warranty/customer,handyWorker,referee/display.do?warrantyId=${fixUpTask.warranty.id}&fixUpTaskId=${fixUpTask.id}"><jstl:out value="${fixUpTask.warranty.title}"/></a>
	<br/>
	
	<strong><spring:message code="fixUpTask.category"/>:</strong>
	<jstl:out value="${category}"/>
	<br/>
</fieldset>

<fieldset>
	<legend><spring:message code="fixUpTask.references"/></legend>

	<strong><spring:message code="fixUpTask.applications"/>:</strong>
	<a href="application/customer,handyWorker,referee/list.do?fixUpTaskId=${fixUpTask.id}"><spring:message code="fixUpTask.application.list"/></a>
	<br/>
	
	<jstl:if test="${isWorkable}">
		<strong><spring:message code="fixUpTask.complaints"/>:</strong>
		<a href="complaint/customer,handyWorker,referee/list.do?fixUpTaskId=${fixUpTask.id}"><spring:message code="fixUpTask.complaint.list"/></a>
		<br/>

		<strong><spring:message code="fixUpTask.phases"/>:</strong>
		<a href="phase/customer,handyWorker,referee/list.do?fixUpTaskId=${fixUpTask.id}"><spring:message code="fixUpTask.phase.list"/></a>
		<br/>
	</jstl:if>
</fieldset>
	
	
<!-- Links -->

<br>

<security:authorize access="hasRole('REFEREE')">
	<a href="complaint/referee/listSelfAssigned.do"><spring:message code="fixUpTask.back.selfAssigned"/></a>
	&nbsp;
	<a href="complaint/referee/listNotAssigned.do"><spring:message code="fixUpTask.back.notAssigned"/></a>
</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">
	<a href="fixUpTask/customer/list.do"><spring:message code="fixUpTask.back"/></a>
</security:authorize>

<security:authorize access="hasRole('HANDYWORKER')">
	<a href="fixUpTask/handyWorker/listAll.do"><spring:message code="fixUpTask.back.listAll"/></a>
	<br>
	<a href="fixUpTask/handyWorker/listInvolved.do"><spring:message code="fixUpTask.back.involved"/></a>
	<br>
	<a href="complaint/handyWorker/list.do"><spring:message code="fixUpTask.back.complaints"/></a>
	<br>
	<a href="application/handyWorker/list.do"><spring:message code="fixUpTask.back.applications"/></a>
</security:authorize>