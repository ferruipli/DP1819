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
	<legend><spring:message code="message.fieldset.messageToMove" /></legend>
	
	<form action="message/${role}/move.do" method="post">
		<input type="hidden" name="messageToMoveId" value="${message.id}">
		<input type="hidden" name="sourceFolderId" value="${sourceBox.id}">
	
		<ul>
			<li>
				<spring:message code="message.move.subject"/>${messageToMove.subject}
			</li>
			
			<li>
				<spring:message code="message.move.sourceFolder"/>${sourceFolder.name}
			</li>
		
			<li>
				<label for="targetFolderSelectId">
					<spring:message code="message.move.targetFolder" />
				</label>
				<select name="targetFolderId" id="targetFolderSelectId" form="formId">
					<jstl:if test="${not empty allFolders}">
						<jstl:forEach var="rowFolder" items="${allFolders}">
							<option value="${rowFolder.id}">${rowFolder.name}</option>
						</jstl:forEach>
					</jstl:if>
				</select>
			</li>
		</ul>
		
		<input type="submit" name="move" value="<spring:message code="message.button.move"/>" />
		<input type="button" name="cancel" value="<spring:message code="message.button.cancel"/>" 
				onclick="javascript: relativeRedir('folder/display.do?folderId=${sourceFolder.id}')" />
	</form>
</fieldset>