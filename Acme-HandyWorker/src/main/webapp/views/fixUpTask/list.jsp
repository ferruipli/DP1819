<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="fixUpTasks" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<jstl:if test="${empty row.applications}">
				<a href="fixUpTask/customer/edit.do?fixUpTaskId=${row.id}"><spring:message code="fixUpTask.edit"/></a>
			</jstl:if>
		</display:column>
	</security:authorize>
	
	<display:column>
		<a href="fixUpTask/referee,customer,handyWorker/display.do?fixUpTaskId=${row.id}"><spring:message code="fixUpTask.display"/></a>
	</display:column>
	
	<spring:message code="fixUpTask.ticker" var="tableTicker"/>
	<display:column property="ticker" title="${tableTicker}"/>
	
	<spring:message code="fixUpTask.publicationMoment" var="tablePublicationMoment"/>
	<spring:message code="fixUpTask.date.format" var="tableDateFormat"/>
	<display:column property="publicationMoment" title="${tablePublicationMoment}" sortable="true"  format="${tableDateFormat}"/>
	
	<spring:message code="fixUpTask.description" var="tableDescription"/>
	<display:column property="description" title="${tableDescription}"/>
	
</display:table>

<security:authorize access="hasRole('CUSTOMER')">
	<a href="fixUpTask/customer/create.do"><spring:message code="fixUpTask.create"/></a>
</security:authorize>

<security:authorize access="hasRole('HANDYWORKER')">
	<input type="button" name="back_finder"	value="<spring:message code="fixUpTask.back.finder"/>" onclick="javascript: relativeRedir('finder/handyWorker/search.do');"/>
</security:authorize>