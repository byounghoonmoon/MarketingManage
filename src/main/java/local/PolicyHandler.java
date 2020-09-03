package local;

import local.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    MarketingRepository marketingRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationRequested_ReservationCollect(@Payload ReservationCompleted completed){

            if(completed.isMe()){
                Marketing temp = new Marketing();
                temp.setCustNm(completed.getCustNm());
                temp.setHospitalId(Long.parseLong(completed.getHospitalId()));
                temp.setHospitalNm(completed.getHospitalNm());
                temp.setResvid(completed.getId());
                marketingRepository.save(temp);
            }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationChanged_ReservationCollect2(@Payload ReservationChanged changed){

        if(changed.isMe()){
            //  변경된 것은 상태를 맞춰 준다.
                Marketing temp = marketingRepository.findByResvid(changed.getId());
                temp.setStatus(changed.getStatus    ());
                marketingRepository.save(temp);

        }
    }


}
