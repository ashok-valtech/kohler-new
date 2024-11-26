<#include "../include/imports.ftl">

<@hst.include ref="container"/>	

<@hst.setBundle basename="seo.labels"/>
<@fmt.message key="seo-form-canonical-url-label" var="canonicalUrlLabel"/>
<@fmt.message key="seo-form-siteMap-label" var="siteMapLabel"/>
<@fmt.message key="seo-form-description-label" var="seoDescription"/>
<@fmt.message key="seo-form-header-name" var="seoFormHeaderName"/>
<@fmt.message key="seo-form-no-follow-label" var="seoFormNoFollowLabel"/>
<@fmt.message key="seo-form-no-index-label" var="seoFormNoIndexLabel"/>
<@fmt.message key="seo-form-page-document-label" var="seoFormPageDocumentLabel"/>
<@fmt.message key="seo-form-seo-page-label" var="seoFormSeoPageLabel"/>
<@fmt.message key="seo-form-submit-button-label" var="seoFormSubmitButtonLabel"/>
<@fmt.message key="seo-form-title-label" var="seoFormTitleLabel"/>
<@fmt.message key="seo-update-page-title" var="seoUpdatePageTitle"/>
<@fmt.message key="seo-visit-link" var="seoVisitLink"/>
<@fmt.message key="seo-no-products" var="seoNoProducts"/>
<@fmt.message key="seo-pagination" var="seoPagination"/>
<@fmt.message key="seo-alert-message" var="seoAlertMessage"/>
<@fmt.message key="seo-regex-valid" var="seoregexexp"/>

<@fmt.message key="seo-title-Error-message" var="seoTitleErrorMessage"/>
<@fmt.message key="seo-desc-Error-message" var="seoDescErrorMessage"/>
<@fmt.message key="seo-canonicalURL-Error-message" var="seoCanonicalURLErrorMessage"/>
<@fmt.message key="seo-title-Error-message2" var="seoTitleErrorMessage2"/>
<@fmt.message key="seo-title-max-Character-value" var="seoTitleCharValue"/>
<@fmt.message key="seo-desc-max-Character-value" var="seoDescCharValue"/>
<@fmt.message key="seo-canonicalURL-max-Character-value" var="seoCanonicalURLCharValue"/>
<@fmt.message key="error-msg-document-in-use" var="documentInUseErrorMsg"/>
<@fmt.message key="category-templatenames" var="categoryTemplateNames"/>
<#assign titleErrorMessage2 = seoTitleErrorMessage2?replace("{0}","${seoTitleCharValue}")/>
<#assign descErrorMessage = seoDescErrorMessage?replace("{0}","${seoDescCharValue}")/>
<#assign canonicalURLErrorMessage = seoCanonicalURLErrorMessage?replace("{0}","${seoCanonicalURLCharValue}")/>
<#assign currentSite=hstRequestContext.getResolvedMount().getMount() />
<#assign parentSite=currentSite />
<#if currentSite.getParent()??>
	<#assign parentSite=currentSite.getParent() />
</#if>
<@hst.link var="rootSiteMapItem" siteMapItemRefId="root"/>
<#assign currentBaseUrl = hstRequestContext.getHstLinkCreator().create(rootSiteMapItem, parentSite).toUrlForm(hstRequestContext, true) />

