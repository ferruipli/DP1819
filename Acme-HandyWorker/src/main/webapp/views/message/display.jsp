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


	<p> <strong> <spring:message code="message.display.sendMoment" />: </strong> </p> <jstl:out value="${message.sendMoment}" />
	<br />
	
	<p> <strong> <spring:message code="message.display.subject" />: </strong> </p> <jstl:out value="${message.subject}" />
	<br />

	<p> <strong> <spring:message code="message.display.body" />: </strong> </p> <jstl:out value="${message.body}" />
	<br />

	<p> <strong> <spring:message code="message.display.priority" />: </strong> </p> <jstl:out value="${message.priority}" />
	<br />
	
	<p> <strong> <spring:message code="message.display.tags" />: </strong> </p> <jstl:out value="${message.tags}" />
	<br />
	
	<p> <strong> <spring:message code="message.display.sender" />: </strong> </p> <jstl:out value="${message.sender.name}" />
	<br />

<fieldset>
	<legend><spring:message code="message.display.recipients"/></legend>
	
	<display:table>
		
		<display:column property="name" titleKey="message.recipient.name"/>
		
		<display:column property="surname" titleKey="message.recipient.surname"/>
	
		<display:column property="email" titleKey="message.recipient.email"/>
	
	</display:table>
</fieldset>


<input type="button" name="return" value="<spring:message code="message.button.return" />" 
				onclick="javascript: relativeRedir('box/${role}/display.do?boxId=${boxId}');" />
				
<input type="submit" name="delete" value="<spring:message code="message.button.delete"/>" 
				onclick="return confirm('<spring:message code="message.confirm.delete" />')"/>
				
<input type="submit" name="move" value="<spring:message code="message.button.move"/>" />