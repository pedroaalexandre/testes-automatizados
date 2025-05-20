package com.example.atividadea4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.atividadea4.entities.Client;
import com.example.atividadea4.repositories.ClientRepository;

@DataJpaTest
class ClientRepositoryTests {

	@Autowired
	private ClientRepository clientRepository;

	/**
	 * <b>Testar buscar cliente por nome existente</b>
	 * <p>Entrada: "Jose"</p>
	 * <p>Saída: Lista de clientes com nome "Jose"</p>
	 * <p>Descrição: Este teste verifica se a busca por clientes com o nome "Jose" retorna uma lista não vazia.</p>
	 */
	@Test
	@DisplayName("Testar buscar cliente por nome existente")
	void testarBuscarClienteComNomeExistente() {
		//assign
		String nomeEsperado = "Jose";

		//act
		List<Client> clientes = clientRepository.findByNameContainingIgnoreCase(nomeEsperado);

		//assert
		assertFalse(clientes.isEmpty());
	}

	/**
	 * <b>Testar buscar cliente por nome inexistente</b>
	 * <p>Entrada: "Pedro"</p>
	 * <p>Saída: Lista de clientes com nome "Pedro"</p>
	 * <p>Descrição: Este teste verifica se a busca por clientes com o nome "Pedro" retorna uma lista vazia, 
	 * pois o cliente Pedro não existe.</p>
	 */
	@Test
	@DisplayName("Testar buscar cliente por nome existente")
	void testarBuscarClienteComNomeInexistente() {
		//assign
		String nomeEsperado = "Pedro";

		//act
		List<Client> clientes = clientRepository.findByNameContainingIgnoreCase(nomeEsperado);

		//assert
		assertTrue(clientes.isEmpty());
	}

	/**
	 * <b>Testar buscar cliente com renda maior que um valor específico</b>
	 * <p>Entrada: 2000.0</p>
	 * <p>Saída: Lista de clientes com salário maior que 2000.0</p>
	 * <p>Descrição: Este teste verifica se a busca por clientes com salário maior que 2000.0 retorna uma lista não vazia.</p>
	 */
	@Test
	@DisplayName("Testar buscar cliente por renda maior que um valor específico")
	void testarBuscarClienteComRendaMaiorQueValorEspecifico() {
		//assign
		Double rendaEsperada = 2000.0;

		//act
		List<Client> clientes = clientRepository.findByIncomeGreaterThan(rendaEsperada);

		//assert
		assertFalse(clientes.isEmpty());
	}

	/**
	 * <b>Testar excluir cliente pelo id</b>
	 * <p>Entrada: 1L</p>
	 * <p>Saída: Cliente com id 1L não deve existir mais</p>
	 * <p>Descrição: Este teste verifica se a exclusão de um cliente pelo id 1L remove o cliente do banco de dados.</p>
	 */
	@Test
	@DisplayName("Testar a exlusão de cliente pelo id")
	void testarExclusaoClientePeloId() {
		//assign
		Long idCliente = 1L;
		Long tamanhoBDPosExclusao = clientRepository.count() - 1;

		//act
		clientRepository.deleteById(idCliente);
		Optional<Client> clientes = clientRepository.findById(idCliente);

		//assert
		assertTrue(clientes.isEmpty());
		assertEquals(tamanhoBDPosExclusao, clientRepository.count());
	}

}
