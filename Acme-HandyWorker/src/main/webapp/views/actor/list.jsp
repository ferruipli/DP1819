<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="actors" id="row" requestURI="${requestUri }" pagesize="5" class="displaytag">
	<display:column>
		<jstl:choose>
			<jstl:when test="${row.userAccount.isBanned}">
				<a href="actor/administrator/unban.do?actorId=${row.id}"><spring:message code="suspicious.table.unban"/></a>
			</jstl:when>
			<jstl:otherwise>
				<a href="actor/administrator/ban.do?actorId=${row.id}"><spring:message code="suspicious.table.ban"/></a>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<spring:message code="suspicious.table.name" var="name"/>
	<display:column property="name" title="${name}" sortable="true"/>
	
	<spring:message code="suspicious.table.middlename" var="middleName"/>
	<display:column property="name" title="${middleName }" sortable="true"/>
	
	<spring:message code="suspicious.table.surname" var="surname"/>
	<display:column property="surname" title="${surname}" sortable="true"/>
	
	<spring:message code="suspicious.table.email" var="email"/>
	<display:column property="email" title="${email}" sortable="true"/>
	
	<spring:message code="suspicious.table.username" var="username"/>
	<display:column property="username" title="${username}" sortable="true"/>
	
	<spring:message code="suspicious.table.authority" var="authority"/>
	<display:column property="authority" title="${authority}" sortable="true"/>
</display:table>