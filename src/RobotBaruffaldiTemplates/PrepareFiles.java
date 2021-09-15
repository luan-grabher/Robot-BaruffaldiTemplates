package RobotBaruffaldiTemplates;

import JExcel.JExcel;
import static RobotBaruffaldiTemplates.RobotBaruffaldiTemplates.ano;
import static RobotBaruffaldiTemplates.RobotBaruffaldiTemplates.mes;
import fileManager.FileManager;
import fileManager.Selector;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PrepareFiles {

    public static String[] monthNames = new String[]{"Janeiro", "Janeiro", "Favereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

    public static String prepare() {
        try {
            // Pega aquivo despesas ordinarias.
            File folder = FileManager.getFile("\\\\HEIMERDINGER\\DOCS\\Contábil\\Clientes\\Norberto Baruffaldi Advogados Associados SS\\Escrituração mensal\\" + ano + "\\Extratos\\" + (mes < 10 ? "0" : "") + mes + "." + ano);
            File file = Selector.getFileOnFolder(folder, "Despesas;Ordinarias;Extraordin;.xlsx");

            XSSFWorkbook wk = new XSSFWorkbook(file);

            //Vai para a aba do mês -> converte numero para nome
            XSSFSheet sh = wk.getSheet(monthNames[mes]);

            //Texto do csv que será salvo
            StringBuilder csvText = new StringBuilder("#DATA;DOC;HISTORICO;VALOR");

            String[] section = new String[]{""};

            // Precorre arquivo <br>
            sh.forEach((row) -> {
                String date = JExcel.getCellString(row.getCell(JExcel.Cell("B")));
                String doc = JExcel.getCellString(row.getCell(JExcel.Cell("C")));
                String hist = JExcel.getCellString(row.getCell(JExcel.Cell("D")));
                String valor = JExcel.getCellString(row.getCell(JExcel.Cell("E")));                
                
                // -- Se não tiver data na primeira e a B, C e D forem iguais <br>               
                if (date.matches("[^0-9]+") &&  "".equals(doc) && "".equals(hist)) {
                    // ---- Grava o valor como 'seção' Ex: Despesas com pessoal <br>
                    section[0] = date;
                } // -- Se na B tiver data, na C nao estiver em branco, na D nao estiver em branco e na E tiver um numero <br>
                else if (date.matches("[0-9]+") && !hist.equals("") && !valor.equals("")) {
                    // ---- Grava no arquivo Excel que está sendo criado a data(B), documento(C), historico("seção" + D), valor (E)<br>
                    csvText.append("\r\n");
                    csvText.append(JExcel.getStringDate(Integer.valueOf(date))).append(";");
                    csvText.append(doc).append(";");
                    csvText.append(section[0]).append(" - ").append(hist).append(";");
                    csvText.append((new BigDecimal(valor)).negate().setScale(2, RoundingMode.HALF_UP).toPlainString().replaceAll("\\.", ","));
                }
            });
            // Salva o arquivo CSV na pasta DESP MES_ANO.xslx"
            FileManager.save(folder, "DESP ZAC " + ano + "_" + mes + ".csv", csvText.toString());

            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "Ocorreu o seguinte erro ao converter o arquivo de despesas para CSV: " + e.getMessage();
        }
    }
}
