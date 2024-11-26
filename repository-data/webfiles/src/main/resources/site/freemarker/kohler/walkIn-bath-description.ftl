<#include "../include/imports.ftl">

<#if document??>
	<div class = "koh-walking-bath-title-and-description">
		<#if document.title?? && document.title?has_content>
			<h1 class="koh-walking-bath-text-title">${document.title}</h1>
		</#if>
		<#if document.description.content?? && document.description.content?has_content>
			<h2 class = "koh-walking-bath-title-description">
				<@hst.html hippohtml=document.description/>
			</h2>
		</#if>
	</div> 
</#if>