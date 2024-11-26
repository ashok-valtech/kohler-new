<#include "../include/imports.ftl">
<@fmt.message key="learn-more" var="learnmore"/>
<@fmt.message key="back-to-top" var="backTotop"/>
<@fmt.message key="company.name" var="companyName"/>
<@fmt.message key="protoolboxMetaDesc" var="protoolboxMetaDesc"/>

    <section class="c-koh-pro-toolbox">
      <div class="koh-pro-toolbox-container">
         <div class="koh-pro-toolbox-banner">
              <@hst.include ref="container"/>
        </div>
        <header>
            <@fmt.message key="pro-toolbox" var="protoolbox"/>
            <h2 class="koh-pro-toolbox-title">${protoolbox}</h2>
        </header>
        <#if pageable?? && pageable.items?has_content>
            <ul class="koh-pro-toolbox-listing">
                <#assign counter=1/>
        		<#list pageable.items as item>
        			<#if item??>
			             <li class="koh-pro-toolbox-section col-xs-12 <#if counter%2==0>koh-pro-toolbox-section-even</#if>">
                             <#if item.imagepath?? && item.imagepath?has_content>
                             <img src= "${item.imagepath}"  width="300" height="232" alt="${item.title?html}">
                             </#if>
		                    <h3 class="koh-pro-toolbox-text-title">
		                    	<#if item.title?? && item.title?has_content>${item.title?html}</#if>
		                    </h3>
		                    <div class="koh-pro-toolbox-text-body">
		                     <#if item.description?? && item.description?has_content><@hst.html hippohtml=item.description/></#if>
		                    </div>
		                    <#assign target="_blank" />
		                    <#assign rel="noopener noreferrer" />
	              			<#if item.link?? && item.link?has_content && item.link?starts_with("/")>
	              				<#assign target="_self" />
	              				<#assign rel="" />
	  						</#if>
		                    <a class="koh-pro-toolbox-text-link" href="<#if item.link?? && item.link?has_content>${item.link?html}</#if>" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>${learnmore}</a>
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
	<title>${protoolbox} | ${companyName}</title>
</@hst.headContribution>

<@hst.headContribution category="meta-tags">
	<meta name="description" content="${protoolboxMetaDesc}"/>
</@hst.headContribution>

