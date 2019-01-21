/*
 *     Jaron  Copyright (C) 2019  Nikhil Soni
 *     This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.
 *     This is free software, and you are welcome to redistribute it
 *     under certain conditions; type `show c' for details.
 *
 * The hypothetical commands `show w' and `show c' should show the appropriate
 * parts of the General Public License.  Of course, your program's commands
 * might be different; for a GUI interface, you would use an "about box".
 *
 *   You should also get your employer (if you work as a programmer) or school,
 * if any, to sign a "copyright disclaimer" for the program, if necessary.
 * For more information on this, and how to apply and follow the GNU GPL, see
 * <https://www.gnu.org/licenses/>.
 *
 *   The GNU General Public License does not permit incorporating your program
 * into proprietary programs.  If your program is a subroutine library, you
 * may consider it more useful to permit linking proprietary applications with
 * the library.  If this is what you want to do, use the GNU Lesser General
 * Public License instead of this License.  But first, please read
 * <https://www.gnu.org/licenses/why-not-lgpl.html>.
 */

package com.nsnik.nrs.jaron.security

import android.util.Base64
import java.nio.charset.Charset
import java.security.*
import javax.crypto.Cipher

class EncryptionUtility {

    companion object {

        private const val default: String = "NA"
        private const val defaultPrivateKey = "NA"
        private const val defaultPublicKey = "NA"


        private fun buildKeyPair(): KeyPair {
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            keyPairGenerator.initialize(1024, SecureRandom())
            return keyPairGenerator.genKeyPair()
        }

        private fun getCipher(): Cipher {
            return Cipher.getInstance("RSA")
        }

        private fun getEncryptedKey(cipher: Cipher, privateKey: PrivateKey, plainText: String): ByteArray {
            cipher.init(Cipher.ENCRYPT_MODE, privateKey)
            return cipher.doFinal(plainText.toByteArray(Charset.forName("UTF-8")))
        }

        private fun getDecryptedKey(cipher: Cipher, publicKey: PublicKey, encrypted: ByteArray): ByteArray {
            cipher.init(Cipher.DECRYPT_MODE, publicKey)
            return cipher.doFinal(encrypted)
        }

        private fun encrypt(plainText: String): String {
            return getEncryptedString(getEncryptedKey(getCipher(), getPrivateKey()!!, plainText))
        }

        private fun decrypt(cipherText: String): String {
            return String(
                getDecryptedKey(
                    getCipher(), getPublicKey()!!,
                    getDecryptedByteArray(cipherText)
                ), Charset.forName("UTF-8")
            )
        }

        private fun getEncryptedString(encrypted: ByteArray): String {
            return Base64.encodeToString(encrypted, Base64.NO_WRAP)
        }

        private fun getDecryptedByteArray(encodedString: String): ByteArray {
            return Base64.decode(encodedString, Base64.NO_WRAP)
        }

        private fun getKeyPair(): KeyPair {
            return buildKeyPair()
        }

        private fun getPublicKey(): PublicKey? {
            //TODO
            return null
        }

        private fun getPrivateKey(): PrivateKey? {
            //TODO
            return null
        }

    }

}