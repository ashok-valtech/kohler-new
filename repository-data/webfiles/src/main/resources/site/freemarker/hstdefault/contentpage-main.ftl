<#include "../include/imports.ftl">
<@hst.setBundle basename="apac.labels"/>
<@fmt.message key="company.name" var="companyName"/>
<@fmt.message key="company.region" var="companyRegion"/>
<#if document??>
	<section class="c-koh-simple-content v-koh-full-page">
	  	<div class="koh-simple-content-container">
	  		<@hst.manageContent hippobean=document/>
	  		<#if document.title?? && document.title?has_content>
			<h2 class="koh-simple-content-title" style="">${document.title?html}</h2>
			</#if>
		    <div class="koh-simple-content-body">
		        <#if document.introduction?? && document.introduction?has_content>
		           <#assign introduction = document.introduction?replace("(R)","<sup>&reg;</sup>")/>
				   <#assign introduction = introduction?replace("(TM)","<sup>&trade;</sup>")/>
	      			<p>
	      				<strong>
	        				${introduction}
	        			</strong>
	     			</p>
    			</#if>
		        <@hst.html hippohtml=document.content/>
			</div>
		</div>
	</section>
</#if>

<#assign pageTitle=companyRegion + " | " +  companyName />
<#if document.title?? && document.title?has_content>
	<#assign pageTitle=document.title + " | " + companyRegion />
<#else>
	<#assign pageTitle=companyRegion + " | " + companyName />
</#if>

<@hst.headContribution  category="ext-scripts">
  <script type="text/javascript">
  			var strPageTitle = '${pageTitle?html}';
  			var indPipeSymbol = strPageTitle.indexOf(' |');

  			if(indPipeSymbol >0) {
  				strPageTitle = strPageTitle.substr(0, indPipeSymbol);
  				strPageTitle = strPageTitle.toLowerCase();
  				strPageTitle = strPageTitle.replace(' ', '-');
  				strPageTitle = 'koh-body-' + strPageTitle;
  			}
  			if(document.title === "" ){
  				document.title = '${pageTitle?html}';
  				document.body.className += strPageTitle;
  			}
  </script>
</@hst.headContribution>

<@hst.headContribution category="meta-tags">
	<meta name="keywords" content="${document.title?html}"/>
</@hst.headContribution>

<@hst.headContribution category="ext-scripts">
	<script>
		jQuery(document).ready(function($) {
			var previousHash = window.location.hash;
			var kohFAQScroll = function() {
				scrollBy(0, -80);
			};
			if (window.location.hash) {
				kohFAQScroll();
			}
			window.addEventListener("hashchange", kohFAQScroll);

			$('.hashnav').click(function(){
				window.location.hash='';
			});
		});
	</script>
</@hst.headContribution>
