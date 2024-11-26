<#include "../include/imports.ftl">
<@fmt.message key="quick-view" var="quickview"/>
<@fmt.message key="select-color" var="selectColor"/>
<@fmt.message key="add-to-compare" var="addCompare"/>
<@fmt.message key="remove-product" var="removeProduct"/>
<@fmt.message key="compare" var="compare"/>
<@fmt.message key="galleryUrl" var="gridImageUrl"/>
<@fmt.message key="common_galleryUrl" var="common_gridImageUrl"/>
<@fmt.message key="galleryNewProductUrl" var="gridImageNewProductUrl"/>
<@fmt.message key="common_galleryNewProductUrl" var="common_galleryNewProductUrl"/>
<@fmt.message key="galleryQuickViewNewProductUrl" var="quickViewImageNewProductUrl"/>
<@fmt.message key="common_galleryQuickViewNewProductUrl" var="common_galleryQuickViewNewProductUrl"/>
<@fmt.message key="compare-products" var="comparePanelProducts"/>
<@fmt.message key="clear-results" var="compareClearResults"/>
<@fmt.message key="collectionsUrl" var="collectionsUrl"/>
<@fmt.message key="productListQuickViewUrl" var="productListQuickViewUrl"/>
<@fmt.message key="common_productListQuickViewUrl" var="common_productListQuickViewUrl"/>
<@fmt.message key="load-more" var="loadMore"/>
<@fmt.message key="imgswatch" var="imgswatch"/>
<@fmt.message key="discontinuedLableWithSku" var="discontinuedLableWithSku"/>
<@fmt.message key="OverallLengthmm" var="OverallLengthmm"/>
<@fmt.message key="OverallHeightmm" var="OverallHeightmm"/>
<@fmt.message key="OverallWidthmm" var="OverallWidthmm"/>
<@fmt.message key="product-currency-code" var="currencyCode"/>
<@fmt.message key="product-price-vat" var="productVatValue"/>
<@fmt.message key="product-price-multiply-value" var="productMultiplicationValue"/>

<#assign productVat = productVatValue?number/>
<#assign productMultiplication = productMultiplicationValue?number/>

<@hst.link var="addToCompareLink" siteMapItemRefId="addToCompare"/>
<@hst.link var="productDetailslink" siteMapItemRefId="productDetails"/>
<@hst.link var="productListLazyLink" siteMapItemRefId="productListLazy"/>

<#assign url=hstRequestContext.servletRequest.getPathInfo() />
<#assign bannerFlag="true" />
<#assign categoryKey= ""/>

<#assign locale = hstRequest.getLocale().getCountry() />

<#if currentCategory??>
	<#assign categoryKey=currentCategory.key />
	<#if url?contains(categoryKey + "/")>
		<#if url?ends_with(categoryKey + "/")>
			<#assign bannerFlag="true" />
		<#else>	
			<#assign bannerFlag="false" />
		</#if>	
	<#else>
		<#list hstRequestContext.servletRequest.getParameterNames() as parameterName>	
			<#if parameterName?contains(OverallLengthmm + "Min") || parameterName?contains(OverallHeightmm + "Min") || parameterName?contains(OverallWidthmm + "Min") >
				<#assign bannerFlag="false" />
			</#if>	
		</#list>
	</#if>	
</#if>

<#if url?contains("/" + collectionsUrl)>
	<#assign bannerFlag="false" />
</#if>	

<#assign isLoadMore="false" />
<#if totalProductCount?? && pageSize?? && ( pageSize < totalProductCount ) >
     <#assign isLoadMore="true" />
</#if> 
<input type="hidden" id="koh-load-more" autocomplete="off" value="${isLoadMore}" />
 
<#if currentSwatch?? && currentSwatch?has_content>
	<input type="hidden" autocomplete="off" id="currentSwatch" value="${currentSwatch?html}" />
<#else>
	<input type="hidden" autocomplete="off" id="currentSwatch" value="" />
