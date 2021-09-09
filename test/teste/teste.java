package teste;

import RobotBaruffaldiTemplates.RobotBaruffaldiTemplates;

public class teste {

    public static void main(String[] args) {
        StringBuilder parametros = new StringBuilder();
        
        parametros.append("[mes:6]");
        parametros.append("[ano:2021]");
        parametros.append("[ini:robot-pantano]");

        RobotBaruffaldiTemplates.testParameters = parametros.toString();
        args = new String[]{"test"};

        RobotBaruffaldiTemplates.main(args);
    }
}
