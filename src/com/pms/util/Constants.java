package com.pms.util;

public class Constants {
	

	/**
	 * GC = Google Chrome  
	 * FF = FireFox
	 * IE = Internet Explorer
	 * 
	 */	
	public static final String BROWSER = "FF";
	public static final String DEV_ENVIRONMENT_LC = "https://devstore.epicommercestore.com/storelocator";
	public static final String USERDIR = "C:/Users/Lenovo/Downloads";
	/**
	 * Add product before sign in
	 */
	
	public static final String XPATH_ITEM_STATUS = "//div[@class='page messages']//div[@class='messages']//div//div";
	
	/**
	 * Registration Welcome message
	 */
	
	public static final String XPATH_REGISTRATION_WELCOME_TEXT = "//main[@id='maincontent']//div[@class='page messages']//div[@class='messages']//div//div";
	
	//main[@id='maincontent']//div[@class='page messages']//div[@class='messages']//div//div[text()='Thank you for registering with Boca Liqour .']
	
	public static final String XPATH_ENTER_DELIVERY_ADD = "//input[@id='addressInput' and @placeholder = 'Enter your delivery address']";
	
	public static final String XPATH_BTN_CONTINUE_SPLASH_SCRN = "//button[text()='Continue']";
	
	public static final String XPATH_BOCA_LIQ_STORE = "//a[text()='Sharons Liquors']";
	
	public static final String XPATH_ACCOUNT_LINK = "//li[@class='link my-account-link']//a[1]";
	
	public static final String XPATH_NEWSLETTER_TEXT = "//div[@class='box box-newsletter']//STRONG[@class='box-title']//span[text()='Newsletters']";
	
	public static final String XPATH_NEWSLETTERBOX_CONTENT = "//div[@class='box box-newsletter']//div[@class='box-content']//p";
	
	public static final String XPATH_NEWSLETTERBOX_EDIT_BTN = "//div[@class='box box-newsletter']//div[@class='box-actions']//span[text()='Edit']";
	
	public static final String XPATH_NEWSLETTER_SUBSCRIPTION_TITLE = "//span[@class='base' and text()='Newsletter Subscription']";
	
	public static final String ID_NEWSLETTER_CHECKBOX_SUBSCRIPTION = "subscription";
	
	public static final String XPATH_NEWSLETTER_SAVE_BTN = "//button[@class='action save primary' and @title ='Save']";
	
	public static final String XPATH_NEWSLETTER_SUBSCRIBED_SUCCESS_MESSAGE = "//div[@class='page messages']//div[@class='messages']//div[@class='message-success success message']//div[text()='We have saved your subscription.']";
	
	public static final String XPATH_NEWSLETTER_UNSUBSCRIBED_SUCCESS_MESSAGE = "//div[@class='page messages']//div[@class='messages']//div[@class='message-success success message']//div[text()='We have removed your newsletter subscription.']";
	
	/*
	 * HomePage Locators
	 */
	
	public static final String XPATH_LOGO = "//a[@class='logo']//img[@title='LiquorCart']";
	
	public static final String XPATH_STORENAME = "//div[@class='merchant-nameAddress']//h2";
	
	//div[@class="merchant-nameAddress"]//h2[text()='Boca Liquors']
	
	public static final String XPATH_SELECT_A_STORE = "//div[@class='merchant-nameAddress']//a";
	
	public static final String XPATH_TITLE_SELECT_A_STORE_PAGE_= "//div[@class='page-title-wrapper']//span";
	
	public static final String XPATH_STOREADDRESS = "//div[@class='merchant-nameAddress']//p";
	
	//div[@class='merchant-nameAddress']//p[text()='Boca Liquors, Yamato Road, Boca Raton, FL']

	
	public static final String XPATH_THEME_IMAGE = "//p//img";
	
	public static final String XPATH_SHOPPING_CART_ICON = "//div[@class='minicart-wrapper']//a[@class='action showcart']";
	
	//a[@class="action showcart"]
	
	public static final String XPATH_MERCHANT_PHNE = "//div[@class='merchant-phoneHours']//h2";
	
	public static final String XPATH_MERCHANT_HRS = "//div[@class='merchant-phoneHours']//p";
	
	public static final String XPATH_SIGNOU_BTN ="//li[@class='link authorization-link']//a[text()='Sign Out']";
	
	public static final String XPATH_PRODUCT_CATEGORY_LIST = "//nav[@class=\"navigation\"]//ul//li//a//span/following-sibling::span";
	
	public static final String ID_SEARCH_BAR ="search";
	
	public static final String XPATH_LIST_OF_STORE_SELECT_A_STORE_PAGE = "//div[@class='amlocator-stores-wrapper']//div[@class='amlocator-store-desc']//a";	
	
