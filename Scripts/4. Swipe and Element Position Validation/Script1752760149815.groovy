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
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
//import io.appium.java_client.MobileElement
//import com.kms.katalon.core.mobile.keyword.internal.MobileElementCommonHelper


// Helper buat TestObject
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

// Mulai aplikasi
String appPath = 'C:/Users/USER/Downloads/android.wdio.native.app.v1.0.8.apk' // disesuaikan dengan letak mobile app yg telah didownload
Mobile.startApplication(appPath, false)


// === a) Masuk menu Swipe ===
Mobile.tap(makeText('Swipe'), 5)

int deviceHeight = Mobile.getDeviceHeight()
int deviceWidth = Mobile.getDeviceWidth()

int startX = (deviceWidth * 0.9) as int
int endX = (deviceWidth * 0.1) as int
int posY = (deviceHeight * 0.5) as int

int maxSwipeLeft = 20
boolean foundCompatible = false

for (int i = 0; i < maxSwipeLeft; i++) {
	println("Swipe ke-${i + 1}, mencari tombol 'COMPATIBLE'...")

	if (Mobile.waitForElementPresent(makeText('COMPATIBLE'), 1)) {
		println("✅ Tombol ditemukan!")
		foundCompatible = true
		Mobile.swipe(startX, posY, endX, posY) // swipe kanan → kiri
		Mobile.delay(2)
		break
	}

	Mobile.swipe(startX, posY, endX, posY) // swipe kanan → kiri
	Mobile.delay(2)
}

assert foundCompatible : '"COMPATIBLE" button not found after swiping!'

// Untuk validasi akhir
Mobile.verifyElementVisible(makeText('COMPATIBLE'), 5)

int startY = (deviceHeight * 0.4) as int  // titik awal dari setelah setengah layar
int endY = (deviceHeight * 0.1) as int    // titik akhir ke atas
int posX = (deviceWidth * 0.5) as int     // swipe di tengah horizontal layar

int maxSwipeUp = 20
boolean foundText = false

for (int i = 0; i < maxSwipeUp; i++) {
	println("Swipe ke-${i + 1}, mencari teks 'You found me!!!'...")
	
	// karena swipe tertutup component
	if (i>0) {
		startY = (deviceHeight * 0.9) as int  // titik awal dari bawah layar
		endY = (deviceHeight * 0.6) as int    // titik akhir ke atas sebelum setengah layar
	}
	
	if (Mobile.waitForElementPresent(makeText('You found me!!!'), 1)) {
		println("✅ Teks ditemukan!")
		foundText = true
		Mobile.swipe(posX, startY, posX, endY)  // swipe vertikal dari bawah ke atas
		Mobile.delay(1)
		break
	}
	
	Mobile.swipe(posX, startY, posX, endY)  // swipe vertikal dari bawah ke atas
	Mobile.delay(1)
}

assert foundText : '"You found me!!!" not found after swiping up'


// Validasi logo WDIO muncul
TestObject wdioLogo = makeXpath("//android.widget.ImageView[contains(@content-desc, 'WebdriverIO')]")
Mobile.verifyElementVisible(wdioLogo, 5)


// Screenshot hasil swipe
Mobile.takeScreenshot("Reports/swipe_verification.png")

// Tutup aplikasi
Mobile.closeApplication()
