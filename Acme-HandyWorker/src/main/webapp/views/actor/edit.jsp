<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="actor/${role}/edit.do" modelAttribute="actor">
	<jstl:choose>
		<jstl:when test="${role == 'customer'}">
			<h2><spring:message code="header.customer"/></h2>
		
			<form:hidden path="fixUpTasks"/>
		</jstl:when>
		<jstl:when test="${role == 'handyworker'}">
			<h2><spring:message code="header.handyworker"/></h2>
		
			<form:hidden path="finder"/>
			<form:hidden path="applications"/>
		</jstl:when>
		<jstl:when test="${role == 'sponsor'}">
			<h2><spring:message code="header.sponsor"/></h2>
		
			<form:hidden path="sponsorships"/>
		</jstl:when>
		<jstl:when test="${role == 'administrator'}">
			<h2><spring:message code="header.administrator"/></h2>
		</jstl:when>
		<jstl:when test="${role == 'referee'}">
			<h2><spring:message code="header.referee"/></h2>
		
			<form:hidden path="complaints"/>
		</jstl:when>
	</jstl:choose>
		
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="isSuspicious"/>
	<form:hidden path="userAccount"/>
	
	<fieldset>
		<legend><spring:message code="actor.legend"/></legend>
	
		<form:label path="name">
			<spring:message code="actor.name" />
		</form:label>
		<form:input path="name"/>
		<form:errors cssClass="error" path="name" />
		<br />
		
		<form:label path="middleName">
			<spring:message code="actor.middleName" />
		</form:label>
		<form:input path="middleName"/>
		<form:errors cssClass="error" path="middleName" />
		<br />
		
		<form:label path="surname">
			<spring:message code="actor.surname" />
		</form:label>
		<form:input path="surname"/>
		<form:errors cssClass="error" path="surname" />
		<br />
		
		<form:label path="photoLink">
			<spring:message code="actor.photoLink" />
		</form:label>
		<form:input path="photoLink"/>
		<form:errors cssClass="error" path="photoLink" />
		<br />
		
		<form:label path="email">
			<spring:message code="actor.email" />
		</form:label>
		<form:input path="email" placeholder="email@email.com"/>
		<form:errors cssClass="error" path="email" />
		<br />
		
		<form:label path="phoneNumber">
			<spring:message code="actor.phoneNumber" />
		</form:label>
		<form:input path="phoneNumber"/>
		<form:errors cssClass="error" path="phoneNumber" />
		<br />
		
		<form:label path="address">
			<spring:message code="actor.address" />
		</form:label>
		<form:input path="address"/>
		<form:errors cssClass="error" path="address" />
		<br /> 
	</fieldset>
	
	<fieldset>
		<legend><spring:message code="userAccount.legend"/></legend>
	
		<label for="newusernameId">
			<spring:message code="userAccount.newUsername" />
		</label>
		<input type="text" name="newUsername" id="newusernameId" value="${userAccount.username}"/>
		<br />
		
		<label for="newPasswordId">
			<spring:message code="userAccount.newPassword" />
		</label>
		<input type="password" name="newPassword" id="newPasswordId"/>
		<br />
		
		<label for="confirmPasswordId">
			<spring:message code="userAccount.confirmPassword" />
		</label>
		<input type="password" name="confirmPassword" id="confirmPasswordId"/>
		<br />
		
		<input type="hidden" name="role" value="${role}"/>
	</fieldset>
	
	<input type="submit" name="save" value="<spring:message code="actor.save" />" />
	<input type="button" name="cancel" value="<spring:message code="actor.cancel" />"
		onclick="javascript: relativeRedir('actor/${role}/display.do?actorId=${actor.id }')" />
	
	<hr>
	
</form:form>