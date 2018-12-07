<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<fieldset>
	<legend><spring:message code="message.fieldset.message" /></legend>
	
	<form action="message/${role}/move.do" method="post">
		<input type="hidden" name="messageId" value="${message.id}">
		<input type="hidden" name="sourceBoxId" value="${sourceBox.id}">
	
		<ul>
			<li>
				<spring:message code="message.move.subject"/>${message.subject}
			</li>
			
			<li>
				<spring:message code="message.move.sourceBox"/>${sourceBox.name}
			</li>
		
			<li>
				<label for="targetBoxSelectId">
					<spring:message code="message.move.targetBox" />
				</label>
				<select name="targetBoxId" id="targetBoxSelectId" form="formId">
					<jstl:if test="${not empty boxes}">
						<jstl:forEach var="rowBox" items="${boxes}">
							<option value="${rowBox.id}">${rowBox.name}</option>
						</jstl:forEach>
					</jstl:if>
				</select>
			</li>
		</ul>
		
		<input type="submit" name="move" value="<spring:message code="message.button.move"/>" />
		<input type="button" name="cancel" value="<spring:message code="message.button.cancel"/>" 
				onclick="javascript: relativeRedir('message/${role }/display.do?messageId=${message.id}')" />
	</form>
</fieldset>