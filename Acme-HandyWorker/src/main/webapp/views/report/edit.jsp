<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="report/referee/edit.do" modelAttribute="report">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="moment"/>
	<form:hidden path="notes"/>
	<form:hidden path="finalMode"/>
	
	<form:label path="description">
		<spring:message code="report.description"/>:
	</form:label>
	<form:textarea path="description"/>
	<form:errors cssClass="error" path="description"/>
	<br/>
	
	<form:label path="attachments">
		<spring:message code="report.attachments"/>:
	</form:label>
	<form:textarea path="attachments"/>
	<form:errors cssClass="error" path="attachments"/>
	<br/>
	
	<input type="hidden" name="complaintId" value="${complaintId}"/>
	
	<!-- Buttons -->
	
	<input type="submit" name="save" value="<spring:message code="report.save"/>"/>
	<jstl:if test="${report.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="report.delete"/>" onclick="return confirm('<spring:message code="report.confirm.delete"/>')"/>
	</jstl:if>
	<input type="button" name="cancel"	value="<spring:message code="report.cancel"/>" onclick="javascript: relativeRedir('complaint/customer,handyWorker,referee/display.do?complaintId=${complaintId}');"/>
	<br />

</form:form>