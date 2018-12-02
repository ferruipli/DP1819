<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="finder/edit.do" modelAttribute="finder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="lastUpdate" />
	<form:hidden path="fixUpTasks" />	

	<form:label path="keyWord">
		<spring:message code="finder.keyWord" />:
	</form:label>
	<form:input path="keyWord" />
	<form:errors cssClass="error" path="keyWord" />
	<br />
	
	<form:label path="startPrice">
		<spring:message code="finder.startPrice" />:
	</form:label>
	<form:input path="startPrice" />
	<form:errors cssClass="error" path="startPrice" />
	<br />

	<form:label path="endPrice">
		<spring:message code="finder.endPrice" />:
	</form:label>
	<form:input path="endPrice" />
	<form:errors cssClass="error" path="endPrice" />
	<br />
	
	<form:label path="startDate">
		<spring:message code="finder.startDate" />:
	</form:label>
	<form:input path="startDate" />
	<form:errors cssClass="error" path="startDate" />
	<br />
	
	<form:label path="endDate">
		<spring:message code="finder.endDate" />:
	</form:label>
	<form:input path="endDate" />
	<form:errors cssClass="error" path="endDate" />
	<br />

	<form:label path="warranty">
		<spring:message code="finder.warranty" />:
	</form:label>
	<form:input path="warranty" />
	<form:errors cssClass="error" path="warranty" />
	<br />
	
	<form:label path="category">
		<spring:message code="finder.category" />:
	</form:label>
	<form:input path="category" />
	<form:errors cssClass="error" path="category" />
	<br />

	
	<input type="submit" name="search" value="<spring:message code="finder.search" />" />
	<input type="button" name="cancel"	value="<spring:message code="finder.cancel" />
		"onclick="javascript: relativeRedir('fixUpTask/list.do');" />
	<br />

</form:form>