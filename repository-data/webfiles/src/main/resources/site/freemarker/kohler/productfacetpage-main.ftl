<#include "../include/imports.ftl">
<@fmt.message key="carousel-link-button" var="carouselLinkButton"/>
<@fmt.message key="product-price-vat" var="productVatValue"/>
<@fmt.message key="product-price-multiply-value" var="productMultiplicationValue"/>

<#if hstRequestContext.getAttribute("categoryBanners")??> 
	<#assign categoryBanners = hstRequestContext.getAttribute("categoryBanners") />
</#if>

<input type="hidden" id="price-vat" value="${productVatValue}" />
<input type="hidden" id="multiply-value" value="${productMultiplicationValue}" />

<section class="c-koh-shopping-guide-carousel v-koh-hero">
	<div class="koh-carousel koh-theme-dark">
		<div class="koh-slide-collection">
		   <#if categoryBanners??>
            <#list categoryBanners as categoryBanner>
             <#assign colorCode="" />
				<#if categoryBanner?? && categoryBanner.textColor?has_content>
					<#assign colorCode=categoryBanner.textColor />
				</#if>
                 <#assign link="" />
				 <#assign target="_blank" />
			     <#assign rel="noopener noreferrer" />
			        <#if categoryBanner?? && categoryBanner.title?has_content>
					     <#assign title = categoryBanner.title?replace("(R)","<sub>&reg;</sub>")/>
						 <#assign title = title?replace("(TM)","<sub>&trade;</sub>")/>
						  <#assign imageAlt=title />
					</#if>  
					<#if categoryBanner?? && categoryBanner.imageAlt?has_content>
					  <#assign imageAlt=categoryBanner.imageAlt />
					</#if>
				   <#if categoryBanner.navigationPath?? && categoryBanner.navigationPath?has_content>
				   	   <#assign link=categoryBanner.navigationPath?html />
					   <#if categoryBanner.navigationPath?starts_with("/")>
	          				<#assign target="_self" />
	          				<#assign rel="" />
	          				<#assign link=categoryBanner.navigationPath/>
						</#if>
						
						<#assign buttonText= "${carouselLinkButton}" />
						<#if categoryBanner.buttonText?? && categoryBanner.buttonText?has_content>
							<#assign buttonText=categoryBanner.buttonText />
						</#if>
					</#if>
			 <div class="koh-banner-slide v-koh-simple m-koh-linked">
				<div class="koh-banner-background">
                  <#if link?? && link?has_content>
                      <a href="${link}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>
				      	<img src= "<#if categoryBanner.imagePath?? && categoryBanner.imagePath?has_content>${categoryBanner.imagePath}</#if>"  height="232" alt="${imageAlt}" <#if categoryBanner.imageTitle?? && categoryBanner.imageTitle?has_content> title="${categoryBanner.imageTitle}"</#if>>					
					  </a> 
				  <#else>
				  	  <img src= "<#if categoryBanner.imagePath?? && categoryBanner.imagePath?has_content>${categoryBanner.imagePath}</#if>"  height="232" alt="${imageAlt}" <#if categoryBanner.imageTitle?? && categoryBanner.imageTitle?has_content> title="${categoryBanner.imageTitle}"</#if>>
				  </#if> 
				</div>
				<#if link?? && link?has_content>
					<a <#if link?? && link?has_content>href="${link}" target="${target}" <#if rel?has_content>rel="${rel}"</#if></#if>>
						<div class="koh-banner-content koh-valignment-middle">
							<span class="koh-banner-text" style="<#if colorCode?has_content>color:${colorCode}</#if>">
		                        <span class="koh-banner-title" style="<#if colorCode?has_content>color:${colorCode}</#if>">${title}</span>
								<span class="koh-banner-summary" style="<#if colorCode?has_content>color:${colorCode}</#if>"><@hst.html hippohtml=categoryBanner.content/></span>
								<#if link?? && link?has_content><span class="koh-banner-cta">${buttonText}</span></#if>
							</span>
						</div>
					</a>
				<#else>
					<div class="koh-banner-content koh-valignment-middle">
						<span class="koh-banner-text" style="<#if colorCode?has_content>color:${colorCode}</#if>">
	                        <span class="koh-banner-title" style="<#if colorCode?has_content>color:${colorCode}</#if>">${title}</span>
							<span class="koh-banner-summary" style="<#if colorCode?has_content>color:${colorCode}</#if>"><@hst.html hippohtml=categoryBanner.content/></span>
							<#if link?? && link?has_content><span class="koh-banner-cta">${buttonText}</span></#if>
						</span>
					</div>
			  </#if>
			</div>
		</#list>
      </#if>							
	</div>
  </div>
</section>
<section class="c-koh-product-faceted-search v-koh-default">
	<@hst.include ref="container"/>
    <@fmt.message key="back-to-top" var="backTotop"/>
	<div class="koh-back-top">
      <button>
      <span class="icon" data-icon="&#xe613"></span>
       ${backTotop}
      </button>
   </div>
</section>
