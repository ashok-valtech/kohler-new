<#include "../include/imports.ftl">

<#if document??>
	<#if document.videoPath?? && document.videoPath?has_content>
		<div class="koh-walking-bath-pages-banner embed-responsive embed-responsive-16by9">
           <iframe src="${document.videoPath}?rel=0&amp;showinfo=0" frameborder="0" allowfullscreen></iframe>
	    </div>
	</#if>
</#if>