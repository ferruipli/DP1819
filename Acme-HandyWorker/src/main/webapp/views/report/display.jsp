<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jstl:if test="${!report.finalMode}">
	<p style="color:blue;"><spring:message code="report.info"/></p>
</jstl:if>

<fieldset>
	<legend><spring:message code="report.attributes"/></legend>
	
	<spring:message code="report.date.format2" var="dateFormat"/>
	<strong><spring:message code="report.moment"/>:</strong>
	<fmt:formatDate value="${report.moment}" pattern="${dateFormat}"/>
	<br/>
	
	<strong><spring:message code="report.description"/>:</strong>
	<jstl:out value="${report.description}"/>
	<br/>
	
	<jstl:if test="${not empty attachments}">
		<strong><spring:message code="report.attachments"/>:</strong>
		<br>
		<ul>
			<jstl:forEach var="attachment" items="${attachments}">
				<li> <a href="${attachment}"><jstl:out value="${attachment}"/></a> </li>
			</jstl:forEach>
		</ul>
	</jstl:if>
</fieldset>

<jstl:if test="${report.finalMode}">
	<fieldset>
		<legend><spring:message code="report.references"/></legend>
		
		<strong><spring:message code="report.notes"/>:</strong>
		<a href="note/customer,handyWorker,referee/list.do?reportId=${report.id}"><spring:message code="report.notes.list"/></a>
		<br/>
	</fieldset>
</jstl:if>
	
	
<!-- Links -->
<br>

<a href="report/customer,handyWorker,referee/back.do?reportId=${report.id}"><spring:message code="report.back"/></a>

<jstl:if test="${reportEditionPerm && !report.finalMode}">
	&nbsp;
	<a href="report/referee/edit.do?reportId=${report.id}"><spring:message code="report.edit"/></a>
	&nbsp;
	<a href="report/referee/makeFinal.do?reportId=${report.id}"><spring:message code="report.make.final"/></a>
</jstl:if>