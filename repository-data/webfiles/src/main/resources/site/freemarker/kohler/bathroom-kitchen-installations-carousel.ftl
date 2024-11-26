<#include "../include/imports.ftl">

<#if document??>
	<section class="koh-installations-carousel-component">
	   <div class="koh-installations-carousel">
	   <#if document.title?? && document.title?has_content>
	      <div class="koh-text-title">${document.title}</div>
	  	</#if>
	  	<#if document.description?? && document.description?has_content>
	      <div class="koh-text-description"><@hst.html hippohtml=document.description/></div>
	  	</#if>
	      <div class="koh-img-carousel">
	  		<#list document.kohler_articledescriptivecarousaldocument as item>
	         	<div class="koh-images-caption">
		         	<#if item.navigationLink?? && item.navigationLink?has_content>
		         		<#assign navigationLink = item.navigationLink />
	            		<#assign target="_blank" />
	                    <#if navigationLink?starts_with( "/")>
	                        <#assign target="_self" />
	                    </#if>
						<a href="${navigationLink}" target="${target}">
							<#if item.carousalImageURL?? && item.carousalImageURL?has_content>
			            		<img src="${item.carousalImageURL}" <#if item.carousalImageTitle?? && item.carousalImageTitle?has_content> title="${item.carousalImageTitle}"</#if> <#if item.carousalImageAlt?? && item.carousalImageAlt?has_content> alt="${item.carousalImageAlt}"</#if>>
							</#if>
						</a>
					<#else>
						<#if item.carousalImageURL?? && item.carousalImageURL?has_content>
			            		<img src="${item.carousalImageURL}" <#if item.carousalImageTitle?? && item.carousalImageTitle?has_content> title="${item.carousalImageTitle}"</#if> <#if item.carousalImageAlt?? && item.carousalImageAlt?has_content> alt="${item.carousalImageAlt}"</#if>>
							</#if>
						</#if>
						<#if item.description.content?? && item.description.content?has_content>
				            <div class="koh-carousel-imageCaption">
				            	<@hst.html hippohtml=item.description/>
				            </div>
	           		   </#if>
						<div class="koh-product-carousel-ctas">
							<#if item.learnMoreLink?? && item.learnMoreLink?has_content>
		                		<#assign learnMoreLink = item.learnMoreLink />
		                		<#assign target="_blank" />
		                        <#if learnMoreLink?starts_with( "/")>
		                            <#assign target="_self" />
		                        </#if>
			                	<#if item.learnMoreLabel?? && item.learnMoreLabel?has_content>
		                      		<a href="${learnMoreLink}"  target="${target}" class="koh-product-carousel-enquiry-btn">${item.learnMoreLabel}</a>
		                  		</#if>
							</#if>
						</div>
					</div>
				</#list>
			</div>
		</div>
	</section>
</#if>


<@hst.headContribution category="ext-scripts">
	<@hst.webfile path="/" var="link" />
	<script src="${link}/js/custom/c-koh-articles-three-image-carousel.min.js" type="text/javascript"></script>
</@hst.headContribution>