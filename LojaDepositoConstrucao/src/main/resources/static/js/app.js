/*
 * LojaDepositoConstrucao - comportamentos da interface.
 * Sem dependências externas.
 */
(function () {
    'use strict';

    var formatadorMoeda = new Intl.NumberFormat('pt-BR', {style: 'currency', currency: 'BRL'});

    /* ---------- Confirmação antes de enviar formulários ---------- */
    document.querySelectorAll('form[data-confirmar]').forEach(function (formulario) {
        formulario.addEventListener('submit', function (evento) {
            if (!window.confirm(formulario.dataset.confirmar)) {
                evento.preventDefault();
            }
        });
    });

    /* ---------- Linhas dinâmicas (telefones, endereços e itens de venda) ---------- */

    // renumera os campos para que o Spring faça o bind correto das listas
    function reindexar(lista) {
        lista.querySelectorAll('.linha-dinamica').forEach(function (linha, indice) {
            linha.querySelectorAll('[data-nome]').forEach(function (campo) {
                campo.name = campo.dataset.nome.replace('{i}', indice);
            });
        });
    }

    function linhaVazia(linha) {
        var campos = linha.querySelectorAll('input, select');
        return Array.prototype.every.call(campos, function (campo) {
            return campo.value.trim() === '';
        });
    }

    function atualizarMensagemVazia(lista) {
        var aviso = lista.parentElement.querySelector('.sem-itens');
        if (aviso) {
            aviso.hidden = lista.querySelectorAll('.linha-dinamica').length > 0;
        }
    }

    document.addEventListener('click', function (evento) {
        var alvo = evento.target.closest('[data-acao]');
        if (!alvo) {
            return;
        }

        if (alvo.dataset.acao === 'adicionar-linha') {
            var lista = document.getElementById(alvo.dataset.lista);
            var modelo = document.getElementById(alvo.dataset.modelo);
            lista.appendChild(modelo.content.cloneNode(true));
            reindexar(lista);
            atualizarMensagemVazia(lista);
            lista.dispatchEvent(new Event('change', {bubbles: true}));
        }

        if (alvo.dataset.acao === 'remover-linha') {
            var linha = alvo.closest('.linha-dinamica');
            var listaPai = linha.parentElement;
            linha.remove();
            reindexar(listaPai);
            atualizarMensagemVazia(listaPai);
            listaPai.dispatchEvent(new Event('change', {bubbles: true}));
        }
    });

    /* ---------- Envio de formulários com listas dinâmicas ---------- */
    document.querySelectorAll('form[data-listas-dinamicas]').forEach(function (formulario) {
        formulario.addEventListener('submit', function () {
            formulario.querySelectorAll('.lista-dinamica').forEach(function (lista) {
                // descarta linhas totalmente em branco
                lista.querySelectorAll('.linha-dinamica').forEach(function (linha) {
                    if (linhaVazia(linha)) {
                        linha.remove();
                    }
                });
                reindexar(lista);
            });
        });
    });

    document.querySelectorAll('.lista-dinamica').forEach(function (lista) {
        reindexar(lista);
        atualizarMensagemVazia(lista);
    });

    /* ---------- Tela de venda: total, aviso de cliente novo e confirmação ---------- */
    var formularioVenda = document.getElementById('formulario-venda');
    if (formularioVenda) {
        var listaItens = document.getElementById('lista-itens');
        var campoTotal = document.getElementById('total-venda');
        var selectCliente = document.getElementById('idCliente');
        var campoData = document.getElementById('dataPagamento');
        var avisoData = document.getElementById('aviso-data');

        var calcularTotal = function () {
            var total = 0;
            listaItens.querySelectorAll('.linha-dinamica').forEach(function (linha) {
                var select = linha.querySelector('select');
                var quantidade = parseInt(linha.querySelector('input').value, 10);
                var opcao = select.options[select.selectedIndex];
                var preco = opcao && opcao.dataset.preco ? parseFloat(opcao.dataset.preco) : 0;
                var subtotal = (isNaN(quantidade) || quantidade < 0) ? 0 : preco * quantidade;
                var campoSubtotal = linha.querySelector('.subtotal');
                if (campoSubtotal) {
                    campoSubtotal.textContent = formatadorMoeda.format(subtotal);
                }
                total += subtotal;
            });
            campoTotal.textContent = formatadorMoeda.format(total);
            return total;
        };

        var atualizarExigenciaPagamento = function () {
            var opcao = selectCliente.options[selectCliente.selectedIndex];
            var exige = opcao && opcao.dataset.exigePagamento === 'true';
            campoData.required = exige;
            avisoData.hidden = !exige;
        };

        formularioVenda.addEventListener('input', calcularTotal);
        formularioVenda.addEventListener('change', calcularTotal);
        selectCliente.addEventListener('change', atualizarExigenciaPagamento);

        formularioVenda.addEventListener('submit', function (evento) {
            var total = calcularTotal();
            var mensagem = 'O valor total da venda é ' + formatadorMoeda.format(total) + '. Deseja continuar?';
            if (!window.confirm(mensagem)) {
                evento.preventDefault();
            }
        });

        calcularTotal();
        atualizarExigenciaPagamento();
    }
})();