<@hst.link var="seoUpdateSiteMapItem" siteMapItemRefId="seo-update"/>
<@hst.link var="productDetailsId" siteMapItemRefId="productDetails"/>

    <@hst.headContribution category="title">
        <META NAME="ROBOTS" CONTENT="NOINDEX, NOFOLLOW" />
    </@hst.headContribution>
    <#if seoTemplateName??>
     	<#assign selectedtemplateName=seoTemplateName>
    </#if>
    <#assign seoUrl=seoUpdateSiteMapItem + "?seoTemplateName">
    <div class="c-koh-seo-page">
       <div class="koh-seo-form-def1">
            <div class="koh-seo-container-def1">
                <div class="koh-seo-header-def1">
                    <div class="koh-seo-title-def1">
                        ${seoUpdatePageTitle}
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="productDetailsId" value="${productDetailsId}" />
        <input type="hidden" id="titleErrorMessage2Id" value="${titleErrorMessage2}" />
		<input type="hidden" id="descErrorMessageId" value="${descErrorMessage}" />
		<input type="hidden" id="canonicalURLErrorMessageId" value="${canonicalURLErrorMessage}" />
		<input type="hidden" id="seoTitleErrorMessageId" value="${seoTitleErrorMessage}" />
		<input type="hidden" id="seoTitleCharValueId" value="${seoTitleCharValue}" />
		<input type="hidden" id="seoDescCharValueId" value="${seoDescCharValue}" />
		<input type="hidden" id="seoCanonicalURLCharValueId" value="${seoCanonicalURLCharValue}" />
		<#if updatedDocument?? && updatedDocument?has_content>
			<input type="hidden" id="updatedDocument" value="${updatedDocument}" />
		</#if>
        <div class="koh-seo-search-filters">
            <div class="koh-seo-search-filters-content">
                <div class="koh-seo-filter-group">
                    <div class="koh-seo-available-filters">
                    <#if siteSeoProdCategorys?? && siteSeoProdCategorys?has_content && siteSeoProdCategorys?size gt 0 >
                    <#if isProduct?? && isProduct?has_content && isProduct=="true">
                   <div class="koh-seo-ul">
                      <div class="koh-seo-li">
                          <a href="${seoUrl?html}">${productDisplayName}
							 <span class="glyphicon-minus"></span>
							</a>
                         </div>
                       </div>
			        <#else>
			        <div class="koh-seo-ul">
                      <div class="koh-seo-li">
                        <a href="${seoUrl?html}=&isProduct=true">${productDisplayName}
							 <span class="glyphicon-plus"></span>
							</a>
                         </div>
                       </div>
			        </#if>
			       </#if>
                    <#if isProduct?? && isProduct?has_content && isProduct=="true">
    <@hst.setBundle basename="essentials.searchbox"/>
   <div class="input-group">
     <form class="navbar-form" role="search" action="seo" method="get">
        <@fmt.message key='searchbox.placeholder' var="placeholder"/>
         <input type="text" class="form-control" placeholder="Search" value="<#if query?? && query?has_content>${query}</#if>" name="query"  pattern="^[a-zA-Z0-9-]{1,20}$"  oninvalid="setCustomValidity('${seoAlertMessage}')" onchange="try{setCustomValidity('')}catch(e){}" required>
         <input type="hidden" id="isProduct" name="isProduct" class="form-control" value="true" readonly/>
         <input type="hidden" id="seoTemplateName" name="seoTemplateName" class="form-control" value="Product Results" readonly/>
         <input type="hidden" id="relativePath" name="relativePath" class="form-control" value="${relativePathAttribute}" readonly/>
         <input type="hidden" id="type" name="type" class="form-control" value="template" readonly/>
         <input type="hidden" id="className" name="className" class="form-control" value="${classNameAttribute}" readonly/>
         <input type="hidden" id="currentPage" name="currentPage" class="form-control" value="1" readonly/>
         <div style="line-height:50%;">
            <br>
         </div><p align="right">
         <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button></p>
         </form> 
    </div>
                    <#if totalPages?? && totalPages?has_content>
                    <#assign pageSize= totalPages?size>
                    </#if>
                     <#if siteSeoProdCategorys??>
                                    <#list siteSeoProdCategorys?keys as CTemplateName>
                                     <#assign ListValues = siteSeoProdCategorys[CTemplateName]>
                                    <#assign displaynameC = CTemplateName>
                                    <#assign relativePathC = "">
                                    <#assign classNameC = "">
                                    <#assign lengthC = "0">
                                    <#if ListValues['displayName']??>
                                    	<#assign displaynameC = ListValues['displayName']>
                                    </#if>
                                    <#if ListValues['relativePath']??>
                                    	<#assign relativePathC = ListValues['relativePath']>
                                    </#if>
                                    <#if ListValues['className']??>
                                    	<#assign classNameC = ListValues['className']>
                                    </#if>
                                    <#if ListValues['length']??>
                                    	<#assign lengthC = ListValues['length']>
                                    </#if>
                                    <#assign length_1C = lengthC?number>
                                        <#if selectedtemplateName?? && selectedtemplateName==CTemplateName>
                                            <div class="koh-seo-ul">
                                               <div class="koh-seo-li <#if length_1C lte 0>active</#if>">
                                                    <a href="${seoUrl?html}=&isProduct=true">${displaynameC}
									                  <#if length_1C gt 0>
									                  	<span class="glyphicon-minus"></span>
									                  </#if>
								                  	</a>
                                                    <#if seoPageDocumentsValues??>
                                                    <#if pageSize?? && pageSize?has_content && pageSize gt 0>
                                                        <#list seoPageDocumentsValues?keys as prop>
                                                            <#assign seoPageData=seoPageDocumentsValues[prop]>
                                                            	<#assign seoTitle="">
                                                            	<#assign seoDesc="">
                                                            	<#assign hippoName="">
                                                            	<#assign noIndex="">
                                                            	<#assign noFollow="">
                                                            	<#assign canonicalUrl="">
                                                            	<#assign categoryKey="">
                                                                <#if seoPageData['seoTitle']??>
                                                                    <#assign seoTitle=seoPageData['seoTitle']>
                                                                </#if>
                                                                <#if seoPageData['seoDesc']??>
                                                                    <#assign seoDesc=seoPageData['seoDesc']>
                                                                </#if>
                                                                <#if seoPageData['hippoName']??>
                                                                    <#assign hippoName=seoPageData['hippoName']>
                                                                </#if>
                                                                <#if seoPageData['noIndex']?? > 
														       		<#assign noIndex=seoPageData['noIndex']>
														     	</#if>
														     	<#if seoPageData['noFollow']?? >
														       		<#assign noFollow=seoPageData['noFollow']>
														     	</#if>
															    <#if seoPageData['canonicalUrl']?? >
															        <#assign canonicalUrl=seoPageData['canonicalUrl']>
															    </#if>
															    <#if seoPageData['categoryKey']?? >
															        <#assign categoryKey=seoPageData['categoryKey']>
															    </#if>
                                                               <div class="name" 
																data-prop="${prop?html}" data-seoTitle="${seoTitle?html}"  data-seoDesc="${seoDesc?html}"  data-noIndex="${noIndex?html}"  data-noFollow="${noFollow?html}"  data-canonicalUrl="${canonicalUrl?html}" <#if categories?? && categories?has_content>data-category="${categories?html}</#if>${categoryKey}">
																<div id="koh-seo-active-filter"> 
																	<div class="koh-active-seo"> 
																		${hippoName}
																	</div>
																</div>
															 </div>
                                                        </#list>
                                                        <#else>
                                                         <div style="line-height:50%;">
           														 <br>
         												 </div>
                                                         ${seoNoProducts}
                                                        </#if>
                                                    </#if>
                                                </div>
                                            </div>
                                            <#if pageSize?? && pageSize?has_content && pageSize gt 1>
                                              </br>
                                            </#if>
                                              <#assign capage = currentPageAttribute?number>
                                              <#if pageSize?? && pageSize?has_content && pageSize gt 1>
                                               ${seoPagination}&nbsp; 
                                              <#if previousPageLs?? && previousPageLs?has_content>
                                               <a href="${seoUrl?html}=${CTemplateName?html}&type=template<#if relativePathC?? && relativePathC?has_content>&relativePath=${relativePathC?html}</#if><#if classNameC?has_content>&className=${classNameC?html}</#if><#if query?has_content>&query=${query?html}</#if>&isProduct=true&currentPage=${previousPageLs}">&lt;</a>
                                              </#if>
                                               &nbsp;
                                               <#if previousPage?? && previousPage?has_content>
                                                  <a href="${seoUrl?html}=${CTemplateName?html}&type=template<#if relativePathC?? && relativePathC?has_content>&relativePath=${relativePathC?html}</#if><#if classNameC?has_content>&className=${classNameC?html}</#if><#if query?has_content>&query=${query?html}</#if>&isProduct=true&currentPage=${previousPage}">${previousPage}</a>
                                                  </#if>
                                                 &nbsp;
                                                  <select onchange="location = this.value;">
                                                  <#list totalPages as pageNo>
                                                    <#if capage!=pageNo>
													  <option value="${seoUrl?html}=${CTemplateName?html}&type=template<#if relativePathC?? && relativePathC?has_content>&relativePath=${relativePathC?html}</#if><#if classNameC?has_content>&className=${classNameC?html}</#if><#if query?has_content>&query=${query?html}</#if>&isProduct=true&currentPage=${pageNo}">${pageNo}</option>
													 <#else> 
													   <option value="${seoUrl?html}=${CTemplateName?html}&type=template<#if relativePathC?? && relativePathC?has_content>&relativePath=${relativePathC?html}</#if><#if classNameC?has_content>&className=${classNameC?html}</#if><#if query?has_content>&query=${query?html}</#if>&isProduct=true&currentPage=${pageNo}" selected>${pageNo}</option>
													</#if>
												 </#list> 
											 	  </select>
											 	  &nbsp;
											 	  
											 	<#if nextPage?? && nextPage?has_content>
											 	  <a href="${seoUrl?html}=${CTemplateName?html}&type=template<#if relativePathC?? && relativePathC?has_content>&relativePath=${relativePathC?html}</#if><#if classNameC?has_content>&className=${classNameC?html}</#if><#if query?has_content>&query=${query?html}</#if>&isProduct=true&currentPage=${nextPage}">${nextPage}</a>
											 	 </#if> 
											 	   &nbsp;
											 	  <#if nextPageGt?? && nextPageGt?has_content>
											 	  <a href="${seoUrl?html}=${CTemplateName?html}&type=template<#if relativePathC?? && relativePathC?has_content>&relativePath=${relativePathC?html}</#if><#if classNameC?has_content>&className=${classNameC?html}</#if><#if query?has_content>&query=${query?html}</#if>&isProduct=true&currentPage=${nextPageGt}">&gt</a>
											 	 </#if> 
											 	 </br> 
											 </#if>	 
                                            <#else>
                                                <div class="koh-seo-ul">
                                                    <div class="koh-seo-li">
                                                         <a href="${seoUrl?html}=${CTemplateName?html}&type=template<#if relativePathC?? && relativePathC?has_content>&relativePath=${relativePathC?html}</#if><#if classNameC?has_content>&className=${classNameC?html}</#if><#if query?has_content>&query=${query?html}</#if>&isProduct=true&currentPage=1">${displaynameC}
										                   <#if length_1C gt 0>
										                  	<span class="glyphicon-plus"></span>
										                  </#if>
									                  	</a>
                                                    </div>
                                                </div>
                                         </#if> 
                                </#list>
                            </#if>
                           </#if> 
                            <#if siteSeoPages??>
                                <#list siteSeoPages?keys as templateName>
                                	<#assign categories = "">
                                	<#if categoryTemplateNames?split(",")?seq_contains(templateName)>
                                		<#assign categories = "/Categories/">
                                	</#if>
                                    <#assign ListValues = siteSeoPages[templateName]>
                                    <#assign displayname = templateName>
                                    <#assign relativePath = "">
                                    <#assign className = "">
                                    <#assign length = "0">
                                    <#if ListValues['displayName']??>
                                    	<#assign displayname = ListValues['displayName']>
                                    </#if>
                                    <#if ListValues['relativePath']??>
                                    	<#assign relativePath = ListValues['relativePath']>
                                    </#if>
                                    <#if ListValues['className']??>
                                    	<#assign className = ListValues['className']>
                                    </#if>
                                    <#if ListValues['length']??>
                                    	<#assign length = ListValues['length']>
                                    </#if>
                                    <#assign length_1 = length?number>
                                        <#if selectedtemplateName?? && selectedtemplateName==templateName>
                                            <div class="koh-seo-ul">
                                               <div class="koh-seo-li <#if length_1 lte 0>active</#if>">
                                                    <a href="${seoUpdateSiteMapItem}">${displayname}
									                  <#if length_1 gt 0>
									                  	<span class="glyphicon-minus"></span>
									                  </#if>
								                  	</a>
                                                    <#if seoPageDocumentsValues??>
                                                        <#list seoPageDocumentsValues?keys as prop>
                                                            <#assign seoPageData=seoPageDocumentsValues[prop]>
                                                            	<#assign seoTitle="">
                                                            	<#assign seoDesc="">
                                                            	<#assign hippoName="">
                                                            	<#assign noIndex="">
                                                            	<#assign noFollow="">
                                                            	<#assign canonicalUrl="">
                                                            	<#assign categoryKey="">
                                                                <#if seoPageData['seoTitle']??>
                                                                    <#assign seoTitle=seoPageData['seoTitle']>
                                                                </#if>
                                                                <#if seoPageData['seoDesc']??>
                                                                    <#assign seoDesc=seoPageData['seoDesc']>
                                                                </#if>
                                                                <#if seoPageData['hippoName']??>
                                                                    <#assign hippoName=seoPageData['hippoName']>
                                                                </#if>
                                                                <#if seoPageData['noIndex']?? >
														       		<#assign noIndex=seoPageData['noIndex']>
														     	</#if>
														     	<#if seoPageData['noFollow']?? >
														       		<#assign noFollow=seoPageData['noFollow']>
														     	</#if>
															    <#if seoPageData['canonicalUrl']?? >
															        <#assign canonicalUrl=seoPageData['canonicalUrl']>
															    </#if>
															    <#if seoPageData['categoryKey']?? >
															        <#assign categoryKey=seoPageData['categoryKey']>
															    </#if>
                                                               <div class="name" 
																data-prop="${prop?html}" data-seoTitle="${seoTitle?html}"  data-seoDesc="${seoDesc?html}"  data-noIndex="${noIndex?html}"  data-noFollow="${noFollow?html}"  data-canonicalUrl="${canonicalUrl?html}" data-category="${categories?html}${categoryKey}">
																<div id="koh-seo-active-filter"> 
																	<div class="koh-active-seo"> 
																		${hippoName} 
																	</div>
																</div>
															 </div>
                                                        </#list>
                                                    </#if>
                                                </div>
                                            </div>
                                            <#else>
                                                <div class="koh-seo-ul">
                                                    <div class="koh-seo-li">
                                                         <a href="${seoUrl?html}=${templateName?html}&type=template<#if relativePath?? && relativePath?has_content>&relativePath=${relativePath?html}</#if><#if className?has_content>&className=${className?html}</#if>&isProduct=false">${displayname}
										                   <#if length_1 gt 0>
										                  	<span class="glyphicon-plus"></span>
										                  </#if>
									                  	</a>
                                                    </div>
                                                </div>
                                        </#if>
                                </#list>
                            </#if>
                         <div id="koh-seo-sm-scroll"></div>
                    </div>
                </div>
            </div>
        </div>
        <#-- Layer between form and filters -->
        <#if seoTemplateName?? && seoTemplateName?has_content>
         <#if pageSize?? && pageSize?has_content && pageSize gt 0 || isProduct=="false">
            <div class="koh-seo-form">
                <#if successKey??>
	                <div id="successMessage">
	                	${successKey}
	            	</div>
            	<#elseif eroorKey??>
            		<div id="errorMessage">
	                	<#if documentInUse?? && documentInUse?has_content> ${documentInUseErrorMsg} <#else>${eroorKey}</#if>
	            	</div>
                </#if>
                <div class="koh-seo-container">
                    <div class="koh-seo-header">
                        <div class="koh-seo-title">
                            ${seoFormHeaderName}
                        </div>
                    </div>
                    <#assign visitTitle = currentBaseUrl>
                    <#if siteMapItem?? && siteMapItem?has_content &&  siteMapItem != "/">
                    	<#if visitTitle?ends_with("/")>
                     		<#assign visitTitle = visitTitle + siteMapItem >
                     	<#else>
                     		<#assign visitTitle = visitTitle + "/" + siteMapItem >
                     	</#if> 	
                    </#if> 
                    <#if pageDocumentAttribute?? && pageDocumentAttribute?has_content>
                    	<#if visitTitle?ends_with("/")>
                     		<#assign visitTitle = visitTitle + pageDocumentAttribute + ".html" >
                     	<#else>
                     		<#assign visitTitle = visitTitle + "/" + pageDocumentAttribute + ".html" >
                     	</#if> 
                   </#if> 
                    <div class="koh-visitpage"><a href="" title="${seoVisitLink}: ${visitTitle}" class="visit-an"><span class="koh-icon-external"></span>${seoVisitLink}</a></div>
                    <div class="koh-seo-form-wrapper">
                        <form action="seo" method="post" class="form-horizontal" id="seoUpdateForm">
                            <div class="form-group">
                                <label class="label control-label">${seoFormTitleLabel}</label>
                                <input type="text" id="seo-title" name="seo-title" value="<#if sitetitle??>${sitetitle?html}</#if>" class="form-control">
                            </div>
                            <div class="form-group">
                                <label class="label control-label"> ${seoDescription}</label>
                                <input type="text" id="seo-description" name="seo-description" value="<#if defaultMetaDesc??>${defaultMetaDesc?html}</#if>" class="form-control">
                            </div>
                            <div class="form-group">
                                <input type="checkbox" id="noIndex" name="noIndex" value="1" <#if noIndexAttribute?? && noIndexAttribute=="checked">checked</#if> class="icon">
                                <label class="label control-label"> ${seoFormNoIndexLabel}</label>
                                <input type="checkbox"  id="noFollow" name="noFollow" value="1" <#if noFollowAttribute?? && noFollowAttribute=="checked">checked</#if> class="icon">
                                <label class="label control-label">${seoFormNoFollowLabel}</label>
                            </div>
                            <div class="form-group">
                                <label class="label control-label">${canonicalUrlLabel}</label>
                                <input type="text" id="canonicalUrl" name="canonicalUrl"  value="<#if canonicalUrlAttribute??>${canonicalUrlAttribute?html}</#if>" class="form-control">
                            </div>  
                            <#if seoTemplateName??>                
                                <input type="hidden" id="seoTemplateName" name="seoTemplateName" class="form-control" value="${seoTemplateName}" readonly/>
                            </#if>    
                            <#if siteMapItem??>
                               <input type="hidden" id="siteMapItem" name="siteMapItem" class="form-control" value="${siteMapItem?html}" readonly/>
                            </#if>
                             <#if relativePathAttribute??>
                                <input type="hidden" id="relativePath" name="relativePath" class="form-control" value="${relativePathAttribute?html}" readonly/>
                             </#if>
                              <#if classNameAttribute??>
                                <input type="hidden" id="className" name="className" class="form-control" value="${classNameAttribute?html}" readonly/>
                              </#if>
                            <#if currentPageAttribute??>
                                <input type="hidden" id="currentPage" name="currentPage" class="form-control" value="${currentPageAttribute?html}" readonly/>
                              </#if>  
                               <#if isProduct??>
                                <input type="hidden" id="isProduct" name="isProduct" class="form-control" value="${isProduct?html}" readonly/>
                              </#if> 
                               <#if query??>
                               <input type="hidden" id="query" name="query" class="form-control" value="${query}" readonly/>
                               </#if> 
                                <input type="hidden" id="category" name="category" class="form-control" value="" readonly/>
                                <input type="hidden" id="seoTemplateName" name="seoTemplateName" class="form-control" value="${seoTemplateName?html}" readonly/>
                                <input type="hidden" id="pageDocument" name="pageDocument" class="form-control" value="<#if pageDocumentAttribute??>${pageDocumentAttribute?html}</#if>" readonly required />
                            <div class="form-group">
                                <button type="submit" value="Submit" class="button-seo">${seoFormSubmitButtonLabel}</button>
                            </div>
                        </form>
                    </div>
                  </div>
                </div>
               </#if>   
          <#else>
            <div class="koh-seo-form-def">
                <div class="koh-seo-container-def">
                    <div class="koh-seo-header-def">
                        <div class="koh-seo-title-def">
                            ${seoUpdatePageTitle}
                        </div>
                    </div>
                </div>
            </div>
        </#if>
         
        <#if hstRequestContext?? && hstRequestContext.servletRequest?? && hstRequestContext.servletRequest.getSession(false)??>
        	<a class="koh-fixed-logout-btn" href="/login/logout?destination=${destinationPage?html}"><span class="koh-icon-envelope"></span>Logout</a>
        </#if>
    </div>
    
<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
  <script src="${link}/js/custom/koh-seo-update.min.js" type="text/javascript"></script>
</@hst.headContribution>  

