<#include "../include/imports.ftl">

<#if document??>
	<div class = "koh-contemporary-image">
		<#if document.navigationPath?? && document.navigationPath?has_content>
		<#assign navigationLink = document.navigationPath />
    		<#assign target="_blank" />
            <#if navigationLink?starts_with( "/")>
                <#assign target="_self" />
            </#if>
            <a href="${navigationLink}" target="${target}">
				<#if document.imagePath?? && document.imagePath?has_content>
					<img src="${document.imagePath}">
				</#if>
			</a>
		<#else>
			<#if document.imagePath?? && document.imagePath?has_content>
				<img src="${document.imagePath}">
			</#if>
		</#if>
	</div>
</#if>