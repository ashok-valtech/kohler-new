<@fmt.message key="template-symbol-download" var="templateSymbolDownload"/> 
<@fmt.message key="2-cad-file" var="twoCadFile"/>
<@fmt.message key="plan" var="plan"/>
<@fmt.message key="front" var="front"/>
<@fmt.message key="side" var="side"/>
<@fmt.message key="overall" var="overall"/>
<@fmt.message key="3-cad-file" var="threeCadFile"/>
<@fmt.message key="cutout-templates" var="cutoutTemplates"/>
<@fmt.message key="technical-Info-Downloads" var="technicalInfoDownloads"/>
<@fmt.message key="home-owner- Guide" var="homeOwnerGuide"/>
<@fmt.message key="specPdfUrls" var="specPdfUrls"/>
<@fmt.message key="cadUrls" var="cadUrls"/>
<div class="koh-search-results c-koh-product-search v-koh-specs">
	<#assign categoryKey = "" />
	<#assign isSingleCategory = "false" />
	<#if currentCategory??>
		<#assign categoryKey = currentCategory.key />
		<#assign isSingleCategory = "true" />
	</#if>    
	<#if pageable?? && pageable.items?has_content>
		<#list pageable.items as product>
		    <@hst.link var="link" hippobean=value />
         	<@hst.link var="productDetailslink" siteMapItemRefId="productDetails"/>
			<#assign productFamily="" />	
			<#assign productDescription="" />
			<#assign defaultSku="" />
			<#assign defaultImage="" />
			 <#list product.attributes as productAttributes>
				<#if productAttributes.key == "BRAND_NAME">
					<#assign productFamily=productAttributes.value[0] />
				</#if>
				<#if productAttributes.key == "DESCRIPTION_PRODUCT"> 
					<#assign productDescription=productAttributes.value[0] />
				</#if>
				<#if productAttributes.key == "DEFAULT_SKU"> 
					<#assign defaultSku=productAttributes.value[0] />
				</#if>
				<#if productAttributes.key == "IMG_ISO"> 
					<#assign defaultImage=productAttributes.value[0] />
				</#if>
             </#list>
			  <#assign selectedSkuitem="" />
			  <#list product.skus as skuSub>   
			  	   <#if skuSub.sku == defaultSku>
			  	   		<#assign selectedSkuitem=skuSub />
			  	   </#if>
			  </#list>
			  <#if selectedSkuitem == "">
				<#assign selectedSkuitem=product.skus[0] />				
			  </#if>
			  <#assign isMultyCategoryProduct = "false" />
			  <#assign productCategoryKey = "" />
			  <#if product.keys??>
		       		<#if (product.keys?size > 1)>
		       			<#assign isMultyCategoryProduct = "true" />
		       			<#if isSingleCategory == "true">
		       				<#assign productCategoryKey = categoryKey />
		       			<#else>
		       				<#assign productCategoryKey = product.keys[0] />
		       			</#if>
		       		</#if>
		       </#if>
			<div class="koh-product-spec">
					<div class="koh-spec-image">
					 <a href="${productDetailslink?html}/${product.itemNo?html}?skuid=${selectedSkuitem.sku?html}<#if isMultyCategoryProduct == "true">&defaultCategory=${productCategoryKey}</#if>" id="${product.itemNo?html}">
						<@fmt.message key="galleryUrl" var="gridImageUrl"/>
                               <#assign defImg = gridImageUrl?replace("{0}","${defaultImage}") />
		                     	<img src="${defImg?html}" width="150" height="112" alt="">
					</a>
					</div>
					<div class="koh-spec-content">
					<a href="${productDetailslink?html}/${product.itemNo?html}?skuid=${selectedSkuitem.sku?html}<#if isMultyCategoryProduct == "true">&defaultCategory=${productCategoryKey}</#if>" id="${product.itemNo?html}">
						<span class="koh-spec-sku">${product.itemNo?html}</span>
						<#if product.descriptionProduct?? && product.descriptionProduct?has_content>
							<#assign productDescription = product.descriptionProduct />
							<#assign productDescription = productDescription?replace("(R)","<sup>&reg;</sup>") />
							<#assign productDescription = productDescription?replace("(TM)","<sup>&trade;</sup>") />
							<span class="koh-spec-description">${productDescription}</span>
						</#if>
						</a>
					</div>
			 <#assign displayTechnicalDownload=false/>
				<#list product.attributes as productAttributes>
				    <#if productAttributes.key == "SPEC_PDF_FILE_NAME" > <#assign displayTechnicalDownload=true/></#if>
				    <#if productAttributes.key == "ENVIRON_PROD_DECL" ><#assign displayTechnicalDownload=true/></#if>
					<#if productAttributes.key == "HOMEGUIDE_WITH_SP_PDF" ><#assign displayTechnicalDownload=true/></#if>
					<#if productAttributes.key == "HOMEGUIDE_WITHOUT_SP_PDF"><#assign displayTechnicalDownload=true/></#if>
					<#if productAttributes.key == "INSTALLATION_CARE_WITH_SP_PDF"><#assign displayTechnicalDownload=true/></#if> 
	                <#if productAttributes.key == "INSTALLATION_CARE_WO_SP_PDF"><#assign displayTechnicalDownload=true/></#if>
	                <#if productAttributes.key == "INSTALLATION_PDF_FILE_NAME"><#assign displayTechnicalDownload=true/> </#if> 
	                <#if productAttributes.key == "INSTALLATION_WITHOUT_SP_PDF"><#assign displayTechnicalDownload=true/> </#if>    
	                <#if productAttributes.key == "LID_LABEL_PDF"><#assign displayTechnicalDownload=true/> </#if>   
	                <#if productAttributes.key == "MAINTENANCE_WITH_SP_PDF"><#assign displayTechnicalDownload=true/></#if>
	                <#if productAttributes.key == "MAINTENANCE_WITHOUT_SP_PDF"><#assign displayTechnicalDownload=true/> </#if> 
	                <#if productAttributes.key == "MSDS_PDF_FILE_NAME"><#assign displayTechnicalDownload=true/></#if>
	                <#if productAttributes.key == "MATERIAL_PDF_FILE_NAME"><#assign displayTechnicalDownload=true/></#if>
	                <#if productAttributes.key == "USER_GUIDE_SPANISH"><#assign displayTechnicalDownload=true/></#if>
	                 <#if productAttributes.key == "USER_GUIDE_ENGLISH"><#assign displayTechnicalDownload=true/></#if>
	                 <#if productAttributes.key == "USER_GUIDE_FRENCH"><#assign displayTechnicalDownload=true/></#if>
	                 <#if productAttributes.key == "USER_GUIDE_PDF"><#assign displayTechnicalDownload=true/></#if>
	                 <#if productAttributes.key == "PARTS_PDF_FILE_NAME"><#assign displayTechnicalDownload=true/></#if>
	                 <#if productAttributes.key == "MATCHING_LIST_PDF"><#assign displayTechnicalDownload=true/></#if>
	                 <#if productAttributes.key == "AP_REGIONAL_SPEC_PDF"><#assign displayTechnicalDownload=true/></#if>
	                </#list>
					<div class="koh-spec-accordion">
						<ul>
							<#if displayTechnicalDownload=true>
							<li>
								<a href="" class="koh-spec-toggle">${technicalInfoDownloads}</a>
								<ol class="koh-spec-info">
							  <#list product.attributes as productAttributes>
								<#if productAttributes.key == "SPEC_PDF_FILE_NAME">
                             	    <@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>
                                </#if>	
                                <#if productAttributes.key == "ENVIRON_PROD_DECL">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                <#if productAttributes.key == "HOMEGUIDE_WITH_SP_PDF">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                <#if productAttributes.key == "HOMEGUIDE_WITHOUT_SP_PDF">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                <#if productAttributes.key == "INSTALLATION_CARE_WITH_SP_PDF">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                <#if productAttributes.key == "INSTALLATION_CARE_WO_SP_PDF">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                 <#if productAttributes.key == "INSTALLATION_PDF_FILE_NAME">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                <#if productAttributes.key == "INSTALLATION_WITHOUT_SP_PDF">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                <#if productAttributes.key == "LID_LABEL_PDF">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                <#if productAttributes.key == "MAINTENANCE_WITH_SP_PDF">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                <#if productAttributes.key == "MAINTENANCE_WITHOUT_SP_PDF">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                <#if productAttributes.key == "MSDS_PDF_FILE_NAME">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                <#if productAttributes.key == "USER_GUIDE_SPANISH">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                <#if productAttributes.key == "USER_GUIDE_ENGLISH">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                <#if productAttributes.key == "USER_GUIDE_FRENCH">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                <#if productAttributes.key == "MSDS_PDF_FILE_NAME">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                <#if productAttributes.key == "USER_GUIDE_PDF">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                 <#if productAttributes.key == "PARTS_PDF_FILE_NAME">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                 <#if productAttributes.key == "MATCHING_LIST_PDF">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                                 <#if productAttributes.key == "AP_REGIONAL_SPEC_PDF">
                                 	<@fmt.message key=productAttributes.key var="keyLabel"/>
							  	    <li> <a target="_blank" rel="noopener noreferrer" href="${specPdfUrls?html}${productAttributes.value[0]?html}" class="koh-pdf-link">${keyLabel}</a>   
                                </#if>
                              </#list>  			
								</ol>
							</li>
						    </#if>	
			   <#assign displaysymbolDownload=false/>
			   <#assign allViews=false/>
			   <#assign planView=false/>
			   <#assign frontView=false/>
			   <#assign sideView=false/>
			   <#assign td3View=false/>
			   <#assign cutOffView=false/>
			   
				  <#list product.attributes as productAttributes>
				  
				     <#if productAttributes.key == "DWG_PLAN_VIEW" >
				       <#assign displaysymbolDownload=true/>
				       <#assign planView=true/>
				     </#if>
				     <#if productAttributes.key == "DXF_PLAN_VIEW" > 
				     	<#assign displaysymbolDownload=true/>
				     	<#assign planView=true/>
				     </#if>
				     <#if productAttributes.key == "DWG_FRONT_VIEW" > 
				     	<#assign displaysymbolDownload=true/>
				     	<#assign frontView=true/>
				     </#if>
				     <#if productAttributes.key == "DXF_FRONT_VIEW" > 
				     	<#assign displaysymbolDownload=true/>
				     	<#assign frontView=true/>
				     </#if>
				     <#if productAttributes.key == "DWG_SIDE_VIEW" > 
				     	<#assign displaysymbolDownload=true/>
				     	<#assign sideView=true/>
				     </#if>
				     <#if productAttributes.key == "DXF_SIDE_VIEW" > 
				     	<#assign displaysymbolDownload=true/>
				     	<#assign sideView=true/>
				     </#if>
				     <#if productAttributes.key == "DXF_ALL_VIEWS" > 
				     	<#assign displaysymbolDownload=true/>
				     	<#assign allViews=true/>
				     </#if>
				     <#if productAttributes.key == "DWG_ALL_VIEWS" > 
				     	<#assign displaysymbolDownload=true/>
				     	<#assign allViews=true/>
				     </#if>
				     <#if productAttributes.key == "3D_DXF_SYMBOL" > 
				     	<#assign displaysymbolDownload=true/>
				     	<#assign td3View=true/>
				     </#if>
				     <#if productAttributes.key == "3D_OBI" > 
				     	<#assign displaysymbolDownload=true/>
				     	<#assign td3View=true/>
				     </#if>
				     <#if productAttributes.key == "3D_3DS" > 
				     	<#assign displaysymbolDownload=true/>
				     	<#assign td3View=true/>
				     </#if>
				     <#if productAttributes.key == "3D_SKETCHUP" > 
				     	<#assign displaysymbolDownload=true/>
				     	<#assign td3View=true/>
				     </#if>
				     <#if productAttributes.key == "3D_REVIT" > 
				     	<#assign displaysymbolDownload=true/>
				     	<#assign td3View=true/>
				     </#if>
				     <#if productAttributes.key == "CUT_OUT_DXF" > 
				     	<#assign displaysymbolDownload=true/>
				     	<#assign cutOffView=true/>
				     </#if>
                </#list>
						<#if displaysymbolDownload==true>
							<li>
								<a href="" class="koh-spec-toggle">${templateSymbolDownload}</a>
									<div class="koh-spec-templates">
										<#if planView==true || frontView==true || sideView==true || allViews = true>
											<div class="koh-spec-templates-2d">
												<span class="koh-templates-title"><#if twoCadFile??>${twoCadFile}</#if></span>
												<#if planView==true>
													<span class="koh-templates-label">${plan}</span>
													<ul class="koh-templates-links">
													 <#list product.attributes as productAttributes>
													 
														<#if productAttributes.key == "DWG_PLAN_VIEW">
															<li><a target="_blank" rel="noopener noreferrer" href="${cadUrls?html}${productAttributes.value[0]?html}" >${productAttributes.value[0]?html}</a></li>
														</#if>
														  <#if productAttributes.key == "DXF_PLAN_VIEW">
															<li><a target="_blank" rel="noopener noreferrer" href="${cadUrls?html}${productAttributes.value[0]?html}" >${productAttributes.value[0]?html}</a></li>
														</#if>
													</#list>	
													</ul>
												</#if>
												<#if frontView==true>
													<span class="koh-templates-label">${front}</span>
													<ul class="koh-templates-links">
													<#list product.attributes as productAttributes>
														<#if productAttributes.key == "DWG_FRONT_VIEW">
															<li><a target="_blank" rel="noopener noreferrer" rel="noopener noreferrer" href="${cadUrls?html}${productAttributes.value[0]?html}" >${productAttributes.value[0]?html}</a></li>
														</#if>
														<#if productAttributes.key == "DXF_FRONT_VIEW">
															<li><a target="_blank" rel="noopener noreferrer" href="${cadUrls?html}${productAttributes.value[0]?html}" >${productAttributes.value[0]?html}</a></li>
														</#if>
													</#list>
													</ul>			
												</#if>
												<#if sideView==true>
													<span class="koh-templates-label">${side}</span>
													<ul class="koh-templates-links">
													<#list product.attributes as productAttributes>
														<#if productAttributes.key == "DWG_SIDE_VIEW">
															<li><a target="_blank" rel="noopener noreferrer" href="${cadUrls?html}${productAttributes.value[0]?html}" >${productAttributes.value[0]?html}</a></li>
														</#if>
														 <#if productAttributes.key == "DXF_SIDE_VIEW">
															<li><a target="_blank" rel="noopener noreferrer" href="${cadUrls?html}${productAttributes.value[0]?html}" >${productAttributes.value[0]?html}</a></li>
														</#if>
													</#list>
													</ul>
												</#if>
												<#if allViews?? && allViews = true>
													<span class="koh-templates-label">${overall}</span>
													<ul class="koh-templates-links">
													<#list product.attributes as productAttributes>
														<#if productAttributes.key == "DWG_ALL_VIEWS">
															<li><a target="_blank" rel="noopener noreferrer" href="${cadUrls?html}${productAttributes.value[0]?html}" >${productAttributes.value[0]?html}</a></li>
														</#if>
														 <#if productAttributes.key == "DXF_ALL_VIEWS">
															<li><a target="_blank" rel="noopener noreferrer" href="${cadUrls?html}${productAttributes.value[0]?html}" >${productAttributes.value[0]?html}</a></li>
														</#if>
													</#list>
													</ul>
												</#if>
											</div>
										</#if>
										
										<div class="koh-spec-templates-3d">
										<#if td3View==true>
											<span class="koh-templates-title"><#if threeCadFile??>${threeCadFile}</#if></span>
											<ul class="koh-templates-links">
											<#list product.attributes as productAttributes>
											
												 <#if productAttributes.key == "3D_DXF_SYMBOL">
													<li><a target="_blank" rel="noopener noreferrer" href="${cadUrls?html}${productAttributes.value[0]?html}" >${productAttributes.value[0]?html}</a></li>
												</#if>
												<#if productAttributes.key == "3D_OBJ">
													<li><a target="_blank" rel="noopener noreferrer" href="${cadUrls?html}${productAttributes.value[0]?html}" >${productAttributes.value[0]?html}</a></li>	
												</#if>
												<#if productAttributes.key == "3D_3DS">
													<li><a target="_blank" rel="noopener noreferrer" href="${cadUrls?html}${productAttributes.value[0]?html}" >${productAttributes.value[0]?html}</a></li>
												</#if>
												<#if productAttributes.key == "3D_SKETCHUP">
													<li><a target="_blank" rel="noopener noreferrer" href="${cadUrls?html}${productAttributes.value[0]?html}" >${productAttributes.value[0]?html}</a></li>
												</#if>
												<#if productAttributes.key == "3D_REVIT" >
													<li><a target="_blank" rel="noopener noreferrer" href="${cadUrls?html}${productAttributes.value[0]?html}" >${productAttributes.value[0]?html}</a></li>
												</#if>
											 
												</#list>
											</ul>
											 </#if>	
										<#if cutOffView==true>	 
											<span class="koh-templates-title">${cutoutTemplates?html}</span>
											<ul class="koh-templates-links">
											<#list product.attributes as productAttributes>
												 <#if productAttributes.key == "CUT_OUT_DXF">
													<li><a target="_blank" rel="noopener noreferrer" href="${cadUrls?html}${productAttributes.value[0]?html}" >${productAttributes.value[0]?html}</a></li>
												 </#if>
										   </#list>		 
											</ul>
										 </#if>		
										</div>
									</div>
							</li>
						</#if>
						</ul>
					</div>
				</div> 
			</#list>
		</#if>	
		<#if totalPages?? && (totalPages gt 1)>
			<#include "../../include/pagination_search.ftl">
		</#if>
</div>

 <@hst.webfile path="/" var="link" />
 <@hst.headContribution category="ext-scripts">
     <script src="${link}/js/c-koh-product-details.min.js" type="text/javascript"></script>
 </@hst.headContribution>
         
         