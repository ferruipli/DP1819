<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jsp:useBean id="utilityService" class="services.UtilityService"/>

<spring:message code="formatDate" var="formatDate" />

<p>
	<strong><spring:message code="curriculum.ticker" /></strong> :
	<jstl:out value="${curriculum.ticker}"></jstl:out>
</p>

<fieldset>
	<p>
		<strong><spring:message
				code="curriculum.personalRecord.fullName" /></strong> :
		<jstl:out value="${curriculum.personalRecord.fullName}"></jstl:out>
	</p>
	<p>
		<strong><spring:message
				code="curriculum.personalRecord.photoLink" /></strong> : <img
			src="${curriculum.personalRecord.photoLink}">
	</p>
	<p>
		<strong><spring:message
				code="curriculum.personalRecord.email" /></strong> :
		<jstl:out value="${curriculum.personalRecord.email}"></jstl:out>
	</p>
	<p>
		<strong><spring:message
				code="curriculum.personalRecord.phoneNumber" /></strong> :
		<jstl:out value="${curriculum.personalRecord.phoneNumber}"></jstl:out>
	</p>
	<p>
		<strong><spring:message
				code="curriculum.personalRecord.linkedInProfile" /></strong> :
		<jstl:out value="${curriculum.personalRecord.linkedInProfile}"></jstl:out>
	</p>
	<security:authorize access="hasRole('HANDYWORKER')">
		<jstl:if test="${handyWorkerLoginId == handyWorkerCurriculumId}">
			<a
				href="personalRecord/handyWorker/edit.do?personalRecordId=${curriculum.personalRecord.id}">
				<spring:message code="curriculum.edit" />
			</a>
		</jstl:if>
	</security:authorize>
</fieldset>

<fieldset>
	<display:table name="curriculum.educationRecords" id="rowEducationRecord" pagesize="5"	class="displaytag" requestURI="${requestURI}">

		<security:authorize access="hasRole('HANDYWORKER')">
			<jstl:if test="${handyWorkerLoginId == handyWorkerCurriculumId}">
				<display:column>
					<a
						href="educationRecord/handyWorker/edit.do?educationRecordId=${rowEducationRecord.id}">
						<spring:message code="curriculum.edit" />
					</a>
				</display:column>
			</jstl:if>
		</security:authorize>
		<spring:message code="curriculum.educationRecord.titleDiploma"
			var="titleDiplomaHeader" />
		<display:column property="titleDiploma" title="${titleDiplomaHeader}"
			sortable="false" />

		<spring:message code="curriculum.educationRecord.startDate"
			var="startDateHeader" />
		<display:column property="startDate" title="${startDateHeader}"
			sortable="false" format="${formatDate}" />

		<spring:message code="curriculum.educationRecord.endDate"
			var="endDateHeader" />
		<display:column property="endDate" title="${endDateHeader}"
			sortable="false" format="${formatDate}" />

		<spring:message code="curriculum.educationRecord.institution"
			var="institutionHeader" />
		<display:column property="institution" title="${institutionHeader}"
			sortable="false" />

		<spring:message code="curriculum.educationRecord.attachment"
			var="attachmentHeader" />
		<display:column title="${attachmentHeader}" sortable="false">
			<ul>
				<jstl:forEach var="attachmentRow" items="${utilityService.getSplittedString(rowEducationRecord.attachment)}">
					<li> <a href="${attachmentRow}"><jstl:out value="${attachmentRow}"/></a> </li>
				</jstl:forEach>
			</ul>
		</display:column>

		<spring:message code="curriculum.educationRecord.comments"
			var="commentsHeader" />
		<display:column property="comments" title="${commentsHeader}"
			sortable="false" />
	</display:table>

	<security:authorize access="hasRole('HANDYWORKER')">
		<jstl:if test="${handyWorkerLoginId == handyWorkerCurriculumId}">
			<a href="educationRecord/handyWorker/create.do"> <spring:message
					code="curriculum.educationRecord.create" />
			</a>
		</jstl:if>
	</security:authorize>
</fieldset>

