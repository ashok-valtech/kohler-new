<#include "../include/imports.ftl">

<#if document??>
	<section class="c-koh-shopping-guide-carousel v-koh-inline koh-banner-thumbnail-comp">
		<div class="koh-video-header">		
			<#if document.title?? && document.title?has_content>
				<@hst.html hippohtml=document.title/>
			</#if>
		</div>
		<div class="koh-carousel koh-theme-dark">
			<div class="koh-slide-collection">
				<#if document.landingPagePackageCompund??>
					<#list document.landingPagePackageCompund as item>
						<div class="koh-banner-slide v-koh-simple m-koh-linked">
							<div class="koh-banner-background">
								<#if item.imageLink?? && item.imageLink?has_content>
	                                  <iframe class="koh-shop-by-look-video" src="${item.imageLink}" frameborder="0" allowfullscreen></iframe>
	                          	</#if>
							</div>
						</div>
					 </#list>
				 </#if>	
			</div>
		</div>
	</section>
</#if>
