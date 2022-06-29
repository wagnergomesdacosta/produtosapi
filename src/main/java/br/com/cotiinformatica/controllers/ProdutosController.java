package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cotiinformatica.entities.Fornecedor;
import br.com.cotiinformatica.entities.Produto;
import br.com.cotiinformatica.repositories.IFornecedorRepository;
import br.com.cotiinformatica.repositories.IProdutoRepository;
import br.com.cotiinformatica.requests.ProdutoPostRequest;
import br.com.cotiinformatica.requests.ProdutoPutRequest;
import br.com.cotiinformatica.responses.ProdutoGetResponse;

@Transactional // habilitar o uso de repositórios da JPA
@Controller
public class ProdutosController {

	@Autowired // autoinicialização
	private IProdutoRepository produtoRepository;

	@Autowired // autoinicialização
	private IFornecedorRepository fornecedorRepository;

	@RequestMapping(value = "/api/produtos", method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody ProdutoPostRequest request) {

		try {

			// obter o fornecedor através do banco de dados
			Optional<Fornecedor> consulta = fornecedorRepository.findById(request.getIdFornecedor());

			// verificar se o fornecedor não foi encontrado
			if (consulta.isEmpty()) {

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fornecedor não encontrado.");
			}

			Produto produto = new Produto();

			produto.setNome(request.getNome());
			produto.setDescricao(request.getDescricao());
			produto.setPreco(request.getPreco());
			produto.setQuantidade(request.getQuantidade());
			produto.setFornecedor(consulta.get());

			produtoRepository.save(produto);

			return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso");

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	@RequestMapping(value = "/api/produtos", method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody ProdutoPutRequest request) {

		try {

			// obter o fornecedor através do banco de dados
			Optional<Fornecedor> consulta = fornecedorRepository.findById(request.getIdFornecedor());

			// verificar se o fornecedor não foi encontrado
			if (consulta.isEmpty()) {

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fornecedor não encontrado.");

			}

			Produto produto = new Produto();

			produto.setIdProduto(request.getIdProduto());
			produto.setNome(request.getNome());
			produto.setDescricao(request.getDescricao());
			produto.setPreco(request.getPreco());
			produto.setQuantidade(request.getQuantidade());
			produto.setFornecedor(consulta.get());

			produtoRepository.save(produto);

			return ResponseEntity.status(HttpStatus.OK).body("Produto atualizado com sucesso");

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	@RequestMapping(value = "/api/produtos/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("id") Integer id) {

		try {

			// consultando o produto no banco de dados através do ID
			Optional<Produto> consulta = produtoRepository.findById(id);

			// verificando se o produto foi encontrado
			if (consulta.isPresent()) {

				Produto produto = consulta.get();
				produtoRepository.delete(produto);

				return ResponseEntity.status(HttpStatus.OK).body("Produto excluído com sucesso");
			} else {

				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Produto não encontrado, verifique o ID informado");

			}

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	@RequestMapping(value = "/api/produtos", method = RequestMethod.GET)
	public ResponseEntity<List<ProdutoGetResponse>> getAll() {

		try {

			// obtendo uma lista de produtos
			List<Produto> produtos = (List<Produto>) produtoRepository.findAll();

			List<ProdutoGetResponse> lista = new ArrayList<ProdutoGetResponse>();

			// percorrer os produtos obtidos de dados
			for (Produto produto : produtos) {

				ProdutoGetResponse response = new ProdutoGetResponse();

				response.setIdProduto(produto.getIdProduto());
				response.setNome(produto.getNome());
				response.setPreco(produto.getPreco());
				response.setQuantidade(produto.getQuantidade());
				response.setTotal(produto.getPreco() * produto.getQuantidade());
				response.setDescricao(produto.getDescricao());
				response.setIdFornecedor(produto.getFornecedor().getIdFornecedor());
				response.setNomeFornecedor(produto.getFornecedor().getNome());
				response.setCnpjFornecedor(produto.getFornecedor().getCnpj());

				lista.add(response);

			}

			return ResponseEntity.status(HttpStatus.OK).body(lista);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@RequestMapping(value = "/api/produtos/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProdutoGetResponse> getById(@PathVariable("id") Integer id) {

		try {

			// consultando 1 produto através do ID
			Optional<Produto> consulta = produtoRepository.findById(id);

			// verificando se o produto foi encontrado
			if (consulta.isPresent()) {

				// capturando o produto obtido na consulta
				Produto produto = consulta.get();

				ProdutoGetResponse response = new ProdutoGetResponse();
				
				response.setIdProduto(produto.getIdProduto());
				response.setNome(produto.getNome());
				response.setPreco(produto.getPreco());
				response.setQuantidade(produto.getQuantidade());
				response.setTotal(produto.getPreco() * produto.getQuantidade());
				response.setDescricao(produto.getDescricao());
				response.setIdFornecedor(produto.getFornecedor().getIdFornecedor());
				response.setNomeFornecedor(produto.getFornecedor().getNome());
				response.setCnpjFornecedor(produto.getFornecedor().getCnpj());
				

				return ResponseEntity.status(HttpStatus.OK).body(response);

			} else {

				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

			}

		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}