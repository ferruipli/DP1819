<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<strong> <spring:message code="dashboard.one" /> </strong>:
</p>
<display:table name="dataFixUpTaskPerUser" id="row" class="displaytag">
	<display:column value="${row[0]}" titleKey="dashboard.average" />
	<display:column value="${row[1]}" titleKey="dashboard.min" />
	<display:column value="${row[2]}" titleKey="dashboard.max" />
	<display:column value="${row[3]}" titleKey="dashboard.deviation" />
</display:table>

<p>
	<strong> <spring:message code="dashboard.five" /> </strong>:
	<jstl:out value="${ratPendingApp}" />
</p>
<p>
	<strong> <spring:message code="dashboard.six" /> </strong>:
	<jstl:out value="${ratAcceptedApp}" />
</p>
<p>
	<strong> <spring:message code="dashboard.seven" /> </strong>:
	<jstl:out value="${ratRejectedApp}" />
</p>
<p>
	<strong> <spring:message code="dashboard.eight" /> </strong>:
	<jstl:out value="${ratPendingPeriodApp}" />
</p>
<p> <strong> <spring:message code="dashboard.nine" />: </strong> </p>
<display:table name="customers" id="row" requestURI="dashboard/administrator/listCustomers" pagesize="5" class="displaytag">
	<display:column property="fullname" titleKey="actor.fullname" />
</display:table>
<br />
<p> <spring:message code="dashboard.ten" /> </p>
<display:table name="handyWorkers" id="row" requestURI="dashboard/administrator/listHandyWorkers" pagesize="5" class="displaytag">
	<display:column property="fullname" titleKey="actor.fullname" />
</display:table>