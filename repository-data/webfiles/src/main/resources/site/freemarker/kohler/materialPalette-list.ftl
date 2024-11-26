<#include "../include/imports.ftl">
<@fmt.message key="materialColorPaletteMetaKeyword" var="materialColorPaletteMetaKeyword"/>

<@hst.include ref="container"/>

    <section class="c-koh-material-color-palette">
        <div class="koh-meterial-container">
        <#if BeanMap??>
         <#list BeanMap?keys as key>
            <div class="koh-meterial-category col-xs-12">
            	<@fmt.message key="colorpalette-${key?html}" var="keyLabel"/>
                <header>
                    <h2 class="koh-material-title">${keyLabel?html} <span class="icon" data-icon=""></span></h2>
                </header>
                <#list BeanMap[key] as colorPallet>
                   <#if colorPallet.title?? && colorPallet.title?has_content>
	                    <#assign title = colorPallet.title?replace("(R)","<sup>&reg;</sup>")/>
						<#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
					    <#assign imgAlt = colorPallet.title?replace("(R)","&reg;")/>
		                <#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
	              </#if>
				  <#if colorPallet.imageAlt?? && colorPallet.imageAlt?has_content>
				        <#assign imgAlt=colorPallet.imageAlt/>
				        <#assign imgAlt = imgAlt?replace("(R)","&reg;")/>
				        <#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
			      </#if>
	                <@hst.link var="colorPalletLink" hippobean=colorPallet/>
	                  <@hst.manageContent hippobean=colorPallet/>
		                 <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 koh-meterial-category-items  images-next-to-one">
		                     <a href="${colorPalletLink?html}" class="koh-meterial-category-items-inner">
		                        <img src="${colorPallet.image?html}" alt="${imgAlt}" <#if colorPallet.imageTitle?? && colorPallet.imageTitle?has_content> title="${colorPallet.imageTitle}"</#if> />
		                        <h6>${title}</h6>
		                        <#if colorPallet.description?? && colorPallet.description?has_content>
		                          <#assign description = colorPallet.description?replace("(R)","<sup>&reg;</sup>")/>
					              <#assign description = description?replace("(TM)","<sup>&trade;</sup>")/>
		                            <p class="koh-meterial-category-items-description">${description}</p>
		                        </#if>
		                    </a>
		                </div>
                </#list>
            </div>
       </#list>
   </#if>
        </div>
    </section>
