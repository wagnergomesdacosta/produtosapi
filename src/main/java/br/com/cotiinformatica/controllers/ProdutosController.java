package br.com.cotiinformatica.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cotiinformatica.requests.ProdutoPostRequest;
import br.com.cotiinformatica.requests.ProdutoPutRequest;

@Controller
public class ProdutosController {

	@RequestMapping(value = "/api/produtos", method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody ProdutoPostRequest request) {

		return ResponseEntity.status(HttpStatus.OK).body("Produto cadastrado com sucesso");

	}
	
	@RequestMapping(value = "/api/produtos", method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody ProdutoPutRequest request) {
		
		return ResponseEntity.status(HttpStatus.OK).body("Produto atualisado com sucesso");
		
	}
	
	@RequestMapping(value = "/api/produtos/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("id") Integer id){
		
		return ResponseEntity.status(HttpStatus.OK).body("Produto excluido com sucesso");
	}

	@RequestMapping(value = "/api/produtos", method = RequestMethod.GET)
	public ResponseEntity<String> get(){
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
}
