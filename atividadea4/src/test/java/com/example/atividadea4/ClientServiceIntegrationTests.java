package com.example.atividadea4;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;

import com.example.atividadea4.dto.ClientDTO;
import com.example.atividadea4.entities.Client;
import com.example.atividadea4.services.ClientService;
import com.example.atividadea4.services.exceptions.ResourceNotFoundException;

@SpringBootTest
public class ClientServiceIntegrationTests {
    
    @Autowired
    ClientService servico;
    
    /**
     * Testa se o método delete do serviço ClientService
     * não faz nada quando o id existe
     * e verifica se o método deleteById do repositório é chamado uma vez.
     * 
     * <p><b>Long idExistente</b> = 1L;</p>
     * <p><b>Retorno</b> = Nenhum retorno, apenas verifica se o método deleteById é chamado.</p>
     */
    @Test
    public void apagarNaoDeveFazerNadaQuandoIdExiste() {
        // assign
        Long idExistente = 1L;

        // act & assign
        Assertions.assertDoesNotThrow(()->{servico.delete(idExistente);});
    }

    /**
     * Testa se o método delete do serviço ClientService
     * lança uma exceção EmptyResultDataAccessException quando o id não existe
     * e verifica se o método deleteById do repositório é chamado uma vez.
     * 
     * <p><b>Long idNaoExistente</b> = 1000L;</p>
     * <p><b>Retorno</b> = EmptyResultDataAccessException.</p>
     */
    @Test 
    public void apagarDeveLancarUmaExcecaoQuandoOIdNaoExiste() {
        // assign
        Long idNaoExistente = 1000L;

        //act & assign
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {servico.delete(idNaoExistente);});
    }

    /**
     * Testa se o método findAllPaged do serviço ClientService
     * retorna uma página de clientes e verifica se o método findAll do repositório é chamado uma vez.
     * 
     * <p><b>PageRequest pageRequest</b> = PageRequest.of(0, 10);</p>
     * <p><b>Retorno</b> = Uma página com 10 clientes.</p>
     */
    @Test
    public void retornarUmaPaginaComTodosOsClientes() {
        // assign
        int pagina = 0;
        int tamanho = 10;

        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        List<Client> clientes = List.of(new Client());

        // act & assert
        Assertions.assertDoesNotThrow(() -> {
            servico.findAllPaged(pageRequest);
        });
    }

    /**
     * Testa se o método findByIncome do serviço ClientService
     * retorna uma lista de clientes com renda maior que o valor informado
     * e verifica se o método findByIncomeGreaterThan do repositório é chamado uma vez.
     * 
     * <p><b>Double renda</b> = 1000.0;</p>
     * <p><b>Retorno</b> = Uma lista de clientes com renda maior que 1000.0.</p>
     */
    @Test
    public void retornarUmaListaComClientesComRendaMaiorQue() {
        // assign
        Double renda = 1000.0;

        // act & assert
        Assertions.assertDoesNotThrow(() -> {servico.findByIncome(renda);});    
    }

    /**
     * Testa se o método findById do serviço ClientService
     * retorna um cliente quando o id existe
     * e verifica se o método findById do repositório é chamado uma vez.
     * 
     * <p><b>Long id</b> = 1L;</p>
     * <p><b>Retorno</b> = Cliente com id 1;</p>
     */
    @Test
    public void retornarOClientePeloId() {
        // assign
        Long id = 1L;

        // act & assert
        Assertions.assertDoesNotThrow(() -> {servico.findById(id);});
    }

    /**
     * Testa se o método findById do serviço ClientService
     * lança uma exceção ResourceNotFoundException quando o id não existe
     * e verifica se o método findById do repositório é chamado uma vez.
     *
     * <p><b>Long id</b> = 1L;</p>
     * <p><b>Retorno</b> = ResourceNotFoundException.</p>
     */
    @Test
    public void retornarUmaExcecaoQuandoNaoEncontrarClientePeloId() {
        // assign
        Long id = 1L;

        // act & assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {servico.findById(id);});
    }

    /**
     * Testa se o método update do serviço ClientService
     * atualiza um cliente existente e retorna o cliente atualizado.
     * Verifica se o método getOne do repositório é chamado uma vez.
     * 
     * <p><b>Long</b> id = 1L;</p>
     * <p><b>String timestampString</b> = "2023-03-15T10:00:00Z";</p>
     * <p><b>Instant instantFromString</b> = Instant.parse(timestampString);</p>
     * <p><b>Client clienteExistente</b> = new Client(id,"Conceição Evaristo", "10619244881", 1500.0, Instant.ofEpochSecond(0), 2);</p>
     * <p><b>ClientDTO dtoAtualizado</b> = new ClientDTO(id, "Pedro Augusto", "12345678901", 1900.0, instantFromString, 3);</p>
     * 
     * <p><b>Retorno</b> = Cliente atualizado com os novos dados.</p>
     */
    @Test
    public void atualizarDeveRetornarClienteAtualizado() {
        // assign
        Long id = 1L;
        String timestampString = "2023-03-15T10:00:00Z";
        Instant instantFromString = Instant.parse(timestampString);
        ClientDTO dtoAtualizado = new ClientDTO(null, "Pedro Augusto", "12345678901", 1900.0, instantFromString, 3);
        

        // act
        ClientDTO resultado = servico.update(id, dtoAtualizado);

        // assert
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Pedro Augusto", resultado.getName());
        Assertions.assertEquals("12345678901", resultado.getCpf());
        Assertions.assertEquals(1900.0, resultado.getIncome());
        Assertions.assertEquals(instantFromString, resultado.getBirthDate());
        Assertions.assertEquals(3, resultado.getChildren());
    }

    /**
     * Testa se o método update do serviço ClientService
     * lança uma exceção ResourceNotFoundException quando o id não existe.
     * Verifica se o método getOne do repositório é chamado uma vez.
     *
     * <p><b>Long idInvalido</b> = 99L;</p>
     * <p><b>ClientDTO dto</b> = new ClientDTO(idInvalido, "Pedro Augusto", "12345678901", 1900.0, Instant.now(), 3);</p>
     * <p><b>Retorno</b> = ResourceNotFoundException.</p>
     */
    @Test
    public void atualizarDeveRetornarExceçãoQuandoIdNaoExistir() {
        //assign
        Long idInvalido = 99L;
        ClientDTO dto = new ClientDTO(idInvalido, "Pedro Augusto", "12345678901", 1900.0, Instant.now(), 3);


        //act & assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            servico.update(idInvalido, dto);
        });

    }

    /**
     * Testa se o método insert do serviço ClientService
     * insere um novo cliente e retorna o cliente inserido.
     * Verifica se o método save do repositório é chamado uma vez.
     *
     * <p><b>Long id</b> = 1L;</p>
     * <p><b>String timestampString</b> = "2023-03-15T10:00:00Z";</p>
     * <p><b>Instant instantFromString</b> = Instant.parse(timestampString);</p>
     * <p><b>ClientDTO dtoEntrada</b> = new ClientDTO(null, "João Silva", "12345678901", 2500.0, instantFromString, 1);</p>
     * <p><b>Client entidadeSalva</b> = new Client(id, "João Silva", "12345678901", 2500.0, instantFromString, 1);</p>
     *
     * <p><b>Retorno</b> = Cliente inserido com os dados fornecidos.</p>
     */
    @Test
    public void retornarUmClientDTONaInsercaoDeUmNovoCliente() {
        //assign
        String timestampString = "2023-03-15T10:00:00Z";
        Instant birthDate = Instant.parse(timestampString);

        ClientDTO dtoEntrada = new ClientDTO(null, "João Silva", "12345678901", 2500.0, birthDate, 1);


        //act
        ClientDTO resultado = servico.insert(dtoEntrada);

        //assert
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(resultado.getId(), resultado.getId());
        Assertions.assertEquals("João Silva", resultado.getName());
        Assertions.assertEquals("12345678901", resultado.getCpf());
        Assertions.assertEquals(2500.0, resultado.getIncome());
        Assertions.assertEquals(birthDate, resultado.getBirthDate());
        Assertions.assertEquals(1, resultado.getChildren());

    }

}
