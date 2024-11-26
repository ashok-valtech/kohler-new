<#include "../include/imports.ftl">
<@fmt.message key="media-contacts" var="mediaContactsDetail"/>
<@fmt.message key="press-releases" var="pressReleases"/>
<@fmt.message key="load-more" var="loadMore"/>
<@fmt.message key="back-to-top" var="backTotop"/>
<@hst.link var="pressReleasesLazyLink" siteMapItemRefId="pressReleasesLazy"/>
<@fmt.message key="pressReleasesMetaKeyword" var="pressReleasesMetaKeyword"/>
<@fmt.message key="pressReleaseDateFormat" var="pressReleaseDateFormat"/>

<@hst.include ref="container"/>


<input type="hidden" id="pressReleasesLazyLink" value="${pressReleasesLazyLink}" />
<input type="hidden" id="offset" value="${pageSize}" autocomplete="off" />
<input type="hidden" id="totalProjects" value="${totalProjects}" />

<section class="c-koh-press-list">
    <div class="koh-press-container">
        <div class="koh-press-header">
            <h1 class="koh-press-title">${pressReleases}</h1>
        </div>
        <#if pageable?? && pageable.items?has_content>
	        <div class="koh-tab-content">
	            <div class="koh-tab-contents">
	                <ul id="mainList">
					  <#list pageable.items as item>
					      <#if item?? && item?has_content>
							<@hst.link var="link" hippobean=item />
	                        <li>
		                        <#if item.date??>
		                             <time><@fmt.formatDate value=item.date.time type="Date" pattern= "${pressReleaseDateFormat}" /></time>
		                        </#if>
	                            <a href="<#if link?? && link?has_content>${link}</#if>">
                                    <#if item.description?? && item.description?has_content>
                                       <#assign description = item.description?replace("(R)","<sup>&reg;</sup>")/>
						               <#assign description = description?replace("(TM)","<sup>&trade;</sup>")/>
                                                  ${description}
                                    </#if>
                                </a>
	                        </li>
                         </#if>  
			      	  </#list>
	        		</ul>
	            </div>
             <#if ( pageSize < totalProjects ) >
             <div class="load-more-wrap">
                <span  class="koh-load-more">${loadMore}</span>
             </div>
             </#if> 
	        </div>
	     </#if>  
     </div>
    
   <div id="back-to-top">
      <button class="btn--square btn--gray">
      <i class="icon--chevron-up"></i>
      <span>${backTotop}</span>
      </button>
   </div>
</section>

<@hst.headContribution category="ext-scripts">
  <@hst.webfile path="/" var="link" />
  <@hst.webfile path="/js/custom/koh-load-more.min.js" var="kohLoadMoreJs"/>
  <script type="text/javascript" src="${kohLoadMoreJs}"></script>
</@hst.headContribution>