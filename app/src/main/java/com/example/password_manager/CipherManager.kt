import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import java.util.Base64

class CipherManager {
    fun generateIV(): ByteArray {
        val random = SecureRandom()
        val iv = ByteArray(16)
        random.nextBytes(iv)
        return iv
    }

    fun encrypt(key: SecretKey, iv: ByteArray, plaintext: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec = SecretKeySpec(key.encoded, "AES")
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encryptedBytes = cipher.doFinal(plaintext.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decrypt(key: SecretKey, iv: ByteArray, ciphertext: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec = SecretKeySpec(key.encoded, "AES")
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        val encryptedBytes = Base64.getDecoder().decode(ciphertext)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes)
    }

    fun createSecretKeyFromString(keyString: String?): SecretKey {
        val algorithm = "AES"
        val keyBytes = keyString?.toByteArray(StandardCharsets.UTF_8)
        val secretKeyBytes = Base64.getDecoder().decode(keyBytes)
        return SecretKeySpec(secretKeyBytes, algorithm)
    }
}
