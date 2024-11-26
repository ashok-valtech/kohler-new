<#include "../include/imports.ftl">
<#if document??>
	<section class="c-koh-showroom-tile-section">
			<#if document.title?? && document.title?has_content>
  				<div class="koh-head-wrapper">
  					<h2>${document.title}</h2>
  				</div>
			</#if>
			<div class="row koh-showroom-tile-list">
				<#if document.shoppingGuideCompoundDocument??>
					<#list document.shoppingGuideCompoundDocument as shoppingGuide>
						<#if shoppingGuide.title?? && shoppingGuide.title?has_content>
							<#assign shoppingGuideTitle = shoppingGuide.title />
						</#if>
						
						<#if shoppingGuide.imageAlt?? && shoppingGuide.imageAlt?has_content>
							<#assign imageAlt = shoppingGuide.imageAlt />
						<#else>
						<#if shoppingGuideTitle?? && shoppingGuideTitle?has_content>
							<#assign imageAlt = shoppingGuideTitle />
						</#if>	
						</#if>
						<#if (shoppingGuide.imageURL?? && shoppingGuide.imageURL?has_content) || (shoppingGuide.navigationLink?? && shoppingGuide.navigationLink?has_content)>
            				<#assign navigationLink = shoppingGuide.navigationLink />
	                		<#assign target="_blank" />
                            <#if navigationLink?starts_with( "/")>
                                <#assign target="_self" />
                            </#if>
						</#if>
		         		<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 koh-showroom-tile-guide">
							<figure class="koh-showroom-tile-image image-container">
								<#if navigationLink?? && navigationLink?has_content>
			            			<a href="${navigationLink}"  target="${target}">
									<picture>
										<source media="(min-width:501px)" srcset="<#if shoppingGuide.imageURL?? && shoppingGuide.imageURL?has_content>${shoppingGuide.imageURL}</#if>">
										<source media="(max-width:500px)" srcset="<#if shoppingGuide.imageTitle?? && shoppingGuide.imageTitle?has_content>${shoppingGuide.imageTitle}</#if>">
			            				<img class="tile-image" src="<#if shoppingGuide.imageURL?? && shoppingGuide.imageURL?has_content>${shoppingGuide.imageURL}</#if>" <#if imageAlt?? && imageAlt?has_content>alt="${imageAlt}"</#if>>
									</picture>	
				            			<div class="gradient-container"></div>
										<#if shoppingGuideTitle?? && shoppingGuideTitle?has_content>
				            				<div class="koh-showroom-tile-content hk-tile-content">
			                  					<div class="koh-showroom-tile-title title-text">
			                  						${shoppingGuideTitle}
			                  					</div>
			                  					<#if shoppingGuide.description?? && shoppingGuide.description?has_content>
		                  							<img class="koh-showroom-360-icon showroom-360-icon" src="${shoppingGuide.description}">
	                  							</#if>
		                  					</div>
											
		                  				</#if>
	                  				</a>
		            			<#else>
									<img src="<#if shoppingGuide.imageURL?? && shoppingGuide.imageURL?has_content>${shoppingGuide.imageURL}</#if>" <#if imageAlt?? && imageAlt?has_content>alt="${imageAlt}"</#if> <#if shoppingGuide.imageTitle?? && shoppingGuide.imageTitle?has_content>title="${shoppingGuide.imageTitle}"</#if>>
									<#if shoppingGuideTitle?? && shoppingGuideTitle?has_content>
			            				<div class="koh-showroom-tile-content">
		                  					<div class="koh-showroom-tile-title">
		                  						${shoppingGuideTitle}
		                  					</div>
	                  						<#if shoppingGuide.description?? && shoppingGuide.description?has_content>
	                  							<img class="koh-showroom-360-icon" src="${shoppingGuide.description}">
                  							</#if>
	                  					</div>
	                  				</#if>
								</#if>
		            		</figure>
		         		</div>
	         		</#list>
         		</#if>
			</div>
	</section>
</#if>

