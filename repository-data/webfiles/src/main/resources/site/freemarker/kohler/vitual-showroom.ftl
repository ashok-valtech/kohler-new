<#include "../include/imports.ftl">

<#if document??>
	<#if document.videoPath?? && document.videoPath?has_content>
		<div class="koh-virtual-showroom">
           <iframe src="${document.videoPath}" frameborder="0" style="width: 100.0%;height: 641.0px;"></iframe>
        </div>
	</#if>
</#if>