package br.com.boletoapi.services;

import br.com.boletoapi.model.Boleto;
import org.springframework.stereotype.Service;

@Service
public class BoletoPdfService {

    public byte[] gerarPdf(Boleto boleto){
        // Simulação: em breve, implementar com iText ou JasperReports
        System.out.println("Gerando PDF para o boleto: " + boleto.getNossoNumero());
        return new byte[0];
    }
}
