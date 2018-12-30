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
	<strong> <spring:message code="sponsorship.targetPage" />: </strong>
	<jstl:out value="${sponsorship.targetPage}" />
</p>

<p>
	<strong> <spring:message code="sponsorship.banner" />: </strong>
	<img src="${sponsorship.banner}">
</p>



<input type="button" 
	   name="return"
	   value="<spring:message code="sponsorship.return" />"
	   onclick="javascript: relativeRedir('sponsorship/sponsor/list.do');" />	


	