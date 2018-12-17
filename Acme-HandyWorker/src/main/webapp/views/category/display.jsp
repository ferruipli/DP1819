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
	<strong> <spring:message code="categoryTranslation.name" />: </strong>
	<jstl:out value="${categoryTranslation.name}" />
</p>

<p>
	<strong> <spring:message code="category.parent" />: </strong>
	<a href="category/administrator/display.do?categoryId=${category.parent.id}">
		<jstl:out value="${category.parent.name}" />
	</a>
</p>

<jstl:if test="${not empty descendantMap}">
	<strong> <spring:message code="category.descendant" />: </strong>
	<jstl:forEach items="${descendantMap.keys()}" var="row">
		<a href="category/administrator/display.do?categoryId=${row}">
			<jstl:out value="${descendantMap.get(row)}" />
		</a>
	</jstl:forEach>
</jstl:if>
<br />

<input type="button" 
	   name="return"
	   value="<spring:message code="category.return" />"
	   onclick="javascript: relativeRedir('category/administrator/list.do');" />
	   
	   