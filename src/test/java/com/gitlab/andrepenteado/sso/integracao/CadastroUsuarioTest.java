package com.gitlab.andrepenteado.sso.integracao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@IfProfileValue(name = "test-group", value = "integration")  // mvn test -Dtest-group=integration
public class CadastroUsuarioTest {

    @Value("${url.homologacao}")
    private String urlHomologacao;

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        // Chrome driver
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver(options);
    }

    @Test
    public void testCadastroUsuario() throws Exception {
        driver.get(urlHomologacao);
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("admin");
        driver.findElement(By.id("form-login")).submit();
        driver.findElement(By.linkText("Configurações")).click();
        driver.findElement(By.linkText("Usuários do Sistema")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Usuários'])[1]/following::a[1]")).click();
        driver.findElement(By.id("login")).clear();
        driver.findElement(By.id("login")).sendKeys("teste");
        driver.findElement(By.id("txt_nova_senha")).clear();
        driver.findElement(By.id("txt_nova_senha")).sendKeys("teste");
        driver.findElement(By.id("txt_confirme_senha")).clear();
        driver.findElement(By.id("txt_confirme_senha")).sendKeys("teste");
        driver.findElement(By.id("nome")).clear();
        driver.findElement(By.id("nome")).sendKeys("Usuário Teste");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Data Expiração'])[1]/following::div[3]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Sá'])[1]/following::td[32]")).click();
        driver.findElement(By.id("perfis1")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Administrador'])[1]/following::button[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Os dados do usuário foram gravados com sucesso'])[1]/following::button[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Usuários do Sistema'])[1]/following::a[1]")).click();
        driver.findElement(By.linkText("Sair")).click();
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("teste");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("teste");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Digite seu usuário e senha para entrar'])[1]/following::button[1]")).click();
        driver.findElement(By.cssSelector("svg.svg-inline--fa.fa-user.fa-w-14 > path")).click();
        driver.findElement(By.linkText("Sair")).click();
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("admin");
        driver.findElement(By.id("form-login")).submit();
        driver.findElement(By.cssSelector("svg.svg-inline--fa.fa-desktop.fa-w-18")).click();
        driver.findElement(By.cssSelector("svg.svg-inline--fa.fa-users.fa-w-20 > path")).click();
        driver.findElement(By.id("nome")).click();
        driver.findElement(By.id("nome")).clear();
        driver.findElement(By.id("nome")).sendKeys("Usuário Teste 2");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Data Expiração'])[1]/following::div[3]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Sá'])[1]/following::td[42]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Administrador'])[1]/following::button[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Os dados do usuário foram gravados com sucesso'])[1]/following::button[1]")).click();
        driver.findElement(By.linkText("Usuários")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Cancelar'])[1]/following::button[1]")).click();
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("admin");
        driver.findElement(By.id("form-login")).submit();
        driver.findElement(By.linkText("Configurações")).click();
        driver.findElement(By.cssSelector("svg.svg-inline--fa.fa-users.fa-w-20 > path")).click();
        driver.findElement(By.cssSelector("svg.svg-inline--fa.fa-plus.fa-w-14.fa-lg > path")).click();
        driver.findElement(By.id("login")).clear();
        driver.findElement(By.id("login")).sendKeys("mateus");
        driver.findElement(By.id("txt_nova_senha")).clear();
        driver.findElement(By.id("txt_nova_senha")).sendKeys("mateus");
        driver.findElement(By.id("txt_confirme_senha")).clear();
        driver.findElement(By.id("txt_confirme_senha")).sendKeys("mateus");
        driver.findElement(By.id("nome")).clear();
        driver.findElement(By.id("nome")).sendKeys("Mateus de Almeida");
        driver.findElement(By.id("perfis1")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Administrador'])[1]/following::button[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Os dados do usuário foram gravados com sucesso'])[1]/following::button[1]")).click();
        driver.findElement(By.linkText("Usuários")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Usuários do Sistema'])[1]/following::a[1]")).click();
        driver.findElement(By.linkText("Sair")).click();
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("mateus");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("mateus");
        driver.findElement(By.id("form-login")).submit();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Usuários do Sistema'])[1]/following::a[1]")).click();
        driver.findElement(By.linkText("Meus Dados")).click();
        driver.findElement(By.id("txt_nova_senha")).click();
        driver.findElement(By.id("txt_nova_senha")).clear();
        driver.findElement(By.id("txt_nova_senha")).sendKeys("teste");
        driver.findElement(By.id("txt_confirme_senha")).clear();
        driver.findElement(By.id("txt_confirme_senha")).sendKeys("teste");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Administrador'])[1]/following::button[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Os dados do usuário foram gravados com sucesso'])[1]/following::button[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Usuários do Sistema'])[1]/following::a[1]")).click();
        driver.findElement(By.linkText("Sair")).click();
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("mateus");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("teste");
        driver.findElement(By.id("form-login")).submit();
        driver.findElement(By.cssSelector("svg.svg-inline--fa.fa-user.fa-w-14 > path")).click();
        driver.findElement(By.linkText("Configurações")).click();
        driver.findElement(By.linkText("Usuários do Sistema")).click();
        driver.findElement(By.cssSelector("svg.svg-inline--fa.fa-user.fa-w-14 > path")).click();
        driver.findElement(By.linkText("Sair")).click();
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("admin");
        driver.findElement(By.id("form-login")).submit();
        driver.findElement(By.linkText("Configurações")).click();
        driver.findElement(By.linkText("Usuários do Sistema")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='mateus'])[1]/following::a[2]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Cancelar'])[1]/following::button[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='A exclusão do usuário foi realizada com sucesso'])[1]/following::button[1]")).click();
        driver.findElement(By.cssSelector("tr.even > td.text-center.text-nowrap > a.btn.btn-success.btn-sm > svg.svg-inline--fa.fa-pencil-alt.fa-w-16 > path")).click();
        driver.findElement(By.id("txt_nova_senha")).click();
        driver.findElement(By.id("txt_nova_senha")).clear();
        driver.findElement(By.id("txt_nova_senha")).sendKeys("passwebra");
        driver.findElement(By.id("txt_confirme_senha")).clear();
        driver.findElement(By.id("txt_confirme_senha")).sendKeys("passwebra");
        driver.findElement(By.id("nome")).click();
        driver.findElement(By.id("nome")).clear();
        driver.findElement(By.id("nome")).sendKeys("André Penteado 2");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Data Expiração'])[1]/following::div[3]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Sá'])[1]/following::td[42]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Administrador'])[1]/following::button[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Os dados do usuário foram gravados com sucesso'])[1]/following::button[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Usuários do Sistema'])[1]/following::a[1]")).click();
        driver.findElement(By.linkText("Sair")).click();
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("andre");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("passwebra");
        driver.findElement(By.id("form-login")).submit();
        driver.findElement(By.cssSelector("svg.svg-inline--fa.fa-user.fa-w-14 > path")).click();
        driver.findElement(By.linkText("Sair")).click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
