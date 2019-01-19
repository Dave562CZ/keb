package com.horcacorp.testing.keb.junit

import com.horcacorp.testing.keb.core.Browser
import com.horcacorp.testing.keb.core.Page
import com.horcacorp.testing.keb.core.ScopedModule
import org.junit.Assert
import org.junit.Test
import org.openqa.selenium.WebElement

class KotlinSiteKebTest : KebTest() {

    @Test
    fun `testing kotlin lang page`() {
        // given
        val homePage = to(::KotlinPage)

        // when
        val title = homePage.header

        // then
        Assert.assertEquals("Kotlin", title.text)
        Assert.assertTrue(homePage.licensedUnderApacheLicense())

        // when
        val docsPage = homePage.openDocumentation()

        // then
        Assert.assertEquals("Learn Kotlin", docsPage.title.text)
    }

}

class KotlinPage(browser: Browser) : Page(browser) {
    override fun url() = "https://kotlinlang.org"
    override fun at() = header

    val header = css(".global-header-logo")
    val menu = scopedModule(::NavMenuModule, css(".nav-links"))
    val footer = scopedModule(::FooterModule, html("footer"))

    fun openDocumentation(): DocsPage {
        menu.menuItems.first { it.text == "LEARN" }.click()
        return at(::DocsPage)
    }

    fun licensedUnderApacheLicense() = footer.licenseNotice.text.contains("apache", ignoreCase = true)

}

class DocsPage(browser: Browser) : Page(browser) {
    override fun url() = "https://kotlinlang.org/docs/reference"
    override fun at() = title

    val title = html("h1")

}

class NavMenuModule(browser: Browser, scope: WebElement) : ScopedModule(browser, scope) {
    val menuItems = htmlList("a")
}

class FooterModule(browser: Browser, scope: WebElement) : ScopedModule(browser, scope) {
    val licenseNotice = css(".terms-copyright")
    val sponsor = css(".terms-sponsor")
}