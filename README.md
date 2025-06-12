[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/x5AMDzCW)
# Aplikasi Management User Sederhana
Lengkapi dan pahami program sederhana berbasis console yang sudah disediakan, agar aplikasi dapat memiliki fitur-fitur berikut ini:

## Requirement
1. **Otentikasi & otorisasi Pengguna**:
   - Aplikasi menerapkan login berdasarkan username dan password. (username dan password default applikasi adalah username:admin dan password:admin123) 
   - Data pengguna dicek melalui query database di UserRepository.authenticateUser(username, password). 
   - Jika data cocok, maka pengguna dapat mengakses fitur CRUD. 
   - Implementasi login terdapat di UASApplication.java pada awal metode main.
2. **Implementasi Session sederhana**:
   - Session login disimpan menggunakan class SessionManager, yang menerapkan singleton pattern. 
   - Saat user login berhasil, objek User disimpan ke dalam session. 
   - Informasi session disimpan dalam file session.ser, sehingga saat aplikasi dijalankan ulang, status login tetap dipertahankan tanpa harus login ulang.
3. **Implementasi CRUD User**:
   - Operasi Create, Read, Update, Delete dilakukan melalui UserRepository. 
   - Atribut pengguna meliputi:
     - String email 
     - String username 
     - String password 
   - Method yang tersedia:
     - insertUser(User user)
     - findAll()
     - updateUser(User user)
     - deleteUser(String username)
4. **Persistensi Data user**:
   - Koneksi database dikelola oleh DBConnectionManager, yang juga menggunakan singleton pattern. 
   - Database SQLite digunakan dan file dbuas.db akan dibuat otomatis jika belum ada. 
   - Struktur database dapat dilihat dari pembuatan table users pada eksekusi pertama kali.

## Desain
Berikut adalah desain class diagram aplikasi yang akan dibuat.  
![plot](/img/classDiagram.png)
- User memiliki atribut email, username, password 
- UserRepository mengakses data melalui Connection dari DBConnectionManager 
- SessionManager menyimpan user yang sedang login 
- UASApplication sebagai antarmuka pengguna utama

## Pengujian
Unit test tersedia di folder test. Silakan jalankan AppsTest untuk memverifikasi implementasi Anda.