<#include "../../include/imports.ftl">
<#assign orderBy="" />
<#assign sort=""/>
<@fmt.message key="searchPageURL" var="searchPageURL"/>
<#assign section="SearchPage" />
<#if hstRequestContext??>
	<#if hstRequestContext.servletRequest.getParameter('orderBy')?has_content>
		<#assign orderBy=hstRequestContext.servletRequest.getParameter('orderBy')>
		<#assign sort=hstRequestContext.servletRequest.getParameter('sort')>
	</#if>
</#if>
<@fmt.message key="relevancy" var="relevancy"/>
<@fmt.message key="sort-by" var="sort_by"/>
<@fmt.message key="name-a-z" var="nameAZ"/>
<@fmt.message key="name-z-a" var="nameZA"/>	
<@fmt.message key="categories-filters" var="categoriesFilters"/> 	
<@fmt.message key="show-all" var="showAll"/>	 
<@fmt.message key="category" var="category"/>	
<@fmt.message key="remove-filter" var="removefilter"/> 	
<@fmt.message key="filter-by" var="filterBy"/>
<@fmt.message key="clear-all" var="ClearAll"/>
<@fmt.message key="remove-filter" var="removeFilter"/>
<@fmt.message key="categories" var="categories"/>
<@fmt.message key="video" var="video"/>
<@fmt.message key="videos" var="videos"/>
<@fmt.message key="search-articles" var="searchArticles"/>
<@fmt.message key="press-releases" var="pressreleases"/>
<@fmt.message key="read-the-article" var="readthearticle"/>
<@fmt.message key="search-read-the-pressreleases" var="readPressReleases"/>
<@fmt.message key="facebookProductDescVideoURL" var="facebookProductDescVideoURL"/>
<@fmt.message key="facebookProductVideoThumbURL" var="facebookProductVideoThumbURL"/>

<header>
	<input type="hidden" class="koh-data-attributes"  data-koh-url-root="${hstRequestContext.siteContentBasePath?html}" data-koh-search-url="${searchPageURL}" data-koh-section="${section}">
