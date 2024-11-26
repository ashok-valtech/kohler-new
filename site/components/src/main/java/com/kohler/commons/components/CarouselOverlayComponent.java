/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.EssentialsListComponent;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.beans.CarouselOverlayCompoundDocument;
import com.kohler.beans.CarouselOverlayDocument;
import com.kohler.beans.Product;
import com.kohler.commons.beans.CarouselOverlayCompoundView;
import com.kohler.commons.beans.CarouselOverlayView;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.service.ProductDetailService;

/**
 * APAC Implementation for Hot Spot , inherits from <code>CommonComponent</code>
 * @author krushna.mahunta
 * Date: 07/14/2017
 * @version 1.0
 */
@ParametersInfo(type = EssentialsListComponentInfo.class)
public class CarouselOverlayComponent extends EssentialsListComponent {
	
	private static final Logger LOG = LoggerFactory.getLogger(CarouselOverlayComponent.class);
	
	ProductDetailService productDetailService = new ProductDetailService();
	
	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {
		List<CarouselOverlayDocument> carouselOverlayDocuments = getCarouselOverlayDocuments(request);
		List<CarouselOverlayView> carouselOverlayViews = new ArrayList<CarouselOverlayView>();
		for(CarouselOverlayDocument carouselOverlayDocument : carouselOverlayDocuments){
			CarouselOverlayView carouselOverlayView = new CarouselOverlayView();
			carouselOverlayView.setTitle(carouselOverlayDocument.getTitle());
			carouselOverlayView.setDescription(carouselOverlayDocument.getDescription());
			carouselOverlayView.setImageURL(carouselOverlayDocument.getImage());
			carouselOverlayView.setLink(carouselOverlayDocument.getLink());
			carouselOverlayView.setLabel(carouselOverlayDocument.getLabel());
			carouselOverlayView.setImageAlt(carouselOverlayDocument.getImageAlt());
			carouselOverlayView.setImageTitle(carouselOverlayDocument.getImageTitle());
			List<CarouselOverlayCompoundDocument> carouselOverlayCompoundDocuments = carouselOverlayDocument.getKohler_carouseloverlaycompounddocument();
			List<CarouselOverlayCompoundView> carouselOverlayCompoundViews = new ArrayList<CarouselOverlayCompoundView>();
			for(CarouselOverlayCompoundDocument carouselOverlayCompoundDocument:carouselOverlayCompoundDocuments){
				CarouselOverlayCompoundView carouselOverlayCompoundView = new CarouselOverlayCompoundView();
				carouselOverlayCompoundView.setItemNo(carouselOverlayCompoundDocument.getItemNo());
				carouselOverlayCompoundView.setSku(carouselOverlayCompoundDocument.getSku());
				carouselOverlayCompoundView.setXcord(carouselOverlayCompoundDocument.getXCoordinate());
				carouselOverlayCompoundView.setYcord(carouselOverlayCompoundDocument.getYCoordinate());
				if(!Strings.isNullOrEmpty(carouselOverlayCompoundDocument.getItemNo())){
					Product product = productDetailService.getProductByItemNo(request, carouselOverlayCompoundDocument.getItemNo());
					if(product!=null){
					carouselOverlayCompoundView.setDescription(product.getDescriptionProduct());
					String IMG_ITEM_ISO[] = null;
					if(!Strings.isNullOrEmpty(carouselOverlayCompoundDocument.getItemNo())){
						IMG_ITEM_ISO = productDetailService.findImageItemIsoForSKU(product, carouselOverlayCompoundDocument.getSku());
					}
					if(IMG_ITEM_ISO != null && IMG_ITEM_ISO.length > 0){
						carouselOverlayCompoundView.setIMG_ISO(IMG_ITEM_ISO[0]);
					}else{
						Map<String, String[]> productAttributes = product.getAttributes().stream()
								.collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
						String IMG_ISO[] = productAttributes.get("IMG_ISO");
						if(IMG_ISO.length >= 0){
							carouselOverlayCompoundView.setIMG_ISO(IMG_ISO[0]);
						}
					}
					carouselOverlayCompoundViews.add(carouselOverlayCompoundView);
				  }
				}
			}
			carouselOverlayView.setCarouselOverlayCompoundViews(carouselOverlayCompoundViews);
			carouselOverlayViews.add(carouselOverlayView);
		}
		request.setAttribute(Constants.REQUEST_ATTR_DOCUMENT, carouselOverlayViews);
	}
	
	/**
	 * Gets list of items from CarouselOverlayDocument bean
	 * @param request
	 * @return CarouselOverlayDocument 
	 * @throws IllegalStateException
	 * @throws QueryException
	 */
	public List<CarouselOverlayDocument> getCarouselOverlayDocuments(HstRequest request){
		final EssentialsListComponentInfo paramInfo = getComponentParametersInfo(request);
		final String path = getScopePath(paramInfo);
		final HippoBean rootBean = getSearchScope(request, path);
		final String sortField = paramInfo.getSortField();
		final String sortOrder = paramInfo.getSortOrder();
		HstQueryResult queryResult = null;
		List<CarouselOverlayDocument> carouselOverlayDocuments = new ArrayList<CarouselOverlayDocument>();
		try {
			@SuppressWarnings("unchecked")
			HstQuery query = request.getRequestContext().getQueryManager().createQuery(rootBean, CarouselOverlayDocument.class);
			if (!Strings.isNullOrEmpty(sortField) && !Strings.isNullOrEmpty(sortOrder)) {
				if (sortOrder.equalsIgnoreCase(Constants.SORT_DIRECTION_ASC)) {
					query.addOrderByAscending(sortField);
				}else {
					query.addOrderByDescending(sortField);
				}
			}
			queryResult = query.execute();
			HippoBeanIterator iterator = queryResult.getHippoBeans();
			while(iterator.hasNext()){
				carouselOverlayDocuments.add((CarouselOverlayDocument) iterator.next());
			}
		} catch (IllegalStateException | QueryException e) {
			LOG.error(e.getMessage());
			e.printStackTrace ();
		}
		return carouselOverlayDocuments;
	}	
}
