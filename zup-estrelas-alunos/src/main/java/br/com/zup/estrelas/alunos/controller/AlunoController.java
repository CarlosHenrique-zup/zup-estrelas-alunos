package br.com.zup.estrelas.alunos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.estrelas.alunos.entity.Aluno;
import br.com.zup.estrelas.alunos.repository.AlunoRepository;

@RestController

@RequestMapping("/alunos")
public class AlunoController {

	@Autowired
	AlunoRepository repository;

	@PostMapping
	public String insereAluno(@RequestBody Aluno aluno) {
		this.repository.save(aluno);
		return "Requisição feita com sucesso";
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Aluno> buscaAlunos() {
		return (List<Aluno>) repository.findAll();
	}

	@GetMapping(path = "/{matricula}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Aluno buscaAluno(@PathVariable Long matricula) {
		return repository.findById(matricula).get();
	}
	
	@GetMapping(path = "/cpf/{cpf}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Aluno buscaAlunoCpf(@PathVariable String cpf) {
		return repository.findByCpf(cpf);
	}
	
	@DeleteMapping(path = "/cpf/{cpf}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Aluno deletaAlunoCpf(@PathVariable String cpf) {
		return repository.deleteByCpf(cpf);
	}
	
	@PutMapping(path = "/{matricula}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public void alunoAtualizado(@PathVariable("matricula") Long matricula, @RequestBody Aluno aluno) throws Exception {
		var alunos = repository.findById(matricula);
		
		if(alunos.isPresent()) {
			var alunoAtual = alunos.get();
			alunoAtual.setNome(aluno.getNome());
			alunoAtual.setMatricula(aluno.getMatricula());
			alunoAtual.setCpf(aluno.getCpf());
			alunoAtual.setDataNascimento(aluno.getDataNascimento());
			alunoAtual.setValorMensalidade(aluno.getValorMensalidade());
			repository.save(alunoAtual);
		}else {
			throw new Exception("Aluno não encontrado!");
		}
	}
	
//	@DeleteMapping(path = "/{matricula}", produces = {MediaType.APPLICATION_JSON_VALUE})
//	public Aluno deletaAlunoCpf(@PathVariable Long matricula) {
//		return repository.delete(entity);;
//	}
	
	

//	@GetMapping(path = "/html", produces = {MediaType.TEXT_HTML_VALUE})
//	public String carregaPage() {
//		return this.htmlInStr;
//	}
}
