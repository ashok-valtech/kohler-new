<#include "../include/imports.ftl">
<@fmt.message key="store-file-extn" var="storefileextn"/>
<@fmt.message key="store-file-size" var="storefilesize"/>
<@fmt.message key="api-key-error" var="apikeyerror"/>
<@fmt.message key="file-upload-error" var="fileuploaderror"/>
<@fmt.message key="file-upload-size-error" var="fileuploadsizeerror"/>

<@fmt.message key="upload-dealers" var="uploaddealers"/>
<@fmt.message key="api-key-file" var="apikey"/>
<@fmt.message key="choose-file-to-upload" var="choosefiletoupload"/>
<#assign englishSite="" />
<#if hstRequestContext.getResolvedMount().getMount().getParent()??>
	<#assign englishSite="/en" />
</#if>



<input type="hidden" autocomplete="off" id="storefileextn" value="${storefileextn}" />
<input type="hidden" autocomplete="off" id="storefilesize" value="${storefilesize}" />
<input type="hidden" autocomplete="off" id="apikeyerror" value="${apikeyerror}" />
<input type="hidden" autocomplete="off" id="fileuploaderror" value="${fileuploaderror}" />
<input type="hidden" autocomplete="off" id="fileuploadsizeerror" value="${fileuploadsizeerror}" />


<section class="c-koh-simple-content v-koh-full-page">
   <div class="koh-simple-content-container">
      <h2 class="koh-simple-content-title">${uploaddealers}</h2>
      <div class="koh-simple-content-body">
		<form id="uploadForm" class="koh-upload-dealers-from" method="post" action="${englishSite}/api/storeservice" enctype="multipart/form-data">
		  <div class="yui-skin-sam">
			  <div class="koh-form-item-dealer">
					<label for="apiKey">${apikey}</label>
					<div class="koh-from-field">
					    <p id="successmsg" class="koh-success-msg-file-up"></p>
						<input type="password" name="api-key" size="50" id="apiKey" />
						<p id="errormsg" class="koh-error-msg-file-up"></p>
						
				    </div>	
			  </div>
			  <div class="koh-form-item-dealer">
		  		<label for="storeFile">${choosefiletoupload}</label>	
		  		<div class="koh-from-field">  		
			   		<input type="file" name="file" size="50" class="fileuploadImage" id="storeFile"/>
			   		<p id="errorKeyFile" class="koh-error-msg-file-up"></p>
		   		</div>
		   	  </div>	
		   		<input type="submit" id="koh-dealerssubmit"  class="koh-dealers-submit-btn" value="Submit" />
		  </div>
		  
		</form>
		
   </div>
</section>

<@hst.headContribution category="title">
	<META NAME="ROBOTS" CONTENT="NOINDEX, NOFOLLOW"/>
</@hst.headContribution>

<@hst.webfile path="/" var="link" />	 
<@hst.headContribution category="ext-scripts">
  <script src="${link}/js/custom/koh-file-upload.min.js" type="text/javascript"></script>
</@hst.headContribution>