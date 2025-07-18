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

// Helper buat TestObject berdasarkan xpath atau text
TestObject makeXpath(String xpath) {
	TestObject to = new TestObject()
	to.addProperty("xpath", ConditionType.EQUALS, xpath)
	return to
}

TestObject makeText(String text) {
	TestObject to = new TestObject()
	to.addProperty("xpath", ConditionType.EQUALS, "//*[@text='${text}']")
	return to
}

// Start aplikasi
String appPath = 'C:/Users/USER/Downloads/android.wdio.native.app.v1.0.8.apk' // disesuaikan dengan letak mobile app yg telah didownload
Mobile.startApplication(appPath, false)

// === a) Buka menu Drag ===
Mobile.tap(makeText('Drag'), 5)

// pieces masuk array dan perulangan
def pieces = []
['l', 'c', 'r'].each { col ->
	[1, 2, 3].each { row ->
		pieces << [source: "drag-${col}${row}", target: "drop-${col}${row}"]
	}
}


for (def pair : pieces) {
	println "Dragging ${pair.source} → ${pair.target}"
	TestObject dragObj = makeXpath("//*[@content-desc='${pair.source}']")
	TestObject dropObj = makeXpath("//*[@content-desc='${pair.target}']")

	Mobile.dragAndDrop(dragObj, dropObj, 5)
	Mobile.delay(1)
}

// === c) Validasi muncul tulisan “Congratulations” ===
boolean success = Mobile.waitForElementPresent(makeText('Congratulations'), 5)
assert success : '"Congratulations" not found, puzzle might not be complete!'

// Screenshot hasil sukses
Mobile.takeScreenshot("Reports/drag_puzzle_success.png")

// Tutup aplikasi
Mobile.closeApplication()
