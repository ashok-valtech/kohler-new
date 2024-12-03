<#include "../include/imports.ftl">

<@hst.include ref="container"/>

<@fmt.message key="categories-filters" var="categoriesFilters"/>
<@fmt.message key="category" var="category"/>
<@fmt.message key="quick-view" var="quickview"/>
<@fmt.message key="back-to-top" var="backTotop"/>
<@fmt.message key="select-color" var="selectColor"/>
<@fmt.message key="productCatMetaDesc" var="productCatMetaDesc"/>
<@fmt.message key="galleryUrl" var="gridImageUrl"/>
<@fmt.message key="common_galleryUrl" var="common_gridImageUrl"/>
<@fmt.message key="galleryNewProductUrl" var="gridImageNewProductUrl"/>
<@fmt.message key="common_galleryNewProductUrl" var="common_gridImageNewProductUrl"/>
<@fmt.message key="galleryQuickViewNewProductUrl" var="quickViewImageNewProductUrl"/>
<@fmt.message key="common_galleryQuickViewNewProductUrl" var="common_quickViewImageNewProductUrl"/>
<@fmt.message key="productListQuickViewUrl" var="productListQuickViewUrl"/>
<@fmt.message key="common_productListQuickViewUrl" var="common_productListQuickViewUrl"/>
<@fmt.message key="categories" var="categoriesRootUrl"/>
<@fmt.message key="discontinuedLableWithSku" var="discontinuedLableWithSku"/>
<@fmt.message key="imgswatch" var="imgswatch"/>
<@fmt.message key="product-currency-code" var="currencyCode"/>
<@fmt.message key="product-price-vat" var="productVatValue"/>
<@fmt.message key="product-price-multiply-value" var="productMultiplicationValue"/>

<#assign productVat = productVatValue?number/>
<#assign productMultiplication = productMultiplicationValue?number/>


<#-- <#assign locale = hstRequest.getLocale().getLanguage() />
<#assign locale = locale + "_"+ hstRequest.getLocale().getCountry() /> -->


<#--  <#assign res900 = "&wid=163&hei=122"/>
<#assign res1000 = "&wid=196&hei=147" />  -->

