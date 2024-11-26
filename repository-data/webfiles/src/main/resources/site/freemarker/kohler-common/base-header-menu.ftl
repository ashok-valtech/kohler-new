<#include "../include/imports.ftl">
<@hst.link var="storelink" siteMapItemRefId="storelocator"/>
<@hst.link var="contactuslink" siteMapItemRefId="contact-us"/>
<@fmt.message key="contact-us" var="contactUs"/>
<@fmt.message key="where-to-buy" var="whereToBuy"/>
<@fmt.message key="company-label" var="companyLabel"/>
<@fmt.message key="alternate_filter_cookie_name" var="alternateFilterCookieName"/>
<@fmt.message key="eshop-title" var="eshopTitle"/>
<@fmt.message key="eshop-link" var="eshopLink"/>
<@fmt.message key="VRshowroom-title" var="VRshowroomTitle"/>
<@fmt.message key="VRshowroom-link" var="VRshowroomeshopLink"/>
<@fmt.message key="service-title" var="serviceTitle"/>
<@fmt.message key="service-link" var="servicesLink"/>

<#assign currentSiteName=hstRequestContext.getResolvedMount().getMount().getName() />
<#assign parentSite="" />
<#if hstRequestContext.getResolvedMount().getMount().getParent()??>
	<#assign parentSite=hstRequestContext.getResolvedMount().getMount().getParent() />
<#else>
	<#assign parentSite=hstRequestContext.getResolvedMount().getMount() />
</#if>	
<#assign multiSiteFlag="no">	
<#list parentSite.getChildMounts() as mount>	
 	<#if mount.isMapped()?string('yes','no') == 'yes'>
  		<#assign multiSiteFlag="yes">	
	</#if>
