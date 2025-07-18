
# 📱 Mobile Test Automation Project

## 🧪 Deskripsi
Ini adalah proyek **Mobile Test Automation** menggunakan **Katalon Studio**, mencakup beberapa skenario UI testing untuk aplikasi Android seperti:
1. Home Screen Elements
2. Login Form
3. Modal Validasi dan Forms
4. Swipe dan Validasi Posisi Elemen
5. Drag and Drop Verification

## 📁 Struktur Folder
```
Test Cases/
  ├── 1. Home Screen Elements
  ├── 2. Login Form
  ├── 3. Modal Validasi dan Forms
  ├── 4. Swipe and Element Position Validation
  └── 5. Drag and Drop Verification
Object Repository/
Test Suites/
Reports/
```

## 🛠️ Tools yang Dibutuhkan
- [Katalon Studio](https://www.katalon.com/)
- Java JDK 8+
- Android SDK (biasanya otomatis saat install Android Studio)

## 🔌 Menjalankan di Perangkat Android

### Langkah Setup:
1. Aktifkan **Developer Options** di HP:
   - Masuk `Settings > About phone > Tap Build number 7x`
2. Aktifkan **USB Debugging**
   - Masuk `Developer Options > USB Debugging`
3. **Pasang Driver USB** jika pakai device fisik:
   - Windows: install driver sesuai merk HP (Samsung, Xiaomi, dll)
   - Mac/Linux: biasanya tidak perlu
4. Cek koneksi dengan:
   ```bash
   adb devices
   ```

> Jika pakai emulator, pastikan emulator aktif dan terdeteksi oleh Katalon.

## 📱 Kompatibilitas Android
- Test ini dibuat dan dijalankan di **Android 11**
- **Bisa dijalankan di Android 10–13**, selama struktur aplikasi dan elemen tidak berubah
- Beberapa fitur (misal: akses file) bisa dibatasi di versi Android tertentu

## 🚀 Cara Menjalankan Test
### Via Katalon Studio:
- Klik kanan pada Test Case → `Run > Android`
- Untuk Test Suite: buka `Test Suites` → klik kanan → `Run`

### Via Command Line:
```bash
katalon -noSplash -runMode=console -projectPath="path/to/your/project.prj" \
-testSuitePath="Test Suites/NamaTestSuite" -executionProfile="default" \
-browserType="Android"
```

## 📊 Hasil Test
Laporan test otomatis tersimpan di folder `Reports/` setelah dijalankan.