<section class="c-koh-product-faceted-search v-koh-uber-category">
   <header>
      <h1 class="koh-search-title">
      	<#if currentCategory??> 
        	<#assign currentCatName = currentCategory.getName()/>
    		<#if currentCatName??>
    			${currentCatName}
    		</#if>
        </#if>
      </h1>
      <input type="hidden" class="koh-data-attributes" data-koh-section="<#if currentCategory??>${currentCategory.name}</#if>" data-koh-url-root="${categoriesRootUrl}"">
   </header>
   <div class="koh-search-filters">
      <div class="koh-search-filters-title">${categoriesFilters}</div>
      <div class="koh-search-filters-content">
         <div class="koh-filter-group koh-category">
            <h3 class="koh-filter-group-title">${category}</h3>
            <ul class="koh-available-filters">
               <#list products?keys as key>
               <#assign value = facet[key]/>
               <li>
                  <@hst.link var="link" hippobean=value />
                  <a href="${link}" data-facet-name="category" data-facet-value="${categoriesRootUrl}/${key}" data-facet-count="${value.count}">
                  <span class="name">
                     <#assign categoryName = categories[key].getInfo(locale).getName() />
                     ${categoryName}
                  </span>
                  <span class="count">(${value.count})</span>
                  </a>
               </li>
               </#list>
            </ul>
         </div>
      </div>
   </div>
   <div class="koh-search-results">
      <#list products?keys as key>
      <#assign value = facet[key] />
      <@hst.link var="link" hippobean=value />
      <div class="koh-result-set">
         <h2 class="koh-result-set-title">
            <a href="${link}">
            <span class="label">
              <#assign categoryName = categories[key].getName() />
              <#if categoryName??>
              	${categoryName}
              </#if>
            </span>
            <span class="icon" data-icon="&#xe612"></span>
            </a>
         </h2>
         <#assign productList = products[key] />
         <#list productList as product> 
         	 <@hst.link var="productDetailslink" siteMapItemRefId="productDetails"/>

			<#assign productFamily="" />	
			<#assign productDescription="" />
			<#assign defaultSku="" />
			<#assign defaultImage="blank" />
			<#assign isMultyCategoryProduct = "false" />
			<#--  <#assign productFamilyAlt="" />  -->
			 <#list product.attributes as productAttributes>
				<#if productAttributes.key == "BRAND_NAME">
					<#assign productFamily = productAttributes.value[0]?replace("(R)","<sup>&reg;</sup>")/>
					<#assign productFamily = productFamily?replace("(TM)","<sup>&trade;</sup>")/>
					<#--  <#assign productFamilyAlt = productAttributes.value[0]?replace("(R)","") />
                    <#assign productFamilyAlt = productFamilyAlt?replace("(TM)","")/>  -->
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
             <#assign selectedSkuitem="" />
             <#assign productPrice="" />
			 <#list product.skus as skuSub>   
		  	   <#if skuSub.sku == defaultSku>
	  	   			<#assign selectedSkuitem=skuSub />
		  	   </#if>
	  		 </#list>
	  		 <#list selectedSkuitem.attributes as skuattr>
		 		 <#if skuattr.key?? && skuattr.key?has_content && skuattr.key == "LIST_PRICE">
	             	<#assign productPrice = skuattr.value[0]/> 
	             	<#assign productPriceNumber = (productPrice?number) * productVat/> 
	             </#if>
	  		 </#list>
			  <#if selectedSkuitem == "">
				<#assign selectedSkuitem=product.skus[0] />	
			  </#if>
			  <#if product.keys??>
		       		<#if (product.keys?size > 1)>
		       			<#assign isMultyCategoryProduct = "true" />
		       		</#if>
		       </#if>
	         <div class="koh-product-tile">
	            <div class="koh-product-tile-inner">
		               <div class="koh-product-tile-content">
		                  <a href="${productDetailslink}/${product.itemNo}?skuid=${selectedSkuitem.sku}<#if isMultyCategoryProduct == "true">&defaultCategory=${key}</#if>" data-similar="ID,ID,ID,ID" id="${product.itemNo}" data-id="${product.itemNo}" data-default-category="<#if isMultyCategoryProduct == "true">${key}</#if>"  data-productlink="${productDetailslink}" data-skuid="${selectedSkuitem.sku}">
		                     <div class="koh-product-image">
							 	<#if defaultImage == "blank">
									<#list selectedSkuitem.attributes as skuattr>
										<#if skuattr.key?? && skuattr.key?has_content && skuattr.key == "IMG_ITEM_ISO">
											<#assign defaultImage=skuattr.value[0] />
										</#if>
                 					</#list>
								</#if>
		                     	<#if defaultImage?starts_with("http")>
									<#assign defImgWithoutHttps=defaultImage?remove_beginning("https://") />
									<#assign defImgFromUrl=defImgWithoutHttps?keep_after_last("/")>
									<#assign defImgDomainFromUrl=defImgWithoutHttps?keep_before_last("/") />
									<#assign defImgDamNameFromUrl=defImgDomainFromUrl?keep_after_last("/") />
									<#if product.isNew>
								  		<#assign defImg = common_gridImageNewProductUrl?replace("{0}","${defImgFromUrl}") />
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
			                     	<#if product.isNew>
								  		<#assign defImg = gridImageNewProductUrl?replace("{0}","${defaultImage}") />
								    <#else>
	                               		<#assign defImg = gridImageUrl?replace("{0}","${defaultImage}") />
	                               	</#if>
	                            </#if>
		                     	<img src="${defImg}" width="300" height="232" alt="${selectedSkuitem.sku}">
								<#--  <picture>
                                    <source media="(min-width:1600px)" srcset="${defImg}">
                                    <source media="(min-width:1000px)" srcset="${defImg+res1000}">
                                    <source media="(max-width:1000px)" srcset="${defImg+res900}">
                                    <img src="${defImg+res900}" alt="${selectedSkuitem.sku} <#if productFamilyAlt?? && productFamilyAlt?has_content>${productFamilyAlt} </#if>${productDescription}">
                                </picture>  -->
		                     </div>
		                     <span class="koh-product-description">
		                     	<span class="koh-product-family">${productFamily}</span>
                                 ${productDescription}
		                     </span>
		                     <span class="koh-product-sku">K-${selectedSkuitem.sku}</span>
		                     <#if currencyCode?? && currencyCode?has_content && productPrice?has_content>
		                     	<span class="koh-product-price">${currencyCode} ${(productPriceNumber?round)*productMultiplication}</span>
	                     	 </#if>
		                  </a>
		               </div>
		               
       					<div class="koh-product-tile-actions">
							<div class="koh-product-quick-view">
								<button class="koh-product-quick-view-button"><span class="label">${quickview}</span></button>
								<div class="koh-product-quick-view-panel">
									<div class="koh-product-image">
									 <a href="${productDetailslink}/${product.itemNo}?skuid=${selectedSkuitem.sku}<#if isMultyCategoryProduct == "true">&defaultCategory=${key}</#if>" id="${product.itemNo}" data-id="${product.itemNo}" data-default-category="<#if isMultyCategoryProduct == "true">${key}</#if>"  data-productlink="${productDetailslink}" data-skuid="${selectedSkuitem.sku}">
		                                 <#if defaultImage?starts_with("http")>
											<#assign quickViewImgWithoutHttps=defaultImage?remove_beginning("https://") />
											<#assign quickViewImgFromUrl=quickViewImgWithoutHttps?keep_after_last("/")>
											<#assign quickViewImgDomainFromUrl=quickViewImgWithoutHttps?keep_before_last("/") />
											<#assign quickViewImgDamNameFromUrl=quickViewImgDomainFromUrl?keep_after_last("/") />
											<#if product.isNew>
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
			                                 <#if product.isNew>
		                                        <#assign quickViewImg = quickViewImageNewProductUrl?replace("{0}","${defaultImage}") />
			                                 <#else>   
		                                        <#assign quickViewImg = productListQuickViewUrl?replace("{0}","${defaultImage}") />
		                                     </#if>
	                                    </#if> 
		                                  <img src="${quickViewImg}" width="570" height="413" alt="${selectedSkuitem.sku}">  
									 </a>
									</div>
									<div class="koh-product-content">
										<span class="koh-product-description">
											<a href="${productDetailslink}/${product.itemNo}?skuid=${selectedSkuitem.sku}" id="${product.itemNo}" data-id="${product.itemNo}" data-default-category="<#if isMultyCategoryProduct == "true">${key}</#if>"  data-productlink="${productDetailslink}" data-skuid="${selectedSkuitem.sku}"><span class="koh-product-family">${productFamily}</span> ${productDescription}</a>
										</span>
				                           	<span class="koh-product-sku" data-product-sku="${selectedSkuitem.sku}">K-${selectedSkuitem.sku}</span>
				                           	<#if currencyCode?? && currencyCode?has_content && productPrice?has_content>
						                     	<span class="koh-product-price">${currencyCode} ${(productPriceNumber?round)*productMultiplication}</span>
					                     	</#if>
											<div class="koh-product-colors">
											      <span class="koh-selected-color">
											          <span class="label">${selectColor}</span>
											          <#assign selecctedSwatch = ""/>
											          <#list selectedSkuitem.attributes as skuattr>
						                                 <#if skuattr.key == "COLOR_FINISH_NAME">
						                                 	<span class="value">${skuattr.value[0]}</span>
						                                 </#if>
														 <#if skuattr.key?? && skuattr.key?has_content && skuattr.key == "IMG_SWATCH">
						                                 	<#assign selecctedSwatch = skuattr.value[0]/>
						                                 </#if>
					                                  </#list>	
											      </span>
											      <ul>
												   	  <#list product.skus as skuitem>   
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
																	<#if product.isNew>
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
                                                                  <#if product.isNew>
                                                                     <#assign quickViewImg = quickViewImageNewProductUrl?replace("{0}","${img}") />
                                                                  <#else> 
                                                                     <#assign quickViewImg = productListQuickViewUrl?replace("{0}","${img}") /> 
                                                                  </#if>
                                                               </#if>
                                                              	<li>  
												                    <span class="koh-product-variant <#if ((selectedSkuitem.id == skuitem.id) && (selecctedSwatch == swatch))>koh-selected-variant</#if> <#if isDiscontinued == "true">koh-discontinued</#if>" data-koh-image="${quickViewImg}" data-koh-sku="${skuitem.sku}"  data-koh-sku-k="K-${skuitem.sku}" data-koh-price="${currencyCode} ${(productPriceNumber?round)*productMultiplication}" data-koh-color="${color}" data-default-category="<#if isMultyCategoryProduct == "true">${key}</#if>">
												                        <button class="koh-product-color" data-hasqtip="${swatch}">
																			<@fmt.message key="imgswatch" var="imgswatch"/>
																			<#assign swatchImg = imgswatch?replace("{0}","${swatch}") />
												                            <img src="${swatchImg}" width="28" height="28" alt="${skuitem.sku}">  
												                            <span class="label">${color}</span>
												                        </button>
												                    </span>
										               	 		</li>
										               	 	</#if>
											        </#list>
												 </ul>
								      	 </div>
									</div>
								</div>
							</div>
						</div>
	            </div>
	         </div>
         </#list>	
      </div>
      </#list>
   </div>
   <div class="koh-back-top">
      <button>
      <span class="icon" data-icon="&#xe613"></span>
      ${backTotop}
      </button>
   </div>
</section>
