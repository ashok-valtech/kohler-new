<#include "../include/imports.ftl">

<#if pageable?? && pageable.items?has_content>
	<#if cparam.pause>
        <#assign pauseCarousel = 'hover'/>
    <#else>
        <#assign pauseCarousel = ''/>
    </#if>
		<section class="c-koh-shopping-guide-carousel v-koh-hero">
			<div class="koh-carousel koh-theme-dark">
				<div class="koh-slide-collection">
					
					<#list pageable.items as item>
							<#assign colorCode="" />
							<#if item.textColor?? && item.textColor?has_content>
								<#assign colorCode=item.textColor />
							</#if>
							<#if item.title?? && item.title?has_content>
								<#assign title=item.title />
							</#if>
							<#if item.imageAlt?? && item.imageAlt?has_content>
								<#assign imageAlt=item.imageAlt />
							<#else>
								<#assign imageAlt=title />
							</#if>
						   <#assign CTALink="" />
						   <#assign target="_blank" />
					       <#assign rel="noopener noreferrer" />
						   <#if item.CTALink?? && item.CTALink?has_content>
						   	   <#assign CTALink=item.CTALink?html />
							   <#if item.CTALink?starts_with("/")>
			          				<#assign target="_self" />
			          				<#assign rel="" />
			          				<#assign CTALink=item.CTALink/>
								</#if>
							</#if>
							
							<#if item.CTAButtonText?? && item.CTAButtonText?has_content>
								<#assign buttonText=item.CTAButtonText />
							</#if>
							<div class="koh-banner-slide v-koh-simple m-koh-linked">
								<div class="koh-banner-background">
								  	  <img src= "<#if item.imagePath?? && item.imagePath?has_content>${item.imagePath}</#if>"  height="232" alt="${imageAlt}" <#if item.imageTitle?? && item.imageTitle?has_content>title="${item.imageTitle}"</#if>>
								</div>
								<@hst.html hippohtml=item.description var="htmlContent"/>
								<#if ((title?? || htmlContent??) && (title?has_content || htmlContent?has_content))>
									<div class="koh-banner-content koh-valignment-middle">
										<span class="koh-banner-text" style="<#if colorCode?has_content>color:${colorCode}</#if>">
					                        <span class="koh-banner-title" style="<#if colorCode?has_content>color:${colorCode}</#if>">${title?html}</span>
											<span class="koh-banner-summary" style="<#if colorCode?has_content>color:${colorCode}</#if>">${htmlContent}</span>
											<#if CTALink?? && CTALink?has_content>
												 <a href="${CTALink}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>
													<span class="koh-banner-cta" style="<#if colorCode?has_content>color:${colorCode}</#if>">${buttonText}</span>
												 </a>
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
