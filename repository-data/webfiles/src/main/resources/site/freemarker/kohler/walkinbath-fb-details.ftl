<#include "../include/imports.ftl">

<#if document??>
	<#if document.kohler_walkinbathfbdetailscompound??>
		<div class= "koh-walkin-bath-detail">
			<#list document.kohler_walkinbathfbdetailscompound as walkInBathFBDetails>
				<div class="row koh-walking-bath-detail-row" <#if walkInBathFBDetails.anchorName?? && walkInBathFBDetails.anchorName?has_content>id="${walkInBathFBDetails.anchorName}" </#if>>
					<div class="col-sm-6 koh-walking-bath-detail-img">
						<#if walkInBathFBDetails.imagePath?? && walkInBathFBDetails.imagePath?has_content>
                        	<img src="${walkInBathFBDetails.imagePath}" <#if walkInBathFBDetails.imageAlt?? && walkInBathFBDetails.imageAlt?has_content> alt="${walkInBathFBDetails.imageAlt}"</#if> <#if walkInBathFBDetails.imageTitle?? && walkInBathFBDetails.imageTitle?has_content> title="${walkInBathFBDetails.imageTitle}"</#if>>
	                        <#if walkInBathFBDetails.imageCaption?? && walkInBathFBDetails.imageCaption?has_content>
								<div class ="koh-walking-bath-detail-image-caption">${walkInBathFBDetails.imageCaption}</div>
							</#if>
	                    </#if>
	                </div>
					<div class="col-sm-6 koh-walking-bath-detail-item-title">
						<div class="koh-walking-bath-detail-item-title-inner">
							<#if walkInBathFBDetails.title?? && walkInBathFBDetails.title?has_content>
		                       <#assign title = walkInBathFBDetails.title?replace("(R)","<sup>&reg;</sup>")/>
							   <#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
		                    </#if>
							<#if title?? && title?has_content>
								<div class= "koh-walking-bath-detail-item-first-title">${title}</div>
							</#if>
							<#if walkInBathFBDetails.subTitle?? && walkInBathFBDetails.subTitle?has_content>
								<div class="koh-walking-bath-detail-subtitle">${walkInBathFBDetails.subTitle}</div>
							</#if>
	                        <#if walkInBathFBDetails.description.content?? && walkInBathFBDetails.description.content?has_content>
								<div class="koh-walking-bath-detail-description"><@hst.html hippohtml=walkInBathFBDetails.description/></div>
							</#if>
						</div>
					</div>
				</div>
			</#list>
		</div>
	</#if>
</#if>