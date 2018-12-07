<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<strong><spring:message code="fixUpTask.ticker"/>:</strong>
<jstl:out value="${fixUpTask.ticker}"/>
<br/>

<strong><spring:message code="fixUpTask.customer"/>:</strong>
<a href="customer/referee,customer,handyWorker,administrator/display.do?customerId=${fixUpTask.customer.id}"><jstl:out value="${fixUpTask.customer.name} ${fixUpTask.customer.surname}"/></a>
<br/>

<spring:message code="fixUpTask.date.format2" var="dateFormat"/>
<strong><spring:message code="fixUpTask.publicationMoment"/>:</strong>
<fmt:formatDate value="${fixUpTask.publicationMoment}" pattern="${dateFormat}"/>
<br/>

<strong><spring:message code="fixUpTask.description"/>:</strong>
<jstl:out value="${fixUpTask.description}"/>
<br/>

<strong><spring:message code="fixUpTask.address"/>:</strong>
<jstl:out value="${fixUpTask.address}"/>
<br/>

<strong><spring:message code="fixUpTask.maxPrice"/>:</strong>
<jstl:out value="${fixUpTask.maxPrice}"/> &#8364;
<br/>

<strong><spring:message code="fixUpTask.startDate"/>:</strong>
<fmt:formatDate value="${fixUpTask.startDate}" pattern="${dateFormat}"/>
<br/>

<strong><spring:message code="fixUpTask.endDate"/>:</strong>
<fmt:formatDate value="${fixUpTask.endDate}" pattern="${dateFormat}"/>
<br/>

<strong><spring:message code="fixUpTask.warranty"/>:</strong>
<a href="warranty/referee,customer,handyWorker/display.do?warrantyId=${fixUpTask.warranty.id}"><jstl:out value="${fixUpTask.warranty.title}"/></a>
<br/>

<strong><spring:message code="fixUpTask.category"/>:</strong>
<!-- TODO: en lugar de usar categoryTranslation habría que llamar a una función javascript que me devuelva la traducción dependiendo del valor de locale -->
<jstl:out value="${categoryTranslation.name}"/>
<br/>

<strong><spring:message code="fixUpTask.applications"/>:</strong>
<a href="application/referee,customer,handyWorker/list.do?fixUpTaskId=${fixUpTask.id}"><spring:message code="fixUpTask.application.list"/></a>
<br/>

<strong><spring:message code="fixUpTask.complaints"/>:</strong>
<a href="complaint/referee,customer,handyWorker/list.do?fixUpTaskId=${fixUpTask.id}"><spring:message code="fixUpTask.complaint.list"/></a>
<br/>

<jstl:if test="${isWorkable}">
	<strong><spring:message code="fixUpTask.phases"/>:</strong>
	<a href="phase/referee,customer,handyWorker/list.do?fixUpTaskId=${fixUpTask.id}"><spring:message code="fixUpTask.phase.list"/></a>
	<br/>
</jstl:if>
	
	
<!-- Buttons -->

<security:authorize access="hasRole('REFEREE')">
	<input type="button" name="back" value="<spring:message code="fixUpTask.back"/>" onclick="javascript: relativeRedir('complaint/referee/listSelfAssigned.do');"/>
</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">
	<input type="button" name="back" value="<spring:message code="fixUpTask.back"/>" onclick="javascript: relativeRedir('fixUpTask/customer/list.do');"/>
</security:authorize>

<security:authorize access="hasRole('HANDYWORKER')">
	<input type="button" name="back" value="<spring:message code="fixUpTask.back"/>" onclick="javascript: relativeRedir('application/handyWorker/list.do');"/>
</security:authorize>