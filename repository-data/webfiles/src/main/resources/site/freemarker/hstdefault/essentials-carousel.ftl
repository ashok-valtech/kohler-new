<#include "../include/imports.ftl">
<@hst.setBundle basename="apac.labels"/>
<@fmt.message key="carousel-link-button" var="carouselLinkButton"/>
<#if pageable?? && pageable.items?has_content>
	<#if cparam.pause>
        <#assign pauseCarousel = 'hover'/>
    <#else>
        <#assign pauseCarousel = ''/>
    </#if>
		<section class="c-koh-banner-carousel v-koh-hero">
			<div class="koh-carousel koh-theme-dark">
				<div class="koh-slide-collection">
					
					<#list pageable.items as item>
							<#assign colorCode="" />
							<#if item.textColor?? && item.textColor?has_content>
								<#assign colorCode=item.textColor />
							</#if>
						   <#assign link="" />
						   <#assign target="_blank" />
					       <#assign rel="noopener noreferrer" />
						   <#if item.navigationPath?? && item.navigationPath?has_content>
						   	   <#assign link=item.navigationPath?html />
							   <#if item.navigationPath?starts_with("/")>
			          				<#assign target="_self" />
			          				<#assign rel="" />
			          				<#assign link=item.navigationPath/>
								</#if>
								   <#if item.title?? &&item.title?has_content>
			          				 <#assign imageAlt=item.title/>
								   </#if>
								<#assign buttonText= "${carouselLinkButton}" />
								<#if item.buttonText?? && item.buttonText?has_content>
									<#assign buttonText=item.buttonText />
								</#if>
							</#if>	
							 <#if item.imageAlt?? &&item.imageAlt?has_content>
			          		   <#assign imageAlt=item.imageAlt>
						     </#if>
							<div class="koh-banner-slide v-koh-simple m-koh-linked">
								<div class="koh-banner-background">
                                      <#if link?? && link?has_content>
	                                      <a href="${link}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>
											<picture>
												<source media="(min-width:980px)" srcset="<#if item.imagePath?? && item.imagePath?has_content>${item.imagePath}</#if>">
												<img src="<#if item.imagePath?? && item.imagePath?has_content>${item.imagePath}</#if>" alt="<#if imageAlt?? && imageAlt?has_content>${imageAlt}</#if>">
											</picture>
										  </a> 
									  <#else>
										  <picture>
												<source media="(min-width:980px)" srcset="<#if item.imagePath?? && item.imagePath?has_content>${item.imagePath}</#if>">
												<img src="<#if item.imagePath?? && item.imagePath?has_content>${item.imagePath}</#if>" alt="<#if imageAlt?? && imageAlt?has_content>${imageAlt}</#if>">
										  </picture>
									  </#if>	  	  
								</div>
								<div class="koh-banner-background-mobile">
									 <picture>
										<source media="(max-width:768px)" srcset="<#if item.imageTitle?? && item.imageTitle?has_content> ${item.imageTitle}</#if>">
										<img src="<#if item.imageTitle?? && item.imageTitle?has_content> ${item.imageTitle}</#if>" alt="<#if imageAlt?? && imageAlt?has_content>${imageAlt}</#if>">
									</picture>
								</div>
								<@hst.html hippohtml=item.content var="htmlContent"/>
									<#if link?? && link?has_content>
										<a <#if link?? && link?has_content>href="${link}" target="${target}" <#if rel?has_content>rel="${rel}"</#if></#if>>
											<div class="koh-banner-content koh-valignment-middle">
												<span class="koh-banner-text" style="<#if colorCode?has_content>color:${colorCode}</#if>">
							                        <span class="koh-banner-title" style="<#if colorCode?has_content>color:${colorCode}</#if>"><#if item.title?? && item.title?has_content>${item.title?html}</#if></span>
													<span class="koh-banner-summary" style="<#if colorCode?has_content>color:${colorCode}</#if>"><#if htmlContent?? && htmlContent?has_content>${htmlContent}</#if></span>
													<#if link?? && link?has_content>
														<span class="koh-background-cta">
															<span class="koh-banner-cta">${buttonText}</span>
														</span>
													</#if>
												</span>
											</div>
										</a>
									<#else>
										<div class="koh-banner-content koh-valignment-middle">
											<span class="koh-banner-text" style="<#if colorCode?has_content>color:${colorCode}</#if>">
						                        <span class="koh-banner-title" style="<#if colorCode?has_content>color:${colorCode}</#if>"><#if item.title?? && item.title?has_content>${item.title?html}</#if></span>
												<span class="koh-banner-summary" style="<#if colorCode?has_content>color:${colorCode}</#if>"><#if htmlContent?? && htmlContent?has_content>${htmlContent}</#if></span>
												<#if link?? && link?has_content>
												<span class="koh-background-cta">
														<span class="koh-banner-cta">${buttonText}</span>
												</span>
												</#if>
											</span>
										</div>
									 </#if>	
							</div>
					 </#list>		
				</div>
			</div>
		</section>
</#if>
