<#include "../include/imports.ftl">

<#if document??>
	<div class ="koh-promotion-titledesc-comp title-container background-grey">
		<#if document.title?? && document.title?has_content>
			<div class="koh-promotion-title title-text">${document.title}</div>
		</#if>
		<#if document.description.content?? && document.description.content?has_content>
			<div class ="koh-promotion-description">
				<@hst.html hippohtml=document.description/>
			</div>
		</#if>
	</div>
</#if>