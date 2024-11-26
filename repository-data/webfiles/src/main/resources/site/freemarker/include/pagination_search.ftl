<#-- @ftlvariable name="pageable" type="org.onehippo.cms7.essentials.components.paging.Pageable" -->
<#include "../include/imports.ftl">
<@fmt.message key="prev" var="previous"/>
<@fmt.message key="next" var="next"/>

<div class="koh-search-pagination">  
<@hst.link var="homeLink" siteMapItemRefId="root"/>
<#if type == "product" && totalProductCount??>
	<#assign totalResults =  totalProductCount/>
<#elseif type== "spec" && totalSpecCount??>
	<#assign totalResults = totalSpecCount />
</#if>
<ul class="pagination">
  <#if totalResults gt 1>
   <#assign startPage = 1/>
     <#if currentPage gt 1>
           <#--  <li><a href="<@hst.link siteMapItemRefId="search" />?<#if query?? &&query?has_content>query=${query?html}</#if><#if type?? &&type?has_content>&type=${type}</#if><#if currentpage?? &&currentpage?has_content>&currentpage=${currentPage-1}</#if><#if orderBy?? &&orderBy?has_content>&orderBy=${orderBy}</#if><#if sort?? &&sort?has_content>&sort=${sort}</#if>">Prev</a></li>-->
          <li><a href="" data-page="${currentPage-1}">${previous}</a>
        </#if>
     <#list 1..totalPages as pageNr>
       <#if currentPage == pageNr>
            <li class="active">${pageNr}</a></li>
        <#else >
            <#--<li><a href="<@hst.link siteMapItemRefId="search" />?<#if query?? &&query?has_content>query=${query?html}</#if><#if type?? &&type?has_content>&type=${type}</#if><#if currentpage?? &&currentpage?has_content>&currentpage=${pageNr}</#if><#if orderBy?? &&orderBy?has_content>&orderBy=${orderBy}</#if><#if sort?? &&sort?has_content>&sort=${sort}</#if>">${pageNr}</a></li>-->
            <li><a href="" data-page="${pageNr}">${pageNr}</a>
        </#if>
    </#list>
    <#if totalPages gt currentPage>
          <#--<li><a href="<@hst.link siteMapItemRefId="search" />?<#if query?? &&query?has_content>query=${query?html}</#if><#if type?? &&type?has_content>&type=${type}</#if><#if currentpage?? &&currentpage?has_content>&currentpage=${currentPage+1}</#if><#if orderBy?? &&orderBy?has_content>&orderBy=${orderBy}</#if><#if sort?? &&sort?has_content>&sort=${sort}</#if>">Next</a></li>-->
          <li><a href="" data-page="${currentPage+1}">${next}</a>
        </#if>
   </#if>
</ul>

</div>
