<#include "../include/imports.ftl">

<#if document??>
	<div class="koh-contemporary-pdp-item-details">
		<#if document.title?? && document.title?has_content>
			<div class="koh-item-details-header">
				${document.title}
			</div>
		</#if>
		<div class ="koh-item-details-container"> 
			<#if document.kohler_articledescriptivecarousaldocument??>
				<#list document.kohler_articledescriptivecarousaldocument as item>
					<#if item.learnMoreLink?? && item.learnMoreLink?has_content>
						<#assign moreLink = item.learnMoreLink>
						<#assign target="_blank" />
                        <#if moreLink?starts_with( "/")>
                            <#assign target="_self" />
                        </#if>
					</#if>
					<div class="koh-item-details-card-list">
						<#if item.carousalImageURL?? && item.carousalImageURL?has_content>
                        	<img src="${item.carousalImageURL}" <#if item.carousalImageAlt?? && item.carousalImageAlt?has_content> alt="${item.carousalImageAlt}"</#if>>
	                    </#if>
						<div class="koh-item-details-card-info">
							<#if item.carousalImageTitle?? && item.carousalImageTitle?has_content>
		                       <#assign title = item.carousalImageTitle?replace("(R)","<sup>&reg;</sup>")/>
							   <#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
		                    </#if>
	           				<#if title?? && title?has_content>
	              				<div class="koh-item-details-title">${title}</div>
	              			</#if>
	              			<#if item.viewAllLabel?? && item.viewAllLabel?has_content>
								<div class="koh-item-details-itemno">${item.viewAllLabel}</div>
							</#if>
							<#if item.description.content?? && item.description.content?has_content>
	              				<div class="koh-item-details-desc"><@hst.html hippohtml=item.description/></div>
	          				</#if>
	          				<#if item.learnMoreLabel?? && item.learnMoreLabel?has_content>
	          					<a href="${moreLink}" target="${target}">
									<div class="koh-item-more">${item.learnMoreLabel}</div>
								</a>
							</#if>
	      				</div>
	         		</div>
	     		</#list>
	 		</#if>
 		</div>
	</div>
</#if>
