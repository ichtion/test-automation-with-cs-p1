package codeSprinters.refactoring;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import codeSprinters.refactoring.dao.PictureDao;
import codeSprinters.refactoring.domain.Picture;
import codeSprinters.refactoring.domain.User;

public class PictureRetrieverTest {

	private PictureRetriever pictureRetriever = new PictureRetriever();
	private User userWithImages, userThatLogins;
	private Picture picture = new Picture();

	@Before
	public void setUp() {
		userWithImages = new User();
		PictureDao.addPicturesForUser(userWithImages, Arrays.asList(picture));
		userThatLogins = new User();
	}

	@Test(expected = NoLoggedUserException.class)
	public void failureWhenNoUserIsLoggedIn() throws NoLoggedUserException {
		userThatLogins = null;
		pictureRetriever.getPicturesFor(userWithImages, userThatLogins);
	}

	@Test
	public void firendShouldHaveAccessToPictures() {

		userThatLogins.addFriend(userWithImages);

		List<Picture> retrievedImages = pictureRetriever.getPicturesFor(
				userWithImages, userThatLogins);

		assertThat(retrievedImages, Matchers.containsInAnyOrder(picture));
	}

	@Test
	public void noAccessToGaleryForUnrelatedUsers() {
		User userUnrelated = new User();
		userThatLogins.addFriend(userUnrelated);
		List<Picture> retrievedImages = pictureRetriever.getPicturesFor(userWithImages,
				userThatLogins);
		assertThat(retrievedImages, is(nullValue()));
	}
}
