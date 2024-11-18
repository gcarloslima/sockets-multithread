package com.relgs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class Cartao {
    private static final Logger logger = LogManager.getLogger(Cartao.class);

    private final String numero;
    private final AtomicInteger saldo;

    public Cartao(String numero, int saldoInicial) {
        this.numero = numero;
        this.saldo = new AtomicInteger(saldoInicial);
        logger.info("Cartão criado: Número={} SaldoInicial={}", numero, saldoInicial);
    }

    public String getNumero() {
        return numero;
    }

    public int getSaldo() {
        return saldo.get();
    }

    public boolean debitar(int valor) {
        if (saldo.get() >= valor) {
            saldo.addAndGet(-valor);
            logger.info("Débito realizado: Número={} Valor={} SaldoAtual={}", numero, valor, saldo.get());
            return true;
        }
        logger.warn("Saldo insuficiente: Número={} Valor={} SaldoAtual={}", numero, valor, saldo.get());
        return false;
    }
}