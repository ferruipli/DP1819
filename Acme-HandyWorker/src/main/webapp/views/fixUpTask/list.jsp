<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jsp:useBean id="now" class="java.util.Date" />

<display:table name="fixUpTasks" id="row" requestURI="${requestURI}" class="displaytag">
	
	
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<jstl:if test="${empty row.applications}">
				<a href="fixUpTask/customer/edit.do?fixUpTaskId=${row.id}"><spring:message code="fixUpTask.edit"/></a>
			</jstl:if>
		</display:column>
	</security:authorize>
	
	<display:column>
		<a href="fixUpTask/customer,handyWorker,referee/display.do?fixUpTaskId=${row.id}"><spring:message code="fixUpTask.display"/></a>
	</display:column>
	
	<display:column property="ticker" titleKey="fixUpTask.ticker"/>
	
	<spring:message code="fixUpTask.date.format" var="tableDateFormat"/>
	<display:column property="publicationMoment" titleKey="fixUpTask.publicationMoment" sortable="true"  format="${tableDateFormat}"/>
	
	<display:column property="description" titleKey="fixUpTask.description"/>
	
	
</display:table>

<security:authorize access="hasRole('CUSTOMER')">
	<a href="fixUpTask/customer/create.do"><spring:message code="fixUpTask.create"/></a>
</security:authorize>

<security:authorize access="hasRole('HANDYWORKER')">
<!-- SI HA PASADO EL TIEMPO DE LA CACHE DOY LA OPCIÓN DE VER LA LISTA YA GUARDADA O DE EDITAR EL FINDER -->
	<jstl:if test="${lastUpdateFinder}">
		<a href="finder/handyWorker/edit.do"><spring:message code="fixUpTask.edit.finder"/></a>
	</jstl:if>
	
<!-- SI NO HA PASADO EL TIEMPO DE LA CACHE EDITO EL FINDER-->
	<jstl:if test="${!lastUpdateFinder}">
	
		<a href="fixUpTask/handyWorker/listFinder.do"><spring:message code="fixUpTask.back.finder"/></a>
		<a href="finder/handyWorker/edit.do"><spring:message code="fixUpTask.edit.finder"/></a>
	</jstl:if>
</security:authorize>