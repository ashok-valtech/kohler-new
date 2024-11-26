<#include "../include/imports.ftl">
<@fmt.message key="facebook" var="facebook" />
<@fmt.message key="twitter" var="twitter" />
<@fmt.message key="print" var="print" />
<@fmt.message key="share" var="share" />
<@fmt.message key="explore-kohler-project" var="exploreKohlerProject" />
<@fmt.message key="country-region" var="countryRegion" />
<@fmt.message key="see-all-country" var="seeAllCountry" />
<@fmt.message key="company.region" var="companyRegion"/>       
<@fmt.message key="company.name" var="companyName" />
<@fmt.message key="globalprojects" var="globalprojects" />
<@fmt.message key="countryRegionMetaKeyword" var="countryRegionMetaKeyword"/>
<@hst.link var="globalprojectKeyLink" siteMapItemRefId="globalprojectKey"/>
<@hst.link var="globalprojectLazyLink" siteMapItemRefId="globalProjectLazy"/>
<@fmt.message key="load-more" var="loadMore"/>
<@fmt.message key="GP-ftr-show-all" var="showAll"/>
<@fmt.message key="gp-ftr-Hospitality" var="hospitality"/>
<@fmt.message key="gp-ftr-Commercial" var="commercial"/>
<@fmt.message key="gp-ftr-Residential" var="residential"/>
   
<#assign countryKey="" />
<#if hstRequestContext??>
    <#if hstRequestContext.servletRequest.getParameter( 'taxonomyKey')?has_content>
        <#assign countryKey=hstRequestContext.servletRequest.getParameter( 'taxonomyKey')>
    </#if>
</#if>

<input type="hidden" id="globalprojectLazyLink" value="${globalprojectLazyLink}" />
<input type="hidden" id="taxonomyKey" value="${countryKey}" />

    <#if countries??>
      <#list countries?keys as key>
        <input type="hidden" class="countries" value="${countries[key]?html}" />
      </#list>
    </#if>  

