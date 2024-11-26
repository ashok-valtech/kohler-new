<#include "../include/imports.ftl">
<@hst.setBundle basename="apac.labels"/>
<#if thumbnailBanners??>
		<section class="c-koh-banner-carousel v-koh-inline koh-banner-thumbnail-comp">
			<div class="koh-carousel koh-theme-dark">
				<div class="koh-slide-collection koh-thumbnail-slide-collection">
					<#list thumbnailBanners as item>
				       <#assign colorCode="" />
							<#if item.textColor?? && item.textColor?has_content>
								<#assign colorCode=item.textColor />
							</#if>
						   <#assign link="" />
						   <#assign target="_blank" />
					       <#assign rel="noopener noreferrer" />
						   <#if item.navigationPath?? && item.navigationPath?has_content>
						   	   <#assign link=item.navigationPath?html />
							   <#if item.navigationPath?starts_with("/")>
			          				<#assign target="_self" />
			          				<#assign rel="" />
			          				<#assign link=item.navigationPath/>
								</#if>
							</#if>	
							<div class="koh-banner-slide v-koh-simple m-koh-linked">
								<div class="koh-banner-background">
									<#--
                                      <img src="<@hst.link hippobean=item.image />" alt="${item.title?html}"/>										
                                      -->
                                       <#if item.title?? && item.title?has_content>
                                         <#assign imageAlt=item.title/>
                                       </#if>
                                       <#if item.imageAlt?? && item.imageAlt?has_content>
                                         <#assign imageAlt=item.imageAlt/>
                                       </#if>
                                      
                                      <#if link?? && link?has_content>
	                                      <a href="${link}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>
									      	<img src= "<#if item.imagePath?? && item.imagePath?has_content>${item.imagePath}</#if>"  height="232" <#if imageAlt?? && imageAlt?has_content>alt="${imageAlt}"</#if>  <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if>>					
										  </a> 
									  <#else>
									  	  <img src= "<#if item.imagePath?? && item.imagePath?has_content>${item.imagePath}</#if>"  height="232" <#if imageAlt?? && imageAlt?has_content>alt="${imageAlt}"</#if> <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if>>
									  </#if>	  	  
								</div>
								<#if ((item.title?? || item.description??) && (item.title?has_content || item.description?has_content))>
									<#if link?? && link?has_content>
										<a <#if link?? && link?has_content>href="${link}" target="${target}" <#if rel?has_content>rel="${rel}"</#if></#if>>
											<div class="koh-banner-content koh-valignment-middle">
											    <span class="koh-banner-text" style="<#if colorCode?has_content>color:${colorCode}</#if>"> 
													<span class="koh-banner-title" style="<#if colorCode?has_content>color:${colorCode}</#if>">${item.title?html}</span>
													<span class="koh-banner-summary" style="<#if colorCode?has_content>color:${colorCode}</#if>">${item.description}</span>
												</span>
											</div>
										</a>
									<#else>
										<div class="koh-banner-content koh-valignment-middle">
										    <span class="koh-banner-text" style="<#if colorCode?has_content>color:${colorCode}</#if>">
												<span class="koh-banner-title" style="<#if colorCode?has_content>color:${colorCode}</#if>">${item.title?html}</span>
												<span class="koh-banner-summary" style="<#if colorCode?has_content>color:${colorCode}</#if>">${item.description}</span>
											</span>
										</div>
									</#if>	
								</#if>	
							</div>
					 </#list>		
				</div>
               
				<div class="koh-thumbnail-slide-navigation">
					<#list thumbnailBanners as item>  
					     <#if item.title?? && item.title?has_content>
                             <#assign imageAlt=item.title/>
                          </#if>
                         <#if item.imageAlt?? && item.imageAlt?has_content>
                             <#assign imageAlt=item.imageAlt/>
                         </#if>
						<div class="thumb">						
						  <img src= "<#if item.imagePath?? && item.imagePath?has_content>${item.imagePath}</#if>" <#if imageAlt?? && imageAlt?has_content>alt="${imageAlt}"</#if> <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if>>					
						</div>
					</#list>		
				</div>
			</div>
		</section>
</#if>
