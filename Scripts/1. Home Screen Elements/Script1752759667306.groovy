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

// Fungsi bantu bikin TestObject inline
TestObject makeTextObject(String visibleText) {
	TestObject to = new TestObject()
	to.addProperty("xpath", ConditionType.EQUALS, "//*[@text='${visibleText}']")
	return to
}

// Mulai aplikasi
String appPath = 'C:/Users/USER/Downloads/android.wdio.native.app.v1.0.8.apk' // disesuaikan dengan letak mobile app yg telah didownload
Mobile.startApplication(appPath, false)

// Tunggu dan validasi masing-masing elemen
String[] homeButtons = ['Login', 'Forms', 'Swipe', 'Drag']

for (String buttonText : homeButtons) {
	TestObject btn = makeTextObject(buttonText)
	Mobile.waitForElementPresent(btn, 1)
	String actualText = Mobile.getText(btn, 0)
	println "Text ditemukan: " + actualText
}

// Screenshot halaman utama
Mobile.takeScreenshot('Reports/home_screen.png')

// Tutup aplikasi
Mobile.closeApplication()
