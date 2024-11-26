<#include "../include/imports.ftl">
<@fmt.message key="compare-products" var="compareProducts"/>
<@fmt.message key="print" var="print"/>
<@fmt.message key="remove" var="remove"/>
<@fmt.message key="back" var="back"/>
<@fmt.message key="back-to-top" var="backTotop"/>
<@fmt.message key="prev" var="prev"/>
<@fmt.message key="next" var="next"/>
<@fmt.message key="regular" var="regular"/>
<@fmt.message key="galleryUrl" var="gridImageUrl"/>
<@fmt.message key="galleryNewProductUrl" var="gridImageNewProductUrl"/>
<@hst.link var="productDetailslink" siteMapItemRefId="productDetails"/>
<@fmt.message key="imgswatch" var="imgswatch"/>
<@fmt.message key="dimensionunit" var="dimensionscompare"/>
<@fmt.message key="discontinuedLableWithSku" var="discontinuedLableWithSku"/>

<@fmt.message key="common_galleryNewProductUrl" var="common_gridImageNewProductUrl"/>
<@fmt.message key="common_galleryUrl" var="common_gridImageUrl"/>
<@fmt.message key="product-currency-code" var="currencyCode"/>
<@fmt.message key="product-price-vat" var="productVatValue"/>
<@fmt.message key="product-price-multiply-value" var="productMultiplicationValue"/>

<#assign productVat = productVatValue?number/>
<#assign productMultiplication = productMultiplicationValue?number/>

<#assign brandNameCount =0/>
<#assign noOfHandlesCount=0 />
<#assign waterSavingCount =0/>
<#assign numberOfHandlesCount =0/>
<#assign productInstallationTypeCount =0/>
<#assign lengthCount=0 />
<#assign heightCount=0 />
<#assign widthCount=0 />
<#assign depthCount=0 />
<#assign noOfHolesCount=0 />
<#assign handleStyleCount=0 />
<#assign waterConservationCount=0 />
<#assign shapeCount=0 />
<#assign materialCount=0 />
<#assign litersPerFlushCount=0 />
<#assign trapWayTypeCount=0 />
<#assign roughInCount=0 />
<#assign toiletTypeCount=0 />
<#assign priceRangeCount=0 />
<#assign productTypeCount=0 />

<#assign brandNameVal_1="-"/>
<#assign noOfHandlesVal_1="-" />
<#assign waterSavingVal_1="-"/>
<#assign handleTypeVal_1="-"/>
<#assign numberOfHandlesVal_1="-"/>
<#assign productInstallationTypeVal_1="-" />
<#assign lengthVal_1="-" />
<#assign heightVal_1="-" />
<#assign widthVal_1="-" />
<#assign depthVal_1="-" />
<#assign noOfHolesVal_1="-" />
<#assign handleStyleVal_1="-" />
<#assign waterConservationVal_1="-" />
<#assign materialtVal_1="-" />
<#assign shapeVal_1="-" />
<#assign litersPerFlushVal_1="-" />
<#assign trapWayTypeVal_1="-" />
<#assign roughInVal_1="-" />
<#assign toiletTypeVal_1="-" />
<#assign priceRangeVal_1="" />
<#assign productTypeVal_1="-"/>

<#assign brandNameVal_2="-"/>
<#assign noOfHandlesVal_2="-" />
<#assign waterSavingVal_2="-"/>
<#assign handleTypeVal_2="-"/>
<#assign numberOfHandlesVal_2="-"/>
<#assign productInstallationTypeVal_2="-" />
<#assign lengthVal_2="-" />
<#assign heightVal_2="-" />
<#assign widthVal_2="-" />
<#assign depthVal_2="-" />
<#assign noOfHolesVal_2="-" />
<#assign handleStyleVal_2="-" />
<#assign waterConservationVal_2="-" />
<#assign materialtVal_2="-" />
<#assign shapeVal_2="-" />
<#assign litersPerFlushVal_2="-" />
<#assign trapWayTypeVal_2="-" />
<#assign roughInVal_2="-" />
<#assign toiletTypeVal_2="-" />
<#assign priceRangeVal_2="" />
<#assign productTypeVal_2="-"/>

<#assign brandNameVal_3="-"/>
<#assign noOfHandlesVal_3="-" />
<#assign waterSavingVal_3="-"/>
<#assign handleTypeVal_3="-"/>
<#assign numberOfHandlesVal_3="-"/>
<#assign productInstallationTypeVal_3="-" />
<#assign lengthVal_3="-" />
<#assign heightVal_3="-" />
<#assign widthVal_3="-" />
<#assign depthVal_3="-" />
<#assign noOfHolesVal_3="-" />
<#assign handleStyleVal_3="-" />
<#assign waterConservationVal_3="-" />
<#assign materialtVal_3="-" />
<#assign shapeVal_3="-" />
<#assign litersPerFlushVal_3="-" />
<#assign trapWayTypeVal_3="-" />
<#assign roughInVal_3="-" />
<#assign toiletTypeVal_3="-" />
<#assign priceRangeVal_3="" />
<#assign productTypeVal_3="-"/>

