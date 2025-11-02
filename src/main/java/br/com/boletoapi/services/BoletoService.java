package br.com.boletoapi.services;

import br.com.boletoapi.model.Boleto;
import br.com.boletoapi.repository.BoletoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoletoService {

    private final BoletoRepository boletoRepository;
    private final BoletoPdfService boletoPdfService;

    public Boleto gerarBoleto(Boleto boleto) {
        boleto.setDataEmissao(LocalDate.now());
        boleto.setStatus("EMITIDO");

        boleto.setNossoNumero(gerarNossoNumero());
        boleto.setLinhaDigitavel(gerarLinhaDigitavel(boleto));
        boleto.setCodigoBarras(gerarCodigoBarras(boleto));
        boleto.setQrCodePix(gerarQrCodePix(boleto));

        Boleto boletoSalvo = boletoRepository.save(boleto);

        boletoPdfService.gerarPdf(boletoSalvo);

        return boletoSalvo;
    }

    public Optional<Boleto> buscarPorId(Long id){
        return boletoRepository.findById(id);
    }

    public List<Boleto> buscarTodos(){
        return boletoRepository.findAll();
    }

    public byte[] gerarPdf(Long id) {
        Boleto boleto = boletoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleto não encontrado"));
        return boletoPdfService.gerarPdf(boleto);
    }

    private String gerarQrCodePix(Boleto boleto) {
        return "00020126580014br.gov.bcb.pix013BOLETO-" + boleto.getNossoNumero();
    }

    private String gerarCodigoBarras(Boleto boleto) {
        return "0019" + boleto.getNossoNumero() + String.format("¨%010d", boleto.getValor().multiply(BigDecimal.valueOf(100)).intValue());
    }

    private String gerarLinhaDigitavel(Boleto boleto) {
        return "00190.00009 01234.567890 12345.678902 1 " + boleto.getValor().multiply(BigDecimal.valueOf(100));
    }

    private String gerarNossoNumero() {
        return UUID.randomUUID().toString().substring(0, 10).replace("-", "").toUpperCase();
    }
}
