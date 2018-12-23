<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="fixUpTask.date.format2" var="dateFormat"/>

<fieldset>
	<legend><spring:message code="fixUpTask.attributes"/></legend>

	<strong><spring:message code="fixUpTask.ticker"/>:</strong>
	<jstl:out value="${fixUpTask.ticker}"/>
	<br/>
	
	<strong><spring:message code="fixUpTask.customer"/>:</strong>
	<a href="actor/administrator,customer,handyWorker,referee/display.do?actorId=${fixUpTask.customer.id}"><jstl:out value="${fixUpTask.customer.name} ${fixUpTask.customer.surname}"/></a>
	<br/>
	
	<strong><spring:message code="fixUpTask.publicationMoment"/>:</strong>
	<fmt:formatDate value="${fixUpTask.publicationMoment}" pattern="${dateFormat}"/>
	<br/>
	
	<strong><spring:message code="fixUpTask.description"/>:</strong>
	<jstl:out value="${fixUpTask.description}"/>
	<br/>
	
	<strong><spring:message code="fixUpTask.address"/>:</strong>
	<jstl:out value="${fixUpTask.address}"/>
	<br/>
	
	<spring:message code="fixUpTask.vat" var="vatTag"/>
	<strong><spring:message code="fixUpTask.maxPrice"/>:</strong>
	<jstl:out value="${fixUpTask.maxPrice * (1 + vat)}"/> &#8364; <jstl:out value="(${vat*100}% ${vatTag} Inc.)"/>
	<br/>
	
	<strong><spring:message code="fixUpTask.startDate"/>:</strong>
	<fmt:formatDate value="${fixUpTask.startDate}" pattern="${dateFormat}"/>
	<br/>
	
	<strong><spring:message code="fixUpTask.endDate"/>:</strong>
	<fmt:formatDate value="${fixUpTask.endDate}" pattern="${dateFormat}"/>
	<br/>
	
	<strong><spring:message code="fixUpTask.warranty"/>:</strong>
	<a href="warranty/customer,handyWorker,referee/display.do?warrantyId=${fixUpTask.warranty.id}"><jstl:out value="${fixUpTask.warranty.title}"/></a>
	<br/>
	
	<strong><spring:message code="fixUpTask.category"/>:</strong>
	<!-- TODO: en lugar de usar categoryTranslation habr�a que llamar a una funci�n javascript que me devuelva la traducci�n dependiendo del valor de locale -->
	<jstl:out value="${categoryTranslation.name}"/>
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
	</jstl:if>
	
	<jstl:if test="${isWorkable}">
		<strong><spring:message code="fixUpTask.phases"/>:</strong>
		<a href="phase/customer,handyWorker,referee/list.do?fixUpTaskId=${fixUpTask.id}"><spring:message code="fixUpTask.phase.list"/></a>
		<br/>
	</jstl:if>
</fieldset>
	
	
<!-- Buttons -->

<br>

<security:authorize access="hasRole('REFEREE')">
	<a href="complaint/referee/listSelfAssigned.do"><spring:message code="fixUpTask.back"/></a>
</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">
	<a href="fixUpTask/customer/list.do"><spring:message code="fixUpTask.back"/></a>
</security:authorize>

<security:authorize access="hasRole('HANDYWORKER')">
	<a href="application/handyWorker/list.do"><spring:message code="fixUpTask.back"/></a>
</security:authorize>