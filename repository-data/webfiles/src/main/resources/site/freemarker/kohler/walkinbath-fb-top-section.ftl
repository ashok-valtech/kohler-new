<#include "../include/imports.ftl">
<#if document??>
	<#if document.kohler_walkinbathfbtopsectioncompuond??>
	<div class="koh-top-section">
		<section class="koh-walking-bath-fb-section">
			<div class="row koh-walking-bath-fb-row">
				<#list document.kohler_walkinbathfbtopsectioncompuond as walkInBathFBTopSection>
					<#if walkInBathFBTopSection.anchorName?? && walkInBathFBTopSection.anchorName?has_content>
						<#assign topSectionId = "${walkInBathFBTopSection.anchorName}">
					</#if>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 koh-walking-bath-coloumn">
						<figure class="koh-walking-bath-fb-figure" >
							<div class="koh-anchor-scroll" id="walkingBathId-${topSectionId}">
								<#if walkInBathFBTopSection.imagePath?? && walkInBathFBTopSection.imagePath?has_content>
									<img src="${walkInBathFBTopSection.imagePath}" <#if walkInBathFBTopSection.imageAlt?? && walkInBathFBTopSection.imageAlt?has_content>alt="${walkInBathFBTopSection.imageAlt}"</#if> <#if walkInBathFBTopSection.imageTitle?? && walkInBathFBTopSection.imageTitle?has_content>title="${walkInBathFBTopSection.imageTitle}"</#if>>
								</#if>
								<#if walkInBathFBTopSection.title?? && walkInBathFBTopSection.title?has_content>
			                       <#assign title = walkInBathFBTopSection.title?replace("(R)","<sup>&reg;</sup>")/>
								   <#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
			                    </#if>
								<#if title?? && title?has_content>
									<div class="koh-walking-bath-fb-title-format">
										<#if walkInBathFBTopSection.imageOrder?? && walkInBathFBTopSection.imageOrder?has_content>
											<i class="number">
												<span class="label-class">${walkInBathFBTopSection.imageOrder}</span>
											</i>
										</#if>
										${title}
									</div>
								</#if>
							</div>
							<#if walkInBathFBTopSection.description.content?? && walkInBathFBTopSection.description.content?has_content>
								<div class="koh-walking-bath-fb-desc-format"><@hst.html hippohtml=walkInBathFBTopSection.description/></div>
							</#if>
						</figure>
					</div>
				</#list>
			</div>
			<#if hrLine??>
				<#if hrLine == true>
					<div class="koh-walking-bath-underline-top"></div>
				</#if>
			</#if>	
		</section>
		</div>
	</#if>
</#if>
