<#include "../include/imports.ftl">
<#if document??>
	<section class="c-koh-shopping-list-2promo">
			<#if document.title?? && document.title?has_content>
  				<div class="koh-main-header">
  					${document.title}
  				</div>
			</#if>
			<#if document.description?? && document.description?has_content>
		      <div class="koh-sub-header"><@hst.html hippohtml=document.description/></div>
		  	</#if>
			<div class="row koh-shopping-list-2promo">
				<div class ="koh-shopping-guide-container"> 
					<#if document.kohler_articledescriptivecarousaldocument??>
						<#list document.kohler_articledescriptivecarousaldocument as item>
							<#if item.carousalImageTitle?? && item.carousalImageTitle?has_content>
								<#assign carousalImageTitle = item.carousalImageTitle />
							</#if>
							
							<#if item.carousalImageAlt?? && item.carousalImageAlt?has_content>
								<#assign imageAlt = item.carousalImageAlt />
							</#if>
							<#if (item.carousalImageURL?? && item.carousalImageURL?has_content) || (item.navigationLink?? && item.navigationLink?has_content)>
	            				<#assign navigationLink = item.navigationLink />
		                		<#assign target="_blank" />
                                <#if navigationLink?starts_with( "/")>
                                    <#assign target="_self" />
                                </#if>
							</#if>
			         		<div class="koh-shopping-guide-2promo">
								<figure class="koh-shopping-item-2promo">
									<#if navigationLink?? && navigationLink?has_content>
				            			<a href="${navigationLink}"  target="${target}">
				            				<img src="<#if item.carousalImageURL?? && item.carousalImageURL?has_content>${item.carousalImageURL}</#if>" <#if imageAlt?? && imageAlt?has_content>alt="${imageAlt}"</#if>>
				            			</a>
			            			<#else>
										<img src="<#if item.carousalImageURL?? && item.carousalImageURL?has_content>${item.carousalImageURL}</#if>" <#if imageAlt?? && imageAlt?has_content>alt="${imageAlt}"</#if>>
									</#if>
		               				<#if carousalImageTitle?? && carousalImageTitle?has_content>
		                  				<div class="koh-shopping-2promo-title-format">${carousalImageTitle}</div>
		                  			</#if>
									<#if item.description.content?? && item.description.content?has_content>
		                  				<div class="koh-shopping-2promo-desc-format"><@hst.html hippohtml=item.description/></div>
	                  				</#if>
			            		</figure>
			         		</div>
		         		</#list>
	         		</#if>
         		</div>
			</div>
	</section>
</#if>
