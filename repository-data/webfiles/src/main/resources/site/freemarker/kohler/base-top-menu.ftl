<#include "../include/imports.ftl">
<@fmt.message key="show-navigation" var="showNavigation"/>
<@fmt.message key="hide-navigation" var="hideNavigation"/>
<@fmt.message key="show-search" var="showSearch"/>
<@fmt.message key="hide-search" var="hideSearch"/>
<@fmt.message key="show-search" var="showSearch"/>
<@fmt.message key="keywords" var="keywords"/>
<@fmt.message key="search" var="search"/>
<@fmt.message key="cookie-max-items-message" var="cookieMaxItemsMessage"/> 
<@fmt.message key="cookie-diff-category-message" var="cookieDiffCategoryMessage"/>
<@fmt.message key="cookie-category" var="cookieCategory"/>
<@fmt.message key="cookie-name" var="cookieName"/>
<@fmt.message key="home-menu-count-number" var="homeMenuCountNumber"/>
<@fmt.message key="ideas-menu-count-number" var="ideasMenuCountNumber"/>
<@fmt.message key="responsive-header-menu-prefix" var="responsiveHeaderMenuPrefix"/>
<@hst.link var="storelink" siteMapItemRefId="storelocator"/>
<@hst.link var="contactuslink" siteMapItemRefId="contact-us"/>
<@fmt.message key="contact-us" var="contactUs"/>
<@fmt.message key="where-to-buy" var="whereToBuy"/>
<@fmt.message key="company-label" var="companyLabel"/>
<@fmt.message key="eshop-title" var="eshopTitle"/>
<@fmt.message key="eshop-link" var="eshopLink"/>
<@fmt.message key="VRshowroom-title" var="VRshowroomTitle"/>
<@fmt.message key="VRshowroom-link" var="VRshowroomeshopLink"/>
<@fmt.message key="service-title" var="serviceTitle"/>
<@fmt.message key="service-link" var="servicesLink"/>
<#assign locale = hstRequest.getLocale().getLanguage() />
<#assign locale = locale + "_"+ hstRequest.getLocale().getCountry() />
<div class="c-koh-navigation-bar">
	<div class="c-koh-navigation">
	<#if menu??>
	<#assign imageAlt="The Bold Look of Kohler" />
	<div class="has-edit-button">
	  <#if siteMenus??>
	  	<#assign homeMenu=siteMenus.children[2]>
      	<#if homeMenu?? && homeMenu?has_content>
        	<#if homeMenu.menu??>
                 <#if homeMenu.menu.image?? && homeMenu.menu.image?has_content>
                        <@hst.link var="homeLogo" hippobean=homeMenu.menu.image.original/> 
                  </#if>
                   <#if homeMenu.menu.imageAlt?? && homeMenu.menu.imageAlt?has_content>
                        <#assign imageAlt=homeMenu.menu.imageAlt />
                  </#if>
          	</#if> 
        </#if> 
		<nav class="koh-primary-nav">
			<div class="koh-primary-nav-header">
			   <@hst.link var="suggest" siteMapItemRefId="suggest"/>
			   <input type="hidden" value="${suggest}" name="suggest" class="koh-primary-nav-suggest-link"/>
			   <@hst.link var="homeLink" siteMapItemRefId="root"/>
				<a class="koh-primary-nav-home-link" href="${homeLink}">
					<img src="${homeLogo}" width="141" height="80" alt="${imageAlt}" <#if homeMenu.menu.imageTitle?? && homeMenu.menu.imageTitle?has_content> title="${homeMenu.menu.imageTitle}"</#if> >
				</a>
				<button type="button" class="koh-primary-nav-toggle koh-mobile-nav">
					<span class="label" data-label-off="${showNavigation}" data-label-on="${hideNavigation}">${showNavigation}</span>
					<span class="icon" data-icon="&#xe614"></span>
					<span class="cross-icon-nav">
						<svg width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="https://www.w3.org/2000/svg">
							<path d="M9.13305 8L15.1203 2.01278C15.2386 1.87462 15.3004 1.6969 15.2934 1.51513C15.2864 1.33337 15.211 1.16094 15.0824 1.03232C14.9538 0.903695 14.7814 0.828344 14.5996 0.821323C14.4178 0.814302 14.2401 0.876129 14.1019 0.994448L8.11471 6.98167L2.12749 0.987226C1.99149 0.851229 1.80704 0.774826 1.61471 0.774826C1.42238 0.774826 1.23793 0.851229 1.10194 0.987226C0.965938 1.12322 0.889536 1.30767 0.889536 1.5C0.889536 1.69233 0.965938 1.87678 1.10194 2.01278L7.09638 8L1.10194 13.9872C1.02633 14.052 0.964928 14.1316 0.921578 14.2213C0.878228 14.3109 0.853867 14.4084 0.850025 14.5079C0.846184 14.6074 0.862943 14.7066 0.899253 14.7992C0.935562 14.8919 0.990637 14.9761 1.06102 15.0465C1.13141 15.1169 1.21558 15.1719 1.30826 15.2082C1.40094 15.2446 1.50012 15.2613 1.59959 15.2575C1.69905 15.2536 1.79664 15.2293 1.88625 15.1859C1.97585 15.1426 2.05552 15.0812 2.12027 15.0056L8.11471 9.01834L14.1019 15.0056C14.2401 15.1239 14.4178 15.1857 14.5996 15.1787C14.7814 15.1717 14.9538 15.0963 15.0824 14.9677C15.211 14.8391 15.2864 14.6666 15.2934 14.4849C15.3004 14.3031 15.2386 14.1254 15.1203 13.9872L9.13305 8Z" fill="black" fill-opacity="0.8"/>
						</svg>
					</span>
				</button>
				<button type="button" class="koh-primary-search-toggle koh-mobile-nav">
					<span class="label" data-label-off="${showSearch}"data-label-on="${hideSearch}">${showSearch}</span>
					<span class="icon" data-icon="&#xe608"></span>
					<span class="cross-icon-search">
						<svg width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="https://www.w3.org/2000/svg">
							<path d="M9.13305 8L15.1203 2.01278C15.2386 1.87462 15.3004 1.6969 15.2934 1.51513C15.2864 1.33337 15.211 1.16094 15.0824 1.03232C14.9538 0.903695 14.7814 0.828344 14.5996 0.821323C14.4178 0.814302 14.2401 0.876129 14.1019 0.994448L8.11471 6.98167L2.12749 0.987226C1.99149 0.851229 1.80704 0.774826 1.61471 0.774826C1.42238 0.774826 1.23793 0.851229 1.10194 0.987226C0.965938 1.12322 0.889536 1.30767 0.889536 1.5C0.889536 1.69233 0.965938 1.87678 1.10194 2.01278L7.09638 8L1.10194 13.9872C1.02633 14.052 0.964928 14.1316 0.921578 14.2213C0.878228 14.3109 0.853867 14.4084 0.850025 14.5079C0.846184 14.6074 0.862943 14.7066 0.899253 14.7992C0.935562 14.8919 0.990637 14.9761 1.06102 15.0465C1.13141 15.1169 1.21558 15.1719 1.30826 15.2082C1.40094 15.2446 1.50012 15.2613 1.59959 15.2575C1.69905 15.2536 1.79664 15.2293 1.88625 15.1859C1.97585 15.1426 2.05552 15.0812 2.12027 15.0056L8.11471 9.01834L14.1019 15.0056C14.2401 15.1239 14.4178 15.1857 14.5996 15.1787C14.7814 15.1717 14.9538 15.0963 15.0824 14.9677C15.211 14.8391 15.2864 14.6666 15.2934 14.4849C15.3004 14.3031 15.2386 14.1254 15.1203 13.9872L9.13305 8Z" fill="black" fill-opacity="0.8"/>
						</svg>
					</span>
                </button>
            </div>
            <div class="koh-primary-nav-menu">
             <ul class="koh-nav-parents ${locale}">
              <#assign menuCounterMain = 0>
                 <#list siteMenus.children as item>
                  <#assign menuCounterMain++>
                    <#if menuCounterMain == homeMenuCountNumber?number> 
                      <#else>
                        <li>
                          <#if item.menu??>
                             <#if item.menu.menuLink?has_content>
                                <#assign target="_blank" />
                                <#assign rel="noopener noreferrer" />
                                <#assign href=item.menu.menuLink />
                                <#if item.menu.menuLink?starts_with("/")>
                                    <#assign target="_self" />
                                    <#assign rel="" />
                                    <#assign href=href/>
                                </#if>
                              </#if>
                                  <a class="single" href="${href}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>${item.siteMenuItem.name?html}</a>
                           <#elseif item.siteMenuItem.externalLink?has_content>
                                  <a class="single" href="${item.siteMenuItem.externalLink}" target="_blank">${item.siteMenuItem.name?html}</a>
                           <#elseif item.siteMenuItem.hstLink?has_content>
                                  <a class="single" href="<@hst.link link=item.siteMenuItem.hstLink/>">${item.siteMenuItem.name?html}</a>
                           <#else>    
                                  <a href="">${item.siteMenuItem.name?html}</a>    
                           </#if>    
                            <div class="koh-nav-subnav">
                                <div class="koh-subnav-content">
                                 <#assign menuCounter=0>
                                        <#if item.children?has_content>
                                              <#list item.children as subItem>
                                                    <div class="koh-subnav-header">
                                                        <#if subItem.siteMenuItem.externalLink?has_content>
                                                            <a href="${subItem.siteMenuItem.externalLink}" target="_blank" rel="noopener noreferrer"><span class="prefix">${responsiveHeaderMenuPrefix}&nbsp</span> ${subItem.siteMenuItem.name?html}</a>
                                                        <#elseif subItem.siteMenuItem.hstLink?has_content>
                                                            <a href="<@hst.link link=subItem.siteMenuItem.hstLink/>"><span class="prefix">${responsiveHeaderMenuPrefix}&nbsp</span>${subItem.siteMenuItem.name?html}</a>    
                                                        <#else>
                                                            <a><span class="prefix">${responsiveHeaderMenuPrefix}&nbsp</span>${subItem.siteMenuItem.name?html}</a>  
                                                        </#if>
                                                    </div>
                                                    <#if menuCounterMain==ideasMenuCountNumber?number>
                                                       <#if subItem.children?has_content>
                                                         <#list subItem.children as subItem1>
                                                            <ul class="koh-nav-section koh-nav-section-three-columns">
                                                                <#if subItem1.menu??>
                                                                    <#if subItem1.menu.menuLink?has_content>
                                                                        <#assign target="_blank" />
                                                                        <#assign rel="noopener noreferrer" />
                                                                        <#assign href=subItem1.menu.menuLink />
                                                                        <#if subItem1.menu.menuLink?starts_with("/")>
                                                                            <#assign target="_self" />
                                                                            <#assign rel="" />
                                                                            <#assign href=href/>
                                                                        </#if>
                                                                    </#if>
                                                                    <li class="nav-heading"><a href="${href}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>${subItem1.siteMenuItem.name?html}</a></li>
                                                                <#elseif subItem1.siteMenuItem.hstLink??>   
                                                                    <li class="nav-heading"><a href="<@hst.link link=subItem1.siteMenuItem.hstLink/>">${subItem1.siteMenuItem.name?html}</a></li>
                                                                <#elseif subItem1.siteMenuItem.externalLink??>
                                                                    <li class="nav-heading"><a href="${subItem1.siteMenuItem.externalLink }" target="_blank" rel="noopener noreferrer">${subItem1.siteMenuItem.name?html}</a></li>
                                                                <#else>
                                                                    <li class="nav-heading"><a>${subItem1.siteMenuItem.name?html}</a></li>
                                                                </#if>
                                                                <#if subItem1.children?has_content>
                                                                  <#list subItem1.children as subItem2>
                                                                    <#if subItem2.menu??>
                                                                        <#if subItem2.menu.menuLink?has_content>
                                                                            <#assign target="_blank" />
                                                                            <#assign rel="noopener noreferrer" />
                                                                            <#assign href=subItem2.menu.menuLink />
                                                                            <#if subItem2.menu.menuLink?starts_with("/")>
                                                                                <#assign target="_self" />
                                                                                <#assign rel="" />
                                                                                <#assign href= href/>
                                                                            </#if>
                                                                        </#if>
                                                                    <li class="nav-item"><a href="${href}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>${subItem2.siteMenuItem.name?html}</a></li>
                                                                    <#elseif subItem2.siteMenuItem.hstLink??>   
                                                                        <li class="nav-item"><a href="<@hst.link link=subItem2.siteMenuItem.hstLink/>">${subItem2.siteMenuItem.name?html}</a></li>
                                                                    <#elseif subItem2.siteMenuItem.externalLink??>
                                                                        <li class="nav-item"><a href="${subItem2.siteMenuItem.externalLink }" target="_blank" rel="noopener noreferrer">${subItem2.siteMenuItem.name?html}</a></li>
                                                                    <#else>
                                                                        <li class="nav-item"><a>${subItem2.siteMenuItem.name?html}</a></li>
                                                                    </#if>
                                                                  </#list>
                                                                </#if>
                                                              </ul>
                                                         </#list>
                                                        </#if>
                                                  <#else>
                                                    <#if subItem.children?has_content>
                                                     <#assign ulCounter=0>
                                                     <#assign noSubItemCounter=0>
                                                        <#list subItem.children as subItem1>
                                                           <#if ulCounter==0>
                                                                <ul class="koh-nav-section">
                                                                <#assign ulCounter=1>
                                                            <#else>
                                                                <#assign ulCounter=2>   
                                                           </#if>
                                                            <#if subItem1.menu??>
                                                                    <#if subItem1.menu.menuLink?has_content>
                                                                        <#assign target="_blank" />
                                                                        <#assign rel="noopener noreferrer" />
                                                                        <#assign href=subItem1.menu.menuLink />
                                                                        <#if subItem1.menu.menuLink?starts_with("/")>
                                                                            <#assign target="_self" />
                                                                            <#assign rel="" />
                                                                            <#assign href=href/>
                                                                        </#if>
                                                                    </#if>
                                                                    <li class="nav-heading"><a href="${href}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>${subItem1.siteMenuItem.name?html}</a></li>
                                                            <#elseif subItem1.siteMenuItem.hstLink??>   
                                                                <li class="nav-heading"><a href="<@hst.link link=subItem1.siteMenuItem.hstLink/>">${subItem1.siteMenuItem.name?html}</a></li>
                                                            <#elseif subItem1.siteMenuItem.externalLink??>
                                                                <li class="nav-heading"><a href="${subItem1.siteMenuItem.externalLink }" target="_blank" rel="noopener noreferrer">${subItem1.siteMenuItem.name?html}</a></li>
                                                            <#else>
                                                                <li class="nav-heading"><a>${subItem1.siteMenuItem.name?html}</a></li>
                                                             </#if>
                                                             
                                                                <#if subItem1.children?has_content>
                                                                  <#list subItem1.children as subItem2>
                                                                    <#if subItem2.menu??>
                                                                        <#if subItem2.menu.menuLink?has_content>
                                                                            <#assign target="_blank" />
                                                                            <#assign rel="noopener noreferrer" />
                                                                            <#assign href=subItem2.menu.menuLink />
                                                                            <#if subItem2.menu.menuLink?starts_with("/")>
                                                                                <#assign target="_self" />
                                                                                <#assign rel="" />
                                                                                <#assign href= href/>
                                                                            </#if>
                                                                        </#if>
                                                                        <li class="nav-item"><a href="${href}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>${subItem2.siteMenuItem.name?html}</a></li>
                                                                    <#elseif subItem2.siteMenuItem.hstLink??>   
                                                                        <li class="nav-item"><a href="<@hst.link link=subItem2.siteMenuItem.hstLink/>">${subItem2.siteMenuItem.name?html}</a></li>
                                                                    <#elseif subItem2.siteMenuItem.externalLink??>
                                                                        <li class="nav-item"><a href="${subItem2.siteMenuItem.externalLink }" target="_blank" rel="noopener noreferrer">${subItem2.siteMenuItem.name?html}</a></li>
                                                                    <#else>
                                                                        <li class="nav-item"><a>${subItem2.siteMenuItem.name?html}</a></li>
                                                                     </#if>
                                                                    </#list>
                                                                <#else>
                                                                    <#if ulCounter==2>
                                                                        <#assign noSubItemCounter=1>    
                                                                    </#if>  
                                                                </#if>
                                                            <#if ulCounter==2 && noSubItemCounter == 0>
                                                                </ul>
                                                                <#assign ulCounter=0>
                                                            </#if>  
                                                          </#list>
                                                          <#if ulCounter != 0>
                                                                </ul>
                                                           </#if>       
                                                       </#if>
                                                    </#if>
                                               </#list>
                                        </#if>
                                    </div>
                                </div>
                            </li>
                       </#if> 
                 </#list>
                </ul>
            </div>
        </nav>
    </#if>
    <@hst.cmseditmenu menu=menu/>
    </div>
  </#if>    
    </div>
    <div class="c-koh-site-search">
        <form class="koh-site-search-form" action="<@hst.link siteMapItemRefId="results" />" method="get" accept-charset="utf-8">
            <span class="koh-form-label" for="koh-nav-searchbox">${keywords}</span>
            <span class="koh-form-input-group">
                <input type="text" id="koh-nav-searchbox" name="search" maxlength="40"  pattern="[^<>]+$"  oninvalid="setCustomValidity('< and > are not allowed to search')" onchange="try{setCustomValidity('')}catch(e){}" placeholder="${search}"  autocomplete="off">
                <span class="koh-form-button">
                    <button type="submit" id="koh-nav-searchbutton"><span class="icon" data-icon="&#xe608;"></span><span class="text">${search}</span></button>
                </span>
            </span>
        </form>
        <div id="search-suggestions-container">
            <div class="search-suggestions-scroller">
                <ul>
                    <!-- auto complete will dynamically be added here -->
                </ul>
            </div>
        </div>  
    </div>
