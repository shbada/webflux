package com.grizz.wooman.completablefuture.blocking;

import com.grizz.wooman.completablefuture.blocking.repository.ArticleRepository;
import com.grizz.wooman.completablefuture.blocking.repository.FollowRepository;
import com.grizz.wooman.completablefuture.blocking.repository.ImageRepository;
import com.grizz.wooman.completablefuture.blocking.repository.UserRepository;
import com.grizz.wooman.completablefuture.common.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceBlockingTest {
    UserBlockingService userBlockingService;
    UserRepository userRepository;
    ArticleRepository articleRepository;
    ImageRepository imageRepository;
    FollowRepository followRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        articleRepository = new ArticleRepository();
        imageRepository = new ImageRepository();
        followRepository = new FollowRepository();

        userBlockingService = new UserBlockingService(
                userRepository, articleRepository, imageRepository, followRepository
        );
    }

    /**
     * 1초 (userRepository만 조회했기때문)
     */
    @Test
    void getUserEmptyIfInvalidUserIdIsGiven() {
        // given
        String userId = "invalid_user_id"; // 없는 유저 ID 셋팅

        // when
        Optional<User> user = userBlockingService.getUserById(userId);

        // then
        assertTrue(user.isEmpty());
    }

    /**
     * 정상적인 케이스
     * 4초
     * - user, image, article, follow
     */
    @Test
    void testGetUser() {
        // given
        String userId = "1234"; // 존재하는 유저 아이디

        // when
        Optional<User> optionalUser = userBlockingService.getUserById(userId);

        // then
        assertFalse(optionalUser.isEmpty());
        var user = optionalUser.get();
        assertEquals(user.getName(), "taewoo");
        assertEquals(user.getAge(), 32);

        assertFalse(user.getProfileImage().isEmpty());
        var image = user.getProfileImage().get();
        assertEquals(image.getId(), "image#1000");
        assertEquals(image.getName(), "profileImage");
        assertEquals(image.getUrl(), "https://dailyone.com/images/1000");

        assertEquals(2, user.getArticleList().size());

        assertEquals(1000, user.getFollowCount());
    }
}
