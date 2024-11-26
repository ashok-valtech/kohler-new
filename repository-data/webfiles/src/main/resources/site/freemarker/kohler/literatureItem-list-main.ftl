<#include "../include/imports.ftl">
<@fmt.message key="literature-title" var="literatureTitle"/>

<#if pageable?? && pageable.items?has_content>
		<section class="c-koh-promo-grid v-koh-scattered">
			<h1 class="koh-promo-title" style="color: #000;">${literatureTitle}</h1>
			<div class="koh-promo-content">
				<div class="koh-promo-tiles">
					<#list pageable.items as item>
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
							<@hst.manageContent hippobean=item/>
				        	<a
				        		<#if item.relatedFile?? && item.relatedFile?has_content>
				        			href="<@hst.link hippobean=item.relatedFile/>"
				        	    <#elseif item.pdfUrl?? && item.pdfUrl?has_content>
				        	        href="${item.pdfUrl}"
				        	    </#if>
				        	   target="_blank" rel="noopener noreferrer">
				        		<div class="koh-promo-image">
						        	<#if item.imageUrl?? && item.imageUrl?has_content>
								      <img src="${item.imageUrl}" width="833" height="665" alt="${imgAlt}" <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if>>
								    </#if>
								</div>
						        <div class="koh-promo-content">
						        <#if item.title?? && item.title?has_content>
									<span class="koh-promo-title" style="">${title}</span>
								</#if>
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
	<#if cparam.showPagination>
      <#include "../include/pagination.ftl">
    </#if>
</#if>

<style>
	.koh-search-pagination ul {
	margin: 20px 0 20px;
	}
	.koh-search-pagination {
	background: #e5e5e5;
	}
	.koh-search-pagination ul li:first-child {
	border-left: 1px solid #fff;
	}
	.koh-search-pagination ul li {
	border: 1px solid #fff;
	    border-left:0;
	}
</style>
