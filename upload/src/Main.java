import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        FileClient client = new FileClient("192.168.1.27", 25584);

        System.out.println(client.uploadJar("Survival") ? "Success" : "Failure");
        client.endConnection();
    }

}
