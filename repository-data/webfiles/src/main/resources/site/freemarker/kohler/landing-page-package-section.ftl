<#include "../include/imports.ftl">

<#if document??>
	<section class="c-koh-ramadan-promotion-carousel">
		<div class = "koh-ramadan-promotion-container">
			<#if document.title.content?? && document.title.content?has_content>
	        	<div class="koh-ramadan-promotion-carousel-title"><@hst.html hippohtml=document.title/></div>
	        </#if>	
				<div class="koh-projects-pages-container-banner">
					<section class="c-koh-shopping-guide-carousel v-koh-article">
						<div class="koh-carousel koh-theme-dark">
							<div class="koh-slide-collection">
							<#if document.landingPagePackageCompund??>
								<#list document.landingPagePackageCompund as item>
									<#if (item.imageLink?? && item.imageLink?has_content) || (item.navigationLink?? && item.navigationLink?has_content)>
				            			<#assign navigationLink=item.navigationLink />
				            			<#assign target="_blank" />
				                        <#if navigationLink?starts_with( "/")>
				                            <#assign target="_self" />
				                        </#if>
	                    			</#if>
	                    			<#if item.imageLink?? && item.imageLink?has_content>
										<#if navigationLink?? && navigationLink?has_content>
											<a href="${navigationLink}"  target="${target}"> 
									            <div class="koh-banner-slide v-koh-inline">
									                <div class="koh-banner-background">
									                     <img src="${item.imageLink}" <#if item.imageAlt?? && item.imageAlt?has_content> alt="${item.imageAlt}"</#if> <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if>>
									                </div>
									            </div>
									        </a>
								        <#else>
								        	<div class="koh-banner-slide v-koh-inline">
								                <div class="koh-banner-background">
								                     <img src="${item.imageLink}" <#if item.imageAlt?? && item.imageAlt?has_content> alt="${item.imageAlt}"</#if> <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if>>
								                </div>
								            </div>
							            </#if>
						            </#if>
								</#list>
							</#if>
			  			</div>
			  			<#if document.promotionLink?? && document.promotionLink?has_content>
			  				<#assign promotionLink=document.promotionLink />
	            			<#assign target="_blank" />
	                        <#if promotionLink?starts_with( "/")>
	                            <#assign target="_self" />
	                        </#if>
	                        <a href="${promotionLink}"  target="${target}"> 
					   			<button id="koh-ramadan-promotion-broucher"  class="btn btn-default"><#if document.promotionButton?? && document.promotionButton?has_content>${document.promotionButton}</#if></button>
					   		</a>	
				   		</#if>
		    		</div>		    
	     		</section>
			</div>
		</div>			
	</section>
</#if>

