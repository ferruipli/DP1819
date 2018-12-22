<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="socialProfiles" id="row" requestURI="${requestURI }" pagesize="5" class="displaytag">
	
	<display:column property="nick" titleKey="socialProfile.nick"/>
	
	<display:column property="socialNetworkName" titleKey="socialProfile.socialNetworkName"/>
	
	<display:column property="profileLink" titleKey="socialProfile.profileLink"/>
	
</display:table>

	
	<a href="socialProfile/authenticated/create.do"><spring:message code="socialProfile.new"/></a>
	
	<br><br>
		
<input type="button" name="return" value="<spring:message code="socialProfile.return" />" 
				onclick="javascript: relativeRedir('actor/authenticated/display.do?actorId=${actorId }');" />