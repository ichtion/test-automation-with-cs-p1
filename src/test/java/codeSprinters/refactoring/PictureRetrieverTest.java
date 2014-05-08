package codeSprinters.refactoring;

import codeSprinters.refactoring.dao.PictureDao;
import codeSprinters.refactoring.domain.Picture;
import codeSprinters.refactoring.domain.User;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class PictureRetrieverTest {

    private PictureRetriever pictureRetriever = new PictureRetriever();

    @Test(expected = NoLoggedUserException.class)
    public void shouldNotGetPictureIfNotLoggedIn() throws Exception {
        User user1 = new User();
        pictureRetriever.getPicturesFor(user1, null);
    }

    @Test
    public void shouldGetNewPictureIfNewFriend() {
        Picture picture = new Picture();
        User user1 = new User();
        User user2 = new User();
        user2.addFriend(user1);
        PictureDao.addPicturesForUser(user1, Arrays.asList(picture));
        List<Picture> list = pictureRetriever.getPicturesFor(user1, user2);
        assertThat(list, Matchers.containsInAnyOrder(picture));
    }

    @Test
    public void shouldNotGetPictureIfNotFriend() {
        User user1 = new User();
        User user2 = new User();
        List<Picture> list = pictureRetriever.getPicturesFor(user1, user2);
        assertThat(list, is(nullValue()));
    }

    @Test
    public void shouldNotGetNewAddedPictureIfNotFriend() {
        Picture picture = new Picture();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user2.addFriend(user3);
        PictureDao.addPicturesForUser(user1, Arrays.asList(picture));
        List<Picture> list = pictureRetriever.getPicturesFor(user1, user2);
        assertThat(list, is(nullValue()));
    }
}
