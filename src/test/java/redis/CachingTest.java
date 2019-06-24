package redis;

import com.bs.library.LibraryApplication;
import com.bs.library.book.BookRepository;
import com.bs.library.book.BookService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static com.bs.library.config.CacheConfig.BOOK_CACHEVALUE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibraryApplication.class)

@AutoConfigureMockMvc
public class CachingTest {

    @Autowired
    BookService bookService;

    @MockBean
    BookRepository bookRepository;

    @Autowired
    CacheManager cacheManager;


    @Before
    public void before() {
        BookService bookService = new BookService(bookRepository);
        MockitoAnnotations.initMocks(this);
        cacheManager.getCache(BOOK_CACHEVALUE).clear();
    }

    @After
    public void after() {
        cacheManager.getCache(BOOK_CACHEVALUE).clear();
    }

    @Test
    public void methodInvocationShouldBeCached() throws Exception {
        //given
        Pageable firstPage = PageRequest.of(0, 4);
        bookService.allBooksPageable(firstPage);
        bookService.allBooksPageable(firstPage);
        //then
        Mockito.verify(bookRepository, Mockito.times(1)).findAll(Mockito.any(Pageable.class));
    }
}
