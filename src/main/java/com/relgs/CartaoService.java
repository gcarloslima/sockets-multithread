package com.relgs;

import com.sun.tools.javac.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CartaoService {
    private static final Logger logger = LogManager.getLogger(CartaoService.class);

    private final Map<String, Cartao> cartoes = new ConcurrentHashMap<>();
    private final AtomicInteger nsuGenerator = new AtomicInteger(1000);

    public CartaoService() {
        cartoes.put("401231021845", new Cartao("401231021845", 5000));
        cartoes.put("401231021846", new Cartao("401231021846", 2000));
        logger.info("Serviço inicializado com cartões padrão.");
    }

    public String processarTransacao(String numeroCartao, int valor) {
        Cartao cartao = cartoes.get(numeroCartao);

        if (cartao == null) {
            logger.warn("Tentativa com cartão inexistente: Número={}", numeroCartao);
            return criarResposta("05", "0000");
        }

        synchronized (cartao) {
            if (!cartao.debitar(valor)) {
                logger.warn("Transação recusada por saldo insuficiente: Número={} Valor={}", numeroCartao, valor);
                return criarResposta("51", "0000");
            }
        }

        String nsu = gerarNSU();
        logger.info("Transação aprovada: Número={} Valor={} NSU={}", numeroCartao, valor, nsu);
        return criarResposta("00", nsu);
    }

    private String criarResposta(String codigoResposta, String nsu) {
        return String.format("0210%s%s", codigoResposta, nsu);
    }

    private String gerarNSU() {
        return String.format("%08d", nsuGenerator.getAndIncrement());
    }
}