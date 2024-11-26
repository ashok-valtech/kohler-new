<#include "../include/imports.ftl">

<@fmt.message key="sociable-Instagram-API-ID" var="instagramAPI"/>
<@fmt.message key="instagram-main-header" var="instagramMainHeader"/>
<@fmt.message key="instagram-sub-header" var="instagramSubHeader"/>

<input type="hidden" id="sociableInstagramAPIId" value="${instagramAPI}" />

<section class="c-koh-social-grid v-koh-social-scattered background-grey">
	<div class="koh-social-header">${instagramMainHeader}</div>
	<div class="koh-social-sub-header">${instagramSubHeader}</div>
	<div class="koh-social-content">
		<div class="koh-social-tiles">
		</div>
	</div>
</section>
	
<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/c-koh-six-design-campaign.min.js"></script>
</@hst.headContribution>