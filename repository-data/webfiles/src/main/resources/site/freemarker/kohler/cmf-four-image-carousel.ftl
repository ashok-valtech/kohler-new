<#include "../include/imports.ftl">

<#if document??>
	<section class="koh-cmf-three-image-carousel">
	   <div class="koh-carousel">
	   <#if document.title?? && document.title?has_content>
	      <div class="koh-main-title">${document.title}</div>
       </#if>
	   <#if document.description.content?? && document.description.content?has_content>
	      <div class="koh-text-description"><@hst.html hippohtml=document.description/></div>
  		</#if>
	      <div class="koh-img-carousel">
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
			            		<img src="${item.imagePath}" <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if>
                              <#if item.imageAlt?? && item.imageAlt?has_content> alt="${item.imageAlt}"</#if>>
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
		            		<img src="${item.imagePath}" <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if>
                              <#if item.imageAlt?? && item.imageAlt?has_content> alt="${item.imageAlt}"</#if>>
		            		<#if item.imageDescription.content?? && item.imageDescription.content?has_content>
									<span class="koh-outerLayer-Caption">
										<span class="koh-carousel-imageCaption">
											<@hst.html hippohtml=item.imageDescription/>
										</span>
									</span>
								</#if>
							</#if>
						</#if>
					</div>
				</#list>
			</div>
		</div>
	</section>
</#if>


<@hst.headContribution category="ext-scripts">
	<@hst.webfile path="/" var="link" />
	<script src="${link}/js/custom/c-koh-cmf-four-image-carousel.min.js" type="text/javascript"></script>
</@hst.headContribution>