<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="sentEndorsements" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column>
		<a href="endorsement/handyWorker,customer/display.do?endorsementId=${row.id}">
			<spring:message code="endorsement.display" />
		</a>
	</display:column>
	
	<display:column>
		<a href="endorsement/handyWorker,customer/edit.do?endorsementId=${row.id}">
			<spring:message code="endorsement.edit" />
		</a>
	</display:column>
	
	<spring:message code="endorsement.comments" var="commentsHeader" />
	<display:column property="comments" title="${commentsHeader}" sortable="true" />
	
	<spring:message code="endorsement.formatMoment" var="w_format"  />
	<spring:message code="endorsement.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="true" format="${w_format}" />
	
	<spring:message code="endorsement.sender" var="senderHeader" />
	<display:column property="sender.name" title="${senderHeader}" sortable="true" />
	
	<spring:message code="endorsement.recipient" var="recipientHeader" />
	<display:column property="recipient.name" title="${recipientHeader}" sortable="true" />
</display:table>

<a href="endorsement/handyWorker,customer/create.do">
	<spring:message code="endorsement.create" />
</a>

<display:table name="receivedEndorsements" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column>
		<a href="endorsement/handyWorker,customer/display.do?endorsementId=${row.id}">
			<spring:message code="endorsement.display" />
		</a>
	</display:column>
	
	<spring:message code="endorsement.comments" var="commentsHeader" />
	<display:column property="comments" title="${commentsHeader}" sortable="true" />
	
	<spring:message code="endorsement.formatMoment" var="w_format"  />
	<spring:message code="endorsement.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="true" format="${w_format}" />
	
	<spring:message code="endorsement.sender" var="senderHeader" />
	<display:column property="sender.name" title="${senderHeader}" sortable="true" />
	
	<spring:message code="endorsement.recipient" var="recipientHeader" />
	<display:column property="recipient.name" title="${recipientHeader}" sortable="true" />
</display:table>