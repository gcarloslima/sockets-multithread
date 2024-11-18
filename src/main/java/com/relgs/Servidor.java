package com.relgs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private static final Logger logger = LogManager.getLogger(Servidor.class);
    private static volatile boolean serverRunning = true;

    public static void main(String[] args) {
        CartaoService cartaoService = new CartaoService();

        try (ServerSocket server = new ServerSocket(2001)) {
            logger.info("Servidor iniciado na porta 2001.");

            while (serverRunning) {
                Socket conexao = server.accept();
                logger.info("Cliente conectado: IP={}", conexao.getInetAddress());
                new Thread(() -> processarCliente(conexao, cartaoService)).start();
            }
        } catch (Exception e) {
            logger.error("Erro no servidor: ", e);
        }
    }

    private static void processarCliente(Socket conexao, CartaoService cartaoService) {
        try (DataInputStream entrada = new DataInputStream(conexao.getInputStream());
             DataOutputStream saida = new DataOutputStream(conexao.getOutputStream())) {

            while (true) {
                try {
                    String mensagem = entrada.readUTF();
                    logger.info("Mensagem recebida: {}", mensagem);
                    String resposta = Parser.processarMensagem(mensagem, cartaoService);
                    saida.writeUTF(resposta);
                } catch (Exception e) {
                    logger.warn("Conexão encerrada pelo cliente.");
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("Erro no processamento do cliente: ", e);
        }
    }

    public static void stopServer() {
        serverRunning = false;
        logger.info("Servidor será encerrado.");
    }
}