<@hst.link var="globalprojectKeyLink" siteMapItemRefId="globalprojectKey" />
<section class="c-koh-projects-pages">
    <div class="koh-projects-pages-container">
        <@hst.include ref="global-project-container" />
                <div class="koh-print-share-tools">
                    <div class="koh-page-print">
                        <button onclick="javascript:window.print()" class="koh-page-print-button" data-hasqtip="2">
                            <span class="icon" data-icon=""></span>
                            <span class="label">${print}</span>
                        </button>
                    </div>
                    <div class="koh-page-share">
                        <button class="koh-page-share-button" data-hasqtip="0"><span class="icon" data-icon="&#xe603"></span><span class="label">${share}</span></button>
                        <ul class="koh-page-share-popover">
                            <li>
                                <button class="koh-share-facebook"><span class="icon" data-icon="&#xe609"></span><span class="label">${facebook}</span></button>
                            </li>
                            <li>
                                <button class="koh-share-twitter"><span class="icon" data-icon="&#xe60d"></span><span class="label">${twitter}</span></button>
                            </li>
                        </ul>
                    </div>
                </div>
    </div>
    <div class="col-xs-12 koh-projects-listing">
        <div class="koh-projects-form">
            <form action="#" method="post">
                <label for="country">${exploreKohlerProject}</label>
                <div class="koh-country select">
                    <select id="country" name="country" autocomplete="off">
                        <#if countries?? && countries?has_content>
                            <option value="" selected="selected" disabled="disabled">${countryRegion}</option>
                            <#list countries?keys as key>
                            	<#if caregoryBasedProjects[key]??>
                                	<option value="${key}" <#if selectedCountryTaxonomy?? && selectedCountryTaxonomy==key>selected</#if> >${allCountries[key]?html}</option>
                                </#if>
                        	</#list>
                        </#if>
                    </select>
                </div>
            </form>
                <div id="myDiv" class="koh-demo-filter">
                
                <#if currentFilters?seq_contains('Hospitality')>
			       <#assign hospitalityFilter='' />
		        <#else >
		            <#assign hospitalityFilter='disabled' />
		        </#if>	
                
                <#if currentFilters?seq_contains('Commercial')>
			       <#assign commercialFilter='' />
		        <#else >
		            <#assign commercialFilter='disabled' />
		        </#if>	
                 
                <#if currentFilters?seq_contains('Residential')>
			       <#assign residentialFilter='' />
		        <#else >
		            <#assign residentialFilter='disabled' />
		        </#if>	
                  <input id="koh-fifthFilterSeeAllCountry" type="checkbox" name="showAll" value="clearAllFilters" checked><label for="koh-fifthFilterSeeAllCountry">${showAll}</label>
		          <input id="koh-fifthFilterHospitality" class="source" type="checkbox" name="fifthCheck" value="Hospitality" ${hospitalityFilter}><label for="koh-fifthFilterHospitality">${hospitality}</label>
		          <input id="koh-fifthFilterCommercial" class="source" type="checkbox" name="fifthCheck" value="Commercial" ${commercialFilter}><label for="koh-fifthFilterCommercial">${commercial}</label>
		          <input id="koh-fifthFilterResidential" class="source" type="checkbox" name="fifthCheck" value="Residential" ${residentialFilter}><label for="koh-fifthFilterResidential">${residential}</label>
              </div>
        </div>
        
        
        <#if selectedCountryBean??>
	        <div id="seeCountryProject">
	                <div class="koh-project-display">
	                    <div class="koh-project-country-title">
	                        <h3 class="col-lg-6 col-md-6 col-sm-6 col-xs-12">${allCountries[selectedCountryTaxonomy]?html}</h3>
	                    </div>
	                    <#list selectedCountryBean as project>
	                        <@hst.link var="categoryProjectsLink" hippobean=project/>
	                        <div class="koh-project-display-items col-lg-4 col-md-4 col-sm-4 col-xs-12">
	                            <a href="${categoryProjectsLink?html}">
	                                <div class="koh-project-display-items-inner">
	                                    <img class="featured-box-image" src="${project.imageUrl?html}" alt="${project.title?html}">
	                                    <div class="koh-project-display-content">
	                                        <p>${project.title?html}</p>
	                                        <span>${project.place?html}</span>
	                                    </div>
	                                </div>
	                            </a>
	                        </div>
	                    </#list>
	                </div>
	         </div>
        <#else>
        <#if caregoryBasedProjects??>
            <#if countries??>
            <div id="globalProjectListSection">
                <#list countries?keys as key>
                    <div class="koh-project-display">
                        <div class="koh-project-country-title">
                            <h3 class="col-lg-6 col-md-6 col-sm-6 col-xs-12">${allCountries[key]?html}  </h3>
                            <#if countries[key]??>
                            	<#if countries[key].count &gt; maxCountryProject>
                             		<div class="koh-project-country-more-link col-lg-6 col-md-6 col-sm-6 col-xs-12"><span class="seeAllCountry" data-taxonomy="${key}">${seeAllCountry?html} ${allCountries[key]?html}</span></div>
                             	</#if>	
                            </#if>
                        </div>
                       <#if caregoryBasedProjects[key]??> 
                        <#list caregoryBasedProjects[key] as project>
                            <#if project?? && project?has_content>
                            <#if project.title?? && project.title?has_content>
                                <#assign imgAlt=project.title>
			            		<#assign imgAlt = imgAlt?replace("(R)","&reg;")/>
								<#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
                            </#if>
                             <#if project.imageAlt?? && project.imageAlt?has_content>
			            		<#assign imgAlt=project.imageAlt>
			            		<#assign imgAlt = imgAlt?replace("(R)","&reg;")/>
								<#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
            	            </#if>
                                <@hst.link var="caregoryBasedProjectsLink" hippobean=project/>
                               <div class="koh-project-display-items col-lg-4 col-md-4 col-sm-4 col-xs-12">
                                    <a href="${caregoryBasedProjectsLink?html}">
                                        <div class="koh-project-display-items-inner">
                                            <@hst.link var="projectRelatedDocsLink" hippobean=project/>
                                            <img class="featured-box-image" src="${project.imageUrl?html}" alt="${imgAlt?html}" <#if project.imageTitle?? && project.imageTitle?has_content> title="${project.imageTitle}"</#if>>
                                            <div class="koh-project-display-content">
                                                <p>${project.title?html}</p>
                                                <span>${project.place?html}</span>
                                            </div>
                                        </div>
                                    </a>
                               </div>
                            </#if>
                        </#list>
                     </#if>
                    </div>
                </#list>
                </div>
            </#if>
              </#if>
              </#if>
				<div id="globalProjectCountryListing hide">
		            <div class="koh-project-display">
			            <div id="globalProjectCountry">
		                    <div class="koh-project-country-title">
		                        <h3 class="col-lg-6 col-md-6 col-sm-6 col-xs-12"> </h3>
		                    </div>
		             	</div>
		                <div id="globalProjectmainList">
		                </div>
	                </div>
                </div>
                
    </div>
</section>

<#assign pageTitle=globalprojects + " | " + companyRegion + " | " +  companyName />

<#-- og tags for twitter-->
<@hst.headContribution category="meta-tags">
                <meta name="twitter:title" content="${pageTitle}" />
</@hst.headContribution>
<@hst.headContribution category="meta-tags">
                <meta name="twitter:site" content="@${companyName}" />
</@hst.headContribution>

<#-- og tags for facebook-->
<@hst.headContribution category="meta-tags">
                <meta property="og:title" content="${pageTitle}" />
</@hst.headContribution>
<@hst.headContribution category="meta-tags">
                <meta property="og:site_name" content="${companyName}" />
</@hst.headContribution>
                

<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
  <script type="text/javascript" src="${link}/js/custom/koh-global-project.min.js"></script>
</@hst.headContribution>


<@hst.headContribution category="ext-scripts">
  <@hst.webfile path="/" var="link" />
  <@hst.webfile path="/js/custom/koh-load-more.min.js" var="kohLoadMoreJs"/>
  <script type="text/javascript" src="${kohLoadMoreJs}"></script>
</@hst.headContribution>