<#include "../include/imports.ftl">

<#if document??>
	<#if document.kohler_articledescriptivecarousaldocument??>
		<div class= "koh-fb-buy-parts-left-section">
			<#list document.kohler_articledescriptivecarousaldocument as item>
			  <div class="row koh-fb-buy-parts-left-section-row">
		    	<div class="col-sm-6 koh-fb-buy-parts-left-section-img">
					<#if item.carousalImageURL?? && item.carousalImageURL?has_content>
	                	<img src="${item.carousalImageURL}" <#if item.carousalImageTitle?? && item.carousalImageTitle?has_content> alt="${item.carousalImageTitle}"</#if>>
	                </#if>
	            </div>
				<div class="col-sm-6 koh-fb-buy-parts-left-section-title">
					<div class="koh-fb-buy-parts-left-section-title-inner">
						<#if item.carousalImageTitle?? && item.carousalImageTitle?has_content>
	                       <#assign title = item.carousalImageTitle?replace("(R)","<sup>&reg;</sup>")/>
						   <#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
	                    </#if>
						<#if title?? && title?has_content>
							<div class= "koh-fb-buy-parts-left-section-first-title">${title}</div>
						</#if>
			            <#if item.description.content?? && item.description.content?has_content>
							<div class="koh-fb-buy-parts-left-section-description"><@hst.html hippohtml=item.description/></div>
						</#if>
						<div class="koh-contact-information">
							<ul>
								<#if item.learnMoreLink?? && item.learnMoreLink?has_content>
			                		<#assign learnMoreLink = item.learnMoreLink />
			                		<#assign target="_blank" />
	                                <#if learnMoreLink?starts_with( "/")>
	                                    <#assign target="_self" />
	                                </#if>
                                </#if>
                                <#if learnMoreLink?? && learnMoreLink?has_content>
								  <li>
									  <a href="${learnMoreLink}" target="${target}">
									    <img src="<#if item.viewAllLabel?? && item.viewAllLabel?has_content>${item.viewAllLabel}</#if>">
								    	<#if item.learnMoreLabel?? && item.learnMoreLabel?has_content>
										    <div class="koh-shop-now-title">
										   	 	${item.learnMoreLabel}
										   	 </div>
								   	 	</#if>
									  </a>
								   </li>
							   </#if>
							   <#if item.viewAllLink?? && item.viewAllLink?has_content>
			                		<#assign viewAllLink = item.viewAllLink />
			                		<#assign target="_blank" />
	                                <#if viewAllLink?starts_with( "/")>
	                                    <#assign target="_self" />
	                                </#if>
                              </#if>
                              <#if viewAllLink?? && viewAllLink?has_content>
								  <li>
								 	<a href="${viewAllLink}" target="${target}">
								 		<img src="<#if item.logoImageURL1?? && item.logoImageURL1?has_content>${item.logoImageURL1}</#if>">
								 		<#if item.logoImageURL2?? && item.logoImageURL2?has_content>
									 		<div class="koh-shop-now-title">
									 			${item.logoImageURL2}
								 			</div>
							 			</#if>
						 			</a>
				 				  </li>
			 				  </#if>
			 				  <#if item.carousalImageAlt?? && item.carousalImageAlt?has_content>
								  <li>
								  	<a href="tel:${item.carousalImageAlt}">
								  		<img src="<#if item.navigationLink?? && item.navigationLink?has_content>${item.navigationLink}</#if>">
								  		<div class="koh-shop-now-title">
								  			${item.carousalImageAlt}
								  		</div>
							  		</a>
						  		  </li>
					  		  </#if>
							</ul>
						</div>
					</div>
				</div>
			</#list>
		</div>
	</#if>
</#if>