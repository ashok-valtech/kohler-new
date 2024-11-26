<#include "../include/imports.ftl">
<#if document??>
	<section class="c-koh-how-it-works-list">
			<#if document.title?? && document.title?has_content>
  				<div class="koh-head-title">
  					<h2>${document.title}</h2>
  				</div>
			</#if>
			<div class="row koh-how-it-works-promo">
				<div class ="koh-how-it-works-container"> 
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
			         		<div class="koh-how-it-works-promo-list">
								<figure class="koh-how-it-works-item">
									<#if navigationLink?? && navigationLink?has_content>
				            			<a href="${navigationLink}"  target="${target}">
				            				<img src="<#if shoppingGuide.imageURL?? && shoppingGuide.imageURL?has_content>${shoppingGuide.imageURL}</#if>" <#if imageAlt?? && imageAlt?has_content>alt="${imageAlt}"</#if> <#if shoppingGuide.imageTitle?? && shoppingGuide.imageTitle?has_content>title="${shoppingGuide.imageTitle}"</#if>>
				            			</a>
			            			<#else>
										<img src="<#if shoppingGuide.imageURL?? && shoppingGuide.imageURL?has_content>${shoppingGuide.imageURL}</#if>" <#if imageAlt?? && imageAlt?has_content>alt="${imageAlt}"</#if> <#if shoppingGuide.imageTitle?? && shoppingGuide.imageTitle?has_content>title="${shoppingGuide.imageTitle}"</#if>>
									</#if>
		               				<#if shoppingGuideTitle?? && shoppingGuideTitle?has_content>
		                  				<div class="koh-how-it-works-image-title">${shoppingGuideTitle}</div>
		                  			</#if>
									<#if shoppingGuide.description?? && shoppingGuide.description?has_content>
		                  				<div class="koh-how-it-works-image-desc">${shoppingGuide.description}</div>
	                  				</#if>
			            		</figure>
			         		</div>
		         		</#list>
	         		</#if>
         		</div>
			</div>
	</section>
</#if>
