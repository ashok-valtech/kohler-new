<#include "../include/imports.ftl">
<#if isFullSize??>
	<#if isFullSize?string("yes", "no") == "yes">
    	<div class="koh-horizontalrule-block">
			<hr />
		</div>
	<#else>
    	<div class="koh-horizontalrule-block koh-horizontalrule-cwidth">
			<hr />
		</div>
	</#if>
</#if>	