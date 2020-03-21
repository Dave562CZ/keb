package org.kebish.core.module

import io.github.bonigarcia.wdm.WebDriverManager
import kotlinx.html.body
import kotlinx.html.passwordInput
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.kebish.core.Page
import org.kebish.core.kebConfig
import org.kebish.junit5.KebTest
import org.kebish.usage.test.util.HtmlContent
import org.kebish.usage.test.util.HttpBuilderServerExtension
import org.kebish.usage.test.util.extendable.Extendable
import org.kebish.usage.test.util.extendable.ExtendableImpl
import org.openqa.selenium.firefox.FirefoxDriver

internal class PasswordInputTest : KebTest(kebConfig {
    WebDriverManager.firefoxdriver().setup()
    this.driver = { FirefoxDriver() }
}), Extendable by ExtendableImpl() {

    @Suppress("unused")
    private val serverExtension = register(HttpBuilderServerExtension(
        browser,
        HtmlContent {
            body {
                passwordInput(classes = "pass") { value = "initial value" }
            }
        }
    ))

    @Test
    fun `testing text input module`() {
        // given
        val inputModule = to(::PasswordInputTestPage).input

        // when
        val initialValue = inputModule.text

        // then
        Assertions.assertThat(initialValue).isEqualTo("initial value")

        // when
        inputModule.text = "testing"

        // then
        Assertions.assertThat(inputModule.text).isEqualTo("testing")
    }

}

class PasswordInputTestPage : Page() {

    override fun at() = input

    val input by content { module(PasswordInput(css(".pass"))) }
}