</#if>
<#if totalProductCount??>
	<input type="hidden" autocomplete="off" id="totalProducts" value="${totalProductCount?html}" />
</#if>	
<#if pageSize??>
	<input type="hidden" autocomplete="off" id="pageSize" value="${pageSize?html}" />
</#if>
<#if currentPage??>
	<input type="hidden" autocomplete="off" id="currentPage" value="${currentPage?html}" />
</#if>
<input type="hidden" autocomplete="off" id="backFromProductDetailPage" value="false" />

<input type="hidden" autocomplete="off" id="productListLazyLink" value="${productListLazyLink}" />
<input type="hidden" autocomplete="off" id="productListQuickViewUrl" value="${productListQuickViewUrl}" />
<input type="hidden" autocomplete="off" id="common_productListQuickViewUrl" value="${common_productListQuickViewUrl}" />
<input type="hidden" autocomplete="off" id="quickview" value="${quickview}" />
<input type="hidden" autocomplete="off" id="selectColor" value="${selectColor}" />
<input type="hidden" autocomplete="off" id="imgswatch" value="${imgswatch}" />
<input type="hidden" autocomplete="off" id="addToCompareLink" value="${addToCompareLink}" />
<input type="hidden" autocomplete="off" id="productDetailslink" value="${productDetailslink}" />
<input type="hidden" autocomplete="off" id="gridImageProductUrl" value="${gridImageUrl}" />
<input type="hidden" autocomplete="off" id="common_gridImageUrl" value="${common_gridImageUrl}" />
<input type="hidden" autocomplete="off" id="gridImageNewProductUrl" value="${gridImageNewProductUrl}" />
<input type="hidden" autocomplete="off" id="common_galleryNewProductUrl" value="${common_galleryNewProductUrl}" />
<input type="hidden" autocomplete="off" id="comparePanelProducts" value="${comparePanelProducts}" />
<input type="hidden" autocomplete="off" id="compareButtonLable" value="${compare}" />
<input type="hidden" autocomplete="off" id="addCompare" value="${addCompare}" />
<input type="hidden" autocomplete="off" id="removeProduct" value="${removeProduct}" />
<input type="hidden" autocomplete="off" id="compareClearResult" value="${compareClearResults}" />
<input type="hidden" autocomplete="off" id="relativeContentPath" value="${hstRequestContext.getResolvedSiteMapItem().getRelativeContentPath()?html}" />
<input type="hidden" autocomplete="off" id="bannerFlag" value="${bannerFlag}" />
<input type="hidden" autocomplete="off" id="discontinuedLableWithSku" value="${discontinuedLableWithSku}" />
<input type="hidden" autocomplete="off" id="defaultCategoryKey" value="${categoryKey}" />
<input type="hidden" autocomplete="off" id="quickViewImageNewProductUrl" value="${quickViewImageNewProductUrl}" />
<input type="hidden" autocomplete="off" id="common_galleryQuickViewNewProductUrl" value="${common_galleryQuickViewNewProductUrl}" />
<input type="hidden" autocomplete="off" id="currencyCode" value="${currencyCode}" />
	<div class="koh-search-results">
		<#if pageable?? && pageable.items?has_content>
		 <#assign productCount = 2 />
		    <#list pageable.items as item>
		      <#assign isMultyCategoryProduct = "false" />
		      <#assign productCount++>
		      <#assign productCategoryKey="" />
		      <#if item??>
		      	<#assign itemItemNo="" />
			    <#if item.itemNo?has_content>
			    	<#assign itemItemNo=item.itemNo />
			       <#if item.keys??>
			       		<#if (item.keys?size > 1)>
			       			<#assign isMultyCategoryProduct = "true" />
			       		</#if>
			       		<#if categoryKey?has_content>
			       			<#assign productCategoryKey=categoryKey />
			       		<#else>	
			       			<#assign productCategoryKey=item.keys[0] />
			       		</#if>	
			       </#if>
			      <@hst.link var="link" hippobean=item />
                     <#assign productFamily="" />	
			         <#assign productDescription="" />
			         <#assign defaultSku="" />
			         <#assign defaultImage="blank" />
			         <#assign productFamilyAlt="" />
                     <#list item.attributes as productAttributes>
						<#if productAttributes.key == "BRAND_NAME">
							<#assign productFamily = productAttributes.value[0]?replace("(R)","<sup>&reg;</sup>")/>
    						<#assign productFamily = productFamily?replace("(TM)","<sup>&trade;</sup>")/>
    						<#assign productFamilyAlt = productAttributes.value[0]?replace("(R)","") />
    						<#assign productFamilyAlt = productFamilyAlt?replace("(TM)","")/>
						</#if>
						<#if productAttributes.key == "DESCRIPTION_PRODUCT_SHORT"> 
							<#assign productDescription = productAttributes.value[0]?replace("(R)","<sup>&reg;</sup>")/>
    						<#assign productDescription = productDescription?replace("(TM)","<sup>&trade;</sup>")/>
						</#if>
                        <#if productAttributes.key == "DEFAULT_SKU"> 
							 <#assign defaultSku=productAttributes.value[0] />
						</#if>
						<#if productAttributes.key == "IMG_ISO"> 
							<#assign defaultImage=productAttributes.value[0] />
						</#if>
				     </#list>
					 
					 <#assign filterSwatchSKU="" />
					 <#assign selectedSkuitem="" />	
                     <#list item.skus as skuSub>
                     	<#if currentSwatch?? && currentSwatch?has_content>   
                     		<#list skuSub.attributes as skuattr>
                     			<#if skuattr.key?? && skuattr.key?has_content && skuattr.key == "IMG_SWATCH">
                                 	<#assign swatch = skuattr.value[0]/>
                                 	<#if swatch == currentSwatch>
                                 		<#assign filterSwatchSKU=skuSub />
                                 	</#if>
                                 </#if>
                     		</#list>
                     	<#elseif skuSub.sku == defaultSku>
				  	   		<#assign selectedSkuitem=skuSub />
							<#if defaultImage == "blank">
								<#list selectedSkuitem.attributes as skuattr>
									<#if skuattr.key?? && skuattr.key?has_content && skuattr.key == "IMG_ITEM_ISO">
										<#assign defaultImage=skuattr.value[0] />
									</#if>
								</#list>
							</#if>
						</#if>
				     </#list>
					<#if selectedSkuitem == "" && item.skus[0]?? && item.skus[0]?has_content>
						<#assign selectedSkuitem=item.skus[0] />				
					</#if>
					<#if filterSwatchSKU?? && filterSwatchSKU?has_content>
						<#assign selectedSkuitem=filterSwatchSKU />
						<#list filterSwatchSKU.attributes as skuattr>
 							 <#if skuattr.key?? && skuattr.key?has_content && skuattr.key == "IMG_ITEM_ISO">
                             	<#assign defaultImage=skuattr.value[0] />
                             </#if>
                 		</#list>
					</#if>
					<#assign selectedSkuitemSku="" />
					<#assign productPrice="" />
					<#if selectedSkuitem.sku?? && selectedSkuitem.sku?has_content>
						<#assign selectedSkuitemSku=selectedSkuitem.sku />
					</#if>
					<#list selectedSkuitem.attributes as skuattr>
				 		 <#if skuattr.key?? && skuattr.key?has_content && skuattr.key == "LIST_PRICE">
			             	<#assign productPrice = skuattr.value[0]/> 
			             	<#assign productPriceNumber = (productPrice?number) * productVat/>
			             </#if>
			  		</#list>
					 <div class="koh-product-tile">
							<div class="koh-product-tile-inner">
								<div class="koh-product-tile-content">
									<a href="${productDetailslink}/${itemItemNo}?skuid=${selectedSkuitemSku}<#if isMultyCategoryProduct == "true">&defaultCategory=${productCategoryKey}</#if>" data-similar="ID,ID,ID,ID" id="load_${itemItemNo}" data-id="${itemItemNo}" data-default-category="<#if isMultyCategoryProduct == "true">${productCategoryKey}</#if>" data-productlink="${productDetailslink}" data-skuid="${selectedSkuitemSku}">
										<div class="koh-product-image">
											<#assign defImg = defaultImage />
											
											<#if defImg?starts_with("http")>
												<#assign defImgWithoutHttps=defImg?remove_beginning("https://") />
												<#assign defImgFromUrl=defImgWithoutHttps?keep_after_last("/")>
												<#assign defImgDomainFromUrl=defImgWithoutHttps?keep_before_last("/") />
												<#assign defImgDamNameFromUrl=defImgDomainFromUrl?keep_after_last("/") />
												<#if item.isNew>
											  		<#assign defImg = common_galleryNewProductUrl?replace("{0}","${defImgFromUrl}") />
											  	<#else>										     										     
                                              		<#assign defImg = common_gridImageUrl?replace("{0}","${defImgFromUrl}") />
                                              	</#if>
                                              	
                                              	<#assign defImg = defImg?replace("{domain}","${defImgDomainFromUrl}") />
                                          		<#assign defImg = defImg?replace("{dam}","${defImgDamNameFromUrl}") />

												<#if defImgDamNameFromUrl == 'kohlerchina'>
														<#assign defImg = defImg?replace("kohlerchina","PAWEB") />
														<#assign defImg = defImg?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
		 										</#if>
											<#else>
												<#if item.isNew>
											  		<#assign defImg = gridImageNewProductUrl?replace("{0}","${defaultImage}") />
											  	<#else>										     										     
                                              		<#assign defImg = gridImageUrl?replace("{0}","${defaultImage}") />
                                              	</#if>
											</#if>
                                          	<#if locale == "TH">
	                     	                	<img src="${defImg}" width="300" height="232" alt="<#if productFamilyAlt?? && productFamilyAlt?has_content>${productFamilyAlt} </#if>${productDescription}">
	                     	              	<#else>
	                     	              		<img src="${defImg}" width="300" height="232" alt="${selectedSkuitemSku}">
												<#--  <img src="${defImg}" width="300" height="232" alt="${selectedSkuitemSku} <#if productFamilyAlt?? && productFamilyAlt?has_content>${productFamilyAlt} </#if>${productDescription}">  -->
	                     	              	</#if>
		                     	                
											</div>
										<span class="koh-product-description">
											<span class="koh-product-family">${productFamily}</span>
                                             ${productDescription}
										</span>
										<span class="koh-product-sku" data-product-sku="${selectedSkuitemSku}">K-${selectedSkuitemSku}</span>
										<#if currencyCode?? && currencyCode?has_content && productPrice?has_content>
					                     	<span class="koh-product-price">${currencyCode} ${(productPriceNumber?round)*productMultiplication}</span>
			                     	    </#if>
									</a>
								</div>
								
								<div class="koh-product-tile-actions">
									<div class="koh-product-quick-view" data-sku="${itemItemNo}">
										<button class="koh-product-quick-view-button"><span class="label">${quickview}</span></button>
										<div class="koh-product-quick-view-panel">
											<div class="koh-product-image">
												<a href="${productDetailslink}/${itemItemNo}?skuid=${selectedSkuitemSku}<#if isMultyCategoryProduct == "true">&defaultCategory=${productCategoryKey}</#if>" id="${itemItemNo}" data-id="${itemItemNo}" data-default-category="<#if isMultyCategoryProduct == "true">${productCategoryKey}</#if>" data-productlink="${productDetailslink}" data-skuid="${selectedSkuitemSku}">
                                                 	<#assign quickViewImg = defaultImage />
                                                 	<#if quickViewImg?starts_with("http")>
														<#assign quickViewImgWithoutHttps=quickViewImg?remove_beginning("https://") />
														<#assign quickViewImgFromUrl=quickViewImgWithoutHttps?keep_after_last("/")>
														<#assign quickViewImgDomainFromUrl=quickViewImgWithoutHttps?keep_before_last("/") />
														<#assign quickViewImgDamNameFromUrl=quickViewImgDomainFromUrl?keep_after_last("/") />
														<#if item.isNew>
													  		<#assign quickViewImg = common_galleryQuickViewNewProductUrl?replace("{0}","${quickViewImgFromUrl}") />
													  	<#else>										     										     
		                                              		<#assign quickViewImg = common_productListQuickViewUrl?replace("{0}","${quickViewImgFromUrl}") />
		                                              	</#if>
		                                              	<#assign quickViewImg = quickViewImg?replace("{domain}","${quickViewImgDomainFromUrl}") />
													    <#assign quickViewImg = quickViewImg?replace("{dam}","${quickViewImgDamNameFromUrl}") />
														<#if quickViewImgDamNameFromUrl == 'kohlerchina'>
															<#assign quickViewImg = quickViewImg?replace("kohlerchina","PAWEB") />
															<#assign quickViewImg = quickViewImg?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
		 										  		</#if>
													<#else>
		                                                 <#if item.isNew>
		                                                   <#assign quickViewImg = quickViewImageNewProductUrl?replace("{0}","${defaultImage}") />
		                                                 <#else>
		                                                   <#assign quickViewImg = productListQuickViewUrl?replace("{0}","${defaultImage}") /> 
		                                                 </#if>
	                                                 </#if> 
		                                         <img src="${quickViewImg}" width="570" height="413" alt="${selectedSkuitemSku}">
                                               </a>
											</div>
											<div class="koh-product-content">
												<span class="koh-product-description">
												  <a href="${productDetailslink}/${itemItemNo}?skuid=${selectedSkuitemSku}<#if isMultyCategoryProduct == "true">&defaultCategory=${productCategoryKey}</#if>" id="${itemItemNo}" data-id="${itemItemNo}" data-default-category="<#if isMultyCategoryProduct == "true">${productCategoryKey}</#if>" data-productlink="${productDetailslink}" data-skuid="${selectedSkuitemSku}"><span class="koh-product-family">${productFamily}</span> ${productDescription}</a>
												</span>
												<span class="koh-product-sku" data-product-sku="${selectedSkuitemSku}">K-${selectedSkuitemSku}</span>
												<#if currencyCode?? && currencyCode?has_content && productPrice?has_content>
							                     	<span class="koh-product-price">${currencyCode} ${(productPriceNumber?round)*productMultiplication}</span>
					                     	    </#if>
												<div class="koh-product-colors">
											      <span class="koh-selected-color">
											          <span class="label">${selectColor}</span>
											          <#assign selecctedSwatch = ""/>
											          <#if selectedSkuitem.attributes??>
												          <#list selectedSkuitem.attributes as skuattr>
							                                 <#if skuattr.key == "COLOR_FINISH_NAME">
							                                 	<span class="value">${skuattr.value[0]}</span>
							                                 </#if>
															 <#if skuattr.key?? && skuattr.key?has_content && skuattr.key == "IMG_SWATCH">
							                                 	<#assign selecctedSwatch = skuattr.value[0]/>
							                                 </#if>
						                                  </#list>
						                              </#if>    	
											      </span>
											        <ul>
											        	<#if item.skus??>
												         <#list item.skus as skuitem>   
												         		<#assign color = ""/>
												         		<#assign isDiscontinued = "false">
																<#assign productPrice = "" />
													        	<#list skuitem.attributes as skuattr>
									                                 <#if skuattr.key?? && skuattr.key?has_content && skuattr.key == "IMG_ITEM_ISO">
									                                 	<#assign img = skuattr.value[0]/> 
									                                 </#if>
									                                 <#if skuattr.key?? && skuattr.key?has_content && skuattr.key == "COLOR_FINISH_NAME">
									                                 	<#assign color = skuattr.value[0]/>
									                                 </#if> 
									                                 <#if skuattr.key?? && skuattr.key?has_content && skuattr.key == "IMG_SWATCH">
									                                 	<#assign swatch = skuattr.value[0]/>
									                                 </#if>
									                                 <#if skuattr.key?? && skuattr.key?has_content && skuattr.key == "DISCONTINUED_DATE">
									                                 	<#assign isDiscontinued = "true">
									                                 </#if>
																	 <#if skuattr.key?? && skuattr.key?has_content && skuattr.key == "LIST_PRICE">
																		<#assign productPrice = skuattr.value[0]/> 
																		<#assign productPriceNumber = (productPrice?number) * productVat/>
																	 </#if>
								                                </#list>
								                                <#if isDiscontinued == "true"> 
								                                	<#assign color = color + " (${discontinuedLableWithSku})" /> 
								                                </#if>
											            	  <#if img?? && img?has_content>
											            	  	<#if img?starts_with("http")>
																	<#assign quickViewImgWithoutHttps=img?remove_beginning("https://") />
																	<#assign quickViewImgFromUrl=quickViewImgWithoutHttps?keep_after_last("/")>
																	<#assign quickViewImgDomainFromUrl=quickViewImgWithoutHttps?keep_before_last("/") />
																	<#assign quickViewImgDamNameFromUrl=quickViewImgDomainFromUrl?keep_after_last("/") />
																	<#if item.isNew>
																  		<#assign quickViewImg = common_galleryQuickViewNewProductUrl?replace("{0}","${quickViewImgFromUrl}") />
																  	<#else>										     										     
					                                              		<#assign quickViewImg = common_productListQuickViewUrl?replace("{0}","${quickViewImgFromUrl}") />
					                                              	</#if>
					                                              	<#assign quickViewImg = quickViewImg?replace("{domain}","${quickViewImgDomainFromUrl}") />
				                                              		<#assign quickViewImg = quickViewImg?replace("{dam}","${quickViewImgDamNameFromUrl}") />
																	<#if quickViewImgDamNameFromUrl == 'kohlerchina'>
																		<#assign quickViewImg = quickViewImg?replace("kohlerchina","PAWEB") />
																		<#assign quickViewImg = quickViewImg?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
		 										  					</#if>
																<#else>
												            	    <#if item.isNew>
		                                                            	<#assign quickViewImg = quickViewImageNewProductUrl?replace("{0}","${img}") />
		                                                            <#else>	
		                                                            	<#assign quickViewImg = productListQuickViewUrl?replace("{0}","${img}") />
		                                                            </#if>
		                                                        </#if>
	                                                         <li>   
											                    <span class="koh-product-variant <#if ((selectedSkuitem.id == skuitem.id) && (selecctedSwatch == swatch))>koh-selected-variant</#if> <#if isDiscontinued == "true">koh-discontinued</#if>" data-koh-image="${quickViewImg}" data-koh-sku="${skuitem.sku}" data-koh-sku-k="K-${skuitem.sku}" data-koh-price="${currencyCode} ${(productPriceNumber?round)*productMultiplication}" data-koh-color="${color}" data-default-category="<#if isMultyCategoryProduct == "true">${productCategoryKey}</#if>">
											                        <button class="koh-product-color" data-hasqtip="${swatch}">
																		<#assign swatchImg = imgswatch?replace("{0}","${swatch}") />
																		<img src="${swatchImg}" width="28" height="28" alt="${skuitem.sku}"> 
											                            <span class="label">${color}</span>
											                        </button>
											                    </span>
											                </li>
											              </#if> 
											             </#list> 
											           </#if>     
											        </ul>
										      	</div>
										      	<#if isCompare?? && isCompare?has_content && isCompare == 'true'>
													<div class="koh-product-tools">
														<a data-href="${addToCompareLink}" class="koh-compare-add koh-compare-button-tile add_${itemItemNo}" data-product-itemno="${itemItemNo}"   data-category="<#if productCategoryKey?has_content>${productCategoryKey}</#if>" data-product-itemno-multianscestors="${itemItemNo}<#if isMultyCategoryProduct == "true">!_${productCategoryKey}</#if>">${compare}</a>
	            										<a data-href="${addToCompareLink}" class="koh-compare-removes koh-compare-button-tile remove_${itemItemNo}" data-product-itemno="${itemItemNo}"   data-category="<#if productCategoryKey?has_content>${productCategoryKey}</#if>" data-product-itemno-multianscestors="${itemItemNo}<#if isMultyCategoryProduct == "true">!_${productCategoryKey}</#if>">${compare}</a>
													</div>
												</#if>
											</div>
										</div>
									</div>
									<#if isCompare?? && isCompare?has_content && isCompare == 'true'>
										<a data-href="${addToCompareLink}" class="koh-compare-add koh-compare-button-tile add_${itemItemNo}" data-product-itemno="${itemItemNo}"  data-category="<#if productCategoryKey?has_content>${productCategoryKey}</#if>" data-product-itemno-multianscestors="${itemItemNo}<#if isMultyCategoryProduct == "true">!_${productCategoryKey}</#if>">${addCompare}</a> 
									    <a data-href="${addToCompareLink}" class="koh-compare-removes koh-compare-button-tile remove_${itemItemNo}" data-product-itemno="${itemItemNo}"   data-category="<#if productCategoryKey?has_content>${productCategoryKey}</#if>" data-product-itemno-multianscestors="${itemItemNo}<#if isMultyCategoryProduct == "true">!_${productCategoryKey}</#if>">${removeProduct}</a>
								    </#if> 
							</div>
						</div>
					 </div>
				  </#if>
               </#if>
               <#if bannerFlag == "true" && (productCount%11) == 0>
				 <#if promoBanners??>
					<#list promoBanners as promoBanner>
					  <#assign target="_blank" />
					  <#if promoBanner.navigationURL?starts_with("/")>
					    <#assign target="_self" />
					  </#if>
					  <#if promoBanner?? && promoBanner.imageURL?has_content>
						<a class="koh-product-tile koh-grid-promo-banner" style="background-image: url(${promoBanner.imageURL});" <#if promoBanner?? && promoBanner.navigationURL?has_content> href="${promoBanner.navigationURL}" </#if> target="${target}">
							<img src="${promoBanner.imageURL}" alt="Promo banner"/>
						</a>
					  </#if>		
					</#list>
				 </#if>
					<#assign productCount = productCount+1/>
				</#if> 
		   </#list>
		</#if>
		<#if totalPages?? && (totalPages gt 1)>
		  <div class="koh-search-pagination-wrapper">	
			<#include "../include/pagination_search.ftl">
		  </div>
		</#if>
	</div>
	 <input type="hidden" autocomplete="off" id="bannerProductCount" value="<#if (productCount?? && productCount?has_content)>${productCount}</#if>"/>
<@hst.webfile path="/" var="link" />	 
<@hst.headContribution category="ext-scripts">
  <script src="${link}/js/custom/koh-compare-load.min.js" type="text/javascript"></script>
</@hst.headContribution>
<#if bannerFlag == "false">
    <@hst.headContribution category="ext-scripts">
              <script>
                           var  loadScrollPositionFilter = function(){
                                  var  kohBanner = $('.koh-banner-content');
                               if( kohBanner.length ){
                                         var kohBannerHeight = kohBanner.height()
                                         $("html, body").animate({ scrollTop: kohBannerHeight + 100}, 1000, function(){
                                         });                        
                                  }else{
                                         $("html, body").animate({ scrollTop: 0}, 600, function(){
                                         });
                                  }      
                                }        
                          $(window).on("load", function() {
                           		var backFromProductDetailPage = false;
                           		if($('#backFromProductDetailPage').length)
				        		 {
				        			 backFromProductDetailPage = $('#backFromProductDetailPage').val();
				        			 console.log ('backFromProductDetailPage: ' + backFromProductDetailPage);
				        		 }
				        		 if (backFromProductDetailPage === 'false') {
                                  	loadScrollPositionFilter();
                                 }
                           });
              </script>
       </@hst.headContribution> 
</#if>
