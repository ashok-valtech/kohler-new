<#include "../include/imports.ftl">

<#if pageable?? && pageable.items?has_content>
		<section class="c-koh-promo-grid v-koh-scattered">
			<div class="koh-promo-content">
				<div class="koh-promo-tiles">
					<#list pageable.items as item>
					  <#assign title=""/>	
					  <#if item.title?? && item.title?has_content>
					    <#assign title = item.title?replace("(R)","<sup>&reg;</sup>")/>
						<#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
					    <#assign imageAlt = item.title?replace("(R)","&reg;")/>
						<#assign imageAlt = imageAlt?replace("(TM)","&trade;")/>
					  </#if>
					     <#if item.imageAlt?? &&item.imageAlt?has_content>
			          	    <#assign imageAlt=item.imageAlt/>
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
				        		 <a <#if hstLink?? && hstLink?has_content>href="${hstLink}"</#if> target="${target}" <#if rel?has_content>rel="${rel}"</#if>>
						          </#if>
						    	<div class="koh-promo-image">
								 <#if item.imagePath?? && item.imagePath?has_content>
								   <#assign imgLink=item.imagePath/>
								   <img src="${imgLink}" width="300" height="232" alt="${imageAlt}" <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if>> 
								 </#if>
								</div>
								<div class="koh-promo-content">
									<span class="koh-promo-title" style="">${title}</span>
								 <#if item.introduction?? && item.introduction?has_content>
			                         <#assign introduction = item.introduction?replace("(R)","<sup>&reg;</sup>")/>
									 <#assign introduction = introduction?replace("(TM)","<sup>&trade;</sup>")/>
									  <span class="koh-promo-description" style="">${introduction}</span>
								 </#if>	
								</div>
						   </a>
						</div>
					</#list>
				</div>
			</div>
		</section>						
</#if>
