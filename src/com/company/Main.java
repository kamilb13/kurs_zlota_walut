import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        String endpoint = "https://api.nbp.pl/api/cenyzlota/last/30";
        ArrayList<com.company.GoldPrice> prices = new ArrayList<>();
        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            if (connection.getResponseCode() != 200 ){
                System.out.println(String.format("Polecial blad!. Kod: %d", connection.getResponseCode()));
            }
            InputStreamReader input = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(input);
            String output;
            ArrayList<Float> tableOfprices = new ArrayList<Float>();
            while ((output = bufferedReader.readLine()) != null) {
                //System.out.println(output);
                output = output.replace("[{", "");
                output = output.replace("}]", "");
                output = output.replace("{", "");
                output = output.trim();
                String[] outputs = output.split("},");

                for (String element : outputs) {
                    String price = element.substring(27, 32);
                    Float price_float = Float.valueOf(price);
                    tableOfprices.add(price_float);
                    //System.out.println(element);
                    String[] elements = element.split(",");
                    prices.add(new com.company.GoldPrice(parseDate(elements[0]), parsePrice(elements[1])));
                }
            }
            float averagePrice = 0;
            float sum = 0;
            for(Float element : tableOfprices){
                sum += element;
            }
            averagePrice = sum / tableOfprices.size();
            System.out.println(tableOfprices);
            System.out.println(averagePrice);
            prices.forEach(System.out::println);
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static LocalDate parseDate(String date) {
        String localDate = date.split(":")[1].replace('"', ' ').trim();
        return LocalDate.of(Integer.valueOf(localDate.split("-")[0]),
                Month.of(Integer.valueOf(localDate.split("-")[1])),
                Integer.valueOf(localDate.split("-")[2]));
    }

    private static BigDecimal parsePrice(String price) {
        return new BigDecimal(price.split(":")[1]);
    }
}
