
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="professionalRecord/handyWorker/edit.do"
	modelAttribute="professionalRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="nameCompany">
		<spring:message code="professionalRecord.nameCompany" />:
	</form:label>
	<form:input path="nameCompany" />
	<form:errors cssClass="error" path="nameCompany" />
	<br />

	<form:label path="startDate">
		<spring:message code="professionalRecord.startDate" />:
	</form:label>
	<form:input path="startDate" />
	<form:errors cssClass="error" path="startDate" />
	<br />

	<form:label path="endDate">
		<spring:message code="professionalRecord.endDate" />:
	</form:label>
	<form:input path="endDate" />
	<form:errors cssClass="error" path="endDate" />
	<br />

	<form:label path="role">
		<spring:message code="professionalRecord.role" />:
	</form:label>
	<form:input path="role" />
	<form:errors cssClass="error" path="role" />
	<br />

	<form:label path="attachment">
		<spring:message code="professionalRecord.attachment" />:
	</form:label>
	<form:input path="attachment" />
	<form:errors cssClass="error" path="attachment" />
	<br />

	<form:label path="comments">
		<spring:message code="professionalRecord.comments" />:
	</form:label>
	<form:input path="comments" />
	<form:errors cssClass="error" path="comments" />
	<br />
	
	<jstl:if test="${professionalRecord.id != 0}"> 		
		<input type="submit" name="delete" value="<spring:message code="professionalRecord.delete" />" 
		onclick="return confirm('<spring:message code="professionalRecord.confirm.delete" />')" />&nbsp; 	
	</jstl:if>

	<input type="submit" name="save"
		value="<spring:message code="professionalRecord.save" />" />

	<input type="button" name="cancel"
		value="<spring:message code="professionalRecord.cancel"/>"
		onclick="javascript: relativeRedir('curriculum/display.do');" />
	<br />
</form:form>
