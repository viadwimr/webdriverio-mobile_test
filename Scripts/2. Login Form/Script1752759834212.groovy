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

// Helper: Buat TestObject dari xpath
TestObject makeText(String text) {
	TestObject to = new TestObject()
	to.addProperty("xpath", ConditionType.EQUALS, "//*[@text='${text}']")
	return to
}

TestObject makeXpath(String xpath) {
	TestObject to = new TestObject()
	to.addProperty("xpath", ConditionType.EQUALS, xpath)
	return to
}

// Start app
String appPath = 'C:/Users/USER/Downloads/android.wdio.native.app.v1.0.8.apk' // disesuaikan dengan letak mobile app yg telah didownload
Mobile.startApplication(appPath, false)


// === STEP A: Buka aplikasi → klik Login ===
Mobile.tap(makeText('Login'), 1)


// === STEP B: Positive Case ===
// Input email
Mobile.setText(makeXpath("//android.widget.EditText[@content-desc='input-email']"), 'tester@yopmail.com', 1)
// Input password
Mobile.setText(makeXpath("//android.widget.EditText[@content-desc='input-password']"), '123456789', 1)
// Klik tombol login
Mobile.tap(findTestObject('Object Repository/android.widget.TextView - LOGIN (1)'), 0)
// Validasi muncul modal
Mobile.getText(findTestObject('Object Repository/android.widget.TextView - You are logged in'), 0)
// Klik OK
Mobile.tap(findTestObject('Object Repository/android.widget.Button - OK'), 0)

// === STEP C: Negative Case ===
// Kembali ke form login
Mobile.pressBack() // atau arahkan ulang jika perlu

// Input invalid email
Mobile.setText(makeXpath("//android.widget.EditText[@content-desc='input-email']"), 'tester', 5)
// Input invalid password
Mobile.setText(makeXpath("//android.widget.EditText[@content-desc='input-password']"), '1234', 5)
// Klik tombol login
Mobile.tap(findTestObject('Object Repository/android.widget.TextView - LOGIN (1)'), 0)


// Validasi pesan error email
Mobile.verifyElementVisible(makeText('Please enter a valid email address'), 5)
// Validasi pesan error password
Mobile.verifyElementVisible(makeText('Please enter at least 8 characters'), 5)

// Validasi tombol login tidak disabled (masih bisa diklik ulang)
Mobile.tap(findTestObject('Object Repository/android.widget.TextView - LOGIN (1)'), 0)


// Selesai
Mobile.takeScreenshot('Reports/login_test.png')
Mobile.closeApplication()
