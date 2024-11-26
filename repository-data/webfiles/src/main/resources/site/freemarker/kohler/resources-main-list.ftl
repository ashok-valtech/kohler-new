<#include "../include/imports.ftl">
<@fmt.message key="resources-cap" var="resourcesC"/>
<@fmt.message key="resourcesMetaDesc" var="resourcesMetaDesc"/>

<#if pageable?? && pageable.items?has_content>
         <@fmt.message key="resources" var="resources"/>
		<section class="c-koh-promo-grid v-koh-scattered">
			<h2 class="koh-promo-title" style="color: #000;">${resourcesC}</h2>
			<div class="koh-promo-content">
				<div class="koh-promo-tiles">
					<#list pageable.items as item>
						<#if item?? && item?has_content>
						   <div class="koh-promo-tile">
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
					        	<a href="${link}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>
						        	<div class="koh-promo-image">
							        	<#if item.imageUrl?? && item.imageUrl?has_content>
									      <img src="${item.imageUrl}" width="300" height="232" alt="${item.title?html}">
									    </#if>
									</div>
							        <div class="koh-promo-content">
										<span class="koh-promo-title" style=""><#if item.title?? && item.title?has_content>${item.title?html}</#if></span>
									</div>
								</a>
							</div>
						</#if>	
					</#list>
				</div>
			</div>
		</section>						
</#if>
<@fmt.message key="company.name" var="companyName"/>	
<@fmt.message key="resources" var="resources"/>
<@hst.headContribution category="title">
	<title>${resources} | ${companyName}</title>
</@hst.headContribution>	

<@hst.headContribution category="meta-tags">
	<meta name="description" content="${resourcesMetaDesc}"/>
</@hst.headContribution>

