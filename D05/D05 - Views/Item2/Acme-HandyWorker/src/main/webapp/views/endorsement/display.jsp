<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<strong> <spring:message code="endorsement.comments" />: </strong>
	<jstl:out value="${endorsement.comments}" />
</p>

<p>
	<spring:message code="endorsement.formatMoment2" var="w_format" />
	<strong> <spring:message code="endorsement.moment" />: </strong>
	<fmt:formatDate value="${endorsement.moment}" pattern="${w_format}" />
</p>

<p>
	<strong> <spring:message code="endorsement.sender" />: </strong>
	<a href="actor/handyWorker,customer/display.do?actorId=${sender.id}}">
		<jstl:out value="${endorsement.sender.name}" />
	</a>
</p>

<p>
	<strong> <spring:message code="endorsement.recipient" />: </strong>
	<a href="actor/handyWorker,customer/display.do?actorId=${recipient.id}}">
		<jstl:out value="${endorsement.recipient.name}" />
	</a>
</p>
<br />

<p>
	<a href="endorsement/handyWorker,customer/list.do">
		 <spring:message code="endorsement.return" />
	</a>
</p>	   
	   