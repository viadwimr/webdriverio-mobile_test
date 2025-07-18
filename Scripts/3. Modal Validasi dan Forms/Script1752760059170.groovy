import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import internal.GlobalVariable as GlobalVariable

// Fungsi bantu: buat TestObject dari teks
TestObject makeText(String text) {
	TestObject to = new TestObject()
	to.addProperty("xpath", ConditionType.EQUALS, "//*[@text='${text}']")
	return to
}

// Fungsi bantu: buat TestObject dari XPath
TestObject makeXpath(String xpath) {
	TestObject to = new TestObject()
	to.addProperty("xpath", ConditionType.EQUALS, xpath)
	return to
}

// Mulai aplikasi
String appPath = 'C:/Users/USER/Downloads/android.wdio.native.app.v1.0.8.apk' // disesuaikan dengan letak mobile app yg telah didownload
Mobile.startApplication(appPath, false)

// === a) Klik menu Forms ===
Mobile.tap(makeText('Forms'), 1)


// === i. Isi text input ===
String inputText = "Testing WDIO"
Mobile.setText(makeXpath("//android.widget.EditText[@content-desc='text-input']"), inputText, 1)
Mobile.hideKeyboard()

// === ii. Aktifkan switch ===
Mobile.tap(makeXpath("//android.widget.Switch[@content-desc='switch']"), 1)

// === iii. Klik tombol Active ===
Mobile.scrollToText('Active')
Mobile.tap(makeXpath("//android.view.ViewGroup[@content-desc='button-Active']"), 1)
Mobile.getText(findTestObject('Object Repository/android.widget.TextView - This button is active'), 0)

Mobile.tap(findTestObject('Object Repository/android.widget.Button - OK (1)'), 0)

// === iv. Klik dropdown dan pilih item acak ===
// 1. Tap dropdown
Mobile.tap(makeXpath("//android.view.ViewGroup[@content-desc='Dropdown']"), 1)
// 2. Ambil semua item dropdown
List<String> dropdownItems = ['webdriver.io is awesome', 'Appium is awesome', 'Detox is awesome']
// 3. Pilih random item
Random rand = new Random()
String selectedItem = dropdownItems[rand.nextInt(dropdownItems.size())]
// 4. Tap item yang dipilih
Mobile.tap(makeText(selectedItem), 1)

// === b) Validasi ===

// i. Modal muncul dengan teks "This button is active"
Mobile.tap(makeXpath("//android.view.ViewGroup[@content-desc='button-Active']"), 1)
Mobile.getText(findTestObject('Object Repository/android.widget.TextView - This button is active'), 0)

Mobile.tap(findTestObject('Object Repository/android.widget.Button - OK (1)'), 0)

// ii. Validasi text input tersimpan dengan benar
String actualInput = Mobile.getText(makeXpath("//android.widget.EditText[@content-desc='text-input']"), 1)
assert actualInput == inputText : "Input tidak sesuai!"

// iii. Validasi switch aktif
String switchStatus = Mobile.getAttribute(makeXpath("//android.widget.Switch[@content-desc='switch']"), "checked", 1)
assert switchStatus == "true" : "Switch tidak aktif!"

// iv. Validasi item dropdown sesuai pilihan
String actualDropdown = Mobile.getText(makeXpath("//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.widget.EditText[1]"), 1)
assert actualDropdown == selectedItem : "Item dropdown tidak sesuai!"

// v. Jika mengandung "Appium", ambil screenshot
if (selectedItem.toLowerCase().contains("appium")) {
	Mobile.takeScreenshot("Reports/dropdown_contains_appium.png")
}

// vi. Screenshot sesudah form diisi
Mobile.takeScreenshot("Reports/final_filled_form.png")

// Tutup aplikasi
Mobile.closeApplication()
