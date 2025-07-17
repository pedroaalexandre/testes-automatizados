package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class SeleniumWebDriverTest {

    private WebDriver driver;

	@BeforeEach
	public void openBrowser() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterEach
	public void closeBrowser() {
		driver.close();
	}


	/**
	 * <p><b>Testar cadastro de um novo usuário</b></p>
	 * <p><b>Nome: </b>Pedro Augusto</p>
	 * <p><b>Especialidade: </b>Cavalos</p>
	 * <p><b>Email: </b>pedro@gmail.com</p>
	 * <p><b>Salário: </b>3500.00</p>
	 * <p><b>Obs.:</b> O teste deve inserir as informações e compará-las para verificar se estão corretas.</p>
	 */
	@Test
	@Order(1)
	@DisplayName("Testar novo cadastro")
	public void testarCadastrarVeterinario() {
		//arrange
		String nome = "Pedro Augusto";
		String especialidade = "Cavalos";
		String email = "pedro@gmail.com";
		String salario = "3500.00";

		//act
		driver.get("http://localhost:8080/home");

			//cadastrar
			WebElement btnCadastrar = driver.findElement(By.cssSelector("a:nth-child(3) > .btn"));
			btnCadastrar.click();

			WebElement campoNome = driver.findElement(By.id("nome"));
			campoNome.sendKeys("Pedro Augusto");

			WebElement campoEmail = driver.findElement(By.id("inputEmail"));
			campoEmail.sendKeys("pedro@gmail.com");

			WebElement campoEspec = driver.findElement(By.id("inputEspecialidade"));
			campoEspec.sendKeys("Cavalos");

			WebElement campoSalario = driver.findElement(By.id("inputSalario"));
			campoSalario.sendKeys("3500");

			WebElement confirmarCadastro = driver.findElement(By.cssSelector(".btn"));
			confirmarCadastro.click();

			//nome
			WebElement textoNome = driver.findElement(By.cssSelector("tr:nth-child(4) > td:nth-child(2) > span"));
			String nomeObtido = textoNome.getText();

			//especialidade
			WebElement textoEspec = driver.findElement(By.cssSelector("tr:nth-child(4) > td:nth-child(3) > span"));
			String especObtida = textoEspec.getText();

			//email
			WebElement textoEmail = driver.findElement(By.cssSelector("tr:nth-child(4) > td:nth-child(4) > span"));
			String emailObtido = textoEmail.getText();

			//salario
			WebElement textoSalario = driver.findElement(By.cssSelector("tr:nth-child(4) > td:nth-child(5) > span"));
			String salarioObtido = textoSalario.getText();

		//assert
		assertEquals(nome, nomeObtido);
		assertEquals(especialidade, especObtida);
		assertEquals(email, emailObtido);
		assertEquals(salario, salarioObtido);
	}

	/**
	 * <p><b>Testar pesquisar um usuário existente</b></p>
	 * <p><b>Obs.:</b> O teste deve clicar no botão <b>Consultar</b> e digitar o nome do usuário Erica no campo,
	 *  logo em seguida ele faz as asserções para conferir se as informações estão corretas.</p>
	 */
	@Test
	@Order(2)	
	@DisplayName("Pesquisar um usuário existente")
	public void testarPesquisarVeterinario() {
		//arrange
		String nome = "Erica Queiroz Pinto";
		String especialidade = "grandes";
		String email = "erica@gmail.com";
		String salario = "4500.00";

		//act
		driver.get("http://localhost:8080/home");

		WebElement btnConsultar = driver.findElement(By.cssSelector("a:nth-child(4) > .btn"));
		btnConsultar.click();

		WebElement textoNome = driver.findElement(By.id("nome"));
		textoNome.sendKeys("Erica");

		WebElement btnConsultar2 = driver.findElement(By.cssSelector(".btn"));
		btnConsultar2.click();

			//nome
			WebElement textoNomeVet = driver.findElement(By.cssSelector("td:nth-child(2) > span"));
			String nomeObtido = textoNomeVet.getText();

			//especialidade
			WebElement textoEspec = driver.findElement(By.cssSelector("td:nth-child(3) > span"));
			String especObtida = textoEspec.getText();

			//email
			WebElement textoEmail = driver.findElement(By.cssSelector("td:nth-child(4) > span"));
			String emailObtido = textoEmail.getText();

			//salario
			WebElement textoSalario = driver.findElement(By.cssSelector("td:nth-child(5) > span"));
			String salarioObtido = textoSalario.getText();

		//assert
		assertEquals(nome, nomeObtido);
		assertEquals(especialidade, especObtida);
		assertEquals(email, emailObtido);
		assertEquals(salario, salarioObtido);
	}

	/**
	 * <p><b>Testar excluir um usuário existente</b></p>
	 * <p><b>Obs.: </b>Clica no botão excluir e utiliza o <b>Wait</b> para esperar o elemento desaparecer.
	 *  Logo em seguida seleciona a linha excluída e faz uma asserção verificando se ela está vazia.</p>
	 */
	@Test
	@Order(3)
	@DisplayName("Excluir um usuário existente")
	public void testarExcluirVeterinario() {
		//arrange
		driver.get("http://localhost:8080/home");

		//act
		WebElement btnExcluir = driver.findElement(By.cssSelector("tr:nth-child(3) .btn-danger"));
		btnExcluir.click();

		//Espera a página ser atualizada ou elemento desaparecer
		new WebDriverWait(driver, Duration.ofSeconds(5))
			.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("tr:nth-child(4)")));

		//assert
		List<WebElement> linha = driver.findElements(By.cssSelector("tr:nth-child(4)"));
		assertTrue(linha.isEmpty());
	}


	/**
	 * <p><b>Testar editar um usuário existente</b></p>
	 * <p><b>Nome: </b>Erica Queiroz</p>
	 * <p><b>Especialidade: </b>pequenos</p>
	 * <p><b>Email: </b>ericaqueiroz@gmail.com</p>
	 * <p><b>Salário: </b>7500.00</p>
	 * <p><b>Obs.:</b> O teste deve editar as informações e compará-las para verificar se de fato foram alteradas.</p>
	 */
	@Test
	@Order(4)
	@DisplayName("Editar usuário existente.")
	public void testarAlterarUsuarioExistente() {
		//arrange
		String novoNome = "Erica Queiroz";
		String novaEspecialidade = "pequenos";
		String novoEmail = "ericaqueiroz@gmail.com";
		String novoSalario = "7500.00";

		//act
		driver.get("http://localhost:8080/home");

			//editar
			WebElement btnEditar = driver.findElement(By.cssSelector("tr:nth-child(3) .btn-warning"));
			btnEditar.click();

			WebElement campoNome = driver.findElement(By.id("nome"));
			campoNome.clear();
			campoNome.sendKeys(novoNome);

			WebElement campoEmail = driver.findElement(By.id("inputEmail"));
			campoEmail.clear();
			campoEmail.sendKeys(novoEmail);

			WebElement campoEspec = driver.findElement(By.id("inputEspecialidade"));
			campoEspec.clear();
			campoEspec.sendKeys(novaEspecialidade);

			WebElement campoSalario = driver.findElement(By.id("inputSalario"));
			campoSalario.clear();
			campoSalario.sendKeys(novoSalario);

			WebElement confirmarCadastro = driver.findElement(By.cssSelector(".btn"));
			confirmarCadastro.click();

			//nome
			WebElement textoNome = driver.findElement(By.cssSelector("tr:nth-child(3) > td:nth-child(2) > span"));
			String nomeObtido = textoNome.getText();

			//especialidade
			WebElement textoEspec = driver.findElement(By.cssSelector("tr:nth-child(3) > td:nth-child(3) > span"));
			String especObtida = textoEspec.getText();

			//email
			WebElement textoEmail = driver.findElement(By.cssSelector("tr:nth-child(3) > td:nth-child(4) > span"));
			String emailObtido = textoEmail.getText();

			//salario
			WebElement textoSalario = driver.findElement(By.cssSelector("tr:nth-child(3) > td:nth-child(5) > span"));
			String salarioObtido = textoSalario.getText();

		//assert
		assertEquals(novoNome, nomeObtido);
		assertEquals(novaEspecialidade, especObtida);
		assertEquals(novoEmail, emailObtido);
		assertEquals(novoSalario, salarioObtido);
	}
}
