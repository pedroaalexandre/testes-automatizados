package br.edu.iftm.atividadea2.atividadea2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculadoraTest {
    private static Calculadora calculadora;

    @BeforeEach
    public void aplicarOValorAoConstrutorAntesDeCadaTeste() {
        calculadora = new Calculadora(3);
    }

    @AfterEach
    public void zerarAMemoriaDepoisDeCadaTeste() {
        calculadora = new Calculadora();
        calculadora.zerarMemoria();
    }

    /**
     * Testa o construtor sem parâmetro, para verificar se o valor da memória é igual a 0
     * <p>
     * <b>Cenário : </b> Utilizar o construtor Calculador calculadora = new Calculadora() para verificar o valor da memória
     * </p>
     * <p>
     * <b>Entrada : </b>
     * </p>
     * <p>
     * - Calculadora calculadoraTeste = new Calculadora();
     * </p>
     * <p>
     * - valorMemoria = calculadoraTeste.getMemoria();
     * </p>
     * <p>
     * <b>Saída : </b>0
     * </p>
     */
    @Test
    public void testarContrutorSemParametro() {
        //arrange
        Calculadora calculadoraTeste = new Calculadora();
        int valorMemoria = calculadoraTeste.getMemoria();
        int resultadoEsperado = 0;

        //act
        int resultadoObtido = valorMemoria;

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * Testa o construtor com parâmetro, passando como parâmetro um número positivo
     * <p>
     * <b>Cenário : </b> Utilizar o construtor Calculador calculadora = new Calculadora(x) atribuir um valor à memória
     * </p>
     * <p>
     * <b>Entrada : </b>
     * </p>
     * <p>
     * - calculadora = new Calculadora(3);
     * </p>
     * <p>
     * <b>Saída : </b>3
     * </p>
     */
    @Test
    public void testarContrutorComParametroNumeroInteiroPositivo() {
        //arrange
        calculadora = new Calculadora(3);
        int resultadoEsperado = 3;

        //act
        int resultadoObtido = calculadora.getMemoria();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * Testa o construtor com parâmetro, passando como parâmetro um número negativo
     * <p>
     * <b>Cenário : </b> Utilizar o construtor Calculador calculadora = new Calculadora(x) atribuir um valor à memória
     * </p>
     * <p>
     * <b>Entrada : </b>
     * </p>
     * <p>
     * - calculadora = new Calculadora(-3);
     * </p>
     * <p>
     * <b>Saída : </b>-3
     * </p>
     */
    @Test
    public void testarContrutorComParametroNumeroInteiroNegativo() {
        //arrange
        calculadora = new Calculadora(-3);
        int resultadoEsperado = -3;

        //act
        int resultadoObtido = calculadora.getMemoria();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }
    
    /**
     * Testa a soma de um número inteiro positivo
     * <p>
     * <b>Cenário : </b> Somar um número inteiro positivo
     * </p>
     * <p>
     * <b>Entrada : </b>
     * </p>
     * <p>
     * - numeroSoma = 2
     * </p>
     * <p>
     * <b>Saída : </b>5
     * </p>
     */
    @Test
    public void testarSomaNumeroInteiroPositivo() {
        //arrange
        int numeroSoma = 2;
        int resultadoEsperado = 5;

        //act
        calculadora.somar(numeroSoma);
        int resultadoObtido = calculadora.getMemoria();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * Testa a soma de um número inteiro negativo
     * <p>
     * <b>Cenário : </b> Somar um número inteiro negativo
     * </p>
     * <p>
     * <b>Entrada : </b>
     * </p>
     * <p>
     * - numeroSoma = -5
     * </p>
     * <p>
     * <b>Saída : </b>-2
     * </p>
     */
    @Test
    public void testarSomaNumeroInteiroNegativo() {
        //arrange
        int numeroSoma = -5;
        int resultadoEsperado = -2;

        //act
        calculadora.somar(numeroSoma);
        int resultadoObtido = calculadora.getMemoria();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * Testa a subtração de um número inteiro positivo
     * <p>
     * <b>Cenário : </b> Subtrair um número inteiro positivo
     * </p>
     * <p>
     * <b>Entrada : </b>
     * </p>
     * <p>
     * - numeroSubtracao = 5
     * </p>
     * <p>
     * <b>Saída : </b>-2
     * </p>
     */
    @Test
    public void testarSubstracaoNumeroInteiroPositivo() {
        //arrange
        int numeroSubtracao = 5;
        int resultadoEsperado = -2;

        //act
        calculadora.subtrair(numeroSubtracao);
        int resultadoObtido = calculadora.getMemoria();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * Testa a subtração de um número inteiro negativo
     * <p>
     * <b>Cenário : </b> Subtrair um número inteiro negativo
     * </p>
     * <p>
     * <b>Entrada : </b>
     * </p>
     * <p>
     * - numeroSubtracao = -5
     * </p>
     * <p>
     * <b>Saída : </b>8
     * </p>
     */
    @Test
    public void testarSubstracaoNumeroInteiroNegativo() {
        //arrange
        int numeroSubtracao = -5;
        int resultadoEsperado = 8;

        //act
        calculadora.subtrair(numeroSubtracao);
        int resultadoObtido = calculadora.getMemoria();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * Testa a multiplicação de um número inteiro positivo
     * <p>
     * <b>Cenário : </b> Multiplicar um número inteiro positivo
     * </p>
     * <p>
     * <b>Entrada : </b>
     * </p>
     * <p>
     * - numeroMultiplicacao = 5
     * </p>
     * <p>
     * <b>Saída : </b>15
     * </p>
     */
    @Test
    public void testarMultiplicacaoNumeroInteiroPositivo() {
        //arrange
        int numeroMultiplicacao = 5;
        int resultadoEsperado = 15;

        //act
        calculadora.multiplicar(numeroMultiplicacao);
        int resultadoObtido = calculadora.getMemoria();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * Testa a multiplicação de um número inteiro negativo
     * <p>
     * <b>Cenário : </b> Multiplicar um número inteiro negativo
     * </p>
     * <p>
     * <b>Entrada : </b>
     * </p>
     * <p>
     * - numeroMultiplicacao = -5
     * </p>
     * <p>
     * <b>Saída : </b>-15
     * </p>
     */
    @Test
    public void testarMultiplicacaoNumeroInteiroNegativo() {
        //arrange
        int numeroMultiplicacao = -5;
        int resultadoEsperado = -15;

        //act
        calculadora.multiplicar(numeroMultiplicacao);
        int resultadoObtido = calculadora.getMemoria();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * Testa a divisão de um número inteiro positivo
     * <p>
     * <b>Cenário : </b> Dividir um número inteiro positivo
     * </p>
     * <p>
     * <b>Entrada : </b>
     * </p>
     * <p>
     * - numeroDivisao = 2
     * </p>
     * <p>
     * <b>Saída : </b>1
     * </p>
     */
    @Test
    public void testarDividirNumeroInteiroPositivo() throws Exception{
        //arrange
        int numeroDivisao = 2;
        int resultadoEsperado = 1;

        //act
        calculadora.dividir(numeroDivisao);
        int resultadoObtido = calculadora.getMemoria();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * Testa a divisão de um número inteiro negativo
     * <p>
     * <b>Cenário : </b> Dividir um número inteiro negativo
     * </p>
     * <p>
     * <b>Entrada : </b>
     * </p>
     * <p>
     * - numeroDivisao = -2
     * </p>
     * <p>
     * <b>Saída : </b>-1
     * </p>
     */
    @Test
    public void testarDividirNumeroInteiroNegativo() throws Exception{
        //arrange
        int numeroDivisao = -2;
        int resultadoEsperado = -1;

        //act
        calculadora.dividir(numeroDivisao);
        int resultadoObtido = calculadora.getMemoria();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * Testa a divisão de um número inteiro por zero
     * <p>
     * <b>Cenário : </b> Dividir um número inteiro por zero
     * </p>
     * <p>
     * <b>Entrada : </b>
     * </p>
     * <p>
     * - numeroDivisao = 0
     * </p>
     * <p>
     * <b>Saída : </b>"Divisão por zero!!!"
     * </p>
     */
    @Test
    public void testarDividirPorZero() {
        // arrange
        int numeroDivisao = 0;
        String mensagemEsperada = "Divisão por zero!!!";

        // act & assert
        Exception exception = assertThrows(Exception.class, () -> calculadora.dividir(numeroDivisao));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    /**
     * Testa a exponenciação de um número inteiro por expoente = 1
     * <p>
     * <b>Cenário : </b> Exponenciar um número inteiro por 1
     * </p>
     * <p>
     * <b>Entrada : </b>
     * </p>
     * <p>
     * - expoente = 1
     * </p>
     * <p>
     * <b>Saída : </b>3
     * </p>
     */
    @Test
    public void testarExponenciacaoPorUm() throws Exception{
        //arrange
        int expoente = 1;
        int resultadoEsperado = 3;

        //act
        calculadora.exponenciar(expoente);
        int resultadoObtido = calculadora.getMemoria();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * Testa a exponenciação de um número inteiro por expoente = 10
     * <p>
     * <b>Cenário : </b> Exponenciar um número inteiro por 10
     * </p>
     * <p>
     * <b>Entrada : </b>
     * </p>
     * <p>
     * - expoente = 10
     * </p>
     * <p>
     * <b>Saída : </b>59049
     * </p>
     */
    @Test
    public void testarExponenciacaoPorDez() throws Exception{
        //arrange
        int expoente = 10;
        int resultadoEsperado = 59049;

        //act
        calculadora.exponenciar(expoente);
        int resultadoObtido = calculadora.getMemoria();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * Testa a exponenciação de um número inteiro por expoente = 20
     * <p>
     * <b>Cenário : </b> Exponenciar um número inteiro por 20
     * </p>
     * <p>
     * <b>Entrada : </b>
     * </p>
     * <p>
     * - expoente = 20
     * </p>
     * <p>
     * <b>Saída : </b>"Expoente incorreto, valor máximo é 10."
     * </p>
     */
    @Test
    public void testarExponenciacaoPorVinte() throws Exception{
        //arrange
        int expoente = 20;
        String mensagemEsperada = "Expoente incorreto, valor máximo é 10.";

        //act & assign
        Exception exception = assertThrows(Exception.class, () -> calculadora.exponenciar(expoente));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    /**
     * Testa o método para zerar a memória
     * <p>
     * <b>Cenário : </b> Utilizar o método calculadora.zerarMemoria() para zerar a memória do programa
     * </p>
     * <p>
     * <b>Entrada : </b>
     * </p>
     * <p>
     * - calculadora.zerarMemoria()
     * </p>
     * <p>
     * <b>Saída : </b>0
     * </p>
     */
    @Test
    public void testarMetodoParaZerarAMemoria() {
        //arrange
        int resultadoEsperado = 0;

        //act
        calculadora.zerarMemoria();
        int resultadoObtido = calculadora.getMemoria();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }
}
