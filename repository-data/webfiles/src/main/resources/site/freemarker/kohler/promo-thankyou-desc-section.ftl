<#include "../include/imports.ftl">

<@fmt.message key="userTemplateDear" var="userTemplateDear"/>
<@fmt.message key="userTemplateDearQuestion" var="userTemplateDearQuestion"/>

<#if document??>
	<div class = "c-koh-ramadan-thank-you-page">
		<div class = "koh-ramadan-thank-you-page-container">
			<section class = "koh-thank-you-top-section">
				<#if document.title?? && document.title?has_content>
					<h1 class = "koh-thank-you-main-title">${document.title}</h1>
				</#if>
				<#if document.description.content?? && document.description.content?has_content>
					<#if hstRequestContext.servletRequest.getParameter('name')?has_content>
						<#assign name="${hstRequestContext.servletRequest.getParameter('name')}" />
						<#if !name?contains("?")>
				 		<h2 class = "koh-thank-you-username">
                        	${userTemplateDear} ${name},
                     	</h2>
                     	<#elseif  userTemplateDearQuestion?? && userTemplateDearQuestion?has_content>
                     	<h2 class = "koh-thank-you-username">
                          ${userTemplateDearQuestion},
                     	</h2>
			     	  </#if>
					</#if>
					<h2 class = "koh-thank-you-first-description">
						<@hst.html hippohtml=document.description/>
					</h2>
				</#if>
			</section>
		</div>
	</div> 
</#if>
