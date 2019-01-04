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
	<strong> <spring:message code="application.fixUpTask" />: </strong>
	<jstl:out value="${application.fixUpTask.ticker}" />
</p>

	<p>
	<strong> <spring:message code="application.registerMoment" />: </strong>
	
	<spring:message code="application.formatMoment1" var="formatMoment"/>
		<fmt:formatDate value="${application.registerMoment}" pattern="${formatMoment}"/>
</p>

<p>
	<strong> <spring:message code="application.offeredPrice" />: </strong>
	<jstl:out value="${application.offeredPrice}" />&#8364;   (<spring:message code = "application.vat.no"></spring:message>)
</p>

<p>
	<strong> <spring:message code="application.status" />: </strong>
	<jstl:out value="${application.status}" />
</p>
<p>
	<strong> <spring:message code="application.handyWorker" />: </strong>
	<a href = "handyWorker/display.do?actorId=${application.handyWorker.id}"><jstl:out value="${application.handyWorker.name}" /></a>
<p>
	<strong> <spring:message code="application.handyWorkerComments" />: </strong>
	<jstl:out value="${application.handyWorkerComments}" />
</p>
<p>
	<strong> <spring:message code="application.customerComments" />: </strong>
	<jstl:out value="${application.customerComments}" />
</p>

<security:authorize access="hasRole('CUSTOMER')" >
	<jstl:if test="${application.status=='ACCEPTED'}">
 <fieldset>
<h2><strong> <spring:message code="application.creditCard" /> </strong></h2>
 <p>
  <strong> <spring:message code="application.creditCard.holderName" />: </strong>
	<jstl:out value="${application.creditCard.holderName}" />
</p>	<p>
	  <strong> <spring:message code="application.creditCard.brandName" />: </strong>
	<jstl:out value="${application.creditCard.brandName}" />
	</p>
	<p>
	 <strong> <spring:message code="application.creditCard.number" />: </strong>
	<jstl:out value="${application.creditCard.number}" />
	</p>
	<p>
	<strong> <spring:message code="application.creditCard.expirationMonth" />: </strong>
	<jstl:out value="${application.creditCard.expirationMonth}" />
	</p><p>
	<strong> <spring:message code="application.creditCard.expirationYear" />: </strong>
	<jstl:out value="${application.creditCard.expirationYear}" />
	 </p>
 <strong> <spring:message code="application.creditCard.cvvCode" />: </strong>
	<jstl:out value="${application.creditCard.cvvCode}" />
 
 </fieldset>
 </jstl:if>
 </security:authorize>
 
   
	<a href="application/customer,handyWorker,referee/list.do?fixUpTaskId=${application.fixUpTask.id}">
					<spring:message	code="application.return" />
				</a>
