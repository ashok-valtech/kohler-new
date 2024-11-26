<#include "../include/imports.ftl">

<#if pageable?? && pageable.items?has_content>
		<section class="c-koh-promo-grid v-koh-scattered koh-img-tit-desc-link-comp">
			<div class="koh-promo-content">
				<div class="koh-promo-tiles">
					<#list pageable.items as item>
					  <#assign title=""/>	
					  <#if item.title?? && item.title?has_content>
					    <#assign title = item.title?replace("(R)","<sup>&reg;</sup>")/>
						<#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
					    <#assign imgAlt = item.title?replace("(R)","&reg;")/>
		                <#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
					  </#if>
					    <#if item.imageAlt?? &&item.imageAlt?has_content>
			          	    <#assign imgAlt=item.imageAlt/>
						</#if>
						<div class="koh-promo-tile">
						      <#if item.hstUrl??>
						         <#assign hstLink=item.hstUrl />
					           	 <#assign target="_blank" />
					           	 <#assign rel="noopener noreferrer" />
					           	 <#if hstLink?starts_with("/")>
						           <#assign target="_self" />
						           <#assign rel="" />
			                   	 </#if>
				        		 <div class="koh-promo-tile-box">
						          </#if>
									<div class="koh-promo-image">
										<#if hstLink?? && hstLink?has_content>	
										   <a class="koh-tile-img-link" href="${hstLink}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>
											<#if item.imagePath?? && item.imagePath?has_content>
												<#assign imgLink=item.imagePath/>
												<img src="${imgLink}" width="300" height="232" alt="${imgAlt}" <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if>> 
											</#if>
		                                   </a>
										<#else>
										<#if item.imagePath?? && item.imagePath?has_content>
											<#assign imgLink=item.imagePath/>
											<img src="${imgLink}" width="300" height="232" alt="${imgAlt}" <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if>> 
										</#if>
									  </#if>	
									</div>
									<div class="koh-promo-content-txt">
										<h3 class="koh-promo-title">${title}</h3>
										<#if item.description?? && item.description?has_content>
										<#assign description = item.description?replace("(R)","<sup>&reg;</sup>")/>
										<#assign description = description?replace("(TM)","<sup>&trade;</sup>")/>
										<p class="koh-promo-description" style="">${description}</p>
										</#if>
									<#if item.navigationTitle?? && item.navigationTitle?has_content>
									   <#if hstLink?? && hstLink?has_content>	
										<a href="${hstLink}" target="${target}">${item.navigationTitle}</a>
									   </#if>	
								    </#if>		
									</div>
							    </div>
						</div>
					</#list>
				</div>
			</div>
		</section>						
</#if>