<fieldset>
	<display:table name="curriculum.professionalRecords" id="rowProfessionalRecord" pagesize="5" class="displaytag" requestURI="${requestURI}">

		<security:authorize access="hasRole('HANDYWORKER')">
			<jstl:if test="${handyWorkerLoginId == handyWorkerCurriculumId}">
				<display:column>
					<a
						href="professionalRecord/handyWorker/edit.do?professionalRecordId=${rowProfessionalRecord.id}">
						<spring:message code="curriculum.edit" />
					</a>
				</display:column>
			</jstl:if>
		</security:authorize>
		<spring:message code="curriculum.professionalRecord.companyName"
			var="companyNameHeader" />
		<display:column property="nameCompany" title="${companyNameHeader}"
			sortable="false" />

		<spring:message code="curriculum.professionalRecord.startDate"
			var="startDateHeader" />
		<display:column property="startDate" title="${startDateHeader}"
			sortable="false" format="${formatDate}" />

		<spring:message code="curriculum.professionalRecord.endDate"
			var="endDateHeader" />
		<display:column property="endDate" title="${endDateHeader}"
			sortable="false" format="${formatDate}" />

		<spring:message code="curriculum.professionalRecord.role"
			var="roleHeader" />
		<display:column property="role" title="${roleHeader}" sortable="false" />

		<spring:message code="curriculum.professionalRecord.attachment"
			var="attachmentHeader" />
		<display:column title="${attachmentHeader}" sortable="false">
			<ul>
				<jstl:forEach var="attachmentRow" items="${utilityService.getSplittedString(rowProfessionalRecord.attachment)}">
					<li> <a href="${attachmentRow}"><jstl:out value="${attachmentRow}"/></a> </li>
				</jstl:forEach>
			</ul>
		</display:column>

		<spring:message code="curriculum.professionalRecord.comments"
			var="commentsHeader" />
		<display:column property="comments" title="${commentsHeader}"
			sortable="false" />
	</display:table>

	<security:authorize access="hasRole('HANDYWORKER')">
		<jstl:if test="${handyWorkerLoginId == handyWorkerCurriculumId}">
			<a href="professionalRecord/handyWorker/create.do"> <spring:message
					code="curriculum.professionalRecord.create" />
			</a>
		</jstl:if>
	</security:authorize>
</fieldset>

<fieldset>
	<display:table name="curriculum.endorserRecords" id="rowEndorserRecord" pagesize="5" class="displaytag" requestURI="${requestURI}">

		<security:authorize access="hasRole('HANDYWORKER')">
			<jstl:if test="${handyWorkerLoginId == handyWorkerCurriculumId}">
				<display:column>
					<a
						href="endorserRecord/handyWorker/edit.do?endorserRecordId=${rowEndorserRecord.id}">
						<spring:message code="curriculum.edit" />
					</a>
				</display:column>
			</jstl:if>
		</security:authorize>
		<spring:message code="curriculum.endorserRecord.fullName"
			var="nameHeader" />
		<display:column property="fullName" title="${nameHeader}"
			sortable="false" />

		<spring:message code="curriculum.endorserRecord.email"
			var="emailHeader" />
		<display:column property="email" title="${emailHeader}"
			sortable="false" />

		<spring:message code="curriculum.endorserRecord.phoneNumber"
			var="telephoneNumberHeader" />
		<display:column property="phoneNumber"
			title="${telephoneNumberHeader}" sortable="false" />


		<a><spring:message
				code="curriculum.endorserRecord.linkedInProfile"
				var="linkedInProfileHeader" /> </a>
		<display:column title="${linkedInProfileHeader}" sortable="false">
			<a href="${rowEndorserRecord.linkedInProfile}"><jstl:out value="${rowEndorserRecord.linkedInProfile}"/></a>
		</display:column>


		<spring:message code="curriculum.endorserRecord.comments"
			var="commentsHeader" />
		<display:column property="comments" title="${commentsHeader}"
			sortable="false" />
	</display:table>
	<security:authorize access="hasRole('HANDYWORKER')">
		<jstl:if test="${handyWorkerLoginId == handyWorkerCurriculumId}">
			<a href="endorserRecord/handyWorker/create.do"> <spring:message
					code="curriculum.endorserRecord.create" />
			</a>
		</jstl:if>
	</security:authorize>


</fieldset>

<fieldset>
	<display:table name="curriculum.miscellaneousRecords" id="rowMiscellaneousRecord" pagesize="5"	class="displaytag" requestURI="${requestURI}">
		<security:authorize access="hasRole('HANDYWORKER')">
			<jstl:if test="${handyWorkerLoginId == handyWorkerCurriculumId}">
				<display:column>
					<a
						href="miscellaneousRecord/handyWorker/edit.do?miscellaneousRecordId=${rowMiscellaneousRecord.id}">
						<spring:message code="curriculum.edit" />
					</a>
				</display:column>
			</jstl:if>
		</security:authorize>
		<spring:message code="curriculum.miscellaneousRecord.title"
			var="titleHeader" />
		<display:column property="title" title="${titleHeader}"
			sortable="false" />

		<spring:message code="curriculum.miscellaneousRecord.attachment"
			var="attachmentHeader" />
		<display:column title="${attachmentHeader}" sortable="false">
			<ul>
				<jstl:forEach var="attachmentRow" items="${utilityService.getSplittedString(rowMiscellaneousRecord.attachment)}">
					<li> <a href="${attachmentRow}"><jstl:out value="${attachmentRow}"/></a> </li>
				</jstl:forEach>
			</ul>
		</display:column>

		<spring:message code="curriculum.miscellaneousRecord.comments"
			var="commentsHeader" />
		<display:column property="comments" title="${commentsHeader}"
			sortable="false" />
	</display:table>

	<security:authorize access="hasRole('HANDYWORKER')">
		<jstl:if test="${handyWorkerLoginId == handyWorkerCurriculumId}">
			<a href="miscellaneousRecord/handyWorker/create.do"> <spring:message
					code="curriculum.miscellaneousRecord.create" />
			</a>
		</jstl:if>
	</security:authorize>

</fieldset>