package com.example.photoview

import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.photoview.api.DTOs.search.PhotosDTO
import com.example.photoview.api.DTOs.search.SearchDTO
import com.example.photoview.api.DTOs.userInfo.BaseUserInfoDTO
import com.example.photoview.api.DTOs.userInfo.UserInfoDTO
import com.example.photoview.utils.imageBitmap
import com.example.photoview.view.userInfo.UserView
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserInfoViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun photosDoDisplay() {
        val photoDTO = mockPhotoDTO()
        mockkStatic(ByteArray::imageBitmap)
        every { any<ByteArray>().imageBitmap() } returns testBitmap().asImageBitmap()

        val photosDTO = PhotosDTO(
            SearchDTO(
                listOf(
                    photoDTO
                ),
                page = 1,
                pages = 1,
                perpage = 1,
                total = 1
            )
        )
        val flickrAPI = mockkFlickrApiSearch(photosDTO)
        mockkFlickrApiUserInfo(
            UserInfoDTO(
                BaseUserInfoDTO()
            ), flickrAPI
        )

        composeTestRule.setContent {
            UserView({}, {}, "user_id")
        }

        composeTestRule.onNode(hasContentDescription("Flickr Image")).assertIsDisplayed()

        coVerify {
            photoDTO.getThumbnail()
            flickrAPI.userInfo("user_id")
            flickrAPI.search(any(), "user_id", any(), any())
        }
    }

    @Test
    fun ownerIdDoesDisplay() {
        composeTestRule.setContent {
            UserView({}, {}, "testId")
        }
        composeTestRule.onNodeWithText("testId").assertIsDisplayed()
    }

    @Test
    fun userDescriptionDoesDisplay() {
        val flickrAPI = mockkFlickrApiUserInfo(
            UserInfoDTO(
                BaseUserInfoDTO(
                    profile_description = "test description"
                )
            )
        )

        composeTestRule.setContent {
            UserView({}, {}, "user_id")
        }

        composeTestRule.onNode(hasContentDescription("Expand")).performClick()
        composeTestRule.onNodeWithText("test description").assertIsDisplayed()

        coVerify {
            flickrAPI.userInfo("user_id")
        }
    }

    @Test
    fun userJoinedOnDateDoesDisplay() {
        val flickrAPI = mockkFlickrApiSearch(PhotosDTO())
        mockkFlickrApiUserInfo(
            UserInfoDTO(
                BaseUserInfoDTO(
                    join_date = 1725111100
                )
            ),
            flickrAPI
        )

        composeTestRule.setContent {
            UserView({}, {}, "user_id")
        }

        composeTestRule.onNode(hasContentDescription("Expand")).performClick()
        composeTestRule.onNodeWithText("Joined on 31/08/2024").assertIsDisplayed()

        coVerify {
            flickrAPI.userInfo("user_id")
        }
    }
}