<#include "../include/imports.ftl">
<@fmt.message key="company.name" var="companyName"/>
<@fmt.message key="about-us" var="aboutus"/>
<@fmt.message key="aboutUsMetaKeyword" var="aboutUsMetaKeyword"/>
<@fmt.message key="aboutUsMetaDescription" var="aboutUsMetaDescription"/>
    <section class="c-koh-pro-toolbox">
      <div class="koh-pro-toolbox-container">
         <div class="koh-pro-toolbox-banner">
              <@hst.include ref="container"/>
        </div>
        <header>
            <@fmt.message key="learn-more" var="learnmore"/>
           <@fmt.message key="back-to-top" var="backTotop"/>
            <h1 class="koh-pro-toolbox-title">${aboutus}</h1>
        </header>
        <#if pageable?? && pageable.items?has_content>
            <ul class="koh-pro-toolbox-listing">
                <#assign counter=1/>
        		<#list pageable.items as item>
        			<#if item??>
        			<#if item.title?? && item.title?has_content>
        			 <#assign alt=item.title />
        			</#if>
        			<#if item.imageAlt?? && item.imageAlt?has_content>
        			 <#assign alt=item.imageAlt />
        			</#if>
			             <li class="koh-pro-toolbox-section col-xs-12 <#if counter%2==0>koh-pro-toolbox-section-even</#if>">
                             <#if item.imagepath?? && item.imagepath?has_content>
                             <img src= "${item.imagepath}"  width="300" height="232" alt="${alt?html}" <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if>>
                             </#if>
		                    <h2 class="koh-pro-toolbox-text-title">
		                    	<#if item.title?? && item.title?has_content>${item.title?html}</#if>
		                    </h2>
		                    <div class="koh-pro-toolbox-text-body">
		                     <#if item.description?? && item.description?has_content><@hst.html hippohtml=item.description/></#if>
		                    </div>
		                    <#assign target="_blank" />
	              			<#if item.link?? && item.link?has_content>
	              				<#if item.link?starts_with("/")>
	              					<#assign target="_self" />
									<#assign link=item.link/>
									<a class="koh-pro-toolbox-text-link" href="${link}" target="${target}">${learnmore}</a>
		                    	<#else>
								  <a class="koh-pro-toolbox-text-link" href="${item.link?html}" target="${target}" rel="noopener noreferrer">${learnmore}</a>
		                        </#if>
							<#elseif item.relatedDocument?? &&  item.relatedDocument?has_content>
			                       <@hst.link var="aboutusDetails" siteMapItemRefId="aboutus-details" />
	                               <a class="koh-pro-toolbox-text-link"  href="${aboutusDetails}?id=${item.relatedDocument.getCanonicalUUID()}">${learnmore}</a>
		                    </#if>
		                 </li>
		                 <#assign counter=counter+1/>
		            </#if>    
                </#list>
            </ul>
        </#if>
      </div>
      <div class="koh-back-top">
        <button style="display: none;">
            <span class="icon" data-icon=""></span>${backTotop}
        </button>
    </div>
    </section>

<@hst.headContribution category="title">
	<title>${aboutus} | ${companyName}</title>
</@hst.headContribution>

<@hst.headContribution category="meta-tags">
    <meta name="description" content="${aboutUsMetaDescription}"/>
</@hst.headContribution>


