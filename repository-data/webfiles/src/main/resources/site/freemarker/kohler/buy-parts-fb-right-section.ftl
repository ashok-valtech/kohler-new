<#include "../include/imports.ftl">

<#if document??>
	<#if document.kohler_walkinbathfbdetailscompound??>
		<div class= "koh-fb-buy-parts-right-section">
			<#list document.kohler_walkinbathfbdetailscompound as walkInBathFBDetails>
				<div class="row koh-fb-buy-parts-right-section-row">
					<#if walkInBathFBDetails.anchorName?? && walkInBathFBDetails.anchorName?has_content>
						<#assign navigationURL = walkInBathFBDetails.anchorName>
						<#assign target="_blank" />
                        <#if navigationURL?starts_with( "/")>
                            <#assign target="_self" />
                        </#if>
					</#if>
					<#if walkInBathFBDetails.anchorName?? && walkInBathFBDetails.anchorName?has_content>
						<a href="${navigationURL}"  target="${target}">
							<div class="col-sm-6 koh-fb-buy-parts-right-section-img">
								<#if walkInBathFBDetails.imagePath?? && walkInBathFBDetails.imagePath?has_content>
		                        	<img src="${walkInBathFBDetails.imagePath}" <#if walkInBathFBDetails.imageAlt?? && walkInBathFBDetails.imageAlt?has_content> alt="${walkInBathFBDetails.imageAlt}"</#if> <#if walkInBathFBDetails.imageTitle?? && walkInBathFBDetails.imageTitle?has_content> title="${walkInBathFBDetails.imageTitle}"</#if>>
			                    </#if>
			                </div>
		                </a>
	                <#else>
	                	<div class="col-sm-6 koh-fb-buy-parts-right-section-img">
							<#if walkInBathFBDetails.imagePath?? && walkInBathFBDetails.imagePath?has_content>
	                        	<img src="${walkInBathFBDetails.imagePath}" <#if walkInBathFBDetails.imageAlt?? && walkInBathFBDetails.imageAlt?has_content> alt="${walkInBathFBDetails.imageAlt}"</#if> <#if walkInBathFBDetails.imageTitle?? && walkInBathFBDetails.imageTitle?has_content> title="${walkInBathFBDetails.imageTitle}"</#if>>
		                    </#if>
		                </div>
	                </#if>
				<div class="col-sm-6 koh-fb-buy-parts-right-section-title">
					<div class="koh-fb-buy-parts-right-section-inner-title">
						<#if walkInBathFBDetails.title?? && walkInBathFBDetails.title?has_content>
	                       <#assign title = walkInBathFBDetails.title?replace("(R)","<sup>&reg;</sup>")/>
						   <#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
	                    </#if>
						<#if title?? && title?has_content>
							<div class= "koh-fb-buy-parts-right-section-first-title">${title}</div>
						</#if>
                        <#if walkInBathFBDetails.description.content?? && walkInBathFBDetails.description.content?has_content>
							<div class="koh-fb-buy-parts-right-section-description"><@hst.html hippohtml=walkInBathFBDetails.description/></div>
						</#if>
					</div>
				</div>
			</div>
		  </#list>
		</div>
	</#if>
</#if>