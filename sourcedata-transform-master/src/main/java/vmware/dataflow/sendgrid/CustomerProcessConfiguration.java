package vmware.dataflow.sendgrid;



import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;

@EnableBinding(Processor.class)
public class CustomerProcessConfiguration {

    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public String convertToPojo(Customer payload) {
        Customer customer=new Customer();

        final StringBuilder sb = new StringBuilder("{\"eventAttributes\":{");
        sb.append("\"eventName\":").append('"'+payload.getName()+'"');
        sb.append(",\"locale\":").append('"'+payload.getLocale()+'"');
        sb.append(",\"sourceSystem\":").append('"'+payload.getSourceSystem()+'"');
        sb.append("},\"basicAttributes\":{");
        sb.append("\"to\":[").append('"'+payload.getAddress()+'"').append("]},");
        sb.append("\"parameters\":[]}");
        System.out.println("payload"+sb.toString());
        return sb.toString();
        /*return payload;*/
    }
}