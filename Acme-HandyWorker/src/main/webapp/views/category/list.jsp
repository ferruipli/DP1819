<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="categories" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column>
		<a href="category/administrator/display.do?categoryId=${row.id}">
			<spring:message code="category.display" />
		</a>
	</display:column>

	<display:column>
		<a href="category/administrator/edit.do?categoryId=${row.id}">
			<spring:message code="category.edit" />
		</a>
	</display:column>
	
	<display:column value="${mapa.get(row.id)}" titleKey="categoryTranslation.name" sortable="true" />		
	<display:column value="${mapa.get(row.parent.id)}" titleKey="category.parent" />
</display:table>

<a href="category/administrator/create.do">
	<spring:message code="category.create" />
</a>

