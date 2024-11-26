<#include "../include/imports.ftl">

<#if document??>
<section class="koh-cmf-three-image-carousel shop-carousel-container">
   <div class="koh-carousel shop-craousel">
   <div class="koh-title-container">
		<#if document.title.content?? && document.title.content?has_content>
			<div class="koh-main-title shop-title"><@hst.html hippohtml=document.title/></div>
		</#if>
			<#if document.promotionLink?? && document.promotionLink?has_content>
			<#assign promotionLink=document.promotionLink />
			<#assign target="_blank" />
				<#if promotionLink?starts_with( "/")>
					<#assign target="_self" />
				</#if>
			</#if>
			<#if promotionLink?? && promotionLink?has_content>
				<div class="koh-cta-container">
					<a class="tile-cta" href='${promotionLink}' target="${target}">
						<#if document.promotionButton?? && document.promotionButton?has_content>
							${document.promotionButton}
						</#if>
					</a>
				</div>
			</#if>
		</div>
	      <div class="koh-img-carousel images-container">
			<#if document.landingPagePackageCompund??>
				<#list document.landingPagePackageCompund as item>
					<div class="koh-images-caption">
						<#if item.imageLink?? && item.imageLink?has_content>
							<img src="${item.imageLink}" <#if item.imageAlt?? && item.imageAlt?has_content> alt="${item.imageAlt}"</#if>>
						</#if>
						<#if item.imageTitle?? && item.imageTitle?has_content>
							<span class="tile-title">${item.imageTitle}</span>
						</#if>
					</div>
				</#list>
			</#if>
		</div>
	</div>
</section>
</#if>

<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/c-koh-six-design-campaign-video-page.min.js"></script>
</@hst.headContribution>