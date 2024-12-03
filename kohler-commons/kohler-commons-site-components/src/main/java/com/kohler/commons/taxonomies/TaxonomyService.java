/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.taxonomies;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.LocaleUtils;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;
import org.hippoecm.hst.site.HstServices;
import org.onehippo.taxonomy.api.Category;
import org.onehippo.taxonomy.api.CategoryInfo;
import org.onehippo.taxonomy.api.Taxonomy;
import org.onehippo.taxonomy.api.TaxonomyManager;

public class TaxonomyService {
	public TaxonomyManager getTaxonomyManager() {
		return HstServices.getComponentManager ().getComponent (TaxonomyManager.class.getName ());
	}

	public Taxonomy getTaxonomyByname(String taxonomyRootName) {
		return getTaxonomyManager ().getTaxonomies ().getTaxonomy (taxonomyRootName);
	}

	public Map<String, String> getFirstLevelCategory(Taxonomy rootTaxonomy) {
		Map<String, String> firstLvlCatMap = new HashMap<String, String> ();
		List<? extends Category> lst = rootTaxonomy.getCategories ();
		for (Category cat : lst) {
			firstLvlCatMap.put (cat.getName (), cat.getKey ());
		}
		return firstLvlCatMap;
	}

	/**
	 * @return List of parent Categories
	 *
	 *         method returns List of parent Categories
	 */
	public List<? extends Category> getParentCategories() {
		return getTaxonomyByname ("apac").getCategories ();

	}

	/*public List<? extends Category> getGlobalProjectCategoryFromKey(String categoryKey) {
		return  getTaxonomyByname ("apac-countries").getCategories();

	}*/


	/**
	 * @return String
	 *
	 *         method returns the category key having synonym value
	 */

	public String getCategoryKeyBySynonym(String synonymValue, String language) {
		List<? extends Category> allCategories = getParentCategories ();
		return recursiveGetCategoryValue (synonymValue, language, allCategories);
	}



	/**
	 * @return String
	 *
	 *         method returns the category key having synonym value using
	 *         recursive search
	 */
	public String recursiveGetCategoryValue(String synonymValue, String language,
			List<? extends Category> allCategories) {
		for (Category category : allCategories) {
			if (category.getChildren ().size () > 0) {
				String categoryKey = recursiveGetCategoryValue (synonymValue, language, category.getChildren ());
				if (!categoryKey.isEmpty ())
					return categoryKey;
			}
			Locale locale =  LocaleUtils.toLocale(language);
			CategoryInfo info = category.getInfo (locale);
			if (Arrays.asList (info.getSynonyms ()).contains (synonymValue)) {
				return category.getKey ();
			}
		}
		return "";
	}



	/**
	 * @return Map
	 *
	 *         method returns List of All Categories with synonyms
	 */
	public Map<String, String> getAllCategorys(String language) {
		Map<String, String> categorys = new HashMap<String, String> ();
		categorys = getAllChildCategorys (language, categorys, getParentCategories ());
		return categorys;
	}



	/**
	 * @return Map
	 *
	 *         method returns List of child Categories with synonyms as key and
	 *         cateegory key as value
	 */
	private Map<String, String> getAllChildCategorys(String language, Map<String, String> allCategories,
			List<? extends Category> childCategorys) {
		for (Category category : childCategorys) {
			if (category.getChildren ().size () > 0)
				getAllChildCategorys (language, allCategories, category.getChildren ());
			String ancestorId = getCategorySynonym (category, language);
			if (ancestorId != null)
				allCategories.put (ancestorId, category.getKey ());
		}
		return allCategories;
	}



	/**
	 * @return String
	 *
	 *         method returns Category Synonym
	 */
	private String getCategorySynonym(Category category, String language) {
		Locale locale =  LocaleUtils.toLocale(language);
		CategoryInfo info = category.getInfo (locale);
		if (info.getSynonyms ().length > 0)
			return Arrays.asList (info.getSynonyms ()).get (0);
		else
			return null;
	}

	/**
	 * @param category
	 * @return
	 *
	 * 		method returns Category
	 */
	public Category getCategoryFromKey(String taxonomyName, String categoryKey) {
		Category category = getTaxonomyByname(taxonomyName).getCategoryByKey(categoryKey);
		return category;
	}

	public Category getGlobalProjectCategoryFromKey(String categoryKey) {
		Category category = getTaxonomyByname("apac-countries").getCategoryByKey(categoryKey);
		return category;
	}


	public Map<String, String> getGlobalProjectFirstLevelCategory(String language) {
		Taxonomy rootTaxonomy = getTaxonomyByname("apac-countries");
		Map<String, String> firstLvlCatMap = new LinkedHashMap<String, String> ();
		List<? extends Category> lst = rootTaxonomy.getCategories ();
		for (Category category : lst) {
			Locale locale =  LocaleUtils.toLocale(language);
			CategoryInfo categoryInfo = category.getInfo(locale);
			firstLvlCatMap.put (category.getKey (), categoryInfo.getName ());
		}
		return firstLvlCatMap;
	}
	/**
	 * @param category
	 *            : Category type :any Category
	 * @return listIterator for caterogy
	 *
	 *
	 *         internally calls getCategoryChildren() method to get list of
	 *         children and return list iterator for the same
	 */
	public ListIterator<? extends Category> getListIterator(Category category) {
		List<? extends Category> categoryList = getCategoryChildren(category);
		ListIterator<? extends Category> categoryListItr = categoryList.listIterator();
		return categoryListItr;
	}

	/**
	 * @param taxonomyCategory
	 * @return children of category as a list
	 */
	private List<? extends Category> getCategoryChildren(Category category) {
		return category.getChildren();
	}

	/**
	 * @param category
	 * @return translated name of category as per locale
	 */
	public String getCategorytranslatedName(Category category, String language) {
		Locale locale =  LocaleUtils.toLocale(language);
		CategoryInfo catinfo = category.getInfo(locale);
		return catinfo.getName();
	}

	public Map<String,String> getCategorieswithKeys()
	{
		Map<String, String> getAllCategories = new HashMap<String, String> ();
		Map<String, String> getCategoriesKeys = new HashMap<String, String> ();
		List<? extends Category> allCategories = getParentCategories ();
		getAllCategories=getChildCategorys("en",  allCategories,getCategoriesKeys);
		return getAllCategories;
	}


	private Map<String, String> getChildCategorys(String language, List<? extends Category> childCategorys,Map<String, String> getCategoriesKeys) {
		for (Category category : childCategorys) {
			getCategoriesKeys.put(category.getKey(), category.getName());
			if (category.getChildren ().size () > 0)
				getChildCategorys (language, category.getChildren (),getCategoriesKeys);
		      }
		return getCategoriesKeys;
	}

	public void addFilterOrder (Category category, Map<String, HippoFolderBean> hippoFolderBeanMap) {
		if ((category != null) && (category.getChildren() != null) && (!category.getChildren().isEmpty())) {
			for (Category categoryChild :category.getChildren()) {
				hippoFolderBeanMap.put(categoryChild.getKey().replaceAll(category.getKey() +"-", ""), null);
			}
		}
	}
	public void addAllFilterOrder (Category category, Map<String, HippoFolderBean> hippoFolderBeanMap) {
		if ((category != null) && (category.getChildren() != null) && (!category.getChildren().isEmpty())) {
			for (Category categoryChild :category.getChildren()) {
				hippoFolderBeanMap.put(categoryChild.getKey(), null);
			}
		}
	}
}
