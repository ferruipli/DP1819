<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="category/administrator/edit.do" modelAtribute="category">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="descendants" />
	
	<form:label path="parent">
		<spring:message code="category.parent" />:
	</form:label>
	<form:select path="parent">
		<form:options item="${parents}" itemLabel="name" itemValue="id" />
		<form:option label="----" value="0" />
	</form:select>
	<form:errors cssClass="error" path="parent" />
	<br/>
	
	<forEach var="categoryTranslation" begin="1" end="${number_languages}">
		<form:hidden path="id" />
		<form:hidden path="version" />
		
		<form:label path="name">
			<spring:message code="categoryTranslation.name" />:
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
		<br/>
		
		<form:label path="language">
			<spring:message code="categoryTranslation.language" />:
		</form:label>
		<form:input path="language" />
		<form:errors cssClass="error" path="language" />
		<br/>
	</forEach>
	
	<input type="submit" name="save" value="<spring:message code="category.save" />" />
	<jstl:if test="${category.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="category.delete" />"
			onclick="return confirm('<spring:message code="category.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"	value="<spring:message code="category.cancel" />
		"onclick="javascript: relativeRedir('categoty/customer/list.do');" />
	<br />
</form:form>