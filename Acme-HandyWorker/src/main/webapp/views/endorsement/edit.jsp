<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="endorsement/customer/edit.do" modelAtribute="endorsement">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="sender" />
	
	<form:label path="comments">
		<spring:message code="endorsement.comments" />:
	</form:label>
	<form:textarea path="comments" />
	<form:errors cssClass="error" path="comments" />
	<br/>
	
	<form:label path="recipient">
		<spring:message code="endorsement.recipient" />:
	</form:label> 
	<form:select path="recipient">
		<form:options items="${recipients}" itemLabel="name" itemValue="id" />
		<form:option label="----" value="0" />
	</form:select>
	<form:errors cssClass="error" path="recipient" />
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="endorsement.save" />" />
	<jstl:if test="${endorsement.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="endorsement.delete" />"
			onclick="return confirm('<spring:message code="endorsement.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"	value="<spring:message code="endorsement.cancel" />
		"onclick="javascript: relativeRedir('endorsement/customer/list.do');" />
	<br />
	
</form:form>