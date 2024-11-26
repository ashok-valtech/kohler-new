<#include "../include/imports.ftl">
<@fmt.message key="back" var="back"/>
<#if document??>
	<div id="video-container" class="video-container">
		<button class="back-button">
			${back}
		</button>
		<iframe class="video-frame" src="${document.videoPath}&mute=1" frameborder="0" allowfullscreen></iframe>
	</div>
</#if>