</div>

<div class="koh-quick-link-shortcut">
 <#if VRshowroomTitle?? && VRshowroomTitle?has_content>
	<div class="koh-quick-link">
			<a href="<#if VRshowroomeshopLink?? && VRshowroomeshopLink?has_content>${VRshowroomeshopLink} </#if>" target="_blank"> 
				<svg
          width="19"
          height="20"
          viewBox="0 0 19 20"
          fill="none"
          xmlns="https://www.w3.org/2000/svg"
        >
          <path
            d="M14.5 0.833328V2.5H17V5H18.6667V0.833328H14.5ZM17 17.5H14.5V19.1667H18.6667V15H17V17.5ZM2.00001 2.5H4.50001V0.833328H0.333344V5H2.00001V2.5ZM2.00001 15H0.333344V19.1667H4.50001V17.5H2.00001V15ZM15.3333 6.64166L9.50001 3.28333L3.66668 6.64166V13.3583L9.50001 16.7167L15.3333 13.3583V6.64166ZM8.66668 14.3083L5.33334 12.3917V8.53333L8.66668 10.475V14.3083ZM9.50001 9.03333L6.20001 7.10833L9.50001 5.20833L12.8 7.10833L9.50001 9.03333ZM13.6667 12.3917L10.3333 14.3083V10.475L13.6667 8.53333V12.3917Z"
            fill="white"
          />
        </svg>
		${VRshowroomTitle}</a>
      </div>
 </#if>
 <#if serviceTitle?? && serviceTitle?has_content>
    <div class="koh-quick-link">
            <a href="<#if servicesLink?? && servicesLink?has_content>${servicesLink} </#if>" target="_self"> 
            <svg
                width="25"
                height="25"
                viewBox="0 0 19 20"
            >
                <path class="cls-1" d="m11.91,9.53c-.25,0-.45-.2-.45-.45v-3.5c0-.17.1-.33.25-.4,1.43-.7,1.36-2,1.35-2.01,0-.92-.49-1.46-.9-1.75v1.87c0,.16-.09.32-.23.39l-.99.53c-.14.07-.3.07-.43,0l-1.07-.61c-.14-.08-.23-.23-.23-.39v-1.75c-.84.63-.76,1.62-.75,1.67,0,1.48,1.36,2.13,1.42,2.16.16.07.26.23.26.41v2.06c0,.25-.2.45-.45.45s-.45-.2-.45-.45v-1.8c-.53-.32-1.68-1.2-1.68-2.79-.08-.7.23-2.26,1.94-2.87.14-.05.29-.03.41.06.12.08.19.22.19.37v2.22l.63.36.54-.29V.69c0-.14.07-.27.18-.36.11-.08.26-.11.39-.07.73.22,2.12,1.1,2.12,2.87,0,.04.1,1.72-1.6,2.71v3.23c0,.25-.2.45-.45.45Z" fill="white"/>
                <path class="cls-1" d="m8.82,14.75c-.19,0-1.18-.04-2.13-.83-.02-.02-.04-.04-.06-.06l-.17-.21-1.65.03c-.13,0-.24-.04-.32-.13-.09-.08-.13-.2-.13-.32v-4.09c0-.25.2-.45.45-.45h1.34c.25-.54.91-1.61,2.26-1.94.03,0,.07-.01.11-.01h1.18c.25,0,.45.2.45.45v1.42h2.69c.49.02,1.17.4,1.17,1.33,0,.02.07,1.28-.83,3.85,0,.03-.02.05-.04.08-.05.09-.53.87-1.26.87h-3.02s-.01,0-.03,0Zm-1.52-1.49c.75.61,1.5.59,1.54.59h3.03c.15,0,.37-.24.47-.4.81-2.34.76-3.47.76-3.49,0-.41-.23-.45-.3-.46h-3.11c-.25,0-.45-.2-.45-.45v-1.42h-.68c-1.27.34-1.69,1.62-1.69,1.64-.06.19-.23.31-.43.31h-1.2v3.18l1.42-.03c.15,0,.27.06.36.17l.28.35Z" fill="white"/>
                <path class="cls-1" d="m4.8,14.09H1.45c-.25,0-.45-.2-.45-.45v-4.88c0-.25.2-.45.45-.45h3.36c.25,0,.45.2.45.45v4.88c0,.25-.2.45-.45.45Zm-2.91-.89h2.46v-3.98H1.89v3.98Z" fill="white"/>
            </svg>
        ${serviceTitle}</a>
      </div>
 </#if>
 <#if eshopTitle?? && eshopTitle?has_content>
      <div class="koh-quick-link">
       
				<a href="<#if eshopLink?? && eshopLink?has_content>${eshopLink} </#if>" target="_blank">
				<svg
          width="18"
          height="18"
          viewBox="0 0 18 18"
          fill="none"
          xmlns="https://www.w3.org/2000/svg"
        >
          <path
            d="M14.1667 14C14.6087 14 15.0326 14.1756 15.3452 14.4882C15.6577 14.8007 15.8333 15.2246 15.8333 15.6667C15.8333 16.1087 15.6577 16.5326 15.3452 16.8452C15.0326 17.1577 14.6087 17.3333 14.1667 17.3333C13.7246 17.3333 13.3007 17.1577 12.9881 16.8452C12.6756 16.5326 12.5 16.1087 12.5 15.6667C12.5 14.7417 13.2417 14 14.1667 14ZM0.833328 0.666672H3.55833L4.34166 2.33334H16.6667C16.8877 2.33334 17.0996 2.42114 17.2559 2.57742C17.4122 2.7337 17.5 2.94566 17.5 3.16667C17.5 3.30834 17.4583 3.45 17.4 3.58334L14.4167 8.975C14.1333 9.48334 13.5833 9.83334 12.9583 9.83334H6.74999L5.99999 11.1917L5.97499 11.2917C5.97499 11.3469 5.99694 11.3999 6.03601 11.439C6.07508 11.4781 6.12807 11.5 6.18333 11.5H15.8333V13.1667H5.83333C5.3913 13.1667 4.96738 12.9911 4.65482 12.6785C4.34226 12.366 4.16666 11.942 4.16666 11.5C4.16666 11.2083 4.24166 10.9333 4.36666 10.7L5.49999 8.65834L2.49999 2.33334H0.833328V0.666672ZM5.83333 14C6.27535 14 6.69928 14.1756 7.01184 14.4882C7.3244 14.8007 7.49999 15.2246 7.49999 15.6667C7.49999 16.1087 7.3244 16.5326 7.01184 16.8452C6.69928 17.1577 6.27535 17.3333 5.83333 17.3333C5.3913 17.3333 4.96738 17.1577 4.65482 16.8452C4.34226 16.5326 4.16666 16.1087 4.16666 15.6667C4.16666 14.7417 4.90833 14 5.83333 14ZM13.3333 8.16667L15.65 4H5.11666L7.08333 8.16667H13.3333Z"
            fill="white"
          />
        </svg>${eshopTitle}</a>
      </div>
 </#if>
      <div class="koh-quick-link">
         <a href="${storelink}">
			 <svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="https://www.w3.org/2000/svg">
			 <g clip-path="url(#clip0_89_1244)">
				<path d="M10 1.66667C6.77501 1.66667 4.16667 4.27501 4.16667 7.50001C4.16667 11.875 10 18.3333 10 18.3333C10 18.3333 15.8333 11.875 15.8333 7.50001C15.8333 4.27501 13.225 1.66667 10 1.66667ZM5.83334 7.50001C5.83334 5.20001 7.70001 3.33334 10 3.33334C12.3 3.33334 14.1667 5.20001 14.1667 7.50001C14.1667 9.90001 11.7667 13.4917 10 15.7333C8.26667 13.5083 5.83334 9.87501 5.83334 7.50001Z" fill="white"/>
			 	<path d="M10 9.58334C11.1506 9.58334 12.0833 8.6506 12.0833 7.50001C12.0833 6.34941 11.1506 5.41667 10 5.41667C8.84941 5.41667 7.91667 6.34941 7.91667 7.50001C7.91667 8.6506 8.84941 9.58334 10 9.58334Z" fill="white"/>
			</g>
			<defs>
				<clipPath id="clip0_89_1244">
					<rect width="20" height="20" fill="white"/>
				</clipPath>
			</defs>
			</svg>${whereToBuy}</a>
      </div>
      <div class="koh-quick-link">
       	<a href="${contactuslink}"><svg
          width="17"
          height="14"
          viewBox="0 0 17 14"
          fill="none"
          xmlns="https://www.w3.org/2000/svg"
        >
          <path
            d="M16.8333 0.333328H0.175001L0.166668 13.6667H16.8333V0.333328ZM15.1667 12H1.83333V3.66666L8.5 7.83333L15.1667 3.66666V12ZM8.5 6.16666L1.83333 1.99999H15.1667L8.5 6.16666Z"
            fill="white"
          />
        </svg>${contactUs}</a>
      </div>
</div>	


<@hst.link var="compareLink" siteMapItemRefId="compare"/>
<input type="hidden" id="compareLink" value="${compareLink}" />
<input id="compare-cookie-info" type="hidden" data-cookie-name="${cookieName}" data-cookie-duration="5" data-cookie-max-items-number="3" data-cookie-max-items-message="${cookieMaxItemsMessage}" data-cookie-diff-category-message="${cookieDiffCategoryMessage}" data-cookie-category="${cookieCategory}">
<div id="comparePanel">
	
</div>        
