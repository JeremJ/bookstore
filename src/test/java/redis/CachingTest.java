package redis;

import com.bs.library.book.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CachingTest {

    interface BookRepo extends Repository<Object, Long> {

        @Cacheable("allBooks")
        Page<Book> findAll(Pageable pageable);
    }

    @Configuration
    @EnableCaching
    static class Config {
        @Bean
        CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("allBooks");
        }

        @Bean
        BookRepo myRepo() {
            return Mockito.mock(BookRepo.class);
        }
    }

    @Autowired CacheManager manager;
    @Autowired BookRepo repo;

    @Test
    public void methodInvocationShouldBeCached() {
        //given
        List<Book> books = Arrays.asList(
                new Book(1L, 7576575, "Czysty Kod", "Robert C. Martin", new BigDecimal(50.99), "qwerty", "qwerty", "qwerty"),
                new Book(2L, 9432123, "Ogniem i mieczem", "Henryk Sienkiewicz", new BigDecimal(99.99), "qwerty", "qwerty", "qwerty")
        );
        Page<Book> page = new PageImpl<>(books);
        Pageable firstPage = PageRequest.of(0, 2);
        Pageable secondPage = PageRequest.of(1, 2);

        //when
        Mockito.when(repo.findAll(Mockito.any(Pageable.class))).thenReturn(page);
        Object result = repo.findAll(firstPage);
        //then
        assertThat(result.equals(firstPage));
        result = repo.findAll(firstPage);
        assertThat(result.equals(firstPage));
        Mockito.verify(repo, Mockito.times(1)).findAll(firstPage);
        assertThat(manager.getCache("allBooks").get(firstPage) == null);
        result = repo.findAll(secondPage);
        assertThat(result.equals(secondPage));
    }
}
