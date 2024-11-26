<#include "../include/imports.ftl">
<#if document??>
	<section class="c-koh-banner-carousel v-koh-inline koh-banner-thumbnail-comp koh-text-image-carousel-comp">
		<div class="koh-carousel koh-theme-dark">
			<div class="koh-slide-collection">
				<#if document.kohler_articledescriptivecarousaldocument??>
					<#list document.kohler_articledescriptivecarousaldocument as item>
						<div class="koh-banner-slide v-koh-simple m-koh-linked">
							<#assign navigationLink = "" />
							<#if item.navigationLink?? && item.navigationLink?has_content>
		                		<#assign navigationLink = item.navigationLink />
		                		<#assign target="_blank" />
	                            <#if navigationLink?starts_with( "/")>
	                            	<#assign target="_self" />
	                            </#if>
	                        </#if>
	                        <#if item.carousalImageURL?? && item.carousalImageURL?has_content>
	                            <#if navigationLink?? && navigationLink?has_content>
	                            	<a href="${navigationLink}" target="${target}">
	                            </#if>
	                             <#if item.carousalImageAlt?? && item.carousalImageAlt?has_content>
	                            	<#assign carousalAlt=item.carousalImageAlt />
	                            <#else>
	                                <#assign carousalAlt=title />
	                             </#if>  
								<div class="koh-banner-background">
									<img src="${item.carousalImageURL}" alt="${carousalAlt}" <#if item.carousalImageTitle?? && item.carousalImageTitle?has_content> title="${item.carousalImageTitle}"</#if> >
								</div>
								<#if navigationLink?? && navigationLink?has_content>
									</a>
								</#if>	
							</#if>
							<div class="koh-banner-content koh-valignment-middle">
							    <span class="koh-banner-text" style="">
							    	<#if item.description?? && item.description?has_content>
								    	<#if navigationLink?? && navigationLink?has_content>
	                                        <a href="${navigationLink}" target="${target}">
	                                    </#if>
										<span class="koh-banner-title" style=""><@hst.html hippohtml=item.description/></span>
										<#if navigationLink?? && navigationLink?has_content>
											</a>
										</#if>
									</#if>
									<div class="more-buttons">
					                	<#if item.learnMoreLink?? && item.learnMoreLink?has_content>
					                		<#assign learnMoreLink = item.learnMoreLink />
					                		<#assign target="_blank" />
                                            <#if learnMoreLink?starts_with( "/")>
                                                <#assign target="_self" />
                                            </#if>
						                	<#if item.learnMoreLabel?? && item.learnMoreLabel?has_content>
				                          		<a href="${learnMoreLink}"  target="${target}" class="sw-button">${item.learnMoreLabel}</a>
			                          		</#if>
										</#if>
				                       </div>
									</span>
								</div>
			            	</div>
			            </#list>
		            </#if>
				</div>
		</div>
	</section>
</#if>