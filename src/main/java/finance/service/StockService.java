package finance.service;
import finance.dto.StockDTO;
import java.util.List;

public interface StockService {
    List<StockDTO> getStocks();
    void refreshStocks();
}