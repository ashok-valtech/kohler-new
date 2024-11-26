<#include "../../include/imports.ftl">
<@fmt.message key="search-products" var="searchproducts"/>
<@fmt.message key="search-spec" var="searchspec"/>
<@fmt.message key="search-articleandvideo" var="searcharticleandvideo"/>
<@fmt.message key="search-help" var="searchhelp"/>
<@fmt.message key="company.name" var="companyName"/>
<@fmt.message key="searchresults" var="searchresults"/>
<@fmt.message key="didyoumeanquestion" var="didyoumeanquestion"/>
<@fmt.message key="searchresultsfor" var="searchresultsfor"/>

<div class="koh-search-hero">
	 <div class="koh-hero-container">
		<h1>${searchresultsfor}<#if search?? && search?has_content>
							<#assign searchTerm = search?replace("(R)","<sup>&reg;</sup>") />
							<#assign searchTerm = searchTerm?replace("(TM)","<sup>&trade;</sup>") />
	  	"${searchTerm}"</#if></h1>
		<#if didyoumean?? && didyoumean?has_content>
			<p>
			  	<h1>  ${didyoumeanquestion}  <a href="<@hst.link siteMapItemRefId="results" />?search=${didyoumean}&currentpage=1<#if orderBy?? && orderBy?has_content>&orderBy=${orderBy}</#if><#if sort?? && sort?has_content>&sort=${sort}</#if>">${didyoumean} <span></span></a>&nbsp &nbsp </h1>
			<p> 	
		</#if>
		
		<#assign selectedcategory=searchproducts>
		<#if type?? && type=="product"><#assign selectedcategory=searchproducts></#if>
		<#if type?? && type=="spec"><#assign selectedcategory=searchspec></#if>
		<#if type?? && type=="article"><#assign selectedcategory=searcharticleandvideo></#if>
		<#if type?? && type=="pressreleases"><#assign selectedcategory=searcharticleandvideo></#if>
		<#if type?? && type=="video"><#assign selectedcategory=searcharticleandvideo></#if>
		<#if type?? && type=="help"><#assign selectedcategory=searchhelp></#if>
		
		    
        <#if totalResults?? && totalResults?has_content>${totalResults}&nbsp ${searchresults} </#if>
	  	    <div class="koh-search-controls">
		    <#if totalResults gt 0>     <button class="koh-search-select">${selectedcategory}</button></#if>		
	        <ul class="koh-search-set">
	          <#if totalProductCount?? && (totalProductCount gt 0)>
				 <li <#if type == "product">class="active" </#if>>
				 	 <a href="<@hst.link siteMapItemRefId="results" />?search=${search?html}&type=product&currentpage=1<#if orderBy?? && orderBy?has_content>&orderBy=${orderBy}</#if><#if sort?? && sort?has_content>&sort=${sort}</#if>">${searchproducts} <span>(${totalProductCount})</span></a>&nbsp &nbsp
				 </li>	  	
			  </#if>
			  <#if totalSpecCount?? && (totalSpecCount gt 0)>
				 <li <#if type == "spec">class="active" </#if>>
				 	 <a href="<@hst.link siteMapItemRefId="results" />?search=${search?html}&type=spec&currentpage=1<#if orderBy?? && orderBy?has_content>&orderBy=${orderBy}</#if><#if sort?? && sort?has_content>&sort=${sort}</#if>">${searchspec} <span>(${totalSpecCount})</span></a>&nbsp &nbsp
				 </li>	  		 	  	
			  </#if>  
			   <#if totalArticleVideoSize?? & totalArticleVideoSize gt 0>
				 <li <#if type == "article" || type=="pressreleases" || type == "video">class="active" </#if>>
				 	 <a href="<@hst.link siteMapItemRefId="results" />?search=${search?html}&type=article&currentpage=1<#if orderBy?? && orderBy?has_content>&orderBy=${orderBy}</#if><#if sort?? && sort?has_content>&sort=${sort}</#if>">${searcharticleandvideo} <span>(${totalArticleVideoSize})</span></a>&nbsp &nbsp
				 </li>
				 	  		 	  	
			  </#if>  
			     <#if totalHelpCount?? && (totalHelpCount gt 0)>
				 <li <#if type == "help">class="active" </#if>>
				 	 <a href="<@hst.link siteMapItemRefId="results" />?search=${search?html}&type=help&currentpage=1<#if orderBy?? && orderBy?has_content>&orderBy=${orderBy}</#if><#if sort?? && sort?has_content>&sort=${sort}</#if>">${searchhelp}<span>(${totalHelpCount})</span></a>&nbsp &nbsp
				 </li>
				 	  		 	  	
			  </#if> 
	        </ul>	 				 		
	   	</div>			 				 				 		
	 </div>			 				 				 				 				 		
</div>


<#if totalResults?? && (totalResults gt 0) && (type?? && type != "article" && type != "pressreleases" && type != "help" && type != "video")>
	<#include "../productfacetpage-navlist.ftl">
</#if>

<#if search?? && searchresults?? && companyName??>
	<@hst.headContribution category="title">
		<title>${search?html} | ${searchresults} | ${companyName}</title>
	</@hst.headContribution>
</#if>
<@hst.headContribution category="title">
	<META NAME="ROBOTS" CONTENT="NOINDEX, NOFOLLOW"/>
</@hst.headContribution>