</header>
<#assign newquery=search>
	<input class="koh-facet-data-attributes" data-koh-section="bathroom" type="hidden" data-koh-type="<#if type?? && type?has_content>${type?html}</#if>" data-koh-searchterm="<#if search?? && search?has_content>${search?html}</#if>" data-koh-currentpage="<#if currentPage?? && currentPage?has_content>${currentPage?html}</#if>" data-koh-orderBy="<#if orderBy?? && orderBy?has_content>${orderBy?html}</#if>" data-koh-sortBy="<#if sort?? && sort?has_content>${sort?html}</#if>"/>
	<div class="koh-search-sorting">
		<#assign sortBy=sort_by>
		<#if  (orderBy=="relevancy" && sort =="asc") >
			<#assign sortBy=relevancy>
		<#elseif (orderBy=="name"  && sort == "asc") >
			<#assign sortBy=nameAZ>
		<#elseif (orderBy=="name"  && sort == "desc") >
			<#assign sortBy=nameZA>
		</#if>
		<button class="koh-sort-by">${sortBy}</button>
		<ul class="koh-sort-options" data-sort-orderby-label="orderBy" data-sort-direction-label="sort">
			<li <#if  (orderBy=="relevancy" && sort =="asc")>class="koh-sort-selected"</#if>><span data-sort-orderby="relevancy" data-sort-direction="asc">${relevancy}</span></li>
			<li <#if  (orderBy=="name"  && sort == "asc") >class="koh-sort-selected"</#if>> <span data-sort-orderby="name" data-sort-direction="asc">${nameAZ}</span></li>
			<li <#if  (orderBy=="name"  && sort =="desc") >class="koh-sort-selected"</#if>><span data-sort-orderby="name" data-sort-direction="desc">${nameZA}</span></li>
		
		</ul>
	</div>
	<div class="koh-search-filters">
		<div class="koh-search-filters-title">${categoriesFilters?html}</div>
		   <div class="koh-search-filters-content">
			  <div class="koh-filter-group koh-category">
				<h3 class="koh-filter-group-title">Content Type</h3>
			            <#if totalArticleCount gt 0  && type == "article">
			             		<ul class="koh-selected-filters-videoart">
					               <li>
										<button class="koh-selected-filter" data-facet-name="" data-facet-value="" data-facet-count="">
											<span class="name">${searchArticles}</span>
											<span class="count">(${totalArticleCount?html})</span>
										</button>
									</li>
								</ul>
								 <#if totalVideoCount gt 0>
								 	<ul class="koh-available-filters-videoart">
							              <li>
							                 <a href="<@hst.link siteMapItemRefId="results" />?type=video&search=${search?html}&currentpage=1&<#if orderBy??>orderBy=${orderBy}<#else>orderBy=relevancy</#if>&<#if sort??>sort=${sort}<#else>sort=asc</#if>">
							                    <span class="name">${videos}</span>
				                                <span class="count">(${totalVideoCount?html})</span>
							                  </a>
							               </li>
						            </ul>
				                 </#if>
				                 <#if totalPressreleasesCount gt 0>
								 	<ul class="koh-available-filters-videoart">
							              <li>
							                 <a href="<@hst.link siteMapItemRefId="results" />?type=pressreleases&search=${search?html}&currentpage=1&<#if orderBy??>orderBy=${orderBy}<#else>orderBy=relevancy</#if>&<#if sort??>sort=${sort}<#else>sort=asc</#if>">
							                    <span class="name">${pressreleases}</span>
				                                <span class="count">(${totalPressreleasesCount?html})</span>
							                  </a>
							               </li>
						            </ul>
				                 </#if> 
						<#elseif  totalVideoCount gt 0 && type == "video">	
								<ul class="koh-selected-filters-videoart">
			              			<li>
										<button class="koh-selected-filter" data-facet-name="" data-facet-value="" data-facet-count="">
											<span class="name">${videos}</span>
											<span class="count">(${totalVideoCount?html})</span>
										</button>
									</li>
								</ul>
								<#if totalArticleCount gt 0 >
									<ul class="koh-available-filters-videoart">
									 	<li>
							                 <a href="<@hst.link siteMapItemRefId="results" />?type=article&search=${search?html}&currentpage=1&<#if orderBy??>orderBy=${orderBy}<#else>orderBy=relevancy</#if>&<#if sort??>sort=${sort}<#else>sort=asc</#if>">
							                    <span class="name">${searchArticles}</span>
				                                <span class="count">(${totalArticleCount?html})</span>
							                  </a>
						               </li>
					               </ul>
								</#if> 
								<#if totalPressreleasesCount gt 0>
								 	<ul class="koh-available-filters-videoart">
							              <li>
							                 <a href="<@hst.link siteMapItemRefId="results" />?type=pressreleases&search=${search?html}&currentpage=1&<#if orderBy??>orderBy=${orderBy}<#else>orderBy=relevancy</#if>&<#if sort??>sort=${sort}<#else>sort=asc</#if>">
							                    <span class="name">${pressreleases}</span>
				                                <span class="count">(${totalPressreleasesCount?html})</span>
							                  </a>
							               </li>
						            </ul>
				                 </#if>
				        <#elseif  totalPressreleasesCount gt 0 && type == "pressreleases">	
								<ul class="koh-selected-filters-videoart">
			              			<li>
										<button class="koh-selected-filter" data-facet-name="" data-facet-value="" data-facet-count="">
											<span class="name">${pressreleases}</span>
											<span class="count">(${totalPressreleasesCount?html})</span>
										</button>
									</li>
								</ul>
								<#if totalArticleCount gt 0 >
									<ul class="koh-available-filters-videoart">
									 	<li>
							                 <a href="<@hst.link siteMapItemRefId="results" />?type=article&search=${search?html}&currentpage=1&<#if orderBy??>orderBy=${orderBy}<#else>orderBy=relevancy</#if>&<#if sort??>sort=${sort}<#else>sort=asc</#if>">
							                    <span class="name">${searchArticles}</span>
				                                <span class="count">(${totalArticleCount?html})</span>
							                  </a>
						               </li>
					               </ul>
								</#if> 
								<#if totalVideoCount gt 0>
								 	<ul class="koh-available-filters-videoart">
							              <li>
							                 <a href="<@hst.link siteMapItemRefId="results" />?type=video&search=${search?html}&currentpage=1&<#if orderBy??>orderBy=${orderBy}<#else>orderBy=relevancy</#if>&<#if sort??>sort=${sort}<#else>sort=asc</#if>">
							                    <span class="name">${videos}</span>
				                                <span class="count">(${totalVideoCount?html})</span>
							                  </a>
							               </li>
						            </ul>
				                 </#if>        
			            </#if> 
			</div>
 	 </div>
