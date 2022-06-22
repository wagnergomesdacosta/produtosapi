package br.com.cotiinformatica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity // (ORM)
@Table(name = "produto") // (ORM)
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

	@Id // define a primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // define o campo com auto incremento
	@Column(name = "idproduto") // define as caracteristicas da coluna da tabela
	private Integer idProduto;

	@Column(name = "nome", length = 150, nullable = false)
	private String nome;

	@Column(name = "descricao", length = 500, nullable = false)
	private String descricao;

	@Column(name = "preco", nullable = false)
	private Double preco;

	@Column(name = "quantidade", nullable = false)
	private Integer quantidade;
	
	@ManyToOne //MUITOS PRODUTOS PARA 1 FORNECEDOR
	@JoinColumn(name = "idfornecedor")
	private Fornecedor fornecedor;

}
