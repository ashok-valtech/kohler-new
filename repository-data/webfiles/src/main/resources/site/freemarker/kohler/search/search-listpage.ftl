<#include "../../include/imports.ftl">
<#if totalResults?? && totalResults gt 0>
<#if type?? && type?has_content>
	<#if type == "product" && totalProductCount??>
		<#include "../productfacetpage-productlist.ftl">
	<#elseif type == "spec" && totalSpecCount??>
		<#include "search-spec.ftl" />
	<#elseif ((type == "article") || (type == "video" ) || (type == "pressreleases")) && totalArticleVideoSize??>
		<#include "search-articlesvideos.ftl" />
	<#elseif type == "help" && totalHelpCount??>
		<#include "search-help.ftl" />			
	</#if>
</#if>
</#if>