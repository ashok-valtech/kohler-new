<#include "../include/imports.ftl">
<@fmt.message key="productFacetListMetaDesc" var="productFacetListMetaDesc"/>
<@hst.link var="productDetailslink" siteMapItemRefId="productDetails"/>
<input type="hidden" id="productDetailslink" value="${productDetailslink}" />
<#assign orderBy="" />
<#assign sort=""/>
<#assign section="FacetPage" />
<#assign isCollections="" />
<@fmt.message key="collectionsLabel" var="collectionsLabel"/>
<@fmt.message key="collectionsUrl" var="collectionsUrl"/>
<@fmt.message key="searchPageURL" var="searchPageURL"/>
<#if hstRequestContext??>
	<#if hstRequestContext.servletRequest.getParameter('orderBy')?has_content>
		<#assign orderBy=hstRequestContext.servletRequest.getParameter('orderBy')?html>
		<#assign sort=hstRequestContext.servletRequest.getParameter('sort')?html>
	</#if>
	<#if hstRequestContext.servletRequest.getPathInfo()?has_content>
		<#assign url=hstRequestContext.servletRequest.getPathInfo() />
		<#if url?contains("/" + searchPageURL)>
			<#assign section="SearchPage" />
		<#elseif  url?contains("/" + collectionsUrl)>
			<#assign section="SearchPage" />
			<#assign isCollections="true" />
		</#if>
	</#if>
</#if>
<@fmt.message key="products" var="products"/>
<@fmt.message key="sort-by" var="sort_by"/>
<@fmt.message key="relevancy" var="relevancy"/>
<@fmt.message key="name-a-z" var="nameAZ"/>
<@fmt.message key="name-z-a" var="nameZA"/>	
<@fmt.message key="categories-filters" var="categoriesFilters"/> 	
<@fmt.message key="show-all" var="showAll"/>	 
<@fmt.message key="category" var="category"/>	
<@fmt.message key="remove-filter" var="removefilter"/> 	
<@fmt.message key="filter-by" var="filterBy"/>
<@fmt.message key="clear-all" var="ClearAll"/>
<@fmt.message key="new-products" var="newProducts"/>
<@fmt.message key="remove-filter" var="removeFilter"/>
<@fmt.message key="categories" var="categories"/>

<@fmt.message key="ft_isNew" var="isNew"/>
<@fmt.message key="ft_color" var="color"/>
<@fmt.message key="ft_overallWidth" var="overAllWidth"/>
<@fmt.message key="ft_overallHeight" var="overAllHeight"/>
<@fmt.message key="ft_overallLength" var="overAllLength"/>

<@fmt.message key="ft_PriceRange" var="priceRangeFacetName"/>
<@fmt.message key="ft_InstallationType" var="installationTypeFacetName"/>
<@fmt.message key="ft_Feature" var="featureFacetName"/>
<@fmt.message key="ft_Shape" var="shapeFacetName"/>
<@fmt.message key="ft_HandleType" var="handleTypeFacetName"/>
<@fmt.message key="ft_NumberOfHandles" var="numberOfHandlesFacetName"/>
<@fmt.message key="ft_WaterSaving" var="waterSavingFacetName"/>
<@fmt.message key="ft_ProductType" var="productTypeFacetName"/>
<@fmt.message key="ft_ToiletType" var="toiletTypeFacetName"/>
<@fmt.message key="ft_LitersperFlush" var="litersperFlushFacetName"/>
<@fmt.message key="ft_BowlShape" var="bowlShapeFacetName"/>
<@fmt.message key="ft_TrapwayType" var="trapwayTypeFacetName"/>
<@fmt.message key="ft_RoughIn" var="roughInFacetName"/>
<@fmt.message key="ft_ProductFamily" var="productFamilyFacetName"/>
<@fmt.message key="ft_Material" var="materialFacetName"/>
<@fmt.message key="ft_LightingType" var="lightingTypeFacetName"/>
<@fmt.message key="ft_MirrorDeFogger" var="mirrorDeFoggerFacetName"/>
<@fmt.message key="ft_FlushingSystem" var="flushingSystemFacetName"/>
<@fmt.message key="ft_BowlStyle" var="bowlStyleFacetName"/>
<@fmt.message key="ft_Style" var="styleFacetName"/>
<@fmt.message key="ft_LengthRange" var="lengthRangeFacetName"/>
<@fmt.message key="ft_GlassThickness" var="glassThicknessFacetName"/>
<@fmt.message key="filter_show_more_label" var="showMoreLabel"/>
<@fmt.message key="filter_show_less_label" var="showLessLabel"/>
<#assign locale = hstRequest.getLocale().getLanguage() />
<#assign locale = locale + "_"+ hstRequest.getLocale().getCountry() />
<#assign showMaxFilter = 0 />
<#if showMaxFiltersReq?? && showMaxFiltersReq?has_content>
	<#assign showMaxFilter = showMaxFiltersReq /> 
