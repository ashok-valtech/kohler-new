<#include "../include/imports.ftl">

<#if document??>
	<#if document.videoPath?? && document.videoPath?has_content>
        <div class="c-koh-walking-bath banner-container">
            <div class="koh-walking-bath-pages-banner embed-responsive embed-responsive-16by9">
                <iframe src="${document.videoPath}&autoplay=1&mute=1&loop=1" frameborder="0" allowfullscreen></iframe>
            </div>
        </div>
	</#if>
</#if>