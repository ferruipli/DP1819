<%--
 * action-1.jsp
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


<form:form action="sponsorship/sponsor/edit.do" modelAttribute="sponsorship">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="banner">
		<spring:message code="sponsorship.banner" />:
	</form:label>
	<form:input path="banner" />
	<form:errors cssClass="error" path="banner" />
	<br />
	
	<form:label path="targetPage">
		<spring:message code="sponsorship.targetPage" />:
	</form:label>
	<form:input path="targetPage"/>
	<form:errors cssClass="error" path="targetPage" />
	<br />

	 <fieldset>
	<h2><strong> <spring:message code="sponsorship.creditCard" /> </strong></h2>

 		<form:label path="creditCard.holderName">
			<spring:message code="sponsorship.creditCard.holderName" />:
		</form:label>
		<form:input path="creditCard.holderName" />
		<form:errors cssClass="error" path="creditCard.holderName" />
	<br /><br/>
	
<%-- 		<form:label id = "brandName" path="brandName">
		<spring:message code="application.creditCard.brandName" />:
		</form:label>
			<form:select path="brandName">
				<form:options items="${brandName}" itemLabel="brandName" itemValue="id" />
			</form:select>
 --%>
 
 <form:label path="creditCard.brandName">
			<spring:message code="sponsorship.creditCard.brandName" />:
		</form:label>
		<form:input path="creditCard.brandName" />
		<form:errors cssClass="error" path="creditCard.brandName" />
	<br /><br/>

		<form:label path="creditCard.number">
			<spring:message code="sponsorship.creditCard.number" />:
		</form:label>
		<form:input path="creditCard.number" />
		<form:errors cssClass="error" path="creditCard.number" />
	<br /><br/>
	
		<form:label path="creditCard.expirationMonth">
			<spring:message code="sponsorship.creditCard.expirationMonth" />:
		</form:label>
		<form:input path="creditCard.expirationMonth" />
		<form:errors cssClass="error" path="creditCard.expirationMonth" />
	<br /><br/>
		<form:label path="creditCard.expirationYear">
			<spring:message code="sponsorship.creditCard.expirationYear" />:
		</form:label>
	
		<form:input path="creditCard.expirationYear" />
		<form:errors cssClass="error" path="creditCard.expirationYear" />
	<br /><br/>
		<form:label path="creditCard.cvvCode">
			<spring:message code="sponsorship.creditCard.cvvCode" />:
		</form:label>
			
		<form:input path="creditCard.cvvCode" />
		<form:errors cssClass="error" path="creditCard.cvvCode" /> 
 	</fieldset>
 	
	
	<input type="submit" name="save" value="<spring:message code="sponsorship.save" />" />
	<input type="button" name="cancel"	value="<spring:message code="sponsorship.cancel" />"onclick="javascript: relativeRedir('tutorial/list.do');" />
	<br />

</form:form>