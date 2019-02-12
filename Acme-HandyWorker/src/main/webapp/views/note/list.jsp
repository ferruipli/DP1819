<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="notes" id="row" requestURI="note/customer,handyWorker,referee/list.do" class="displaytag">
	<display:column>
		<a href="note/customer,handyWorker,referee/display.do?noteId=${row.id}"><spring:message code="note.display"/></a>
	</display:column>
	
	<spring:message code="note.date.format" var="tableDateFormat"/>
	<display:column property="moment" titleKey="note.moment" sortable="true" format="${tableDateFormat}"/>
</display:table>

<a href="report/customer,handyWorker,referee/display.do?reportId=${reportId}"><spring:message code="note.back"/></a>
&nbsp;
<a href="note/customer,handyWorker,referee/create.do?reportId=${reportId}"><spring:message code="note.create"/></a>