<@hst.include ref="container"/>

 <#if items??>
    <#assign itemCounter = 1/>
	<#list items as item>
	  <#list item.skus as skuitem>   
		<#list skuitem.attributes as skuattr>
			<#if skuattr.key == "PRICE_RANGE">
				  <#if skuattr.value[0]?? && skuattr.value[0]?has_content >
				 		 <#assign priceRangeCount = priceRangeCount+1/>
				 		 <#if itemCounter == 1>
                 	 		<#assign priceRangeVal_1=skuattr.value[0]/>
	                 	 <#elseif itemCounter == 2>
	                 	 	<#assign priceRangeVal_2=skuattr.value[0]/>
	                 	 <#elseif itemCounter == 3>
	                 	 	<#assign priceRangeVal_3=skuattr.value[0]/>
	                 	 </#if>
				  </#if>
			</#if>
		</#list>
	</#list>
		<#list item.attributes as attribute>	
			<#if attribute.key == "BRAND_NAME">
				 <#assign productBrand = attribute.value[0]?replace("(R)","<sup>&reg;</sup>")/>
                 <#assign productBrand = productBrand?replace("(TM)","<sup>&trade;</sup>")/>
                 <#if productBrand?? && productBrand?has_content >
                 	<#assign brandNameCount = brandNameCount+1/>
                 	<#if itemCounter == 1>
                 	 	<#assign brandNameVal_1=productBrand/>
                 	 <#elseif itemCounter == 2>
                 	 	<#assign brandNameVal_2=productBrand/>
                 	 <#elseif itemCounter == 3>
                 	 	<#assign brandNameVal_3=productBrand/>
                 	 </#if>	
                 </#if>											
			</#if>
			 <#if attribute.key == "MATERIAL">
				  <#if attribute.value[0]?? && attribute.value[0]?has_content >
				 		 <#assign materialCount = materialCount+1/>
				 		 <#if itemCounter == 1>
                 	 		<#assign materialVal_1=attribute.value[0]/>
	                 	 <#elseif itemCounter == 2>
	                 	 	<#assign materialVal_2=attribute.value[0]/>
	                 	 <#elseif itemCounter == 3>
	                 	 	<#assign materialVal_3=attribute.value[0]/>
	                 	 </#if>
				  </#if>
			</#if>
			 <#if attribute.key == "SHAPE">
				  <#if attribute.value[0]?? && attribute.value[0]?has_content >
				 		 <#assign shapeCount = shapeCount+1/>
				 		 <#if itemCounter == 1>
                 	 		<#assign shapeVal_1=attribute.value[0]/>
	                 	 <#elseif itemCounter == 2>
	                 	 	<#assign shapeVal_2=attribute.value[0]/>
	                 	 <#elseif itemCounter == 3>
	                 	 	<#assign shapeVal_3=attribute.value[0]/>
	                 	 </#if>
				  </#if>
			</#if>
			<#if attribute.key == "LITERS_PER_FLUSH">
				  <#if attribute.value[0]?? && attribute.value[0]?has_content >
				 		 <#assign litersPerFlushCount = litersPerFlushCount+1/>
				 		 <#if itemCounter == 1>
                 	 		<#assign litersPerFlushVal_1=attribute.value[0]/>
	                 	 <#elseif itemCounter == 2>
	                 	 	<#assign litersPerFlushVal_2=attribute.value[0]/>
	                 	 <#elseif itemCounter == 3>
	                 	 	<#assign litersPerFlushVal_3=attribute.value[0]/>
	                 	 </#if>
				  </#if>
			</#if>
			<#if attribute.key == "TRAPWAY_TYPE">
				  <#if attribute.value[0]?? && attribute.value[0]?has_content >
				 		 <#assign trapWayTypeCount = trapWayTypeCount+1/>
				 		 <#if itemCounter == 1>
                 	 		<#assign trapWayTypeVal_1=attribute.value[0]/>
	                 	 <#elseif itemCounter == 2>
	                 	 	<#assign trapWayTypeVal_2=attribute.value[0]/>
	                 	 <#elseif itemCounter == 3>
	                 	 	<#assign trapWayTypeVal_3=attribute.value[0]/>
	                 	 </#if>
				  </#if>
			</#if>
			<#if attribute.key == "MINIMUM_ROUGH_IN">
				  <#if attribute.value[0]?? && attribute.value[0]?has_content >
				 		 <#assign roughInCount = roughInCount+1/>
				 		 <#if itemCounter == 1>
                 	 		<#assign roughInVal_1=attribute.value[0]/>
	                 	 <#elseif itemCounter == 2>
	                 	 	<#assign roughInVal_2=attribute.value[0]/>
	                 	 <#elseif itemCounter == 3>
	                 	 	<#assign roughInVal_3=attribute.value[0]/>
	                 	 </#if>
				  </#if>
			</#if>
			<#if attribute.key == "TOILET_TYPE">
				  <#if attribute.value[0]?? && attribute.value[0]?has_content >
				 		 <#assign toiletTypeCount = toiletTypeCount+1/>
				 		 <#if itemCounter == 1>
                 	 		<#assign toiletTypeVal_1=attribute.value[0]/>
	                 	 <#elseif itemCounter == 2>
	                 	 	<#assign toiletTypeVal_2=attribute.value[0]/>
	                 	 <#elseif itemCounter == 3>
	                 	 	<#assign toiletTypeVal_3=attribute.value[0]/>
	                 	 </#if>
				  </#if>
			</#if>
		   <#if attribute.key == "NUMBER_OF_HANDLES">
				  <#if attribute.value[0]?? && attribute.value[0]?has_content >
				 		 <#assign noOfHandlesCount = noOfHandlesCount+1/>
				 		 <#if itemCounter == 1>
                 	 		<#assign noOfHandlesVal_1=attribute.value[0]/>
	                 	 <#elseif itemCounter == 2>
	                 	 	<#assign noOfHandlesVal_2=attribute.value[0]/>
	                 	 <#elseif itemCounter == 3>
	                 	 	<#assign noOfHandlesVal_3=attribute.value[0]/>
	                 	 </#if>
				  </#if>
			</#if>
			<#if attribute.key == "INSTALLATION_TYPE">
				  <#if attribute.value[0]?? && attribute.value[0]?has_content >
				 	<#assign productInstallationTypeCount = productInstallationTypeCount+1/>
				 	<#if itemCounter == 1>
                 	 	<#assign productInstallationTypeVal_1=attribute.value[0]/>
                 	 <#elseif itemCounter == 2>
                 	 	<#assign productInstallationTypeVal_2=attribute.value[0]/>
                 	 <#elseif itemCounter == 3>
                 	 	<#assign productInstallationTypeVal_3=attribute.value[0]/>
                 	 </#if>
				  </#if>
			</#if>
			<#if attribute.key == "OVERALL_LENGTH_MM">
				  <#if attribute.value[0]?? && attribute.value[0]?has_content >
				 		 <#assign lengthCount = lengthCount+1/>
				 		 <#if itemCounter == 1>
                 	 		<#assign lengthVal_1=attribute.value[0]/>
                 	 		<#assign lengthVal_1=lengthVal_1 + " ${dimensionscompare}"/>
	                 	 <#elseif itemCounter == 2>
	                 	 	<#assign lengthVal_2=attribute.value[0]/>
	                 	 	<#assign lengthVal_2=lengthVal_2 + " ${dimensionscompare}"/>
	                 	 <#elseif itemCounter == 3>
	                 	 	<#assign lengthVal_3=attribute.value[0]/>
	                 	 	<#assign lengthVal_3=lengthVal_3 + " ${dimensionscompare}"/>
	                 	 </#if>
				  </#if>
			</#if>
			<#if attribute.key == "OVERALL_WIDTH_MM">
				  <#if attribute.value[0]?? && attribute.value[0]?has_content >
				 		 <#assign widthCount = widthCount+1/>
				 		 <#if itemCounter == 1>
                 	 		<#assign widthVal_1=attribute.value[0]/>
                 	 		<#assign widthVal_1=widthVal_1 + " ${dimensionscompare}"/>
	                 	 <#elseif itemCounter == 2>
	                 	 	<#assign widthVal_2=attribute.value[0]/>
	                 	 	<#assign widthVal_2=widthVal_2 + " ${dimensionscompare}"/>
	                 	 <#elseif itemCounter == 3>
	                 	 	<#assign widthVal_3=attribute.value[0]/>
	                 	 	<#assign widthVal_3=widthVal_3 + " ${dimensionscompare}"/>
	                 	 </#if>
				  </#if>
			</#if>
			<#if attribute.key == "OVERALL_HEIGHT_MM">
				  <#if attribute.value[0]?? && attribute.value[0]?has_content >
				 		<#assign heightCount = heightCount+1/>
				 		<#if itemCounter == 1>
                 	 		<#assign heightVal_1=attribute.value[0]/>
                 	 		<#assign heightVal_1=heightVal_1 + " ${dimensionscompare}"/>
	                 	 <#elseif itemCounter == 2>
	                 	 	<#assign heightVal_2=attribute.value[0]/>
	                 	 	<#assign heightVal_2=heightVal_2 + " ${dimensionscompare}"/>
	                 	 <#elseif itemCounter == 3>
	                 	 	<#assign heightVal_3=attribute.value[0]/>
	                 	 	<#assign heightVal_3=heightVal_3 + " ${dimensionscompare}"/>
	                 	 </#if>
				  </#if>
			</#if>
			<#if attribute.key == "OVERALL_DEPTH_MM">
				  <#if attribute.value[0]?? && attribute.value[0]?has_content >
				 		 <#assign depthCount = depthCount+1/>
				 		 <#if itemCounter == 1>
                 	 		<#assign depthVal_1=attribute.value[0]/>
                 	 		<#assign depthVal_1=depthVal_1 + " ${dimensionscompare}"/>
	                 	 <#elseif itemCounter == 2>
	                 	 	<#assign depthVal_2=attribute.value[0]/>
	                 	 	<#assign depthVal_2=depthVal_2 + " ${dimensionscompare}"/>
	                 	 <#elseif itemCounter == 3>
	                 	 	<#assign depthVal_3=attribute.value[0]/>
	                 	 	<#assign depthVal_3=depthVal_3 + " ${dimensionscompare}"/>
	                 	 </#if>
				  </#if>
			</#if>
			<#if attribute.key == "NUMBER_OF_HOLES">
				  <#if attribute.value[0]?? && attribute.value[0]?has_content >
				 		 <#assign noOfHolesCount = noOfHolesCount+1/>
				 		 <#if itemCounter == 1>
                 	 		<#assign noOfHolesVal_1=attribute.value[0]/>
	                 	 <#elseif itemCounter == 2>
	                 	 	<#assign noOfHolesVal_2=attribute.value[0]/>
	                 	 <#elseif itemCounter == 3>
	                 	 	<#assign noOfHolesVal_3=attribute.value[0]/>
	                 	 </#if>
				  </#if>
			</#if>
			<#if attribute.key == "HANDLE_STYLE">
				  <#if attribute.value[0]?? && attribute.value[0]?has_content >
				 		 <#assign handleStyleCount = handleStyleCount+1/>
				 		 <#if itemCounter == 1>
                 	 		<#assign handleStyleVal_1=attribute.value[0]/>
	                 	 <#elseif itemCounter == 2>
	                 	 	<#assign handleStyleVal_2=attribute.value[0]/>
	                 	 <#elseif itemCounter == 3>
	                 	 	<#assign handleStyleVal_3=attribute.value[0]/>
	                 	 </#if>
				  </#if>
			</#if>
			<#if attribute.key == "WATER_CONSERVATION">
				  <#if attribute.value[0]?? && attribute.value[0]?has_content >
				 		 <#assign waterConservationCount = waterConservationCount+1/>
				 		 <#if itemCounter == 1>
                 	 		<#assign waterConservationVal_1=attribute.value[0]/>
	                 	 <#elseif itemCounter == 2>
	                 	 	<#assign waterConservationVal_2=attribute.value[0]/>
	                 	 <#elseif itemCounter == 3>
	                 	 	<#assign waterConservationVal_3=attribute.value[0]/>
	                 	 </#if>
				  </#if>
			</#if>
			<#if attribute.key == "PRODUCT_TYPE">
				  <#if attribute.value[0]?? && attribute.value[0]?has_content >
				 		 <#assign productTypeCount = productTypeCount+1/>
				 		 <#if itemCounter == 1>
                 	 		<#assign productTypeVal_1=attribute.value[0]/>
	                 	 <#elseif itemCounter == 2>
	                 	 	<#assign productTypeVal_2=attribute.value[0]/>
	                 	 <#elseif itemCounter == 3>
	                 	 	<#assign productTypeVal_3=attribute.value[0]/>
	                 	 </#if>
				  </#if>
			</#if>
		</#list>
		<#assign itemCounter = itemCounter + 1/>
	</#list>
