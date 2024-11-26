<#include "../include/imports.ftl">

<@fmt.message key="site-map" var="siteMap"/>
<@fmt.message key="company.name" var="companyName"/>
<@fmt.message key="siteMapMetaDesc" var="siteMapMetaDesc"/>
<@hst.include ref="container" />

<div class="koh-page-content one-content">
    <div class="koh-content-area primary">
        <section class="c-koh-simple-content v-koh-full-page c-koh-sitemap-content">
            <div class="koh-simple-content-container">
                <h2 class="koh-simple-content-title">${siteMap}</h2>
                <div class="koh-simple-content-body">
                    <ul class="col-xs-12 koh-site-map">
                        <#if siteMapMenu??>
							<#list siteMapMenu as item>
                                <#if item.children?has_content>
                                    <#list item.children as subItem>
                                        <#if subItem.children?has_content>
                                            <#list item.children as subItem>
                                               <li class="col-lg-3 col-md-4 col-sm-6 col-xs-12 koh-sitemap-list">
                                                    <div class="koh-subnav-header">
                                                    <#if subItem.siteMenuItem.externalLink?has_content>                                                                       
                                                        <a href="${subItem.siteMenuItem.externalLink}" target="_blank" rel="noopener noreferrer">${subItem.siteMenuItem.name?html}</a>
                                                    <#else>
                                                        ${subItem.siteMenuItem.name?html}
                                                    </#if>
                                                    </div>
                                                      <ul class="koh-nav-section">
                                                        <#if subItem.children?has_content>
	                                                       <#list subItem.children as subItem1>     
                                                            <#if subItem1.menu?? >
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
			                                                       <li class="nav-heading"><a href="${subItem1.siteMenuItem.externalLink}" target="_blank" rel="noopener noreferrer">${subItem1.siteMenuItem.name?html}</a></li>                                                         
			                                                   <#else>               
			                                                       <li class="nav-heading"><a>${subItem1.siteMenuItem.name?html}</a></li>                                                         
			                                                </#if>
	                                                     </#list>
                                                      </#if>
                                                   </ul>
                                                </li>   
                                            </#list>
                                        </#if>
                                    </#list>
                                </#if>
                            </#list>
                        </#if>
                    </ul>
                </div>
            </div>
        </section>
    </div>
</div>
    
<#--  <@hst.headContribution  category="title">
	<title>${siteMap} | ${companyName}</title>
</@hst.headContribution>

<@hst.headContribution category="meta-tags">
	<meta name="description" content="${siteMapMetaDesc}"/>
</@hst.headContribution> -->

