<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="note/customer,handyWorker,referee/edit.do" modelAttribute="note">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="moment"/>
	
	<security:authorize access="hasRole('REFEREE')">
		<form:hidden path="commentCustomer"/>
		<form:hidden path="commentHandyWorker"/>
		
		<form:label path="commentReferee">
			<spring:message code="note.comment"/>:
		</form:label>
		<form:textarea path="commentReferee"/>
		<form:errors cssClass="error" path="commentReferee"/>
		<br/>
	</security:authorize>
	
	<security:authorize access="hasRole('CUSTOMER')">
		<form:hidden path="commentReferee"/>
		<form:hidden path="commentHandyWorker"/>
		
		<form:label path="commentCustomer">
			<spring:message code="note.comment"/>:
		</form:label>
		<form:textarea path="commentCustomer"/>
		<form:errors cssClass="error" path="commentCustomer"/>
		<br/>
	</security:authorize>
	
	<security:authorize access="hasRole('HANDYWORKER')">
		<form:hidden path="commentCustomer"/>
		<form:hidden path="commentReferee"/>
		
		<form:label path="commentHandyWorker">
			<spring:message code="note.comment"/>:
		</form:label>
		<form:textarea path="commentHandyWorker"/>
		<form:errors cssClass="error" path="commentHandyWorker"/>
		<br/>
	</security:authorize>
	
	<input type="hidden" name="reportId" value="${reportId}"/>
	
	
	<!-- Buttons -->
	
	<input type="submit" name="save" value="<spring:message code="note.save"/>" onclick="return confirm('<spring:message code="note.confirm.save"/>')"/>
	
	<jstl:if test="${note.id == 0}">
		<jstl:set var="cancelRedir" value="note/customer,handyWorker,referee/list.do?reportId=${reportId}"/>
	</jstl:if>
	<jstl:if test="${note.id != 0}">
		<jstl:set var="cancelRedir" value="note/customer,handyWorker,referee/back.do?noteId=${note.id}"/>
	</jstl:if>
	
	<input type="button" name="cancel"	value="<spring:message code="note.cancel"/>" onclick="javascript: relativeRedir('${cancelRedir}');"/>
	<br />

</form:form>