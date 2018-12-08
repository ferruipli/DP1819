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
	
	<display:column property="name" titleKey="suspicious.table.name"/>
	
	<display:column property="middleName" titleKey="suspicious.table.middleName"/>
	
	<display:column property="surname" titleKey="suspicious.table.surname"/>
	
	<display:column property="email" titleKey="suspicious.table.email"/>
	
	<display:column property="username" titleKey="suspicious.table.username"/>
	
	<display:column property="authority" titleKey="suspicious.table.authority"/>
</display:table>