<#include "../include/imports.ftl">
<#if pageable?? && pageable.items?has_content>
	<#if cparam.pause>
        <#assign pauseCarousel = 'hover'/>
    <#else>
        <#assign pauseCarousel = ''/>
    </#if>
    <div class="koh-carousel koh-theme-dark">
        <div class="koh-slide-collection">
            <#list pageable.items as item>
				<#assign colorCode="" />
				<#if item.textColor?? && item.textColor?has_content>
					<#assign colorCode=item.textColor />
				</#if>
			   <#assign link="" />
			   <#assign target="_blank" />
			   <#if item.navigationPath?? && item.navigationPath?has_content>
			   	   <#assign link=item.navigationPath?html />
				   <#if item.navigationPath?starts_with("/")>
          				<#assign target="_self" />
          				<#assign link=item.navigationPath/>
					</#if>
				</#if>	
	            <div class="koh-banner-slide v-koh-simple">
	                <div class="koh-banner-background">
	                <#if link?? && link?has_content>
	                    <a href="${link}" target="${target}">
	                        <img src="<#if item.imagePath?? && item.imagePath?has_content>${item.imagePath}</#if>" width="1800" height="800" alt="<#if item.title?? && item.title?has_content>${item.title}</#if>">
						</a>
					<#else>	
					<img src="<#if item.imagePath?? && item.imagePath?has_content>${item.imagePath}</#if>" width="1800" height="800" alt="<#if item.title?? && item.title?has_content>${item.title}</#if>">
					</#if>
	                </div>
	                <div class="koh-banner-overlay koh-theme-dark">
	                    <span class="koh-overlay-title">${item.title?html}</span>
	                    <span class="koh-overlay-text"><@hst.html hippohtml=item.content/></span>
	                </div>
	            </div>
			</#list>
        </div>
    </div>		    
</#if>



