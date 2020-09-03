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
            //  예약 완료 된건만 마켓팅 전략을 세우기 위해 데이터를 모집한다.
            if(completed.isMe()){
                Marketing temp = new Marketing();
                temp.setCustNm(completed.getCustNm());
                temp.setHospitalId(completed.getHospitalId());
                temp.setHospitalNm(completed.getHospitalNm());
                temp.setResvid(completed.getId());
                marketingRepository.save(temp);
            }
        }
    }


}