</#if>

<input type="hidden" id="price-vat" value="${productVatValue}" />
<input type="hidden" id="multiply-value" value="${productMultiplicationValue}" />

<section class="c-koh-compare-products">
    <div class="koh-compare-container">
        <!-- Header -->
        <div class="koh-compare-header">
            <h1 class="koh-compare-title">${compareProducts}</h1>
        </div>
        <!-- koh-compare-header -->
        <!-- Table -->
        <div class="koh-compare-table">
            <div class="koh-compare-table-inner">
                <div class="koh-compare-features">
                    <div class="koh-compare-top">
                        <button class="koh-compare-print" onclick="javascript:window.print()">
                            <span class="icon" data-icon=""></span>
                            <span class="label">${print}</span>
                        </button>
                    </div>
                    <!-- .koh-compare-top -->
                    
                    <ul class="koh-compare-features-list">
				       <#if (brandNameCount > 0) >
	                        <@fmt.message key="brandName" var="brandName"/>
	                        <li>${brandName}</li>
                        </#if>
                        <#if (priceRangeCount > 0) >
	                        <@fmt.message key="priceRangeCompare" var="priceRange"/>
	                        <li>${priceRange}</li>
                        </#if>
                        <#if (toiletTypeCount > 0) >
	                        <@fmt.message key="ToiletType" var="toiletType"/>
	                        <li>${toiletType}</li>
                        </#if>
                        <#if (productInstallationTypeCount > 0) >
	                        <@fmt.message key="InstallationType" var="installationType"/>
	                        <li>${installationType}</li>
                       </#if>
                        <#if (shapeCount > 0) >
	                        <@fmt.message key="shape" var="shape"/>
	                        <li>${shape}</li>
                        </#if>
                        <#if (materialCount > 0) >
	                        <@fmt.message key="Material" var="material"/>
	                        <li>${material}</li>
                        </#if>
                        <#if (handleStyleCount > 0) >
	                        <@fmt.message key="HandlesType" var="HandlesType"/>
	                        <li>${HandlesType}</li>
                        </#if>
                         <#if (noOfHandlesCount > 0) >
	                        <@fmt.message key="NumberOfHandles" var="numberOfHandles"/>
	                        <li>${numberOfHandles}</li>
                        </#if>
                        <#if (noOfHolesCount > 0) >
	                        <@fmt.message key="numberOfHoles" var="numberOfHoles"/>
	                        <li>${numberOfHoles}</li>
                        </#if>
                        <#if (litersPerFlushCount > 0) >
	                        <@fmt.message key="litersPerFlush" var="litersperFlush"/>
	                        <li>${litersperFlush}</li>
                        </#if>
                        <#if (trapWayTypeCount > 0) >
	                        <@fmt.message key="trapwayType" var="trapwaytype"/>
	                        <li>${trapwaytype}</li>
                        </#if>
                        <#if (roughInCount > 0) >
	                        <@fmt.message key="RoughIn" var="roughIn"/>
	                        <li>${roughIn}</li>
                        </#if>
                       <#if (lengthCount > 0) >
	                        <@fmt.message key="length" var="length"/>
	                        <li>${length}</li>
                        </#if>
                        <#if (widthCount > 0) >
	                        <@fmt.message key="width" var="width"/>
	                        <li>${width}</li>
                        </#if>
                        <#if (heightCount > 0) >
	                        <@fmt.message key="height" var="height"/>
	                        <li>${height}</li>
                        </#if>
                        <#if (depthCount > 0) >
	                        <@fmt.message key="depth" var="depth"/>
	                        <li>${depth}</li>
                        </#if>
                        <@fmt.message key="colorFinishes" var="colorFinishes"/>
                        <li class="colorFinhsh">${colorFinishes}</li>
                        <#if (waterConservationCount > 0) >
			                   <@fmt.message key="waterConservation" var="waterConservation"/>
                        		<li>${waterConservation}</li>
			            </#if>
			            <#if (productTypeCount > 0) >
			                   <@fmt.message key="ProductType" var="productType"/>
                        		<li>${productType}</li>
			            </#if>
                    </ul>
                    <!-- koh-compare-features-list -->
                </div>
                <!-- koh-compare-features -->
			   <div class="koh-compare-products" id="compare-product-details">
	                <ul class="koh-compare-columns">
	                	<#if items??>
	                	    <#assign SKU_ROW_VALUE=10 />
                            <#assign SKU_ROW_VALUE2=20 />
                            <#assign SKU_ROW_VALUE3=30 />
                            <#assign SKU_GLOBAL_COUNTER=0 />
                            <#assign itemCounter = 1/>
							<#list items as item>
	                            <li class="koh-compare-product">
	                                <div class="koh-compare-top">
	                                	<#--  <img src="http://s7g10.scene7.com/is/image/kohlerindia/Category%5FTemplate?$GridResults$&$Badge1_src=kohlerindia%2FBlank&$gradient_src=kohlerindia%2Forganic%2Dgradient&$shadow_src=kohlerindia%2FBlank&$Badge4_src=kohlerindia%2FBlank&$Badge3_src=kohlerindia%2FBlank&$Badge2_src=kohlerindia%2FBlank&$product_src=is <http://s7g10.scene7.com/is/image/kohlerindia/Category_Template?$GridResults$&$Badge1_src=kohlerindia/Blank&$gradient_src=kohlerindia/organic-gradient&$shadow_src=kohlerindia/Blank&$Badge4_src=kohlerindia/Blank&$Badge3_src=kohlerindia/Blank&$Badge2_src=kohlerindia/Blank&$product_src=is> {kohlerindia%2F${productImage.value[0]}}" width="570" height="413" alt=""></a>-->
	                                    <#--<img src="http://s7g10.scene7.com/is/image/kohlerindia/Category%5FTemplate?$GridResults$&$Badge1_src=kohlerindia%2FBlank&$gradient_src=kohlerindia%2Forganic%2Dgradient&$shadow_src=kohlerindia%2FBlank&$Badge4_src=kohlerindia%2FBlank&$Badge3_src=kohlerindia%2FBlank&$Badge2_src=kohlerindia%2FBlank&$product_src=is <http://s7g10.scene7.com/is/image/kohlerindia/Category_Template?$GridResults$&$Badge1_src=kohlerindia/Blank&$gradient_src=kohlerindia/organic-gradient&$shadow_src=kohlerindia/Blank&$Badge4_src=kohlerindia/Blank&$Badge3_src=kohlerindia/Blank&$Badge2_src=kohlerindia/Blank&$product_src=is> {kohlerindia%2F${productImage.value[0]}}" width="570" height="413" alt=""></a>-->	                                     
	                                     <#assign defaultImage="" />
	                                     <#assign defaultSku="" />
	                                     <#assign defaultSkuValue="" />
	                                     <#assign productPrice="" />
	                                     <#list item.attributes as selectedSku>
											 <#if selectedSku.key == "DEFAULT_SKU">	
												<#assign defaultSkuValue=selectedSku.value[0] />												 
											</#if>		
											<#if selectedSku.key == "IMG_ISO"> 
										    	<#if selectedSku.value[0]?? && selectedSku.value[0]?has_content>
											  		<#assign defaultImage=selectedSku.value[0] />
											  	</#if>	
										     </#if>									
										</#list>
										
										<#if defaultSkuValue == "" &&  item.skus?has_content>
											<#assign defaultSkuValue = item.skus[0].sku />
										</#if>
	                                   
	                                   <#if defaultImage == "" &&  item.skus?has_content>
	                                   		 <#list item.skus[0].attributes as skuAttribute>
	                                   		 	<#if skuAttribute.key == "IMG_ITEM_ISO"> 
											    	<#if skuAttribute.value[0]?? && skuAttribute.value[0]?has_content>
												  		<#assign defaultImage=skuAttribute.value[0] />
												  	</#if>	
										     	</#if>	
											 </#list>	
										</#if>
										<#if defaultImage == "" >
	                                    	<#assign defaultImage="blank" />
	                                    </#if>
	                                    
	                                    <#if defaultImage?starts_with("https")>
											<#assign defImgWithoutHttps=defaultImage?remove_beginning("https://") />
											<#assign defImgFromUrl=defImgWithoutHttps?keep_after_last("/")>
											<#assign defImgDomainFromUrl=defImgWithoutHttps?keep_before_last("/") />
											<#assign defImgDamNameFromUrl=defImgDomainFromUrl?keep_after_last("/") />
											<#if item.isNew>
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
		                                    <#if item.isNew>
										  		<#assign defImg = gridImageNewProductUrl?replace("{0}","${defaultImage}") />
											<#else>										     										     
	                                      		<#assign defImg = gridImageUrl?replace("{0}","${defaultImage}") />
		                                    </#if>
		                                </#if>
	                                    <#assign categoryParameter = "" />
	                                    <#if item.keys?? && (item.keys?size > 1) && categoryKey??>
	                                    	<#list item.keys as key>
	                                    		<#if key == categoryKey>
		                                   		 <#assign categoryParameter = "&defaultCategory=${categoryKey}" />
		                                   		</#if> 
		                                   	</#list>	 
		                                </#if>   
									     <a href="${productDetailslink}/${item.itemNo}?skuid=<#if defaultSkuValue?? && defaultSkuValue?has_content>${defaultSkuValue}</#if>${categoryParameter}" id="${item.itemNo}">	
								            <img src="${defImg}" width="570" height="413" alt="${defaultSkuValue}">
								         	<#assign productDescription = "" /> 
		                                    <#list item.attributes as productDesc>
												 <#if productDesc.key == "DESCRIPTION_PRODUCT_SHORT">
													 <#assign productDescription = productDesc.value[0]?replace("(R)","<sup>&reg;</sup>")/>
							                         <#assign productDescription = productDescription?replace("(TM)","<sup>&trade;</sup>")/>
												</#if>
											</#list>
											<#list item.skus as skuSub>   
										  	   <#if skuSub.sku == defaultSkuValue>
									  	   			<#assign defaultSku=skuSub />
										  	   </#if>
									  		</#list>
									  		<#list defaultSku.attributes as skuAttribute>
											 	<#if skuAttribute.key == "LIST_PRICE"> 
											    	<#if skuAttribute.value[0]?? && skuAttribute.value[0]?has_content>
												  		<#assign productPrice=skuAttribute.value[0] />
												  		<#assign productPriceNumber = (productPrice?number) * productVat/>
												  	</#if>	
											 	</#if>	
								            </#list>
											<span class="koh-compare-name">${productDescription}</span>							
											<span class="koh-compare-sku">K-${defaultSkuValue}</span>
											<#if currencyCode?? && currencyCode?has_content && productPrice?has_content>
												<span class="koh-compare-price">${currencyCode} ${(productPriceNumber?round)*productMultiplication}</span>
											</#if>
										</a>												               
	                                    <#--<span class="koh-compare-sku">${selectedSkuitem.sku}</span> -->
	                                    <#-- <span class="koh-compare-price">
	                                    Rs.63,530.00</span> -->
	                                    <button class="koh-compare-remove" data-product-itemno="${item.itemNo}" <#if categoryParameter != "">data-product-itemno-multianscestors="${item.itemNo}!_${categoryKey}"</#if>>
	                                        <span class="icon" data-icon=""></span>
	                                        <span class="label">${remove}</span>
	                                    </button>
	                                </div>
	                                <!-- .koh-compare-top -->
	                                
	                                <ul class="koh-compare-features-list">
									   <#if (brandNameCount > 0) >
									   		<#if itemCounter == 1 >
										   		<li class="strong">${brandNameVal_1}</li>
										   	<#elseif itemCounter == 2 >
										   		<li class="strong">${brandNameVal_2}</li>
										   	<#elseif itemCounter == 3 >
										   		<li class="strong">${brandNameVal_3}</li>
										   	</#if>
										</#if>																			
										<#if (priceRangeCount > 0) >
									   		<#if itemCounter == 1 >
									   			<#if priceRangeVal_1?? && priceRangeVal_1?has_content>
										   			<@fmt.message key="pricerange-${priceRangeVal_1}" var="priceRange1"/>
											   		<li class="strong">${priceRange1}</li>
											   	<#else>
											   			<li class="strong">-</li>
											   	</#if>	
										   	<#elseif itemCounter == 2 >
										   		<#if priceRangeVal_2?? && priceRangeVal_2?has_content>
											   		<@fmt.message key="pricerange-${priceRangeVal_2}" var="priceRange2"/>
											   		<li class="strong">${priceRange2}</li>
											   	<#else>
											   		<li class="strong">-</li>
											   	</#if>	
										   	<#elseif itemCounter == 3 >
										   		<#if priceRangeVal_3?? && priceRangeVal_3?has_content>
											   		<@fmt.message key="pricerange-${priceRangeVal_3}" var="priceRange3"/>
											   		<li class="strong">${priceRange3}</li>
											   	<#else>
											   		<li class="strong">-</li>
											   	</#if>	
										   	</#if>
										</#if>
										<#if (toiletTypeCount > 0) >
									   		<#if itemCounter == 1 >
										   		<li class="strong">${toiletTypeVal_1}</li>
										   	<#elseif itemCounter == 2 >
										   		<li class="strong">${toiletTypeVal_2}</li>
										   	<#elseif itemCounter == 3 >
										   		<li class="strong">${toiletTypeVal_3}</li>
										   	</#if>
										</#if>
										<#if (productInstallationTypeCount > 0) >
										 	<#if itemCounter == 1 >
										   		<li class="strong">${productInstallationTypeVal_1}</li>
										   	<#elseif itemCounter == 2 >
										   		<li class="strong">${productInstallationTypeVal_2}</li>
										   	<#elseif itemCounter == 3 >
										   		<li class="strong">${productInstallationTypeVal_3}</li>
										   	</#if>
										</#if>
										<#if (shapeCount > 0) >
									   		<#if itemCounter == 1 >
										   		<li class="strong">${shapeVal_1}</li>
										   	<#elseif itemCounter == 2 >
										   		<li class="strong">${shapeVal_2}</li>
										   	<#elseif itemCounter == 3 >
										   		<li class="strong">${shapeVal_3}</li>
										   	</#if>
										</#if>
										<#if (materialCount > 0) >
									   		<#if itemCounter == 1 >
										   		<li class="strong"><#if materialVal_1??>${materialVal_1}<#else>-</#if></li>
										   	<#elseif itemCounter == 2 >
										   		<li class="strong"><#if materialVal_2??>${materialVal_2}<#else>-</#if></li>
										   	<#elseif itemCounter == 3 >
										   		<li class="strong"><#if materialVal_3??>${materialVal_3}<#else>-</#if></li>
										   	</#if>
										</#if>
										<#if (handleStyleCount > 0) >
											<#if itemCounter == 1 >
										   		<li class="">${handleStyleVal_1}</li>
										   	<#elseif itemCounter == 2 >
										   		<li class="">${handleStyleVal_2}</li>
										   	<#elseif itemCounter == 3 >
										   		<li class="">${handleStyleVal_3}</li>
										   	</#if>
	                                	</#if>
										<#if (noOfHandlesCount > 0) >
											<#if itemCounter == 1 >
										   		<li class="">${noOfHandlesVal_1}</li>
										   	<#elseif itemCounter == 2 >
										   		<li class="">${noOfHandlesVal_2}</li>
										   	<#elseif itemCounter == 3 >
										   		<li class="">${noOfHandlesVal_3}</li>
										   	</#if>
	                                	</#if>
	                                	<#if (noOfHolesCount > 0) >
											<#if itemCounter == 1 >
										   		<li class="">${noOfHolesVal_1}</li>
										   	<#elseif itemCounter == 2 >
										   		<li class="">${noOfHolesVal_2}</li>
										   	<#elseif itemCounter == 3 >
										   		<li class="">${noOfHolesVal_3}</li>
										   	</#if>
	                                	</#if>
										<#if (litersPerFlushCount > 0) >
									   		<#if itemCounter == 1 >
										   		<li class="strong">${litersPerFlushVal_1}</li>
										   	<#elseif itemCounter == 2 >
										   		<li class="strong">${litersPerFlushVal_2}</li>
										   	<#elseif itemCounter == 3 >
										   		<li class="strong">${litersPerFlushVal_3}</li>
										   	</#if>
										</#if>
										<#if (trapWayTypeCount > 0) >
									   		<#if itemCounter == 1 >
										   		<li class="strong">${trapWayTypeVal_1}</li>
										   	<#elseif itemCounter == 2 >
										   		<li class="strong">${trapWayTypeVal_2}</li>
										   	<#elseif itemCounter == 3 >
										   		<li class="strong">${trapWayTypeVal_3}</li>
										   	</#if>
										</#if>
										<#if (roughInCount > 0) >
									   		<#if itemCounter == 1 >
										   		<li class="strong">${roughInVal_1}</li>
										   	<#elseif itemCounter == 2 >
										   		<li class="strong">${roughInVal_2}</li>
										   	<#elseif itemCounter == 3 >
										   		<li class="strong">${roughInVal_3}</li>
										   	</#if>
										</#if>										
										<#if (lengthCount > 0) >
										 	<#if itemCounter == 1 >
										   		<li class="strong">${lengthVal_1}</li>
										   	<#elseif itemCounter == 2 >
										   		<li class="strong">${lengthVal_2}</li>
										   	<#elseif itemCounter == 3 >
										   		<li class="strong">${lengthVal_3}</li>
										   	</#if>
										</#if>
										<#if (widthCount > 0) >
										 	<#if itemCounter == 1 >
										   		<li class="strong">${widthVal_1}</li>
										   	<#elseif itemCounter == 2 >
										   		<li class="strong">${widthVal_2}</li>
										   	<#elseif itemCounter == 3 >
										   		<li class="strong">${widthVal_3}</li>
										   	</#if>
										</#if>
										<#if (heightCount > 0) >
										 	<#if itemCounter == 1 >
										   		<li class="strong">${heightVal_1}</li>
										   	<#elseif itemCounter == 2 >
										   		<li class="strong">${heightVal_2}</li>
										   	<#elseif itemCounter == 3 >
										   		<li class="strong">${heightVal_3}</li>
										   	</#if>
										</#if>
										<#if (depthCount > 0) >
										 	<#if itemCounter == 1 >
										   		<li class="strong">${depthVal_1}</li>
										   	<#elseif itemCounter == 2 >
										   		<li class="strong">${depthVal_2}</li>
										   	<#elseif itemCounter == 3 >
										   		<li class="strong">${depthVal_3}</li>
										   	</#if>
										</#if>
			                            <li class="colorFinish">
			                            	<#assign SKU_LOCAL_COUNTER=0 />
			                                <#list item.skus as sku>
			                                	 <#assign skuColorFinishName="" />
			                                	 <#assign skuImageSwatch="" />
			                                	 <#assign isDiscontinued = "false">
			                                	 <#list sku.attributes as ImageSwatch>
			                                	 	<#if ImageSwatch.key == "IMG_SWATCH">
			                                	 		 <#assign skuImageSwatch=ImageSwatch.value[0] />
			                                	 		 <#assign SKU_LOCAL_COUNTER=SKU_LOCAL_COUNTER + 1 />
			                                	 	</#if>
			                                	 	<#if ImageSwatch.key == "COLOR_FINISH_NAME">
			                                	 		<#assign skuColorFinishName=ImageSwatch.value[0]/>
			                                	 	</#if>
			                                	 	<#if ImageSwatch.key == "DISCONTINUED_DATE" || item.discontinued>
			                                	 		<#assign isDiscontinued = "true">
			                                	 	</#if>
			                                	 </#list>
												 <#assign swatchImg = imgswatch?replace("{0}","${skuImageSwatch}") />
			                                	 <#if isDiscontinued == "true">
			                                	 	<#assign skuColorFinishName = skuColorFinishName + " (${discontinuedLableWithSku})" /> 
			                                	 	<span class="koh-discontinued">
			                                   	 		<img src="${swatchImg}" class="swatch compareColorSwatch" alt="${skuColorFinishName}" data-hasqtip="0" width="20" height="20">
			                                   	 	</span>
			                                   	 <#else>
			                                   	 	<img src="${swatchImg}" class="swatch compareColorSwatch" alt="${skuColorFinishName}" data-hasqtip="0" width="20" height="20">
			                                	 </#if>
			                                 </#list>
			                                 <#if (SKU_LOCAL_COUNTER > SKU_GLOBAL_COUNTER) >
			                                 	<#assign SKU_GLOBAL_COUNTER=SKU_LOCAL_COUNTER />
			                                 </#if>
			                             </li>
			                             <#if (waterConservationCount > 0) >
										 	<#if itemCounter == 1 >
										   		<li class="">${waterConservationVal_1}</li>
										   	<#elseif itemCounter == 2 >
										   		<li class="">${waterConservationVal_2}</li>
										   	<#elseif itemCounter == 3 >
										   		<li class="">${waterConservationVal_3}</li>
										   	</#if>
			                             </#if>
			                             <#if (productTypeCount > 0) >
										 	<#if itemCounter == 1 >
										   		<li class="">${productTypeVal_1}</li>
										   	<#elseif itemCounter == 2 >
										   		<li class="">${productTypeVal_2}</li>
										   	<#elseif itemCounter == 3 >
										   		<li class="">${productTypeVal_3}</li>
										   	</#if>
			                             </#if>
	                        		</ul>
	                                <!-- .koh-compare-features-list -->
	                            </li>
	                            <#assign itemCounter = itemCounter + 1 />		                             
				    		</#list>
				    		<#if (SKU_GLOBAL_COUNTER > SKU_ROW_VALUE) >
				    			<@hst.headContribution category="ext-scripts">
								  <script type="text/javascript">
								  	jQuery(document).ready(function($) {
	                        	    	$(".colorFinish").each(function(){
	                        	    	<#if (SKU_GLOBAL_COUNTER > SKU_ROW_VALUE3) >	                        	    	
	                        	    		  $(this).addClass("koh-image-row-hight-4th");
	                        	    		  return false;
	                        	    		</#if>
	                        	    	<#if (SKU_GLOBAL_COUNTER > SKU_ROW_VALUE2) >	                        	    	
	                        	    		  $(this).addClass("koh-image-row-hight-3rd");
	                        	    		  return false;
	                        	    		</#if>		                        	    		
	                        	    	 <#if (SKU_GLOBAL_COUNTER > SKU_ROW_VALUE) >	                        	    	
	                        	    		   $(this).addClass("koh-image-row-hight");
	                        	    	 </#if>	                        	    		
	                        	    	});
	                        	    });
								  </script>
								</@hst.headContribution>
	                        </#if>	
				    	</#if> 
	                </ul>
	                <!-- .koh-compare-columns -->
	            </div>
	            <!-- .koh-compare-products -->
	            <ul class="koh-compare-navigation">
	                <li><a href="#0" class="prev inactive">${prev}</a></li>
	                <li><a href="#0" class="next">${next}</a></li>
	            </ul>
	            <!-- .koh-compare-navigation -->
	        </div>
	    </div>
			    <!-- .koh-compare-table -->
	</div>
    <!-- koh-compare-container -->
    <div class="koh-history-page">
        <button type="button" name="back">${back}</button>
    </div>
   <div class="koh-back-top">
      <button style="display: none;">
      <span class="icon" data-icon=""></span>
       ${backTotop}
      </button>
     </div> 
    <!-- koh-search-top -->
</section>

 <@hst.webfile path="/" var="link" />
 <@hst.headContribution category="ext-scripts">
     <script src="${link}/js/c-koh-product-details.min.js" type="text/javascript"></script>
 </@hst.headContribution>

<script>
if (document.documentMode===10){
   if (sessionStorage.getItem("Page2Visited")) {
          sessionStorage.removeItem("Page2Visited");
          window.location.reload(true);
     }
}  
</script>