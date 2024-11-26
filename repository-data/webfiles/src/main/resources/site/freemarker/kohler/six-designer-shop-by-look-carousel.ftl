<#include "../include/imports.ftl">

<#if document??>
	<section class="koh-cmf-three-image-carousel shop-carousel-container">
	   <div class="koh-carousel shop-craousel">
	   <div class="koh-title-container">
		<#if document.title?? && document.title?has_content>
			<div class="koh-main-title shop-title">${document.title}</div>
		</#if>
		</div>
	      <div class="koh-img-carousel images-container">
      		<#list document.articleThreeImageCompound as item>
	         	<div class="koh-images-caption">
		         	<#if item.navigationLink?? && item.navigationLink?has_content>
		         		<#assign navigationLink = item.navigationLink />
                		<#assign target="_blank" />
                        <#if navigationLink?starts_with( "/")>
                            <#assign target="_self" />
                        </#if>
						<a href="${navigationLink}" target="${target}">
							<#if item.imagePath?? && item.imagePath?has_content>
			            		<img src="${item.imagePath}" <#if item.imageAlt?? && item.imageAlt?has_content> alt="${item.imageAlt}"</#if>>
			            		<#if item.imageDescription.content?? && item.imageDescription.content?has_content>
					            <span class="koh-outerLayer-Caption">
						            <span class="koh-carousel-imageCaption">
						            	<@hst.html hippohtml=item.imageDescription/>
						            </span>
		           		 		</span>
			           		  </#if>
							</#if>
						</a>
					<#else>
						<#if item.imagePath?? && item.imagePath?has_content>
		            		<img src="${item.imagePath}" <#if item.imageAlt?? && item.imageAlt?has_content> alt="${item.imageAlt}"</#if>>
		            		<#if item.imageDescription.content?? && item.imageDescription.content?has_content>
									<span class="koh-outerLayer-Caption">
										<span class="koh-carousel-imageCaption">
											<@hst.html hippohtml=item.imageDescription/>
										</span>
									</span>
								</#if>
							</#if>
						</#if>
					<#if item.imageTitle?? && item.imageTitle?has_content>
						<span class="tile-title">${item.imageTitle}</span>
					</#if>
					</div>
				</#list>
			</div>
		</div>
	</section>
</#if>

<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/c-koh-six-design-campaign.min.js"></script>
</@hst.headContribution>