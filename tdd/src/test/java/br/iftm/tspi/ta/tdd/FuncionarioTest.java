package br.iftm.tspi.ta.tdd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FuncionarioTest {
    private Funcionario funcionario;

    @BeforeEach
    public void instanciarObjeto() {
        funcionario = new Funcionario();
    }

    /**
     * <p><b>Cenário:</b> Testa o construtor com horas trabalhadas válidas fora do limite</p>
     * <p><b>Entrada:</b> 30 </p>
     * <p><b>Saída esperada:</b> 30 </p>
     */
    @Test
    @DisplayName("Caso de teste que verifica a quantidade de horas trabalhadas validas")
    public void testarConstrutorComHorasTrabalhadasValidasForaDoLimite() {
        //arrange
        int entradaValida = 30;
        int resultadoEsperado = 30;

        //act
        funcionario = new Funcionario("Nome Default", entradaValida, 91.08);
        int resultadoObtido = funcionario.getHorasTrabalhadas();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * <p><b>Cenário:</b> Testa o construtor com horas trabalhadas válidas no limite inferior</p>
     * <p><b>Entrada:</b> 20 </p>
     * <p><b>Saída esperada:</b> 20 </p>
     */
    @Test
    @DisplayName("Caso de teste que verifica a quantidade de horas trabalhadas validas no limite inferior")
    public void testarConstrutorComHorasTrabalhadasValidasNoLimiteInferior() {
        //arrange
        int entradaValida = 20;
        int resultadoEsperado = 20;

        //act
        funcionario = new Funcionario("Nome Default", entradaValida, 91.08);
        int resultadoObtido = funcionario.getHorasTrabalhadas();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * <p><b>Cenário:</b> Testa o construtor com horas trabalhadas válidas no limite superior</p>
     * <p><b>Entrada:</b> 40 </p>
     * <p><b>Saída esperada:</b> 40 </p>
     */
    @Test
    @DisplayName("Caso de teste que verifica a quantidade de horas trabalhadas validas no limite superior")
    public void testarConstrutorComHorasTrabalhadasValidasNoLimiteSuperior() {
        //arrange
        int entradaValida = 40;
        int resultadoEsperado = 40;

        //act
        funcionario = new Funcionario("Nome Default", entradaValida, 91.08);
        int resultadoObtido = funcionario.getHorasTrabalhadas();
        
        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * <p><b>Cenário:</b> Testa o construtor com horas trabalhadas inválida</p>
     * <p><b>Entrada:</b> 7 </p>
     * <p><b>Saída esperada:</b> "O funcionário deve trabalhar entre 20 e 40 horas." </p>
     */
    @Test
    @DisplayName("Caso de teste que verifica a quantidade de horas trabalhadas inválida")
    public void testarConstrutorComHorasTrabalhadasInvalidas() {
        //arrange
        int entradaInvalida = 7;
        String resultadoEsperado = "O funcionário deve trabalhar entre 20 e 40 horas.";
        funcionario = new Funcionario("Nome Default", entradaInvalida, 91.08);

        //act & assign
        Exception ex = assertThrows(Exception.class, () -> {
            funcionario.validaHorasTrabalhadas(entradaInvalida);
        });
        assertEquals(resultadoEsperado, ex.getMessage());
    }

    /**
     * <p><b>Cenário:</b> Testa o construtor com valor das horas trabalhadas fora do limite</p>
     * <p><b>Entrada:</b> 90.08 </p>
     * <p><b>Saída esperada:</b> 90.08 </p>
     */
    @Test
    @DisplayName("Caso de teste que verifica o valor da hora trabalhada fora do limite")
    public void testarContrutorComValorHoraValidoForaDoLimite() {
        //arrange
        double entradavalida = 90.08;
        double resultadoEsperado = 90.08;

        //act
        funcionario = new Funcionario("Nome Default", 30, entradavalida);
        double resultadoObtido = funcionario.getValorHora();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * <p><b>Cenário:</b> Testa o construtor com valor das horas trabalhadas no limite superior</p>
     * <p><b>Entrada:</b> 151.80 </p>
     * <p><b>Saída esperada:</b> 151.80 </p>
     */
    @Test
    @DisplayName("Caso de teste que verifica o valor da hora trabalhada no limite superior")
    public void testarContrutorComValorHoraValidoNoLimiteSuperior() {
        //arrange
        double entradavalida = 151.80;
        double resultadoEsperado = 151.80;

        //act
        funcionario = new Funcionario("Nome Default", 30, entradavalida);
        double resultadoObtido = funcionario.getValorHora();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * <p><b>Cenário:</b> Testa o construtor com valor das horas trabalhadas inválida</p>
     * <p><b>Entrada:</b> 170 </p>
     * <p><b>Saída esperada:</b> "O valor da hora trabalhada não pode ser inferior a 1% e nem superior a 10% do salário mínimo" </p>
     */
    @Test
    @DisplayName("Caso de teste que verifica o valor da hora trabalhada inválida")
    public void testarContrutorComValorHoraInvalido() {
        //arrange
        double entradaInvalida = 170;
        String resultadoEsperado = "O valor da hora trabalhada não pode ser inferior a 1% e nem superior a 10% do salário mínimo";

        //act & assign
        funcionario = new Funcionario("Nome Default", 30, entradaInvalida);
        Exception ex = assertThrows(Exception.class, () -> {
            funcionario.validarValorHora(entradaInvalida);
        });
        assertEquals(resultadoEsperado, ex.getMessage());
    }

    /**
     * <p><b>Cenário:</b> Testa o cálculo do pagamento</p>
     * <p><b>Entradas:</b> horasTrabalhadas = 170 ; valorHora: 90.08</p>
     * <p><b>Saída esperada:</b> 10809.60 </p>
     */
    @Test
    @DisplayName("Caso teste que verifica o cálculo do pagamento.")
    public void testarOCalculoDoPagamento() {
        //arrange
        int horasTrabalhadas = 30;
        double valorHora = 90.08;
        double resultadoEsperado = 10809.60;

        //act
        funcionario = new Funcionario("Nome Default", horasTrabalhadas, valorHora);
        double resultadoObtido = funcionario.calcularPagamento();

        //assign
        assertEquals(resultadoEsperado, resultadoObtido);
    }

    /**
     * <p><b>Cenário:</b> Testa o cálculo do pagamento acima de R$ 15.000</p>
     * <p><b>Entradas:</b> horasTrabalhadas = 40 ; valorHora: 136.62</p>
     * <p><b>Saída esperada:</b> "O pagamento não pode ultrapassar R$ 15.000,00" </p>
     */
    @Test
    @DisplayName("Caso teste que verifica o valor de pagamento acima de R$ 15.000")
    public void testarOCalculoDoPagamentoAcimaDoLimite () {
        //arrange
        int horasTrabalhadas = 40;
        double valorHora = 136.62;
        String resultadoEsperado = "O pagamento não pode ultrapassar R$ 15.000,00";

        //act & assign
        funcionario = new Funcionario("Nome default", horasTrabalhadas, valorHora);
        Exception ex = assertThrows(Exception.class, () -> {
            funcionario.calcularPagamento();
        });
        assertEquals(resultadoEsperado, ex.getMessage());
    }

    /**
     * <p><b>Cenário:</b> Testa o cálculo do pagamento abaixo de R$ 1518</p>
     * <p><b>Entradas:</b> horasTrabalhadas = 20 ; valorHora: 15.18</p>
     * <p><b>Saída esperada:</b> "O pagamento não pode ser menor que 1 salário mínimo" </p>
     */
    @Test
    @DisplayName("Caso teste que verifica o valor de pagamento abaixo de R$ 1518,00")
    public void testarOCalculoDoPagamentoAbaixoDoLimite () {
        //arrange
        int horasTrabalhadas = 20;
        double valorHora = 15.18;
        String resultadoEsperado = "O pagamento não pode ser menor que 1 salário mínimo";

        //act & assign
        funcionario = new Funcionario("Nome default", horasTrabalhadas, valorHora);
        Exception ex = assertThrows(Exception.class, () -> {
            funcionario.calcularPagamento();
        });
        assertEquals(resultadoEsperado, ex.getMessage());
    }

}