</#if>
<header>
	<#if isCollections?has_content>
		<h1 class="koh-search-title">
			${collectionsLabel}
		</h1>
		<#if totalResults??><div class="koh-search-results-count">${totalResults}&nbsp${products}</div></#if>
	<#elseif currentCategory??>
		<h1 class="koh-search-title">
			<#assign currentCatName = currentCategory.getInfo(locale).getName() />${currentCatName}
		</h1>
		<#if leafNode??>
			<div class="koh-search-results-count">${leafNode.count}&nbsp${products}</div>
		</#if>
	</#if>
	<input type="hidden" class="koh-data-attributes" data-koh-section="${section}" data-koh-category="<#if leafNode?? && leafNode.name?has_content>${categories}/${leafNode.name}</#if>" data-koh-url-root="${categories}" data-koh-collections-url="${collectionsUrl}" data-koh-search-url="${searchPageURL}">
</header>
	<input id="category-info" type="hidden" autocomplete="off" data-cookie-category-product="plumbing-apac-bathroom-toilets"/>
	<input class="koh-facet-data-attributes" type="hidden" autocomplete="off" data-koh-type="<#if type?? && type?has_content>${type}</#if>" data-koh-searchterm="<#if search?? && search?has_content>${search}</#if>" data-koh-currentpage="<#if currentPage?? && currentPage?has_content>${currentPage}</#if>" data-koh-orderBy="<#if orderBy?? && orderBy?has_content>${orderBy}</#if>" data-koh-sortBy="<#if sort?? && sort?has_content>${sort}</#if>"/>
	
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
			<li <#if  (orderBy=="relevancy" && sort =="asc") >class="koh-sort-selected"</#if>><span data-sort-orderby="relevancy" data-sort-direction="asc">${relevancy}</span></li>
			<li <#if  (orderBy=="name"  && sort == "asc") >class="koh-sort-selected"</#if>> <span data-sort-orderby="name" data-sort-direction="asc">${nameAZ}</span></li>
			<li <#if  (orderBy=="name"  && sort =="desc") >class="koh-sort-selected"</#if>><span data-sort-orderby="name" data-sort-direction="desc">${nameZA}</span></li>
		</ul>
	</div>
	<div class="koh-search-filters">
		<div class="koh-search-filters-title">${categoriesFilters}</div>
		<div class="koh-search-filters-content">
			<div class="koh-filter-group koh-category">
				<h3 class="koh-filter-group-title">${category}</h3>
				<#if currentCategory??><a class="koh-filter-group-all-link" href="">${showAll}</a></#if>
				<#if leafNode?? && leafNode.name?has_content>
					<ul class="koh-selected-filters">
						<li>
							<button class="koh-selected-filter" data-facet-name="Categories" data-facet-value="${categories}/${leafNode.name}" data-facet-count="<#if leafNode??>${leafNode.count}</#if>">
								<span class="remove">${removeFilter}</span>
								<span class="name">
									<#if currentCategory??>
										<#assign categoryName = currentCategory.getInfo(locale).getName() />${categoryName}
									</#if>
								</span>
								<span class="count">(${leafNode.count})</span>
							</button>
						</li>	
					</ul>
				</#if>
				<#if categoryFolderBean?? && categoryFolderBean?has_content>
					<ul class="koh-available-filters">
		               <#list categoryFolderBean.folders as value>
	               		<li>
							<a href="" data-facet-name="category" data-facet-count="${value.count}" data-facet-value="${categories}/${value.name}">
								<span class="name">${categoryBeanMap[value.name].getInfo(locale).getName()}&nbsp;</span>
								<span class="count">(${value.count})</span>
							</a>
						</li>
		               </#list>
	            	</ul>
            	</#if>
			</div>
			<#assign showMaxFilterCounter = 0 />
			<#assign showLessButon = false>
			<#if NavBeanMap?? && hideFilters?string('yes', 'no') == "no">
				<div class="koh-filter-group koh-filters">
					<h3 class="koh-filter-group-title">${filterBy}</h3>
					<a class="koh-filter-group-all-link" href="">${ClearAll}</a>
				 		<#list NavBeanMap?keys as key>
				 			<#if NavBeanMap[key]??>
			      				<#assign mainList = NavBeanMap[key]/>
			      			<#else>	
			      				<#assign mainList = ""/>
			      			</#if>	
			      			<#if mainList?? && mainList?has_content && mainList.folders?has_content>
			      				<#if ((showLessButon?then('Y', 'N') == 'N') && (showMaxFilter?number != 0) && (showMaxFilterCounter == showMaxFilter?number))>
					 				<#assign showLessButon = true>
					 			</#if>
			      				<#if mainList.name == isNew  && isCollections != "true">
                                      <#list mainList.folders as value>
                                             <#if value.name = "true">
                                             		<#assign showMaxFilterCounter = showMaxFilterCounter + 1>
                                                    <#if value.count &gt; 0>
                                                           <div class="koh-filter koh-filter-type-checkbox">
                                                             <span class="koh-filter-name">${isNew}</span>
                                                             <div class="koh-filter-content">
                                                                  <div class="koh-checkbox-filter">
                                                                      <#if value.leaf>
							                                             <a href="" class="koh-checkbox-checked" data-facet-name="${mainList.name?html}" data-facet-value="${value.name?html}" data-facet-count="${value.count}">
							                                               <span class="icon"></span>
							                                               <span class="label">${newProducts} (${value.count})</span>
							                                             </a>
                                                                   	  <#else>
				                                                          <a href="" class="koh-checkbox-unchecked" data-facet-name="${mainList.name?html}" data-facet-value="${value.name?html}" data-facet-count="${value.count}">
				                                                             <span class="icon"></span>
				                                                             <span class="label">${newProducts} (${value.count})</span>
				                                                          </a>
                                                                     </#if>
                                                                </div>               
                                                             </div>
                                                          </div> 
                                                   </#if>
                                             </#if> 
                                      </#list>
      			<#elseif mainList.name == color && isCollections != "true">
				      				<#assign expand="false" />
									<#list mainList.folders as value>
								  		<#if value.count &gt; 0>
								            <#if value.leaf>
								            	<#assign expand="true" />
								            </#if>
								        </#if>    
									</#list>
									<#if (mainList.folders?size > 1 || expand=="true") >
										<#assign showMaxFilterCounter = showMaxFilterCounter + 1>
										<div class="koh-filter koh-filter-type-colors <#if (showLessButon?then('Y', 'N') == 'Y')>showMoreDropdowns</#if>">
											<span class="koh-filter-name <#if expand=="true">open</#if>">
												<@fmt.message key="ftr-${mainList.name}" var="colorFilter"/>	
											     ${colorFilter}
											</span>
											<div class="koh-filter-content <#if expand=="true">open</#if>">
												<ul class="<#if expand=="true">koh-selected-filters<#else>koh-available-filters v-koh-colors</#if>">
													<#list mainList.folders as value>
												  		<#if value.count &gt; 0>
												            <#if value.leaf>
																<li>
																	<button class="koh-selected-filter" data-facet-name="${mainList.name?html}" data-facet-value="${value.name?html}" data-facet-count="${value.count}">
																		<span class="remove">${removeFilter}</span>
																		<span class="swatch">
																		 <@fmt.message key="imgswatch" var="imgswatch"/>
		                                                                 <#assign swatchImg = imgswatch?replace("{0}","${value.name?html}") />
												                         <img src="${swatchImg}" width="28" height="28" alt="">
																		</span>
																		<span class="label"><#if swatchMap?? && swatchMap[value.name]?has_content>${swatchMap[value.name]?html}</#if></span>
																		<span class="count">(${value.count})</span>
																	</button>
																</li>
															<#elseif expand=="false">
																<li><a href="" title="${value.count}" class="koh-color-unselected" data-facet-name="${mainList.name?html}" data-facet-value="${value.name?html}" data-facet-count="${value.count}">
																     <@fmt.message key="imgswatch" var="imgswatch"/>
		                                                             <#assign swatchImg = imgswatch?replace("{0}","${value.name?html}") />
												                     <img src="${swatchImg}" width="28" height="28" alt="">
																    <span class="label"><#if swatchMap?? && swatchMap[value.name]?has_content>${swatchMap[value.name]?html}</#if>(${value.count})</span></a>
																</li>
															</#if>
														</#if>	
													</#list>
												</ul>	
											</div>
										</div>
									</#if>		
								<#elseif mainList.name == "Country" && isCollections != "true">
									<#assign expand="false" />
									<#list mainList.folders as value>
								  		<#if value.count &gt; 0>
								            <#if value.leaf>
								            	<#assign expand="true" />
								            </#if>
								        </#if>    
									</#list>  
									<#if (mainList.folders?size > 1 || expand=="true") >
										<#if section != "FacetPage">
											<#assign showMaxFilterCounter = showMaxFilterCounter + 1>
									    </#if>
										<div class="koh-filter koh-filter-type-list <#if section == "FacetPage">koh-filter-type-hidden<#else><#if (showLessButon?then('Y', 'N') == 'Y')>showMoreDropdowns</#if></#if> ">
											<span class="koh-filter-name <#if expand=="true">open</#if>">
												<@fmt.message key="${mainList.name}" var="filter"/>
												 ${filter}
											</span>
											<div class="koh-filter-content <#if expand=="true">open</#if>">
												<#if expand=="true">
													<#list mainList.folders as value>
												  		<#if value.count &gt; 0>
												            <#if value.leaf>
												            	<@fmt.message key="${value.name}" var="country"/>
																<ul class="koh-selected-filters">
																	<li>
																		<button class="koh-selected-filter" data-facet-name="${mainList.name?html}" data-facet-value="${value.getProperty("encodedName")}" data-facet-count="${value.count}">
																			<span class="remove">Remove Filter</span>
																			<span class="name">${country?html}</span>
																			<span class="count">(${value.count})</span>
																		</button>
																	</li>
																</ul>
															</#if>
														</#if>	
													</#list>			
												<#elseif expand=="false">
								                    <ul class="koh-available-filters">
								                    	<#list mainList.folders as value>
											  				<#if value.count &gt; 0>
										                    	<@fmt.message key="${value.name}" var="country"/>
																<li>
																	<a href="" data-facet-name="${mainList.name?html}" data-facet-value="${value.getProperty("encodedName")}" data-facet-count="${value.count}">
																		<span class="name">${country?html}</span>
																		<span class="count">(${value.count})</span>
																	</a>
																</li>
															</#if>
														</#list>	
													</ul>
												</#if>
											</div>
										</div>
									</#if>	
								<#elseif (mainList.name == overAllLength || mainList.name == overAllHeight ||  mainList.name == overAllWidth)  && isCollections != "true">
									<@fmt.message key="${mainList.name}mm" var="filterMM"/>
									<#assign expand="false" />
									<#assign selectedMin = "" />
									<#assign selectedMax = "" />
									<#assign initMin = "0" />
									<#assign initMax = "0" />
									<#if hstRequestContext??>
										<#if hstRequestContext.servletRequest.getParameter(filterMM + 'Min')?has_content>
											<#assign selectedMin = hstRequestContext.servletRequest.getParameter(filterMM + 'Min')?html/>
										</#if>
										<#if hstRequestContext.servletRequest.getParameter(filterMM + 'Max')?has_content>
											<#assign selectedMax = hstRequestContext.servletRequest.getParameter(filterMM + 'Max')?html/>
										</#if>
									</#if>
									
									<#if hstRequest.getAttribute(mainList.name + 'InitMin')??>
										<#assign initMin = hstRequest.getAttribute(mainList.name + 'InitMin')?html/>
									</#if>
									<#if hstRequest.getAttribute(mainList.name + 'InitMax')??>
										<#assign initMax = hstRequest.getAttribute(mainList.name + 'InitMax')?html/>
									</#if>
									<#if selectedMin == "" || selectedMin == "">
							            <#assign expand="false" />
							        <#else>
							        	<#assign expand="true" />    
							        </#if>
									<#if selectedMin == "">
										<#assign selectedMin=initMin />
									</#if>	 
									<#if selectedMax == "">
										<#assign selectedMax=initMax />
									</#if>
									<#if (initMin != "0") && (initMax != "0") && (initMin != initMax)>
										<#assign showMaxFilterCounter = showMaxFilterCounter + 1>
										<div class="koh-filter koh-filter-type-range <#if (showLessButon?then('Y', 'N') == 'Y')>showMoreDropdowns</#if>">
										   <span class="koh-filter-name <#if expand=="true">open</#if>">
										   <@fmt.message key="ftr-${mainList.name}" var="filter"/>
											 ${filter}</span>
										   <div class="koh-filter-content <#if expand=="true">open</#if>">
										      <#if expand=="true">
										       	<ul class="koh-selected-filters">
					                                   <li>
					                                         <button class="koh-selected-filter" data-facet-name="${filterMM?html}" data-facet-value="${selectedMin}-${selectedMax}">
					                                              <span class="remove">Remove Filter</span>
					                                              <span class="name">${selectedMin}mm - ${selectedMax}mm</span>
					                                         </button>
					                                   </li>
					                            </ul>
					                          </#if>  
										      <div class="koh-range-filter" data-facet-name="${filterMM?html}">
										         <div class="koh-range-slider-labels" data-label-prefix="" data-label-suffix="mm">
										            <span class="koh-range-slider-min" data-min-value="${selectedMin}">${selectedMin}</span>
										            <span class="koh-range-slider-max" data-max-value="${selectedMax}">${selectedMax}</span>
										         </div>
										         <div class="koh-range-slider noUi-target noUi-ltr noUi-horizontal noUi-background" data-range-min="${initMin}" data-range-max="${initMax}">
										         </div>
										      </div>
										   </div>
										</div>
									</#if>
								<#else>
									<#if (isCollections != "true") || ((isCollections == "true") && mainList.name == productFamilyFacetName)>
										<#assign expand="false" />
										<#list mainList.folders as value>
									  		<#if value.count &gt; 0>
									            <#if value.leaf>
									            	<#assign expand="true" />
									            </#if>
									        </#if>    
										</#list>    
										<#if (mainList.folders?size > 1 || expand=="true") >    
											<#assign showMaxFilterCounter = showMaxFilterCounter + 1>
											<div class="koh-filter koh-filter-type-list <#if (showLessButon?then('Y', 'N') == 'Y')>showMoreDropdowns</#if>">
												<span class="koh-filter-name <#if expand=="true" || isCollections == "true">open</#if>">
													<#switch mainList.name>
													  <#case installationTypeFacetName>
													    <@fmt.message key="ftr-InstallationType" var="filter"/>	
													    <#break>
												      <#case priceRangeFacetName>
													    <@fmt.message key="ftr-PriceRange" var="filter"/>	
													    <#break>
													  <#case featureFacetName>
													    <@fmt.message key="ftr-Feature" var="filter"/>	
													    <#break>
													  <#case shapeFacetName>
													    <@fmt.message key="ftr-Shape" var="filter"/>	
													    <#break>
													  <#case handleTypeFacetName>
													    <@fmt.message key="ftr-HandleType" var="filter"/>	
													    <#break>
													  <#case numberOfHandlesFacetName>
													    <@fmt.message key="ftr-NumberOfHandles" var="filter"/>	
													    <#break>
													  <#case waterSavingFacetName>
													    <@fmt.message key="ftr-WaterSaving" var="filter"/>	
													    <#break>
													  <#case productTypeFacetName>
													    <@fmt.message key="ftr-ProductType" var="filter"/>	
													    <#break>
													  <#case toiletTypeFacetName>
													    <@fmt.message key="ftr-ToiletType" var="filter"/>	
													    <#break>
													  <#case litersperFlushFacetName>
													    <@fmt.message key="ftr-LitersperFlush" var="filter"/>	
													    <#break>
													  <#case bowlShapeFacetName>
													    <@fmt.message key="ftr-BowlShape" var="filter"/>	
													    <#break>            
													  <#case trapwayTypeFacetName>
													    <@fmt.message key="ftr-TrapwayType" var="filter"/>	
													    <#break>
													  <#case roughInFacetName>
													    <@fmt.message key="ftr-RoughIn" var="filter"/>	
													    <#break>
													  <#case productFamilyFacetName>
													    <@fmt.message key="ftr-ProductFamily" var="filter"/>	
													    <#break>
													  <#case materialFacetName>
													    <@fmt.message key="ftr-Material" var="filter"/>	
													    <#break>  
													  <#case lightingTypeFacetName>
													    <@fmt.message key="ftr-LightingType" var="filter"/>	
													    <#break>
												  	  <#case mirrorDeFoggerFacetName>
													    <@fmt.message key="ftr-MirrorDeFogger" var="filter"/>	
													    <#break>
												      <#case flushingSystemFacetName>
													    <@fmt.message key="ftr-FlushingSystem" var="filter"/>	
													    <#break>
												      <#case bowlStyleFacetName>
													    <@fmt.message key="ftr-BowlStyle" var="filter"/>	
													    <#break>
												      <#case styleFacetName>
													    <@fmt.message key="ftr-Style" var="filter"/>	
													    <#break>
												      <#case lengthRangeFacetName>
													    <@fmt.message key="ftr-LengthRange" var="filter"/>	
													    <#break>
												      <#case glassThicknessFacetName>
													    <@fmt.message key="ftr-GlassThickness" var="filter"/>	
													    <#break>       
													  <#default>
													</#switch>
													<#if filter?? && filter?has_content>
														${filter}
													<#else>
													    ${mainList.name}
													</#if>		
												</span>
												<div class="koh-filter-content <#if expand=="true" || isCollections == "true">open</#if>">
													<#if expand=="true">
														<#list mainList.folders as value>
													  		<#if value.count &gt; 0>
													            <#if value.leaf>
																	<ul class="koh-selected-filters">
																		<li>
																			<button class="koh-selected-filter" data-facet-name="${mainList.name?html}" data-facet-value="${value.getProperty("encodedName")}" data-facet-count="${value.count}">
																				<span class="remove">Remove Filter</span>
																				<#assign nameVal = "${value.name?html}" />
																		        	<#assign nameVal = nameVal?replace("(R)","<sup>&reg;</sup>")/>
																    				<#assign nameVal = nameVal?replace("(TM)","<sup>&trade;</sup>")/>
																				<span class="name">${nameVal}</span>
																				<span class="count">(${value.count})</span>
																			</button>
																		</li>
																	</ul>
																</#if>
															</#if>	
														</#list>
													<#elseif expand=="false">
									                    <ul class="koh-available-filters">
									                    	<#list mainList.folders as value>
											  					<#if value.count &gt; 0>
																	<li>
																		<a href="" data-facet-name="${mainList.name?html}" data-facet-value="${value.getProperty("encodedName")}" data-facet-count="${value.count}">
																			<#assign nameVal = "${value.name?html}" />
																        	<#assign nameVal = nameVal?replace("(R)","<sup>&reg;</sup>")/>
														    				<#assign nameVal = nameVal?replace("(TM)","<sup>&trade;</sup>")/>
																			<span class="name">${nameVal}</span>
																			<span class="count">(${value.count})</span>
																		</a>
																	</li>
																</#if>	
															</#list>	
														</ul>
													</#if>
												</div>
											</div>
										</#if>	
									</#if>	
						   		</#if>
						 </#if>  
					</#list>
					<#if ((showMaxFilter?number != 0) && (showMaxFilterCounter > showMaxFilter?number))>
					 	<a href="#" class="categories-and-filters__more-filters-link"><span>${showMoreLabel}</span></a>
					<#else>		
						<#assign showLessButon = false>			 				
		 			</#if>
					<#if (showLessButon?then('Y', 'N') == 'Y')>
		 				<a href="#" class="categories-and-filters__less-filters-link" style="display: none;"><span>${showLessLabel}</span></a>
		 			</#if>
				</div>
			</#if>	
	</div>
