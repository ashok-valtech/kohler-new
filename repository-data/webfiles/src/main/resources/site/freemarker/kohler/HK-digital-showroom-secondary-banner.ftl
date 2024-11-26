<#include "../include/imports.ftl">

<#if document??>
	<div class = "koh-top-static-image banner-container">
		<#if document.imagePath?? && document.imagePath?has_content>
		<picture>
			<source media="(min-width:768px)" srcset="${document.imagePath}">
			<source media="(max-width:767px)" srcset="${document.videoPath}">
			<img src="${document.imagePath}">
		</picture>
		</#if>
	</div>
</#if>