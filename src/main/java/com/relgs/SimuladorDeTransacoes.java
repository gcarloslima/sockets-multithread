package com.relgs;

import java.util.List;

public class SimuladorDeTransacoes {
    private SimuladorDeTransacoes() {
        throw new IllegalStateException("Utility class");
    }

    public static List<String> getTransacoes() {
        return List.of(
                "0200000000000200401231021845", // Transação válida
                "0200000000500000401231021846", // Saldo suficiente
                "0200000000300000401231021847", // Cartão inexistente
                "0200000000700000401231021845", // Saldo insuficiente
                "020invalidFormat01231021845"  // Formato inválido
        );
    }
}