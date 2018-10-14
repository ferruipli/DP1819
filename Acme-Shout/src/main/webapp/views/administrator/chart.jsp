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

<p><spring:message code="administrator.action.2" /></p>

<jstl:set var="as" value="${statistics.get('count.all.shouts')}" />
<jstl:set var="ss" value="${statistics.get('count.short.shouts')}" />
<jstl:set var="ls" value="${statistics.get('count.long.shouts')}" />

<div class="container">
	<canvas id="myChart" style="width:75%; height:50%;"></canvas>
</div>

<script>	
	let myChart = document.getElementById("myChart").getContext("2d");
	
	let allS = <jstl:out value="${as}" />;
	let shortS = <jstl:out value="${ss}" />;;
	let longS = <jstl:out value="${ls}" />;;
	
	let massPopChart = new Chart(myChart, {
		type: 'bar',
		data: {
			labels: ["All shouts", "Short shouts", "Long shouts"],
			datasets:[{
				label: 'Total Shouts',
				data: [allS, shortS, longS],
				backgroundColor: [
				     'green',
				     'blue',
				     'red'
				],
				borderColor: [
					 'green',
					 'blue',
					 'red'
				],
				borderWidth: 1
			}]
		},
		options: {
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero: true
					}
				}]
			}
		}
	});
</script>


