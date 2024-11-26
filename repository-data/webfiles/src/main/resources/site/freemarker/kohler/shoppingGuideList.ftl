<#include "../include/imports.ftl">
<#if document??>
	<#if layout??>
		<#if layout == "Two Column">
			<#assign c_koh_shopping_list_promo="c-koh-shopping-list-2promo" />
			<#assign koh_shopping_list_promo="row koh-shopping-list-2promo" />
			<#assign koh_undeline = "koh-underline-2promo" />
			<#assign koh_shopping_guide_promo="koh-shopping-guide-2promo" />
			<#assign koh_shopping_item_promo="koh-shopping-item-2promo" />
			<#assign koh_shopping_promo_title_format="koh-shopping-2promo-title-format" />
			<#assign koh_shopping_promo_desc_format="koh-shopping-2promo-desc-format" />
		<#elseif layout == "Three Column">
			<#assign c_koh_shopping_list_promo="c-koh-shopping-list-3promo" />
			<#assign koh_shopping_list_promo="row koh-shopping-list-3promo" />
			<#assign koh_undeline = "koh-underline-3promo" />
			<#assign koh_shopping_guide_promo="col-lg-4 col-md-4 col-sm-4 col-xs-12 koh-shopping-guide-3promo" />
			<#assign koh_shopping_item_promo="koh-shopping-item-3promo" />
			<#assign koh_shopping_promo_title_format="koh-shopping-3promo-title-format" />
			<#assign koh_shopping_promo_desc_format="koh-shopping-3promo-desc-format" />
		</#if>
		<section class="${c_koh_shopping_list_promo}">
				<#if document.title?? && document.title?has_content>
      				<div class="koh-head-wrapper">
      					<h2>${document.title}</h2>
      				</div>
  				</#if>
  				<div class="${koh_shopping_list_promo}">
  					<div class = "koh-shopping-guide-container"> 
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
				         		<div class="${koh_shopping_guide_promo}">
									<figure class="${koh_shopping_item_promo}">
										<#if navigationLink?? && navigationLink?has_content>
					            			<a href="${navigationLink}"  target="${target}">
					            				<img src="<#if shoppingGuide.imageURL?? && shoppingGuide.imageURL?has_content>${shoppingGuide.imageURL}</#if>" <#if imageAlt?? && imageAlt?has_content>alt="${imageAlt}"</#if> <#if shoppingGuide.imageTitle?? && shoppingGuide.imageTitle?has_content>title="${shoppingGuide.imageTitle}"</#if>>
					            			</a>
				            			<#else>
											<img src="<#if shoppingGuide.imageURL?? && shoppingGuide.imageURL?has_content>${shoppingGuide.imageURL}</#if>" <#if imageAlt?? && imageAlt?has_content>alt="${imageAlt}"</#if> <#if shoppingGuide.imageTitle?? && shoppingGuide.imageTitle?has_content>title="${shoppingGuide.imageTitle}"</#if>>
										</#if>
			               				<#if shoppingGuideTitle?? && shoppingGuideTitle?has_content>
			                  				<div class="${koh_shopping_promo_title_format}">${shoppingGuideTitle}</div>
			                  			</#if>
										<#if shoppingGuide.description?? && shoppingGuide.description?has_content>
			                  				<div class="${koh_shopping_promo_desc_format}">${shoppingGuide.description}</div>
		                  				</#if>
				            		</figure>
				         		</div>
			         		</#list>
		         		</#if>
	         		</div>
				</div>
				<#if hrLine??>
					<#if hrLine == true>
						<div class="${koh_undeline}"></div>
					</#if>
				</#if>
		</section>
	</#if>
</#if>

<@hst.headContribution category="ext-scripts">
	<@hst.webfile path="/" var="link" />
	<script src="${link}/js/custom/c-koh-shopping-guide-responsive-carousel.min.js" type="text/javascript"></script>
</@hst.headContribution>