</#list>
<@fmt.message key="cookie-name-reme" var="cookieNameReme"/>
<@fmt.message key="cookie-duration-reme" var="cookieDuration"/>
<#assign locale = hstRequest.getLocale().getLanguage() />
<#assign locale = locale + "_"+ hstRequest.getLocale().getCountry() />
<input id="compare-cookie-info-reme" type="hidden" data-cookie-duration="${cookieDuration}" data-cookie-name="${cookieNameReme}" data-cookie-value="${locale}" data-multisite="${multiSiteFlag}" data-alternate_filter_cookie_name="${alternateFilterCookieName}"/>
<div class="c-koh-branding-bar">
	<div class="koh-links">
		<ul class="koh-links-set-1">
			<#if VRshowroomTitle?? && VRshowroomTitle?has_content>
				<li><a href="<#if VRshowroomeshopLink?? && VRshowroomeshopLink?has_content>${VRshowroomeshopLink} </#if>" target="_blank"><span class="icon koh-icon-vrshowroom"></span>${VRshowroomTitle}</a></li>
			</#if>
			<#if serviceTitle?? && serviceTitle?has_content>
                <li><a href="<#if servicesLink?? && servicesLink?has_content>${servicesLink} </#if>" target="_self"><span class="koh-icon-service"></span>${serviceTitle}</a></li>
            </#if>
			<#if eshopTitle?? && eshopTitle?has_content>
				<li><a href="<#if eshopLink?? && eshopLink?has_content>${eshopLink} </#if>" target="_blank"><span class="icon koh-icon-estore"></span>${eshopTitle}</a></li>
			</#if>
		    <li><a href="${storelink}"><span class="icon"></span>${whereToBuy}</a></li>
		    <li><a href="${contactuslink}"><span class="icon koh-icon-envelope"></span>${contactUs}</a></li>
		</ul>
		
	    <ul class="koh-links-set-2"> 
		    	
    		<li><a class="koh-link-company-brand" href="" onclick="$('.koh-link-company-brand').toggleClass('open');">${companyLabel}</a></li>
		    
		    <#if multiSiteFlag == "yes">	
		    	<#assign currentSiteUrl = "/" />
		    	<#if currentSiteName != parentSite.getName()>
		    		<#assign currentSiteUrl = "/" + currentSiteName />
		    	</#if>
		      <li>
		      	 <a href="/" class="koh-link-language-left parent-mount <#if currentSiteName == parentSite.getName()>koh-link-language-active</#if>" data-locale="${parentSite.locale}" data-current-site-url="${currentSiteUrl}">${parentSite.getAlias()}</a>
			     <span class="koh-link-language-divider"></span>
		      	 <#list parentSite.getChildMounts() as mount>	
		      	 	<#if mount.isMapped()?string('yes','no') == 'yes'>
		      	 		<#sep><span class="koh-link-language-divider"></span></#sep>
			      		<a href="/${mount.getName()}" class="koh-link-language-left <#if currentSiteName == mount.getName()>koh-link-language-active</#if>" data-locale="${mount.locale}" data-current-site-url="${currentSiteUrl}">${mount.getName()}</a>
		      		</#if>
		      	 </#list>	
		      </li>
		   </#if>    
	    </ul>
	</div>
	<div class="koh-brands">
		<#if regionMenu??> 
			<div class="koh-worldwide-menu">
			    <ul class="koh-links-set-3">
		         <#list regionMenu.children as item>
			            <#if item.children?has_content>
					        <li class="worldwide-menu-top">
					            <span class="worldwide-menu-label">${item.siteMenuItem.name?html}</span>
					           <ul>
				                <#list item.children as subMenuItem>
			                      		<#if subMenuItem.menu??>
			                      			<#if subMenuItem.menu.menuLink?has_content>
			                      				<#assign target="_blank" />
			                      				<#assign rel="noopener noreferrer" />
			                      				<#assign href=subMenuItem.menu.menuLink />
				                      			<#if subMenuItem.menu.menuLink?starts_with("/")>
				                      				<#assign target="_self" />
				                      				<#assign rel="" />
				                      				<#assign href= href/>
	                      						</#if>
				                      		</#if>
				                      		<#if subMenuItem.menu.image??>	
					                      		<@hst.link var="img" hippobean=subMenuItem.menu.image/>
					                      	</#if>	
			                      			<li class="worldwide-menu-sub"><a href="${href}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>><#if img?has_content><span class="icon"><img src="${img}" /></span><#else><span>${subMenuItem.siteMenuItem.name?html}</span></#if></a></li>
										<#elseif subMenuItem.siteMenuItem.externalLink?has_content>
			                      			<li class="worldwide-menu-sub"><a href="${subMenuItem.siteMenuItem.externalLink}" target="_blank" rel="noopener noreferrer">${subMenuItem.siteMenuItem.name?html}</a></li>
			                      		<#elseif subMenuItem.siteMenuItem.hstLink?has_content>
			                      			<li class="worldwide-menu-sub"><a href="<@hst.link link=subMenuItem.siteMenuItem.hstLink/>">${subMenuItem.siteMenuItem.name?html}</a></li>	
			                      		<#else>
			                      			<li class="worldwide-menu-sub"><a>${subMenuItem.siteMenuItem.name?html}</a></li>	
			                      		</#if>
			                       </#list>
					            </ul>
					        </li>
					      </#if>
			         </#list>   
			    </ul>
			</div>
		</#if> 
		
		<#if brandingMenu??>
				<div class="koh-brands-header"></div>
				<div class="koh-brand-groupings">
					<#if brandingMenu.children?has_content>
						 	<#list brandingMenu.children as childrens>
						 		 <#if childrens.children?has_content>
					                  <#list childrens.children as item>
							            <#if item.children?has_content>
									       		<#list item.children as subMenuItem>
									       			<ul class="koh-brand-grouping">
							                      	  <#if subMenuItem.menu??>
							                      		<#if subMenuItem.menu.menuLink?has_content>
							                      				<#assign target="_blank" />
							                      				<#assign rel="noopener noreferrer" />
							                      				<#assign href=subMenuItem.menu.menuLink />
								                      			<#if subMenuItem.menu.menuLink?starts_with("/")>
								                      				<#assign target="_self" />
								                      				<#assign rel="" />
								                      				<#assign href= href/>
					                      						</#if>
								                      		<#if subMenuItem.menu.image?? && subMenuItem.menu.image?has_content>	
									                      		<@hst.link var="img" hippobean=subMenuItem.menu.image/>
                                                                <li><a href="${href}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>><span class="icon"><img src="${img}"/></span></a></li>
                                                            <#else>
                                                                <li><a href="${href}" target="${target}" rel="${rel}"><span>${subMenuItem.siteMenuItem.name?html}</span></a></li>     
									                      	</#if>
 														  <#else>
 														    <#if subMenuItem.menu.image?? && subMenuItem.menu.image?has_content>	
									                      		<@hst.link var="img" hippobean=subMenuItem.menu.image/>
                                                                <li><a><span class="icon"><img src="${img}"/></span></a></li>
                                                            <#else>
                                                                <li><a><span>${subMenuItem.siteMenuItem.name?html}</span></a></li>     
									                      	</#if>					
                                                          </#if>
							                      		<#elseif subMenuItem.siteMenuItem.externalLink?has_content>
							                      			<li><a href="${subMenuItem.siteMenuItem.externalLink}" target="_blank" rel="noopener noreferrer">${subMenuItem.siteMenuItem.name?html}</a></li>
							                      		<#elseif subMenuItem.siteMenuItem.hstLink?has_content>
							                      			<li><a href="<@hst.link link=subMenuItem.siteMenuItem.hstLink/>">${subMenuItem.siteMenuItem.name?html}</a></li>	
							                      		<#else>
							                      			<li><a>${subMenuItem.siteMenuItem.name?html}</a></li>	
							                      		</#if>
													</ul>
						                       </#list> 
										    </#if>
							         </#list>
							      </#if>   
							</#list>
						</#if> 
				</div>
		</#if>
	</div>
</div>
