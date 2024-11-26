<#include "../include/imports.ftl">

<#if document??>
	<#if document.kohler_walkinbathfbdetailscompound??>
		<div class= "koh-showroom-cta-image-section">
			<#list document.kohler_walkinbathfbdetailscompound as walkInBathFBDetails>
				<div class="row koh-showroom-cta-image-section-row">
					<#if walkInBathFBDetails.anchorName?? && walkInBathFBDetails.anchorName?has_content>
						<#assign navigationURL = walkInBathFBDetails.anchorName>
						<#assign target="_blank" />
                        <#if navigationURL?starts_with( "/")>
                            <#assign target="_self" />
                        </#if>
					</#if>
                	<div class="col-sm-6 koh-showroom-cta-image-section-img">
						<#if walkInBathFBDetails.imagePath?? && walkInBathFBDetails.imagePath?has_content>
                        	<img src="${walkInBathFBDetails.imagePath}" <#if walkInBathFBDetails.imageAlt?? && walkInBathFBDetails.imageAlt?has_content> alt="${walkInBathFBDetails.imageAlt}"</#if> <#if walkInBathFBDetails.imageTitle?? && walkInBathFBDetails.imageTitle?has_content> title="${walkInBathFBDetails.imageTitle}"</#if>>
	                    </#if>
	                </div>
				<div class="col-sm-6 koh-showroom-cta-image-section-title">
					<div class="koh-showroom-cta-image-section-inner-title">
						<#if walkInBathFBDetails.title?? && walkInBathFBDetails.title?has_content>
	                       <#assign title = walkInBathFBDetails.title?replace("(R)","<sup>&reg;</sup>")/>
						   <#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
	                    </#if>
						<#if title?? && title?has_content>
							<div class="koh-showroom-cta-image-section-first-title">${title}</div>
						</#if>
                        <#if walkInBathFBDetails.description.content?? && walkInBathFBDetails.description.content?has_content>
							<div class="koh-showroom-cta-image-section-description"><@hst.html hippohtml=walkInBathFBDetails.description/></div>
						</#if>
						<#if walkInBathFBDetails.imageCaption?? && walkInBathFBDetails.imageCaption?has_content>
							<a href="${navigationURL}" target="${target}" class="koh-showroom-cta">${walkInBathFBDetails.imageCaption}</a>
						</#if>
					</div>
				</div>
			</div>
		  </#list>
		</div>
	</#if>
</#if>