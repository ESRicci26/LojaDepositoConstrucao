package br.com.javaricci.infraestrutura.persistencia;

import br.com.javaricci.dominio.excecao.RegraNegocioException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

/**
 * Converte violações de integridade do banco em mensagens de negócio compreensíveis.
 */
final class TradutorErroPersistencia {

    private TradutorErroPersistencia() {
    }

    static RegraNegocioException traduzir(DataIntegrityViolationException erro) {
        String detalhe = erro.getMostSpecificCause().getMessage();
        detalhe = detalhe == null ? "" : detalhe;

        if (detalhe.contains("uk_tb_cliente_cpf")) {
            return new RegraNegocioException("Já existe um cliente cadastrado com este CPF.", erro);
        }
        if (detalhe.contains("uk_tb_cliente_rg")) {
            return new RegraNegocioException("Já existe um cliente cadastrado com este RG.", erro);
        }
        if (detalhe.contains("uk_tb_telefone_cliente")) {
            return new RegraNegocioException("Um dos telefones informados já está cadastrado.", erro);
        }
        if (detalhe.contains("uk_tb_endereco_cliente")) {
            return new RegraNegocioException("Um dos endereços informados já está cadastrado.", erro);
        }
        if (erro instanceof DuplicateKeyException) {
            return new RegraNegocioException("Já existe um registro com os mesmos dados.", erro);
        }
        return new RegraNegocioException(
                "Operação não permitida: o registro está em uso ou contém dados inválidos.", erro);
    }
}
