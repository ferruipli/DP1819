<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<h3> <spring:message code="endorsement.sent" /> </h3>
<display:table name="sentEndorsements" id="row" requestURI="${requestURI}" class="displaytag">
	<display:column>
		<a href="endorsement/customer,handyWorker/display.do?endorsementId=${row.id}">
			<spring:message code="endorsement.display" />
		</a>
	</display:column>
	
	<display:column>
		<a href="endorsement/customer,handyWorker/edit.do?endorsementId=${row.id}">
			<spring:message code="endorsement.edit" />
		</a>
	</display:column>
	
	<display:column property="comments" titleKey="endorsement.comments" />
	
	<spring:message code="endorsement.formatMoment" var="w_format"  />
	<display:column property="moment" titleKey="endorsement.moment" sortable="true" format="${w_format}" />
	
	<display:column property="recipient.fullname" titleKey="endorsement.recipient" />
</display:table>
<br />
<a href="endorsement/customer,handyWorker/create.do">
	<spring:message code="endorsement.create" />
</a>

<h3> <spring:message code="endorsement.received" /> </h3>
<display:table name="receivedEndorsements" id="row" requestURI="${requestURI}" class="displaytag">
	<display:column>
		<a href="endorsement/customer,handyWorker/display.do?endorsementId=${row.id}">
			<spring:message code="endorsement.display" />
		</a>
	</display:column>
	
	<display:column property="comments" titleKey="endorsement.comments" />
	
	<spring:message code="endorsement.formatMoment" var="w_format"  />
	<display:column property="moment" titleKey="endorsement.moment" sortable="true" format="${w_format}" />
	
	<display:column property="sender.fullname" titleKey="endorsement.sender" />
</display:table>