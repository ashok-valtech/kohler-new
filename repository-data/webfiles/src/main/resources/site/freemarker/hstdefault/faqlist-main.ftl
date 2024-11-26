<#include "../include/imports.ftl">
<@hst.setBundle basename="apac.labels"/>
<@fmt.message key="select-a-category" var="selectCategory"/>
<@fmt.message key="back-to-top" var="backTotop"/>
<@fmt.message key="faqMetaDesc" var="faqMetaDesc"/>

<#if document??>

<#else>
	<#if items?? &&items?has_content>
		<#list items as item>
			<#if document??>
			
			<#else>
				<#assign document = item />
			</#if>	
		</#list>
	<#else>
		<h1>Empty </h1>
	</#if>	
</#if>

<section class="c-koh-simple-content v-koh-full-page">
	<h2 class="koh-simple-content-title"><@hst.html hippohtml=document.description/></h2>
</section>
<section class="c-koh-tabbed-faqs">
	<div class="koh-tab-controls">
		<button class="koh-tab-select">
          <#if document?? && document?has_content>${document.title}<#else>${selectCategory}</#if>		
        </button>
		<ul class="koh-tab-set">
			<#list items as item>
				<@hst.link var="link" hippobean=item />
				<a href="${link}"><li <#if item.canonicalHandleUUID == document.canonicalHandleUUID>class="open"</#if> data-target-id="tab00">${item.title?html}</li></a>
			</#list>	
		</ul>
	</div>
	
	<div class="koh-tab-content">
		<div class="koh-tab-contents open">
			<div class="koh-tab-content-header">
				<ul class="koh-faq-questions">
					<#list document.faqItems as faq>
						<li><a href="#faq0${faq?index}" <#if faq?index == 0>id="fqqc${faq?index}"</#if>>${faq.question?html}</a></li>
					</#list>
				</ul>
			</div>
			<div class="koh-tab-content-body">
				 <#list  document.faqItems as faq1>
					<div class="koh-faq" id="faq0${faq1?index}">
						<div class="koh-faq-question"></div>
						<div class="koh-faq-answer"><@hst.html hippohtml=faq1.answer/></div>
					</div>
				</#list>
			</div>
		</div>
        <div id="back-to-top">
	      <button class="btn--square btn--gray">
	      <i class="icon--chevron-up"></i>
	      <span>${backTotop}</span>
	      </button>
	    </div>
</section>	

<@fmt.message key="company.name" var="companyName"/>	
<@fmt.message key="faq" var="faq"/>			
<@hst.headContribution>
		<title>${faq} | ${companyName}</title>
</@hst.headContribution>	
<@hst.headContribution category="ext-scripts">
	<script>
		jQuery(document).ready(function($) {
			var previousHash = window.location.hash;
			var kohFAQScroll = function() { 
				scrollBy(0, -80);
			};
			if (window.location.hash) kohFAQScroll();
			window.addEventListener("hashchange", kohFAQScroll);
			
			$('#fqqc0').click(function(){
				var hash = window.location.hash;
				if(hash === '' || hash === '#faq00'){
					window.location.hash='';
				}
			});
		});
	</script>
</@hst.headContribution>

<@hst.headContribution category="meta-tags">
	<meta name="keywords" content="${faq}"/>
</@hst.headContribution>
<@hst.headContribution category="meta-tags">
	<meta name="description" content="${faqMetaDesc}"/>
</@hst.headContribution>

<@hst.webfile path="/" var="jsLink" />
<@hst.headContribution category="ext-scripts">
  <script src="${jsLink}/js/c-koh-tabbed-faqs.min.js" type="text/javascript"></script>
</@hst.headContribution>
