package br.com.boletoapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "boletos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Boleto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nossoNumero;
    private String linhaDigitavel;
    private String codigoBarras;
    private String qrCodePix;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private LocalDate dataEmissao;
    private String nomePagador;
    private String cpfCnpjPagador;
    private String status;

}
