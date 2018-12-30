<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="images/logo.png" alt="Acme-HandyWorker Co., Inc."  width="500" height="200"/></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>

					<li><a href="actor/administrator/list.do"><spring:message code="master.page.administrator.list" /></a></li>
					<li><a href="message/administrator/broadcast.do"><spring:message code="master.page.administrator.broadcast" /></a></li>					
					<li><a href="customisation/administrator/display.do"> <spring:message code="master.page.customisation.customisation" /> </a></li>
					<li><a href="category/administrator/list.do"> <spring:message code="master.page.administrator.category" /> </a></li>
					<li><a href="endorsable/administrator/list.do"> <spring:message code="master.page.administrator.endorsable" /> </a></li>
					<li><a href="dashboard/administrator/display.do"> <spring:message code="master.page.administrator.dashboard" /> </a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/action-1.do"><spring:message code="master.page.customer.action.1" /></a></li>
					<li><a href="customer/action-2.do"><spring:message code="master.page.customer.action.2" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('HANDYWORKER')">
			<li><a class="fNiv"><spring:message	code="master.page.application" /></a>			
				
				<ul>
					<li class="arrow"></li>
					<li><a href="application/handyWorker/list.do"><spring:message code="master.page.handyWorker.application.list" /></a></li>					
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.tutorial" /></a>				
				<ul>
					<li class="arrow"></li>
					<li><a href="tutorial/handyWorker/list.do"><spring:message code="master.page.handyWorker.tutorial.list" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasAnyRole('HANDYWORKER','CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.endorsable" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="endorsement/customer,handyWorker/list.do"><spring:message code="master.page.endorsable.endorsement" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('REFEREE')">
			<li><a class="fNiv"><spring:message	code="master.page.complaint" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/referee/listSelfAssigned.do"><spring:message code="master.page.complaint.self.assigned" /></a></li>
					<li><a href="complaint/referee/listNotAssigned.do"><spring:message code="master.page.complaint.not.assigned" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('HANDYWORKER')">
			<li><a class="fNiv"><spring:message	code="master.page.complaint" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/handyWorker/list.do"><spring:message code="master.page.complaint.involved" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message	code="master.page.sponsor.sponsorship" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsorship/sponsor/list.do"><spring:message code="master.page.sponsor.sponsorship.list" /></a></li>
				</ul>
			</li>
		</security:authorize>

	<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			
		</security:authorize>
			<li><a class="fNiv"><spring:message	code="master.page.tutorial" /></a>			
				<ul>
					<li class="arrow"></li>
					<li><a href="tutorial/list.do"><spring:message code="master.page.tutorial.list" /></a></li>					
				</ul>
			</li>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/administrator,customer,handyWorker,referee,sponsor/display.do"><spring:message code="master.page.actor.display" /></a></li>
					<li><a href="box/administrator,customer,handyWorker,referee,sponsor/list.do"><spring:message code="master.page.box.list" /></a></li>
					<li><a href="profile/action-3.do"><spring:message code="master.page.profile.action.3" /></a></li>					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

