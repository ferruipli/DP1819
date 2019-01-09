

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="educationRecord/handyWorker/edit.do"
	modelAttribute="educationRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="titleDiploma">
		<spring:message code="educationRecord.titleDiploma" />:
	</form:label>
	<form:input path="titleDiploma" />
	<form:errors cssClass="error" path="titleDiploma" />
	<br />

	<form:label path="startDate">
		<spring:message code="educationRecord.startDate" />:
	</form:label>
	<form:input path="startDate" placeholder = "dd/mm/yyyy"/>
	<form:errors cssClass="error" path="startDate" />
	<br />

	<form:label path="endDate">
		<spring:message code="educationRecord.endDate" />:
	</form:label>
	<form:input path="endDate" placeholder = "dd/mm/yyyy"/>
	<form:errors cssClass="error" path="endDate" />
	<br />

	<form:label path="institution">
		<spring:message code="educationRecord.institution" />:
	</form:label>
	<form:input path="institution" />
	<form:errors cssClass="error" path="institution" />
	<br />

	<form:label path="attachment">
		<spring:message code="educationRecord.attachment" />:
	</form:label>
	<form:textarea path="attachment" />
	<form:errors cssClass="error" path="attachment" />
	<br />

	<form:label path="comments">
		<spring:message code="educationRecord.comments" />:
	</form:label>
	<form:textarea path="comments" />
	<form:errors cssClass="error" path="comments" />
	<br />
	
	<jstl:if test="${educationRecord.id != 0}"> 		
		<input type="submit" name="delete" value="<spring:message code="educationRecord.delete" />" 
		onclick="return confirm('<spring:message code="educationRecord.confirm.delete" />')" />&nbsp; 	
	</jstl:if>

	<input type="submit" name="save"
		value="<spring:message code="educationRecord.save" />" />

	<input type="button" name="cancel"
		value="<spring:message code="educationRecord.cancel"/>"
		onclick="javascript: relativeRedir('curriculum/display.do?handyWorkerId=${handyWorkerId}');" />
	<br />
</form:form>
