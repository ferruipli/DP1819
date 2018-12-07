<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<fieldset>
	<legend><spring:message code="actor.legend"/></legend>
	<p> <strong> <spring:message code="actor.name" />: </strong> </p> <jstl:out value="${actor.name}" />
	<br />
	
	<p> <strong> <spring:message code="actor.middleName" />: </strong> </p> <jstl:out value="${actor.middleName}" />
	<br />

	<p> <strong> <spring:message code="actor.surname" />: </strong> </p> <jstl:out value="${actor.surname}" />
	<br />

	<p> <strong> <spring:message code="actor.photoLink" />: </strong> </p> <jstl:out value="${actor.photoLink}" />
	<br />
	
	<p> <strong> <spring:message code="actor.email" />: </strong> </p> <jstl:out value="${actor.email}" />
	<br />
	
	<p> <strong> <spring:message code="actor.phoneNumber" />: </strong> </p> <jstl:out value="${actor.phoneNumber}" />
	<br />

	<p> <strong> <spring:message code="actor.address" />: </strong> </p> <jstl:out value="${actor.address}" />

</fieldset>

<fieldset>
	<legend><spring:message code="userAccount.legend"/></legend>
	<p> <strong> <spring:message code="actor.username" />: </strong> </p> <jstl:out value="${actor.userAccount.username}" />
	<br />
	
	<p> <strong> <spring:message code="actor.authority" />: </strong> </p> <jstl:out value="${actor.userAccount.authorities}" />

</fieldset>

<fieldset>
	<legend><spring:message code="actor.other.legend"/></legend>
	<p> <strong> <spring:message code="actor.socialProfile" />: </strong> </p> 
	<jstl:choose>
		<jstl:when test="${role == 'customer'}">
			<a href="socialProfile/customer/list.do?actorId=${actor.id}"><spring:message code="actor.socialProfiles"/></a>
		</jstl:when>
		<jstl:when test="${role == 'handyworker'}">
			<a href="socialProfile/handyworker/list.do?actorId=${actor.id}"><spring:message code="actor.socialProfiles"/></a>
		</jstl:when>
		<jstl:when test="${role == 'sponsor'}">
			<a href="socialProfile/sponsor/list.do?actorId=${actor.id}"><spring:message code="actor.socialProfiles"/></a>
		</jstl:when>
		<jstl:when test="${role == 'administrator'}">
			<a href="socialProfile/administrator/list.do?actorId=${actor.id}"><spring:message code="actor.socialProfiles"/></a>
		</jstl:when>
		<jstl:when test="${role == 'referee'}">
			<a href="socialProfile/referee/list.do?actorId=${actor.id}"><spring:message code="actor.socialProfiles"/></a>
		</jstl:when>
	</jstl:choose>
</fieldset>

<input type="button" name="return" value="<spring:message code="box.return" />" 
				onclick="javascript: relativeRedir('welcome/index.do');" />