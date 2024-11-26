<#include "../include/imports.ftl">

<@hst.include ref="container"/>

<#if pageable?? && pageable.items?has_content>
<@fmt.message key="bathroom-ideas" var="bathroomIdeas"/>
<@fmt.message key="bathroomIdeasMetaKeyword" var="bathroomIdeasMetaKeyword"/>
		<section class="c-koh-promo-grid v-koh-scattered">
			<h1 class="koh-promo-title" style="color: #000;">${bathroomIdeas}</h1>
			<div class="koh-promo-content">
				<div class="koh-promo-tiles">
					<#list pageable.items as item>
					   <#if item.title?? && item.title?has_content>
					    <#assign title = item.title?replace("(R)","<sup>&reg;</sup>")/>
						<#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
					    <#assign imageAlt = item.title?replace("(R)","&reg;")/>
						<#assign imageAlt = imageAlt?replace("(TM)","&trade;")/>
					   </#if>
					   <#if item.imageAlt?? && item.imageAlt?has_content>
	            		<#assign imgAlt=item.imageAlt>
	            		<#assign imgAlt = imgAlt?replace("(R)","&reg;")/>
						<#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
            	       </#if>
			           <#assign link="" />
					   <#assign target="_blank" />
					   <#assign rel="noopener noreferrer" />
					   <#if item.url??>
					   	   <#assign link=item.url?html />
						   <#if item.url?starts_with("/")>
		          				<#assign target="_self" />
		          				<#assign rel="" />
		          				<#assign link=item.url/>
							</#if>
						</#if>	
						<div class="koh-promo-tile">
				        	<a <#if link?has_content>href="${link}"</#if> target="${target}" <#if rel?has_content>rel="${rel}"</#if>>
				        		<div class="koh-promo-image">
						        	<#if item.imageUrl?? && item.imageUrl?has_content>
								      <img src="${item.imageUrl}" width="833" height="665" alt="${imgAlt}" <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if> >
								    </#if>
								</div>
						        <div class="koh-promo-content">
						        <#if item.title?? && item.title?has_content>
									<span class="koh-promo-title" style="">${title}</span>
								</#if>	
								<#if item.description?? && item.description?has_content>
								  <#assign description = item.description?replace("(R)","<sup>&reg;</sup>")/>
						          <#assign description = description?replace("(TM)","<sup>&trade;</sup>")/>
									<span class="koh-promo-description" style="">${description}</span>
								</#if>		
								</div>
					        </a>
						</div>
					</#list>
				</div>
			</div>
		</section>						
</#if>	
