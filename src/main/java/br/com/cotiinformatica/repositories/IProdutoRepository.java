package br.com.cotiinformatica.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.cotiinformatica.entities.Produto;

//entre <> põe-se a entidade que será feita o CRUD e o tipo da primary krey
public interface IProdutoRepository extends CrudRepository<Produto, Integer>{

}



