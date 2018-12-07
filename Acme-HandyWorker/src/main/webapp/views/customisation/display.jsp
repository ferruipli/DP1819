<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<strong> <spring:message code="customisation.systemName" />: </strong>
	<jstl:out value="${customisation.systemName}" />
</p>

<p>
	<strong> <spring:message code="customisation.banner" />: </strong>
	<jstl:out value="${customisation.banner}" />
</p>

<p>
	<strong> <spring:message code="customisation.welcomeMessageEn" />: </strong>
	<jstl:out value="${customisation.welcomeMessageEn}" />
</p>

<p>
	<strong> <spring:message code="customisation.welcomeMessageEs" />: </strong>
	<jstl:out value="${customisation.welcomeMessageSp}" />
</p>

<p>
	<strong> <spring:message code="customisation.vat" />: </strong>
	<jstl:out value="${customisation.VAT*100}" />
	<jstl:out value=" %" />
</p>

<p>
	<strong> <spring:message code="customisation.countryCode" />: </strong>
	<jstl:out value="${customisation.countryCode}" />
</p>

<p>
	<strong> <spring:message code="customisation.timeCached" />: </strong>
	<jstl:out value="${customisation.timeCachedFinderResults}" />
</p>

<p>
	<strong> <spring:message code="customisation.maxResults" />: </strong>
	<jstl:out value="${customisation.maxFinderResults}" />
</p>

<strong> <spring:message code="customisation.creditCardMakes" />: </strong>
<jstl:forEach var="row" items="${creditCardMakes}">
	<p> <jstl:out value="${row}" /> </p>
</jstl:forEach>

<strong> <spring:message code="customisation.positiveWords" />: </strong>
<jstl:forEach var="row" items="${positiveWords}">
	<p> <jstl:out value="${row}" /> </p>
</jstl:forEach>

<strong> <spring:message code="customisation.negativeWords" />: </strong>
<jstl:forEach var="row" items="${negativeWords}">
	<p> <jstl:out value="${row}" /> </p>
</jstl:forEach>

<strong> <spring:message code="customisation.spamWords" />: </strong>
<jstl:forEach var="row" items="${spamWords}">
	<p> <jstl:out value="${row}" /> </p>
</jstl:forEach>
<br />

<a href="customisation/administrator/edit.do">
	<spring:message code="customisation.edit" />
</a>
<br />

<input type="button" 
	   name="return"
	   value="<spring:message code="endorsement.return" />"
	   onclick="javascript: relativeRedir('welcome/index.do');" />
	   
	   