package com.example.photoview

import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasContentDescriptionExactly
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.photoview.api.DTOs.search.PhotosDTO
import com.example.photoview.api.DTOs.search.SearchDTO
import com.example.photoview.utils.imageBitmap
import com.example.photoview.view.search.PhotoSearchView
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoSearchViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun yorkshirePopulates() {
        val flickrAPI = mockkFlickrApiSearch(PhotosDTO())

        composeTestRule.setContent {
            PhotoSearchView(onPhoto = {}, onUser = {})
        }

        composeTestRule.onNodeWithText("Yorkshire").assertIsDisplayed()
        coVerify {
            flickrAPI.search("Yorkshire", any(), any(), 1)
        }
    }

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
        mockkFlickrApiSearch(photosDTO)

        composeTestRule.setContent {
            PhotoSearchView(onPhoto = {}, onUser = {})
        }

        composeTestRule.onNode(hasContentDescription("Flickr Image")).assertIsDisplayed()

        coVerify {
            photoDTO.getThumbnail()
        }
    }

    @Test
    fun buddyIconDoesDisplay() {
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

        mockkFlickrApiSearch(photosDTO)

        composeTestRule.setContent {
            PhotoSearchView(onPhoto = {}, onUser = {})
        }

        //do not want placeholder buddy icon so check content description matches exactly
        composeTestRule.onNode(hasContentDescriptionExactly("Buddy Icon")).assertIsDisplayed()

        coVerify {
            photoDTO.getBuddyIcon()
        }
    }

    @Test
    fun tagsDoDisplay() {
        val photoDTO = mockPhotoDTO(mTags = "test tag")
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

        mockkFlickrApiSearch(photosDTO)

        composeTestRule.setContent {
            PhotoSearchView(onPhoto = {}, onUser = {})
        }

        //commas are added between tags, so manually added commas here
        composeTestRule.onNodeWithText("Tags: test, tag").assertIsDisplayed()
    }

    @Test
    fun buttonDoesDisplay() {
        val photoDTO = mockPhotoDTO(mOwner = "testOwner")
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

        mockkFlickrApiSearch(photosDTO)

        composeTestRule.setContent {
            PhotoSearchView(onPhoto = {}, onUser = {})
        }

        //do not want placeholder buddy icon so check content description matches exactly
        composeTestRule.onNode(hasContentDescriptionExactly("Buddy Icon")).assertIsDisplayed()
        //space needed in front of 'testOwner' to match UI space between icon and text
        composeTestRule.onNodeWithText(" testOwner").assertIsDisplayed()

        coVerify {
            photoDTO.getBuddyIcon()
        }
    }
}