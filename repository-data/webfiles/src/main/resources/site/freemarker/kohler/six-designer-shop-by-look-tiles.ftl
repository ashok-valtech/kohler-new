<#include "../include/imports.ftl">
	<#if pageable?? && pageable.items?has_content>
        <section class="v-koh-full-page container-comp container-bg background-grey">
        	<#list pageable.items as item>
	            <div class="tile shop-tile">
	                <div class="thumbnail-container">
	                	<#if item.imagePath?? && item.imagePath?has_content>
	                    	<img src="${item.imagePath}" alt="" class="bg-img"/>
                    	</#if>
						<div class="tile-title">
							<#if item.title?? &&item.title?has_content>
								<p>${item.title}</p>
							</#if>
							<#if item.navigationPath?? && item.navigationPath?has_content>
								<#assign link=item.navigationPath?html />
								<#assign target="_blank" />
								<#if link?starts_with( "/")>
									<#assign target="_self" />
								</#if>
							</#if>
							<#if link?? && link?has_content>
								<a class="tile-cta" href="${link}" target="${target}">
									<#if item.buttonText?? && item.buttonText?has_content>
										${item.buttonText}
									</#if>
								</a>
							</#if>
						</div>
	                    <div class="gradient-container"></div>
	                </div>
	            </div>
            </#list>
         </section>
    </#if>

<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/c-koh-six-design-campaign.min.js"></script>
</@hst.headContribution>