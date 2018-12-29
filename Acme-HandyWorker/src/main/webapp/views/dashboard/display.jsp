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
<p>
	<strong> <spring:message code="dashboard.thirteen" /> </strong>:
	<jstl:out value="${ratTaskWithComplaints}" />
</p>

<p> <strong> <spring:message code="dashboard.nine" />: </strong> </p>
<display:table name="customers" id="row" requestURI="dashboard/administrator/listCustomers" pagesize="5" class="displaytag">
	<display:column property="fullname" titleKey="actor.fullname" />
</display:table>
<p> <strong> <spring:message code="dashboard.ten" />: </strong> </p>
<display:table name="handyWorkers" id="row" requestURI="dashboard/administrator/listHandyWorkers" pagesize="5" class="displaytag">
	<display:column property="fullname" titleKey="actor.fullname" />
</display:table>

<p> <strong> <spring:message code="dashboard.fourteen" />: </strong> </p>
<display:table name="topThreeC" id="row" requestURI="dashboard/administrator/listCustomers" pagesize="5" class="displaytag">
	<display:column property="fullname" titleKey="actor.fullname" />
</display:table>
<p> <strong> <spring:message code="dashboard.fifteen" />: </strong> </p>
<display:table name="topThreeHW" id="row" requestURI="dashboard/administrator/listHandyWorkers" pagesize="5" class="displaytag">
	<display:column property="fullname" titleKey="actor.fullname" />
</display:table>