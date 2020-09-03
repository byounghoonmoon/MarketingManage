package local;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MarketingRepository extends PagingAndSortingRepository<Marketing, Long>{

    Marketing findByResvid(Long resvId);
}