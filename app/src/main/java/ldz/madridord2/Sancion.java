package ldz.madridord2;

public class Sancion {
    String tipo, ley, articulo;

    public Sancion(){};

    public Sancion(String ley, String tipo){
        this.tipo = tipo;
        this.ley = ley;
    };
    public Sancion(String ley, String tipo, String articulo){
        this.tipo = tipo;
        this.ley = ley;
        this.articulo = articulo;
    };

    public String Calcular (){
        switch (ley) {
            case "lopsc":
                if (tipo.toUpperCase().contains("LEVE")) {
                    return ("100 - 600 €");
                } else if (tipo.toUpperCase().trim().equals("GRAVE")) {
                    return("601 - 30.000 €");
                } else if (tipo.toUpperCase().contains("MUY GRAVE")) {
                    return("30.001 - 600.000 €");
                }
                break;

            case "acustica":
                switch (articulo.split(" ")[0]) {
                    case "59.1":
                        return("Hasta 600 € \nActividades comerciales, industriales y de servicios.");
                    case "59.2":
                        return("601 - 12.000 € \nActividades comerciales, industriales y de servicios.");
                    case "59.3":
                        return("12.001 - 300.000 € \nActividades comerciales, industriales y de servicios.");

                    case "60.1":
                        return("Hasta 90 € \nVehículos de motor y ciclomotores");

                    case "60.2":
                        return("91 - 300 €  \nVehículos de motor y ciclomotores");

                    case "60.3":
                        return("301 - 600 €  \nVehículos de motor y ciclomotores");

                    case "61.1":
                        return("Hasta 750 € \nUsuarios vía pública, actividades domésticas y relaciones vecinales.");

                    case "61.2":
                        return("Hasta 1.500 € \nUsuarios vía pública, actividades domésticas y relaciones vecinales.");

                    case "61.3":
                        return("Hasta 3.000 € \nUsuarios vía pública, actividades domésticas y relaciones vecinales.");
                }
                break;

            case "animales":
                if (tipo.toUpperCase().contains("LEVE")) {
                    return("150 - 300 €");
                } else if (tipo.toUpperCase().trim().equals("GRAVE")) {
                    return("301 - 2.400 €");
                } else if (tipo.toUpperCase().contains("MUY GRAVE")) {
                    return("2.401 - 15.000 €");
                }
                break;

            case "alimentacion":
                if (tipo.toUpperCase().contains("LEVE")) {
                    return("Hasta 5.000 €");
                } else if (tipo.toUpperCase().trim().equals("GRAVE")) {
                    return("5.001 - 20.000 €");
                } else if (tipo.toUpperCase().contains("MUY GRAVE")) {
                    return("20.001 - 600.000 €");
                } else {
                    return("Consultar norma");
                }


            case "lepar":
                switch (articulo.split(" ")[0]) {
                    case "38.8":
                        return("4.501 - 90.000 €");

                    case "38.17":
                        return("4.501 - 90.000 €");

                    case "37.10":
                        return("60.001 - 900.000 €");

                }
                if (tipo.toUpperCase().contains("LEVE")) {
                    return("Hasta 4.500 €");
                } else if (tipo.toUpperCase().trim().contains("GRAVE")) {
                    return("4.501 - 60.000 €");
                } else if (tipo.toUpperCase().contains("MUY GRAVE")) {
                    return("60.001 - 600.000 €");
                } else {
                    return("Consultar norma");
                }

            default:
                return("Consultar norma");

        }
        return "Consultar norma";
    }

}
