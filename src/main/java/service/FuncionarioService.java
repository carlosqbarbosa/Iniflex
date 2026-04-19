package com.industria.service;

import com.industria.model.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Serviço responsável por toda a lógica de negócio relacionada a funcionários.
 * Utiliza Streams e lambdas para manipulação funcional das coleções.
 */
public class FuncionarioService {

    /**
     * Remove um funcionário pelo nome (case-sensitive).
     *
     * @param funcionarios lista mutável de funcionários
     * @param nome         nome a remover
     * @return true se algum funcionário foi removido
     */
    public boolean removerPorNome(List<Funcionario> funcionarios, String nome) {
        return funcionarios.removeIf(f -> f.getNome().equals(nome));
    }

    /**
     * Aplica aumento percentual sobre o salário de todos os funcionários.
     *
     * @param funcionarios  lista de funcionários
     * @param percentual    valor percentual (ex: 10 para 10%)
     */
    public void aplicarAumento(List<Funcionario> funcionarios, double percentual) {
        BigDecimal fator = BigDecimal.valueOf(1 + percentual / 100.0);
        funcionarios.forEach(f ->
                f.setSalario(f.getSalario().multiply(fator).setScale(2, RoundingMode.HALF_UP))
        );
    }

    /**
     * Agrupa funcionários por função.
     *
     * @param funcionarios lista de funcionários
     * @return mapa função → lista de funcionários
     */
    public Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    /**
     * Filtra funcionários cujo aniversário cai nos meses informados.
     *
     * @param funcionarios lista de funcionários
     * @param meses        meses desejados (1–12)
     * @return lista filtrada
     */
    public List<Funcionario> filtrarPorMesAniversario(List<Funcionario> funcionarios, int... meses) {
        Set<Integer> mesesSet = new HashSet<>();
        for (int m : meses) mesesSet.add(m);
        return funcionarios.stream()
                .filter(f -> mesesSet.contains(f.getDataNascimento().getMonthValue()))
                .collect(Collectors.toList());
    }

    /**
     * Retorna o funcionário mais velho (maior idade).
     *
     * @param funcionarios lista de funcionários
     * @return Optional com o funcionário mais velho
     */
    public Optional<Funcionario> funcionarioMaisVelho(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .min(Comparator.comparing(f -> f.getDataNascimento()));
    }

    /**
     * Calcula a idade em anos de um funcionário em relação à data atual.
     */
    public int calcularIdade(Funcionario funcionario) {
        return LocalDate.now().getYear() - funcionario.getDataNascimento().getYear();
    }

    /**
     * Retorna a lista de funcionários em ordem alfabética pelo nome.
     */
    public List<Funcionario> ordenarAlfabeticamente(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());
    }

    /**
     * Calcula a soma de todos os salários.
     */
    public BigDecimal totalSalarios(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calcula quantos salários mínimos um funcionário recebe.
     *
     * @param salario         salário do funcionário
     * @param salarioMinimo   valor do salário mínimo
     * @return quantidade de salários mínimos (com 2 casas decimais)
     */
    public BigDecimal salariosMinimos(BigDecimal salario, BigDecimal salarioMinimo) {
        return salario.divide(salarioMinimo, 2, RoundingMode.HALF_UP);
    }
}