<#include "../include/imports.ftl">
<input type="hidden" value="${document.imagePath}" id="desktopMainVideo" />
<input type="hidden" value="${document.videoPath}" id="mobileMainVideo" />
<@hst.link var="sixDesignerURL" siteMapItemRefId="six-designer-page"/>
<input type="hidden" value="${sixDesignerURL}" id="sixDesignerUrlId" />
<#if document??>
	<div class="koh-walking-bath-pages-banner embed-responsive embed-responsive-16by9">
		<#--  iframe video will be added here using JS -->
	</div>
</#if>

<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/c-koh-six-design-campaign.min.js"></script>
</@hst.headContribution>