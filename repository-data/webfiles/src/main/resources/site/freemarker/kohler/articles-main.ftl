<#include "../include/imports.ftl">
	<@fmt.message key="facebook" var="facebook" />
    <@fmt.message key="twitter" var="twitter" />
    <@fmt.message key="print" var="print" />
    <@fmt.message key="share" var="share" />
    <@fmt.message key="innovationMetaKeyword" var="innovationMetaKeyword" />
    <@fmt.message key="isSchemaPresent" var="isSchemaPresent"/>
    
    <#assign seoTitle="">
    <#assign metaDescContent="">
    <#assign ogThumbnail="">
    <#if hstRequestContext??>
		<#assign partOfURL = hstRequestContext.getServletRequest().getPathInfo() />
		<#assign currentURL = hstRequestContext.getHstLinkCreator().create(partOfURL, hstRequestContext.getResolvedMount().getMount()).toUrlForm(hstRequestContext, true) />
	</#if>
	<@hst.include ref="container"/>
	<#if document??>    
    	<section class="c-koh-article-pages <#if document.textColor?? && document.backgroundColor?? && document.textColor?has_content && document.backgroundColor?has_content>backgroundchange" style="color:${document.textColor} !important; background-color: ${document.backgroundColor} !important;"<#else>"</#if>>
        	<div class="koh-article-pages-container" >
                <#if document.title?? && document.title?has_content>
				     <#assign title = document.title?replace("(R)","<sup>&reg;</sup>")/>
					 <#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
				     <#assign imgAlt = document.title?replace("(R)","&reg;")/>
					 <#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
				</#if> 
				<#if document.imageAlt?? && document.imageAlt?has_content>
            		<#assign imgAlt=document.imageAlt>
            		<#assign imgAlt = imgAlt?replace("(R)","&reg;")/>
					<#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
            	</#if>
            	<#if document.seoTitle?? && document.seoTitle?has_content>
            		<#assign seoTitle="${document.seoTitle}">
            	<#else>
            		<#assign seoTitle="${document.title}">
            	</#if>
            	<#if document.metaDescription?? && document.metaDescription?has_content>
            		<#assign metaDescContent="${document.metaDescription}">
            	</#if>
            	<#if document.thumbnailUrl?? && document.thumbnailUrl?has_content>
            		<#assign ogThumbnail="${document.thumbnailUrl}">
            	</#if>
                <#if document.videoUrl?? && document.videoUrl?has_content>
                    <#assign externalLink=document.videoUrl />
                    <div class="koh-article-pages-banner embed-responsive embed-responsive-16by9">
                        <iframe src="${externalLink}?rel=0&amp;showinfo=0" frameborder="0" allowfullscreen></iframe>
                    </div>
                    <#elseif document.imageLink?? && document.imageLink?has_content>
                         <#assign docImgLink=document.imageLink />
                        <div class="koh-article-pages-banner">
                            <img src="${docImgLink}" alt="${imgAlt}" <#if document.imageTitle?? && document.imageTitle?has_content> title="${document.imageTitle}"</#if>>
                        </div>
                </#if>
                <header <#if document.titleColor?? && document.titleColor?has_content>style="border-bottom: 1px solid ${document.titleColor} !important;"</#if>>
                    <h1 class="koh-article-pages-title" <#if document.titleColor?? && document.titleColor?has_content>style="color:${document.titleColor} !important;"</#if>><#if document.title?? && document.title?has_content>${title}</#if></h1>
                    <div class="koh-print-share-tools">
                        <div class="koh-page-print">
                            <button onclick="javascript:window.print()" class="koh-page-print-button" data-hasqtip="2">
                                <span class="icon" data-icon=""></span>
                                <span class="label">${print}</span>
                            </button>
                        </div>
                        <div class="koh-page-share">
                            <button class="koh-page-share-button" data-hasqtip="0"><span class="icon" data-icon="&#xe603"></span><span class="label">${share}</span></button>
                            <ul class="koh-page-share-popover">
                                <li>
                                    <button class="koh-share-facebook"><span class="icon" data-icon="&#xe609"></span><span class="label">${facebook}</span></button>
                                </li>
                                <li>
                                    <button class="koh-share-twitter"><span class="icon" data-icon="&#xe60d"></span><span class="label">${twitter}</span></button>
                                </li>
                            </ul>
                        </div>
                    </div>
                </header>
		        <#if (document.titleHeader?? && document.titleHeader?has_content) || (document.description?? && document.description?has_content) || (document.link?? && document.link?has_content)>
		            <div class="col-xs-12 koh-article-description">
		              <#if document.titleHeader?? && document.titleHeader?has_content>
		               <#assign titleHeader = document.titleHeader?replace("(R)","<sup>&reg;</sup>")/>
					   <#assign titleHeader = titleHeader?replace("(TM)","<sup>&trade;</sup>")/>
		                <h3 class="koh-article-pages-text-title" <#if document.titleColor?? && document.titleColor?has_content>style="color:${document.titleColor} !important;"</#if>>${titleHeader}</h3>
		              </#if>   
		                <#if document.description?? && document.description?has_content>
		                    <div class="koh-article-pages-text-body">
		                        <@hst.html hippohtml=document.description/>
		                    </div>
		                </#if>
		                <#if document.link??>
		                    <#assign hstLink=document.link />
		                    <#if hstLink?has_content>
		                        <#assign target="_blank" />
		                        <#assign rel="noopener noreferrer" />
		                        <#if hstLink?starts_with( "/")>
		                            <#assign target="_self" />
		                            <#assign rel="" />
		                        </#if>
		                        <a class="koh-article-pages-text-link" href="${hstLink}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>
		                            <#if document.link?has_content>${document.linkLable}</#if>
		                        </a>
		                    </#if>
		                </#if>
		            </div>
		        </#if>    
        <#if document.relatedDocument?? && document.relatedDocument?has_content>
            <ul class="koh-article-pages-listing" <#if document.titleColor?? && document.titleColor?has_content>style="border-top: 1px solid ${document.titleColor} !important;"</#if>>
                <#list document.relatedDocument as relateditem>
                	<#if relateditem.getClass().getName() == "com.kohler.beans.Article">
	                    <#if relateditem.title?? && relateditem.title?has_content>
	                       <#assign title = relateditem.title?replace("(R)","<sup>&reg;</sup>")/>
						   <#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
	                       <#assign imgAlt = relateditem.title?replace("(R)","&reg;")/>
						   <#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
	                    </#if>
	                   <#if relateditem.imageAlt?? && relateditem.imageAlt?has_content>
		            		<#assign imgAlt=relateditem.imageAlt>
		            		<#assign imgAlt = imgAlt?replace("(R)","&reg;")/>
							<#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
            	       </#if>
	                    <#if (relateditem.videoLink?? && relateditem.videoLink?has_content) || (relateditem.imageLink?? && relateditem.imageLink?has_content)>
	                        <li class="koh-article-pages-section col-xs-12" <#if document.titleColor?? && document.titleColor?has_content>style="border-bottom: 1px solid ${document.titleColor} !important;"</#if>>
								<div class="koh-learn-more">
	                        		<#if relateditem.imageTitle?? && relateditem.imageTitle?has_content>${relateditem.imageTitle}</#if>
	                        	</div>
	                            <div class="koh-article-content col-lg-4 col-md-4 col-sm-4 col-xs-12">
	                                <#if relateditem.videoLink?? && relateditem.videoLink?has_content>
	                                    <#assign externalLink=relateditem.videoLink />
	                                    <div class="koh-article-img-video embed-responsive embed-responsive-16by9">
	                                        <iframe src="${externalLink}?rel=0&amp;showinfo=0" frameborder="0" allowfullscreen></iframe>
	                                    </div>
	                                    <#elseif relateditem.imageLink?? && relateditem.imageLink?has_content>
	                                        <#assign imgLink=relateditem.imageLink />
	                                        <div class="koh-article-img-video">
	                                            <img src="${imgLink}" alt="${imgAlt}" <#if relateditem.imageTitle?? && relateditem.imageTitle?has_content> title="${relateditem.imageTitle}"</#if> >
	                                             <#if relateditem.imageCaption?? && relateditem.imageCaption?has_content>
		                                        	<div class="caption"> 
														${relateditem.imageCaption}
													</div>
	                                        	</#if>
	                                        </div>
	                                </#if>
	                            </div>
	                            <div class="koh-article-content col-lg-8 col-md-8 col-sm-8 col-xs-12">
	                                <#if relateditem.title?? && relateditem.title?has_content>
	                                    <h2 class="koh-article-pages-text-title" <#if document.titleColor?? && document.titleColor?has_content>style="color:${document.titleColor} !important;"</#if>>${title}</h2>
	                                </#if>
	                                <#if relateditem.description?? && relateditem.description?has_content>
	                                    <div class="koh-article-pages-text-body">
	                                        <@hst.html hippohtml=relateditem.description/>
	                                    </div>
	                                </#if>
	                                <#if relateditem.link??>
	                                    <#assign hstLink=relateditem.link />
	                                    <#if hstLink?has_content>
	                                        <#assign target="_blank" />
	                                        <#assign rel="noopener noreferrer" />
	                                        <#if hstLink?starts_with( "/")>
	                                            <#assign target="_self" />
	                                            <#assign rel="" />
	                                        </#if>
	                                        <a class="koh-article-pages-text-link" href="${hstLink}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>
	                                            <#if relateditem.link?has_content>${relateditem.linkLable}</#if>
	                                        </a>
	                                    </#if>
	                                </#if>
	                            </div>
	                        </li>
	                        <#else>
	                            <#if (relateditem.title?? && relateditem.title?has_content) || (relateditem.description?? && relateditem.description?has_content)>
	                                <li class="koh-article-pages-section col-xs-12" <#if document.titleColor?? && document.titleColor?has_content>style="border-bottom: 1px solid ${document.titleColor} !important;"</#if>>
	                                    <div class="koh-article-content col-xs-12">
	                                        <#if relateditem.title?? && relateditem.title?has_content>
	                                            <h2 class="koh-article-pages-text-title" <#if document.titleColor?? && document.titleColor?has_content>style="color:${document.titleColor} !important;"</#if>>${title}</h2>
	                                        </#if>
	                                        <#if relateditem.description?? && relateditem.description?has_content>
	                                            <div class="koh-article-pages-text-body">
	                                                <@hst.html hippohtml=relateditem.description/>
	                                            </div>
	                                        </#if>
	                                        <#if relateditem.link??>
	                                            <#assign hstLink=relateditem.link />
	                                            <#if hstLink?has_content>
	                                                <#assign target="_blank" />
	                                                <#assign rel="noopener noreferrer" />
	                                                <#if hstLink?starts_with( "/")>
	                                                    <#assign target="_self" />
	                                                    <#assign rel="" />
	                                                </#if>
	                                                <a class="koh-article-pages-text-link" href="${hstLink}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>
	                                                    <#if relateditem.link?has_content>${relateditem.linkLable}</#if>
	                                                </a>
	                                            </#if>
	                                        </#if>
	                                    </div>
	                                </li>
	                            </#if>
	                    	</#if>
						<#elseif relateditem.getClass().getName() == "com.kohler.beans.ArticleCarouselDocument">
						  <li class="koh-article-pages-section col-xs-12" <#if document.titleColor?? && document.titleColor?has_content>style="border-bottom: 1px solid ${document.titleColor} !important;"</#if>>	
							<div class="koh-projects-pages-container-banner">
								<section class="c-koh-shopping-guide-carousel v-koh-inline">
								    <div class="koh-carousel koh-theme-dark">
								        <div class="koh-slide-collection">
											<#list relateditem.kohler_articlecarouselcompounddocument as item>
												<#if item.imageURL?? && item.imageURL?has_content>
							            			<#assign imageUrl=item.imageURL />
										            <div class="koh-banner-slide v-koh-simple">
										                <div class="koh-banner-background">
										                     <img src="${imageUrl}" <#if item.imageAlt?? && item.imageAlt?has_content> alt="${item.imageAlt}"</#if> <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if>> 
										                </div>
										            </div>
									            </#if>
											</#list>
							  			</div>
						    		</div>		    
					     		</section>
							</div>
							</li>
						<#elseif relateditem.getClass().getName() == "com.kohler.beans.DescriptiveCarousalDocument">
						 <@hst.html hippohtml=relateditem.description var="relatedItemDescription" />
						 <li class="koh-article-pages-section col-xs-12" <#if document.titleColor?? && document.titleColor?has_content>style="border-bottom: 1px solid ${document.titleColor} !important;"</#if>>
						 	<section class="c-koh-shopping-guide-carousel v-koh-inline koh-banner-thumbnail-comp koh-descriptive-carousel-comp">
						 		<#if (relateditem.title?? && relateditem.title?has_content) || (relatedItemDescription?? && relatedItemDescription?has_content)>
							 		<div class="top">
								        <div class="header-small" <#if document.titleColor?? && document.titleColor?has_content>style="color:${document.titleColor} !important;"</#if>>${relateditem.title}</div>
								        <div class="copy"><@hst.html hippohtml=relateditem.description/></div>
								        <div class="line"></div>
							      	</div>
						      	</#if>
								<div class="koh-carousel koh-theme-dark">
									<div class="koh-slide-collection koh-thumbnail-slide-collection">
										<#list relateditem.kohler_articledescriptivecarousaldocument as item>
											<div class="koh-banner-slide v-koh-simple m-koh-linked">
												<#assign navigationLink = "" />
												<#if item.navigationLink?? && item.navigationLink?has_content>
							                		<#assign navigationLink = item.navigationLink />
							                		<#assign target="_blank" />
	                                                <#if navigationLink?starts_with( "/")>
	                                                	<#assign target="_self" />
	                                                </#if>
	                                            </#if>
	                                            <#if item.carousalImageURL?? && item.carousalImageURL?has_content>
		                                            <#if navigationLink?? && navigationLink?has_content>
		                                            	<a href="${navigationLink}" target="${target}">
		                                            </#if>
	                                                 <#if item.carousalImageAlt?? && item.carousalImageAlt?has_content>
		                                            	<#assign carousalAlt=item.carousalImageAlt />
		                                            <#else>
		                                                <#assign carousalAlt=title />
		                                             </#if>  
													<div class="koh-banner-background">
														<img src="${item.carousalImageURL}" alt="${carousalAlt}" <#if item.carousalImageTitle?? && item.carousalImageTitle?has_content> title="${item.carousalImageTitle}"</#if> >
													</div>
												
													<#if navigationLink?? && navigationLink?has_content>
														</a>
													</#if>	
												</#if>
												
												<div class="koh-banner-content koh-valignment-middle">
												    <span class="koh-banner-text" style="">
												    	<#if item.description?? && item.description?has_content>
													    	<#if navigationLink?? && navigationLink?has_content>
				                                                <a href="${navigationLink}" target="${target}">
				                                            </#if>
				                                            
															<span class="koh-banner-title" style=""><@hst.html hippohtml=item.description/></span>
															
															<#if navigationLink?? && navigationLink?has_content>
																</a>
															</#if>
														</#if>	
															
										                <div class="more-buttons">
										                	<#if item.learnMoreLink?? && item.learnMoreLink?has_content>
										                		<#assign learnMoreLink = item.learnMoreLink />
										                		<#assign target="_blank" />
				                                                <#if learnMoreLink?starts_with( "/")>
				                                                    <#assign target="_self" />
				                                                </#if>
											                	<#if item.learnMoreLabel?? && item.learnMoreLabel?has_content>
									                          		<a href="${learnMoreLink}"  target="${target}" class="sw-button">${item.learnMoreLabel}</a>
								                          		</#if>
															</#if>
															<#if item.viewAllLink?? && item.viewAllLink?has_content>
																<#assign viewAllLink = item.viewAllLink />
										                		<#assign target="_blank" />
				                                                <#if viewAllLink?starts_with( "/")>
				                                                    <#assign target="_self" />
				                                                </#if>
																<#if item.viewAllLabel?? && item.viewAllLabel?has_content>
								                          			<a href="${viewAllLink}" target="${target}" class="sw-button">${item.viewAllLabel}</a>
								                          		</#if>
															</#if>
									                       </div>
									                        <#if (item.logoImageURL1?? && item.logoImageURL1?has_content) || (item.logoImageURL2?? && item.logoImageURL2?has_content)>
										                        <div class="more-seals">
										                          <div class="seal1 seal seal-2"><img src="${item.logoImageURL1}" <#if item.logoImageURL1Title?? && item.logoImageURL1Title?has_content> title="${item.logoImageURL1Title}"</#if>
																<#if item.logoImageURL1Alt?? && item.logoImageURL1Alt?has_content> alt="${item.logoImageURL1Alt}"</#if>></div>
										                          <div class="seal2"><img src="${item.logoImageURL2}" <#if item.logoImageURL2Title?? && item.logoImageURL2Title?has_content> title="${item.logoImageURL2Title}"</#if>
																<#if item.logoImageURL2Alt?? && item.logoImageURL2Alt?has_content> alt="${item.logoImageURL2Alt}"</#if>></div>
										                        </div>
									                        </#if>
														</span>
													</div>
								            	</div>
								            </#list>
										</div>
									<div class="koh-thumbnail-slide-navigation">
										<#list relateditem.kohler_articledescriptivecarousaldocument as item>  
										  <#if item.carousalImageAlt?? && item.carousalImageAlt?has_content>
		                                       <#assign carousalAlt=item.carousalImageAlt />
		                                  <#else>
		                                        <#assign carousalAlt=title />
		                                  </#if> 
											<div class="thumb">						
												<img src="${item.carousalImageURL}" alt="${carousalAlt}" <#if item.carousalImageTitle?? && item.carousalImageTitle?has_content> title="${item.carousalImageTitle}"</#if>>					
											</div>
										</#list>		
									</div>
								</div>
							</section>
						 </li>
						<#elseif relateditem.getClass().getName() == "com.kohler.beans.ArticleThreeImageDocument">
					   <li class="koh-article-pages-section col-xs-12" <#if document.titleColor?? && document.titleColor?has_content>style="border-bottom: 1px solid ${document.titleColor} !important;"</#if>>
						 <section class="koh-three-image-carousel">
						   <div class="koh-carousel">
						   <#if (relateditem.title?? && relateditem.title?has_content) || (relateditem.description.content?? && relateditem.description.content?has_content)>
						      <div class="koh-main-title" <#if document.titleColor?? && document.titleColor?has_content>style="color:${document.titleColor} !important;"</#if>>${relateditem.title}</div>
						      <div class="koh-text-description"><@hst.html hippohtml=relateditem.description/></div>
					      	</#if>
						      <div class="koh-img-carousel">
					      		<#list relateditem.articleThreeImageCompound as item>
						         	<div class="koh-images-caption">
							         	<#if item.navigationLink?? && item.navigationLink?has_content>
							         		<#assign navigationLink = item.navigationLink />
					                		<#assign target="_blank" />
	                                        <#if navigationLink?starts_with( "/")>
	                                            <#assign target="_self" />
	                                        </#if>
											<a href="${navigationLink}" target="${target}">
												<#if item.imagePath?? && item.imagePath?has_content>
								            		<img src="${item.imagePath}" <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if>
                                                  <#if item.imageAlt?? && item.imageAlt?has_content> alt="${item.imageAlt}"</#if>>
								            		<#if item.imageDescription.content?? && item.imageDescription.content?has_content>
										            <span class="koh-outerLayer-Caption">
											            <span class="koh-carousel-imageCaption">
											            	<@hst.html hippohtml=item.imageDescription/>
											            </span>
							           		 		</span>
								           		  </#if>
												</#if>
											</a>
										<#else>
											<#if item.imagePath?? && item.imagePath?has_content>
							            		<img src="${item.imagePath}" <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if>
                                                  <#if item.imageAlt?? && item.imageAlt?has_content> alt="${item.imageAlt}"</#if>>
							            		<#if item.imageDescription.content?? && item.imageDescription.content?has_content>
														<span class="koh-outerLayer-Caption">
															<span class="koh-carousel-imageCaption">
																<@hst.html hippohtml=item.imageDescription/>
															</span>
														</span>
													</#if>
												</#if>
											</#if>
										</div>
									</#list>
								</div>
							</div>
						</section>
					</li>
					<#elseif relateditem.getClass().getName() == "com.kohler.beans.CategoryBanner">
					<li class="koh-article-pages-section col-xs-12" <#if document.titleColor?? && document.titleColor?has_content>style="border-bottom: 1px solid ${document.titleColor} !important;"</#if>>
					<div class="koh-patricia-section">
							<div class="row koh-patricia-section-row ">
								<#if relateditem.navigationPath?? && relateditem.navigationPath?has_content>
									<#assign navigationURL = relateditem.navigationPath>
									<#assign target="_blank" />
			                        <#if navigationURL?starts_with( "/")>
			                            <#assign target="_self" />
			                        </#if>
								</#if>
								<#if relateditem.navigationPath?? && relateditem.navigationPath?has_content>
									<a href="${navigationURL}"  target="${target}">
										<div class="koh-patricia-section-img">
											<#if relateditem.imagePath?? && relateditem.imagePath?has_content>
					                        	<img src="${relateditem.imagePath}" <#if relateditem.imageAlt?? && relateditem.imageAlt?has_content> alt="${relateditem.imageAlt}"</#if>>
						                    </#if>
						                </div>
					                </a>
				                <#else>
				                	<div class="koh-patricia-section-img">
										<#if relateditem.imagePath?? && relateditem.imagePath?has_content>
				                        	<img src="${relateditem.imagePath}" <#if relateditem.imageAlt?? && relateditem.imageAlt?has_content> alt="${relateditem.imageAlt}"</#if>>
					                    </#if>
					                </div>
				                </#if>
								<div class="koh-patricia-section-item-title">
									<div class="koh-patricia-section-title-inner">
										<#if relateditem.title?? && relateditem.title?has_content>
					                       <#assign title = relateditem.title?replace("(R)","<sup>&reg;</sup>")/>
										   <#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
					                    </#if>
										<#if title?? && title?has_content>
											<h2 class= "koh-patricia-section-first-title">${title}</h2>
										</#if>
										<#if relateditem.imageTitle?? && relateditem.imageTitle?has_content>
											<div class="koh-patricia-section-subtitle">${relateditem.imageTitle}</div>
										</#if>
				                        <#if relateditem.content?? && relateditem.content?has_content>
											<div class="koh-patricia-section-description"><@hst.html hippohtml=relateditem.content/></div>
										</#if>
									</div>
								</div>
							</div>
						</div>
					</li>
				</#if>
			</#list>
            </ul>
        </#if>
        </div>
    </section>
   </#if>
  
	<#if metaDescContent?? && metaDescContent?has_content>
        <@hst.headContribution category="meta-tags">
            <meta property="og:description" content="${metaDescContent}" />
        </@hst.headContribution>
	</#if>
    <#if document?? && document.title??>
        <@fmt.message key="company.name" var="companyName" />
         <@hst.headContribution category="meta-tags">
		  	<meta name="twitter:title" content="${seoTitle} | ${companyName}" />
		</@hst.headContribution>
		<@hst.headContribution category="meta-tags">
		  	<meta property="og:title" content="${seoTitle} | ${companyName}" />
		</@hst.headContribution>
		<#if companyName?? && companyName?has_content>
			<@hst.headContribution category="meta-tags">
			  	<meta property="og:site_name" content="${companyName}" />
			</@hst.headContribution>
			<@hst.headContribution category="meta-tags">
		  		<meta name="twitter:site" content="@${companyName}" />
			</@hst.headContribution>
		</#if>
   	</#if>

	<#if docImgLink?? && docImgLink?has_content>
		<@hst.headContribution category="meta-tags">
	  		<meta name="twitter:card" content="${docImgLink?html}" />
		</@hst.headContribution>
		<@hst.headContribution category="meta-tags">
		  	<meta name="twitter:image:src" content="${docImgLink?html}" />
		</@hst.headContribution>
	</#if>
	<#if ogThumbnail?? && ogThumbnail?has_content>	
		<@hst.headContribution category="meta-tags">
		  	<meta property="og:image" content="${ogThumbnail}" />
		</@hst.headContribution>
	<#elseif docImgLink?? && docImgLink?has_content>
		<@hst.headContribution category="meta-tags">
			<meta property="og:image" content="${docImgLink?html}" />
		</@hst.headContribution>
	</#if>

	<@hst.headContribution category="ext-scripts">
			<@hst.webfile path="/" var="link" />
			<script src="${link}/js/custom/c-koh-articles-three-image-carousel.min.js" type="text/javascript"></script>
	</@hst.headContribution>
	
<#if isSchemaPresent?? && isSchemaPresent?has_content>
	<@hst.headContribution category="schema">
		<#if document??>
			<#if document.title?? && document.title?has_content>
				<#assign title=document.title/>
			</#if>
			<#if document.imageLink?? && document.imageLink?has_content>
				<#assign image=document.imageLink/>
			<#else>
				<#assign image="https://www.kohler.com.vn/binaries/content/gallery/kohler/home/kohler-logo-261x146.png"/>
			</#if>
			 <#if document.metaDescription?? && document.metaDescription?has_content>
				<#assign description=document.metaDescription/>
			</#if>
		</#if>
		<script type="application/ld+json">
			{
			  "@context": "https://schema.org",
			  "@type": "Article",
			  "headline": "${title}",
			  "Description": "${description}",
			  "image": "${image}",
			  "url": "${currentURL}"
		  	}
		</script>
	</@hst.headContribution>
</#if>
