<#include "../include/imports.ftl">
<@fmt.message key="promotion-ribbon-content-path" var="ribbonPath"/>

<#if ribbonPath?? && ribbonPath?has_content && promotionRibbonContent?? && promotionRibbonContent.kohler_promotionribbon?? && promotionRibbonContent.kohler_promotionribbon?has_content>
	<input type="hidden" id="promotionRibbonItems" value="${promotionRibbonContent.kohler_promotionribbon?size-1}" />
	<div id="c-koh-promotion-ribbon" class="c-koh-promotion-ribbon" >
		<#list promotionRibbonContent.kohler_promotionribbon as item>
			<#if item.ctaLink??>
				<#assign navigationLink=item.ctaLink />
				<#if navigationLink?has_content>
					<#assign target="_blank" />
					<#if navigationLink?starts_with( "/")>
						<#assign target="_self" />
					</#if>
				</#if>
			 </#if>
			<#if item.deskTopTitle?? && item.deskTopTitle?has_content>
				<#assign deskTopTitle=item.deskTopTitle />
			</#if>
			<div id="c-koh-promotion-ribbon-item${item?index}" class="c-koh-promotion-ribbon-item <#if navigationLink?? && navigationLink?has_content>navigation</#if>">
				<div class="koh-md-ribbon">
					<#if deskTopTitle?? && deskTopTitle?has_content>
						<div class="koh-md-ribbon-description" id="koh-ribbon-description">${item.deskTopTitle}
							<#if navigationLink?? && navigationLink?has_content>
								<div class="koh-md-ribbon-CTALabel" id="koh-ribbon-label"><a class="koh-ribbon-link" href="${navigationLink}" target="${target}">${item.ctaLabel}</a></div>
							</#if>
						</div>
					</#if>
				</div>
				
				<div class="koh-sm-ribbon">
					<#if item.responsiveTitle?? && item.responsiveTitle?has_content>
						<div class="koh-sm-ribbon-description" >${item.responsiveTitle}
							<#if navigationLink?? && navigationLink?has_content>
								<div class="koh-sm-ribbon-CTALabel"><a class="koh-ribbon-link" href="${navigationLink}" target="${target}">${item.ctaLabel}</a></div>
							</#if>
						</div>
					<#else>
						<div class="koh-sm-ribbon-description" >${deskTopTitle}
							<#if navigationLink?? && navigationLink?has_content>
								<div class="koh-sm-ribbon-CTALabel"><a class="koh-ribbon-link" href="${navigationLink}" target="${target}">${item.ctaLabel}</a></div>
							</#if>
						</div>
					</#if>
				</div>
			</div>  
		</#list>
	</div>  
</#if>
