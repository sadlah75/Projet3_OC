
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.annotation.NonNull;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewUtil.atPositionOnView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;

import java.util.List;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private static int ITEM_POSITION = 2;

    private ListNeighbourActivity mActivity;
    private NeighbourApiService mService;
    private List<Neighbour> mNeighboursList;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());

        mService = DI.getNewInstanceApiService();
        mNeighboursList = mService.getNeighbours();
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        ViewInteraction recyclerView = onView(allOf(withId(R.id.list_neighbours), isDisplayingAtLeast(60)));
        recyclerView.check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we click on Item, the item details is launched
     */
    @Test
    public void neighbourDetails_onClickItem_shouldBeLaunched() {
        //Given : Launch Details Activity
        //When perform a click on item at position 2
        ViewInteraction recyclerView = onView(allOf(withId(R.id.list_neighbours), isDisplayingAtLeast(60)));
        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_POSITION, click()));
        //Then : we check if add Fab in Details is displayed
        onView(withId(R.id.activity_display_add_fab)).check(matches(isDisplayed()));
    }

    /**
     * Check if the name of Item displayed in details is the same as the name item selected in the list
     */
    @Test
    public void neighbourDetailsName_checkIsCorrect() {
        Neighbour neighbour = mNeighboursList.get(ITEM_POSITION);
        //Given : We display the TextView Name in Details
        //When perform a click on the Item at position 2
        ViewInteraction recyclerView = onView(allOf(withId(R.id.list_neighbours), isDisplayingAtLeast(60)));
        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_POSITION,click()));
        //Then : We check if the name displayed in details is identical to the name of the Item selected
        onView(withId(R.id.activity_display_name_profile)).check(matches(withText(neighbour.getName())));

    }

    /**
     * When add an item, the neighbours's size is increment by 1
     */
    @Test
    public void myNeighboursList_addAction_shouldIncrementByOne() {
        onView(withId(R.id.add_neighbour)).perform(click());
        onView(withId(R.id.name)).perform(clearText(),typeText("test"));
        closeSoftKeyboard();
        onView(withId(R.id.create)).perform(click());
        ViewInteraction recyclerView = onView(allOf(withId(R.id.list_neighbours), isDisplayingAtLeast(60)));
        recyclerView.check(withItemCount(ITEMS_COUNT+1));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        ViewInteraction recyclerView = onView(allOf(withId(R.id.list_neighbours), isDisplayingAtLeast(60)));
        // When perform a click on a delete icon
        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        recyclerView.check((withItemCount(ITEMS_COUNT-1)));
    }



    /**
     * When we add an item as favorite, the item selected should be in to favorite's tab
     */

    @Test
    public void favoriteNeighbour_addItem_shouldBeInFavorites() {
        ViewInteraction recyclerView = onView(allOf(withId(R.id.list_neighbours), isDisplayingAtLeast(60)));
        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(ViewMatchers.withId(R.id.activity_display_add_fab)).perform(click());
        onView(ViewMatchers.withId(R.id.activity_display_return)).perform(click());
        onView(withId(R.id.container)).perform(swipeLeft());

        recyclerView.check(matches(atPositionOnView(0, withText("Jack"),R.id.item_list_name)));

    }
}
