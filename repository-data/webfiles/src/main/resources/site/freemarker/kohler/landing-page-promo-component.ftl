<#include "../include/imports.ftl">

<#if document??>
	<div class = "koh-top-static-image">
		<#if document.imagePath?? && document.imagePath?has_content>
			<img src="${document.imagePath}">
		</#if>
	</div>
</#if>