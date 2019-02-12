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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<p>
	<strong> <spring:message code="section.number" />: </strong>
	<jstl:out value="${section.number}" />
</p>

<p>
	<strong> <spring:message code="section.title" />: </strong>
	<jstl:out value="${section.title}" />
</p>

<p>
	<strong> <spring:message code="section.text" />: </strong>
	<jstl:out value="${section.text}" />
</p>

<p>
<strong> <spring:message code="section.pictures" />: </strong>
<img src="${section.pictures}">
</p> 

	<a href="tutorial/list.do">
					<spring:message	code="section.return" />
	</a> 




	