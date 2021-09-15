package teste;

import RobotBaruffaldiTemplates.PrepareFiles;
import RobotBaruffaldiTemplates.RobotBaruffaldiTemplates;

public class teste {

    public static void main(String[] args) {
        testMain(args);
    }
    
    public static void testPrepareFile(){
        RobotBaruffaldiTemplates.ano = 2021;
        RobotBaruffaldiTemplates.mes = 6;
        
        PrepareFiles.prepare();
    }

    public static void testMain(String[] args) {
        StringBuilder parametros = new StringBuilder();

        parametros.append("[mes:6]");
        parametros.append("[ano:2021]");
        parametros.append("[ini:robot-baruffaldiadv]");

        RobotBaruffaldiTemplates.testParameters = parametros.toString();
        args = new String[]{"test"};

        RobotBaruffaldiTemplates.main(args);
    }
}