</div>	
<#if type=="article">
  <div class="koh-search-results v-koh-articles">
  		<#if resultArticleList??>
		     <#list resultArticleList as article>
		     	<#assign value = article/>
				<@hst.link var="articleLink" hippobean=value />
				<div class="koh-product-article">
				  	<div class="koh-article-content">
						<#assign articleTitle = article.title />
						<#if articleTitle?? && articleTitle?has_content>
							<#assign articleTitle = articleTitle?replace("(R)","<sup>&reg;</sup>")/>
    						<#assign articleTitle = articleTitle?replace("(TM)","<sup>&trade;</sup>")/>
    					<#else>
    						<#assign articleTitle = "" />	
    					</#if>	
						<span class="koh-article-description">${articleTitle}</span>
						<span class="koh-article-date"><#if article.publishedDate?? && article.publishedDate?has_content><time><@fmt.formatDate value=article.publishedDate.time type="Date" pattern="MMMM d, yyyy" /></time></#if></span>
						<a href="${articleLink?html}" class="koh-article-link">${readthearticle}</a>
					</div>
			    </div>
			 </#list>
		</#if>	 
     </div>  
<#elseif type=="video">
	<div class="koh-product-content">
	 <div class="koh-product-features">
	  <div class="koh-search-results v-koh-articles">
	  		<#if resultVideoList??>
				<#list resultVideoList as product>
				    <#list product.attributes as productAttributes>
				      <#if productAttributes.key == "YOUTUBE_ID" >
				       <#assign facebookId=productAttributes.value[0] />
				      	<div class="koh-product-article">
					 		<div class="koh-article-content">
					            <span class="koh-product-resources-subtitle">${video}</span>
						        <div class="koh-product-resources-videos">
						               <div class="koh-article-video">
						               	  <#assign facebookDescURL = facebookProductDescVideoURL?replace("{0}","${facebookId}") />
						                  <a href="${facebookDescURL?html}" target="_blank" rel="noopener noreferrer" data-api="">
						                  <span class="koh-article-icon">
						                  		<span class="icon" data-icon=""></span>
						                  </span>
						                   <#assign facebookThumbURL = facebookProductVideoThumbURL?replace("{0}","${facebookId}") />
						                  <img src="${facebookThumbURL?html}" alt="" width="191" height="164" /></a>
						               </div>
						        </div>
					        </div>
			    		</div>
		              </#if>
		            </#list>
			   </#list>
			 </#if>
	    </div>
	  </div>	
	</div>	
	<@hst.webfile path="/" var="link" />
	<@hst.headContribution category="ext-scripts">
	  <script src="${link}/js/c-koh-product-details.min.js" type="text/javascript"></script>
	</@hst.headContribution>
<#elseif type=="pressreleases">
  <div class="koh-search-results v-koh-articles">
  	<#if resultPressReleaseList??>
	     <#list resultPressReleaseList as pressReleases>
			<#assign value = pressReleases/>
			<@hst.link var="link" hippobean=value />
			<div class="koh-product-article">
			  	<div class="koh-article-content">
					<#--<span class="koh-article-type">${article.title}</span>-->
					<span class="koh-article-description"><#if value.description?? && value.description?has_content>${value.description}</#if></span>
					<span class="koh-article-date"><time><@fmt.formatDate value=value.date.time type="Date" pattern="MMMM d, yyyy" /></time></span>
					<a href="${link?html}" class="koh-article-link"><#if value.description?? && value.description?has_content>${readPressReleases}</#if></a>
				</div>
		    </div>
		 </#list>
	</#if>
  </div>	
</#if>
