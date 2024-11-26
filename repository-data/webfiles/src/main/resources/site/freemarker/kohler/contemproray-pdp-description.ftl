<#include "../include/imports.ftl">

<#if document??>
	<div class = "koh-contemporary-pdp-title-and-description">
		<#if document.title?? && document.title?has_content>
			<h1 class="koh-contemporary-pdp-title">${document.title}</h1>
		</#if>
		<#if document.description.content?? && document.description.content?has_content>
			<h2 class = "koh-contemporary-pdp-description">
				<@hst.html hippohtml=document.description/>
			</h2>
		</#if>
	</div> 
</#if>