package com.example.password_manager

import com.example.password_manager.secret_code.MyApp
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import android.util.Base64

fun String.encrypt(): String {
    val iv = IvParameterSpec(MyApp.secretKey.toByteArray())
    val keySpec = SecretKeySpec(MyApp.secretKey.toByteArray(),"AES")
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)
    val crypted = cipher.doFinal(this.toByteArray())
    val encodedByte = Base64.encode(crypted,Base64.DEFAULT)
    return String(encodedByte)
}

fun String.decrypt(): String {
    val iv = IvParameterSpec(MyApp.secretKey.toByteArray())
    val keySpec = SecretKeySpec(MyApp.secretKey.toByteArray(),"AES")
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)
    val encryptedBytes = Base64.decode(this, Base64.DEFAULT)
    val output = cipher.doFinal(encryptedBytes)
    return String(output)
}