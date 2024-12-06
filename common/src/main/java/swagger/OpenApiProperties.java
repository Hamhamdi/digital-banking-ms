package swagger;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "openapi")
@Getter @Setter
public class OpenApiProperties {
    private String title;
    private String version;
    private String description;
    private Contact contact = new Contact();
    private License license = new License();

    @Getter
    @Setter
    public static class Contact {
        private String name;
        private String email;
        private String url;
    }

    @Getter
    @Setter
    public static class License {
        private String name;
        private String url;
    }


}
