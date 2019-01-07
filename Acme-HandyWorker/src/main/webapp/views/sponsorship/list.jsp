<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<display:table id="row" name="sponsorships"  requestURI="${requestURI}"  pagesize="5"  class="displaytag">
	<display:column>
		<a href="sponsorship/sponsor/display.do?sponsorshipId=${row.id}">
			<spring:message	code="sponsorship.display" />			
		</a>
	</display:column>
	
	<display:column property="banner" titleKey="sponsorship.banner" sortable="true" />

	<display:column property="targetPage" titleKey="sponsorship.targetPage" />		
</display:table>
