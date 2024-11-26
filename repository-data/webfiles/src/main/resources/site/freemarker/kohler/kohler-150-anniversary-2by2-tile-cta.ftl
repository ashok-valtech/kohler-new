<#include "../include/imports.ftl">
<@fmt.message key="kohler_150_learn_more" var="learnMore" />
<#if document??>
	<section class="c-koh-150-2by2-tile-cta-image">
		<div class ="koh-150-2by2-tile-cta-promo-container">
			<div class="row koh-150-2by2-tile-cta-promo">
				<#if document.promotionButton?? && document.promotionButton?has_content>
					<div class="col-12 col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<h2 class="koh-150-header-title">
							${document.promotionButton}
						</h2>
					</div>
				</#if>
				<#if document.title.content?? && document.title.content?has_content>
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
						<div class="koh-150-header-description">
							<@hst.html hippohtml=document.title/>
						</div>
					</div>
				</#if>
				<#if document.promotionLink?? && document.promotionLink?has_content>
					<#assign promotionLink=document.promotionLink />
	    			<#assign target="_blank" />
	                <#if promotionLink?starts_with( "/")>
	                    <#assign target="_self" />
	                </#if>
                </#if>
                <#if promotionLink?has_content>
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
						<a class="koh-2by2-150-image-btn" href="${promotionLink}" target="${target}">${learnMore}</a>
					</div>
				</#if>
				<#if document.landingPagePackageCompund??>
					<#list document.landingPagePackageCompund as item>
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
							<#if item.imageLink?? && item.imageLink?has_content>
								<figure class="koh-150-2by2-tile-cta-promo-item">
									<img src="${item.imageLink}">
								</figure>
							</#if>
							<#if item.imageTitle?? && item.imageTitle?has_content>
								<h3 class="koh-2by2-150-image-cta-title">${item.imageTitle}</h3>
							</#if>
							<#if item.navigationLink?? && item.navigationLink?has_content>
								<div class="koh-2by2-150-image-cta-description">
									${item.navigationLink}
								</div>
							</#if>
						</div>	
					</#list>
				</#if>
			</div>
		</div>
	</section>
</#if>