</div>			
		
<#if section=="FacetPage">
	<#if seoCategory??>
		<#if seoCategory.getSeo().getSeoTitle()??>
		  <@hst.headContribution category="SEO" keyHint="hst.seo.document.title">
		  	<title>${seoCategory.getSeo().getSeoTitle()?html}</title>
		  </@hst.headContribution>
		</#if>
		<#if seoCategory.getSeo().getSeoDescription()??>
		  <@hst.headContribution category="SEO" keyHint="hst.seo.document.description">
		  <meta name="description" content="${seoCategory.getSeo().getSeoDescription()?html}"/>
		  </@hst.headContribution>
		</#if>
		<#if seoCategory.getSeo().getCanonicalUrl()?? && seoCategory.getSeo().getCanonicalUrl()?has_content>
			<@hst.headContribution category="SEO">
			 	<link rel="canonical" href="${seoCategory.getSeo().getCanonicalUrl()?html}" />
			</@hst.headContribution>
		</#if>
		<#if seoCategory.getSeo().getNoFollow()?? && seoCategory.getSeo().getNoIndex()?? && (seoCategory.getSeo().getNoFollow()?string("yes", "no") == "yes") && (seoCategory.getSeo().getNoIndex()?string("yes", "no") == "yes")>
		  	<@hst.headContribution category="meta-tags">
				<meta name="ROBOTS" content="NOINDEX, NOFOLLOW" />
			</@hst.headContribution>
		<#elseif seoCategory.getSeo().getNoFollow()?? && (seoCategory.getSeo().getNoFollow()?string("yes", "no") == "yes")>
			<@hst.headContribution category="meta-tags">
				<meta name="ROBOTS" content="NOFOLLOW" />
			</@hst.headContribution>
		<#elseif seoCategory.getSeo().getNoIndex()?? && (seoCategory.getSeo().getNoIndex()?string("yes", "no") == "yes")>
			<@hst.headContribution category="meta-tags">
				<meta name="ROBOTS" content="NOINDEX" />
			</@hst.headContribution>	
		</#if>
    </#if>
</#if>
<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
  <script src="${link}/js/nouislider/nouislider.min.js" type="text/javascript"></script>
</@hst.headContribution>	
