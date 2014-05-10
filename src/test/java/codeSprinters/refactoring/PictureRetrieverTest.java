package codeSprinters.refactoring;

import codeSprinters.refactoring.domain.Picture;
import codeSprinters.refactoring.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static codeSprinters.refactoring.dao.PictureDao.addPicturesForUser;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class PictureRetrieverTest {

    private PictureRetriever pictureRetriever = new PictureRetriever();
    private final List<Picture> pictures = asList(new Picture());
    private final User friendWithPictures = new User();
    private final User anotherFriendWithoutPictures = new User();
    private final User loggedInUserWithFriend = new User();

    @Before
    public void setup() {
        addPicturesForUser(friendWithPictures, pictures);
        loggedInUserWithFriend.addFriend(friendWithPictures);
    }

    @Test(expected = NoLoggedUserException.class)
    public void shouldThrowNoLoggedUserExceptionWhenNoLoggedUserIsProvided() throws Exception {
        User NO_LOGGED_USER = null;

        pictureRetriever.getPicturesFor(friendWithPictures, NO_LOGGED_USER);
    }

    @Test
    public void shouldRetrievePicturesFromUserOnlyIfItIsFriendOfLoggedInUser() {
        User friend = new User();
        List<Picture> friendsPictures = asList(new Picture());
        addPicturesForUser(friend, friendsPictures);
        loggedInUserWithFriend.addFriend(friend);

        List<Picture> list = pictureRetriever.getPicturesFor(friend, loggedInUserWithFriend);

        assertThat(list, containsInAnyOrder(friendsPictures.toArray()));
    }

    @Test
    public void shouldReturnNullWhenAskedToRetrievePicturesForUserThatIsNotFriendOfLoggedInUser() {
        User loggedInUser = new User();
        loggedInUser.addFriend(anotherFriendWithoutPictures);

        List<Picture> list = pictureRetriever.getPicturesFor(friendWithPictures, loggedInUser);

        assertThat(list, is(nullValue()));
    }
}
