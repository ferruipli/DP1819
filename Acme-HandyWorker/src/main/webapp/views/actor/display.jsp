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
	<p> <strong> <spring:message code="actor.fullname" /> </strong>  <jstl:out value="${actor.fullname}" /></p>

	<p> <strong> <spring:message code="actor.photoLink" /> </strong> <jstl:out value="${actor.photoLink}" /></p>
	
	<p> <strong> <spring:message code="actor.email" /> </strong>  <jstl:out value="${actor.email}" /></p>
	
	<p> <strong> <spring:message code="actor.phoneNumber" /> </strong>  <jstl:out value="${actor.phoneNumber}" /></p>

	<p> <strong> <spring:message code="actor.address" /> </strong>  <jstl:out value="${actor.address}" /></p>
	
	<jstl:if test="${isEndorsable && actor.score != null}">
		<p> <strong> <spring:message code="endorsable.score" /> </strong>  <jstl:out value="${actor.score}" /></p>
	</jstl:if>
	
	<a href="actor/administrator,customer,handyWorker,referee,sponsor/edit.do?actorId=${actor.id}"><spring:message code="actor.edit"/></a>
</fieldset>

<fieldset>
	<legend><spring:message code="userAccount.legend"/></legend>
	<p> <strong> <spring:message code="actor.username" />: </strong>  <jstl:out value="${actor.userAccount.username}" /></p>
	
	<p> <strong> <spring:message code="actor.authority" />: </strong>  <jstl:out value="${actor.userAccount.authorities}" /></p>

</fieldset>

<fieldset>
	<legend><spring:message code="actor.other.legend"/></legend>
	<p> <strong> <spring:message code="actor.socialProfile" />: </strong>  
		<a href="socialProfile/administrator,customer,handyWorker,referee,sponsor/list.do?actorId=${actor.id}"><spring:message code="actor.socialProfiles"/></a>
	</p>
	<jstl:if test="${actor.userAccount.authorities=='[HANDYWORKER]'}">
		<p> <strong> <spring:message code="actor.curriculum" />: </strong>  
		<a href="curriculum/display.do?handyWorkerId=${actor.id}"><spring:message code="actor.curriculum"/></a>
	</p>
	</jstl:if>
</fieldset>

<input type="button" name="return" value="<spring:message code="actor.return" />" 
				onclick="javascript: relativeRedir('welcome/index.do');" />