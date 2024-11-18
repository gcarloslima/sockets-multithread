package com.relgs;

public class Parser {

    private Parser() {
        throw new IllegalStateException("Utility class");
    }

    public static String processarMensagem(String mensagem, CartaoService service) {
        if (mensagem.length() < 28) {
            return "0210" + "05" + "0000";
        }

        try {
            String numeroCartao = mensagem.substring(16, 28);
            int valor = Integer.parseInt(mensagem.substring(4, 16).trim());
            return service.processarTransacao(numeroCartao, valor);
        } catch (NumberFormatException e) {
            return "0210" + "05" + "0000";
        }
    }
}