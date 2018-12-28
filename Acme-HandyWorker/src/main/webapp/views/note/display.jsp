<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('REFEREE')">
	<jstl:set var="role" value="referee"/>
</security:authorize>
<security:authorize access="hasRole('CUSTOMER')">
	<jstl:set var="role" value="customer"/>
</security:authorize>
<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:set var="role" value="handyWorker"/>
</security:authorize>


<spring:message code="note.date.format2" var="dateFormat"/>
<strong><spring:message code="note.moment"/>:</strong>
<fmt:formatDate value="${note.moment}" pattern="${dateFormat}"/>
<br/>

<jstl:if test="${not empty note.commentCustomer}">
	<strong><spring:message code="note.comment.customer"/>:</strong>
	<jstl:out value="${note.commentCustomer}"/>
	<br/>
</jstl:if>

<jstl:if test="${not empty note.commentHandyWorker}">
	<strong><spring:message code="note.comment.handyWorker"/>:</strong>
	<jstl:out value="${note.commentHandyWorker}"/>
	<br/>
</jstl:if>

<jstl:if test="${not empty note.commentReferee}">
	<strong><spring:message code="note.comment.referee"/>:</strong>
	<jstl:out value="${note.commentReferee}"/>
	<br/>
</jstl:if>
	
	
<!-- Links -->
<br>

<a href="note/customer,handyWorker,referee/back.do?noteId=${note.id}"><spring:message code="note.back"/></a>
<jstl:if test="${(empty note.commentCustomer || role != 'customer') && (empty note.commentHandyWorker || role != 'handyWorker') && (empty note.commentReferee || role != 'referee')}">
	&nbsp;
	<a href="note/customer,handyWorker,referee/comment.do?noteId=${note.id}&role=${role}"><spring:message code="note.write.comment"/></a>
</jstl:if>