	/**
	 * My Account page
	 */
	
	public static final String XPATH_EDIT_BUTTON_ACCOUNTINFORMATION = "//div[@class='box box-information']//a[@class='action edit']//span";
	
	public static final String XPATH_TITLE_EDIT_ACCINFORMATION ="//div[@class='columns']//span[@class='base']";
	
	public static final String XPATH_CONTACTINFO_ACCINFORMATION = "//div[@class='box box-information']//div[@class='box-content']//p";
	
	public static final String XPATH_ADDRESS_ADDRESSBOOK = "//div[@class='box box-billing-address']//div[@class='box-content']//address";
	
	public static final String XPATH_CHANGE_PASSWORD_MYACC_PAGE = "//div[@class='box-actions']//a[@class='action change-password']";
	
	public static final String XPATH_CHANGE_PASSWORD_CURRENT_PWD_MYACC_PAGE = "//div[@class='control']//input[@id='current-password']";
	
	public static final String XPATH_CHANGE_PASSWORD_NEW_PWD_MYACC_PAGE = "//div[@class='control']//input[@id='password']";
	
	public static final String XPATH_CHANGE_PASSWORD_CONFIRM_PWD_MYACC_PAGE = "//div[@class='control']//input[@id='password-confirmation']";
	
	public static final String XPATH_CHANGE_PASSWORD_SAVE_BTN_MYACC_PAGE = "//div[@class='actions-toolbar']//div//button//span";
	
	
	
	public static final String XPATH_ADDRESSBOOK_TAB_MYACC_PAGE = "//div[@id='block-collapsible-nav']//li//a[text()='Address Book']";
	
	public static final String XPATH_ACCOUNTINFORMATION_TAB_MYACC_PAGE = "//div[@id='block-collapsible-nav']//li//a[text()='Account Information']";
	
	public static final String XPATH_MYORDERS_TAB_MYACC_PAGE ="//div[@class='columns']//div[@id='block-collapsible-nav']//li//a[text()='My Orders']";
	
	/*
	 *  Account Information Page
	 */
	
	public static final String XPATH_ACCOUNT_INFO_FIRSTNAME_ACCOUNTINFO_PAGE ="//input[@id='firstname' and @value]";
	
	public static final String ID_ACCOUNT_INFO_FIRSTNAME_ACCOUNTINFO_PAGE ="firstname";
	
	public static final String ID_ACCOUNT_INFO_LASTNAME_ACCOUNTINFO_PAGE ="lastname";
	
	public static final String XPATH_ACCOUNT_INFO_LASTNAME_ACCOUNTINFO_PAGE ="//input[@id='lastname']";
	
	
	/**
	 *  Address BOOK page
	 */
	
	public static final String ID_ADD_NEW_ADDRESS_PHNENUMBR_ADDRESSBOOK = "telephone";
	public static final String ID_ADD_NEW_ADDRESS_STREETADD_1_ADDRESSBOOK = "street_1";
	public static final String ID_ADD_NEW_ADDRESS_STREETADD_2_ADDRESSBOOK = "street_2";
	public static final String ID_ADD_NEW_ADDRESS_COUNTRY_DROPDOWN_ADDRESSBOOK ="country";
	public static final String ID_ADD_NEW_ADDRESS_STATE_DROPDOWN_ADDRESSBOOK ="region_id";
	public static final String ID_ADD_NEW_ADDRESS_STATE_FIELD_ADDRESSBOOK = "region";
	public static final String ID_ADD_NEW_ADDRESS_CITY_FIELD_ADDRESSBOOK = "city";
	public static final String ID_ADD_NEW_ADDRESS_ZIP_FIELD_ADDRESSBOOK = "zip";
	public static final String CLASS_ADD_NEW_ADDRESS_SAVE_ADDRESS_BTN_ADDRESSBOOK = "action save primary";
	/*
	 * My Orders Page
	 */
	
	public static final String XPATH_MYORDERS_STATUS_MYORDERS_PAGE = "//div[@class='column main']//div[@class='message info empty']//span[text()='You have placed no orders.']";
	
	public static final String XPATH_MYORDER_HEADER_MYORDERS_PAGE = "//table[@id='my-orders-table']//thead//tr//th";
	
	public static final String XPATH_MYORDER_ORDER_NUMBERS_LIST_MYORDER_PAGE = "//div[@class='table-wrapper orders-history']//tbody//tr//td[@class='col id']";
	
	/*
	 * Accounts Module
	 */
	public static final String XPATH_SUCCESS_MSZ_ADDRESS_SAVED_ADDRESSBOOK = "//div[@class='page messages']//div[@class='messages']//div//div";
	
	private Constants()
	{

	}

}
