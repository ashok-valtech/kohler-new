<#include "../include/imports.ftl">

<#if document??>
	<#if document.videoPath?? && document.videoPath?has_content>
		<div class="koh-150-anniversary-video">
			<div class="embed-responsive embed-responsive-16by9">
	           <iframe src="${document.videoPath}?rel=0&amp;showinfo=0" frameborder="0" width="100%" allowfullscreen ></iframe>
		    </div>
	    </div>
	</#if>
</#if>