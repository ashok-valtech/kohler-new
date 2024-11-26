<#include "../include/imports.ftl">

<#if document??>
	<#if document.videoPath?? && document.videoPath?has_content>
		<div class="koh-promo-video">
           <iframe src="${document.videoPath}?autoplay=1&mute=1" frameborder="0" allowfullscreen></iframe>
	    </div>
	</#if>
</#if>