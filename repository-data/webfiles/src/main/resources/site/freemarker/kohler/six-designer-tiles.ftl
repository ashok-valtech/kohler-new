<#include "../include/imports.ftl">
	<#if pageable?? && pageable.items?has_content>
        <section class="v-koh-full-page container-comp container-bg background-dark">
        	<#list pageable.items as item>
	            <div class="tile">
	            	<#if item.videoUrl?? && item.videoUrl?has_content>
	            		<#assign videoUrlValue=item.videoUrl>
	            	</#if>
	                <div class="thumbnail-container">
	                	<#if item.imageLink?? && item.imageLink?has_content>
	                    	<img src="${item.imageLink}" alt="placeholder" class="bg-img"/>
                    	</#if>
	                    <div class="gradient-container"></div>
	                    <div class="video-play-button-mobile">
	                        <svg width="61" height="62" class="play-icon" data-source="${videoUrlValue}" viewBox="0 0 61 62" fill="none" xmlns="https://www.w3.org/2000/svg">
	                            <rect opacity="0.4" y="0.25293" width="61" height="61" rx="30.5" fill="white"/>
	                            <path d="M8.37268 30.8277C8.37268 18.5658 18.3129 8.62549 30.5749 8.62549C42.8368 8.62549 52.7771 18.5658 52.7771 30.8277C52.7771 43.0896 42.8368 53.0299 30.5749 53.0299C18.3129 53.0299 8.37268 43.0896 8.37268 30.8277Z" fill="white"/>
	                            <path d="M27.6669 35.7305C27.882 35.7305 28.0714 35.6537 28.307 35.5154L34.3539 32.0183C34.7942 31.7623 34.9786 31.5626 34.9786 31.2401C34.9786 30.9175 34.7942 30.7229 34.3539 30.4618L28.307 26.9647C28.0714 26.8264 27.882 26.7496 27.6669 26.7496C27.2471 26.7496 26.9501 27.0722 26.9501 27.5791V34.901C26.9501 35.413 27.2471 35.7305 27.6669 35.7305Z" fill="#1C1C1E"/>
	                        </svg>
	                    </div>
	                </div>
	                <div class="tile-info">
	                	<#if item.thumbnailUrl?? && item.thumbnailUrl?has_content>
		                    <div class="designer-profile">
		                        <img src="${item.thumbnailUrl}" alt="profile" />
		                    </div>
	                    </#if>
	                    <div class="designer-info">
	                        <div class="designer-name">
	                            <span class="first-name"><#if item.title?? && item.title?has_content>${item.title}</#if></span>
	                            <#if item.titleHeader?? && item.titleHeader?has_content>${item.titleHeader}</#if>
	                        </div>
	                        <#if item.description?? && item.description?has_content>
		                        <div class="designer-bio">
		                        	<@hst.html hippohtml=item.description/>
		                        </div>
	                        </#if>
	                    </div>
	                    <div class="video-play-button">
	                        <svg width="61" height="62" class="play-icon" data-source="${videoUrlValue}" viewBox="0 0 61 62" fill="none" xmlns="https://www.w3.org/2000/svg">
	                            <rect opacity="0.4" y="0.25293" width="61" height="61" rx="30.5" fill="white"/>
	                            <path d="M8.37268 30.8277C8.37268 18.5658 18.3129 8.62549 30.5749 8.62549C42.8368 8.62549 52.7771 18.5658 52.7771 30.8277C52.7771 43.0896 42.8368 53.0299 30.5749 53.0299C18.3129 53.0299 8.37268 43.0896 8.37268 30.8277Z" fill="white"/>
	                            <path d="M27.6669 35.7305C27.882 35.7305 28.0714 35.6537 28.307 35.5154L34.3539 32.0183C34.7942 31.7623 34.9786 31.5626 34.9786 31.2401C34.9786 30.9175 34.7942 30.7229 34.3539 30.4618L28.307 26.9647C28.0714 26.8264 27.882 26.7496 27.6669 26.7496C27.2471 26.7496 26.9501 27.0722 26.9501 27.5791V34.901C26.9501 35.413 27.2471 35.7305 27.6669 35.7305Z" fill="#1C1C1E"/>
	                        </svg>
	                    </div>
	                </div>
	                <#if item.description?? && item.description?has_content>
		                <div class="designer-bio-mobile">
		                    <@hst.html hippohtml=item.description/>
		                </div>
	                </#if>
	            </div>
            </#list>
        </#if>
        <div id="koh-preview-from-remodal" class="koh-preview-from-remodal video-modal"></div>
    </section>

<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/c-koh-six-design-campaign.min.js"></script>
</@hst.headContribution>