package com.relgs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) throws Exception {
        try (Socket conexao = new Socket("127.0.0.1", 2001);
             DataInputStream entrada = new DataInputStream(conexao.getInputStream());
             DataOutputStream saida = new DataOutputStream(conexao.getOutputStream())) {

            for (String mensagem : SimuladorDeTransacoes.getTransacoes()) {
                saida.writeUTF(mensagem);
                String resposta = entrada.readUTF();
                System.out.println("Resposta: " + resposta);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
